package com.disney.api.soapServices.commonDoorService.operations;

import org.testng.Assert;
import org.testng.Reporter;

import com.disney.api.soapServices.commonDoorService.CommonDoorService;
import com.disney.utils.XMLTools;


public class RetrieveAuthorizedAccessGroup extends CommonDoorService{

	public RetrieveAuthorizedAccessGroup(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAuthorizedAccessGroup")));
		//System.out.println(getRequest());
		
		//generateServiceContext();			
		//setRequestNodeValueByXPath(getTestScenario("services/commonDoorServices/addAuthorizedAccessGroup/retrieveAuthorizedAccessGroup_Input.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void validateResortAccess(String access, String facilityId, String resourceId){
		boolean isFacilityIdFound = false;
		boolean isResourceIdFound = false;
		
		isFacilityIdFound = validateNodeContainsValueByXPathAndLocatorValue(getResponseDocument(),"/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='authorizedAccessGroups'][1]/*[local-name(.)='authorizedAccesses']", "true", facilityId );
		isResourceIdFound = validateNodeContainsValueByXPathAndLocatorValue(getResponseDocument(),"/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='authorizedAccessGroups'][1]/*[local-name(.)='authorizedAccesses']", "true", resourceId );
		
		if(isFacilityIdFound == isResourceIdFound && isFacilityIdFound == true){
			Assert.assertEquals(isFacilityIdFound, true, "Access to facility ID \b[" +facilityId+ "]\b was found.");
			Assert.assertEquals(isResourceIdFound, true, "Access to facility ID \b[" +facilityId+ "]\b was found.");
			Reporter.log("Both facility ID \b["  +facilityId+ "]\b and resource ID \b[" +resourceId+ "]\b were found to have access", true);
		}else if(isFacilityIdFound == isResourceIdFound && isFacilityIdFound == false){
			Reporter.log("Neither the facility ID \b[" +facilityId+ "]\b not the resource ID \b[" +resourceId+ "]\b were found in the response.", true);
			Assert.fail("Neither the facility ID \b[" +facilityId+ "]\b not the resource ID \b[" +resourceId+ "]\b were found in the response.");
		}else if(isFacilityIdFound == false){
			Reporter.log("The facility ID \b[" +facilityId+ "]/b was found to have access but the resource ID \b[" +resourceId+ "]\b was not contained in the same node branch in the response.", true);
			Assert.fail("The facility ID \b[" +facilityId+ "]/b was found to have access but the resource ID \b[" +resourceId+ "]\b was not contained in the same node branch in the response.");
		}
		else{
			Reporter.log("The resource ID \b[" +resourceId+ "]/b was found to have access but the facilty ID \b[" +facilityId+ "]\b was not the facility expected to be associated with the resource ID.", true);
			Assert.fail("The resource ID \b[" +resourceId+ "]/b was found to have access but the facilty ID \b[" +facilityId+ "]\b was not the facility expected to be associated with the resource ID.");			
		}
	}
	
	public void setGroupName(String groupName){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAuthorizedAccessGroup'][1]/*[local-name(.)='authAcsGrpName'][1]", groupName);
	}
	
	public void setGroupId(String groupId){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAuthorizedAccessGroup'][1]/*[local-name(.)='authAcsGrpId'][1]", groupId);
	}
	
	public String getGroupId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='retrieveAuthorizedAccessGroupResponse'][1]/*[local-name(.)='authorizedAccessGroups'][1]/*[local-name(.)='id'][1]");
	}
}
