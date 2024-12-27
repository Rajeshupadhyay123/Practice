package com.rajesh.practice.currency_conversioin_service.proxy;

import com.rajesh.practice.currency_conversioin_service.bean.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange",url = "localhost:8000")
@FeignClient(name = "currency-exchange") //Here we are using the Load Balancer so no need "url"
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrivrExchangeValue(
            @PathVariable("from") String from,
            @PathVariable("to") String to
    );
}


//Key Features of Feign
//Declarative Client: Define REST endpoints as Java interfaces.
//Integration with Spring Cloud: Works seamlessly with Spring Cloud for service discovery (Eureka, Consul).
//Load Balancing: Integrates with Ribbon for client-side load balancing.
//Pluggable Features: Easily integrates with interceptors, custom configurations, and fault tolerance mechanisms
// (e.g., Hystrix, Resilience4J).
//Minimal Code: Reduces the need for manual RestTemplate configuration and boilerplate.


//How Feign Works
//Feign works by processing the interface annotations and generating a dynamic implementation at runtime
// that handles HTTP requests and responses for the specified endpoints.


//When to Use Feign
//To call REST APIs declaratively.
//When you want to simplify HTTP communication in microservices architecture.
//To integrate with service discovery (like Eureka) for calling other microservices.
//For load balancing and fault tolerance in distributed systems.


//How to Use:
//1.Use the @EnableFeignClients annotation in your main application class:

//2. Create an interface with methods representing the external service’s endpoints.
//   Use @FeignClient to specify the base URL or service name.
//   Here while using the @FeignClient we can pass 2 parameter, 1. name and 2. url
//name: name should be the name of microservice that register with service discovery(i.e: eureka) or in application.properties
//    for which we want to create the proxy
//url:  Base URL of the external service. (optional when we will use the load balancing with service discovery like eureka)

//Note: url is optional with load balancer because with url we need to provide the port and load balancer manage
//the port dynamically that not needed to provide in advance

//3. Inject the Feign client into your service and call the methods like a normal Java method.














