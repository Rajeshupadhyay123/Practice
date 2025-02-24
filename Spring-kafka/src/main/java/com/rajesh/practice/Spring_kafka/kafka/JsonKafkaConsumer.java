package com.rajesh.practice.Spring_kafka.kafka;

import com.rajesh.practice.Spring_kafka.payload.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaConsumer {


    @KafkaListener(topics = "javaguides_json",groupId = "myGroup")
    public void consume(User user){
        log.info("Message received -> {}", user.toString());
    }
}
