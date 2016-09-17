package com.disney.composite.api.profileModule.profileServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.ProfileErrorCode;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.api.BaseTest;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail_Negative extends BaseTest{

	@Override
	@BeforeClass
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;
	}

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptionDetail_InvalidEnumType(){
		GetOptionDetail getDetail = new GetOptionDetail(environment);
		getDetail.setProfileOptionEnumType("BLAH");
		getDetail.setProfileOptionKey("AFD");
		getDetail.sendRequest();
		validateApplicationError(getDetail, ProfileErrorCode.PROFILE_OPTION_ENUM_MISSING);
		TestReporter.logAPI(!getDetail.getFaultString().contains("ProfileOptionEnum should not be null"), getDetail.getFaultString() ,getDetail);
	}

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "negative"})
	public void testGetOptionDetail_InvalidOptionKey(){
		GetOptionDetail getDetail = new GetOptionDetail(environment);
		getDetail.setProfileOptionEnumType("ITEM");
		getDetail.setProfileOptionKey("BLAH");
		getDetail.sendRequest();
		TestReporter.logAPI(getDetail.getNumberResponseNodes() != 0, getDetail.getFaultString() ,getDetail);
	}
}