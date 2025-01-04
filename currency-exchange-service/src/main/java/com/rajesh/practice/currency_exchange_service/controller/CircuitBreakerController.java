package com.rajesh.practice.currency_exchange_service.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api",fallbackMethod = "hardcodedResponse")
//    @CircuitBreaker(name = "sample-api",fallbackMethod = "hardcodedResponse")
//    @RateLimiter(name = "default")
    @Bulkhead(name = "sample-api")
    public String sampleApi(){
        logger.info("Sample API call received");
//        ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8080/dummy-url", String.class);
//        return entity.getBody();
        return "success api";
    }

    public String hardcodedResponse(Exception exe){
        logger.info("Fallback response: Service is down. Please try later.");
        return "fallback-response";
    }
}


/*
     Let's  understand @Retry(name = "sample-api",fallbackMethod = "hardcodedResponse")
     Here in this GetMapping we are trying to call a dummy URL which is not present and while calling this
     our API will throw error as this url is not up. Meaning behind this, if we are calling a Microservice which are
     down or give the response slow then in that case rather than throwing error we can retry to call the api again
     so that if the service was slow then it can give response else sometime service go down for a millisecond and when
     we try to reattempt it get success.

     There inside the annotation name is the service instance name on which this retry get identify in application.properties
     And, fallbackMethod is the method name which will call when we retry to call the API again but every time, server
     give the error. In that case this method will get execute to handle the Exception.
     Reason behind it, rather than giving the unwanted response we can show our own custom response.
 */
