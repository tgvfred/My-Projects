package com.disney.composite.api.partyService.searchGuestIDByExternalReference;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyService.operations.CreateParty;
import com.disney.api.soapServices.partyService.operations.SearchGuestIDByExternalReference;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByExternalReference  extends BaseTest{
	private Guest guest = new Guest();
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByODS(){
		TestReporter.logScenario("Search By External Reference Type and Value");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", guest.getOdsId());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByIataNumber(){		
		String type = "IATANUMBER";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDBySwid(){
		String type = "SWID";
		String value = "{" +  Randomness.generateMessageId() + "}";  // sample SWID ID = {1C97E407-7260-4614-97E4-0772605614E0}
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByGuid(){
		String type = "GUID";
		String value = Randomness.generateMessageId();  // sample GUID ID = k4fejpB7-PBswew-YSzym3-4Y3K0P-LV1YEJMTLq
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByPassportID(){
		String type = "PASSPORT";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByXbmsID(){
		String type = "XBMS";
		String value = Randomness.generateMessageId();  // sample XBMS ID = 275DB573-4B1D-4302-9737-D2F3B4CDF607
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByOriginalTxnGuestID(){
		String type = "ORIGINAL_TXN_GUEST_ID";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByFolioID(){
		String type = "FOLIOID";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByPlid(){
		String type = "PLID";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByDclGuestId(){
		String type = "DCLGUESTID";
		String value = String.valueOf(Randomness.randomNumber(9));
		Guest guest = new Guest();
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		CreateParty party = new CreateParty(environment, "SampleCreate");
		party.setPrimaryGuestAge(guest.getAge());
		party.setPrimaryGuestFirstName(guest.getFirstName());
		party.setPrimaryGuestLastName(guest.getLastName());
		party.setExternalReferenceSource(type);	
		party.setExternalReferenceValue(value);		
		party.setAddressLocatorId(guest.primaryAddress().getLocatorId());
		party.setAddressCity(guest.primaryAddress().getCity());
		party.setAddressState(guest.primaryAddress().getState());
		party.setAddressZipCode(guest.primaryAddress().getZipCode());
		party.setPhoneNumberLocatorId(guest.primaryPhone().getLocatorId());
		party.setPhoneNumber(guest.primaryPhone().getNumber());
		party.setEmailAddressLocatorId(guest.primaryEmail().getLocatorId());
		party.setEmailAddress(guest.primaryEmail().getEmail());		
		party.sendRequest();
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, value);
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(party.getPartyid()), "The Party ID ["+search.getPartyId()+"] is not ["+party.getPartyid()+"] as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Type_NoResults(){
		TestReporter.logScenario("Search By External Reference Type, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("?", guest.getOdsId());
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_NoTypeNode(){
		TestReporter.logScenario("Search By External Reference Type Node, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(BaseSoapCommands.REMOVE_NODE.toString(), guest.getOdsId());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Values_NoResults(){
		TestReporter.logScenario("Search By External Reference Value, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", "-1");
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_MultipleValues(){
		TestReporter.logScenario("Search By External Reference Type and Multiple Values");
		Guest guest2 = new Guest();
		guest2.sendToApi(environment);		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", guest.getOdsId());
		search.addExternalReferenceValue(guest2.getOdsId());
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 2, "Party IDs were found when none were expected.");

		LogItems logItems = new LogItems();
		logItems.addItem("PartyIF", "searchGuestIDByExternalReference", false);
		validateLogs(search, logItems);
	}
}