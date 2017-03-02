package baseFramework.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

/**
 * @author chao.li
 * @date 2017年3月1日
 */
public class MsgFactory {
	private static MessageFactory mf = LogManager.getRootLogger().getMessageFactory();

	public static Message build(String message) {
		return mf.newMessage(message);
	}

	public static Message build(String message, Object... params) {
		return mf.newMessage(message, params);
	}
}
