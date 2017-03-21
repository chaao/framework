package baseFramework.utils;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @author lichao
 * @date 2014年11月27日
 */
/**
 * @author chao.li
 * @date 2016年11月4日
 */
public class DateTools extends org.apache.commons.lang3.time.DateUtils {

	public static long getTruncatedMinute() {
		return truncate(new Date(), Calendar.MINUTE).getTime();
	}

	public static long getTruncatedHour() {
		return truncate(new Date(), Calendar.HOUR).getTime();
	}

	public static long getTruncatedToday() {
		return truncate(new Date(), Calendar.DATE).getTime();
	}

	public static long truncateToMinute(long time) {
		return truncate(new Date(time), Calendar.MINUTE).getTime();
	}

	public static long truncateToHour(long time) {
		return truncate(new Date(time), Calendar.HOUR).getTime();
	}

	public static long truncateToToday(long time) {
		return truncate(new Date(time), Calendar.DATE).getTime();
	}

	/**
	 * 10分钟取整
	 */
	public static long truncateToTenMinute() {

		Calendar val = Calendar.getInstance();

		long time = val.getTimeInMillis();

		int millisecs = val.get(Calendar.MILLISECOND);
		time = time - millisecs;

		int seconds = val.get(Calendar.SECOND);
		time = time - (seconds * 1000L);

		int minutes = val.get(Calendar.MINUTE);
		int span = minutes % 10;
		if (span > 0) {
			time = time - (span * 60000L);
		}

		return time;
	}

	/**
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateFormat(long date) {
		FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String dateFormat(Date date) {
		FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String dateFormat(Date date, String pattern) {
		FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
		return dateFormat.format(date);
	}

	public static String dateFormat(long date, String pattern) {
		FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
		return dateFormat.format(date);
	}

	public static Date getUTCTime() {
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTime();
	}

	/**
	 * 格式化间隔时间
	 * 
	 * @param milliseconds
	 */
	public static String intervalFormat(long milliseconds) {

		TimeUnit unit = chooseUnit(milliseconds);
		double value = (double) milliseconds / MILLISECONDS.convert(1, unit);

		// Too bad this functionality is not exposed as a regular method call
		return String.format(Locale.ROOT, "%.4g %s", value, abbreviate(unit));
	}

	private static TimeUnit chooseUnit(long milliseconds) {
		if (DAYS.convert(milliseconds, MILLISECONDS) > 0) {
			return DAYS;
		}
		if (HOURS.convert(milliseconds, MILLISECONDS) > 0) {
			return HOURS;
		}
		if (MINUTES.convert(milliseconds, MILLISECONDS) > 0) {
			return MINUTES;
		}
		if (SECONDS.convert(milliseconds, MILLISECONDS) > 0) {
			return SECONDS;
		}
		return MILLISECONDS;
	}

	private static String abbreviate(TimeUnit unit) {
		switch (unit) {
		case MILLISECONDS:
			return "ms";
		case SECONDS:
			return "s";
		case MINUTES:
			return "min";
		case HOURS:
			return "h";
		case DAYS:
			return "d";
		default:
			throw new AssertionError();
		}
	}

}
