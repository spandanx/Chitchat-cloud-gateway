package com.example.cloudgateway.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import com.example.cloudgateway.filter.JwtAuthFilter;

@Configuration
public class CloudGatewayConfig {

//	@Autowired
//	private JwtAuthenticationFilter filter;
	
	@Autowired
	private JwtAuthFilter filter;
	
	@Value("${user-service.client-id}")
	private String userClientId;
	
	@Value("${user-service.client-secret}")
	private String userClientSecret;
	
	@Value("${ws-service.client-id}")
	private String wsClientId;
	
	@Value("${ws-service.client-secret}")
	private String wsClientSecret;

//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//		
//		return builder.routes()
//				.route("user", r -> r.path("/user-service/**").filters(f -> f.rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://user-service"))
//				.route("WebSocketModule", r -> r.path("/ws-service/**").filters(f -> f.rewritePath("/ws-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://WS-SERVICE"))
//				.build();
//	}
	
	private String encodeCreds(String id, String secret) {
		System.out.println("Calling encodeCreds()");
		String creds = id+":"+secret;
		String encoded = Base64.getEncoder().encodeToString(creds.getBytes());
		System.out.println("Encoded : "+encoded);
		return "Basic "+encoded;
	}
	
	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route("user", r -> r.path("/user-service/**").filters(f -> f.filter(filter).removeRequestHeader("Authorization").addRequestHeader("Authorization", encodeCreds(userClientId, userClientSecret)).rewritePath("/user-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://user-service"))
				.route("WebSocketModule", r -> r.path("/ws-service/**").filters(f -> f.addRequestHeader("Authorization", encodeCreds(wsClientId, wsClientSecret)).rewritePath("/ws-service(?<segment>/?.*)", "$\\{segment}")).uri("lb://ws-service"))
				.build();
		//filter(filter).removeRequestHeader("Authorization")
	}
	
//	@Bean
//	public ReactorNettyRequestUpgradeStrategy reactorNettyRequestUpgradeStrategy(
//			HttpClientProperties httpClientProperties) {
//		ReactorNettyRequestUpgradeStrategy requestUpgradeStrategy = new ReactorNettyRequestUpgradeStrategy();
//
//		HttpClientProperties.Websocket websocket = httpClientProperties
//				.getWebsocket();
//		PropertyMapper map = PropertyMapper.get();
//		map.from(websocket::getMaxFramePayloadLength).whenNonNull()
//				.to(requestUpgradeStrategy::setMaxFramePayloadLength);
//		map.from(websocket::isProxyPing).to(requestUpgradeStrategy::setHandlePing);
//		return requestUpgradeStrategy;
//	}
//	@Bean
//	public RouteLocator routes(RouteLocatorBuilder builder) {
//		return builder.routes().route("auth", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://auth"))
//				.route("alert", r -> r.path("/alert/**").filters(f -> f.filter(filter)).uri("lb://alert"))
//				.route("echo", r -> r.path("/echo/**").filters(f -> f.filter(filter)).uri("lb://echo"))
//				.route("hello", r -> r.path("/hello/**").filters(f -> f.filter(filter)).uri("lb://hello")).build();
//	}

}