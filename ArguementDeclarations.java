public class ArguementDeclarations extends Token {

    private ArguementDeclarationList arguementDeclarationList;

    ArguementDeclarations() {
        this.arguementDeclarationList = null;
    }

    ArguementDeclarations(ArguementDeclarationList arguementDeclarationList) {
        this.arguementDeclarationList = arguementDeclarationList;
    }

    public ArguementDeclarationList getList() {
        return arguementDeclarationList;
    }

    public int getSize() {
        if(arguementDeclarationList == null)    return 0;

        return arguementDeclarationList.getSize();
    }

    public String toString(int t) {
        return (arguementDeclarationList == null ? "" : this.arguementDeclarationList.toString(t));
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(arguementDeclarationList == null || arguementDeclarationList.getSize() < 1)  return null;

        return arguementDeclarationList.typeCheck();
    }

}