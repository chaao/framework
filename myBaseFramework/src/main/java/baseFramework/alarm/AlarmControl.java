package baseFramework.alarm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年10月31日
 */
public class AlarmControl implements InitializingBean {

	// key1:value1,value2;key2:value3,value4
	private String receiverStr;
	private int duration;// 发送间隔时间

	private Map<String, List<String>> receiver = Maps.newHashMap();
	private Cache<CacheKey, AtomicLong> cache;

	@Override
	public void afterPropertiesSet() throws Exception {

		Preconditions.checkNotNull(this.receiverStr, "monitor.email.receiver is required!");
		Preconditions.checkArgument(this.duration > 0, "monitor.email.duration must be greater than 0!");

		for (String item : StringTools.trimAndSplit(receiverStr, ";")) {
			String[] pair = item.split(":");
			String key = pair[0];
			List<String> mails = Lists.newArrayList(pair[1].split(","));
			receiver.put(key, mails);
		}

		this.cache = CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.MINUTES).build();
	}

	public List<String> control(AlarmMessage msg) {

		if (msg == null || StringUtils.isBlank(msg.getReceiveKey())) {
			return null;
		}

		List<String> recivers = receiver.get(msg.getReceiveKey());

		if (recivers == null || recivers.size() == 0) {
			return null;
		}

		CacheKey key = new CacheKey(msg.getTitle(), msg.getReceiveKey());
		AtomicLong times = cache.getIfPresent(key);
		if (times == null) {// 第一次
			times = new AtomicLong(1);
			cache.put(key, times);
		} else {
			times.incrementAndGet();
		}

		if (times.get() >= msg.getThreshold()) {
			return recivers;
		} else {
			return null;
		}

	}

	class CacheKey {
		private String title;
		private String receiveKey;

		/**
		 * @param title
		 * @param receiveKey
		 */
		public CacheKey(String title, String receiveKey) {
			super();
			this.title = title;
			this.receiveKey = receiveKey;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getReceiveKey() {
			return receiveKey;
		}

		public void setReceiveKey(String receiveKey) {
			this.receiveKey = receiveKey;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((receiveKey == null) ? 0 : receiveKey.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (receiveKey == null) {
				if (other.receiveKey != null)
					return false;
			} else if (!receiveKey.equals(other.receiveKey))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			return true;
		}

		private AlarmControl getOuterType() {
			return AlarmControl.this;
		}

	}

}
