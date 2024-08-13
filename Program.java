class Program extends Token {

    private String className;
    private MemberDeclarations memberDeclarations;

    public Program(String name, MemberDeclarations memds) {
        this.className = name;
        this.memberDeclarations = memds;
    }
  
    public String toString(int t) {
        return "class " + className + " {\n" + memberDeclarations.toString(t+1) + "}";
    }
}
  