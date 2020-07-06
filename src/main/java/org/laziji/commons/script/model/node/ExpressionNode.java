package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionNode extends BaseNode {

    private List<Node> nodes;
    private List<Operator> operators;

    public ExpressionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {

        this.nodes = new ArrayList<>();
        this.operators = new ArrayList<>();
        parse(0, this.segment.length() - 1);
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException, OperationException {
        Stack<Value> values = new Stack<>();
        Stack<Operator> operators = new Stack<>();
        for (Node node : this.nodes) {
            values.push(node.run(contexts));
        }
        for (Operator operator : this.operators) {
            operators.push(operator);
        }
        while (values.size() > 1) {
            Value a = values.pop();
            Value b = values.pop();
            switch (operators.pop()) {
                case ADD:
                    values.push(a.add(b));
                    break;
                case SUB:
                    values.push(a.subtract(b));
                    break;
                case MUL:
                    values.push(a.multiply(b));
                    break;
                case DIV:
                    values.push(a.divide(b));
                    break;
            }
        }
        return values.pop();
    }

    private void parse(int l, int r) throws CompileException {
        int p = 0;
        for (char[] operators : new char[][]{{'+', '-'}, {'*', '/'}}) {
            for (int i = r; i >= l; i--) {
                char ch = this.segment.charAt(i);
                if (ch == '(') {
                    p++;
                } else if (ch == ')') {
                    p--;
                }
                if (p != 0) {
                    continue;
                }
                for (char operator : operators) {
                    if (ch != operator) {
                        continue;
                    }
                    this.operators.add(Operator.match(operator));
                    this.parse(i + 1, r);
                    this.parse(l, i - 1);
                    return;
                }
            }
        }
        if (this.segment.charAt(l) == '(') {
            parse(l + 1, r - 1);
            return;
        }
        SingleNode node = new SingleNode(this.segment.substring(l, r + 1));
        node.compile();
        this.nodes.add(node);
    }

    private enum Operator {
        ADD, SUB, MUL, DIV;

        public static Operator match(char operator) {
            if (operator == '+') {
                return ADD;
            } else if (operator == '-') {
                return SUB;
            } else if (operator == '*') {
                return MUL;
            } else if (operator == '/') {
                return DIV;
            }
            return null;
        }
    }
}
