import java.util.LinkedList;
import java.util.List;

public class Arguements extends Token {

    private List<Expression> arguements;

    Arguements() {
        this.arguements = null;
    }

    Arguements(Expression e) {
        this.arguements = new LinkedList<Expression>();
        this.arguements.add(e);
    }

    public Arguements prepend(Expression expr) {
        arguements.add(0, expr);
        return this;
    }

    public String toString(int t) {
        if(arguements.size() < 1)   return "";

        String text = "";

        for (int i = 0; i < arguements.size(); i++) {
            if(i > 0)  text += ", ";
            text += arguements.get(i).toString(t);
        }

        return text;
    }
}

