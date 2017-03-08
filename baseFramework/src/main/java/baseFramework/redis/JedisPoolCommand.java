package baseFramework.redis;

import baseFramework.exception.JedisExecutorException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author chao.li
 * @date 2017年3月8日
 */
public abstract class JedisPoolCommand<T> {
	private JedisPool jedisPool;
	private int maxAttempts;

	public JedisPoolCommand(JedisPool jedisPool, int maxAttempts) {
		this.jedisPool = jedisPool;
		this.maxAttempts = maxAttempts;
	}

	public abstract T execute(Jedis connection);

	public T run() {
		return runWithRetries(this.maxAttempts);
	}

	private T runWithRetries(int attempts) {
		if (attempts <= 0) {
			throw new JedisExecutorException("repeat too many times?");
		}

		Jedis connection = null;
		try {

			connection = jedisPool.getResource();

			return execute(connection);

		} catch (JedisException e) {
			throw e;
		} catch (Exception e) {
			// release current connection before recursion
			releaseConnection(connection);
			connection = null;

			if (attempts <= 1) {
				throw e;
			}

			return runWithRetries(attempts - 1);
		} finally {
			releaseConnection(connection);
		}

	}

	private void releaseConnection(Jedis connection) {
		if (connection != null) {
			connection.close();
		}
	}
}
