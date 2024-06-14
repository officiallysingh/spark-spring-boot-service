// package com.ksoot.spark;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Component;
//
// @Component
// @Slf4j
// public class SparkJobEventListener {
//
//  //    @KafkaListener(topics = "${data-fetcher.kafka.read-topic}", groupId =
//  // "${kafka.consumer.group-id}")
//  @KafkaListener(topics = "${data-fetcher.kafka.read-topic}")
//  public void onDataFetchEvent(final SparkJobEvent sparkJobEvent) {
//    log.info("DataFetchEvent : " + sparkJobEvent);
//  }
// }
