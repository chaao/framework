package webFramework;

import com.google.common.base.Preconditions;

/**
 * @author chao.li
 * @date 2016年9月19日
 */
public class JettyConfig {

	private String tempDir = "work";
	private boolean threadMonitor = false;// 是否开启jetty线程监控
	private long checkTime = 2000;// 监控间隔时间

	private String host;
	private int port = 8080;
	private int maxThreads = 256;
	private int minThreads = 64;

	public void check() {
		Preconditions.checkArgument(port > 0, "port must be greater than 0 !");
		Preconditions.checkArgument(maxThreads > 0, "maxThreads must be greater than 0 !");
		Preconditions.checkArgument(minThreads > 0, "minThreads must be greater than 0 !");
		Preconditions.checkNotNull(this.tempDir, "temp dir is required!");
		if (threadMonitor)
			Preconditions.checkArgument(checkTime > 0, "checkTime must be greater than 0 !");
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public boolean isThreadMonitor() {
		return threadMonitor;
	}

	public void openThreadMonitor() {
		this.threadMonitor = true;
	}

	public long getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(long checkTime) {
		this.checkTime = checkTime;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getMinThreads() {
		return minThreads;
	}

	public void setMinThreads(int minThreads) {
		this.minThreads = minThreads;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
