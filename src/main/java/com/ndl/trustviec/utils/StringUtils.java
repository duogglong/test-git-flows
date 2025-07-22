package com.ndl.trustviec.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String UNDERSCORE = "_";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final String paramCharacter = "\\{\\}";

    public StringUtils() {
    }

    public static String format(String format, List<String> params) {
        String res = format;
        for (String param : params) {
            res = res.replaceFirst(paramCharacter, param);
        }
        return res;
    }

    public static boolean isEmpty(String... args) {
        String[] var1 = args;
        int var2 = args.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            String ele = var1[var3];
            if (ele == null || ele.trim().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static String concatenate(List<String> listOfItems, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = listOfItems.iterator();

        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String toStringFromList(List<String> list, String separator) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); ++i) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static boolean checkSpecialCharacter(String value) {
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~ -]", Pattern.CASE_INSENSITIVE);
        Matcher m = special.matcher(value);
        return m.find();
    }

    public static String convertDateToString(Date date, String pattern) {
        String dateStr = null;
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat(pattern);

            try {
                dateStr = dateFormat.format(date);
            } catch (Exception var5) {
                return null;
            }
        }

        return dateStr;
    }

    public static String escapePath(String path) {
        if (path == null || EMPTY.equals(path)) {
            return path;
        }

        String step1 = path.replace("\\", "\\\\");
        step1 = step1.replace("%", "\\%");
        return step1.replace("_", "\\_");
    }

    public static String trimString(String input, String strReplacement) {
        return Arrays.stream(input.trim().split("\\s+"))
                .map(String::trim)
                .collect(Collectors.joining(strReplacement));
    }

    public static Integer getYearFromString(String dateString) {
        try {
            if (!StringUtils.isEmpty(dateString)) {
                boolean hasHyphen = dateString.contains("-");

                SimpleDateFormat sdf = hasHyphen ? new SimpleDateFormat("yyyy-MM-dd") : new SimpleDateFormat("yyyy");

                Date date = sdf.parse(dateString);

                return Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equals(str2);
    }

    public static boolean isNotEquals(String str1, String str2) {
        return !equals(str1, str2);
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    public static String generateCIF() {
        Random random = new Random();
        StringBuilder cif = new StringBuilder();
        int cifLength = 10;

        for (int i = 0; i < cifLength; i++) {
            int digit = random.nextInt(10);
            cif.append(digit);
        }

        return cif.toString();
    }

    public static String generateEnterpriseId() {
        Random random = new Random();
        StringBuilder cif = new StringBuilder();
        int cifLength = 6;

        for (int i = 0; i < cifLength; i++) {
            int digit = random.nextInt(6);
            cif.append(digit);
        }

        return cif.toString();
    }

    /**
     * Hàm validate email.
     * @param email chuỗi email cần kiểm tra
     * @return true nếu email hợp lệ, false nếu không hợp lệ
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
