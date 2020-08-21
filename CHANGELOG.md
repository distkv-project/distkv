# Changelog

## 0.1.3 [2020-02-20]

### Features:

- Implement DstSortedListLinkedImpl for sorted list ([#378](https://github.com/distkv-project/distkv/pull/378))
- Add Distkv docker support ([#411](https://github.com/distkv-project/distkv/pull/411))
- Refine client with 1 client instance for all concepts ([#413](https://github.com/distkv-project/distkv/pull/413))
- Add BSD-3-clause LICENSE ([#422](https://github.com/distkv-project/distkv/pull/422))
- Support that master sync the requests to slaves ([#435](https://github.com/distkv-project/distkv/pull/435))
- Upgrade protobuf to proto3 ([#458](https://github.com/distkv-project/distkv/pull/458))
- Enable io thread only for drpc server ([#460](https://github.com/distkv-project/distkv/pull/460))
- Refine the relationship between worker, worker pool and store runtime ([#467](https://github.com/distkv-project/distkv/pull/467)#)
- Remove legacy table concept ([#485](https://github.com/distkv-project/distkv/pull/485))
- Support namespace filter for client to allow key conflicts ([#504](https://github.com/distkv-project/distkv/pull/504))
- Make SyncClient wrap the AsyncClient. ([#506](https://github.com/distkv-project/distkv/pull/506))
- Add namespace command executor ([#508](https://github.com/distkv-project/distkv/pull/508))
- The POC of non heap hash map for store. ([#507](https://github.com/distkv-project/distkv/pull/507))
- [Pine] Init pine module as the ecosystem of Distkv. ([#518](https://github.com/distkv-project/distkv/pull/518))
- Add deactive namespace support ([#521](https://github.com/distkv-project/distkv/pull/521))
- [Pine] Support PineTopper as a component of distkv ecosystem. ([#520](https://github.com/distkv-project/distkv/pull/520))

### Fix：

- Fix jmh bug ([#481](https://github.com/distkv-project/distkv/pull/481))
- Fix SortedListLinkedImpl bug ([#487](https://github.com/distkv-project/distkv/pull/487))
- Fix start dkv server and cli not work ([#490](https://github.com/distkv-project/distkv/pull/490))
- Fix keyboardinterrept exception ([#512](https://github.com/distkv-project/distkv/pull/512))
- Fix package wheel and deploy ([#519](https://github.com/distkv-project/distkv/pull/519))

## 0.1.2 [2019-12-29]

### Features:

- Support asynchronous services.([#294](https://github.com/distkv-project/distkv/pull/294))
- Support Dst asynchronous client.([#333](https://github.com/distkv-project/distkv/pull/333) [#340](https://github.com/distkv-project/distkv/pull/340) [#341](https://github.com/distkv-project/distkv/pull/341))
- Enable new DstExecutor.([#314](https://github.com/distkv-project/distkv/pull/314) [#294](https://github.com/distkv-project/distkv/pull/294)321 [#342](https://github.com/distkv-project/distkv/pull/342) [#343](https://github.com/distkv-project/distkv/pull/343) [#347](https://github.com/distkv-project/distkv/pull/347))
- Introduce Dmeta Module to Dst.([#290](https://github.com/distkv-project/distkv/pull/290))
- Introduce drpc to replace brpc.([#291](https://github.com/distkv-project/distkv/pull/291) [#307](https://github.com/distkv-project/distkv/pull/307) [#345](https://github.com/distkv-project/distkv/pull/345) [#364](https://github.com/distkv-project/distkv/pull/364) )
- Support Dst Error code System.([#306](https://github.com/distkv-project/distkv/pull/306))
- Use HashMap instead of ConcurrentHashMap in core module.([#350](https://github.com/distkv-project/distkv/pull/350))
- Refactor the Dst core module.([#352](https://github.com/distkv-project/distkv/pull/352) [#316](https://github.com/distkv-project/distkv/pull/316) [#361](https://github.com/distkv-project/distkv/pull/361) [#371](https://github.com/distkv-project/distkv/pull/371))
- Refine and optimize the API.([#322](https://github.com/distkv-project/distkv/pull/322) [#317](https://github.com/distkv-project/distkv/pull/317) [#325](https://github.com/distkv-project/distkv/pull/325) [#328](https://github.com/distkv-project/distkv/pull/328) [#339](https://github.com/distkv-project/distkv/pull/339))
- Support Dst configuration classes.(387[](https://github.com/distkv-project/distkv/pull/))

### Fix：

- Fix command line tool.([#296](https://github.com/distkv-project/distkv/pull/296) )
- Fix client disconnection.([#319](https://github.com/distkv-project/distkv/pull/319) )
- Fix unexpected output in the command line.([#358](https://github.com/distkv-project/distkv/pull/358) )
- Fix the bug of out of index of the the shard index.([#365](https://github.com/distkv-project/distkv/pull/365) )
- Fix list removeRange.([#389](https://github.com/distkv-project/distkv/pull/389) )

## 0.1.1 [2019-11-30]

### Features:

- Support DstNewSQL, Parser and new client tool with antlr4([#236](https://github.com/distkv-project/distkv/pull/236) [#237](https://github.com/distkv-project/distkv/pull/237) [#242](https://github.com/distkv-project/distkv/pull/242) [#249](https://github.com/distkv-project/distkv/pull/249)[#264](https://github.com/distkv-project/distkv/pull/264) [#265](https://github.com/distkv-project/distkv/pull/265) [#263](https://github.com/distkv-project/distkv/pull/263) [#266](https://github.com/distkv-project/distkv/pull/266))
- Support table concept in core module ([#155](https://github.com/distkv-project/distkv/pull/155) )
- Support SortedList in core module ([#186](https://github.com/distkv-project/distkv/pull/186))
- Support DstConcurrentQueue interface and DefaultConcurrentQueue ([#248](https://github.com/distkv-project/distkv/pull/248))
- Refine rpc services ([#251](https://github.com/distkv-project/distkv/pull/251) [#255](https://github.com/distkv-project/distkv/pull/255) [#260](https://github.com/distkv-project/distkv/pull/260))
- Rename group id from org.dst to com.distkv.dst([#282](https://github.com/distkv-project/distkv/pull/282))


## 0.1.0 [2019-09-22]

This is the first release version of Dst.

The features of this release version are:

- Support Redis-like structure: str, set, list and dict.
- Support dst-cli client tool.
- Support Java client SDK with sync client.
