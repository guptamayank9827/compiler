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
}
