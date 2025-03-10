package com.rajesh.practice.api_gateway.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoint=List.of(
            "/auth/register",
            "/auth/login",
            "auth/validate/token/**",
            "/auth/refreshToken",
            "/kafka/publish",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured=
            request-> openApiEndPoint.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
