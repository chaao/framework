package baseFramework.exception;

import baseFramework.utils.StringTools;

/**
 * @author chao.li
 * @date 2016年11月30日
 */
public class ServerException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServerException(String message) {
		super(message);
	}

	public ServerException(String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages));
	}

	public ServerException(Throwable cause, String message) {
		super(message, cause);
	}

	public ServerException(Throwable cause, String messageFormat, Object... messages) {
		super(StringTools.format(messageFormat, messages), cause);
	}

}
