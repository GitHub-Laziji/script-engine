package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.UndefinedValue;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableDefinitionNode extends BaseNode {

    private static final Pattern reg = Pattern.compile("^\\s*let\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*(=\\s*([^\\s][\\s\\S]*))?$");

    private String name;
    private ExpressionNode expressionNode;

    public VariableDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        Matcher matcher = reg.matcher(this.segment);
        if (!matcher.matches()) {
            throw new CompileException();
        }
        this.name = matcher.replaceAll("$1");
        String expression = matcher.replaceAll("$3").trim();
        if (!expression.isEmpty()) {
            this.expressionNode = new ExpressionNode(expression);
            this.expressionNode.compile();
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        Context context = contexts.peek();
        if (context.get(this.name) != null) {
            throw new RunException();
        }
        Value value = this.expressionNode == null ? UndefinedValue.create() : this.expressionNode.run(contexts);
        context.put(name, value);
        return value;
    }
}
