package baseFramework.redis;

import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import com.google.common.collect.Lists;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author chao.li
 * @date 2017年1月9日
 */
public class ShardedJedisExecutorFactory implements FactoryBean<ShardedJedisExecutor>, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(ShardedJedisExecutorFactory.class);
	private Resource addressConfig;
	private String addressKeyPrefix;

	private ShardedJedisExecutor shardedJedisExecutor;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;

	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	@Override
	public ShardedJedisExecutor getObject() throws Exception {
		return shardedJedisExecutor;
	}

	@Override
	public Class<? extends ShardedJedisExecutor> getObjectType() {
		return (this.shardedJedisExecutor != null ? this.shardedJedisExecutor.getClass() : ShardedJedisExecutor.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private List<JedisShardInfo> parseHostAndPort() throws Exception {
		try {
			Properties prop = new Properties();
			prop.load(this.addressConfig.getInputStream());

			List<JedisShardInfo> shards = Lists.newArrayList();
			for (Object key : prop.keySet()) {

				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}

				String val = (String) prop.get(key);

				boolean isIpPort = p.matcher(val).matches();

				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = val.split(":");

				JedisShardInfo shard = new JedisShardInfo(ipAndPort[0], Integer.parseInt(ipAndPort[1]), timeout);
				shards.add(shard);
			}

			return shards;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		List<JedisShardInfo> shards = this.parseHostAndPort();

		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(genericObjectPoolConfig, shards);
		shardedJedisExecutor = new ShardedJedisExecutor(shardedJedisPool, maxRedirections);

		logger.info("load SharedeJedisExecutor success! redis address:{}", shards);
	}

	public void setAddressConfig(Resource addressConfig) {
		this.addressConfig = addressConfig;
	}

	public void setAddressKeyPrefix(String addressKeyPrefix) {
		this.addressKeyPrefix = addressKeyPrefix;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(Integer maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

}