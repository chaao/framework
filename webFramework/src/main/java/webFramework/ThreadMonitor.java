package webFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chao.li
 * @date 2016年9月19日
 */
public class ThreadMonitor {
	private static Logger logger = LoggerFactory.getLogger("threadMonitor");
	private static ExecutorService executor;
	private static volatile boolean busy = false;

	public static boolean isBusy() {
		return busy;
	}

	private static void setBusy(boolean busy) {
		ThreadMonitor.busy = busy;
	}

	public static void init(Server jettyServer, long checkTime) throws Exception {

		if (checkTime > 0) {

			executor = Executors.newSingleThreadExecutor();
			executor.submit(new Runnable() {

				@Override
				public void run() {
					while (true) {

						try {
							Thread.sleep(checkTime);

							if (jettyServer == null || jettyServer.getThreadPool() == null)
								continue;
							final QueuedThreadPool pool = (QueuedThreadPool) jettyServer.getThreadPool();
							int threads = pool.getThreads();
							int idle = pool.getIdleThreads();
							int work = pool.getBusyThreads();

							int maxThreads = pool.getMaxThreads();

							int queueSize = pool.getQueueSize();

							setBusy(queueSize > (maxThreads * 2));

							logger.info("jetty status - created:{}, work:{}, idle:{}, queueSize:{}, maxThreads:{}.",
									threads, work, idle, queueSize, maxThreads);
						} catch (Exception e) {
							logger.error("", e);
						}
					}
				}
			});

		}
	}

}
