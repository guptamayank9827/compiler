public class Name extends Token {
    
    String name;
    Expression expression;

    Name(String name) {
        this.name = name;
        this.expression = null;
    }
    Name(String name, Expression expr) {
        this.name = name;
        this.expression = expr;
    }

    public String toString(int t) {
        return name + (expression == null ? "" : "[" + expression.toString(t) + "]");
    }
}
