package com.disney.composite.api.profileModule.profileServicePort.retrieveProfilesById;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.RetrieveProfilesById;
import com.disney.utils.TestReporter;

public class TestRetrieveProfilesById extends BaseTest{

	@Test(groups = {"api", "presmoke", "regression", "profile", "ProfileServiceIF"})
	public void Presmoke_testRetrieveProfilesByCode(){
		TestReporter.logScenario("Retrieve Profiles By Code");
		RetrieveProfilesById retrieveById = new RetrieveProfilesById(environment);
		retrieveById.setIncludeInactiveProfiles("false");
		retrieveById.setProfileId("400");
		retrieveById.sendRequest();
		TestReporter.logAPI(!retrieveById.getResponseStatusCode().equals("200"), "An error occurred retrieving profiles by code", retrieveById);
	
	}
}
