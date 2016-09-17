package com.disney.composite.api.profileModule.profileServicePort.getOptionsByFilter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.ProfileErrorCode;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptionsByFilter;
import com.disney.utils.TestReporter;

public class TestGetOptionsByFilter_Negative extends BaseTest{
	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = "latest";}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptionsByFilter_InvalidEnumType(){
		TestReporter.logScenario("Test GetOptions for Enum Type [ BLAH ], filter key of [ SERVICE_TYPE ] and filter value of [ Delivery ]");
		GetOptionsByFilter go = new GetOptionsByFilter(environment);
		go.setProfileOptionEnumType("BLAH");
		go.setProfileOptionKey("SERVICE_TYPE");
		go.setProfileOptionValue("Delivery");
		go.sendRequest();
		validateApplicationError(go, ProfileErrorCode.PROFILE_OPTION_ENUM_MISSING);
		TestReporter.logAPI(!go.getFaultString().contains("ProfileOptionEnum should not be null"), go.getFaultString() ,go);
	}
	

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptionsByFilter_InvalidFilterKey(){
		TestReporter.logScenario("Test GetOptions for Enum Type [ ITEM ], filter key of [ BLAH ] and filter value of [ Delivery ]");
		GetOptionsByFilter go = new GetOptionsByFilter(environment);
		go.setProfileOptionEnumType("ITEM");
		go.setProfileOptionKey("BLAH");
		go.setProfileOptionValue("Delivery");
		go.sendRequest();
		validateApplicationError(go, ProfileErrorCode.PROFILE_FILTER_OPTION_ENUM_MISSING);
		TestReporter.logAPI(!go.getFaultString().contains("ProfileFilterOptionEnum should not be null"), go.getFaultString() ,go);
	}

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptionsByFilter_InvalidFilterValue(){
		TestReporter.logScenario("Test GetOptions for Enum Type [ ITEM ], filter key of [ SERVICE_TYPE ] and filter value of [ BLAH ]");
		GetOptionsByFilter go = new GetOptionsByFilter(environment);
		go.setProfileOptionEnumType("ITEM");
		go.setProfileOptionKey("SERVICE_TYPE");
		go.setProfileOptionValue("BLAH");
		go.sendRequest();
		
		TestReporter.logAPI(go.getOptionsKeyValuePairs().size() != 0, "Filter was not supposed to return any data" ,go);
	}
}