package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.partyServicePort.operations.GetOptions;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail_ROLE extends AccommodationBaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
    }

    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {
        GetOptions getOptions = new GetOptions(Environment.getBaseEnvironmentName(environment));
        getOptions.setOptionType("ROLE");
        getOptions.sendRequest();
        System.out.println(getOptions.getResponse());
        System.out.println(getOptions.getRequest());
        TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), "testing]", getOptions);

        String OptionKey = "";
        // String OptionK[] = new String[23];
        String OptionV, OptionV2 = "";
        int numberOfOptionKeys = 0;
        // int numberOfOptionValues = 0;
        numberOfOptionKeys = getOptions.getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsResponse/return/optionKey");

        System.out.println(numberOfOptionKeys);
        for (int index = 1; index <= numberOfOptionKeys; index++) {
            // int index = 1;
            OptionKey = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionKey");
            OptionV = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + index + "]/optionValue");
            // OptionK2 = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + 2 + "]/optionKey");
            // OptionV2 = getOptions.getResponseNodeValueByXPath("/Envelope/Body/getOptionsResponse/return[" + 2 + "]/optionKey");

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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_ROLE(String key, String value) {
        System.out.println(key);
        System.out.println(value);
        // String optionKey = "";
        // int numberOfOptionKeys = 0;
        // int numberOfOptionValues = 0;
        // String optionKeyResponses;
        // String optionValueResponses;

        // GetOptions getOptions = new GetOptions(Environment.getBaseEnvironmentName(environment));
        // getOptions.setOptionType("ROLE");
        // getOptions.sendRequest();
        // TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), "testing]", getOptions);

        // optionKey = getOptions.getOptionKey();
        // System.out.println(getOptions.getRequest());
        // System.out.println(getOptions.getResponse());
        //
        // GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        // getOptionDetail.setOptionKeyVal(Ok);
        // getOptionDetail.setAccommodationSalesOptionsEnum("ROLE");
        // getOptionDetail.sendRequest();

        // System.out.println(OV);
        System.out.println("Test Passed maybe?");
        // numberOfOptionKeys = getOptions.getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsResponse/return/optionKey");
        // numberOfOptionValues = getOptions.getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionsResponse/return/optionValue");

    }

}