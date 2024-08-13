public class ArguementDeclaration extends Token {

    String type;
    String argName;
    boolean isArray;

    ArguementDeclaration(String type, String name) {
        this.type = type;
        this.argName = name;
        this.isArray = false;
    }

    ArguementDeclaration(String type, String name, boolean isArray) {
        this.type = type;
        this.argName = name;
        this.isArray = isArray;
    }

    public String toString(int t) {
        String text = type + " " + argName;

        if(isArray) text += "[]";

        return text;
    }
}
