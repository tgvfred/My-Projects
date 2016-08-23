package com.disney.api.soapServices;

public class ServiceConstants {
	
	/*
	 * Booking Source Types
	 */

	public static class BookingSource{
		public static final String DREAMS_TC = "DREAMS_TC";
		public static final String DREAMS_TCFEE = "DREAMS_TCFEE";
		public static final String DREAMS_TCG = "DREAMS_TCG";
		public static final String DREAMS_TP = "DREAMS_TP";
		public static final String DREAMS_TPS = "DREAMS_TPS";
	}
	
	/*
	 * Folio External Reference Types
	 */
	public static class FolioExternalReference{
		public static final String ACCOVIA = "ACCOVIA";
		public static final String ACCOVIACFN = "ACCOVIACFN";
		public static final String ADMISSION_ENTITLEMENT_NUMBER = "ADMISSION_ENTITLEMENT_NUMBER";
		public static final String AMADEUS = "AMADEUS";
		public static final String AMEX_PROXY_ID = "AMEX_PROXY_ID";
		public static final String APOLLO = "APOLLO";
		public static final String ATS = "ATS";
		public static final String CRSITIN = "CRSITIN";
		public static final String CRSR = "CRSR";
		public static final String DCL = "DCL";
		public static final String DSCS = "DSCS";
		public static final String DPMSBLOCK = "DPMSBLOCK";
		public static final String DPMSBOOKING = "DPMSBOOKING";
		public static final String DPMSGROUPPROFILE = "DPMSGROUPPROFILE";
		public static final String DPMSPROPERTY = "DPMSPROPERTY";
		public static final String DREAMS_TC = "DREAMS_TC";
		public static final String DREAMS_TCFEE = "DREAMS_TCFEE";
		public static final String DREAMS_TCG = "DREAMS_TCG";
		public static final String DREAMS_TP = "DREAMS_TP";
		public static final String DREAMS_TPS = "DREAMS_TPS";
		public static final String DVC = "DVC";
		public static final String GALILEO = "GALILEO";
		public static final String ITINERARY = "ITINERARY";
		public static final String JAZZ = "JAZZ";
		public static final String KTTW = "KTTW";
		public static final String LILO_UI = "LILO_UI";
		public static final String MEMBER = "MEMBER";
		public static final String ROOM = "ROOM";
		public static final String RTP = "RTP";
		public static final String SABRE = "SABRE";
		public static final String SECURE_ID = "SECURE_ID";
		public static final String SOGGEN = "SOGGEN";
		public static final String SOGSP2 = "SOGSP2";
		public static final String VISUAL_ID = "VISUAL_ID";
		public static final String VISIT_ACTIVITY = "VISIT_ACTIVITY";
		public static final String VISIT_ACTIVITY_SCHEDULE = "VISIT_ACTIVITY_SCHEDULE";
		public static final String WORLDSPAN = "WORLDSPAN";
	}
	
	/*
	 * Folio Types
	 */
	public static class FolioType{
		public static final String CHARGE_GROUP = "CHARGE_GROUP";
		public static final String EXTERNAL_ORGANIZATION = "EXTERNAL_ORGANIZATION";
		public static final String INTERNAL_ORGANIZATION = "INTERNAL_ORGANIZATION";
		public static final String INDIVIDUAL = "INDIVIDUAL";
		public static final String THIRD_PARTY_PAY = "THIRD_PARTY_PAY";
		public static final String TELEPHONE_SUSPENSE = "TELEPHONE_SUSPENSE";
		public static final String UNAGROUPMASTER = "UNAGROUPMASTER";
		public static final String UNAGUESTBOOKING = "UNAGUESTBOOKING";
	}
	
	public static class SeGuestRequests{

		public static final String BOOSTER_SEAT = "Booster Seat";
		public static final String BOOSTER_SEAT_ID = "400";
		public static final String BOOSTER_SEAT_CODE = "GRBS";

		public static final String HIGH_CHAIR = "High Chair";
		public static final String HIGH_CHAIR_ID = "404";
		public static final String HIGH_CHAIR_CODE = "GRHC";

		public static final String REQUEST_HIGH_CHAIR = "REQUEST HIGH CHAIR";
		public static final String REQUEST_HIGH_CHAIR_ID = "1068";
		public static final String REQUEST_HIGH_CHAIR_CODE = "!HC";

		public static final String REQUEST_TWO_HIGH_CHAIRS = "REQUEST TWO HIGH CHAIRS";
		public static final String REQUEST_TWO_HIGH_CHAIRS_ID = "1069";
		public static final String REQUEST_TWO_HIGH_CHAIRS_CODE = "!HC2";
	}
	
	public static class SeSpecialNeeds{
		public static final String SPECIAL_DIETARY_REQUEST = "Special Dietary Request";
		public static final String SPECIAL_DIETARY_REQUEST_ID = "819";
		public static final String SPECIAL_DIETARY_REQUEST_CODE = "SNDR";

		public static final String HEARING_LOSS = "Hearing loss";
		public static final String HEARING_LOSS_ID = "821";
		public static final String HEARING_LOSS_CODE = "SNHL";

		public static final String LIMITED_MOBILITY = "Limited mobility";
		public static final String LIMITED_MOBILITY_ID = "822";
		public static final String LIMITED_MOBILITY_CODE = "SNLM";

		public static final String NON_APPARENT_DISABILITY = "Non-apparent disability";
		public static final String NON_APPARENT_DISABILITY_ID = "823";
		public static final String NON_APPARENT_DISABILITY_CODE = "SNND";

		public static final String OXYGEN_TANK_USE = "Oxygen tank use";
		public static final String OXYGEN_TANK_USE_ID = "824";
		public static final String OXYGEN_TANK_USE_CODE = "SNOTU";

		public static final String SERVICE_ANIMAL = "Service Animal";
		public static final String SERVICE_ANIMAL_ID = "825";
		public static final String SERVICE_ANIMAL_CODE = "SNSA";

		public static final String VISUAL_DISABILITY = "Visual disability";
		public static final String VISUAL_DISABILITY_ID = "826";
		public static final String VISUAL_DISABILITY_CODE = "SNVD";
		
		public static final String WHEELCHAIR_ACCESSIBILITY = "Wheelchair accessibility";
		public static final String WHEELCHAIR_ACCESSIBILITY_ID = "827";
		public static final String WHEELCHAIR_ACCESSIBILITY_CODE = "SNWA";
	}
}

/*	public enum bookingSource{
DREAMS_TC,
DREAMS_TCFEE,
DREAMS_TCG,
DREAMS_TP,
DREAMS_TPS;
}
public enum folioExternalReference{
ACCOVIA,
ACCOVIACFN,
ADMISSION_ENTITLEMENT_NUMBER,
AMADEUS,
AMEX_PROXY_ID,
APOLLO,
ATS,
CRSITIN,
CRSR,
DCL,
DSCS,
DPMSBLOCK,
DPMSBOOKING,
DPMSGROUPPROFILE,
DPMSPROPERTY,
DREAMS_TC,
DREAMS_TCFEE,
DREAMS_TCG,
DREAMS_TP,
DREAMS_TPS,
DVC,
GALILEO,
ITINERARY,
JAZZ,
KTTW,
LILO_UI,
MEMBER,
ROOM,
RTP,
SABRE,
SECURE_ID,
SOGGEN,
SOGSP2,
VISUAL_ID,
VISIT_ACTIVITY,
VISIT_ACTIVITY_SCHEDULE,
WORLDSPAN;
}

public enum folioType{
CHARGE_GROUP,
EXTERNAL_ORGANIZATION,
INTERNAL_ORGANIZATION, 
INDIVIDUAL,		
THIRD_PARTY_PAY,
TELEPHONE_SUSPENSE,
UNAGROUPMASTER,
UNAGUESTBOOKING;
}*/
