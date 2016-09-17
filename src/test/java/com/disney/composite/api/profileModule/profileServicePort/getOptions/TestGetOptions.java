package com.disney.composite.api.profileModule.profileServicePort.getOptions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.utils.TestReporter;

/**
 * These script test the functionality of ProfileOptionEnums ROUTING_TYPE, PROFILE_TYPE, SERVICE_TYPE, and ITEM
 * @author Justin Phlegar
 * @see <a href="http://profilesvcgrpy.wdw.disney.com/Profile/ProfileServicePort?xsd=Profile_enums.xsd">ProfileOptionEnum</a>
 * 
 */
public class TestGetOptions extends BaseTest{
	
	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptions_ROUTING_TYPE(){
		testGetOptions("ROUTING_TYPE");
	}
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptions_PROFILE_TYPE(){
		testGetOptions("PROFILE_TYPE");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptions_SERVICE_TYPE(){
		testGetOptions("SERVICE_TYPE");
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptions_ITEM(){
		testGetOptions("ITEM");
	}
	
	private void testGetOptions(String enumType){
		TestReporter.logScenario("Test GetOptions for Enum Type ["+enumType+"]");
		GetOptions go = new GetOptions(environment);
		go.setProfileOptionEnumType(enumType);
		go.sendRequest();
		TestReporter.logAPI(!go.getResponseStatusCode().equals("200"), "An error occured getting options for enum type ["+enumType+"].", go);
		TestReporter.assertTrue(go.getReponseOptions().getLength() > 0, "No options were returned for enum type ["+enumType+"].");
	}
}