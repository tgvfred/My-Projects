package com.disney.api.soapServices.chargeGroup.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.chargeGroup.ChargeGroup;
import com.disney.utils.XMLTools;

public class RetrieveNonGuaranteedGuestChargeGroups extends ChargeGroup{
	private Map<String, String> reservations = new HashMap<String, String>();
	private int numberOfReservations;
	
	public RetrieveNonGuaranteedGuestChargeGroups(String environment) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveNonGuaranteedGuestChargeGroups")));
	
		generateServiceContext();
//		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setRunDate(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveNonGuaranteedGuestChargeGroups/runDate", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveNonGuaranteedGuestChargeGroups/sourceAcctCtrId", value);}
	public int getNumberOfReservations(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveNonGuaranteedGuestChargeGroupsResponse/returnParameter/descendentChargeGroups").getLength();}
	public Map<String, String> getAllReservations(){
		numberOfReservations = getNumberOfReservations();
		for(int i = 1; i <=numberOfReservations; i++){
			reservations.put(String.valueOf(i), getReferenceValue(i));
		}		
		
		return reservations;
	}
	
	public String getReferenceName(int index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveNonGuaranteedGuestChargeGroupsResponse/returnParameter/descendentChargeGroups["+index+"]/primaryExternalReference/referenceName");}
	public String getReferenceValue(int index){return getResponseNodeValueByXPath("/Envelope/Body/retrieveNonGuaranteedGuestChargeGroupsResponse/returnParameter/descendentChargeGroups["+index+"]/primaryExternalReference/referenceValue");}
}
