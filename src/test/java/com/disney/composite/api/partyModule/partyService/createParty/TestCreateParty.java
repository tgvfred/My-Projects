package com.disney.composite.api.partyModule.partyService.createParty;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.bussvcsModule.guestServiceV2.operations.Create;
import com.disney.api.soapServices.partyModule.partyService.operations.CreateParty;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestCreateParty extends BaseTest{
	protected Create guest;
	protected CreateParty party;
	protected String partyId;
	protected String primaryGuestAge = String.valueOf( Randomness.randomNumberBetween(17, 99));
	protected String primaryGuestFirstName = Randomness.randomString(6);
	protected String primaryGuestLastName = Randomness.randomString(6);
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String env){
		environment = env;
		guest = new Create(environment, "Main");
		guest.sendRequest();
	}
	
	//@Test()
	public void testCreateParty(){
		party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(primaryGuestAge);
		party.setPrimaryGuestFirstName(primaryGuestFirstName);
		party.setPrimaryGuestLastName(primaryGuestLastName);
		party.setExternalReferenceValue(guest.getOdsGuestId());		
		party.setAddressLocatorId(guest.getAddressLocatorId());
		party.setAddressCity(guest.getCity());
		party.setAddressState(guest.getState());
		party.setAddressZipCode(guest.getPostalCode());
		party.setPhoneNumberLocatorId(guest.getPhoneLocatorId());
		party.setPhoneNumber(guest.getPhoneNumber());
		party.setEmailAddressLocatorId(guest.getEmailLocatorId());
		party.setEmailAddress(guest.getEmail());		
		party.sendRequest();
		TestReporter.logAPI(!party.getResponseStatusCode().equals("200"), "An error occurred creating a party.", party);
		partyId = party.getPartyid();
		TestReporter.assertTrue(Regex.match("[0-9]+", partyId), "The party ID ["+partyId+"] was not numeric as expected");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("PartyIF", "createParty", false);
		logValidItems.addItem("GuestServiceV1", "updateWithRestriction", true);
		validateLogs(party, logValidItems);
	}
}