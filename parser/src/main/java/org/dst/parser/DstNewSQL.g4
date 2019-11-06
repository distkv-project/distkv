grammar DstNewSQL;

@header {
package org.dst.parser.generated;
}

statement: conceptStatement;
conceptStatement: strStatement | listStatement | setStatement | dictStatement;

// str concept
strStatement: strPut | strGet;
strPut: 'str.put' STRING STRING;
strGet: 'str.get' STRING ;

// list concept
listStatement: ;

// set concept
setStatement: setPut | setGet | setDropByKey;
setPut: 'set.put' STRING (STRING)+;
setGet:'set.get' STRING;
setDropByKey: 'set.dropByKey' STRING STRING;

// dict concept
dictStatement: ;

STRING: '"'.*?'"'  ;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n ]+ -> skip;
