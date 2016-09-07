package com.disney.api.soapServices.dvcModule.membershipService.operations;
import com.disney.api.soapServices.dvcModule.membershipService.MembershipService;
import com.disney.utils.XMLTools;

public class SearchMemberships extends MembershipService {
	
	public SearchMemberships(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchMemberships")));
//		System.out.println(getRequest());
		
		//Generate a dynamic request from the wsdl
		
//		System.out.println(getRequest());
		
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
//		System.out.println(getRequest());
		removeComments() ;
		removeWhiteSpace();
//		sendRequest();
	}
	
	public void setMembershipID(String membershipID){
		setRequestNodeValueByXPath("//searchMemberships/membershipId", membershipID);
	}
	
	public void setMemberID(String membershipID){
		setRequestNodeValueByXPath("//searchMemberships/member/@memberId", membershipID);
	}
	
	public void setAgeType(String ageType){
		setRequestNodeValueByXPath("//searchMemberships/member/personInfo/@ageType", ageType);
	}
	
	public void setIncludeCanceledContracts(String include){
		setRequestNodeValueByXPath("//searchMemberships/includeCanceledContracts", include);
	}
	
	public void setIncludeVoidedContracts(String include){
		setRequestNodeValueByXPath("//searchMemberships/includeVoidedContracts", include);
	}
	
	public String getMembershipID(){
		return getResponseNodeValueByXPath("//searchMemberships/member/@memberId");
	}
	
	public String getMembershipRefID(){
		return getResponseNodeValueByXPath("//searchMemberships/member/@memberRefId");
	}

}
