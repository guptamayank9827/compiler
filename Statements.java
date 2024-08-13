import java.util.LinkedList;
import java.util.List;

public class Statements extends Token {
    private List<Statement> statements;

    Statements() {
        this.statements = new LinkedList<Statement>();
    }

    public Statements prepend(Statement stmt) {
        statements.add(0,stmt);
        return this;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public String toString(int t) {
        if(statements.size() < 1)   return "";

        String text = "";

        for (Statement statement : statements)
            text += statement.toString(t);

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(statements == null || statements.size() < 1) return null;

        for (Statement statement : statements)
            statement.typeCheck();

        return null;
    }
}