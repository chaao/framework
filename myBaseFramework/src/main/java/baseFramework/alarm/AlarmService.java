package baseFramework.alarm;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import baseFramework.utils.StringTools;

/**
 * 报警服务
 * 
 * @author chao.li
 * @date 2016年11月7日
 */
public class AlarmService implements InitializingBean, DisposableBean {
	private static Logger logger = LoggerFactory.getLogger("alarm");

	private AlarmControl alarmControl;
	private MailHandler mailHandler;
	private ExecutorService executor;
	private int period = 150;
	private BlockingQueue<AlarmMessage> queue = new LinkedBlockingQueue<AlarmMessage>(10000);

	@Override
	public void afterPropertiesSet() throws Exception {
		executor = Executors.newSingleThreadExecutor();
		executor.submit(new Runnable() {

			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					sendAlarmInternal();
					try {
						TimeUnit.MILLISECONDS.sleep(period);
					} catch (InterruptedException e) {
						logger.error("", e);
					}
				}
			}
		});
	}

	private void sendAlarmInternal() {
		AlarmMessage msg = null;
		try {
			msg = queue.take();

			List<String> receivers = alarmControl.control(msg);
			if (receivers != null) {
				mailHandler.doSend(msg, receivers);
				logger.info("send alarm [{}] to {}", msg.getTitle(), receivers);
			}

		} catch (Exception e) {
			logger.error(StringTools.format("send alarm error! [%s]", msg.toString()), e);
		}
	}

	public void sendAlarm(AlarmMessage data) {
		try {
			if (!queue.offer(data, 1, TimeUnit.SECONDS)) {
				logger.error("alarm sent to queue error : [{}]", data.toString());
			}
		} catch (Exception e) {
			logger.error(StringTools.format("send alarm to queue error! [%s]", data.toString()), e);
		}
	}

	@Override
	public void destroy() throws Exception {
		if (executor != null && !executor.isShutdown()) {
			executor.shutdown();
			executor.awaitTermination(2, TimeUnit.SECONDS);
		}
		if (!queue.isEmpty()) {
			int size = queue.size();
			logger.warn("there are {} alarms wait to be sent \n {}", size, dumpQueue());
		}
	}

	private String dumpQueue() {
		if (queue.isEmpty()) {
			return StringUtils.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (AlarmMessage data : queue) {
			sb.append(data.toString()).append("\n");
		}

		return sb.toString();
	}

	public void setAlarmControl(AlarmControl alarmControl) {
		this.alarmControl = alarmControl;
	}

	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}

}
