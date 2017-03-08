package baseFramework.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.JedisPool;

/**
 * 
 * @author chao.li
 * @date 2017年1月9日
 */
public class JedisPoolExecutorFactory implements FactoryBean<JedisPoolExecutor>, InitializingBean {
	private static Logger logger = LoggerFactory.getLogger(JedisPoolExecutorFactory.class);

	private JedisPoolExecutor jedisPoolExecutor;
	private String host;
	private Integer port;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;

	@Override
	public JedisPoolExecutor getObject() throws Exception {
		return jedisPoolExecutor;
	}

	@Override
	public Class<? extends JedisPoolExecutor> getObjectType() {
		return (this.jedisPoolExecutor != null ? this.jedisPoolExecutor.getClass() : JedisPoolExecutor.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		JedisPool jedisPool = new JedisPool(genericObjectPoolConfig, host, port, timeout);
		jedisPoolExecutor = new JedisPoolExecutor(jedisPool, maxRedirections);

		logger.info("load JedisPoolExecutor success! address: {}:{}", host, port);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(Integer port) {
		this.port = port;
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