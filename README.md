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
print a;
print b;
print c;
print a + b + c;

while(a > 0){
    c = c + a;
    a = a - 1;
}
print c;
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
                        "print a;                       \n" +
                        "print b;                       \n" +
                        "print c;                       \n" +
                        "print a + b + c;               \n" +
                        "                               \n" +
                        "while(a > 0) {                 \n" +
                        "  c = c + a;                   \n" +
                        "  a = a - 1;                   \n" +
                        "}                              \n" +
                        "print c;                       \n");
        node.compile();
        node.run(contexts);
    }
}
```
