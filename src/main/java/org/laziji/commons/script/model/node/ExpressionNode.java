package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        Stack<Value> values = new Stack<>();
        Stack<Operator> operators = new Stack<>();
        for (Node node : this.nodes) {
            values.push(node.run(contexts));
        }
        for (Operator operator : this.operators) {
            operators.push(operator);
        }
        while (values.size() > 1) {
            Value a = values.pop().copy();
            Value b = values.pop().copy();
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
                case GT:
                    values.push(a.greater(b));
                    break;
                case GTEQ:
                    values.push(a.greaterOrEqual(b));
                    break;
                case SL:
                    values.push(a.smaller(b));
                    break;
                case SLEQ:
                    values.push(a.smallerOrEqual(b));
                    break;
                case EQ:
                    values.push(a.equal(b));
                    break;
            }
        }
        return values.pop();
    }

    private void parse(int l, int r) throws CompileException {
        int p = 0;
        for (String[] operators : new String[][]{{"||"}, {"&&"}, {">", ">=", "<", "<="}, {"+", "-"}, {"*", "/"}}) {
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
                StringBuilder op = new StringBuilder();
                int ii = i;
                while (ii >= l) {
                    char cc = this.segment.charAt(ii);
                    if (cc != '+' && cc != '-' && cc != '*' && cc != '/' && cc != '>' && cc != '<' && cc != '=') {
                        break;
                    }
                    op.append(cc);
                    ii--;
                }
                if (op.length() > 0) {
                    String cop = op.reverse().toString();
                    for (String operator : operators) {
                        if (!cop.equals(operator)) {
                            continue;
                        }
                        this.operators.add(Operator.match(operator));
                        this.parse(i + 1, r);
                        this.parse(l, i - 1);
                        return;
                    }
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
        ADD("+"), SUB("-"), MUL("*"), DIV("/"),
        GT(">"), GTEQ(">="), SL("<"), SLEQ("<="), EQ("=="),
        AND("&&"), OR("||");

        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public static Operator match(String operator) {
            for (Operator op : Operator.values()) {
                if (Objects.equals(op.operator, operator)) {
                    return op;
                }
            }
            return null;
        }
    }
}
