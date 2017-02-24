package baseFramework.exception;

import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class NoSuchNodeException extends BaseException {
	private static final long serialVersionUID = 1L;

	public NoSuchNodeException(String message) {
		super(message);
	}

	public NoSuchNodeException(String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages));
	}

	public NoSuchNodeException(Throwable cause, String message) {
		super(message, cause);
	}

	public NoSuchNodeException(Throwable cause, String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages), cause);
	}

}
