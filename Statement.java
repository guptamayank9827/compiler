public class Statement extends Token {

    String type;
    Expression expression;
    Statement statement;
    ReadList readList;
    PrintList printList;
    PrintLineList printLineList;
    FieldDeclarations fieldDeclarations;
    Statements statements;
    Name name;
    String unaryOperator;
    String methodName;
    Arguements arguements;
    Statement elseStatement;
    String semicolon;

    Statement() {
        this.type = "RETURN";
    }
    Statement(Expression expr) {
        this.expression = expr;
        this.type = "RETURN_EXPRESSION";
    }
    Statement(Expression expr, Statement stmt) {
        this.expression = expr;
        this.statement = stmt;
        this.type = "WHILE";
    }
    Statement(ReadList rList) {
        this.readList = rList;
        this.type = "READ";
    }
    Statement(PrintList pList) {
        this.printList = pList;
        this.type = "PRINT";
    }
    Statement(PrintLineList pLineList) {
        this.printLineList = pLineList;
        this.type = "PRINTLINE";
    }
    Statement(FieldDeclarations fds, Statements stmts, String optSemi) {
        this.fieldDeclarations = fds;
        this.statements = stmts;
        this.semicolon = optSemi;
        this.type = "BLOCK";
    }
    Statement(Name name, Expression expr) {
        this.name = name;
        this.expression = expr;
        this.type = "ASSIGN";
    }
    Statement(Name name, String op) {
        this.name = name;
        this.unaryOperator = op;
        this.type = "UNARY";
    }
    Statement(String name) {
        this.methodName = name;
        this.type = "METHOD";
    }
    Statement(String name, Arguements args) {
        this.methodName = name;
        this.arguements = args;
        this.type = "METHOD_ARGS";
    }
    Statement(Expression expr, Statement stmt, Statement elseStmt) {
        this.expression = expr;
        this.statement = stmt;
        this.elseStatement = elseStmt;
        this.type = "IF";
    }

    public String toString(int t) {
        String text = getTabs(t);

        switch (type) {
            case "RETURN":
                text += "return;";
                break;
            case "RETURN_EXPRESSION":
                text += "return " + expression.toString(t) + ";";
                break;
            case "WHILE":
                text += "while(" + expression.toString(t) + ")\n" + getTabs(1) + statement.toString(t);
                break;
            case "READ":
                text += "read(" + (readList==null ? "" : readList.toString(t)) + ");";
                break;
            case "PRINT":
                text += "print(" + (printList==null ? "" : printList.toString(t)) + ");";
                break;
            case "PRINTLINE":
                text += "printline(" + (printLineList==null ? "" : printLineList.toString(t)) + ");";
                break;
            case "BLOCK":
                text += "{\n" + (fieldDeclarations==null ? "" : fieldDeclarations.toString(t+1)) + (statements==null ? "" : statements.toString(t+1)) + getTabs(t) + "}" + semicolon;
                break;
            case "ASSIGN":
                text += name.toString(t) + " = " + expression.toString(t) + ";";
                break;
            case "UNARY":
                text += name.toString(t) + unaryOperator + ";";
                break;
            case "METHOD":
                text += methodName + "();";
                break;
            case "METHOD_ARGS":
                text += methodName + "(" + (arguements==null ? "" : arguements.toString(t)) + ");";
                break;
            case "IF":
                text += "if(" + expression.toString(t) + ")\n";

                if(statement.type != "BLOCK")   text += getTabs(1);
                text += statement.toString(t);

                if(elseStatement != null) {
                    text += getTabs(t) + "else \n";
                    if(elseStatement.type != "BLOCK")   text += getTabs(1);
                    text += elseStatement.toString(t);
                }

                break;
            default:
                break;
        }

        text += (type == "IF" || type == "WHILE" ? "" : "\n");

        return text;
    }
}
