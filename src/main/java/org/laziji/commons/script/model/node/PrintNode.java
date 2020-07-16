package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrintNode extends BaseNode {

    private static final Pattern reg = Pattern.compile("^\\s*print\\s+([^\\s][\\s\\S]*)$");

    private ExpressionNode expressionNode;

    public PrintNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        Matcher matcher = reg.matcher(this.segment);
        if (!matcher.matches()) {
            throw new CompileException();
        }
        this.expressionNode = new ExpressionNode(matcher.replaceAll("$1"));
        this.expressionNode.compile();
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        System.out.println(String.format(
                "%s = %s",
                this.expressionNode.getSegment(),
                this.expressionNode.run(contexts).toString()
        ));
        return null;
    }
}
