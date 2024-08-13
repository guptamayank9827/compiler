public class Expression extends Token {

    String type;
    Expression expression;
    String operator;
    Name name;
    String methodName;
    Arguements arguements;
    String expressionType;
    Expression expression2;
    Expression expression3;
    int int_val;
    float float_val;
    String string_val;
    char char_val;
    boolean bool_val;
    
    Expression() {
        this.expression = null;
        this.type = "OPTIONAL";
    }
    Expression(Expression expr, String op) {
        this.expression = expr;
        this.operator = op;
        this.type = "UNARY";
    }
    Expression (Name name) {
        this.name = name;
        this.type = "NAME";
    }
    Expression(String name) {
        this.methodName = name;
        this.type = "METHOD";
    }
    Expression(String name, Arguements args) {
        this.methodName = name;
        this.arguements = args;
        this.type = "METHOD_ARGS";
    }
    Expression(Expression expr) {
        this.expression = expr;
        this.type = "PARANTHESIS";
    }
    Expression(String type, Expression expr) {
        this.expressionType = type;
        this.expression = expr;
        this.type = "TYPECAST";
    }
    Expression(Expression expr1, Expression expr2, String op) {
        this.expression = expr1;
        this.expression2 = expr2;
        this.operator = op;
        this.type = "BINARY";
    }
    Expression(Expression expr1, Expression expr2, Expression expr3) {
        this.expression = expr1;
        this.expression2 = expr2;
        this.expression3 = expr3;
        this.type = "TERNARY";
    }
    Expression(int i, String literal) {
        this.int_val = i;
        this.type = "INT_LIT";
    }
    Expression(float f, String literal) {
        this.float_val = f;
        this.type = "FLOAT_LIT";
    }
    Expression(char c, String literal) {
        this.char_val = c;
        this.type = "CHAR_LIT";
    }
    Expression(boolean val, String literal) {
        this.bool_val = val;
        this.type = "BOOLEAN_LIT";
    }
    Expression(String val, String literal) {
        this.string_val = val;
        this.type = "STRING_LIT";
    }
    
    public String toString(int t) {

        if(type == null)    return "";

        String text = "";

        switch (type) {
            case "INT_LIT":
                text += "" + int_val;
                break;
            case "FLOAT_LIT":
                text += "" + float_val;
                break;
            case "CHAR_LIT":
                text += "" + char_val;
                break;
            case "BOOLEAN_LIT":
                text += "" + (bool_val ? "true":"false");
                break;
            case "STRING_LIT":
                text += string_val;
                break;
            case "NAME":
                text += name.toString(t);
                break;
            case "METHOD":
                text += methodName + "()";
                break;
            case "METHOD_ARGS":
                text += methodName + "(" + (arguements==null ? "" : arguements.toString(t)) + ")";
                break;
            case "PARANTHESIS":
                text += "(" + expression.toString(t) + ")";
                break;
            case "TYPECAST":
                text += "(" + expressionType + ") " + expression.toString(t);
                break;
            case "UNARY":
                text += operator + expression.toString(t);
                break;
            case "BINARY":
                text += "(" + expression.toString(t) + " " + operator + " " + expression2.toString(t) + ")";
                break;
            case "TERNARY":
                text += "( " + expression.toString(t) + " ? " + expression2.toString(t) + " : " + expression3.toString(t) + " )";
                break;
            case "OPTIONAL":
                text += "";
                break;
            default:
                text = "";
                break;
        }

        return text;
    }
}