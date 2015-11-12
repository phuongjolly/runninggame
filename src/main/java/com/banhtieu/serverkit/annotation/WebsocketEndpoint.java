package com.banhtieu.serverkit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

/**
 * Interface for a lobby annotation. 
 * Lobby is the main 
 * @author banhtieu
 */
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface WebsocketEndpoint {

	/**
	 * Name of the lobby
	 * @return
	 */
	public String name() default "lobby";
	
	/**
	 * The End Point of the Lobby
	 * @return
	 */
	public String endPoint() default "/endpoint";
	
	
	/**
	 * Allow origin to connected to
	 * @return
	 */
	public String allowOrigins() default "*";
}
