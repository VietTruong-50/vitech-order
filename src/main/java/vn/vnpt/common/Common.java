package vn.vnpt.common;

import vn.vnpt.common.constant.ConstantString;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

public class Common {

    public static Object converterBigDecimal(Class<?> t, Object resFromDb) {
        if (t == Integer.class) {
            return ((BigDecimal) resFromDb).intValue();
        } else if (t == Long.class) {
            return ((BigDecimal) resFromDb).longValue();
        } else if (t == Float.class) {
            return ((BigDecimal) resFromDb).floatValue();
        } else if (t == Double.class) {
            return ((BigDecimal) resFromDb).doubleValue();
        } else if (t == Short.class) {
            return ((BigDecimal) resFromDb).shortValue();
        } else if (t == Byte.class) {
            return ((BigDecimal) resFromDb).byteValue();
        } else if (t == Boolean.class) {
            return ((BigDecimal) resFromDb).intValue() != 0;
        } else {
            return null;
        }
    }

    public static boolean isNullOrEmpty(Object obj) {
        return Objects.isNull(obj) || (obj instanceof String ? ((String) obj).isEmpty() :
                (obj instanceof Collection && ((Collection<?>) obj).isEmpty()));
    }

    public static String subString(String str) {
        if (isNullOrEmpty(str)) {
            return "";
        }
        if (str.length() > 100) {
            return str.substring(ConstantString.ZERO, ConstantString.MAX_SIZE);
        }
        return str;
    }

    // default if null or empty
    public static <T> T defaultIfNullOrEmpty(T str, T defaultValue) {
        return isNullOrEmpty(str) ? defaultValue : str;
    }


    public static String GenerateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getAlphaNumeric(int len) {
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z'};

        char[] c = new char[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }

    public static Integer randomNumber(boolean includeNegative) {
        if (includeNegative) return new Random().nextInt();
        else return new Random().nextInt(Integer.MAX_VALUE);
    }

    public static String normalizeNamespace(String str) {
        return removeDiacritics(str
                .concat("-" + Common.getAlphaNumeric(4))
                .replaceAll("[\\s_]", "-")
                .toLowerCase());
    }

    //Remove UTF-8
    public static String removeDiacritics(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("");
    }
}
