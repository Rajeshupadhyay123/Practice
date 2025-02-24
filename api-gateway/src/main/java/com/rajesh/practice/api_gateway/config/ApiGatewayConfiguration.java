package com.rajesh.practice.api_gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoute(RouteLocatorBuilder builder){

//        Function<PredicateSpec, Buildable<Route>> routeFunction
//                =p -> p.path("/get")
//                .filters(f -> f.
//                        addRequestParameter("MyHeader","MyURI")
//                        .addRequestParameter("Param","MyValue"))
//                .uri("http://httpbin.org:80");
//        return builder.routes()
//                .route(routeFunction)
//                .build();
//    }

        return builder.routes()
                .route(p -> p.path("/get")
                .filters(f -> f.
                        addRequestParameter("MyHeader","MyURI")
                        .addRequestParameter("Param","MyValue"))
                .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .route("Spring-kafka",p -> p.path("/kafka/**")
                        .uri("lb://Spring-kafka"))
                .route("identify-service",p -> p.path("/auth/**")
                        .uri("lb://identify-service"))
                .build();
    }
}

//http://localhost:8765/kafka/publish?message=are you okay

/*
    Here let's Understand the logic of rewritePath() methods.
    We first need to find the URL which will capture and then move to the target URL.
    So, In the route()  we capture the path of prefix while using the /** means other part of URL,
    And, then move to the target URL using uri() methods.

    Note : Here we are creating a new URL : /currency-conversion-new which is not present.
    And we want to move to the target URL, SO we are using the regular expression Here for storing the rest part
    of the URL which was previously used as /** into the segment variable.
    And then use this variable in target URL.
-----------------------------------------------------------------------------------------------------------
    Let's Understand : (?<segment>.*)
   (?<segment>.*) is a named capturing group in a regular expression (regex).
🔹 It extracts part of the URL path that comes after /currency-conversion-new/.
🔹 This extracted value is stored in a named variable called segment, which is then used in the rewritten URL.

Example 1: Before and After URL Transformation
Incoming Request:
http://localhost:8080/currency-conversion-new/USD-INR

Regex Matching:

/currency-conversion-new/(?<segment>.*) → Extracts "USD-INR" and assigns it to <segment>.

Rewritten Path:
/currency-conversion-feign/USD-INR

Why Do We Use <segment>?
✅ Flexible URL Manipulation → Instead of hardcoding paths, we dynamically extract and reuse parts of the URL.
✅ Avoids Manual String Manipulation → The regex automatically extracts the required part of the URL.
✅ Supports Multiple Dynamic Paths → Works for any number of dynamic segments like /currency-conversion-new/EUR-USD, /currency-conversion-new/GBP-INR, etc.
✅ Decouples Client and Service Endpoints → Clients request /currency-conversion-new/, but the actual service runs on /currency-conversion-feign/.

 Without Segment:
 .route(p -> p.path("/currency-conversion-new/USD-INR")
    .uri("lb://currency-conversion-feign"))
.route(p -> p.path("/currency-conversion-new/EUR-USD")
    .uri("lb://currency-conversion-feign"))

Note: Why Segment in place of /**


Why Other Routes Use /**?
.route(p -> p.path("/currency-exchange/**")
        .uri("lb://currency-exchange"))

📌 Here, /** means:
Any request that starts with /currency-exchange/ will be forwarded as is to lb://currency-exchange.
Request:  /currency-exchange/USD-INR
Forwarded to: lb://currency-exchange/USD-INR

Why Use <segment> in rewritePath()?
.route(p -> p.path("/currency-conversion-new/**")
    .filters(f -> f.rewritePath(
        "/currency-conversion-new/(?<segment>.*)",
        "/currency-conversion-feign/${segment}"
    ))
    .uri("lb://currency-conversion"))


📌 Here’s why <segment> is needed:
Unlike the other routes, this modifies the request path.
/** would forward everything without modification, but here we need to transform the path.
 .route(p -> p.path("/currency-conversion-new/**")
    .uri("lb://currency-conversion-feign"))

Problem:
/currency-conversion-new/USD-INR would be forwarded as /currency-conversion-new/USD-INR,
But the currency-conversion-feign service expects /currency-conversion-feign/USD-INR instead.
Wrong endpoint mapping! ❌

.route(p -> p.path("/currency-conversion-new/**")
    .filters(f -> f.rewritePath(
        "/currency-conversion-new/(?<segment>.*)",
        "/currency-conversion-feign/${segment}"
    ))
    .uri("lb://currency-conversion"))


Now, /currency-conversion-new/USD-INR is rewritten as /currency-conversion-feign/USD-INR before being forwarded.
✅ The target service receives the expected path.


----------------------------------------------------------------------------------------------------------
Meaning of lb://CURRENCY-SERVICE in Spring Cloud Gateway:

The prefix lb:// in lb://CURRENCY-SERVICE stands for Load Balancer, and it is used in Spring Cloud Gateway
 to indicate that the request should be routed through Eureka Service Discovery using Spring Cloud Load Balancer.

 1️⃣ What Happens When We Use lb://CURRENCY-SERVICE?
👉 Spring Cloud Gateway does not route to a fixed URL.
👉 Instead, it looks up CURRENCY-SERVICE in Eureka and dynamically picks an available instance.

2️⃣ How It Works Internally?
1. The API Gateway receives the request.
2. It checks the Eureka Server for a registered service named "CURRENCY-SERVICE".
3. If multiple instances of CURRENCY-SERVICE are running, the Spring Cloud Load Balancer picks one instance using
 round-robin or another load balancing strategy.
4. The request is then forwarded to the selected instance.

4️⃣ Why Use lb:// Instead of Hardcoding URLs?
✅ Service Discovery & Dynamic Routing → No need to hardcode backend URLs; it dynamically resolves them.
✅ Load Balancing → Distributes traffic across multiple instances for better performance.
✅ Fault Tolerance → If one instance is down, requests are routed to another available instance.
✅ Scalability → Easily scale microservices without updating API Gateway routes.
 */



