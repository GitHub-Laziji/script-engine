package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.BlockContext;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfNode extends BaseNode {

    private static final Pattern preReg = Pattern.compile("^\\s*if\\s+\\([\\s\\S]+$");
    private static final Pattern bodyReg = Pattern.compile("^\\s*\\{([\\s\\S]*)}\\s*$");

    private ExpressionNode expressionNode;
    private CombinationNode combinationNode;

    public IfNode(String segment) {
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
        int p = 0;
        for (int i = start; i < this.segment.length(); i++) {
            char ch = this.segment.charAt(i);
            if (ch == '(') {
                p++;
            }
            if (ch == ')') {
                p--;
            }
            if (p == 0) {
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
        if (this.expressionNode.run(contexts).toBoolean().getValue()) {
            contexts.push(new BlockContext());
            combinationNode.run(contexts);
            contexts.pop();
        }
        return null;
    }
}
