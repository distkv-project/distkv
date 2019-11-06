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

listStatement: ;

setStatement: setPut | setGet;
setPut: 'set.put' key valueArray;
setGet:'set.get' key;

dictStatement: ;

key: STRING;
value: STRING;
valueArray :(STRING)+;

STRING: '"'.*?'"'  ; // match String
WS : [ \t\r\n ]+ -> skip; // skip spaces, tabs, newlines
