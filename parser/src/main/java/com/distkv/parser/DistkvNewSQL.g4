grammar DistkvNewSQL;

@header {
package com.distkv.parser.generated;
}

/**
* This is the Distkv new SQL grammar definition.
*/

statement: (conceptStatement) EOF;
conceptStatement: basicOperationsStatement | strStatement | listStatement
| setStatement | dictStatement | slistStatement | intStatement;

// basic operations
basicOperationsStatement: exit | activeNamespace | deactiveNamespace | expire | ttl | drop | exists;
exit: 'exit';
activeNamespace: 'active namespace' namespace;
deactiveNamespace: 'deactive namespace';
expire: 'expire' key anyInt;
ttl: 'ttl' key;
drop: 'drop' key;
exists: 'exists' key;

// str concept
strStatement: strPut | strGet;
strPut: 'str.put' key value;
strGet: 'str.get' key ;

// list concept
listStatement: listPut | listLput | listRput | listGet | listRemove | listMRemove;
listPut: 'list.put' key valueArray;
listLput: 'list.lput' key valueArray;
listRput: 'list.rput' key valueArray;
listGet: 'list.get' (listGetAll | listGetOne | listGetRange);
listRemove: 'list.remove' (listRemoveOne | listRemoveRange);
listMRemove: 'list.mremove' key (index)+;

// Get the all values of this list.
listGetAll: key;
// Get the specific value of the given index.
listGetOne: key index;
// Get the specific values by the given range.
listGetRange: key index index;

listRemoveOne: key index;
listRemoveRange: key index index;


// set concept
setStatement: setPut | setGet | setPutItem | setRemoveItem | setExists;
setPut: 'set.put' key valueArray;
setGet:'set.get' key;
setPutItem: 'set.putItem' key itemValue;
setRemoveItem: ('set.remove'|'set.removeItem') key itemValue;
setExists: 'set.exists' key itemValue;


// dict concept
dictStatement: dictPut | dictGet | dictPutItem | dictGetItem | dictPopItem | dictRemoveItem;
dictPut: 'dict.put' key keyValuePairs;
dictGet: 'dict.get' key;
dictPutItem: 'dict.putItem' key itemKey itemValue;
dictGetItem: 'dict.getItem' key itemKey;
dictPopItem: 'dict.popItem' key itemKey;
dictRemoveItem: 'dict.removeItem' key itemKey;

// slist concept
slistStatement: slistPut | slistTop | slistIncrScore | slistPutMember | slistRemoveMember | slistGetMember;
slistPut: 'slist.put' key slistEntityPairs;
slistTop: 'slist.top' key topCount;
slistIncrScore: 'slist.incrScore' (slistIncrScoreDefault | slistIncrScoreDelta);
slistPutMember: 'slist.putMember' key itemMember itemScore;
slistRemoveMember: 'slist.removeMember' key itemMember;
slistGetMember: 'slist.getMember' key itemMember;

// slist.incrScore arguments
// slist increase one score by default
slistIncrScoreDefault: key itemMember;
// slist increase delta scores
slistIncrScoreDelta: key itemMember anyInt;

// int concept
intStatement: intPut | intGet | intIncr;
intPut: 'int.put' key anyInt;
intGet: 'int.get' key;
intIncr: 'int.incr' (intIncrDefault | intIncrDelta);
//  Increase one point by default
intIncrDefault: key;
// Increase delta points
intIncrDelta: key anyInt;


keyValuePairs: (keyValuePair)+;
keyValuePair: itemKey itemValue;
itemKey: STRING;
itemValue: STRING;

slistEntityPairs: (slistEntity)+;
slistEntity: itemMember itemScore;
itemMember: STRING;
itemScore: anyInt;
topCount: POSITIVE_INT;

// meta
namespace: STRING;
key: STRING;
value: STRING | NEGATIVE_INT | POSITIVE_INT | ZERO;
valueArray: (STRING)+;
index: POSITIVE_INT | ZERO;
anyInt: NEGATIVE_INT | POSITIVE_INT | ZERO;


NEGATIVE_INT: '-'[1-9][0-9]*;
POSITIVE_INT: [1-9][0-9]*;
ZERO: '0';
STRING: (~[ \t\r\n])+;

 // Skip spaces, tabs, and newlines.
WS : [ \t\r\n]+ -> skip;
