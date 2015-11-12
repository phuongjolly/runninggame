package com.banhtieu.serverkit.annotation;


/**
 * Room is used for a setup connected user
 */
public @interface RoomInfo {

	/**
	 * Name of room
	 * @return
	 */
	public String name();
	
	/**
	 * Maximum user
	 * @return
	 */
	public int maxUser() default 0;
	
}
