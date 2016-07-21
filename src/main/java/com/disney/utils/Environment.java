package com.disney.utils;

public class Environment {
	
	public static String getEnvironmentName(String env){
		switch(env.toLowerCase()){
			case "latest": return "Bashful";
			case "stage": return "Sleepy";
			case "shadow": return "Grumpy";
			
			/*
			 * Entries for Composite Modernization
			 */
			case "latest_cm": return "Bashful_CM";
			case "stage_cm": return "Sleepy_CM";
			case "shadow_cm": return "Grumpy_CM";
			default: return env;
		}
	}
}
