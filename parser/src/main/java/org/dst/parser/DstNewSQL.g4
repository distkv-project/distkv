grammar DstNewSQL;

@header {
package org.dst.parser.generated;
}

statement: conceptStatement;
conceptStatement: strStatement | listStatement | setStatement | dictStatement;

// str concept
strStatement: strPut | strGet;
strPut: 'str.put' key value;
strGet: 'str.get' key ;

// list concept
listStatement: ;

// set concept
setStatement: setPut | setGet | setDropByKey;
setPut: 'set.put' key value_array;
setGet:'set.get' key;
setDropByKey: 'set.dropByKey' key;

// dict concept
dictStatement: ;

// meta
key: STRING;
value: STRING;
value_array: (STRING)+;

STRING: '"'.*?'"'  ;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n ]+ -> skip;
