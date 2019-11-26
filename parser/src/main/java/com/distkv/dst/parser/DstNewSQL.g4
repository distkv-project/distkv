grammar DstNewSQL;

@header {
package com.distkv.dst.parser.generated;
}

/**
* This is the Dst new SQL grammar definition.
*/

statement: (conceptStatement) EOF;
conceptStatement: strStatement | listStatement | setStatement | dictStatement;

// str concept
strStatement: strPut | strGet;
strPut: 'str.put' key value;
strGet: 'str.get' key ;

// list concept
listStatement: listPut | listLput | listRput | listGet | listRGet | listDelete | listMDelete;
listPut: 'list.put' key valueArray;
listLput: 'list.lput' key valueArray;
listRput: 'list.rput' key valueArray;
listGet: 'list.get' (listGetAll | listGetOne | listGetRange);
listRGet: 'list.rget' listGetArguments;
listDelete: 'list.remove' (listRemoveOne | listRemoveRange);
listMDelete: 'list.mRemove' key (index)+;

listGetArguments: listGetAll | listGetOne | listGetRange;
// Get the all values of this list.
listGetAll: key;
// Get the specific value of the given index.
listGetOne: key index;
// Get the specific values by the given range.
listGetRange: key index index;

listRemoveOne: key index;
listRemoveRange: key index index;


// set concept
setStatement: setPut | setGet | setPutItem | setRemoveItem | setExists | setDrop;
setPut: 'set.put' key valueArray;
setGet:'set.get' key;
setPutItem: 'set.putItem' key itemValue;
setRemoveItem: ('set.remove'|'set.removeItem') key itemValue;
setExists: 'set.exists' key itemValue;
setDrop: 'set.drop' key;


// dict concept
dictStatement: dictPut | dictGet | dictPutItem | dictGetItem | dictPopItem | dictRemoveItem | dictDrop;
dictPut: 'dict.put' key keyValuePairs;
dictGet: 'dict.get' key;
dictPutItem: 'dict.putItem' key itemKey itemValue;
dictGetItem: 'dict.getItem' key itemKey;
dictPopItem: 'dict.popItem' key itemKey;
dictRemoveItem: 'dict.removeItem' key itemKey;
dictDrop: 'dict.drop' key;

keyValuePairs: (keyValuePair)+;
keyValuePair: itemKey itemValue;
itemKey: STRING;
itemValue: STRING;

// meta
key: STRING;
value: STRING;
valueArray: (STRING)+;
index: NON_NEGATIVE_INT;


NON_NEGATIVE_INT: [1-9][0-9]* | '0';
STRING: (~[ \t\r\n])+;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n]+ -> skip;
