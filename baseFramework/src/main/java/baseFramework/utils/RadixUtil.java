package baseFramework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理进制的工具类
 *
 * @author chao.li
 * @date 2017/6/14
 */
public class RadixUtil {
    private final static String NAME = RadixUtil.class.getName();

    /**
     * 产生进制的数字
     **/
    public final static char[] DIGIT = {//
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', //
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', //
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', //
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', //
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', //
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', //
            '8', '9' //
    };

    public final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();

    static {
        for (int i = 0; i < DIGIT.length; i++) {
            digitMap.put(Character.valueOf(DIGIT[i]), Integer.valueOf(i));
        }
    }

    /**
     * 支持的最大进制数
     */
    public static final int MAX_RADIX = DIGIT.length;

    /**
     * 支持的最小进制数
     */
    public static final int MIN_RADIX = 2;

    /**
     * 最多一次返回32个字符
     **/
    public static final int SIZE = 32;

    /**
     * 获得0的个数
     **/
    public static String obtainZero(int num) {
        return obtainChar(num, DIGIT[0]);
    }

    /**
     * 获得0的个数
     **/
    public static String obtainZero0(int num) {
        return obtainChar(num, '0');
    }

    /**
     * 获得特殊字符的个数
     **/
    public static String obtainChar(int num, char c) {
        if (num < 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (num-- > 0) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获得long型数据的最大长度
     **/
    public static int obtainMaxLengOfLong() {
        return longToString(Long.MAX_VALUE).length();
    }

    /**
     * 只用26个大写字母
     **/
    public static String longToLetter(long i) {
        return longToString(i, 26);
    }

    public static String longToString(long i) {
        return longToString(i, MAX_RADIX);
    }

    /**
     * 将长整型数值转换为指定的进制数（最大支持62进制）
     */
    public static String longToString(long i, int radix) {
        if (radix < MIN_RADIX || radix > MAX_RADIX) {
            radix = 10;
        }
        if (radix == 10) {
            return Long.toString(i);
        }

        StringBuilder sb = new StringBuilder(SIZE);
        boolean negative = (i < 0);
        if (negative) {
            i = -i;
        }

        while (i >= radix) {
            sb.append(DIGIT[(int) (i % radix)]);
            i = i / radix;
        }
        sb.append(DIGIT[(int) (i)]);
        if (negative) {
            sb.append("-");
        }
        return sb.reverse().toString();
    }

    /**
     * 将字符串转换为长整型数字
     */
    public static long stringToLong(String s) {
        return stringToLong(s, MAX_RADIX);
    }

    public static long letterToLong(String s) {
        return stringToLong(s, 26);
    }

    /**
     * 将字符串转换为长整型数字
     */
    public static long stringToLong(String s, int radix) {
        if (s == null) {
            throw new NumberFormatException("null");
        }
        if (radix < MIN_RADIX) {
            throw new NumberFormatException("radix " + radix + " less than " + NAME + ".MIN_RADIX");
        }
        if (radix > MAX_RADIX) {
            throw new NumberFormatException("radix " + radix + " greater than " + NAME + ".MAX_RADIX");
        }
        // 返回结果
        long result = 0;
        char[] ss = s.toCharArray();
        // 计算阶乘
        long factorial = 1;
        for (int i = (ss.length - 1); i >= 0; i--) {
            char c = ss[i];
            Integer v = digitMap.get(Character.valueOf(c));
            if (v != null) {
                result += v.intValue() * factorial;
                factorial *= radix;
            } else if (c == '-') {
                result = -result;
            } else {
                throw new NumberFormatException("char " + c + " not in " + NAME + ".DIGIT");
            }
        }
        return result;
    }

    public static byte[] stringToBytes(String s) {
        return stringToBytes(s, MAX_RADIX);
    }

    /**
     * 将string转换成byte数组
     */
    public static byte[] stringToBytes(String s, int radix) {
        if (s == null) {
            throw new NullPointerException(NAME + "parameter s is null");
        }
        // 二进制的数组
        StringBuilder binary = new StringBuilder(s.getBytes().length * 8);
        long t;
        String te = null;
        // 定长,照着这个长度切割
        int llength = obtainMaxLengOfLong();
        // 长度
        // 最后一位是标记，最后一段的长度的
        int length = s.length() - 1;
        // 最后一段的长度
        int lastLength = (int) stringToLong(s.substring(length, s.length()), radix);
        // 下标
        int start = 0;
        int end = llength;
        while (end < length) {
            // 转换成long
            t = stringToLong(s.substring(start, end), radix);
            // 转换成二进制string
            te = Long.toBinaryString(t);
            // 补齐0
            te = obtainZero0(radix - te.length()) + te;
            binary.append(te);
            start = end;
            end = start + llength;
        }
        // 最后一段
        if (start < length) {
            t = stringToLong(s.substring(start, length), radix);
            te = Long.toBinaryString(t);
            te = obtainZero0(lastLength - te.length()) + te;
            binary.append(te);
        }
        System.out.println("binary.toString()");
        System.out.println(binary.toString());
        // 将二进制转换成byte数据
        // 返回的结果
        length = binary.length();
        byte[] result = new byte[length / Byte.SIZE];
        int i = 0;
        start = 0;
        end = Byte.SIZE;
        while (end < length) {
            result[i++] = parseByte(binary.substring(start, end), 2);
            start = end;
            end = start + Byte.SIZE;
        }
        // 最后一段
        if (start < length) {
            result[i++] = parseByte(binary.substring(start, length), 2);
        }
        return result;
    }

    public static String bytesToString(byte[] bs) {
        return bytesToString(bs, MAX_RADIX);
    }

    /**
     * 将byte数组转换成string
     */
    public static String bytesToString(byte[] bs, int radix) {
        // 最终的结果
        StringBuilder result = new StringBuilder(bs.length);
        // 二进制的数组
        StringBuilder binary = new StringBuilder(bs.length * 8);
        // 临时存储
        String t = null;
        for (int n = 0; n < bs.length; n++) {
            byte b = bs[n];
            // 将byte转化成 二进制的string
            t = Integer.toBinaryString(Byte.toUnsignedInt(b));
            // 获得完成8位的二进制的string
            binary.append(obtainZero0(Byte.SIZE - t.length()) + t);
        }
        System.out.println("binary.toString()");
        System.out.println(binary.toString());
        // 长度
        int length = binary.length();
        // 下标
        int start = 0;
        int end = radix;
        while (end < length) {
            t = longToString(Long.valueOf(binary.substring(start, end), 2), radix);
            result.append(obtainZero(obtainMaxLengOfLong() - t.length()) + t);
            start = end;
            end = start + radix;
        }
        // 最后不必设置成定长
        if (start < length) {
            t = longToString(Long.valueOf(binary.substring(start, length), 2), radix);
            result.append(t);
            result.append(longToString(length - start, radix));
        } else {
            result.append(longToString(0, radix));
        }
        return result.toString();
    }

    public static byte parseByte(String s, int radix) throws NumberFormatException {
        if (s == null) {
            throw new NumberFormatException("s is null");
        }
        if (s.length() < 8) {
            return Byte.parseByte(s, radix);
        }
        boolean nagetive = false;
        if (s.charAt(0) == '1') {
            s = '0' + s.substring(1, s.length());
            nagetive = true;
        }
        int i = Integer.parseInt(s, radix);
        if (nagetive) {
            i = -i;
        }
        if (i < Byte.MIN_VALUE || i > Byte.MAX_VALUE)
            throw new NumberFormatException("Value out of range. Value:\"" + s + "\" Radix:" + radix);
        return (byte) i;
    }

}
