
package webFramework;

import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chao.li
 * @date 2016年9月14日
 */
public abstract class AbstractServer {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Server server;
	private JettyConfig config;

	protected abstract void config(JettyConfig config) throws Exception;

	protected void run() {
		try {

			config = new JettyConfig();
			config(config);
			config.check();

			logger.info("## start the jetty server.");
			start();

			if (config.isThreadMonitor()) {
				ThreadMonitor.init(server, config.getCheckTime());
				logger.info("## Jetty thread open thread monitor");
			}

			logger.info("## the jetty server is running now ......");
			Runtime.getRuntime().addShutdownHook(new Thread() {

				public void run() {
					try {
						logger.info("## stop the jetty server");
						AbstractServer.this.stop();
					} catch (Throwable e) {
						logger.warn("## something goes wrong when stopping jetty Server:\n{}",
								ExceptionUtils.getStackTrace(e));
					} finally {
						logger.info("## jetty server is down.");
					}
				}

			});
			join();
		} catch (Throwable e) {
			logger.error("## Something goes wrong when starting up the jetty Server:\n{}",
					ExceptionUtils.getStackTrace(e));
			System.exit(0);
		}
	}

	public void start() throws Exception {
		server = new Server(createThreadPool());

		server.addConnector(createConnector());
		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);
		server.setDumpBeforeStop(false);
		server.setDumpAfterStart(false);

		server.start();
		logger.info("## Jetty Server is startup!");
	}

	public void join() throws Exception {
		server.join();
		logger.info("##Jetty Server joined!");
	}

	public void stop() throws Exception {
		server.stop();
		logger.info("##Jetty Server is stop!");
	}

	private ThreadPool createThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(config.getMinThreads());
		threadPool.setMaxThreads(config.getMaxThreads());

		logger.info("## Jetty server set thread pool: minThread:{}, maxThread:{}", threadPool.getMinThreads(),
				threadPool.getMaxThreads());
		return threadPool;
	}

	private NetworkConnector createConnector() {
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(config.getPort());
		connector.setHost(config.getHost());
		return connector;
	}

	private HandlerCollection createHandlers() {
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setResourceBase("/");
		webAppContext.setDefaultsDescriptor("/web.xml");
		File tempDir = new File(config.getTempDir());
		if (!tempDir.exists()) {
			tempDir.mkdir();
		}
		webAppContext.setTempDirectory(tempDir);
		logger.info("## Jetty server set temp dir:{}", webAppContext.getTempDirectory());

		HandlerCollection handlerCollection = new HandlerCollection();
		handlerCollection.setHandlers(new Handler[] { webAppContext });
		return handlerCollection;
	}

}
