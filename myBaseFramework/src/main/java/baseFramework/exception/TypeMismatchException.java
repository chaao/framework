package baseFramework.exception;

import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class TypeMismatchException extends BaseException {
	private static final long serialVersionUID = 1L;

	public TypeMismatchException(String message) {
		super(message);
	}

	public TypeMismatchException(String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages));
	}

	public TypeMismatchException(Throwable cause, String message) {
		super(message, cause);
	}

	public TypeMismatchException(Throwable cause, String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages), cause);
	}
}
