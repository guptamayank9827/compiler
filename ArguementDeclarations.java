public class ArguementDeclarations extends Token {

    private ArguementDeclarationList arguementDeclarationList;

    ArguementDeclarations() {
        this.arguementDeclarationList = null;
    }

    ArguementDeclarations(ArguementDeclarationList arguementDeclarationList) {
        this.arguementDeclarationList = arguementDeclarationList;
    }

    public String toString(int t) {
        return (arguementDeclarationList == null ? "" : this.arguementDeclarationList.toString(t));
    }
}
