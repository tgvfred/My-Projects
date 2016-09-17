package com.disney.composite.api.profileModule.profileServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.profileModule.profileServicePort.operations.GetOptions;
import com.disney.api.BaseTest;
import com.disney.utils.TestReporter;

/**
 * This script will test the return of all availble options for the ITEM ProfileOptionEnum. At the time of this script. ITEM is the only valid Enum type this 
 * accepts. Using the response of ProfileServicePort.operations.GetOptions as dataprovider, this will spin up a test for every option return in GetOptions for
 * enum type ITEM. Each test iteration ensures the data integraty is consant and maintain accross operations.
 *  
 * @author Justin Phlegar
 * 
 */
public class TestGetOptionDetail extends BaseTest{
	@DataProvider(name = "dp", parallel=true)
	public Object[][] dp(){
		Map<String, String> allPairs = new HashMap<String, String>();
		boolean error = false;
		GetOptions getOptions = new GetOptions(environment);
		getOptions.setProfileOptionEnumType("ITEM");

		int k = 0;
		try{
			getOptions.sendRequest();
			if(!getOptions.getResponseStatusCode().equals("200")) error = true;
		}catch(Exception e){
			error = true;
		}
		if(!error){
			Map<String,String> pairs = null;
			pairs  = getOptions.getOptionsKeyValuePairs();
			for(String key : pairs.keySet()){
				allPairs.put(String.valueOf(k)+":ITEM", key+":"+pairs.get(key));
				k++;
			}
		}else{
			allPairs.put(String.valueOf(k)+":ITEM", "null");
			k++;
		}
		Object[][] objKeyValue = new Object[allPairs.size()][2];
		int i = 0;
		for(String key : allPairs.keySet()){
			objKeyValue[i][0] = key;
			objKeyValue[i][1] = allPairs.get(key);
			i++;
		}
		
		return objKeyValue;
	}
	@Override
	@BeforeClass
	@Parameters("environment")
	public void setup(@Optional String environment){this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF"}, dataProvider="dp", dependsOnMethods="testGetOptionDetail_SingleOptions")
	public void testGetOptionDetail_AllOptions(String key, String value){
		GetOptionDetail getDetail = new GetOptionDetail(environment);
		getDetail.setProfileOptionEnumType("ITEM");
		getDetail.setProfileOptionKey(value.split(":")[0]);
		getDetail.sendRequest();
		TestReporter.logAPI(!getDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getDetail.getFaultString(), getDetail);
		TestReporter.assertTrue(getDetail.getResponseOptionValue().equals(value.split(":")[1]), "Verify actual value ["+getDetail.getResponseOptionValue()+"] is that which was expected ["+value.split(":")[1]+"].");
	}
	

	@Test(groups = {"api", "regression", "profile", "ProfileServiceIF", "smoke"})
	public void testGetOptionDetail_SingleOptions(){
		GetOptionDetail getDetail = new GetOptionDetail(environment);
		getDetail.setProfileOptionEnumType("ITEM");
		getDetail.setProfileOptionKey("AFD");
		getDetail.sendRequest();
		TestReporter.logAPI(!getDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getDetail.getFaultString(), getDetail);
		TestReporter.assertTrue(getDetail.getResponseOptionValue().equals("Adjustable Frequency Drive"), "Verify actual value ["+getDetail.getResponseOptionValue()+"] is that which was expected [Adjustable Frequency Drive].");
	}
	
}