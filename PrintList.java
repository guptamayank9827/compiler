import java.util.LinkedList;
import java.util.List;

public class PrintList extends Token {

    private List<Expression> expressions;

    PrintList(Expression expr) {
        this.expressions = new LinkedList<Expression>();
        this.expressions.add(expr);
    }

    public PrintList prepend(Expression ex) {
        expressions.add(0,ex);
        return this;
    }

    public String toString(int t) {
        if(expressions.size() < 1)    return "";

        String text = "";

        for (int i = 0; i < expressions.size(); i++) {
            if(i > 0)  text += ", ";
            text += expressions.get(i).toString(t);
        }

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(expressions == null || expressions.size() < 1)   return null;

        for (Expression expression : expressions) {
            SymbolTable.Type expressionType = expression.typeCheck();

            if(expressionType.equals(SymbolTable.Type.VOID))
                throw new LangException("Cannot print void type expression");

            if(expression.name != null) {
                SymbolTable.Variable variable = symbolTable.getElement(expression.name.name);
                if(variable.isArray && expression.name.expression==null)
                    throw new LangException("Cannot print array " + variable.name);
            }
        }

        return null;
    }
}
