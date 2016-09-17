package com.disney.composite.api.profileModule.profileServicePort.getOptions;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.applicationError.ProfileErrorCode;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.utils.TestReporter;

public class TestGetOptions_Negative extends BaseTest{
	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptions_Invalid_EnumType(){
		GetOptions go = new GetOptions(environment);
		go.setProfileOptionEnumType("ITEM_BLAH");
		go.sendRequest();
		validateApplicationError(go, ProfileErrorCode.PROFILE_OPTION_ENUM_MISSING);
		TestReporter.logAPI(!go.getFaultString().contains("ProfileOptionEnum should not be null"), go.getFaultString() ,go);
	}
	
}