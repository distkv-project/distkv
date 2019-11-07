grammar DstNewSQL;

@header {
package org.dst.parser.generated;
}

statement: (conceptStatement) EOF;
conceptStatement: strStatement | listStatement | setStatement | dictStatement;

// str concept
strStatement: (strPut | strGet);
strPut: 'str.put' key value;
strGet: 'str.get' key ;

// list concept
listStatement: ;
// listStatement: listPut | listGet | listDel | listLput | listRput | listLdel | listRdel;

// set concept
setStatement: setPut | setGet | setDropByKey;
setPut: 'set.put' key valueArray;
setGet:'set.get' key;
setDropByKey: 'set.dropByKey' key;

// dict concept
dictStatement: ;

// meta
key: STRING;
value: STRING;
valueArray: (STRING)+;

STRING: (~[ \t\r\n])+;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n]+ -> skip;
