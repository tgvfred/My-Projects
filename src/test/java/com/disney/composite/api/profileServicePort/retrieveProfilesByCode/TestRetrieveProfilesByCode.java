package com.disney.composite.api.profileServicePort.retrieveProfilesByCode;

import java.util.Map.Entry;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfiles;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfilesByCode;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;

public class TestRetrieveProfilesByCode extends BaseTest{
	protected GetOptions go;
	protected RetrieveProfiles retrieve;

	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
//		this.environment = "Development";
		go = new GetOptions(this.environment);
		go.setProfileOptionEnumType("PROFILE_TYPE");
		go.sendRequest();
		TestReporter.logAPI(!go.getResponseStatusCode().equals("200"), "An error occured getting options for enum type [PROFILE_TYPE].", go);
		
		for(Entry<String, String> entry : go.getOptionsKeyValuePairs().entrySet()){
			retrieve = new RetrieveProfiles(environment);
			retrieve.setEnterpriseFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
			retrieve.setIncludeInactiveProfiles("false");
			retrieve.setPackageId(BaseSoapCommands.REMOVE_NODE.toString());
			retrieve.setResortCode(BaseSoapCommands.REMOVE_NODE.toString());
			retrieve.setRetrieveOnlyDefaultProfiles("false");
			retrieve.setRetrieveOnlySelectableProfiles("false");
			retrieve.setRoomTypeCode(BaseSoapCommands.REMOVE_NODE.toString());
			retrieve.setProfileType(entry.getKey());
			retrieve.sendRequest();
			if(retrieve.getResponseStatusCode().equals("200")){
				retrieve.setProfileInfos();
				break;
			}
		}
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving profiles", retrieve);
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByCode(){
		RetrieveProfilesByCode retrieveByCode = new RetrieveProfilesByCode(environment);
		retrieveByCode.setIncludeInactiveProfiles("false");
		retrieveByCode.setProfileCode(retrieve.getProfileInfosCodes()[0]);
		retrieveByCode.sendRequest();
		TestReporter.logAPI(!retrieveByCode.getResponseStatusCode().equals("200"), "An error occurred retrieving profiles by code", retrieveByCode);
	}
}
