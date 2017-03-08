package baseFramework.redis;

import baseFramework.exception.JedisExecutorException;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * @author chao.li
 * @date 2017年3月8日
 */
public abstract class ShardedJedisCommand<T> {
	private ShardedJedisPool shardedJedisPool;
	private int maxAttempts;

	public ShardedJedisCommand(ShardedJedisPool shardedJedisPool, int maxAttempts) {
		this.shardedJedisPool = shardedJedisPool;
		this.maxAttempts = maxAttempts;
	}

	public abstract T execute(ShardedJedis connection);

	public T run() {
		return runWithRetries(this.maxAttempts);
	}

	private T runWithRetries(int attempts) {
		if (attempts <= 0) {
			throw new JedisExecutorException("repeat too many times?");
		}

		ShardedJedis connection = null;
		try {

			connection = shardedJedisPool.getResource();

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

	private void releaseConnection(ShardedJedis connection) {
		if (connection != null) {
			connection.close();
		}
	}
}
