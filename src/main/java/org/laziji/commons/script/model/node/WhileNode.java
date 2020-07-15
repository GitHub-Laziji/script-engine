package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.BlockContext;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.LoopContext;
import org.laziji.commons.script.model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhileNode extends BaseNode {

    private static final Pattern preReg = Pattern.compile("^\\s*while\\s*\\([\\s\\S]+$");
    private static final Pattern bodyReg = Pattern.compile("^\\s*\\{([\\s\\S]*)}\\s*$");

    private ExpressionNode expressionNode;
    private CombinationNode combinationNode;

    public WhileNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!preReg.matcher(this.segment).matches()) {
            throw new CompileException();
        }
        int start = this.segment.indexOf('(');
        if (start == -1) {
            throw new CompileException();
        }
        int end = -1;
        Stack<Character> brackets = new Stack<>();
        Map<Character, Character> bracketsMatchMap = new HashMap<>();
        bracketsMatchMap.put(')', '(');
        bracketsMatchMap.put(']', '[');
        bracketsMatchMap.put('}', '{');
        for (int i = start; i < this.segment.length(); i++) {
            char ch = segment.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                brackets.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (brackets.isEmpty() || !bracketsMatchMap.get(ch).equals(brackets.pop())) {
                    throw new CompileException();
                }
            }
            if (brackets.isEmpty()) {
                end = i;
                break;
            }
        }
        if (end == -1) {
            throw new CompileException();
        }

        this.expressionNode = new ExpressionNode(this.segment.substring(start + 1, end));
        this.expressionNode.compile();

        String body = this.segment.substring(end + 1);
        Matcher matcher = bodyReg.matcher(body);
        if (!matcher.matches()) {
            throw new CompileException();
        }
        this.combinationNode = new CombinationNode(matcher.replaceAll("$1"));
        this.combinationNode.compile();
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        LoopContext context = new LoopContext();
        contexts.push(context);
        while (this.expressionNode.run(contexts).toBoolean().getValue()) {
            if (context.isClose()) {
                break;
            }
            contexts.push(new BlockContext());
            combinationNode.run(contexts);
            contexts.pop();
        }
        contexts.pop();
        return null;
    }
}
