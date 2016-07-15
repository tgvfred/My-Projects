package com.disney.composite.api.profileServicePort.retrieveProfiles;

import java.util.Map.Entry;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfiles;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class TestRetrieveProfiles extends BaseTest{
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
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfiles_ProfileType(){
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
		
		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getProfileInfosCodes()[0]), "Verify the code ["+retrieve.getProfileInfosCodes()[0]+"] is numeric.");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosDescriptions()[0]), "Verify the description ["+retrieve.getProfileInfosDescriptions()[0]+"] is alphanumeric.");
		TestReporter.assertTrue(Regex.match("[0-9]+", retrieve.getProfileInfosProfileIds()[0]), "Verify the id ["+retrieve.getProfileInfosProfileIds()[0]+"] is numeric");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosProfileTypes()[0]), "Verify the profile type ["+retrieve.getProfileInfosProfileTypes()[0]+"] is alphabetic.");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosLastUpdateBys()[0]), "Verify the last updated by ["+retrieve.getProfileInfosLastUpdateBys()[0]+"] is alphanumeric.");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosLastUpdateOns()[0]), "Verify the last updated on ["+retrieve.getProfileInfosLastUpdateOns()[0]+"] is alphanumeric.");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosCreatedBys()[0]), "Verify the created by ["+retrieve.getProfileInfosCreatedBys()[0]+"] is alphanumeric.");
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z].*", retrieve.getProfileInfosCreatedOns()[0]), "Verify the created on ["+retrieve.getProfileInfosCreatedOns()[0]+"] is alphanumeric.");
		TestReporter.assertTrue(isBoolean(retrieve.getProfileInfosActives()[0]), "Verify the active ["+retrieve.getProfileInfosActives()[0]+"] is a boolean string");
		TestReporter.assertTrue(isBoolean(retrieve.getProfileInfosAPrioritys()[0]), "Verify the priority ["+retrieve.getProfileInfosAPrioritys()[0]+"] is a boolean string.");
	}
	
	private boolean isBoolean(String string){
		boolean isBoolean = false;
		if(string.trim().toLowerCase().equals("true") || string.trim().toLowerCase().equals("false")) isBoolean = true;
		return isBoolean;
	}
}