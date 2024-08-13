## Phase #2 - Parser

This phase accepts an input text file and parses it according to acceptable grammar rules.

All the grammar rules are defined using a combination of terminals and non-terminals.

grammar.cup file holds all the production rules, which accepts a given input text file.

This input is parsed and printed as indentated code text file.


Steps to run the code:
1. Run the following command:
    ```make run```
    OR
    ```make```

    This will accept the four input test files (Phase2_empty.txt, Phase2_fields.txt, Phase2_methods.txt, and Phase2_full.txt) and create their respective output files.

2. For more test cases:

    (a) Add a new file <newFile.txt>
    And add the following code in Makefile test command.
    ```
            $(JAVA) -cp $(CP) ScannerTest test-inputs/<newFile.txt> > test-outputs/<newFile-output.txt>
    ```

    (b) Add new test cases to any of the existing fourt files, and re-run following step 1.
