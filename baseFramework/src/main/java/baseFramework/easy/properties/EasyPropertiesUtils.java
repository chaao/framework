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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * EasyCommons 项目下的 Properties 文件操作工具类<br>
 * EasyPropertiesUtils：直接修改Properties文件的工具类，提供原格式写功能，包括保留注释，支持内容合并（merge: 增, 删,
 * 改）和修改（modify: 增,改）。<br>
 * 适合场景：对properties文件进行修改操作，又不影响原格式的情况。<br>
 * 
 * @author easyproject.cn
 * @since 1.0.0
 * 
 */
public class EasyPropertiesUtils {

	public static File createFile(String path) {
		URL url = EasyPropertiesUtils.class.getClassLoader().getResource(path);
		return new File(url.getPath());
	}

	/**
	 * 保留文件原格式，注释等
	 * 将properties中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath, Properties properties) {
		return merger(createFile(propertiesFilePath), properties, null);
	}

	/**
	 * 保留文件原格式，注释等
	 * 将properties中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath, Properties properties, String charset) {
		return merger(createFile(propertiesFilePath), properties, charset);
	}

	/**
	 * 保留文件原格式，注释等 将properties中的数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile, Properties properties) {
		return merger(propertiesFile, properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties中的数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile, Properties properties, String charset) {
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> e : properties.entrySet()) {
			map.put(e.getKey().toString(), e.getValue().toString());
		}
		return merger(propertiesFile, map, charset);
	}

	/**
	 * 保留文件原格式，注释等 将Map中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath, Map<String, String> properties) {

		return merger(createFile(propertiesFilePath), properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将Map中的数据合并到propertiesFilePath指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(String propertiesFilePath, Map<String, String> properties, String charset) {
		return merger(createFile(propertiesFilePath), properties, charset);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile, Map<String, String> properties) {
		return merger(propertiesFile, properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据合并到propertiesFile指定的Properties配置文件（增加，修改，删除）
	 * 
	 * @param propertiesFile
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean merger(File propertiesFile, Map<String, String> properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
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
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					String loadKey = loadConvert(key);
					if (properties.containsKey(loadKey)) { // 如果存在要修改的属性，则修改
						s = key + "=" + saveConvert(properties.get(loadKey), false, true);
						properties.remove(loadKey);
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
			if (!properties.isEmpty()) {

				for (Entry<String, String> entry : properties.entrySet()) {
					// 支持插入#开头的注释行
					if (entry.getValue().toString().trim().equals("#comments")) {
						String res = "#" + saveConvertChar(entry.getKey());

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
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile, Map<String, String> properties) {
		return modify(propertiesFile, properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile, Map<String, String> properties, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
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
				if ((!s.startsWith("#")) && s.indexOf("=") != -1) {
					String key = s.substring(0, equalMark).trim();
					// String value=s.substring(equalMark);
					String loadKey = loadConvert(key);
					if (properties.containsKey(loadKey)) { // 如果存在要修改的属性，则修改
						s = key + "=" + saveConvert(properties.get(loadKey), false, true);
						properties.remove(loadKey);
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

		if (flag) {
			flag = false;
			try {
				if (charset != null) {
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(propertiesFile), charset));
				} else {
					FileWriter fw = new FileWriter(propertiesFile);
					bw = new BufferedWriter(fw);
				}
				bw.write(sb.toString());

				if (!properties.isEmpty()) {

					for (Entry<String, String> entry : properties.entrySet()) {

						// 支持插入#开头的注释行
						if (entry.getValue().toString().trim().equals("#comments")) {

							String res = "#" + saveConvertChar(entry.getKey());

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
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null) {
					try {
						bw.flush();
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		return flag;
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath, Map<String, String> properties) {

		return modify(createFile(propertiesFilePath), properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath, Map<String, String> properties, String charset) {

		return modify(createFile(propertiesFilePath), properties, charset);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile, Properties properties) {

		return modify(propertiesFile, properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFile指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFile
	 *            配置文件
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(File propertiesFile, Properties properties, String charset) {
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> e : properties.entrySet()) {
			map.put(e.getKey().toString(), e.getValue().toString());
		}

		return modify(propertiesFile, map, charset);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath, Properties properties) {

		return modify(createFile(propertiesFilePath), properties, null);
	}

	/**
	 * 保留文件原格式，注释等 将properties数据修改到propertiesFilePath指定的Properties配置文件（增加，修改）
	 * 
	 * @param propertiesFilePath
	 *            配置文件存储路径
	 * @param properties
	 *            修改的数据列表（key,value），包括新增的属性
	 * @param charset
	 *            文件字符集
	 * @return 是否修改成功
	 */
	public final static boolean modify(String propertiesFilePath, Properties properties, String charset) {
		Map<String, String> map = new HashMap<String, String>();
		for (Entry<Object, Object> e : properties.entrySet()) {
			map.put(e.getKey().toString(), e.getValue().toString());
		}

		return modify(createFile(propertiesFilePath), map, charset);
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

	/*
	 * Converts unicodes to encoded &#92;uxxxx and escapes special characters
	 * with a preceding slash
	 */
	private static String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
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

	/**
	 * 根据正则表达式移除行，可以用来移除注释等
	 * 
	 * @param propertiesFile
	 *            properties文件对象
	 * @param regex
	 *            正则
	 * @param charset
	 *            字符集
	 * @return 是否成功
	 */
	public static boolean removeLinesToFile(File propertiesFile, String regex, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
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
				// 如果匹配删除
				Pattern pattern = Pattern.compile(regex);
				if (pattern.matcher(s).matches() || pattern.matcher(loadConvert(s)).matches()) {

				} else {
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
			// String[]
			// lines=sb.toString().split(System.getProperty("line.separator"));
			// for (String line : lines) {
			// if(line.trim().startsWith("#")){
			// // 注释，空格不转意
			// bw.write(saveConvertChar(line)+System.getProperty("line.separator"));
			// }else if(line.contains("=")){
			// // key=value
			// int equalMark = line.indexOf("=");
			// String key = line.substring(0, equalMark).trim();
			// String value = line.substring(equalMark+1);
			// line = saveConvert(key, true, false)
			// + "="
			// + saveConvert(
			// value,
			// false, true);
			// bw.write(line+System.getProperty("line.separator"));
			// }
			//
			//
			//
			// }

			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;

	}

	/**
	 * 根据正则表达式移除行，可以用来移除注释等
	 * 
	 * @param propertiesFile
	 *            properties文件对象
	 * @param regex
	 *            正则
	 * @return 是否成功
	 */
	public static boolean removeLinesToFile(File propertiesFile, String regex) {
		return removeLinesToFile(propertiesFile, regex, null);
	}

	/**
	 * 根据正则表达式移除行，可以用来移除注释等
	 * 
	 * @param propertiesFilePath
	 *            properties文件路径
	 * @param regex
	 *            正则
	 * @param charset
	 *            字符集
	 * @return 是否成功
	 */
	public static boolean removeLinesToFile(String propertiesFilePath, String regex, String charset) {
		return removeLinesToFile(createFile(propertiesFilePath), regex, charset);
	}

	/**
	 * 根据正则表达式移除行，可以用来移除注释等
	 * 
	 * @param propertiesFilePath
	 *            properties文件路径
	 * @param regex
	 *            正则
	 * @return 是否成功
	 */
	public static boolean removeLinesToFile(String propertiesFilePath, String regex) {
		return removeLinesToFile(createFile(propertiesFilePath), regex, null);
	}

	/**
	 * 根据正则表达式移除匹配的内容，可以用来移除注释等
	 * 
	 * @param propertiesFile
	 *            properties文件对象
	 * @param regex
	 *            正则
	 * @param charset
	 *            字符集
	 * @return 是否成功
	 */
	public static boolean removeMatchesToFile(File propertiesFile, String regex, String charset) {
		boolean flag = false;
		StringBuilder sb = new StringBuilder();
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
				// 如果匹配删除
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
		sb.replace(0, sb.length(), sb.toString().replaceAll(regex, ""));
		// 转换后删除
		sb.replace(0, sb.length(), loadConvert(sb.toString()).replaceAll(regex, ""));
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
			// bw.write(sb.toString());
			String[] lines = sb.toString().split(System.getProperty("line.separator"));
			for (String line : lines) {
				if (line.trim().startsWith("#")) {
					// 注释：空格不转义
					bw.write(saveConvertChar(line) + System.getProperty("line.separator"));
				} else if (line.contains("=")) {
					// key=value
					int equalMark = line.indexOf("=");
					String key = line.substring(0, equalMark).trim();
					String value = line.substring(equalMark + 1);
					line = saveConvert(key, true, true) + "=" + saveConvert(value, false, true);
					bw.write(line + System.getProperty("line.separator"));
				} else {
					bw.write(line + System.getProperty("line.separator"));
				}

			}

			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return flag;

	}

	/**
	 * 根据正则表达式移除匹配的内容，可以用来移除注释等
	 * 
	 * @param propertiesFile
	 *            properties文件对象
	 * @param regex
	 *            正则
	 * @return 是否成功
	 */
	public static boolean removeMatchesToFile(File propertiesFile, String regex) {
		return removeMatchesToFile(propertiesFile, regex, null);
	}

	/**
	 * 根据正则表达式移除匹配的内容，可以用来移除注释等
	 * 
	 * @param propertiesFilePath
	 *            properties文件路径
	 * @param regex
	 *            正则
	 * @param charset
	 *            字符集
	 * @return 是否成功
	 */
	public static boolean removeMatchesToFile(String propertiesFilePath, String regex, String charset) {
		return removeMatchesToFile(createFile(propertiesFilePath), regex, charset);
	}

	/**
	 * 根据正则表达式移除匹配的内容，可以用来移除注释等
	 * 
	 * @param propertiesFilePath
	 *            properties文件路径
	 * @param regex
	 *            正则
	 * @return 是否成功
	 */
	public static boolean removeMatchesToFile(String propertiesFilePath, String regex) {
		return removeMatchesToFile(createFile(propertiesFilePath), regex, null);
	}

	private static String saveConvertChar(String theString) {
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

}