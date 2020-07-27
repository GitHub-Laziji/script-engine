package org.laziji.commons.script.util;

import org.laziji.commons.script.exception.CompileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TextUtils {

    private static final char[][] BRACKET_PAIRS = {{'(', ')'}, {'[', ']'}, {'{', '}'}};
    private static final char[] SPACES = {' ', '\r', '\n', '\t', '\f'};

    /**
     * 去除两端空格及括号
     *
     * @param text 文本
     * @return 去括号后的文本
     */
    public static String trimBrackets(String text) {
        text = text.trim();
        if (matchBrackets(text.charAt(0), text.charAt(text.length() - 1))) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    /**
     * 获取下一个代码片段索引
     *
     * @param text             文本
     * @param from             开始索引
     * @param strict           是否严格模式, 每个代码段后需要分号
     * @param ignoreTerminator 是否忽略结尾分号
     * @return 代码段
     * @throws CompileException 解析失败
     */
    public static int nextSegmentIndex(String text, int from, boolean strict, boolean ignoreTerminator) throws CompileException {
        Stack<Character> bracketStack = new Stack<>();
        for (int i = from; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (isLeftBracket(ch)) {
                bracketStack.push(ch);
            } else if (isRightBracket(ch)) {
                if (bracketStack.isEmpty() || !matchBrackets(bracketStack.pop(), ch)) {
                    throw new CompileException();
                }
            }
            if (bracketStack.isEmpty()) {
                if (ch == ';' || !strict && ch == '}' || ignoreTerminator && i == text.length() - 1) {
                    return i + 1;
                }
            }
        }
        throw new CompileException();
    }

    /**
     * 分割文本
     *
     * @param text             文本
     * @param strict           是否严格模式, 每个代码段后需要分号
     * @param ignoreTerminator 是否忽略结尾分号
     * @return 代码段
     * @throws CompileException 解析失败
     */
    public static List<String> splitSegment(String text, boolean strict, boolean ignoreTerminator) throws CompileException {
        List<String> segments = new ArrayList<>();
        boolean omit = false;
        int i = 0;
        do {
            int next = nextSegmentIndex(text, i, strict, ignoreTerminator);
            char pre = text.charAt(next - 1);
            if (pre == ';') {
                String segment = text.substring(i, next - 1).trim();
                if (!omit || !segment.isEmpty()) {
                    segments.add(segment);
                }
                omit = false;
            } else if (pre == '}' || next == text.length()) {
                segments.add(text.substring(i, next).trim());
                omit = true;
            }
            i = next;
        } while (i < text.length());
        return segments;
    }

    /**
     * 按最小单元分割文本
     *
     * @param text 文本
     * @return 代码段
     * @throws CompileException 解析失败
     */
    public static List<String> splitUnit(String text) throws CompileException {
        List<String> units = new ArrayList<>();
        int index = 0;
        while (index < text.length()) {
            index = skipSpace(text, index);
            int current = index;
            char currentChar = text.charAt(current);
            if (isLeftBracket(currentChar)) {
                Stack<Character> bracketStack = new Stack<>();
                while (current < text.length()) {
                    char ch = text.charAt(current);
                    if (isLeftBracket(ch)) {
                        bracketStack.push(ch);
                    } else if (isRightBracket(ch) && (bracketStack.isEmpty() || !matchBrackets(bracketStack.pop(), ch))) {
                        throw new CompileException();
                    }
                    current++;
                    if (bracketStack.isEmpty()) {
                        break;
                    }
                }
            } else if (isMark(currentChar)) {
                while (current < text.length() && isMark(text.charAt(current))) {
                    current++;
                }
            } else if (isQuotes(currentChar)) {
                current++;
                while (current < text.length()) {
                    char ch = text.charAt(current);
                    current++;
                    if (ch == '\\') {
                        current++;
                    } else if (ch == currentChar) {
                        break;
                    }
                }
            } else {
                while (current < text.length()) {
                    char ch = text.charAt(current);
                    if (isSpace(ch) || isLeftBracket(ch) || isMark(ch) || isQuotes(ch)) {
                        break;
                    }
                    if (isRightBracket(ch)) {
                        throw new CompileException();
                    }
                    current++;
                }
            }
            units.add(text.substring(index, current));
            index = current;
        }
        return units;
    }

    /**
     * 按给定的字符集分割文本
     *
     * @param text   文本
     * @param tokens 字符集
     * @return 代码段
     */
    public static List<String> split(String text, List<String> tokens) {
        return null;
    }

    private static boolean isLeftBracket(char character) {
        for (char[] pair : BRACKET_PAIRS) {
            if (character == pair[0]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isRightBracket(char character) {
        for (char[] pair : BRACKET_PAIRS) {
            if (character == pair[1]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSpace(char character) {
        for (char space : SPACES) {
            if (character == space) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMark(char character) {
        return character == '_' || character == '$' || character == '.' || character >= '0' && character <= '9'
                || character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z';
    }

    private static boolean isQuotes(char character) {
        return character == '\'' || character == '"';
    }


    private static boolean matchBrackets(char leftBracket, char rightBracket) {
        for (char[] pair : BRACKET_PAIRS) {
            if (leftBracket == pair[0] && rightBracket == pair[1]) {
                return true;
            }
        }
        return false;
    }

    private static int skipSpace(String text, int index) {
        while (index < text.length() && isSpace(text.charAt(index))) {
            index++;
        }
        return index;
    }

    public static void main(String[] args) throws CompileException {
        splitUnit(" function aaa_das123(ddd,ccc) { return aa} +da-'asda\\''*123").forEach(System.out::println);
    }
}
