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
		Token.java Program.java MemberDeclarations.java\
		FieldDeclarations.java MethodDeclarations.java ArguementDeclarations.java ArguementDeclarationList.java Statements.java Arguements.java PrintLineList.java\
		FieldDeclaration.java MethodDeclaration.java ArguementDeclaration.java Statement.java PrintList.java ReadList.java\
		Expression.java Name.java

run: test

all: Lexer.java parser.java $(FILE:java=class)

base: all
		$(JAVA) -cp $(CP) ScannerTest test-inputs/test.txt > test-outputs/test-output.txt

test: all
		$(JAVA) -cp $(CP) ScannerTest test-inputs/Phase2_empty.txt > test-outputs/Phase2_empty.txt
		$(JAVA) -cp $(CP) ScannerTest test-inputs/Phase2_fields.txt > test-outputs/Phase2_fields.txt
		$(JAVA) -cp $(CP) ScannerTest test-inputs/Phase2_methods.txt > test-outputs/Phase2_methods.txt
		$(JAVA) -cp $(CP) ScannerTest test-inputs/Phase2_full.txt > test-outputs/Phase2_full.txt

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
