package com.rajesh.practice.api_gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class LoginFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("path of the request received -> {}",exchange.getRequest().getPath());
        logger.info("Response Status Code -> {}",exchange.getResponse().getStatusCode());
        return chain.filter(exchange);
    }
}


/*
1️⃣ What is a GlobalFilter?
A GlobalFilter in Spring Cloud API Gateway is a filter that applies to all routes passing through the Gateway.
It is used for pre-processing (before sending a request) and post-processing (after receiving a response) of API requests.

2️⃣ Use Cases of GlobalFilter
✅ Logging Requests and Responses (Monitor incoming requests and outgoing responses).
✅ Authentication & Authorization (Verify tokens, API keys, or user roles).
✅ Request Modification (Add headers, parameters, or modify request body).
✅ Response Modification (Modify response body or headers before returning to the client).
✅ Rate Limiting (Restrict excessive API calls per user or IP).
✅ Request Validation (Check required parameters before forwarding).


 Explanation of Code:
1️⃣ Pre-processing (Before Request is Sent)
Logs the incoming request URI (exchange.getRequest().getURI()).
2️⃣ Processing (Pass Request to Next Filter/Service)
Calls chain.filter(exchange), which sends the request to the appropriate microservice.
3️⃣ Post-processing (After Response is Received)
Logs the response status code (exchange.getResponse().getStatusCode()).

Difference between GlobalFilter and GatewayFilter:-
GlobalFilter ->
Feature :
Scope : Applies to all requests
Customization : Can modify requests, responses, and logging globally
Implementation : Implements GlobalFilter


GatewayFilter ->
Feature :
Scope : Applies to specific routes
Customization : Used for route-specific tasks (e.g., adding headers, validation)
Implementation : Uses .filters() in route configuration


Execution Order: GlobalFilter vs GatewayFilter in Spring Cloud Gateway
The execution order of filters in Spring Cloud Gateway follows this sequence:

1️⃣ Global Pre-Filters (GlobalFilter) → Executed before request routing.
2️⃣ Route-Specific Filters (GatewayFilter) → Applied to specific routes.
3️⃣ Request is forwarded to the microservice.
4️⃣ Response is received from the microservice.
5️⃣ Route-Specific Filters (GatewayFilter) (Post-processing) → Modify the response for a specific route.
6️⃣ Global Post-Filters (GlobalFilter) → Final modifications before sending the response to the client.
 */
