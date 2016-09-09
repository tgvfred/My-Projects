package com.disney.composite.api.profileModule.profileServicePort.retrieveProfilesByCode;

import java.util.Map.Entry;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.RetrieveProfiles;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.RetrieveProfilesByCode;
import com.disney.utils.TestReporter;

/**
 * The following steps are taken for this test:
 * 		<ol>
 * 			<li>Get profile options using the GetOptions operation and the enum type "PROFILE_TYPE."</li>
 * 			<li>Iterate over each returned option type and use that to retrieve profiles with the RetrieveProfiles operation.</li>
 * 			<li>Once profiles are returned, the loop is exited.</li>
 * 			<li>The code from the first returned profile is then used to test the RetrieveProfilesByCode operation.</li>
 * 			<li>Validations are performed to ensure the expected values match the actual values for various fields.</li>
 * 		</ol>
 * @author Waits Avery
 *
 */
public class TestRetrieveProfilesByCode extends BaseTest{
	protected GetOptions go;
	protected RetrieveProfiles retrieve;

	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){		
		// Get all options for the enum value "PROFILE_TYPE"
		this.environment = environment;
		go = new GetOptions(this.environment);
		go.setProfileOptionEnumType("PROFILE_TYPE");
		go.sendRequest();
		TestReporter.logAPI(!go.getResponseStatusCode().equals("200"), "An error occured getting options for enum type [PROFILE_TYPE].", go);
		
		// Iterate over the options from the response and use them to retrieve profiles.  Once profiles are returned, break out of the loop.
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
		TestReporter.logScenario("Retrieve Profiles By Code");
		RetrieveProfilesByCode retrieveByCode = new RetrieveProfilesByCode(environment);
		retrieveByCode.setIncludeInactiveProfiles("false");
		retrieveByCode.setProfileCode(retrieve.getProfileInfosCodes()[0]);
		retrieveByCode.sendRequest();
		TestReporter.logAPI(!retrieveByCode.getResponseStatusCode().equals("200"), "An error occurred retrieving profiles by code", retrieveByCode);
		
		TestReporter.assertEquals(retrieve.getProfileInfosActives()[0], retrieveByCode.getActive(), "Verify the actual active value ["+retrieveByCode.getActive()+"] matches that which was expected ["+retrieve.getProfileInfosActives()[0]+"].");
		TestReporter.assertEquals(retrieve.getProfileInfosAPrioritys()[0], retrieveByCode.getPriority(), "Verify the actual priority value ["+retrieveByCode.getPriority()+"] matches that which was expected ["+retrieve.getProfileInfosAPrioritys()[0]+"].");
		TestReporter.assertEquals(retrieve.getProfileInfosDescriptions()[0], retrieveByCode.getDescription(), "Verify the actual description ["+retrieveByCode.getDescription()+"] matches that which was expected ["+retrieve.getProfileInfosDescriptions()[0]+"].");
		TestReporter.assertEquals(retrieve.getProfileInfosProfileIds()[0], retrieveByCode.getProfileId(), "Verify the actual ID ["+retrieveByCode.getProfileId()+"] matches that which was expected ["+retrieve.getProfileInfosProfileIds()[0]+"].");
		TestReporter.assertEquals(retrieve.getProfileInfosProfileTypes()[0], retrieveByCode.getProfileType(), "Verify the actual type ["+retrieveByCode.getProfileType()+"] matches that which was expected ["+retrieve.getProfileInfosProfileTypes()[0]+"].");
	}
}
