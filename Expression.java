import java.util.LinkedList;

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

    public SymbolTable.Type typeCheck() throws LangException {
        SymbolTable.Type returnType = null;
        SymbolTable.Type expression1Type = null;
        SymbolTable.Type expression2Type = null;
        SymbolTable.Type expression3Type = null;
        SymbolTable.Variable method = null;

        switch (type) {
            case "INT_LIT":
                returnType = SymbolTable.Type.INT;
                break;

            case "FLOAT_LIT":
                returnType = SymbolTable.Type.FLOAT;
                break;

            case "CHAR_LIT":
                returnType = SymbolTable.Type.CHAR;
                break;

            case "BOOLEAN_LIT":
                returnType = SymbolTable.Type.BOOL;
                break;

            case "STRING_LIT":
                returnType = SymbolTable.Type.STRING;
                break;

            case "NAME":
                returnType = name.typeCheck();
                break;

            case "METHOD":
                method = symbolTable.getElement(methodName);

                if(method.arguements.size() > 0)    throw new LangException("Non-matching number of arguements with method " + methodName);

                returnType = method.type;
                break;

            case "METHOD_ARGS":
                method = symbolTable.getElement(methodName);

                arguements.typeCheck();
                if(method.arguements.size() != arguements.getArguements().size())
                    throw new LangException("Non-matching number of arguements with method " + methodName);

                //compare args with method arg declarations
                LinkedList<SymbolTable.Arguement> methodArgs = method.arguements;
                for (int i = 0; i < methodArgs.size(); i++) {
                    SymbolTable.Arguement methodArg = methodArgs.get(i);
                    Expression arg = arguements.getArguements().get(i);

                    if(!symbolTable.coercibleByType(arg.typeCheck(), methodArg.type))
                        throw new LangException("Incompatible arguement " + methodArg.name);
                }

                returnType = method.type;
                break;

            case "PARANTHESIS":
                returnType = expression.typeCheck();
                break;

            case "TYPECAST":
                expression1Type = expression.typeCheck();

                if(!symbolTable.coercibleByType(expression1Type, symbolTable.stringToEnumType(expressionType)))
                    throw new LangException("cannot typecast " + expression1Type + " to " + expressionType);

                returnType = symbolTable.stringToEnumType(expressionType);
                break;

            case "UNARY":
                expression1Type = expression.typeCheck();
                if(operator.equals("~")) {
                    if(!symbolTable.coercibleByType(expression1Type, SymbolTable.Type.BOOL))
                        throw new LangException("Cannot negate a non-boolean");

                    returnType = SymbolTable.Type.BOOL;
                }
                else if(operator.equals("+") || operator.equals("-")) {
                    if(!(expression1Type.equals(SymbolTable.Type.INT) || expression1Type.equals(SymbolTable.Type.FLOAT)))
                        throw new LangException("Cannot unary plus/minus on a non-number");

                    returnType = expression1Type;   //int or float
                }
                break;

            case "BINARY":
                expression1Type = expression.typeCheck();
                expression2Type = expression2.typeCheck();

                //boolean operators
                if(operator.equals("||") || operator.equals("&&")) {

                    if(!symbolTable.coercibleByType(expression1Type, SymbolTable.Type.BOOL))    throw new LangException("Left Expression " + expression1Type + " is not boolean");
                    if(!symbolTable.coercibleByType(expression2Type, SymbolTable.Type.BOOL))    throw new LangException("Right Expression " + expression2Type + " is not boolean");

                    returnType = SymbolTable.Type.BOOL;
                }
                //logical operators
                else if(operator.equals("==") || operator.equals("<>") || operator.equals("<") || operator.equals(">") || operator.equals("<=") || operator.equals(">=")) {

                    if(!(expression1Type.equals(SymbolTable.Type.INT) || expression1Type.equals(SymbolTable.Type.FLOAT)))
                        throw new LangException("Left Expression " + expression1Type + " is not a number");
                    if(!(expression2Type.equals(SymbolTable.Type.INT) || expression2Type.equals(SymbolTable.Type.FLOAT)))
                        throw new LangException("Right Expression " + expression2Type + " is not a number");

                    returnType = SymbolTable.Type.BOOL;
                }
                //mathematical operators
                else if(operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {                    

                    if(expression.name != null) {
                        SymbolTable.Variable expression1Var = symbolTable.getElement(expression.name.name);
                        if(expression1Var.isArray && expression.name.expression==null)
                            throw new LangException("Cannot binary operate on array variable " + expression.name.name);
                    }
                    if(expression2.name != null) {
                        SymbolTable.Variable expression2Var = symbolTable.getElement(expression2.name.name);
                        if(expression2Var.isArray && expression2.name.expression==null)
                            throw new LangException("Cannot binary operate on array variable " + expression2.name.name);
                    }

                    //string concatenate
                    if(operator.equals("+") && (expression1Type.equals(SymbolTable.Type.STRING) || expression2Type.equals(SymbolTable.Type.STRING))) {
                        returnType = SymbolTable.Type.STRING;
                        break;
                    }

                    if(!(expression1Type.equals(SymbolTable.Type.INT) || expression1Type.equals(SymbolTable.Type.FLOAT)))
                        throw new LangException("Left Expression " + expression1Type + " is not a number");
                    if(!(expression2Type.equals(SymbolTable.Type.INT) || expression2Type.equals(SymbolTable.Type.FLOAT)))
                        throw new LangException("Right Expression " + expression2Type + " is not a number");

                    returnType = expression1Type.equals(SymbolTable.Type.FLOAT) || expression2Type.equals(SymbolTable.Type.FLOAT) ? SymbolTable.Type.FLOAT : SymbolTable.Type.INT;
                }

                break;

            case "TERNARY":
                expression1Type = expression.typeCheck();
                if(!symbolTable.coercibleByType(expression1Type, SymbolTable.Type.BOOL))
                    throw new LangException("Ternary Condition " + expression1Type + " is not a boolean");
                
                expression2Type = expression2.typeCheck();
                expression3Type = expression3.typeCheck();

                if(!expression2Type.equals(expression3Type))
                    throw new LangException("Ternary Expressions " + expression2Type + " and " + expression3Type + " are not of same type");

                returnType = expression2Type;
                break;

            case "OPTIONAL":
                returnType = null;
                break;

            default:
                break;
        }

        return returnType;
    }
}