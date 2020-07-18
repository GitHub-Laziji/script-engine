package org.laziji.commons.script.util;

import org.laziji.commons.script.exception.CompileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TextUtils {

    private static final char[][] BRACKET_PAIRS = {{'(', ')'}, {'[', ']'}, {'{', '}'}};

    public static boolean isLeftBracket(char character) {
        for (char[] pair : BRACKET_PAIRS) {
            if (character == pair[0]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRightBracket(char character) {
        for (char[] pair : BRACKET_PAIRS) {
            if (character == pair[1]) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchBrackets(char leftBracket, char rightBracket) {
        for (char[] pair : BRACKET_PAIRS) {
            if (leftBracket == pair[0] && rightBracket == pair[1]) {
                return true;
            }
        }
        return false;
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
                } else if (ch == '}') {
                    segments.add(text.substring(start, i + 1).trim());
                    omit = true;
                    start = i + 1;
                }
            }
        }
        return segments;
    }

    public static List<String> splitUnit(String text) {
        return null;
    }
}
