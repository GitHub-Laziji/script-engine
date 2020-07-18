# script-engine

Java实现的类JS脚本引擎, 支持语法
- `function` 函数定义
- `let` 变量定义
- `if` 条件控制
- `while` while循环
- `for` for循环
- `return` 函数返回
- `break` 跳出循环
- `print` 打印变量

## 实例
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

for(let i = 1;i < 3;i = i + 1) {
    if(i == 2) {
        break;
    }
    c = c + 10;
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
                "function sum(n) {                \n" +
                        "  if(n == 1) {                   \n" +
                        "    return 1;                    \n" +
                        "  }                              \n" +
                        "  return n + sum(n - 1);         \n" +
                        "}                                \n" +
                        "                                 \n" +
                        "let a = 3;                       \n" +
                        "let b = 4;                       \n" +
                        "let c = a + 3 + sum(b);          \n" +
                        "print a;                         \n" +
                        "print b;                         \n" +
                        "print c;                         \n" +
                        "print a + b + c;                 \n" +
                        "                                 \n" +
                        "while(a > 0) {                   \n" +
                        "  c = c + a;                     \n" +
                        "  a = a - 1;                     \n" +
                        "}                                \n" +
                        "print c;                         \n" +
                        "                                 \n" +
                        "for(let i = 1;i < 3;i = i + 1) { \n" +
                        "  if(i == 2) {                   \n" +
                        "    break;                       \n" +
                        "  }                              \n" +
                        "  c = c + 10;                    \n" +
                        "}                                \n" +
                        "print c;                         \n");
        node.compile();
        node.run(contexts);
    }
}
```
