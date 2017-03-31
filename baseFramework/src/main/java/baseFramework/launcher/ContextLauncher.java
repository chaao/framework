package baseFramework.launcher;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author chao.li
 * @date 2016年10月14日
 */
public abstract class ContextLauncher {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ApplicationContext context;

	protected void run() {
		run("classpath*:spring.xml");
	}

	protected void run(String configLocation) {
		Thread.currentThread().setName("ContextLauncher");
		init();
		logger.info("##### Server init sucess!");

		try {
			context = new ClassPathXmlApplicationContext(configLocation) {
				@Override
				protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
					super.customizeBeanFactory(beanFactory);
					beanFactory.setAllowBeanDefinitionOverriding(false);// 禁止覆盖重复bean
				}
			};
			logger.info("##### load Spring sucess!");
		} catch (Exception e) {
			logger.error("##### load spring error!", e);
			System.exit(0);
		}

		try {
			logger.info("#####  server is running now ......");
			Runtime.getRuntime().addShutdownHook(new Thread() {

				public void run() {
					try {
						logger.info("##### stop the server");
						try {
							release(context);
						} catch (Exception e) {
							logger.error("##### something goes wrong when release resource:\n{}",
									ExceptionUtils.getStackTrace(e));
						}
						((ClassPathXmlApplicationContext) context).close();
					} catch (Throwable e) {
						logger.error("##### something goes wrong when stopping Server:\n{}",
								ExceptionUtils.getStackTrace(e));
					} finally {
						logger.info("##### server is down.");
					}
				}

			});
		} catch (Throwable e) {
			logger.error("##### Something goes wrong when starting up the Server:\n{}",
					ExceptionUtils.getStackTrace(e));
			System.exit(0);
		}
	}

	protected void init() {

	}

	protected void release(ApplicationContext context) {

	}

	protected void join() throws InterruptedException {
		Thread.currentThread().join();
	}

}
