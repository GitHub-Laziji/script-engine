# script-engine

Java实现的类JS脚本引擎

```
function sum(n) {
    if(n==1) {
        return 1;
    }
    return n + sum(n - 1);
};
let a = 3;
let b = 4;
let c = a + 3 + sum(b); 
```

```java
public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Stack<Context> contexts = new Stack<>();
        contexts.push(new BlockContext());
        CombinationNode node = new CombinationNode(
                "   function sum (n) {\n" +
                        "   \tif (n==1) {\n" +
                        "   \t\treturn 1;\n" +
                        "   \t}\n" +
                        "   \treturn n+sum(n-1);\n" +
                        "   }\n" +
                        "   let a=3;\n" +
                        "   let b=4;\n" +
                        "   let c=a+3+sum(b);\n");
        node.compile();
        node.run(contexts);
        System.out.println(contexts.get(0).get("c").toString());
    }
}
```
