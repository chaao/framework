package baseFramework.exception;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class UnImplementedException extends BaseException {
	private static final long serialVersionUID = 1L;

	public UnImplementedException(String message) {
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
	public UnImplementedException(String formatter, Object... params) {
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
	public UnImplementedException(String message, String formatter, Object... params) {
		super(message, formatter, params);
	}

	public UnImplementedException(Throwable cause, String message) {
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
	public UnImplementedException(Throwable cause, String formatter, Object... params) {
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
	public UnImplementedException(Throwable cause, String message, String formatter, Object... params) {
		super(cause, message, formatter, params);
	}

}
