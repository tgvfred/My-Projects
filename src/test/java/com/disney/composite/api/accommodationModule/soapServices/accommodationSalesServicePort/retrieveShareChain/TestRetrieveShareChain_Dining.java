package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveShareChain;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.RetrieveShareChainHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestRetrieveShareChain_Dining extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain", "negative" })
    public void retrieveSharechain_Dining() {

        String faultString = "Accommodations not found";

        ScheduledEventReservation res = new ShowDiningReservation(environment);
        res.setTravelPlanId(getBook().getTravelPlanId());
        res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        RetrieveShareChainHelper helper = new RetrieveShareChainHelper();
        helper.getTcgUsingTps(environment, res.getConfirmationNumber()); // confirmation number is the TPS ID

        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setTravelComponentGroupingId(helper.getTcg());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);
    }
}
