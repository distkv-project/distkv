grammar DstNewSQL;

@header {
package org.dst.parser.generated;
}

statement: (conceptStatement) EOF;
conceptStatement: strStatement | listStatement | setStatement | dictStatement;

// str concept
strStatement: strPut | strGet;
strPut: 'str.put' key value;
strGet: 'str.get' key ;

// list concept
listStatement: listPut | listGet | listLput | listRput | listLdel | listRdel;
listPut: 'list.put' key valueArray;
listGet: 'list.get' key;
listLput: 'list.lput' key valueArray;
listRput: 'list.rput' key valueArray;
listLdel: 'list.ldel' key index;
listRdel: 'list.rdel' key index;


// set concept
setStatement: setPut | setGet | setDropByKey;
setPut: 'set.put' key valueArray;
setGet:'set.get' key;
setDropByKey: 'set.dropByKey' key;

// dict concept
dictStatement: dictPut | dictGet | dictGetItemValue | dictPutItem | dictPopItem | dictDel | dictDelItem;
dictPut: 'dict.put' key keyValuePairs;
dictGet: 'dict.get' key;
dictGetItemValue: 'dict.getItem' key itemKey;
dictPutItem: 'dict.putItem' key itemKey itemValue;
dictPopItem: 'dict.popItem' key itemKey;
dictDel: 'dict.del' key;
dictDelItem: 'dict.delItem' key itemKey;

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
