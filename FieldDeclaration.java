public class FieldDeclaration extends Token {

    boolean isFinal;
    String type;
    String id;
    int int_lit;
    Expression expression;

    FieldDeclaration(boolean isFinal, String type, String id) {
        this.isFinal = isFinal;
        this.type = type;
        this.id = id;
        this.expression = null;
        this.int_lit = 0;
    }

    FieldDeclaration(boolean isFinal, String type, String id, Expression expr) {
        this.isFinal = isFinal;
        this.type = type;
        this.id = id;
        this.expression = expr;
        this.int_lit = 0;
    }

    FieldDeclaration(String type, String id, Expression expr) {
        this.isFinal = false;
        this.type = type;
        this.id = id;
        this.expression = expr;
        this.int_lit = 0;
    }

    FieldDeclaration(String type, String id, int int_lit) {
        this.isFinal = false;
        this.type = type;
        this.id = id;
        this.expression = null;
        this.int_lit = int_lit;
    }

    public String toString(int t) {
        String text = getTabs(t) + (isFinal ? "final ":"") + type + " " + id;

        if(int_lit > 0) text += "[" + int_lit + "]";
        if(expression != null)    text += " = " + expression.toString(t);

        text += ";\n";

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        symbolTable.addVariable(type, id, isFinal, int_lit>0?true:false);

        if(expression == null)  return null;

        SymbolTable.Type expressionType = expression.typeCheck();
        if(!symbolTable.coercibleByType(expressionType, symbolTable.stringToEnumType(type)))
            throw new LangException("Cannot assign " + expressionType + " to " + symbolTable.stringToEnumType(type));

        return null;
    }
}