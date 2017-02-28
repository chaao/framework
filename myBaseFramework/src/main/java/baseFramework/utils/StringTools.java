package baseFramework.utils;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;

/**
 * @author lichao
 * @date 2014年11月27日
 */
public class StringTools {
	private static final Joiner joinerWithBlank = Joiner.on(" ").skipNulls();
	private static final Joiner joinerWithComma = Joiner.on(",").skipNulls();

	public static String joinWithBlank(Object first, Object second, Object... rest) {
		return joinerWithBlank.join(first, second, rest);
	}

	public static String joinWithBlank(Iterable<?> parts) {
		return joinerWithBlank.join(parts);
	}

	public static String joinWithComma(Object first, Object second, Object... rest) {
		return joinerWithComma.join(first, second, rest);
	}

	public static String joinWithComma(Iterable<?> parts) {
		return joinerWithComma.join(parts);
	}

	public static String[] trimAndSplit(String str, String separatorChars) {
		return StringUtils.split(trimAll(str), separatorChars);
	}

	public static String trimAll(final String str) {
		return str == null ? null : str.replaceAll("\\s*", "");
	}

	/**
	 * 效率高于String.format()5倍
	 * 
	 * @param template
	 *            {}
	 */
	public static String format(String template, Object... args) {
		template = String.valueOf(template); // null -> "null"

		// start substituting the arguments into the '{}' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			int placeholderStart = template.indexOf("{}", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

}
