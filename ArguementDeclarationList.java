import java.util.LinkedList;
import java.util.List;

public class ArguementDeclarationList extends Token {

    private List<ArguementDeclaration> arguementDeclarations;

    ArguementDeclarationList() {
        this.arguementDeclarations = new LinkedList<ArguementDeclaration>();
    }

    ArguementDeclarationList(ArguementDeclaration arguementDeclaration) {
        this.arguementDeclarations = new LinkedList<ArguementDeclaration>();
        this.arguementDeclarations.add(arguementDeclaration);
    }

    public ArguementDeclarationList prepend(ArguementDeclaration ad) {
        arguementDeclarations.add(0,ad);
        return this;
    }

    public String toString(int t) {

        if(arguementDeclarations.size() < 1)    return "";

        String text = "";

        for (int i = 0; i < arguementDeclarations.size(); i++) {
            if(i > 0)  text += ", ";
            text += arguementDeclarations.get(i).toString(t);
        }

        return text;
    }
}
