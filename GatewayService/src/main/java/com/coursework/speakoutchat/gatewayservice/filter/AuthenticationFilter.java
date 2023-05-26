package com.coursework.speakoutchat.gatewayservice.filter;

import com.coursework.speakoutchat.gatewayservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing auth header");
                }

                List<String> authHeaders = exchange.getRequest().getHeaders()
                        .get(HttpHeaders.AUTHORIZATION);
                String authHeader = "";
                if (authHeaders != null && authHeaders.size() > 0 && authHeaders.get(0).startsWith("Bearer ")) {
                    authHeader = authHeaders.get(0).substring(7);

                }
                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
