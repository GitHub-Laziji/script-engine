package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.FunctionValue;
import org.laziji.commons.script.model.value.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionDefinitionNode extends BaseNode {

    private static final Pattern reg = Pattern.compile("^function\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(((\\s*)|(\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s*(,\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s*)*))\\)\\s*\\{([\\s\\S]*)}$");

    private String name;
    private List<String> parameterNames;
    private CombinationNode combinationNode;

    public FunctionDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!this.segment.startsWith("function ")) {
            throw new CompileException();
        }
        Matcher matcher = reg.matcher(this.segment);
        if (!matcher.matches()) {
            throw new CompileException();
        }
        this.name = matcher.replaceAll("$1");
        String parameter = matcher.replaceAll("$2").trim();
        if (parameter.isEmpty()) {
            this.parameterNames = new ArrayList<>();
        } else {
            this.parameterNames = Arrays.asList(parameter.split("\\s*,\\s*"));
        }
        this.combinationNode = new CombinationNode(matcher.replaceAll("$6"));
        this.combinationNode.compile();
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException {
        Context context = contexts.peek();
        if (context.get(this.name) != null) {
            throw new RunException();
        }
        FunctionValue functionValue = new FunctionValue(this.combinationNode, this.parameterNames);
        context.put(this.name, new FunctionValue(this.combinationNode, this.parameterNames));
        return functionValue;
    }
}
