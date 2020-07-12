# script-engine

Java实现的类JS脚本引擎

```
function add(a, b) {
    return a + b;
};
let a = 3;
let b = 4;
let c = a + 3 + add(a + 1, b * 2); 
```

```java
public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Stack<Context> contexts = new Stack<>();
        contexts.push(new BlockContext());
        CombinationNode node = new CombinationNode(
                "function add(a,b){\n" +
                        "\treturn a+b;\n" +
                        "};\n" +
                        "\n" +
                        "let a=3;\n" +
                        "let b=4;\n" +
                        "let c=a + 3 + add(a+1,b*2);");
        node.compile();
        node.run(contexts);
        System.out.println(contexts.get(0).get("c").toString());
    }
}
```
