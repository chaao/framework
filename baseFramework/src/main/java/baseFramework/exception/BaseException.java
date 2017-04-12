package baseFramework.exception;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.message.Message;

import java.util.List;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private StringBuilder formatter;
	private List<Object> params;

	public BaseException() {

		super();
		this.formatter = new StringBuilder(getClass().getSimpleName()).append("|");
	}

	public BaseException(String message) {
		super(message);
		this.formatter = new StringBuilder(getClass().getSimpleName()).append("|").append(message);
	}

	/**
	 * no message (replaced by exception name)
	 *
	 * @param formatter log-msg-formatter
	 * @param params    log-msg-params
	 */
	public BaseException(String formatter, Object... params) {
		super();
		this.formatter = new StringBuilder(getClass().getSimpleName()).append("|").append(formatter);
		this.params = Lists.newArrayList(params);
	}

	public BaseException(Throwable cause, String message) {
		super(message, cause);
		this.formatter = new StringBuilder(getClass().getSimpleName()).append("|").append(message);
	}

	/**
	 * no message (replaced by exception name)
	 *
	 * @param formatter log-msg-formatter
	 * @param params    log-msg-params
	 */
	public BaseException(Throwable cause, String formatter, Object... params) {
		super(cause);
		this.formatter = new StringBuilder(getClass().getSimpleName()).append("|").append(formatter);
		this.params = Lists.newArrayList(params);
	}

	public void addMessage(String formatter, Object... params) {
		this.formatter.append(" ").append(formatter);

		if (this.params != null) {
			for (Object item : params) {
				this.params.add(item);
			}
		} else {
			this.params = Lists.newArrayList(params);
		}
	}

	public Message getMsg() {
		if (params != null) {
			return MsgFactory.build(this.formatter.toString(), params);
		} else {
			return MsgFactory.build(this.formatter.toString());
		}
	}

	@Override
	public String toString() {
		String name = getClass().getSimpleName();
		String message = getLocalizedMessage();
		return (message != null) ? (name + ":" + message) : name;
	}

}
