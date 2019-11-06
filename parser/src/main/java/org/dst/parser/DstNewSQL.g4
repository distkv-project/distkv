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
setPut: 'set.put' key valueArray;
setGet:'set.get' key;
setDropByKey: 'set.dropByKey' key key;

// dict concept
dictStatement: ;

// meta
key: STRING;
value: STRING;
valueArray :(STRING)+;

STRING: '"'.*?'"'  ;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n ]+ -> skip;
