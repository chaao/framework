package baseFramework.exception;

import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages));
	}

	public BaseException(Throwable cause, String message) {
		super(message, cause);
	}

	public BaseException(Throwable cause, String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages), cause);
	}

}
