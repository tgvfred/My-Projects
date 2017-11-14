package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_dvc_MCash extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(removeCM(environment));
        setUseDvcResort(true);
        setCheckingIn(true);
        setBook(bookDvcReservation("testBook_MCash", 1));
        setTpId(getFirstBooking().getTravelPlanId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_dvc_MCash() {

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(tpId.get());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.baseValidationDVC(getFirstBooking(), retrieve);
        helper.dvcMembershipValidations(retrieve, getFirstMember());

        // Old vs New
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (retrieve.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }
    }

}