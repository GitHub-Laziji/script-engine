package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.BlockContext;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.LoopContext;
import org.laziji.commons.script.model.value.Value;
import org.laziji.commons.script.util.TextUtils;

import java.util.HashMap;
import java.util.List;
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
        List<String> units = TextUtils.splitUnit(this.segment);
        if (units.size() != 3) {
            throw new CompileException();
        }
        if (!units.get(0).equals("while") || units.get(1).charAt(0) != '(' || units.get(2).charAt(0) != '{') {
            throw new CompileException();
        }
        this.expressionNode = new ExpressionNode(TextUtils.trimBrackets(units.get(1)));
        this.expressionNode.compile();
        this.combinationNode = new CombinationNode(TextUtils.trimBrackets(units.get(2)));
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
