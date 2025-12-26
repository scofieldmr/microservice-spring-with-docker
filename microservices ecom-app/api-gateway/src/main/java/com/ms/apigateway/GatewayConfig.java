package com.ms.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/v1/product/**")
//                        .filters(f -> f.rewritePath(
//                                "/product(?<segment>/?.*)",
//                                "/api/v1/product${segment}"))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("user-service",r -> r
                        .path("/api/v1/user/**")
//                        .filters(f -> f.rewritePath(
//                                "/user(?<segment>/?.*)",
//                                "/api/v1/user${segment}"))
                        .uri("lb://USER-SERVICE"))
                .route("order-service", r -> r
                        .path("/api/v1/cart/**")
//                        .filters(f -> f.rewritePath(
//                                "/cart(?<segment>/?.*)",
//                                "/api/v1/cart${segment}"))
                        .uri("lb://ORDER-SERVICE"))
                .route("order-service", r -> r
                        .path("/api/v1/order/**")
//                        .filters(f -> f.rewritePath(
//                                "/order(?<segment>/?.*)",
//                                "/api/v1/order${segment}"))
                        .uri("lb://ORDER-SERVICE"))
                .route("eureka-server", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }


}
