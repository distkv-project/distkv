## Dst-ErrorCode Rule

### Introduction:
Dst-ErrorCode provides specific rules to identify any error that 
was occurred during the execution.
According to the rules of ErrorCode, users can clearly interpret 
the error class information from it.

### The meaning of each code:
#### 1. First digit: Data class / Grammatical errors

Digit Code|Meaning 
---|---
0|Meaningless/Error-free
A|String
B|List
C|Set
D|Dict
E|SortedList
F|Table
X|Grammatical error 

#### 2. Second digit: Common advanced error class

Digit Code|Meaning
---|---
0|Meaningless/Error-free
1|KeyNotFound
2|OutOfBounds

#### 3. Third digit: Grammatical error identification

Digit Code|Meaning
---|---
0|Meaningless/Error-free
1|Incomprehensible input
2|Incorrect number of parameters

#### 4. Fourth digit: Concrete error class

Digit Code|Meaning
---|---
0|Meaningless/Error-free
1|DictKeyNotFound
2|ListIndexOutOfBounds
3|ItemNotExistInSet
4|ItemNotFoundInSortedList
5|SortedListTopNumBePositive

### ErrorCode List:
ErrorCode|Meaning|Suggest
---|:---:|---
X010|Grammatical error. Incomprehensible input.|Please check your command.
X020|Grammatical error. Incorrect number of parameters.|Please confirm whether the parameters of your command are correct.
A100|String command. Key not found.|Please check whether the content of 'key' is correct.
B100|List command. Key not found.|Please check whether the content of 'key' is correct.
B202|List command. Out of bounds. List index out of bounds.|Please confirm whether the 'index' parameter in your command is in the range of List.
C100|Set command. Key not found.|Please check whether the content of 'key' is correct.
D100|Dict command. Key not found.|Please check whether the content of 'key' is correct.
D001|Dict command. Dict key not found.|Please check whether the keyword in your command is correct.


