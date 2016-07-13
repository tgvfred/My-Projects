package com.disney.utils;

public class Environment {
	
	public static String getEnvironmentName(String env){
		switch(env.toLowerCase()){
			case "latest": return "Bashful";
			case "stage": return "Sleepy";
			case "shadow": return "Grumpy";
			default: return env;
		}
	}
}
