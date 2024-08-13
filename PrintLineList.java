public class PrintLineList extends Token {

    private PrintList printList;

    PrintLineList() {
        this.printList = null;
    }

    PrintLineList(PrintList list) {
        this.printList = list;
    }

    public String toString(int t) {
        return (printList == null ? "" : printList.toString(t));
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(printList == null)   return null;

        return printList.typeCheck();
    }
}
