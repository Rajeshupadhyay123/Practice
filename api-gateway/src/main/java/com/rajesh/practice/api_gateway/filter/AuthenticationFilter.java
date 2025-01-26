package com.rajesh.practice.api_gateway.filter;


import com.rajesh.practice.api_gateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(validator.isSecured.test(exchange.getRequest())){

                // Refresh the request with the current headers
                ServerHttpRequest mutatedRequest= exchange.getRequest().mutate().build();
                ServerWebExchange mutatedExchange= exchange.mutate().request(mutatedRequest).build();

                //header contain token or not
                logger.info("Token is: {}", mutatedExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));



                String tokenContain=mutatedExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if(tokenContain==null || tokenContain.isEmpty()){
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                    //throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                String jwtToken = null;
                if(authHeader!=null && authHeader.startsWith("Bearer ")){
                    jwtToken  = authHeader.substring(7);
                }

                try{
                    logger.info("Token is: {}", jwtToken);
                    boolean validated = jwtUtil.validateToken(jwtToken);

                    logger.info("Token valid flag: {}", validated);
                    if (!validated){
                        throw new RuntimeException("un authorized access");
                    }
                }catch (Exception e){
                    throw new RuntimeException("Un authorized access");
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config{

    }
}
