import java.util.LinkedList;
import java.util.List;

public class FieldDeclarations extends Token {

    private List<FieldDeclaration> fieldDeclarations;

    FieldDeclarations() {
        this.fieldDeclarations = new LinkedList<FieldDeclaration>();
    }

    public FieldDeclarations prepend(FieldDeclaration fieldDeclaration) {
        fieldDeclarations.add(0,fieldDeclaration);
        return this;
    }

    public String toString(int t) {

        if(fieldDeclarations.size() < 1)    return "";

        String text = "";

        for (FieldDeclaration fieldDeclaration : fieldDeclarations)
            text += fieldDeclaration.toString(t);

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(fieldDeclarations == null || fieldDeclarations.size() < 1)   return null;

        for (FieldDeclaration fieldDeclaration : fieldDeclarations)
            fieldDeclaration.typeCheck();

        return null;
    }
}
