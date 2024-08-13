public class MemberDeclarations extends Token {

    private FieldDeclaration fieldDeclaration;
    private MethodDeclaration methodDeclaration;
    private MethodDeclarations methodDeclarations;
    private MemberDeclarations memberDeclarations;

    public MemberDeclarations() {
        this.methodDeclaration = null;
        this.methodDeclarations = null;
        this.fieldDeclaration = null;
        this.memberDeclarations = null;
    }

    public MemberDeclarations(FieldDeclaration fd, MemberDeclarations memds) {
        this.fieldDeclaration = fd;
        this.memberDeclarations = memds;
        this.methodDeclaration = null;
        this.methodDeclarations = null;
    }

    public MemberDeclarations(MethodDeclaration md, MethodDeclarations mds) {
        this.methodDeclaration = md;
        this.methodDeclarations = mds;
        this.fieldDeclaration = null;
        this.memberDeclarations = null;
    }

    public String toString(int t) {
        String text = "";

        if(fieldDeclaration != null) {
            text += fieldDeclaration.toString(t);
            if(memberDeclarations != null)  text += memberDeclarations.toString(t);
        }
        else if(methodDeclaration != null) {
            text += methodDeclaration.toString(t);
            if(methodDeclarations != null)  text += methodDeclarations.toString(t);
        }

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {

        if(fieldDeclaration != null) {
            fieldDeclaration.typeCheck();
            if(memberDeclarations != null)  memberDeclarations.typeCheck();
        }
        else if(methodDeclaration != null) {
            methodDeclaration.typeCheck();
            if(methodDeclarations != null)  methodDeclarations.typeCheck();
        }

        return null;
    }
}
