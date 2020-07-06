package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.OperationException;
import org.laziji.commons.script.exception.RunException;
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
        int p1 = 0, p2 = 0, p3 = 0;
        for (int i = 0; i < this.segment.length(); i++) {
            char ch = segment.charAt(i);
            if (ch == '(') {
                p1++;
            } else if (ch == ')') {
                p1--;
            } else if (ch == '[') {
                p2++;
            } else if (ch == ']') {
                p2--;
            } else if (ch == '{') {
                p3--;
            } else if (ch == '}') {
                p3--;
            }
            if (p1 == 0 && p2 == 0 && p3 == 0 && ch == ';') {
                String s = this.segment.substring(start, i);
                System.out.println(s);
                Node matchNode = null;
                for (Node node : new Node[]{
                        new VariableDefinitionNode(s),
                        new AssignmentNode(s)
                }) {
                    try {
                        node.compile();
                        matchNode = node;
                    } catch (CompileException ignored) {

                    }
                }
                if (matchNode == null) {
                    throw new CompileException();
                }
                nodes.add(matchNode);
                start = i + 1;
            }
        }
    }

    @Override
    public Value run(List<Map<String, Value>> contexts) throws RunException, OperationException {
        for (Node node : this.nodes) {
            node.run(contexts);
        }
        return null;
    }


}
