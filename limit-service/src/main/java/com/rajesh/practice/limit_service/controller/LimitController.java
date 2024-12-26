package com.rajesh.practice.limit_service.controller;

import com.rajesh.practice.limit_service.controller.bean.Limit;
import com.rajesh.practice.limit_service.controller.bean.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitController {

    @Autowired
    private PropertiesConfiguration configuration;

    @GetMapping("/limit_retrieve")
    public Limit retriveLimit(){
        return new Limit(configuration.getMaximum(),configuration.getMinimum());
    }

}
