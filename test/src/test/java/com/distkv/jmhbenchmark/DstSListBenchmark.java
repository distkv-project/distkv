package com.distkv.jmhbenchmark;

import com.distkv.asyncclient.DefaultAsyncClient;
import com.distkv.asyncclient.DstAsyncClient;
import com.distkv.client.DefaultDstClient;
import com.distkv.client.DstClient;
import com.distkv.common.DistKVTuple;
import com.distkv.common.entity.sortedList.SortedListEntity;
import com.distkv.core.struct.slist.SortedList;
import com.distkv.core.struct.slist.SortedListLinkedImpl;
import com.distkv.supplier.TestUtil;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;
import org.testng.annotations.Test;


public class DstSListBenchmark {

  private static final int maxOperationTimes=1000;

  private static final int minValueInPutList=-100000;

  private static final int maxValueInPutList=100000;

  private static final int lengthInPutList=10000;

  public static void main(String[] args){
    System.out.println("Start run benchmark..........\n\n\n");
    long start=System.currentTimeMillis();

    //Test SortedListLinkedImpl.put
    double totalTimes=0;
    for (int i=0; i < maxOperationTimes; i++){
      SortedList sortedList=new SortedListLinkedImpl();
      List<SortedListEntity> list=generatePutDates(minValueInPutList,
          maxValueInPutList,lengthInPutList);
      double consume=testPut(sortedList,list);
      addWeight(sortedList,consume);
    }
    System.out.println("Method benchmark                 Cnt        Magnitude" +
        "  Average weighted time(ns)     Total weighted time(ns)");
    System.out.println("SortedList.put        " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                      " + totalTimes);

    //Test SortedListLinkedImpl.putItem
    SortedList sortedList=new SortedListLinkedImpl();
    List<SortedListEntity> list=generatePutDates(minValueInPutList,
        maxValueInPutList,lengthInPutList);
    testPut(sortedList,list);
    totalTimes=0;
    for (int i = 0; i <maxOperationTimes; i++) {
      SortedListEntity entity=generatePutItemDatas(minValueInPutList-1000,
          maxValueInPutList+1000);
      double consume=testPutItem(sortedList,entity);
      totalTimes+=addWeight(sortedList,consume);
    }
    System.out.println("SortedList.putItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "             " + totalTimes);
    // Test SortedList.removeItem
    sortedList=new SortedListLinkedImpl();
    list=generatePutDates(minValueInPutList,
        maxValueInPutList,lengthInPutList);
    testPut(sortedList,list);
    totalTimes=0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str=generateRemoveItemDatas(minValueInPutList-100,
          maxValueInPutList+100);
      double consume=testRemoveItem(sortedList,str);
      totalTimes+=addWeight(sortedList,consume);
    }
    System.out.println("SortedList.removeItem " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedList.incrScore
    sortedList = new SortedListLinkedImpl();
    list=generatePutDates(minValueInPutList,
        maxValueInPutList,lengthInPutList);
    testPut(sortedList,list);
    totalTimes=0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistKVTuple<String,Integer> tuple=generateIncrScoreDatas(minValueInPutList-100,
          maxValueInPutList+100);
      double consume=testIncrSore(sortedList,tuple.getFirst(),tuple.getSecond());
      totalTimes  += addWeight(sortedList,consume);
    }
    System.out.println("SortedList.incrScore  " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedListLinkedImpl.subList
    sortedList = new SortedListLinkedImpl();
    list = generatePutDates(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      DistKVTuple<Integer, Integer> tuple = generateSubListDatas(sortedList.size());
      double consume = testSubList(sortedList, tuple.getFirst(), tuple.getSecond());;
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedList.subList    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    // Test SortedList.getItem
    sortedList = new SortedListLinkedImpl();
    list = generatePutDates(minValueInPutList,
        maxValueInPutList, lengthInPutList);
    testPut(sortedList, list);
    totalTimes = 0;
    for (int i = 0; i < maxOperationTimes; i++) {
      String str = generateRemoveItemDatas(minValueInPutList - 100,
          maxValueInPutList + 100);
      double consume = testGetItem(sortedList, str);
      totalTimes += addWeight(sortedList, consume);
    }
    System.out.println("SortedList.getItem    " + maxOperationTimes +
        "        " + lengthInPutList + "          " + totalTimes / maxOperationTimes +
        "                         " + totalTimes);

    long end = System.currentTimeMillis();
    System.out.println("\n\n\nBenchmarking takes time " + (end - start) + "ms");
  }

  private static long testPut(
      SortedList sortedList, List<SortedListEntity > sortedListEntities){
    long start=System.nanoTime();
    sortedList.put(sortedListEntities);
    long end=System.nanoTime();
    return end-start;
  }

  private static List<SortedListEntity> generatePutDates(
      int minValue, int maxValue , int len){
    List<SortedListEntity> list=new LinkedList<>();
    for (int i = 0 ;i < len; i++ ){
      int randomValue=(int)(Math.random()*(maxValue-minValue+1))+minValue;
      list.add(new SortedListEntity(String.valueOf(randomValue),randomValue));
    }
    return list;
  }

  private static long testPutItem(
   SortedList sortedList,SortedListEntity sortedListEntity){
    long start = System.nanoTime();
    sortedList.putItem(sortedListEntity);
    long end = System.nanoTime();
    return end-start;
  }

  private static SortedListEntity generatePutItemDatas(
      int minValue, int maxValue){
  int randomValue = (int)(Math.random()*(maxValue-minValue+1))+minValue;
    return new SortedListEntity(String.valueOf(randomValue),randomValue);
  }

  private static long testRemoveItem(
      SortedList sortedList, String menber){
    long start=System.nanoTime();
    sortedList.removeItem(menber);
    long end = System.nanoTime();
    return end-start;
  }

  private static String generateRemoveItemDatas(
      int minValue, int maxValue){
    int randomValue=(int)(Math.random()*(maxValue-minValue+1))+minValue;
    return String.valueOf(randomValue);
  }

  private static long testIncrSore(
      SortedList sortedList, String mumber, int delta){
    long start =System.nanoTime();
    sortedList.incrScore(mumber,delta);
    long end=System.nanoTime();
    return end -start;
  }

  private static DistKVTuple<String,Integer> generateIncrScoreDatas(
      int minValue, int maxValue){
    int randomValue=(int)(Math.random()*(maxValue-minValue+1)) + minValue;
    int randomDelta=(int)(Math.random()*(maxValue-minValue+1)) + minValue;
    return new DistKVTuple<>(String.valueOf(randomValue),randomDelta);
  }

 private static long testSubList(
     SortedList sortedList, int startIndex, int endIndex){
    long start=System.nanoTime();
    sortedList.subList(startIndex,endIndex);
    long end =System.nanoTime();
    return end - start;
 }

 private static DistKVTuple<Integer,Integer> generateSubListDatas(
     int size){
   int startIndex=(int) Math.random()*size+1;
   int len=(int)Math.random()*(size-startIndex+1);
   return new DistKVTuple<>(startIndex,startIndex+len);
 }

 private static long testGetItem(
     SortedList sortedList,String mumber){
    long start=System.nanoTime();
    sortedList.getItem(mumber);
    long end=System.nanoTime();
    return end -start;
  }

  private static double addWeight(
      SortedList sortedList,double consume){
    return consume/(sortedList.size()+1);
  }


}
