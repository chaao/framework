package baseFramework.http;

import java.util.Map;

/**
 * @author chao.li
 * @date 2017年3月29日
 */
public class HttpResult {
	private int code;
	private Map<String, String> header;
	private String bodyStr;
	private byte[] bodyByte;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public String getBodyStr() {
		return bodyStr;
	}

	public void setBodyStr(String bodyStr) {
		this.bodyStr = bodyStr;
	}

	public byte[] getBodyByte() {
		return bodyByte;
	}

	public void setBodyByte(byte[] bodyByte) {
		this.bodyByte = bodyByte;
	}

}
