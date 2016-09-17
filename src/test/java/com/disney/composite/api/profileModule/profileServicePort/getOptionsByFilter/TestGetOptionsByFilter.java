package com.disney.composite.api.profileModule.profileServicePort.getOptionsByFilter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptionsByFilter;
import com.disney.utils.TestReporter;

public class TestGetOptionsByFilter extends BaseTest{
	@Override
	@BeforeMethod
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptionsByFilter_Maintenance(){
		testGetOptionsByFilter("Maintenance");
	}


	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptionsByFilter_Pickup(){
		testGetOptionsByFilter("Pickup");
	}

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"})
	public void testGetOptionsByFilter_Delivery(){
		testGetOptionsByFilter("Delivery");
	}

	
	private void testGetOptionsByFilter(String value){
		TestReporter.logScenario("Test GetOptions for Enum Type [ ITEM ], filter key of [ SERVICE_TYPE ] and filter value of [ " + value+ " ]");
		GetOptionsByFilter go = new GetOptionsByFilter(environment);
		go.setProfileOptionEnumType("ITEM");
		go.setProfileOptionKey("SERVICE_TYPE");
		go.setProfileOptionValue(value);
		go.sendRequest();
		TestReporter.logAPI(!go.getResponseStatusCode().equals("200"), "An error occured getting options for enum type ["+value+"].", go);
		TestReporter.assertTrue(go.getReponseOptions().getLength() > 0, "No options were returned for enum type ["+value+"].");
	}
}