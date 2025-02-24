package com.rajesh.practice.Spring_kafka.kafka;

import com.rajesh.practice.Spring_kafka.payload.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonKafkaProducer {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User data){
        log.info("Message Sent [{}]",data.toString());

        Message<User> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, "javaguides_json")
                .build();

        kafkaTemplate.send(message);
    }

}
