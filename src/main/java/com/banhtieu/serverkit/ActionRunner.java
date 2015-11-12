package com.banhtieu.serverkit;

import java.lang.reflect.Method;

import com.banhtieu.serverkit.model.Identity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Action Runner
 * @author banhtieu
 *
 */
public class ActionRunner implements Runnable {

	private Identity identity;
	
	private Method actionMethod;
	
	private Object targetObject;
	
	private JsonNode parameter;
	
	/**
	 * The action method
	 * @param targetObject
	 * @param actionMethod
	 * @param identity
	 * @param parameter
	 */
	public ActionRunner(Object targetObject, 
			Method actionMethod, 
			Identity identity, 
			JsonNode parameter) {
		this.targetObject = targetObject;
		this.actionMethod = actionMethod;
		this.identity = identity;
		this.parameter = parameter;
	}
	
	
	/**
	 * Execute the action
	 */
	@Override
	public void run() {
		
		try {
			// get the second parameter
			Class<?> parameterType = actionMethod.getParameters()[1].getClass();
			ObjectMapper mapper = new ObjectMapper();
			Object parameterData = mapper.treeToValue(parameter, parameterType);
			
			//Class<?> returnType = actionMethod.getReturnType();
			//Object returnData = null;
			
			//returnData = 
			actionMethod.invoke(targetObject, identity, parameterData);
			
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
}
