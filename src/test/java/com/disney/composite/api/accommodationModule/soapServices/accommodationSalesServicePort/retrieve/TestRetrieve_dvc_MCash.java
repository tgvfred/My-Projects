package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;

public class TestRetrieve_dvc_MCash extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(removeCM(environment));
        setUseDvcResort(true);
        setRetrieveAfterBook(false);
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
        validateApplicationError(retrieve, AccommodationErrorCode.RETRIEVE_DVC_RESERVATION);
    }

}
