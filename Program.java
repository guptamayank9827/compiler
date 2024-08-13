class Program extends Token {

    private String className;
    private MemberDeclarations memberDeclarations;

    public Program(String name, MemberDeclarations memds) {
        this.className = name;
        this.memberDeclarations = memds;
        symbolTable = new SymbolTable();
    }
  
    public String toString(int t) {
        return "class " + className + " {\n" + memberDeclarations.toString(t+1) + "}";
    }

    public SymbolTable.Type typeCheck() throws LangException {

        symbolTable.startScope();
        memberDeclarations.typeCheck();
        symbolTable.endScope();

        System.err.println(symbolTable.toString(0));

        return null;
    }
}