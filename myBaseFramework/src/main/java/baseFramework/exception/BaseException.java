package baseFramework.exception;

import org.apache.logging.log4j.message.Message;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String formatter;
	private Object[] params;

	public BaseException(String message) {
		super(message);
		this.formatter = message;
	}

	/**
	 * no message (replaced by exception name)
	 * 
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public BaseException(String formatter, Object... params) {
		super();
		this.formatter = formatter;
		this.params = params;
	}

	/**
	 * @param message
	 *            msg
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public BaseException(String message, String formatter, Object... params) {
		super(message);
		this.formatter = formatter;
		this.params = params;
	}

	public BaseException(Throwable cause, String message) {
		super(message, cause);
		this.formatter = message;
	}

	/**
	 * no message (replaced by exception name)
	 * 
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public BaseException(Throwable cause, String formatter, Object... params) {
		super(cause);
		this.formatter = formatter;
		this.params = params;
	}

	/**
	 * @param message
	 *            msg
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public BaseException(Throwable cause, String message, String formatter, Object... params) {
		super(message, cause);
		this.formatter = formatter;
		this.params = params;
	}

	public Message getMsg() {
		if (params != null) {
			return MsgFactory.build(formatter, params);
		} else {
			return MsgFactory.build(formatter);
		}
	}

	@Override
	public String toString() {
		String name = getClass().getSimpleName();
		String message = getLocalizedMessage();
		return (message != null) ? (name + ":" + message) : name;
	}

}
