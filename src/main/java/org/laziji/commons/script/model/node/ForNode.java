package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.context.LoopContext;
import org.laziji.commons.script.model.context.LoopUnitContext;
import org.laziji.commons.script.model.value.Value;
import org.laziji.commons.script.util.TextUtils;

import java.util.List;
import java.util.Stack;

public class ForNode extends BaseNode {

    private VariableDefinitionNode variableDefinitionNode;
    private ExpressionNode expressionNode;
    private AssignmentNode assignmentNode;
    private CombinationNode combinationNode;

    public ForNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        List<String> units = TextUtils.splitUnit(this.segment);
        if (units.size() != 3) {
            throw new CompileException();
        }
        if (!units.get(0).equals("for") || units.get(1).charAt(0) != '(' || units.get(2).charAt(0) != '{') {
            throw new CompileException();
        }
        List<String> segments = TextUtils.splitSegment(TextUtils.trimBrackets(units.get(1)), true, true);
        if (segments.size() != 3) {
            throw new CompileException();
        }
        if (!segments.get(0).isEmpty()) {
            this.variableDefinitionNode = new VariableDefinitionNode(segments.get(0));
            this.variableDefinitionNode.compile();
        }
        if (!segments.get(1).isEmpty()) {
            this.expressionNode = new ExpressionNode(segments.get(1));
            this.expressionNode.compile();
        }
        if (!segments.get(2).isEmpty()) {
            this.assignmentNode = new AssignmentNode(segments.get(2));
            this.assignmentNode.compile();
        }
        this.combinationNode = new CombinationNode(TextUtils.trimBrackets(units.get(2)));
        this.combinationNode.compile();

    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        LoopContext context = new LoopContext();
        contexts.push(context);
        if (this.variableDefinitionNode != null) {
            this.variableDefinitionNode.run(contexts);
        }
        while (this.expressionNode.run(contexts).toBoolean().getValue()) {
            if (context.isClose()) {
                break;
            }
            contexts.push(new LoopUnitContext());
            combinationNode.run(contexts);
            contexts.pop();
            if (this.assignmentNode != null) {
                this.assignmentNode.run(contexts);
            }
        }
        contexts.pop();
        return null;
    }
}
