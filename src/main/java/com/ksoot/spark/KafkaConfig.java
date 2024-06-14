// package com.ksoot.spark;
//
// import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.support.converter.StringJsonMessageConverter;
//
// @Configuration
// class KafkaConfig {
//
//  @Bean
//  ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
//      final ConsumerFactory<String, String> consumerFactory,
//      final KafkaProperties kafkaProperties) {
//    ConcurrentKafkaListenerContainerFactory<String, String> factory =
//        new ConcurrentKafkaListenerContainerFactory<>();
//    factory.setConsumerFactory(consumerFactory);
//    factory.setRecordMessageConverter(new StringJsonMessageConverter());
//    factory.setConcurrency(
//        kafkaProperties.getListener().getConcurrency()); // Set the number of threads to 1
//    return factory;
//  }
// }
