public class MethodDeclaration extends Token {

    String returnType;
    String methodName;
    ArguementDeclarations argDeclarations;
    FieldDeclarations fieldDeclarations;
    Statements statements;
    String semicolon;

    MethodDeclaration(String returnType, String name, ArguementDeclarations ads, FieldDeclarations fds, Statements stmts, String semicolon) {
        this.returnType = returnType;
        this.methodName = name;
        this.argDeclarations = ads;
        this.fieldDeclarations = fds;
        this.statements = stmts;
        this.semicolon = semicolon;
    }

    public String toString(int t) {
        String text = getTabs(t) + returnType + " " + methodName + "(" + argDeclarations.toString(t) + ") {\n";

        if(fieldDeclarations != null)   text += fieldDeclarations.toString(t+1);
        if(statements != null)  text += statements.toString(t+1);

        text += getTabs(t) + "}" + (semicolon != "" ? ";":"") + "\n";

        return text;
    }
}
