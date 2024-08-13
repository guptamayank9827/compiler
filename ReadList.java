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
}
