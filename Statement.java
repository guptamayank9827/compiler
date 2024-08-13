import java.util.LinkedList;

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
                text += "while(" + expression.toString(t) + ")\n";
                if(statement.type != "BLOCK")   text += getTabs(1);
                text += statement.toString(t);
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

    public SymbolTable.Type typeCheck() throws LangException {
        SymbolTable.Type expressionType = null;
        SymbolTable.Type nameType = null;
        SymbolTable.Variable variable = null;
        SymbolTable.Variable method = null;

        switch (type) {
            case "RETURN":
                return SymbolTable.Type.VOID;

            case "RETURN_EXPRESSION":
                return expression.typeCheck();

            case "WHILE":
                expressionType = expression.typeCheck();
                if(!symbolTable.coercibleByType(expressionType, SymbolTable.Type.BOOL))
                    throw new LangException("While Condition " + expressionType + " is not a BOOL");

                statement.typeCheck();
                break;

            case "READ":
                readList.typeCheck();
                break;

            case "PRINT":
                printList.typeCheck();
                break;

            case "PRINTLINE":
                printLineList.typeCheck();
                break;

            case "BLOCK":
                symbolTable.startScope();

                if(fieldDeclarations != null) fieldDeclarations.typeCheck();
                if(statements != null)  statements.typeCheck();

                symbolTable.endScope();
                break;

            case "ASSIGN":
                expressionType = expression.typeCheck();
                nameType = name.typeCheck();

                if(name.isFinal())  throw new LangException("Cannot reassign a final variable " + name.name);

                if(!symbolTable.coercibleByType(expressionType, nameType))
                    throw new LangException("Cannot assign a " + expressionType + " to " + nameType);

                if(expression.name != null) {
                    SymbolTable.Variable expressionVar = symbolTable.getElement(expression.name.name);
                    if(expressionVar.isArray && expression.name.expression==null)
                        throw new LangException("RHS of assignment cannot be an array: " + expression.name.name);
                }
                if(name != null) {
                    SymbolTable.Variable nameVar = symbolTable.getElement(name.name);
                    if(nameVar.isArray && name.expression==null)
                        throw new LangException("LHS of assignment cannot be an array: " + name.name);
                }

                break;

            case "UNARY":
                variable = symbolTable.getElement(name.name);
                if(variable.isFinal)
                    throw new LangException("Cannot modify final variable " + name.name);

                nameType = name.typeCheck();
                if(name.isArray())  throw new LangException("Cannot apply a Unary operator on array " + name.name);

                if(!(nameType.equals(SymbolTable.Type.INT) || nameType.equals(SymbolTable.Type.FLOAT)))
                    throw new LangException("Cannot apply a Unary operator on non-number " + name.name);

                break;

            case "METHOD":
                method = symbolTable.getElement(methodName);

                if(method.arguements.size() > 0)
                    throw new LangException("Non-matching number of arguements, " + method.arguements.size() + " and expecting " + 0);

                break;

            case "METHOD_ARGS":
                method = symbolTable.getElement(methodName);
                
                arguements.typeCheck();

                if(method.arguements.size() != arguements.getArguements().size())
                    throw new LangException("Non-matching number of arguements, " + method.arguements.size() + " and expecting " + arguements.getArguements().size());

                LinkedList<SymbolTable.Arguement> methodArgs = method.arguements;
                for (int i = 0; i < methodArgs.size(); i++) {
                    SymbolTable.Arguement methodArg = methodArgs.get(i);
                    Expression arg = arguements.getArguements().get(i);

                    if(!symbolTable.coercibleByType(arg.typeCheck(), methodArg.type))
                        throw new LangException("Incompatible arguement " + methodArg.name);
                }

                break;

            case "IF":
                expressionType = expression.typeCheck();
                if(!symbolTable.coercibleByType(expressionType, SymbolTable.Type.BOOL))
                    throw new LangException("If Condition " + expressionType + " is not a boolean");

                statement.typeCheck();
                if(elseStatement != null)   elseStatement.typeCheck();

                break;

            default:
                break;
        }

        return null;
    }
}
