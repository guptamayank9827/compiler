import java.util.LinkedList;
import java.util.List;

public class ReadList extends Token {

    private List<Name> names;

    ReadList(Name name) {
        this.names = new LinkedList<Name>();
        this.names.add(name);
    }

    public ReadList prepend(Name name) {
        names.add(0,name);
        return this;
    }

    public String toString(int t) {
        if(names.size() < 1)    return "";

        String text = "";

        for (int i = 0; i < names.size(); i++) {
            if(i > 0)  text += ", ";
            text += names.get(i).toString(t);
        }

        return text;
    }

    public SymbolTable.Type typeCheck() throws LangException {
        if(names == null || names.size() < 1)   return null;

        for (Name name : names) {

            name.typeCheck();
            SymbolTable.Variable variable = symbolTable.getElement(name.name);

            if(variable.isFinal)    throw new LangException("Read isn't applicable on final variable " + variable.name);
            if(variable.isMethod)   throw new LangException("Read isn't applicable on method " + variable.name);
            if(variable.isArray && name.expression==null)    throw new LangException("Read isn't applicable on array " + variable.name);
        }

        return null;
    }

}
