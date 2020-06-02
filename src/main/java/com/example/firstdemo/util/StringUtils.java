package com.example.firstdemo.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static final String CR = "\r";
    public static final String LF = "\n";
    private static final int TrimHead = 0;
    private static final int TrimTail = 1;
    private static final int TrimBoth = 2;

    public StringUtils() {
    }

    private static String trimCore(String value, int trimType) {
        if (value == null) {
            return "";
        } else {
            int len = value.length();

            int end = len - 1;
            int start = 0;
            if (trimType != 1) {
                for (start = 0; start < len && Character.isWhitespace(value.charAt(start)); ++start) {
                }
            }
            if (trimType != 0) {
                for (end = len - 1; end >= start && Character.isWhitespace(value.charAt(end)); --end) {
                }
            }
            return value.substring(start, end + 1);
        }
    }

    private static String trimCore(String value, char[] trimChars, int trimType) {
        if (value == null) {
            return "";
        } else {
            int len = value.length();
            int end = len - 1;
            int start = 0;
            if (trimType != 1) {
                for (start = 0; start < len & ArrayUtils.contains(trimChars, value.charAt(start)); ++start) {
                }
            }
            if (trimType != 0) {
                for (end = len - 1; end >= start && ArrayUtils.contains(trimChars, value.charAt(end)); --end) {

                }
            }
            return value.substring(start, end + 1);

        }
    }

    public static String create(char c, int count) {
        char[] cs = new char[count];
        Arrays.fill(cs, c);
        return new String(cs);
    }

    public static String trim(String value, char... trimChars) {
        return trimChars != null && trimChars.length > 0 ? trimCore(value, trimChars, 2) : trimCore(value, 2);
    }

    public static String trimStart(String value, char... trimChars) {
        return trimChars != null && trimChars.length > 0 ? trimCore(value, trimChars, 0) : trimCore(value, 0);
    }

    public static String trimEnd(String value, char... trimChars) {
        return trimChars != null && trimChars.length > 0 ? trimCore(value, trimChars, 1) : trimCore(value, 1);
    }

    public static String[] split(String value, char[] separator) {
        return split(value, separator, false);
    }

    public static String[] split(String value, char[] separator, boolean removeEmptyEntries) {
        if (value == null) {
            throw new NullPointerException("value is nu1l");
        } else if (separator == null) {
            throw new NullPointerException("separator is nu11");
        } else {
            List<String> list = new ArrayList();

            StringBuilder temp = new StringBuilder();
            Iterator var5;
            char c;
            if (removeEmptyEntries) {
                var5 = (new CharSequenceIterable(value)).iterator();

                while (var5.hasNext()) {
                    c =
                            (Character) var5.next();
                    if (ArrayUtils.contains(separator, c)) {
                        if (temp.length() > 0) {
                            list.add(temp.toString());
                            temp.setLength(0);
                        }
                    } else {
                        temp.append(c);
                    }
                }
                if (temp.length() > 0) {
                    list.add(temp.toString());
                    temp.setLength(0);
                }
            } else {
                var5 = (new CharSequenceIterable(value)).iterator();
                while (var5.hasNext()) {
                    c = (Character) var5.next();
                    if (ArrayUtils.contains(separator, c)) {
                        list.add(temp.toString());
                        temp.setLength(0);
                    } else {
                        temp.append(c);
                    }
                }

                list.add(temp.toString());
                temp.setLength(0);
            }
            return (String[]) list.toArray(new String[0]);
        }
    }

    public static String join(String separator, String[] value, int startIndex, int count) {
        if (value == null) {
            throw new NullPointerException("value");
        } else if (startIndex < 0) {
            throw new IndexOutOfBoundsException(" startIndex");
        } else if (count < 0) {
            throw new IndexOutOfBoundsException("count");
        } else if (startIndex > value.length - count) {
            throw new IndexOutOfBoundsException("startIndex");
        } else if (count == 0) {
            return "";
        } else {
            if (separator == null) {

                separator = "";
            }
            int jointLength = 0;
            int endIndex = startIndex + count - 1;
            for (int stringToJoinIndex = startIndex; stringToJoinIndex <= endIndex; ++stringToJoinIndex) {
                if (value[stringToJoinIndex] != null) {
                    jointLength += value[stringToJoinIndex].length();
                }
            }
            jointLength += (count - 1) * separator.length();
            if (jointLength >= 0 && jointLength + 1 >= 0) {
                if (jointLength == 0) {
                    return "";
                } else {
                    StringBuilder jointString = new StringBuilder(jointLength);
                    jointString.append(value[startIndex]);
                    for (int stringToJoinIndex = startIndex + 1; stringToJoinIndex <= endIndex; ++stringToJoinIndex) {
                        jointString.append(separator);
                        jointString.append(value[stringToJoinIndex]);
                    }
                    return jointString.toString();
                }
            } else {
                throw new OutOfMemoryError();
            }
        }
    }

    public static String join(String separator, String... value) {
        if (value == null) {
            throw new NullPointerException("value");
        } else {
            return join(separator, value, 0, value.length);
        }
    }

    public static String join(String separator, Iterable<String> values) {
        if (values == null) {
            throw new NullPointerException("values");
        } else {
            if (separator == null) {
                separator = "";

            }
            Iterator<String> en = values.iterator();
            if (!en.hasNext()) {
                return
                        "";
            } else {
                StringBuilder result = new StringBuilder();
                String first = (String) en.next();
                if (first != null) {
                    result.append(first);
                }
                while (en.hasNext()) {
                    result.append(separator);
                    String next = (String) en.next();
                    if (next != null) {
                        result.append(next);
                    }
                }
                return result.toString();
            }
        }
    }

    public static String capitalize(String value) {
        if (isEmpty(value)) {
            return "";
        } else {
            char[] chars = value.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }

    public static String decapitalize(String value) {
        if (isEmpty(value)) {
            return "";
        } else if (value.length() > 1 && Character.isUpperCase(value.charAt(0)) & Character.isUpperCase(value.charAt(1))) {
            return value;
        } else {
            char[] chars = value.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static String uncapitalize(String str) {
        return org.apache.commons.lang3.StringUtils.uncapitalize(str);
    }

    public static String stripNewLine(String value) {
        if (isEmpty(value)) {
            return value;
        } else {
            StringBuilder builder = new StringBuilder(value.length());
            Iterator var2 = (new CharSequenceIterable(value)).iterator();
            while (var2.hasNext()) {
                char c = (Character) var2.next();
                switch (c) {
                    case '\n':
                    case '\r':
                        break;
                    default:
                        builder.append(c);
                }

            }
            return builder.toString();
        }
    }

}
