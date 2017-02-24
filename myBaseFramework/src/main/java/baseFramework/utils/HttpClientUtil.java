package baseFramework.utils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Maps;

/**
 * @author chao.li
 * @date 2016年10月11日
 */
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	private static final int defaultTimeout = 3 * 1000;
	private static final int maxTotal = 100;
	private static final int maxPerRoute = 100;
	private static final int maxRoute = 100;
	private static final int retry = 2;

	private static LoadingCache<String, HttpClientCacheValue> cache = CacheBuilder.newBuilder().concurrencyLevel(1)
			.expireAfterAccess(5, TimeUnit.MINUTES)
			.removalListener(new RemovalListener<String, HttpClientCacheValue>() {
				@Override
				public void onRemoval(RemovalNotification<String, HttpClientCacheValue> notification) {
					try {
						notification.getValue().cm.close();
						notification.getValue().httpClient.close();
						logger.info("release httpClient for {}", notification.getValue());
					} catch (IOException e) {
						logger.error("release httpClient error! " + notification, e);
					}
				}
			}).build(new CacheLoader<String, HttpClientCacheValue>() {

				@Override
				public HttpClientCacheValue load(String key) throws Exception {
					String hostname;
					int port = 80;

					if (key.contains(":")) {
						String[] arr = key.split(":");
						hostname = arr[0];
						port = Integer.parseInt(arr[1]);
					} else {
						hostname = key;
					}

					HttpRoute httpRoute = new HttpRoute(new HttpHost(hostname, port));
					HttpClientCacheValue cacheValue = new HttpClientCacheValue();
					cacheValue.httpRoute = httpRoute;
					createHttpClient(cacheValue);
					logger.info("create httpClient for {}", key);
					return cacheValue;
				}

			});

	static class HttpClientCacheValue {
		CloseableHttpClient httpClient;
		HttpRoute httpRoute;
		PoolingHttpClientConnectionManager cm;
	}

	private static CloseableHttpClient getHttpClient(String url) {

		Preconditions.checkArgument(StringUtils.isNotBlank(url), "url must not be null!");

		String hostAndPort = url.split("/")[2];
		try {
			return cache.get(hostAndPort).httpClient;
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createHttpClient(HttpClientCacheValue cacheValue) {

		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainsf).register("https", sslsf).build();

		// 连接池工厂
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		// 将最大连接数增加
		cm.setMaxTotal(maxTotal);
		// 将每个路由基础的连接增加
		cm.setDefaultMaxPerRoute(maxPerRoute);
		// 将目标主机的最大连接数增加
		cm.setMaxPerRoute(cacheValue.httpRoute, maxRoute);
		cm.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(true).build());

		// 请求重试处理
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= retry) {// 如果已经重试了2次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					return false;
				}
				if (exception instanceof SSLException) {// SSL握手异常
					return false;
				}

				HttpClientContext clientContext = HttpClientContext.adapt(context);
				HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					return true;
				}
				return false;
			}
		};

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
				.setRetryHandler(httpRequestRetryHandler).build();
		cacheValue.cm = cm;
		cacheValue.httpClient = httpClient;
	}

	private static void configTimeout(HttpRequestBase httpRequestBase, int timeout) {

		RequestConfig requestConfig = RequestConfig.custom()// 配置请求的超时设置
				.setConnectionRequestTimeout(timeout)// 从connectManager获取Connection超时时间
				.setConnectTimeout(timeout)// 连接超时时间
				.setSocketTimeout(timeout)// 请求获取数据的超时时间
				.setContentCompressionEnabled(true)// 启用压缩
				.build();
		httpRequestBase.setConfig(requestConfig);
	}

	private static void configHander(HttpRequestBase httpRequestBase, Map<String, String> header) {
		if (header != null)
			for (Entry<String, String> entry : header.entrySet()) {
				httpRequestBase.setHeader(entry.getKey(), entry.getValue());
			}
	}

	private static void setPostParams(HttpPost httpost, Map<String, Object> params)
			throws UnsupportedEncodingException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
	}

	public static Map<String, PoolStats> getAllStats() {
		Map<String, PoolStats> result = Maps.newHashMap();
		try {
			for (Entry<String, HttpClientCacheValue> entry : cache.asMap().entrySet()) {
				HttpClientCacheValue httpClientValue = entry.getValue();
				result.put(entry.getKey(), httpClientValue.cm.getStats(httpClientValue.httpRoute));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return result;
	}

	public static String post(String url, Map<String, Object> params) throws IOException {
		return post(url, params, defaultTimeout);
	}

	public static String post(String url, Map<String, Object> params, int timeout) throws IOException {
		HttpPost httppost = new HttpPost(url);
		configTimeout(httppost, timeout);
		setPostParams(httppost, params);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = getHttpClient(url).execute(httppost, HttpClientContext.create());
			entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} finally {
			// EntityUtils.consume(entity);
			if (response != null)
				response.close();
		}
	}

	public static String post(String url, String body, ContentType contentType, Map<String, String> header, int timeout)
			throws IOException {
		HttpPost httppost = new HttpPost(url);
		configTimeout(httppost, timeout);
		configHander(httppost, header);

		if (contentType == null)
			contentType = ContentType.DEFAULT_TEXT;

		httppost.setEntity(new StringEntity(body, contentType));
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = getHttpClient(url).execute(httppost, HttpClientContext.create());
			entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} finally {
			// EntityUtils.consume(entity);
			if (response != null)
				response.close();
		}
	}

	public static byte[] postByte(String url, byte[] body, Map<String, String> header, int timeout) throws IOException {
		HttpPost httppost = new HttpPost(url);
		configTimeout(httppost, timeout);
		configHander(httppost, header);
		httppost.setEntity(new ByteArrayEntity(body));
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = getHttpClient(url).execute(httppost, HttpClientContext.create());
			entity = response.getEntity();
			return EntityUtils.toByteArray(entity);
		} finally {
			// EntityUtils.consume(entity);
			if (response != null)
				response.close();
		}
	}

	public static String getFullUrl(String url, Map<String, String> parameters) throws IOException {
		if (url.contains("?")) {
			url = url + (parameters.size() == 0 ? "" : "&") + formatGetParameters(parameters);
		} else {
			url = url + (parameters.size() == 0 ? "" : "?") + formatGetParameters(parameters);
		}
		return url;
	}

	private static final char QP_SEP_A = '&';
	private static final String NAME_VALUE_SEPARATOR = "=";

	public static String formatGetParameters(final Map<String, String> parameters) throws IOException {
		final StringBuilder result = new StringBuilder();
		for (final Entry<String, String> parameter : parameters.entrySet()) {

			final String encodedName = parameter.getKey() != null ? URLEncoder.encode(parameter.getKey(), "utf-8")
					: null;
			final String encodedValue = parameter.getValue() != null ? URLEncoder.encode(parameter.getValue(), "utf-8")
					: null;

			if (result.length() > 0) {
				result.append(QP_SEP_A);
			}
			result.append(encodedName);
			if (encodedValue != null) {
				result.append(NAME_VALUE_SEPARATOR);
				result.append(encodedValue);
			}
		}
		return result.toString();
	}

	public static String get(String url) throws IOException {
		return get(url, defaultTimeout);
	}

	public static String get(String url, int timeout) throws IOException {
		HttpGet httpget = new HttpGet(url);
		configTimeout(httpget, timeout);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = getHttpClient(url).execute(httpget, HttpClientContext.create());
			entity = response.getEntity();
			return EntityUtils.toString(entity, "utf-8");
		} finally {
			// EntityUtils.consume(entity);
			if (response != null)
				response.close();
		}
	}

	public static int getCode(String url) throws ClientProtocolException, IOException {
		HttpGet httpget = new HttpGet(url);
		configTimeout(httpget, defaultTimeout);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			response = getHttpClient(url).execute(httpget, HttpClientContext.create());
			entity = response.getEntity();
			return response.getStatusLine().getStatusCode();
		} finally {
			EntityUtils.consume(entity);
			if (response != null)
				response.close();
		}
	}

}
