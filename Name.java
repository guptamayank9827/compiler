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

    public boolean isArray() throws LangException {
        return expression == null ? symbolTable.getElement(name).isArray : false;
    }

    public boolean isFinal() throws LangException {
        return symbolTable.getElement(name).isFinal;
    }

    public String toString(int t) {
        return name + (expression == null ? "" : "[" + expression.toString(t) + "]");
    }

    public SymbolTable.Type typeCheck() throws LangException {

        SymbolTable.Variable variable = symbolTable.getElement(name);

        if(expression != null) {

            if(!variable.isArray)   throw new LangException("Cannot index a non-array variable " + variable.name);

            SymbolTable.Type expressionType = expression.typeCheck();
            if(!expressionType.equals(SymbolTable.Type.INT))  throw new LangException("Cannot index an array with a non-integer index");
        }

        return variable.type;
    }
}
