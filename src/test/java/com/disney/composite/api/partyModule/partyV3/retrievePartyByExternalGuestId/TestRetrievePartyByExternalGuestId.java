package com.disney.composite.api.partyModule.partyV3.retrievePartyByExternalGuestId;

import org.testng.annotations.Test;

import com.disney.api.soapServices.partyModule.partyV3.operations.RetrievePartyByExternalGuestId;
import com.disney.api.soapServices.partyModule.partyV3.operations.SearchGuestIDByEmail;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class TestRetrievePartyByExternalGuestId extends BaseTest{
	@Test(groups = {"api", "presmoke", "partyV3"})
	public void Presmoke_testRetrievePartyByExternalGuestId(){
		RetrievePartyByExternalGuestId retrieve= new RetrievePartyByExternalGuestId("Virtual");
		retrieve.setExternalGuestIDAndSource("1234567890", "ODS");
		retrieve.sendRequest();
		
		TestReporter.logAPI(!retrieve.getResponseStatusCode().contains("200"), retrieve.getFaultString(), retrieve);
		TestReporter.assertTrue(retrieve.isPartyFound(), "The return message did not contain [Party Information Found]. ");
	}
}
