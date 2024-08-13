JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
		$(JAVAC) -cp $(CP) $*.java

FILE= 	Lexer.java parser.java sym.java LexerTest.java ScannerTest.java\
		LangException.java SymbolTable.java\
		Token.java Program.java MemberDeclarations.java\
		FieldDeclarations.java MethodDeclarations.java ArguementDeclarations.java ArguementDeclarationList.java Statements.java Arguements.java PrintLineList.java\
		FieldDeclaration.java MethodDeclaration.java ArguementDeclaration.java Statement.java PrintList.java ReadList.java\
		Expression.java Name.java

run: p3

all: Lexer.java parser.java $(FILE:java=class)

test: all
		$(JAVA) -cp $(CP) ScannerTest test.txt > test-output.txt

p3: all
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badDec.as > test-outputs/badDec.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badnc.as > test-outputs/badInc.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badNegation.as > test-outputs/badNegation.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badString.as > test-outputs/badString.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badTernaryCond.as > test-outputs/badTernaryCond.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/badTernaryTypes.as > test-outputs/badTernaryTypes.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/boolToFloat.as > test-outputs/boolToFloat.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/boolToInt.as > test-outputs/boolToInt.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/callNonExistFunc.as > test-outputs/callNonExistFunc.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/charToFloat.as > test-outputs/charToFloat.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/charToInt.as > test-outputs/charToInt.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/floatToInt.as > test-outputs/floatToInt.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/fullValidProgram.as > test-outputs/fullValidProgram.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/incompatBinary.as > test-outputs/incompatBinary.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/intArrayToBoolArray.as > test-outputs/intArrayToBoolArray.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/noReturn.as > test-outputs/noReturn.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/reassignFinal.as > test-outputs/reassignFinal.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/redefMethod.as > test-outputs/redefMethod.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/redefVar.as > test-outputs/redefVar.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/redefVarAsMethod.as > test-outputs/redefVarAsMethod.as
		$(JAVA) -cp $(CP) ScannerTest test-inputs/returnTypeBad.as > test-outputs/returnTypeBad.as

sampleFile.utd: all
		$(JAVA) -cp $(CP) ScannerTest test-inputs/sampleFile.utd > test-outputs/sampleFile.txt

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
