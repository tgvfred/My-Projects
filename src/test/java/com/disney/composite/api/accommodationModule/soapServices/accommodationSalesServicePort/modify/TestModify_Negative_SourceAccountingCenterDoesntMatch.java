package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.TestReporter;

public class TestModify_Negative_SourceAccountingCenterDoesntMatch extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        DVCSalesBaseTest.environment = environment;
        setValues("80010383", "TB", "61", "11");
        setUseExistingValues(true);
        setUseDvcResort(true);
        setValues();
        setRetrieveAfterBook(false);
        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        DVCSalesBaseTest.environment = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative", "GCAL" })
    public void testModify_Negative_SourceAccountingCenterDoesntMatch() {
        String errorMessage = "Source Accounting Center Not Matching :  Modification Not Allowed";
        Modify modify = new Modify(getFirstBooking());

        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/resortCode", "15");
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/roomDetail/roomTypeCode", "5A");
        modify.setRequestNodeValueByXPath("/Envelope/Body/modify/request/locationId", "10068");
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.SOURCE_ACCOUNTING_CENTER_NOT_MATCHING, errorMessage);
    }

    private void validateError(Modify modify, ApplicationErrorCode error, String errorMessage) {
        TestReporter.logAPI(!modify.getFaultString().trim().toLowerCase().contains(errorMessage.trim().toLowerCase()), "Validate expected error message [ " + errorMessage + " ] is returned in response [ " + modify.getFaultString() + " ]", modify);
        validateApplicationError(modify, error);
    }
}
