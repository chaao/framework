package baseFramework.qconf;

import baseFramework.exception.NoSuchNodeException;
import baseFramework.exception.TypeMismatchException;
import net.qihoo.qconf.Qconf;

/**
 * @author chao.li
 * @date 2016年12月5日
 */
public class QconfClient {

	public static String getString(String key) {
		return getConf(key);
	}

	public static int getInt(String key) {
		String value = getConf(key);
		try {
			return Integer.valueOf(value);
		} catch (Exception e) {
			throw new TypeMismatchException(key, e);
		}
	}

	public static long getLong(String key) {
		String value = getConf(key);
		try {
			return Long.valueOf(value);
		} catch (Exception e) {
			throw new TypeMismatchException(key, e);
		}
	}

	public static double getDouble(String key) {
		String value = getConf(key);
		try {
			return Double.valueOf(value);
		} catch (Exception e) {
			throw new TypeMismatchException(key, e);
		}
	}

	public static boolean getBoolean(String key) {
		String value = getConf(key);
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			throw new TypeMismatchException(key, e);
		}
	}

	private static String getConf(String key) {
		try {
			return Qconf.getConf(key);
		} catch (Exception e) {
			throw new NoSuchNodeException(key, e);
		}
	}

}
