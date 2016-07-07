package com.disney.composite.api.profileServicePort.retrieveProfilesByRoutingType;

import java.util.Map.Entry;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfiles;
import com.disney.api.soapServices.profileServicePort.operations.RetrieveProfilesByRoutingType;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveProfilesByRoutingType extends BaseTest{
	protected GetOptions go;
	protected RetrieveProfiles retrieve;
	protected int indexOfNodeWithRoutingType = -1;

	@Override
	@BeforeTest
	@Parameters("environment")
	public void setup(@Optional String environment){
		
		// Get all options for the enum value "PROFILE_TYPE"
		this.environment = "Development";
		go = new GetOptions(this.environment);
		go.setProfileOptionEnumType("PROFILE_TYPE");
		go.sendRequest();
		TestReporter.logAPI(!go.getResponseStatusCode().equals("200"), "An error occured getting options for enum type [PROFILE_TYPE].", go);
		
		// Iterate over the options from the response and use them to retrieve profiles.  Once profiles are returned, break out of the loop.
		for(Entry<String, String> entry : go.getOptionsKeyValuePairs().entrySet()){
			retrieve = new RetrieveProfiles(this.environment);
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
		
		for(int i = 0; i < retrieve.getProfileInfosRoutingTypes().length; i++){
			if(!retrieve.getProfileInfosRoutingTypes()[i].isEmpty()){
				indexOfNodeWithRoutingType = i;
				break;
			}
		}
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() > 0, "Verify that the number of profiles in the response ["+retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes()+"] is greater than zero.");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_ProfileCode(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type and Profile Code");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				retrieve.getProfileInfosCodes()[indexOfNodeWithRoutingType], BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() > 0, "Verify that the number of profiles in the response ["+retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes()+"] is greater than zero.");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_ProfileCodeProfileType(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type, Profile Code, and Profile Type");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				retrieve.getProfileInfosCodes()[indexOfNodeWithRoutingType], retrieve.getProfileInfosProfileTypes()[indexOfNodeWithRoutingType], BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() > 0, "Verify that the number of profiles in the response ["+retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes()+"] is greater than zero.");
		
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getActive(), retrieve.getProfileInfosActives()[indexOfNodeWithRoutingType], "The actual profile active value ["+retrieveProfilesByRoutingType.getActive()+"] did not match the expected active value ["+retrieve.getProfileInfosActives()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getCode(), retrieve.getProfileInfosCodes()[indexOfNodeWithRoutingType], "The actual profile code ["+retrieveProfilesByRoutingType.getCode()+"] did not match the expected code ["+retrieve.getProfileInfosCodes()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getDescription(), retrieve.getProfileInfosDescriptions()[indexOfNodeWithRoutingType], "The actual profile description ["+retrieveProfilesByRoutingType.getDescription()+"] did not match the expected desciption ["+retrieve.getProfileInfosDescriptions()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getPriority(), retrieve.getProfileInfosAPrioritys()[indexOfNodeWithRoutingType], "The actual profile priority ["+retrieveProfilesByRoutingType.getPriority()+"] did not match the expected priority ["+retrieve.getProfileInfosAPrioritys()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getProfileId(), retrieve.getProfileInfosProfileIds()[indexOfNodeWithRoutingType], "The actual profile ID ["+retrieveProfilesByRoutingType.getProfileId()+"] did not match the expected ID ["+retrieve.getProfileInfosProfileIds()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getProfileType(), retrieve.getProfileInfosProfileTypes()[indexOfNodeWithRoutingType], "The actual profile type ["+retrieveProfilesByRoutingType.getProfileType()+"] did not match the expected type ["+retrieve.getProfileInfosProfileTypes()[indexOfNodeWithRoutingType]+"].");
		TestReporter.assertEquals(retrieveProfilesByRoutingType.getRoutingType(), retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType], "The actual profile rounting type ["+retrieveProfilesByRoutingType.getRoutingType()+"] did not match the expected routing type ["+retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType]+"].");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_ProfileType(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type and Profile Type");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				BaseSoapCommands.REMOVE_NODE.toString(), retrieve.getProfileInfosProfileTypes()[indexOfNodeWithRoutingType], BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() > 0, "Verify that the number of profiles in the response ["+retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes()+"] is greater than zero.");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_InvalidProfileType(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type and Invalid Profile Type");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				BaseSoapCommands.REMOVE_NODE.toString(), Randomness.randomString(4), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() > 0, "Verify that the number of profiles in the response ["+retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes()+"] is greater than zero.");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_InvalidProfileCode(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type and Invalid Profile Code");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				Randomness.randomString(4), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() == 0, "Verify that no profiles are returned in the reponse.");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testRetrieveProfilesByRoutingType_InvalidProfileCodeAndProfileType(){
		if(indexOfNodeWithRoutingType == -1) throw new AutomationException("A profile type was identified that returned profiles.  However, no profile was found that had a routing type, which is required for this scenario.");
		TestReporter.logScenario("Retrieve Profiles By Routing Type, and Invalid Profile Code and Profile");
		RetrieveProfilesByRoutingType retrieveProfilesByRoutingType = setRetrieveProfilesInstance(retrieve.getProfileInfosRoutingTypes()[indexOfNodeWithRoutingType],
				Randomness.randomString(4), Randomness.randomString(4), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(retrieveProfilesByRoutingType.getNumberofProfileDetailsNodes() == 0, "Verify that no profiles are returned in the reponse.");
	}
	
	private RetrieveProfilesByRoutingType setRetrieveProfilesInstance(String routingType, String profileCode, String profileType, String inactiveProfiles){
		RetrieveProfilesByRoutingType rpbrt = new RetrieveProfilesByRoutingType(environment);
		rpbrt.setRoutingType(routingType);
		rpbrt.setProfileCode(profileCode);
		rpbrt.setProfileType(profileType);
		rpbrt.setIncludeInactiveProfiles(inactiveProfiles);
		rpbrt.sendRequest();
		TestReporter.logAPI(!rpbrt.getResponseStatusCode().equals("200"), "An error occurred retrieving profiles by routing type.", rpbrt);
		return rpbrt;
	}
}