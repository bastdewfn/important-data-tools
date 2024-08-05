package com.dewfn.busstation.importantdata.security;

public class DesensitizeAdapter implements DesensitizeHandler {
    public DesensitizeAdapter() {
    }

    public String doMask(String fieldValue) {
        throw new RuntimeException();
    }

    public Long doMask() {
        return 0L;
    }

    protected String maskCardNumber(String idCardNum, int front, int end) {
        if (!this.isBlank(idCardNum) && front + end > idCardNum.length()) {
            return front >= 0 && end >= 0 ? this.hide(idCardNum, front, idCardNum.length() - end) : "";
        } else {
            return "";
        }
    }

    protected String hide(CharSequence str, int startInclude, int endExclude) {
        return this.replace(str, startInclude, endExclude, '*');
    }

    protected String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (this.isEmpty(str)) {
            return this.str(str);
        } else {
            int strLength = str.length();
            if (startInclude > strLength) {
                return this.str(str);
            } else {
                if (endExclude > strLength) {
                    endExclude = strLength;
                }

                if (startInclude > endExclude) {
                    return this.str(str);
                } else {
                    char[] chars = new char[strLength];

                    for(int i = 0; i < strLength; ++i) {
                        if (i >= startInclude && i < endExclude) {
                            chars[i] = replacedChar;
                        } else {
                            chars[i] = str.charAt(i);
                        }
                    }

                    return new String(chars);
                }
            }
        }
    }

    protected int indexOf(CharSequence seq, int searchChar) {
        return this.isEmpty(seq) ? -1 : this.indexOf(seq, searchChar, 0);
    }

    private int indexOf(final CharSequence cs, final int searchChar, int start) {
        if (cs instanceof String) {
            return ((String)cs).indexOf(searchChar, start);
        } else {
            int sz = cs.length();
            if (start < 0) {
                start = 0;
            }

            for(int i = start; i < sz; ++i) {
                if (cs.charAt(i) == searchChar) {
                    return i;
                }
            }

            return -1;
        }
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    protected String str(CharSequence cs) {
        return cs == null ? null : cs.toString();
    }

    protected String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        } else {
            char[] result = new char[count];

            for(int i = 0; i < count; ++i) {
                result[i] = c;
            }

            return new String(result);
        }
    }

    protected boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public DesensitizeType getFieldType() {
        throw new RuntimeException();
    }
}
