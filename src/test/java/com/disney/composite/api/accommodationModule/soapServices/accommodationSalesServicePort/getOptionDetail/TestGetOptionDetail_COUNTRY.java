package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.partyServicePort.operations.GetOptions;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail_COUNTRY extends AccommodationBaseTest {
    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_COUNTRY(String key, String value) {

        // System.out.println(key);
        // System.out.println(value);

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("COUNTRY");

        getOptionDetail.setOptionKeyVal(key.split(",")[0]);

        getOptionDetail.sendRequest();
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getOptionDetail.getFaultString(), getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");
        TestReporter.assertTrue(getOptionDetail.getOptionValue().contains(value.split(",")[0]), "The response Option VALUE [" + getOptionDetail.getOptionValue() + "] matches the PartyService getOptions value [" + value.split(",")[0] + "].");
        TestReporter.assertAll();

    }

    // grabs the GetOptions operation from the Party Service Port and sends a request to get a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {
        GetOptions getOptions = new GetOptions(Environment.getBaseEnvironmentName(environment));
        getOptions.setOptionType("COUNTRY");
        getOptions.sendRequest();
        System.out.println(getOptions.getResponse());
        System.out.println(getOptions.getRequest());
        TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), "testing]", getOptions);

        String OptionKey = "";
        String OptionV = "";
        int numberOfOptionKeys = 0;

        numberOfOptionKeys = getOptions.getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsResponse/return/optionKey");
        System.out.println(numberOfOptionKeys);

        for (int index = 1; index <= numberOfOptionKeys; index++) {

            OptionKey = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionKey");
            OptionV = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionValue");

            allPairs.put(OptionKey, OptionV);
        }

        System.out.println(allPairs.values());

        Object[][] objKeyValue = new Object[allPairs.size()][2];
        int i = 0;
        for (String key : allPairs.keySet()) {
            objKeyValue[i][0] = key;
            objKeyValue[i][1] = allPairs.get(key);
            i++;
        }

        return objKeyValue;

    }

}
