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
}
