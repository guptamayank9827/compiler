## Part 1 - Scanner

This phase accepts an input text file and breaks it down into acceptable tokens.

The tokens are categorized as per their terminal types.

tokens.jflex file defines the regEx rules to identify token, and creates a Symbol object for each.

grammar.cup file defines all the terminals along with their number.


Steps to run the code:
1. Run the following command:
    ```make run```
    OR
    ```make```

    This will accept the three input test files (basicTerminals.txt, basicRegex.txt, basicFails.txt) and create their respective output files.

2. For more test cases:

    (a) Add a new file <newFile.txt>
    And add the following code in Makefile test command.
    ```
            $(JAVA) -cp $(CP) LexerTest test-inputs/<newFile.txt> > test-outputs/<newFile-output.txt>
    ```

    (b) Add new test cases to any of the existing three files, and re-run following step 1.
