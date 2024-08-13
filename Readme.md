## Phase3 - Semantic Analyzer

This phase accepts an input text code file and checks for semantic type analysis.

All the type checking rules are implemented using the typeCheck() function.

All scopes are created and stored as a list of hashmaps.

This input is parsed and produces output as Success or error message stating the reason of failure of unacceptable grammar.

Given testcases are stored in p3tests, and their results are stored in p3tests_out.


Steps to run the code:
1. Run the following command:
    ```make run```
    OR
    ```make```

    This will accept the four input test files () and create their respective output files.

2. For more test cases:

    (a) Add a new file <newFile.txt>
    And add the following code in Makefile test command.
    ```
            $(JAVA) -cp $(CP) ScannerTest test-inputs/<newFile.txt> > test-outputs/<newFile-output.txt>
    ```

    (b) Add new test cases to any of the existing files, and re-run step 1.