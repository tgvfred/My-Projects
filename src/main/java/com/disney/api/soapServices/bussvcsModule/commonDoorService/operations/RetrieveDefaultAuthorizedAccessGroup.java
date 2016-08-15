package com.disney.api.soapServices.bussvcsModule.commonDoorService.operations;

import org.testng.Assert;
import org.testng.Reporter;

import com.disney.api.soapServices.bussvcsModule.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class RetrieveDefaultAuthorizedAccessGroup extends CommonDoorService{

	public RetrieveDefaultAuthorizedAccessGroup(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDefaultAuthorizedAccessGroup")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/quickbook/quickbookInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
		System.out.println(getRequest());
	}
	
	public void validateResortAccessForGroup(String[] groupNumberAndName){
		boolean isGroupNumberFound = false;
		boolean isGroupNameFound = false;
		
		isGroupNumberFound = XMLTools.validateNodeContainsValueByXPath(getResponseDocument(),"/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveDefaultAuthorizedAccessGroupResponse'][1]/*[local-name(.)='defaultAuthorizedAccessGroupResponse'][1]/*[local-name(.)='authorizedAccessGroups']/*[local-name(.)='id']", groupNumberAndName[0] );
		isGroupNameFound = XMLTools.validateNodeContainsValueByXPath(getResponseDocument(),"/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveDefaultAuthorizedAccessGroupResponse'][1]/*[local-name(.)='defaultAuthorizedAccessGroupResponse'][1]/*[local-name(.)='authorizedAccessGroups']/*[local-name(.)='authAcsGrpName']", groupNumberAndName[1] );
		
		if(isGroupNumberFound == isGroupNameFound && isGroupNumberFound == true){
			Assert.assertEquals(isGroupNumberFound, true, "Group ID \b[" +groupNumberAndName[0]+ "]\b was found in the response.");
			Assert.assertEquals(isGroupNameFound, true, "Group name \b[" +groupNumberAndName[1]+ "]\b was found in the response");
			Reporter.log("Both group ID \b["  +groupNumberAndName[0]+ "]\b and group name \b[" +groupNumberAndName[1]+ "]\b were found to have access", true);
		}else if(isGroupNumberFound == isGroupNameFound && isGroupNumberFound == false){
			Reporter.log("Neither the group ID \b[" +groupNumberAndName[0]+ "]\b not the group name \b[" +groupNumberAndName[1]+ "]\b were found in the response.", true);
			Assert.fail("Neither the group ID \b[" +groupNumberAndName[0]+ "]\b not the group name \b[" +groupNumberAndName[1]+ "]\b were found in the response.");
		}else if(isGroupNumberFound == false){
			Reporter.log("The group ID \b[" +groupNumberAndName[0]+ "]/b was found to have access but the group name \b[" +groupNumberAndName[1]+ "]\b was not contained in the same node branch in the response.", true);
			Assert.fail("The group ID \b[" +groupNumberAndName[0]+ "]/b was found to have access but the group name \b[" +groupNumberAndName[1]+ "]\b was not contained in the same node branch in the response.");
		}
		else{
			Reporter.log("The group name \b[" +groupNumberAndName[1]+ "]/b was found to have access but the group ID \b[" +groupNumberAndName[0]+ "]\b was not the group name expected to be associated with the group ID.", true);
			Assert.fail("The group name \b[" +groupNumberAndName[1]+ "]/b was found to have access but the group ID \b[" +groupNumberAndName[0]+ "]\b was not the group name expected to be associated with the group ID.");			
		}
	}
}
