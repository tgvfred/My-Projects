package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.partyModule.partyService.operations.GetOptions;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail_DEVICE_TYPE extends BaseTest {
    Map<String, String> allPairs = new HashMap<String, String>();

    // accommodation sales request grabs data providers from party service response
    // @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_DEVICE_TYPE(String key, String value) {

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("DEVICE_TYPE");

        getOptionDetail.setOptionKeyVal(key.split(",")[0]);

        getOptionDetail.sendRequest();
        // System.out.print(getOptionDetail.getRequest());
        // System.out.println(getOptionDetail.getResponse());
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getOptionDetail.getFaultString(), getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(value.split(",")[0]), "The response Option VALUE [" + getOptionDetail.getOptionValue() + "] matches the PartyService getOptions value [" + value.split(",")[0] + "].");
        TestReporter.assertAll();

    }

    // grabs the GetOptions operation from the Party Service Port and sends a request to get a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {
        GetOptions getOptions = new GetOptions(environment);
        getOptions.setOptionType("DEVICE_TYPE");
        getOptions.sendRequest();

        TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), "Error in the Party Service request. Response status code not 200.", getOptions);

        String OptionKey = "";
        String OptionV = "";
        int numberOfOptionKeys = 0;

        numberOfOptionKeys = getOptions.getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsResponse/return/optionKey");

        for (int index = 1; index <= numberOfOptionKeys; index++) {

            OptionKey = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionKey");
            OptionV = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionValue");

            allPairs.put(OptionKey, OptionV);
        }

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
