package org.laziji.commons.script.model.node;

import org.laziji.commons.script.exception.CompileException;
import org.laziji.commons.script.exception.RunException;
import org.laziji.commons.script.model.context.Context;
import org.laziji.commons.script.model.value.Value;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionDefinitionNode extends BaseNode {

    private static final Pattern reg = Pattern.compile("^function\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\((\\s*|\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s(,\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s)*)\\)\\s*\\{(.*)}$");

    private String name;

    public FunctionDefinitionNode(String segment) {
        super(segment);
    }

    @Override
    public void compile() throws CompileException {
        if (!this.segment.startsWith("function ")) {
            throw new CompileException();
        }
        Matcher matcher = reg.matcher(this.segment);
        this.name = matcher.replaceAll("$1");
        String parameter = matcher.replaceAll("$1");
    }

    @Override
    public Value run(Stack<Context> contexts) throws RunException {
        return null;
    }

    public static void main(String[] args) {
        Matcher matcher = reg.matcher("function asd (aa,vv,cc) { return 0;}");
        System.out.println(matcher.replaceAll("$1"));
        System.out.println(matcher.replaceAll("$2"));
        System.out.println(matcher.replaceAll("$3"));
        System.out.println(matcher.replaceAll("$4"));
        System.out.println(matcher.replaceAll("$5"));
    }
}
