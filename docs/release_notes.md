## Release 0.1.3
date: 22 Feb 2020

#### Features
- Implement DstSortedListLinkedImpl for sorted list (#378)
- Add Distkv docker support (#411)
- Refine client with 1 client instance for all concepts (#413)
- Add BSD-3-clause LICENSE (#422)
- Support that master sync the requests to slaves (#435)
- Upgrade protobuf to proto3 (#458)
- Enable io thread only for dousi server (#460)
- Refine the relationship between worker, worker pool and store runtime (#467)
- Remove legacy table concept (#485)
- Support namespace filter for client to allow key conflicts (#504)
- Make SyncClient wrap the AsyncClient. (#506)
- Add namespace command executor (#508)
- The POC of non heap hash map for store. (#507)
- [Pine] Init pine module as the ecosystem of Distkv. (#518)
- Add deactive namespace support (#521)
- [Pine] Support PineTopper as a component of distkv ecosystem. (#520)
#### Bug Fix
- Fix jmh bug (#481)
- Fix SortedListLinkedImpl bug (#487)
- Fix start dkv server and cli not work (#490)
- Fix keyboardinterrept exception (#512)
- Fix package wheel and deploy (#519)

## Release 0.1.2
date: 29 Dec 2019
#### Features
- Support asynchronous services.(#294 )
- Support Dst asynchronous client.(#333 #340 #341 )
- Enable new DstExecutor.(#314 #294 #321 #342 #343 #347 )
- Introduce Dmeta Module to Dst.(#290 )
- Introduce drpc to replace brpc.(#291 #307 #345 #364 )
- Support Dst Error code System.(#306 )
- Use HashMap instead of ConcurrentHashMap in core module.(#350 )
- Refactor the Dst core module.(#352 #316 #361 #371 )
- Refine and optimize the API.(#322 #317 #325 #328 #339 )
- Support Dst configuration classes.(#387 )
#### Bug Fix
- Fix command line tool.(#296 )
- Fix client disconnection.(#319 )
- Fix unexpected output in the command line.(#358 )
- Fix the bug of out of index of the the shard index.(#365 )
- Fix list removeRange.(#389 )

## Release 0.1.1
date: 30 Nov 2019

#### Features:
- Support DstNewSQL, Parser and new client tool with antlr4(#236 #237 #242 #249 #264 #265 #263 #266 )
- Support table concept in core module (#155 )
- Support SortedList in core module (#186 )
- Support DstConcurrentQueue interface and DefaultConcurrentQueue (#248 )
- Refine rpc services (#251 #260 #255 )
- Rename group id from org.dst to com.distkv.dst(#282 )

## Release 0.1.0
date: 22 Sep 2019
#### Features:
- Support Redis-like structure: str, set, list and dict.
- Support dst-cli client tool.
- Support Java client SDK with sync client.
