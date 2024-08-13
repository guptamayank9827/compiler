import java.util.LinkedList;
import java.util.List;

public class MethodDeclarations extends Token {

    private List<MethodDeclaration> methodDeclarations;

    MethodDeclarations() {
        this.methodDeclarations = new LinkedList<MethodDeclaration>();
    }

    public MethodDeclarations prepend(MethodDeclaration methodDeclaration) {
        methodDeclarations.add(0, methodDeclaration);
        return this;
    }

    public String toString(int t) {
        if(methodDeclarations.size() < 1)   return "";

        String text = "";

        for (MethodDeclaration methodDeclaration : methodDeclarations)
            text += methodDeclaration.toString(t);

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(methodDeclarations == null || methodDeclarations.size() < 1)   return null;

        for (MethodDeclaration methodDeclaration : methodDeclarations)
            methodDeclaration.typeCheck();

        return null;
    }
}
