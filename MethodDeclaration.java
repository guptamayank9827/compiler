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

    public String getType() {
        return returnType;
    }

    public String toString(int t) {
        String text = getTabs(t) + returnType + " " + methodName + "(" + argDeclarations.toString(t) + ") {\n";

        if(fieldDeclarations != null)   text += fieldDeclarations.toString(t+1);
        if(statements != null)  text += statements.toString(t+1);

        text += getTabs(t) + "}" + (semicolon != "" ? ";":"") + "\n";

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        symbolTable.addMethod(returnType, methodName, argDeclarations);

        symbolTable.startScope();

        argDeclarations.typeCheck();
        fieldDeclarations.typeCheck();
        statements.typeCheck();

        Statement returnStatement = null;
        for (Statement statement : statements.getStatements()) {
            if(statement.type.equals("RETURN") || statement.type.equals("RETURN_EXPRESSION")) {
                returnStatement = statement;
                break;
            }
        }

        if(returnStatement == null && !symbolTable.stringToEnumType(returnType).equals(SymbolTable.Type.VOID))
            throw new LangException("Missing return statement in method " + methodName);

        if(returnStatement == null) {
            symbolTable.endScope();
            return null;
        }

        if(!symbolTable.coercibleByType(returnStatement.typeCheck(), symbolTable.stringToEnumType(returnType)))
            throw new LangException("Return type " + returnStatement.typeCheck() + " doesn't match method type " + symbolTable.stringToEnumType(returnType));

        symbolTable.endScope();

        return null;
    }
}
