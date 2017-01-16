package com.disney.api.soapServices.accommodationModule.availabilityWSPort.operations;

import com.disney.api.soapServices.accommodationModule.availabilityWSPort.AvailabilityWSPort;
import com.disney.utils.XMLTools;

public class FreezeInventory extends AvailabilityWSPort{
	
	public FreezeInventory(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("freezeInventory")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setOverrideFreeze(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/overrideFreeze", value);}
	public void setPackageCode(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/packageCode", value);}
	public void setPartyMixAge(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/age", value);}
	public void setPartyMixAgeType(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/ageType", value);}
	public void setPartyMixTitle(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/title", value);}
	public void setPartyMixFirstName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/firstName", value);}
	public void setPartyMixLastName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/lastName", value);}
	public void setPartyMixPartyId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/partyId", value);}
	public void setPartyMixDoNotMail(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/doNotMailIndicator", value);}
	public void setPartyMixDoNotPhone(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/doNotPhoneIndicator", value);}
	public void setPartyMixDclGuestId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/dclGuestId", value);}
	public void setPartyMixGuestId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/guestId", value);}
	public void setPartyMixActive(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/guest/active", value);}
	public void setPartyMixPurposeOfVisit(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/purposeOfVisit", value);}
	public void setPartyMixRole(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/role", value);}
	public void setPartyMixCorrelationId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/partyMix["+index+"]/correlationID", value);}
	public void setResortCode(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/resortCode", value);}
	public void setResortPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/resortPeriod/endDate", value);}
	public void setResortPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/resortPeriod/startDate", value);}
	public void setRoomCode(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/newFreezeDetail/roomTypeCode", value);}
	public void setWholesaler(String value){setRequestNodeValueByXPath("/Envelope/Body/freezeInventory/freezeRequest/wholeSaler", value);}
	
	public String getFreezeId(){return getResponseNodeValueByXPath("/Envelope/Body/freezeInventoryResponse/return/freezeId");}
}
