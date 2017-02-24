package baseFramework.easy.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * EasyCommons 项目下的 Properties 文件操作工具类<br>
 * EasyProperties：替代java.util.Properties的properties文件类，
 * 扩展了java.util.Properties，提供了内容合并（merge: 增, 删, 改）和修改（modify:
 * 增,改）功能，修改属性时不影响原格式。<br>
 * 适合场景：任何需要对properties文件进行读写操作的场景，不影响文件原格式。<br>
 * 
 * @author easyproject.cn
 * @since 1.0.0
 * 
 */
public class EasyProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static synchronized EasyProperties create(String file) throws IOException {
		EasyProperties prop = new EasyProperties();// 属性集合对象
		InputStream in = EasyProperties.class.getClassLoader().getResourceAsStream(file);
		prop.load(in);// 将属性文件流装载到Properties对象中
		return prop;
	}
	
	private static File createFile(String path) {
		URL url = EasyPropertiesUtils.class.getClassLoader().getResource(path);
		return new File(url.getPath());
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFile
	 *            合并到的properties文件
	 * @return 合并结果
	 */
	public boolean mergeToFile(File propertiesFile) {
		return this.mergeToFile(propertiesFile, null);
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFilePath
	 *            合并到的properties文件路径
	 * @return 合并结果
	 */
	public boolean mergeToFile(String propertiesFilePath) {
		return this.mergeToFile(createFile(propertiesFilePath));
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFile
	 *            修改到的properties文件
	 * @return 合并结果
	 */
	public boolean modifyToFile(File propertiesFile) {
		return this.modifyToFile(propertiesFile, null);
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFilePath
	 *            修改到的properties文件路径
	 * @return 合并结果
	 */
	public boolean modifyToFile(String propertiesFilePath) {
		return modifyToFile(createFile(propertiesFilePath));
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFile
	 *            合并到的properties文件
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 */
	public boolean mergeToFile(File propertiesFile, String charset) {
		boolean flag = false;

		StringBuilder sb = new StringBuilder();
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) this.clone();
		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			if (charset != null) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesFile), charset));
			} else {
				FileReader fr = new FileReader(propertiesFile);
				br = new BufferedReader(fr);
			}

			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.trim().startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					String loadKey = loadConvert(key);
					if (tempProperties.containsKey(loadKey)) { // 如果存在要修改的属性，则修改

						// s = saveConvert(key, true, true)
						// + "="
						// +
						// saveConvert(tempProperties.getProperty(key), false,
						// true)
						// ;
						s = key + "=" + saveConvert(tempProperties.getProperty(loadKey), false, true);

						tempProperties.remove(loadKey);
					} else {
						s = null;
					}
				}
				if (s != null) {
					sb.append(s).append(System.getProperty("line.separator"));
				}
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 如果有异常，则不再继续处理
		if (!flag) {
			return flag;
		}

		try {
			flag = false;
			if (charset != null) {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(propertiesFile), charset));
			} else {
				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
			}

			bw.write(sb.toString());
			if (!tempProperties.isEmpty()) {

				for (Entry<Object, Object> entry : tempProperties.entrySet()) {

					// 以#开头的键值，作为注释，不转意
					if (entry.getValue().toString().trim().equals("#comments")) {
						String res = "#" + saveConvertChar(entry.getKey().toString());
						bw.write(res);
					} else if (entry.getValue().toString().trim().equals("#newLine")) {
						bw.newLine();
					} else {
						bw.write(saveConvert(entry.getKey().toString(), true, true) + "="
								+ saveConvert(entry.getValue().toString(), false, true));
					}

					bw.newLine();
				}
			}

			flag = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 将Properties对象合并到指定文件（增，删，改）
	 * 
	 * @param propertiesFilePath
	 *            合并到的properties文件路径
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 * @throws IOException
	 *             操作异常
	 */
	public boolean mergeToFile(String propertiesFilePath, String charset) throws IOException {
		return this.mergeToFile(createFile(propertiesFilePath), charset);
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFile
	 *            修改到的properties文件
	 * @param charset
	 *            文件字符集
	 * @return 合并结果
	 */
	public boolean modifyToFile(File propertiesFile, String charset) {
		boolean flag = false;

		BufferedReader br = null;

		StringBuilder sb = new StringBuilder();
		// 克隆对象，临时使用。通过移除方法判定是否有新添加属性，防止破坏原始对象
		Properties tempProperties = (Properties) this.clone();

		try {
			if (charset != null) {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesFile), charset));
			} else {
				FileReader fr = new FileReader(propertiesFile);
				br = new BufferedReader(fr);
			}

			String s = null;
			while ((s = br.readLine()) != null) {
				int equalMark = s.indexOf("=");
				if ((!s.trim().startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					String loadKey = loadConvert(key);
					if (tempProperties.containsKey(loadKey)) { // 如果存在要修改的属性，则修改
						s = key + "=" + saveConvert(tempProperties.getProperty(loadKey), false, true);
						tempProperties.remove(loadKey);
					}
				}

				sb.append(s).append(System.getProperty("line.separator"));
			}

			flag = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 如果有异常，则不再继续处理
		if (!flag) {
			return flag;
		}

		// 写出
		BufferedWriter bw = null;
		try {
			flag = false;
			if (charset != null) {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(propertiesFile), charset));
			} else {
				FileWriter fw = new FileWriter(propertiesFile);
				bw = new BufferedWriter(fw);
			}

			bw.write(sb.toString());
			if (!tempProperties.isEmpty()) {

				for (Entry<Object, Object> entry : tempProperties.entrySet()) {

					if (entry.getValue().toString().trim().equals("#comments")) {
						String res = "#" + saveConvertChar(entry.getKey().toString());
						bw.write(res);
					} else if (entry.getValue().toString().trim().equals("#newLine")) {
						bw.newLine();
					} else {

						bw.write(saveConvert(entry.getKey().toString(), true, true) + "="
								+ saveConvert(entry.getValue().toString(), false, true)

						);
					}
					bw.newLine();
				}
			}

			flag = true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 将Properties对象修改到指定文件（增，改，不包括删除原文件中具有的参数）
	 * 
	 * @param propertiesFilePath
	 *            修改到的properties文件路径
	 * @return 合并结果
	 * @param charset
	 *            文件字符集
	 */
	public boolean modifyToFile(String propertiesFilePath, String charset) {
		return modifyToFile(createFile(propertiesFilePath), charset);
	}

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	private String saveConvertChar(String theString) {
		boolean escapeUnicode = true;
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars and changes special saved
	 * chars to their original forms
	 */
	private static String loadConvert(String str) {
		int off = 0;
		int len = str.length();
		char[] in = str.toCharArray();
		char[] convtBuf = new char[1024];
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}
}