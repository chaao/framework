package baseFramework.alarm;

import java.io.Serializable;

/**
 * title,receiveKey一样则视为相同消息
 * 
 * @author chao.li
 * @date 2016年10月12日
 * 
 */
public class AlarmMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String title;
	private String message;
	private String receiveKey;
	private long threshold = 0;// 阀值

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiveKey() {
		return receiveKey;
	}

	public void setReceiveKey(String receiveKey) {
		this.receiveKey = receiveKey;
	}

	public long getThreshold() {
		return threshold;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "AlarmMessage [title=" + title + ", message=" + message + ", receiveKey=" + receiveKey + ", threshold="
				+ threshold + "]";
	}

}
