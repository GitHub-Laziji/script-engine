package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.FunctionValue;
import org.laziji.commons.script.model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionNode extends BaseNode {

    private static final Pattern reg = Pattern.compile("^\\s*([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(([\\s\\S]*)\\)\\s*$");

    private String name;
    private List<Node> nodes;

    public FunctionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        Matcher matcher = reg.matcher(this.segment);
        if (!matcher.matches()) {
            throw new CompileException();
        }
        this.name = matcher.replaceAll("$1");
        String expressions = matcher.replaceAll("$2").trim();
        this.nodes = new ArrayList<>();
        if (!expressions.isEmpty()) {
            expressions += ",";
            int start = 0;
            int p = 0;
            for (int i = 0; i < expressions.length(); i++) {
                char ch = expressions.charAt(i);
                if (ch == '(') {
                    p++;
                } else if (ch == ')') {
                    p--;
                }
                if (p == 0 && ch == ',') {
                    ExpressionNode node = new ExpressionNode(expressions.substring(start, i));
                    node.compile();
                    this.nodes.add(node);
                    start = i + 1;
                }
            }
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        Value value = null;
        for (int i = contexts.size() - 1; i >= 0; i--) {
            Context context = contexts.get(i);
            value = context.get(name);
            if (value != null) {
                break;
            }
        }
        if (value instanceof FunctionValue) {
            List<Value> parameters = new ArrayList<>();
            for (Node node : this.nodes) {
                parameters.add(node.run(contexts));
            }
            return ((FunctionValue) value).call(contexts, parameters);
        }
        throw new RunException();
    }
}
