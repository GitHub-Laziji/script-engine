package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.*;

public class CombinationNode extends BaseNode {

    private List<Node> nodes;

    public CombinationNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        this.nodes = new ArrayList<>();
        int start = 0;
        Stack<Character> brackets = new Stack<>();
        Map<Character, Character> bracketsMatchMap = new HashMap<>();
        bracketsMatchMap.put(')', '(');
        bracketsMatchMap.put(']', '[');
        bracketsMatchMap.put('}', '{');
        for (int i = 0; i < this.segment.length(); i++) {
            char ch = segment.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                brackets.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (brackets.isEmpty() || !bracketsMatchMap.get(ch).equals(brackets.pop())) {
                    throw new CompileException();
                }
            }
            if (brackets.isEmpty() && (ch == ';' || ch == '}')) {
                String s = this.segment.substring(start, ch == ';' ? i : (i + 1)).trim();
                start = i + 1;
                if (s.isEmpty()) {
                    continue;
                }
                Node matchNode = null;
                for (Node node : new Node[]{
                        new FunctionDefinitionNode(s),
                        new VariableDefinitionNode(s),
                        new AssignmentNode(s),
                        new IfNode(s),
                        new WhileNode(s),
                        new ReturnNode(s),
                        new PrintNode(s)
                }) {
                    try {
                        node.compile();
                        matchNode = node;
                        break;
                    } catch (CompileException ignored) {

                    }
                }
                if (matchNode == null) {
                    System.out.println(s);
                    throw new CompileException();
                }
                nodes.add(matchNode);

            }
        }
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException, OperationException {
        for (Node node : this.nodes) {
            if (contexts.peek().isClose()) {
                return null;
            }
            node.run(contexts);
        }
        return null;
    }


}
