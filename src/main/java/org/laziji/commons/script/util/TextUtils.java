package org.laziji.commons.script.util;

import org.laziji.commons.script.exception.CompileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TextUtils {

    private static final char[][] BRACKET_PAIRS = {{'(', ')'}, {'[', ']'}, {'{', '}'}};
    private static final char[] SPACES = {' ', '\r', '\n', '\t', '\f'};

    public static String trimBrackets(String text) {
        text = text.trim();
        if (matchBrackets(text.charAt(0), text.charAt(text.length() - 1))) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    public static List<String> splitSegment(String text) throws CompileException {
        List<String> segments = new ArrayList<>();
        Stack<Character> bracketStack = new Stack<>();
        int start = 0;
        boolean omit = false;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (isLeftBracket(ch)) {
                bracketStack.push(ch);
            } else if (isRightBracket(ch)) {
                if (bracketStack.isEmpty() || !matchBrackets(bracketStack.pop(), ch)) {
                    throw new CompileException();
                }
            }
            if (bracketStack.isEmpty()) {
                if (ch == ';') {
                    String segment = text.substring(start, i).trim();
                    if (!omit || !segment.isEmpty()) {
                        segments.add(segment);
                    }
                    omit = false;
                    start = i + 1;
                } else if (ch == '}' || i == text.length() - 1) {
                    segments.add(text.substring(start, i + 1).trim());
                    omit = true;
                    start = i + 1;
                }
            }
        }
        return segments;
    }

    public static List<String> splitUnit(String text) throws CompileException {
        List<String> units = new ArrayList<>();
        int index = 0;
        while (index < text.length()) {
            index = skipSpace(text, index);
            int current = index;
            if (isLeftBracket(text.charAt(current))) {
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
            } else {
                while (current < text.length()) {
                    char ch = text.charAt(current);
                    if (isSpace(ch) || isLeftBracket(ch)) {
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
        splitUnit("  function aaa(aa,bbb) {  ddd}").forEach(System.out::println);
    }
}
