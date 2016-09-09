package com.disney.utils.dataFactory.guestFactory.seeds;

import java.util.Random;

public class States {
	private static String[] states = {
		"alabama",
		"alaska",
		"arizona",
		"arkansas",
		"california",
		"colorado",
		"connecticut",
		"delaware",
		"florida",
		"georgia",
		"hawaii",
		"idaho",
		"illinois",
		"indiana",
		"iowa",
		"kansas",
		"kentucky",
		"louisiana",
		"maine",
		"maryland",
		"massachusetts",
		"michigan",
		"minnesota",
		"mississippi",
		"missouri",
		"montana",
		"nebraska",
		"nevada",
		"new hampshire",
		"new jersey",
		"new mexico",
		"new york",
		"north carolina",
		"north dakota",
		"ohio",
		"oklahoma",
		"oregon",
		"pennsylvania",
		"rhode island",
		"south carolina",
		"south dakota",
		"tennessee",
		"texas",
		"utah",
		"vermont",
		"virginia",
		"washington",
		"west virginia",
		"wisconsin",
		"wyoming"
	};
	public static String getState(){

	    Random r = new Random();
	    return states[r.nextInt(states.length)];
	}
}
