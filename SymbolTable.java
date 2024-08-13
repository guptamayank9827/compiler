import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

class SymbolTable {

    enum Type { VOID, INT, FLOAT, CHAR, BOOL, STRING }

    public Type stringToEnumType(String type) {
        switch (type) {
            case "int":
                return Type.INT;
            case "char":
                return Type.CHAR;
            case "string":
                return Type.STRING;
            case "float":
                return Type.FLOAT;
            case "bool":
                return Type.BOOL;
            case "void":
                return Type.VOID;
            default:
                return Type.VOID;
        }
    }

    public LinkedList<Arguement> argDeclarationsToArguements(ArguementDeclarations argDeclarations) {
        LinkedList<Arguement> args = new LinkedList<Arguement>();

        if(argDeclarations == null || argDeclarations.getSize() < 1)    return args;

        ArguementDeclarationList argDeclarationList = argDeclarations.getList();
        if(argDeclarationList == null || argDeclarationList.getSize() < 1)  return args;

        List<ArguementDeclaration> argDeclarationArray = argDeclarationList.getArray();
        if(argDeclarationArray == null || argDeclarationArray.size() < 1)   return args;

        for (ArguementDeclaration argDeclaration : argDeclarationArray)
            args.add(new Arguement(argDeclaration.type, argDeclaration.argName, argDeclaration.isArray));

        return args;
    }

    public boolean coercibleByVariable(String name, SymbolTable.Type toType) throws LangException {
        SymbolTable.Variable element = getElement(name);

        if(element.isArray && toType.equals(SymbolTable.Type.STRING))
            throw new LangException("An array cannot coerced to a String");

        return coercibleByType(element.type, toType);
    }

    public boolean coercibleByType(SymbolTable.Type fromType, SymbolTable.Type toType) {
        if(fromType.equals(toType)) return true;

        switch (toType) {
            case SymbolTable.Type.INT:
                return false;
        
            case SymbolTable.Type.FLOAT:
                if(fromType.equals(SymbolTable.Type.INT))   return true;
                break;

            case SymbolTable.Type.BOOL:
                if(fromType.equals(SymbolTable.Type.INT))   return true;
                break;

            case SymbolTable.Type.CHAR:
                return false;

            case SymbolTable.Type.STRING:
                return true;

            default:
                break;
        }

        return false;
    }

    public class Arguement {
        String name;
        Type type;
        boolean isArray;

        public Arguement(String type, String name, boolean isArray) {
            this.type = stringToEnumType(type);
            this.name = name;
            this.isArray = isArray;
        }
    }

    public class Variable {
        Type type;
        String name;
        boolean isFinal;
        boolean isArray;
        boolean isMethod;
        LinkedList<Arguement> arguements;

        //variable constructor
        public Variable(String type, String name, boolean isFinal, boolean isArray) {
            this.type = stringToEnumType(type);
            this.name = name;
            this.isFinal = isFinal;
            this.isArray = isArray;
            this.isMethod = false;
            this.arguements = null;
        }

        //method constructor
        public Variable(String type, String name, LinkedList<Arguement> args) {
            this.type = stringToEnumType(type);
            this.name = name;
            this.isFinal = false;
            this.isArray = false;
            this.isMethod = true;
            this.arguements = args;
        }
    }

    LinkedList<HashMap<String, Variable>> scopes;

    public SymbolTable() {
        scopes = new LinkedList<HashMap<String, Variable>>();
    }

    public void startScope() {
        scopes.addFirst(new HashMap<String, Variable>());
    }

    public void endScope() {
        scopes.removeFirst();
    }

    public void addMethod(String type, String id, ArguementDeclarations argDeclarations) throws LangException {
        HashMap<String,Variable> scope = scopes.getFirst();

        if(scope.containsKey(id))   throw new LangException("Cannot redeclare method " + id);

        Variable method = new Variable(type, id, argDeclarationsToArguements(argDeclarations));
        scope.put(id, method);

        return;
    }

    public void addVariable(String type, String id, boolean isFinal, boolean isArray) throws LangException  {
        HashMap<String,Variable> scope = scopes.getFirst();

        if(scope.containsKey(id))   throw new LangException("Cannot redeclare variable " + id);

        Variable variable = new Variable(type, id, isFinal, isArray);
        scope.put(id, variable);
        
        return;
    }

    //method or variable
    public Variable getElement(String id) throws LangException {

        for (int i = 0; i < scopes.size(); i++) {
            HashMap<String,Variable> scope = scopes.get(i);
            if(scope.containsKey(id))   return scope.get(id);
        }

        throw new LangException("Cannot find declaration of Variable/Method " + id);
    }

    public String toString(int t) {
        String text = "";

        for (int i = 0; i < scopes.size(); i++) {
            HashMap<String,Variable> scope = scopes.get(i);
            text += "Scope " + i + "\n";
            for (String key : scope.keySet()) {
                Variable var = scope.get(key);
                text += (var.isMethod?"method":"variable") + " " + (var.isFinal?"final ":"") + var.type + " " + var.name + " " + (var.isArray?"array ":"") + "\n";
            }
            text += "\n";
        }

        return text;
    }
}