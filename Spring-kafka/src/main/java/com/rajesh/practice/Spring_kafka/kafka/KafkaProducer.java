package com.rajesh.practice.Spring_kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void sendMessage(String message){
        log.info(String.format("message send --->>>> %s",message));
        kafkaTemplate.send("javaguides",message);
    }
}
