package baseFramework.es;

import baseFramework.utils.SpringContextHolder;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.TermsEnum;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.termvectors.TermVectorsResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * @author chao.li
 * @date 2017/8/11
 */
public class ElasticSearchUtil {


	private static Logger logger = LogManager.getLogger(ElasticSearchUtil.class);
	private Client client;


	private ElasticSearchUtil() {
		logger.info("init ElasticSearchUtil");
		try {
			Settings settings = Settings.builder()
					.put("cluster.name", SpringContextHolder.getPropertiesValue("es.cluster.name"))
					.put("client.transport.sniff", true)
					.build();
			TransportClient transportClient = new PreBuiltTransportClient(settings);

			List<HostAndPort> hostAndPorts = Lists.newArrayList();
			String addresses = SpringContextHolder.getPropertiesValue("es.address");
			if (StringUtils.isNotBlank(addresses)) {
				for (String item : addresses.split(",")) {
					hostAndPorts.add(HostAndPort.fromString(item));
				}
			}
			for (HostAndPort hostAndPort : hostAndPorts) {
				transportClient.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(hostAndPort.getHostText(), hostAndPort.getPort())));
			}

			client = transportClient;

		} catch (Exception e) {
			logger.error("init ElasticSearch error!", e);
		}

	}

	public static ElasticSearchUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}

	//原生态ES Client
	public static Client getClient() {
		return getInstance().client;
	}

	public static BulkResponse bulkUpdate(Map<String, Map<String, Object>> mUpdateDoc, String index, String type) {
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();

		for (Map.Entry<String, Map<String, Object>> entry : mUpdateDoc.entrySet()) {
			String contentId = entry.getKey();
			Map<String, Object> mData = (Map<String, Object>) entry.getValue();
			UpdateRequest request = new UpdateRequest(index, type, contentId).doc(mData);
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.get();

		for (BulkItemResponse itemResponse : bulkResponse) {
			String contentId = itemResponse.getId();
			if (itemResponse.isFailed()) {
				logger.debug("contentId = " + itemResponse.getId() + " update ES failed.");
			} else {
				String fieldCTR = "-1_" + "FallbackConstant.ES_CTR_NAME";
				double valueCTR = NumberUtils.toDouble(String.valueOf(mUpdateDoc.get(contentId).get(fieldCTR)));
				logger.debug("contentId = " + itemResponse.getId() + " update ES successfully " + fieldCTR + "=" + valueCTR);
			}
		}

		return bulkResponse;

	}

	public static void indexDoc(String index, String type, String id, String doc, boolean refresh) {

		WriteRequest.RefreshPolicy refreshPolicy = refresh ? WriteRequest.RefreshPolicy.IMMEDIATE : WriteRequest.RefreshPolicy.NONE;


		IndexResponse response = getClient()
				.prepareIndex(index, type, id)
				.setRefreshPolicy(refreshPolicy)
				.setSource(doc)
				.get();
		logger.info("elasticsearch index doc success. index:{}, type:{}, id:{}, result:{}, status:{}", index, type, id, response.getResult(), response.status());
	}

	public static void indexDocs(String index, String type, List<ESDocement> docs) {
		BulkRequestBuilder bulkRequest = getClient().prepareBulk();
		docs.forEach(esDoc -> {
			bulkRequest.add(getClient().prepareIndex(index, type, esDoc.getId()).setSource(esDoc.getDoc()));
		});
		bulkRequest.get().forEach(item -> {
			if (item.isFailed()) {
				logger.error("elasticsearch index error! index:{}, type:{}, id:{}, errorMsg:{}", index, type, item.getId(), item.getFailureMessage());
			} else {
				logger.info("elasticsearch index doc success. index:{}, type:{}, id:{}, result:{}, status:{}", index, type, item.getId(), item.getResponse().getResult(), item.status());
			}
		});

	}

	public static String getDoc(String index, String type, String id) {
		return getClient().prepareGet(index, type, id).get().getSourceAsString();
	}

	public static List<String> multiGetDoc(String index, String type, List<String> ids) {
		MultiGetResponse response = getClient().prepareMultiGet().add(index, type, ids).get();
		List<String> docs = Lists.newArrayList();
		response.forEach(item -> {
			if (item.getResponse().isExists())
				docs.add(item.getResponse().getSourceAsString());
		});
		return docs;
	}

	public static DeleteResponse deleteDoc(String index, String type, String id) {
		return getClient().prepareDelete(index, type, id).get();
	}

	public static void updateDoc(String index, String type, String id, String doc) {
		try {
			UpdateResponse response = getClient().prepareUpdate(index, type, id).setDoc(doc).get();
			logger.info("elasticsearch update doc. index:{}, type:{}, id:{}, result:{}, status:{}", index, type, id, response.getResult(), response.status());
		} catch (DocumentMissingException e) {
			logger.error("missing doc! index:{}, type:{}, id:{}", index, type, id);
		} catch (Exception e) {
			logger.error("update doc error!", e);
		}
	}

	public static void refresh(String index) {
		getClient().admin().indices().refresh(new RefreshRequest(index)).actionGet();
	}


	public static List<String> search(String[] indexs, String[] types, ESQCondition eSQCondition) {

		SearchRequestBuilder searchRequestBuilder = getClient()
				.prepareSearch(indexs).setTypes(types)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(eSQCondition.getQueryBuilder())// the search
//				.setFetchSource(new String[]{"id"}, null)// add field
				.setSize(eSQCondition.getSize());

		if (eSQCondition.getPostFilter() != null) {
			searchRequestBuilder.setPostFilter(eSQCondition.getPostFilter());
		}

		for (SortBuilder sortBuilder : eSQCondition.getSortBuilders()) {
			searchRequestBuilder.addSort(sortBuilder);
		}

		SearchResponse response = searchRequestBuilder.get();

		List<String> results = Lists.newArrayList();
		for (SearchHit searchHit : response.getHits().getHits()) {
			results.add(searchHit.getSourceAsString());
		}

		return results;
	}


	/**
	 * 批量查询,此方法存在性能问题,进行注释;
	 *
	 * @param index
	 * @param type
	 * @return BulkResponse
	 */
	public BulkResponse bulkUpdate(HashMap<String, Double> map, String index, String type) {
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		for (String infoid : map.keySet()) {
			Map<String, Object> json = new HashMap<String, Object>();
			json.put("ctr", map.get(infoid));
			bulkRequest.add(client.prepareIndex(index, type, infoid).setSource(json));
		}

		BulkResponse bulkResponse = bulkRequest.get();

		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			System.out.println(bulkItemResponse.isFailed());

		}

		return bulkResponse;

	}

	/**
	 * @param index    :es index
	 * @param type     :es type
	 * @param infoid   :the es infoid
	 * @param infoid_2 :another es infoid
	 * @param field    : the field to compare,eg:title
	 * @return ture:Duplicated false: not Duplicated
	 * @throws IOException
	 */
	public boolean isDuplicated(String index, String type, String infoid, String infoid_2, String field) {

		List<String> list1;
		List<String> list2;
		try {
			list1 = listofTermVector(index, type, infoid, field);
			list2 = listofTermVector(index, type, infoid_2, field);
			return isDuplicated(list1, list2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否重复
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	private boolean isDuplicated(List<String> list1, List<String> list2) {
		List<String> intersection = intersection(list1, list2);
		List<String> union = union(list1, list2);

		double threshold = 0.45;
		double jaccard = intersection.size() * 1.0 / union.size();
		if (intersection.size() > 0 && jaccard >= threshold) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 进行两个list  交集
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	private <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();
		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}
		return list;
	}

	/***
	 * 并行两个list 交集
	 * @param list1
	 * @param list2
	 * @return
	 */
	private <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();
		set.addAll(list1);
		set.addAll(list2);
		return new ArrayList<T>(set);
	}

	/**
	 * 获取TermVector
	 *
	 * @param index
	 * @param type
	 * @param infoid
	 * @param field
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> listofTermVector(String index, String type, String infoid, String field) throws IOException {

		TermVectorsResponse response = client
				.prepareTermVectors(index, type, infoid).setPayloads(false)
				.setOffsets(false).setPositions(false)
				.setFieldStatistics(false).setTermStatistics(false)
				.setSelectedFields(field).execute().actionGet();

		TermsEnum iterator = response.getFields().terms(field).iterator();
		Set<T> set = new HashSet<T>();
		while ((iterator.next()) != null) {
			set.add((T) iterator.term().utf8ToString());
		}
		return new ArrayList<T>(set);
	}

	private static class SingletonHolder {
		static final ElasticSearchUtil INSTANCE = new ElasticSearchUtil();
	}


}
