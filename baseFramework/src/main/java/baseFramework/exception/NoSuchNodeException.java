package baseFramework.exception;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class NoSuchNodeException extends BaseException {
	private static final long serialVersionUID = 1L;

	public NoSuchNodeException(String message) {
		super(message);
	}

	/**
	 * no message (replaced by exception name)
	 * 
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public NoSuchNodeException(String formatter, Object... params) {
		super(formatter, params);
	}

	/**
	 * @param message
	 *            msg
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public NoSuchNodeException(String message, String formatter, Object... params) {
		super(message, formatter, params);
	}

	public NoSuchNodeException(Throwable cause, String message) {
		super(cause, message);
	}

	/**
	 * no message (replaced by exception name)
	 * 
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public NoSuchNodeException(Throwable cause, String formatter, Object... params) {
		super(cause, formatter, params);
	}

	/**
	 * @param message
	 *            msg
	 * @param formatter
	 *            log-msg-formatter
	 * @param params
	 *            log-msg-params
	 */
	public NoSuchNodeException(Throwable cause, String message, String formatter, Object... params) {
		super(cause, message, formatter, params);
	}

}
