grammar DstNewSQL;

@header {
package org.dst.parse.gen;
}

statement: concept_statement;
concept_statement: str_statement | list_statement | set_statement | dict_statement;
str_statement: str_put | str_get;
str_put: 'str.put' key value;
str_get: 'str.get' key ;
list_statement: ;
set_statement: set_put | set_get;
set_put: 'set.put' key array_value;
set_get:'set.get' key;
dict_statement: ;
key: STRING;
value: STRING;
array_value :(STRING)+;
STRING: '"'.*?'"'  ; // match String
WS : [ \t\r\n ]+ -> skip; // skip spaces, tabs, newlines