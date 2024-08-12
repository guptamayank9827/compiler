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

FILE=LexerTest.java

run: test

all: Lexer.java parser.java $(FILE:java=class)

sampleFile.utd: all
		$(JAVA) -cp $(CP) LexerTest sampleFile.utd > sampleFile-output.txt
		cat sampleFile.utd
		cat -n sampleFile-output.txt

test: all
		$(JAVA) -cp $(CP) LexerTest test-inputs/basicTerminals.txt > test-outputs/basicTerminals.txt
		$(JAVA) -cp $(CP) LexerTest test-inputs/basicRegex.txt > test-outputs/basicRegex.txt
		$(JAVA) -cp $(CP) LexerTest test-inputs/basicFails.txt > test-outputs/basicFails.txt

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup