package com.disney.utils.dataFactory.guestFactory.seeds;

import java.util.Random;

public class LastNames {
	private static String[] names = {
		"adams",
		"alexander",
		"allen",
		"alvarez",
		"anderson",
		"andrews",
		"armstrong",
		"arnold",
		"austin",
		"bailey",
		"baker",
		"banks",
		"barnes",
		"barnett",
		"barrett",
		"bates",
		"beck",
		"bell",
		"bennett",
		"berry",
		"bishop",
		"black",
		"bowman",
		"boyd",
		"bradley",
		"brewer",
		"brooks",
		"brown",
		"bryant",
		"burke",
		"burns",
		"burton",
		"butler",
		"byrd",
		"caldwell",
		"campbell",
		"carlson",
		"carpenter",
		"carr",
		"carroll",
		"carter",
		"castillo",
		"castro",
		"chambers",
		"chapman",
		"chavez",
		"clark",
		"cole",
		"coleman",
		"collins",
		"cook",
		"cooper",
		"cox",
		"craig",
		"crawford",
		"cruz",
		"cunningham",
		"curtis",
		"daniels",
		"davidson",
		"davis",
		"day",
		"dean",
		"diaz",
		"dixon",
		"douglas",
		"duncan",
		"dunn",
		"edwards",
		"elliott",
		"ellis",
		"evans",
		"ferguson",
		"fernandez",
		"fields",
		"fisher",
		"fleming",
		"fletcher",
		"flores",
		"ford",
		"foster",
		"fowler",
		"fox",
		"franklin",
		"frazier",
		"freeman",
		"fuller",
		"garcia",
		"gardner",
		"garrett",
		"garza",
		"george",
		"gibson",
		"gilbert",
		"gomez",
		"gonzales",
		"gonzalez",
		"gordon",
		"graham",
		"grant",
		"graves",
		"gray",
		"green",
		"gregory",
		"griffin",
		"gutierrez",
		"hale",
		"hall",
		"hamilton",
		"hansen",
		"hanson",
		"harper",
		"harris",
		"harrison",
		"hart",
		"harvey",
		"hawkins",
		"hayes",
		"henderson",
		"henry",
		"hernandez",
		"herrera",
		"hicks",
		"hill",
		"hoffman",
		"holland",
		"holmes",
		"holt",
		"hopkins",
		"horton",
		"howard",
		"howell",
		"hudson",
		"hughes",
		"hunt",
		"hunter",
		"jackson",
		"jacobs",
		"james",
		"jenkins",
		"jennings",
		"jensen",
		"jimenez",
		"johnson",
		"johnston",
		"jones",
		"jordan",
		"kelley",
		"kelly",
		"kennedy",
		"kim",
		"king",
		"knight",
		"kuhn",
		"lambert",
		"lane",
		"larson",
		"lawrence",
		"lawson",
		"lee",
		"lewis",
		"little",
		"long",
		"lopez",
		"lowe",
		"lucas",
		"lynch",
		"marshall",
		"martin",
		"martinez",
		"mason",
		"matthews",
		"may",
		"mccoy",
		"mcdonalid",
		"mckinney",
		"medina",
		"mendoza",
		"meyer",
		"miles",
		"miller",
		"mills",
		"mitchell",
		"mitchelle",
		"montgomery",
		"moore",
		"morales",
		"moreno",
		"morgan",
		"morris",
		"morrison",
		"murphy",
		"murray",
		"myers",
		"neal",
		"nelson",
		"newman",
		"nguyen",
		"nichols",
		"obrien",
		"oliver",
		"olson",
		"ortiz",
		"owens",
		"palmer",
		"parker",
		"patterson",
		"payne",
		"pearson",
		"pena",
		"perez",
		"perkins",
		"perry",
		"peters",
		"peterson",
		"phillips",
		"pierce",
		"porter",
		"powell",
		"price",
		"ramirez",
		"ramos",
		"ray",
		"reed",
		"reid",
		"reyes",
		"reynolds",
		"rhodes",
		"rice",
		"richards",
		"richardson",
		"riley",
		"rivera",
		"roberts",
		"robertson",
		"robinson",
		"rodriguez",
		"rodriquez",
		"rogers",
		"romero",
		"rose",
		"ross",
		"ruiz",
		"russell",
		"ryan",
		"sanchez",
		"sanders",
		"schmidt",
		"scott",
		"shaw",
		"shelton",
		"silva",
		"simmmons",
		"simmons",
		"simpson",
		"sims",
		"smith",
		"snyder",
		"soto",
		"spencer",
		"stanley",
		"stephens",
		"stevens",
		"steward",
		"stewart",
		"stone",
		"sullivan",
		"sutton",
		"taylor",
		"terry",
		"thomas",
		"thompson",
		"torres",
		"tucker",
		"turner",
		"vargas",
		"vasquez",
		"wade",
		"wagner",
		"walker",
		"wallace",
		"walters",
		"ward",
		"warren",
		"washington",
		"watkins",
		"watson",
		"watts",
		"weaver",
		"webb",
		"welch",
		"wells",
		"west",
		"wheeler",
		"white",
		"williams",
		"williamson",
		"willis",
		"wilson",
		"wood",
		"woods",
		"wright",
		"young"
	};
	public static String getLastName(){

	    Random r = new Random();
	    return names[r.nextInt(names.length)];
	}
}
