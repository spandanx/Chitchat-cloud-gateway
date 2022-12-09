package com.example.cloudgateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudGatewayConfig {

//	@Autowired
//	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route("user", r -> r.path("/user-service/**").filters(f -> f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://user-service"))
				.route("WebSocketModule", r -> r.path("/ws-service/**").filters(f -> f.rewritePath("/ws-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://WS-SERVICE"))
				.build();
	}
//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//		return builder.routes().route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth"))
//				.route("alert", r -> r.path("/alert/**").filters(f -> f.filter(filter)).uri("lb://alert"))
//				.route("echo", r -> r.path("/echo/**").filters(f -> f.filter(filter)).uri("lb://echo"))
//				.route("hello", r -> r.path("/hello/**").filters(f -> f.filter(filter)).uri("lb://hello")).build();
//	}

}