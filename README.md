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

while(a > 0){
    c = c + a;
    a = a - 1;
}
```

```java
public class MainTest {

    @Test
    public void test() throws CompileException, RunException, OperationException {
        Stack<Context> contexts = new Stack<>();
        contexts.push(new BlockContext());
        CombinationNode node = new CombinationNode(
                        "function sum(n) {              \n" +
                        "  if(n == 1) {                 \n" +
                        "    return 1;                  \n" +
                        "  }                            \n" +
                        "  return n + sum(n - 1);       \n" +
                        "}                              \n" +
                        "                               \n" +
                        "let a = 3;                     \n" +
                        "let b = 4;                     \n" +
                        "let c = a + 3 + sum(b);        \n" +
                        "                               \n" +
                        "while(a > 0) {                 \n" +
                        "  c = c + a;                   \n" +
                        "  a = a - 1;                   \n" +
                        "}                              \n");
        node.compile();
        node.run(contexts);
        System.out.println(contexts.get(0).get("c").toString());
    }
}
```
