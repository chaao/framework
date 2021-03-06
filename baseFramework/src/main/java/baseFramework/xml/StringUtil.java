package baseFramework.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具类
 */
final class StringUtil {
	public static final String EMPTY_STRING = "";
	/**
	 * Windows 换行符
	 */
	public static final String WINDOWS_NEXT_LINE = "\r\n";
	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private static final String WINDOWS_PREFIX = "windows";
	private static final String SEPARATOR_OF_UNIX_FILE = "/";
	private static final String SEPARATOR_OF_WINDOWS_FILE = "\\";

	/**
	 * @param sb        字符串构建器
	 * @param targetStr 目标字符串
	 * @return boolean true-追加成功, false-追加失败
	 * @Title: append
	 * @Description: 追加有效字符串(无效字符串:null/空字符串"")
	 */
	public static boolean appendEffectiveVal(StringBuilder sb, String targetStr) {
		if (null != sb && isNotEmpty(targetStr, true)) {
			sb.append(targetStr);
			return true;
		}
		return false;
	}

	/**
	 * @param propertyName 属性名
	 * @return String "get"开头且参数(propertyName)值首字母大写的字符串
	 * @Title: getMethodName
	 * @Description: 获取对象类型属性的get方法名
	 */
	public static String convertToReflectGetMethod(String propertyName) {
		return "get" + toFirstUpChar(propertyName);
	}

	/**
	 * @param propertyName 属性名
	 * @return String "set"开头且参数(propertyName)值首字母大写的字符串
	 * @Title: convertToReflectSetMethod
	 * @Description: 获取对象类型属性的set方法名
	 */
	public static String convertToReflectSetMethod(String propertyName) {
		return "set" + toFirstUpChar(propertyName);
	}

	/**
	 * @param likeVal 模糊参数, 值为null时, 返回null
	 * @return String 转换后模糊条件值("%" + likeVal + "%")
	 * @Title: parseVagueCondition
	 * @Description: 转换为模糊查询条件值, 内部包含单引号(')或百分号(%)时, 使用转义符号进行转义(\'或\%),
	 * 并在前后添加百分号(%)
	 */
	public static String convertVagueCondition(String likeVal) {
		if (likeVal == null)
			return null;

		likeVal = likeVal.replace(SEPARATOR_OF_WINDOWS_FILE, "\\\\'").replace("'", "\\'").replace("%", "\\%").replace("_", "\\_");
		return "%" + likeVal + "%";
	}

	/**
	 * @param source 源字符串
	 * @param target 目标字符串
	 * @return int 数量值, -1:任意参数非法时
	 * @Title: countSubstring
	 * @Description: 计算源字符串包含目标字符串的数量
	 */
	public static int countSubstring(String source, String target) {
		if (source == null || target == null) {
			return -1;
		}

		int srcLen = source.length();
		int subLen = target.length();
		if (srcLen < subLen) {
			return 0;
		}

		if (source.equals(target)) {
			return 1;
		}

		char[] srcChs = source.toCharArray();
		char[] subChs = target.toCharArray();
		char[] tChs = new char[subChs.length];
		int count = 0;

		for (int i = 0, maxLen = srcLen - subLen + 1; i < maxLen; i++) {
			if (srcChs[i] == subChs[0] && srcChs[i + subLen - 1] == subChs[subLen - 1]) {
				System.arraycopy(srcChs, i, tChs, 0, subLen);
				if (Arrays.equals(tChs, subChs)) {
					++count;
					i += subLen;
				}
			}
		}

		return count;
	}

	/**
	 * @param src    第一个对象
	 * @param target 第二个对象
	 * @return boolean 两个对象equals返回true, 否则返回false
	 * @Title: equalsTwo
	 * @Description: 验证两个对象是否 equals
	 */
	public static boolean equalsTwo(Object src, Object target) {
		return equalsTwo(src, target, false);
	}

	/**
	 * @param src        第一个对象
	 * @param target     第二个对象
	 * @param ignoreCase 字符串比较时是否忽略大小写
	 * @return boolean 两个对象equals返回true, 否则返回false
	 * @Title: equalsTwo
	 * @Description: 验证两个对象是否 equals
	 */
	public static boolean equalsTwo(Object src, Object target, boolean ignoreCase) {
		// 两个都为null,
		// 或两个引用指向同一个地址
		if (target == src) {
			return true;
		}

		// 剩余条件: 两个对象不相等(或引用地址不同)
		// 其中一个为null
		if (null == target || null == src) {
			return false;
		}

		// 剩余条件: 两个对象都不为null,且不相等
		// src 是字符串
		if (src instanceof String) {
			if (target instanceof String && ignoreCase) {
				return ((String) src).equalsIgnoreCase((String) target);
			}
		}

		return src.equals(target);
	}

	/**
	 * @return String classpath 路径, 总是以"/"结尾
	 * @throws Exception
	 * @Title: getClassPath
	 * @Description: 获取 classpath 路径
	 */
	public static String getClassPath() throws Exception {
		URL resource = Thread.currentThread().getContextClassLoader().getResource("");
		String basePath = resource.toURI().getPath();
		if (isWindowsSys()) {
			basePath = basePath.substring(1);
		}

		basePath = basePath.replace(SEPARATOR_OF_WINDOWS_FILE, SEPARATOR_OF_UNIX_FILE);
		if (!basePath.endsWith(SEPARATOR_OF_UNIX_FILE)) {
			basePath += SEPARATOR_OF_UNIX_FILE;
		}

		return basePath;
	}

	/**
	 * @return String 项目根目录
	 * @Title: getWebProjectPath
	 * @Description: 获取项目根目录, 总是以"/"结尾
	 */
	public static String getWebProjectPath() {
		try {
			return StringUtil.getClassPath().split("WEB-INF")[0];
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param path 文件路径, 如果以文件分隔符开头, 且不是 Windows系统时, 将原样返回
	 * @return String 完整文件路径, path == null 时返回 null
	 * @Title: getFullFilePath
	 * @Description: 获取完整文件路径(盘符路径)
	 */
	public static String getFullFilePath(String path) {
		if (isEmpty(path, true)) {
			return null;
		}

		String rPath = path.trim();
		boolean isWinSys = isWindowsSys();
		String classPath;
		try {
			classPath = getClassPath();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}

		boolean isRootPath = isStartSeparator(rPath);
		if (isWinSys) {
			LOGGER.debug("The System of WINDOWS");
			rPath = rPath.replace(SEPARATOR_OF_UNIX_FILE, SEPARATOR_OF_WINDOWS_FILE);
			while (isRootPath) {
				rPath = rPath.substring(1);
			}
			return rPath.matches("[a-zA-Z][:][\\\\].+") ? rPath : (classPath + rPath);
		} else {
			LOGGER.debug("The System of UNIX");
			rPath = rPath.replace(SEPARATOR_OF_WINDOWS_FILE, SEPARATOR_OF_UNIX_FILE);
			return (rPath.startsWith(classPath) ? rPath : (classPath + rPath));
		}
	}

	/**
	 * @param path 目标路径
	 * @return boolean true-完整路径, false-相对路径
	 * @Title: isFullPath
	 * @Description: 是否完整路径
	 */
	public static boolean isFullPath(String path) {
		if (isEmpty(path, true)) {
			return false;
		}

		if (isWindowsSys()) {
			return path.matches("[a-zA-Z][:][\\\\].+");
		} else {
			return path.startsWith(SEPARATOR_OF_UNIX_FILE);
		}
	}

	/**
	 * @param target 字符串
	 * @param isTrim 是否去掉前后空格
	 * @return boolean true-空对象, false-非空对象
	 * @Title: isEmpty
	 * @Description: 检测字符串对象是否为 null 或 length() == 0
	 */
	public static boolean isEmpty(Object target, boolean isTrim) {
		if (null == target) {
			return true;
		}
		return isEmpty(String.valueOf(target), isTrim);
	}

	/**
	 * @param target 字符串
	 * @param isTrim 是否去掉前后空格
	 * @return boolean true-空对象, false-非空对象
	 * @Title: isEmpty
	 * @Description: 检测字符串对象是否为 null 或 length() == 0
	 */
	public static boolean isEmpty(String target, boolean isTrim) {
		if (target != null) {
			if (isTrim) {
				target = target.trim();
			}
			return target.length() == 0;
		}
		return true;
	}

	/**
	 * @param target 目标字符串
	 * @param isTrim 验证时是否去掉前后空格
	 * @return boolean true-不是null值且不是空串, false-是空串或null值
	 * @Title: isNotEmpty
	 * @Description: 验证指定字符串是否不时null或空串
	 */
	public static boolean isNotEmpty(String target, boolean isTrim) {
		return !isEmpty(target, isTrim);
	}

	/**
	 * @param target 目标字符串
	 * @return boolean true-是纯数值字符串(或整数, 或小数, 或指数), false-不是纯数值字符串(或null值,
	 * 或0长度值, 或全空白字符值)
	 * @Title: isNumber
	 * @Description: 是否纯数值字符串
	 */
	public static boolean isNumber(String target) {
		final Pattern pattern = Pattern.compile("^[+-]?((\\d{0,}(\\.\\d*)?[f|F|d|D]?)|(\\d+[l|L]?))$");
		return isEmpty(target, true) ? false : pattern.matcher(target).matches();
	}

	/**
	 * @param path 目标路径
	 * @return boolean true-路径以文件分隔符开头, false-不是文件分隔符开头
	 * @Title: isStartSeparator
	 * @Description: 路径是否以文件分隔符开头
	 */
	private static boolean isStartSeparator(String path) {
		return path.startsWith(FILE_SEPARATOR);
	}

	/**
	 * @return boolean true-是 windows 系统, false-不是 windows 系统
	 * @Title: isWindowsSys
	 * @Description: 判断当前系统是否 windows 系统
	 */
	public static boolean isWindowsSys() {
		return System.getProperty("os.name").toLowerCase().startsWith(WINDOWS_PREFIX);
	}

	/**
	 * @param List<String> List中的元素必须是数字字符串。
	 * @return List<Integer>
	 * @Title: listStringToInteger
	 * @Description: 将List<String>转为List<Integer>
	 */
	public static List<Integer> listStringToInteger(List<String> str) {
		if (null != str && str.size() > 0) {
			List<Integer> listInt = new ArrayList<Integer>();
			for (int i = 0; i < str.size(); i++) {
				listInt.add(Integer.parseInt(str.get(i)));
			}
			return listInt;
		}
		return null;
	}

	/**
	 * @param target 目标字符串
	 * @param isTrim <i>target</i>非空时，返回值是否去掉前后空格；true-去掉前后空格，false返回原字符串
	 * @return String 转换后字符串
	 * @Title: nullToEmpty
	 * @Description: 将null字符串转换为空字符串("")
	 */
	public static String nullToEmpty(String target, boolean isTrim) {
		if (isEmpty(target, isTrim)) {
			target = "";
		} else if (isTrim) {
			target = target.trim();
		}
		return target;
	}

	/**
	 * @param targetArray 目标数组
	 * @return String 数组为null时返回"null", 否则返回 Arrays.toString()
	 * @Title: toArrayString
	 * @Description: 将数组转换为字符串
	 */
	public static String toArrayString(Object[] targetArray) {
		return (null == targetArray ? "null" : Arrays.toString(targetArray));
	}

	/**
	 * @param target 目标字符串
	 * @return String 首字母大写的字符串
	 * @Title: toFirstUpChar
	 * @Description: 将字符串的首字母大写
	 */
	public static String toFirstUpChar(String target) {
		StringBuilder sb = new StringBuilder(target);
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		return sb.toString();
	}

	/**
	 * @param sb          字符串构建器
	 * @param emptyToNull 字符串构建器为空或null时处理标识, true-返回null, false-返回空字符串
	 * @return String 解析后字符串
	 * @Title: toString
	 * @Description: 字符串解析
	 */
	public static String toString(StringBuilder sb, boolean emptyToNull) {
		if (null == sb || 0 == sb.length()) {
			return (emptyToNull ? null : "");
		}

		return sb.toString();
	}

	/**
	 * @param targetPath 目标路径
	 * @return String 相对路径, null-<i>targetPath</i>非法或获取系统前缀失败
	 * @Title: getRelativePath
	 * @Description: 获取相对路径
	 */
	public static String getRelativePath(String targetPath) {
		try {
			if (isNotEmpty(targetPath, true)) {
				String classPath = getClassPath();
				String basePath = classPath.split("WEB-INF")[0];

				basePath = basePath.replace(SEPARATOR_OF_WINDOWS_FILE, SEPARATOR_OF_UNIX_FILE);
				targetPath = targetPath.replace(SEPARATOR_OF_WINDOWS_FILE, SEPARATOR_OF_UNIX_FILE);
				if (targetPath.startsWith(basePath)) {
					int prefixLen = basePath.length();
					return targetPath.substring(prefixLen);
				}
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * @param isTrim  true-检测时去掉前后空格
	 * @param targets 目标字符串
	 * @return boolean false-至少包含一个无效字符串, true-所有被检测字符串都有效
	 * @Title: valid
	 * @Description: 检测是否包含无效字符串(null或0长度)
	 */
	public static boolean valid(boolean isTrim, Object... targets) {
		if (null == targets || 0 >= targets.length) {
			return false;
		}

		for (int i = 0; i < targets.length; i++) {
			Object target = targets[i];
			if (isEmpty(target, isTrim)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param targetPath 目标路径
	 * @return String 转换后路径, <i>targetPath</i>非法时返回null
	 * @Title: convertToUnixPath
	 * @Description: 转换为UNIX文件路径分隔符
	 */
	public static String convertToUnixPath(String targetPath) {
		return (isEmpty(targetPath, true) ? null : targetPath.replace(SEPARATOR_OF_WINDOWS_FILE, SEPARATOR_OF_UNIX_FILE));
	}

	/**
	 * @param filePath 文件路径, 路径非法时总是返回null值
	 * @return String 路径中最后一个文件分隔符之后的字符串值
	 * @Title: getFileName
	 * @Description: 获取文件名
	 */
	public static String getFileName(String filePath) {
		if (isNotEmpty(filePath, true)) {
			filePath = convertToUnixPath(filePath);
			String fileName = filePath.substring(filePath.lastIndexOf(StringUtil.SEPARATOR_OF_UNIX_FILE) + 1);
			return fileName;
		} else {
			return null;
		}
	}

	/**
	 * @param valOjb 目标对象, null时返回{@link #EMPTY_STRING}
	 * @param isTrim 是否去掉前后空格
	 * @return String 转换后字符串
	 * @Title: toStringEmtry
	 * @Description: 转换为字符串
	 */
	public static String toStringEmpty(Object valOjb, boolean isTrim) {
		String val = EMPTY_STRING;
		if (null != valOjb) {
			val = String.valueOf(valOjb);
		}

		if (isTrim) {
			val = val.trim();
		}

		return val;
	}
}
