package baseFramework.utils;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

/**
 * @author chao.li
 * @date 2017/11/3
 */
public class Base64Tools {

	public static String encode(String src) {
		if (StringUtils.isNotBlank(src))
			return Base64.getEncoder().encodeToString(src.getBytes(Charsets.UTF_8));
		else
			return null;
	}

	public static String decode(String src) {
		if (StringUtils.isNotBlank(src)) {
			byte[] decode = Base64.getDecoder().decode(src);
			return new String(decode, Charsets.UTF_8);
		} else {
			return null;
		}
	}

	public static String encodeUrl(String src) {
		if (StringUtils.isNotBlank(src))
			return Base64.getUrlEncoder().encodeToString(src.getBytes(Charsets.UTF_8));
		else
			return null;
	}

	public static String decodeUrl(String src) {
		if (StringUtils.isNotBlank(src)) {
			byte[] decode = Base64.getUrlDecoder().decode(src);
			return new String(decode, Charsets.UTF_8);
		} else {
			return null;
		}
	}

}
