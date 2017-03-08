package baseFramework.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import baseFramework.exception.UnImplementedException;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

/**
 * @author chao.li
 * @date 2017年3月8日
 */
public class JedisPoolExecutor implements JedisCommands, BinaryJedisCommands {

	private JedisPool connectionHandler;
	private int maxAttempts;

	/**
	 * @param connectionHandler
	 * @param maxAttempts
	 */
	public JedisPoolExecutor(JedisPool connectionHandler, int maxAttempts) {
		super();
		this.connectionHandler = connectionHandler;
		this.maxAttempts = maxAttempts;
	}

	@Override
	public String set(final String key, final String value) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.set(key, value);
			}
		}.run();
	}

	@Override
	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.set(key, value, nxxx, expx, time);
			}
		}.run();
	}

	@Override
	public String get(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.get(key);
			}
		}.run();
	}

	@Override
	public Boolean exists(final String key) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.exists(key);
			}
		}.run();
	}

	@Override
	public Long persist(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.persist(key);
			}
		}.run();
	}

	@Override
	public String type(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.type(key);
			}
		}.run();
	}

	@Override
	public Long expire(final String key, final int seconds) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.expire(key, seconds);
			}
		}.run();
	}

	@Override
	public Long pexpire(final String key, final long milliseconds) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.pexpire(key, milliseconds);
			}
		}.run();
	}

	@Override
	public Long expireAt(final String key, final long unixTime) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.expireAt(key, unixTime);
			}
		}.run();
	}

	@Override
	public Long pexpireAt(final String key, final long millisecondsTimestamp) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.pexpireAt(key, millisecondsTimestamp);
			}
		}.run();
	}

	@Override
	public Long ttl(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.ttl(key);
			}
		}.run();
	}

	@Override
	public Long pttl(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.pttl(key);
			}
		}.run();
	}

	@Override
	public Boolean setbit(final String key, final long offset, final boolean value) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.setbit(key, offset, value);
			}
		}.run();
	}

	@Override
	public Boolean setbit(final String key, final long offset, final String value) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.setbit(key, offset, value);
			}
		}.run();
	}

	@Override
	public Boolean getbit(final String key, final long offset) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.getbit(key, offset);
			}
		}.run();
	}

	@Override
	public Long setrange(final String key, final long offset, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.setrange(key, offset, value);
			}
		}.run();
	}

	@Override
	public String getrange(final String key, final long startOffset, final long endOffset) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.getrange(key, startOffset, endOffset);
			}
		}.run();
	}

	@Override
	public String getSet(final String key, final String value) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.getSet(key, value);
			}
		}.run();
	}

	@Override
	public Long setnx(final String key, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.setnx(key, value);
			}
		}.run();
	}

	@Override
	public String setex(final String key, final int seconds, final String value) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.setex(key, seconds, value);
			}
		}.run();
	}

	@Override
	public String psetex(final String key, final long milliseconds, final String value) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.psetex(key, milliseconds, value);
			}
		}.run();
	}

	@Override
	public Long decrBy(final String key, final long integer) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.decrBy(key, integer);
			}
		}.run();
	}

	@Override
	public Long decr(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.decr(key);
			}
		}.run();
	}

	@Override
	public Long incrBy(final String key, final long integer) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.incrBy(key, integer);
			}
		}.run();
	}

	@Override
	public Double incrByFloat(final String key, final double value) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.incrByFloat(key, value);
			}
		}.run();
	}

	@Override
	public Long incr(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.incr(key);
			}
		}.run();
	}

	@Override
	public Long append(final String key, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.append(key, value);
			}
		}.run();
	}

	@Override
	public String substr(final String key, final int start, final int end) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.substr(key, start, end);
			}
		}.run();
	}

	@Override
	public Long hset(final String key, final String field, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.hset(key, field, value);
			}
		}.run();
	}

	@Override
	public String hget(final String key, final String field) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.hget(key, field);
			}
		}.run();
	}

	@Override
	public Long hsetnx(final String key, final String field, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.hsetnx(key, field, value);
			}
		}.run();
	}

	@Override
	public String hmset(final String key, final Map<String, String> hash) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.hmset(key, hash);
			}
		}.run();
	}

	@Override
	public List<String> hmget(final String key, final String... fields) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.hmget(key, fields);
			}
		}.run();
	}

	@Override
	public Long hincrBy(final String key, final String field, final long value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.hincrBy(key, field, value);
			}
		}.run();
	}

	@Override
	public Double hincrByFloat(final String key, final String field, final double value) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.hincrByFloat(key, field, value);
			}
		}.run();
	}

	@Override
	public Boolean hexists(final String key, final String field) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.hexists(key, field);
			}
		}.run();
	}

	@Override
	public Long hdel(final String key, final String... field) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.hdel(key, field);
			}
		}.run();
	}

	@Override
	public Long hlen(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.hlen(key);
			}
		}.run();
	}

	@Override
	public Set<String> hkeys(final String key) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.hkeys(key);
			}
		}.run();
	}

	@Override
	public List<String> hvals(final String key) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.hvals(key);
			}
		}.run();
	}

	@Override
	public Map<String, String> hgetAll(final String key) {
		return new JedisPoolCommand<Map<String, String>>(connectionHandler, maxAttempts) {
			@Override
			public Map<String, String> execute(Jedis connection) {
				return connection.hgetAll(key);
			}
		}.run();
	}

	@Override
	public Long rpush(final String key, final String... string) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.rpush(key, string);
			}
		}.run();
	}

	@Override
	public Long lpush(final String key, final String... string) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.lpush(key, string);
			}
		}.run();
	}

	@Override
	public Long llen(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.llen(key);
			}
		}.run();
	}

	@Override
	public List<String> lrange(final String key, final long start, final long end) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.lrange(key, start, end);
			}
		}.run();
	}

	@Override
	public String ltrim(final String key, final long start, final long end) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.ltrim(key, start, end);
			}
		}.run();
	}

	@Override
	public String lindex(final String key, final long index) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.lindex(key, index);
			}
		}.run();
	}

	@Override
	public String lset(final String key, final long index, final String value) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.lset(key, index, value);
			}
		}.run();
	}

	@Override
	public Long lrem(final String key, final long count, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.lrem(key, count, value);
			}
		}.run();
	}

	@Override
	public String lpop(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.lpop(key);
			}
		}.run();
	}

	@Override
	public String rpop(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.rpop(key);
			}
		}.run();
	}

	@Override
	public Long sadd(final String key, final String... member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.sadd(key, member);
			}
		}.run();
	}

	@Override
	public Set<String> smembers(final String key) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.smembers(key);
			}
		}.run();
	}

	@Override
	public Long srem(final String key, final String... member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.srem(key, member);
			}
		}.run();
	}

	@Override
	public String spop(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.spop(key);
			}
		}.run();
	}

	@Override
	public Set<String> spop(final String key, final long count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.spop(key, count);
			}
		}.run();
	}

	@Override
	public Long scard(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.scard(key);
			}
		}.run();
	}

	@Override
	public Boolean sismember(final String key, final String member) {
		return new JedisPoolCommand<Boolean>(connectionHandler, maxAttempts) {
			@Override
			public Boolean execute(Jedis connection) {
				return connection.sismember(key, member);
			}
		}.run();
	}

	@Override
	public String srandmember(final String key) {
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.srandmember(key);
			}
		}.run();
	}

	@Override
	public List<String> srandmember(final String key, final int count) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.srandmember(key, count);
			}
		}.run();
	}

	@Override
	public Long strlen(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.strlen(key);
			}
		}.run();
	}

	@Override
	public Long zadd(final String key, final double score, final String member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zadd(key, score, member);
			}
		}.run();
	}

	@Override
	public Long zadd(final String key, final double score, final String member, final ZAddParams params) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zadd(key, score, member, params);
			}
		}.run();
	}

	@Override
	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zadd(key, scoreMembers);
			}
		}.run();
	}

	@Override
	public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zadd(key, scoreMembers, params);
			}
		}.run();
	}

	@Override
	public Set<String> zrange(final String key, final long start, final long end) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrange(key, start, end);
			}
		}.run();
	}

	@Override
	public Long zrem(final String key, final String... member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zrem(key, member);
			}
		}.run();
	}

	@Override
	public Double zincrby(final String key, final double score, final String member) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.zincrby(key, score, member);
			}
		}.run();
	}

	@Override
	public Double zincrby(final String key, final double score, final String member, final ZIncrByParams params) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.zincrby(key, score, member, params);
			}
		}.run();
	}

	@Override
	public Long zrank(final String key, final String member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zrank(key, member);
			}
		}.run();
	}

	@Override
	public Long zrevrank(final String key, final String member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zrevrank(key, member);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrange(final String key, final long start, final long end) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrange(key, start, end);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrangeWithScores(key, start, end);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrevrangeWithScores(key, start, end);
			}
		}.run();
	}

	@Override
	public Long zcard(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zcard(key);
			}
		}.run();
	}

	@Override
	public Double zscore(final String key, final String member) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.zscore(key, member);
			}
		}.run();
	}

	@Override
	public List<String> sort(final String key) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.sort(key);
			}
		}.run();
	}

	@Override
	public List<String> sort(final String key, final SortingParams sortingParameters) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.sort(key, sortingParameters);
			}
		}.run();
	}

	@Override
	public Long zcount(final String key, final double min, final double max) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zcount(key, min, max);
			}
		}.run();
	}

	@Override
	public Long zcount(final String key, final String min, final String max) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zcount(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByScore(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByScore(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByScore(key, max, min);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByScore(key, min, max, offset, count);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByScore(key, max, min);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByScore(key, min, max, offset, count);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByScore(key, max, min, offset, count);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrangeByScoreWithScores(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrevrangeByScoreWithScores(key, max, min);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByScore(key, max, min, offset, count);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrangeByScoreWithScores(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrevrangeByScoreWithScores(key, max, min);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrangeByScoreWithScores(key, min, max, offset, count);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		}.run();
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public Set<Tuple> execute(Jedis connection) {
				return connection.zrevrangeByScoreWithScores(key, max, min, offset, count);
			}
		}.run();
	}

	@Override
	public Long zremrangeByRank(final String key, final long start, final long end) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zremrangeByRank(key, start, end);
			}
		}.run();
	}

	@Override
	public Long zremrangeByScore(final String key, final double start, final double end) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zremrangeByScore(key, start, end);
			}
		}.run();
	}

	@Override
	public Long zremrangeByScore(final String key, final String start, final String end) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zremrangeByScore(key, start, end);
			}
		}.run();
	}

	@Override
	public Long zlexcount(final String key, final String min, final String max) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zlexcount(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByLex(key, min, max);
			}
		}.run();
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrangeByLex(key, min, max, offset, count);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByLex(key, max, min);
			}
		}.run();
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min, final int offset,
			final int count) {
		return new JedisPoolCommand<Set<String>>(connectionHandler, maxAttempts) {
			@Override
			public Set<String> execute(Jedis connection) {
				return connection.zrevrangeByLex(key, max, min, offset, count);
			}
		}.run();
	}

	@Override
	public Long zremrangeByLex(final String key, final String min, final String max) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.zremrangeByLex(key, min, max);
			}
		}.run();
	}

	@Override
	public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.linsert(key, where, pivot, value);
			}
		}.run();
	}

	@Override
	public Long lpushx(final String key, final String... string) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.lpushx(key, string);
			}
		}.run();
	}

	@Override
	public Long rpushx(final String key, final String... string) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.rpushx(key, string);
			}
		}.run();
	}

	@Override
	public Long del(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.del(key);
			}
		}.run();
	}

	@Override
	public String echo(final String string) {
		// note that it'll be run from arbitary node
		return new JedisPoolCommand<String>(connectionHandler, maxAttempts) {
			@Override
			public String execute(Jedis connection) {
				return connection.echo(string);
			}
		}.run();
	}

	@Override
	public Long bitcount(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.bitcount(key);
			}
		}.run();
	}

	@Override
	public Long bitcount(final String key, final long start, final long end) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.bitcount(key, start, end);
			}
		}.run();
	}

	@Override
	public Long bitpos(final String key, final boolean value) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.bitpos(key, value);
			}
		}.run();
	}

	@Override
	public Long bitpos(final String key, final boolean value, final BitPosParams params) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.bitpos(key, value, params);
			}
		}.run();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor) {
		return new JedisPoolCommand<ScanResult<Entry<String, String>>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<Entry<String, String>> execute(Jedis connection) {
				return connection.hscan(key, cursor);
			}
		}.run();
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(final String key, final String cursor, final ScanParams params) {
		return new JedisPoolCommand<ScanResult<Entry<String, String>>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<Entry<String, String>> execute(Jedis connection) {
				return connection.hscan(key, cursor, params);
			}
		}.run();
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		return new JedisPoolCommand<ScanResult<String>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<String> execute(Jedis connection) {
				return connection.sscan(key, cursor);
			}
		}.run();
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor, final ScanParams params) {
		return new JedisPoolCommand<ScanResult<String>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<String> execute(Jedis connection) {
				return connection.sscan(key, cursor, params);
			}
		}.run();
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return new JedisPoolCommand<ScanResult<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<Tuple> execute(Jedis connection) {
				return connection.zscan(key, cursor);
			}
		}.run();
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params) {
		return new JedisPoolCommand<ScanResult<Tuple>>(connectionHandler, maxAttempts) {
			@Override
			public ScanResult<Tuple> execute(Jedis connection) {
				return connection.zscan(key, cursor, params);
			}
		}.run();
	}

	@Override
	public Long pfadd(final String key, final String... elements) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.pfadd(key, elements);
			}
		}.run();
	}

	@Override
	public long pfcount(final String key) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.pfcount(key);
			}
		}.run();
	}

	@Override
	public List<String> blpop(final int timeout, final String key) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.blpop(timeout, key);
			}
		}.run();
	}

	@Override
	public List<String> brpop(final int timeout, final String key) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.brpop(timeout, key);
			}
		}.run();
	}

	@Override
	public Long geoadd(final String key, final double longitude, final double latitude, final String member) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.geoadd(key, longitude, latitude, member);
			}
		}.run();
	}

	@Override
	public Long geoadd(final String key, final Map<String, GeoCoordinate> memberCoordinateMap) {
		return new JedisPoolCommand<Long>(connectionHandler, maxAttempts) {
			@Override
			public Long execute(Jedis connection) {
				return connection.geoadd(key, memberCoordinateMap);
			}
		}.run();
	}

	@Override
	public Double geodist(final String key, final String member1, final String member2) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.geodist(key, member1, member2);
			}
		}.run();
	}

	@Override
	public Double geodist(final String key, final String member1, final String member2, final GeoUnit unit) {
		return new JedisPoolCommand<Double>(connectionHandler, maxAttempts) {
			@Override
			public Double execute(Jedis connection) {
				return connection.geodist(key, member1, member2, unit);
			}
		}.run();
	}

	@Override
	public List<String> geohash(final String key, final String... members) {
		return new JedisPoolCommand<List<String>>(connectionHandler, maxAttempts) {
			@Override
			public List<String> execute(Jedis connection) {
				return connection.geohash(key, members);
			}
		}.run();
	}

	@Override
	public List<GeoCoordinate> geopos(final String key, final String... members) {
		return new JedisPoolCommand<List<GeoCoordinate>>(connectionHandler, maxAttempts) {
			@Override
			public List<GeoCoordinate> execute(Jedis connection) {
				return connection.geopos(key, members);
			}
		}.run();
	}

	@Override
	public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude,
			final double radius, final GeoUnit unit) {
		return new JedisPoolCommand<List<GeoRadiusResponse>>(connectionHandler, maxAttempts) {
			@Override
			public List<GeoRadiusResponse> execute(Jedis connection) {
				return connection.georadius(key, longitude, latitude, radius, unit);
			}
		}.run();
	}

	@Override
	public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude,
			final double radius, final GeoUnit unit, final GeoRadiusParam param) {
		return new JedisPoolCommand<List<GeoRadiusResponse>>(connectionHandler, maxAttempts) {
			@Override
			public List<GeoRadiusResponse> execute(Jedis connection) {
				return connection.georadius(key, longitude, latitude, radius, unit, param);
			}
		}.run();
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius,
			final GeoUnit unit) {
		return new JedisPoolCommand<List<GeoRadiusResponse>>(connectionHandler, maxAttempts) {
			@Override
			public List<GeoRadiusResponse> execute(Jedis connection) {
				return connection.georadiusByMember(key, member, radius, unit);
			}
		}.run();
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius,
			final GeoUnit unit, final GeoRadiusParam param) {
		return new JedisPoolCommand<List<GeoRadiusResponse>>(connectionHandler, maxAttempts) {
			@Override
			public List<GeoRadiusResponse> execute(Jedis connection) {
				return connection.georadiusByMember(key, member, radius, unit, param);
			}
		}.run();
	}

	@Override
	public List<Long> bitfield(final String key, final String... arguments) {
		return new JedisPoolCommand<List<Long>>(connectionHandler, maxAttempts) {
			@Override
			public List<Long> execute(Jedis connection) {
				return connection.bitfield(key, arguments);
			}
		}.run();
	}

	@Override
	public String set(String key, String value, String nxxx) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<String> blpop(String arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<String> brpop(String arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long move(String key, int dbIndex) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<String> sscan(String key, int cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Tuple> zscan(String key, int cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String set(byte[] key, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] get(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean exists(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long persist(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String type(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long expire(byte[] key, int seconds) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long pexpire(byte[] key, long milliseconds) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long expireAt(byte[] key, long unixTime) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long ttl(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean setbit(byte[] key, long offset, boolean value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean setbit(byte[] key, long offset, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean getbit(byte[] key, long offset) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long setrange(byte[] key, long offset, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] getSet(byte[] key, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long setnx(byte[] key, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String setex(byte[] key, int seconds, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long decrBy(byte[] key, long integer) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long decr(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long incrBy(byte[] key, long integer) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double incrByFloat(byte[] key, double value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long incr(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long append(byte[] key, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] substr(byte[] key, int start, int end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long hset(byte[] key, byte[] field, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long hincrBy(byte[] key, byte[] field, long value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double hincrByFloat(byte[] key, byte[] field, double value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean hexists(byte[] key, byte[] field) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long hdel(byte[] key, byte[]... field) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long hlen(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> hkeys(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Collection<byte[]> hvals(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long rpush(byte[] key, byte[]... args) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long lpush(byte[] key, byte[]... args) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long llen(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> lrange(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String ltrim(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] lindex(byte[] key, long index) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public String lset(byte[] key, long index, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long lrem(byte[] key, long count, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] lpop(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] rpop(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long sadd(byte[] key, byte[]... member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> smembers(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long srem(byte[] key, byte[]... member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] spop(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> spop(byte[] key, long count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long scard(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Boolean sismember(byte[] key, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] srandmember(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> srandmember(byte[] key, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long strlen(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrange(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zrem(byte[] key, byte[]... member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zrank(byte[] key, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zrevrank(byte[] key, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrange(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zcard(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double zscore(byte[] key, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> sort(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zcount(byte[] key, double min, double max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zcount(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zremrangeByRank(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zremrangeByScore(byte[] key, double start, double end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zlexcount(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long lpushx(byte[] key, byte[]... arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long rpushx(byte[] key, byte[]... arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> blpop(byte[] arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> brpop(byte[] arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long del(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public byte[] echo(byte[] arg) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long move(byte[] key, int dbIndex) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long bitcount(byte[] key) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long bitcount(byte[] key, long start, long end) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long pfadd(byte[] key, byte[]... elements) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public long pfcount(byte[] key) {
		return 0;
	}

	@Override
	public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> geohash(byte[] key, byte[]... members) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius,
			GeoUnit unit) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit,
			GeoRadiusParam param) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
		throw new UnImplementedException("unimplemented method");
	}

	@Override
	public List<byte[]> bitfield(byte[] key, byte[]... arguments) {
		throw new UnImplementedException("unimplemented method");
	}

}
