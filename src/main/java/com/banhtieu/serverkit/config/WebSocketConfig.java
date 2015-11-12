package com.banhtieu.serverkit.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.banhtieu.serverkit.annotation.WebsocketEndpoint;


/**
 * Configuration for web socket
 * @author banhtieu
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	
	/**
	 * Injected the application context here
	 */
	@Autowired
	private ApplicationContext context;
	
	/**
	 * Registered all web socket handlers
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// scan for all lobby
		Collection<Object> endPoints = context.getBeansWithAnnotation(WebsocketEndpoint.class).values();
		
		// make a loop through all lobbies
		for (Object endPoint : endPoints) {
			WebsocketEndpoint endPointInfo = endPoint.getClass().getAnnotation(WebsocketEndpoint.class);
			
			registry.addHandler((WebSocketHandler) endPoint, endPointInfo.endPoint())
						.setAllowedOrigins(endPointInfo.allowOrigins());
		}
	}

}
