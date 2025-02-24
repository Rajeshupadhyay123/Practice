package com.rajesh.practice.Spring_kafka.controller;

import com.rajesh.practice.Spring_kafka.kafka.JsonKafkaProducer;
import com.rajesh.practice.Spring_kafka.payload.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class JsonMessageController {

    @Autowired
    private JsonKafkaProducer jsonKafkaProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        jsonKafkaProducer.sendMessage(user);
        return ResponseEntity.ok("Json Message Sent to kafka");
    }
}
