grammar Operate;


opreate:set|str|list|dict;
set:'set.' setoperate key value;
str:'str.';
list:'str.';
dict:'dict.';
setoperate:'put' | 'get' | 'del' | 'exists' | 'drop';
key:STRING;
value:(STRING)*;
STRING: '"'.*?'"'  ; // match String
WS : [ \t\r\n ]+ -> skip; // skip spaces, tabs, newlines