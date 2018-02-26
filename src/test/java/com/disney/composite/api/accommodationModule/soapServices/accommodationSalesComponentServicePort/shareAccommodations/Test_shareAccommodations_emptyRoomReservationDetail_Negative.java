package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.shareAccommodations;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ShareAccommodations;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class Test_shareAccommodations_emptyRoomReservationDetail_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "shareAccommodations" })
    public void test_shareAccommodations_emptyRoomReservationDetail_Negative() {

        ShareAccommodations share = new ShareAccommodations(environment);

        // share.setAccommodation(BaseSoapCommands.REMOVE_NODE.toString());
        share.setBookingDate(getBook().getStartDate());
        share.setFreezeId("0");
        share.setGuaranteeStatus("None");
        share.setInventoryStatus("Unassgned");
        share.setOverrideFreeze("false");
        share.setPackageCode(getPackageCode());
        share.setResortCode(getResortCode());
        share.setResortEndDate(getBook().getEndDate());
        share.setResortStartDate(getBook().getStartDate());
        share.setRoomTypeCode(getRoomTypeCode());
        share.setRSRReservations("false");
        share.setTcgId(getBook().getTravelComponentGroupingId());
        share.setTcId(getBook().getTravelComponentId());
        share.setTravelStatus("Booked");
        share.setLocationId(getLocationId());

        share.setShared("false");
        share.setSpecialNeedsRequested("0");
        share.sendRequest();
        validateApplicationError(share, MISSING_REQUIRED_PARAM_EXCEPTION);
    }
}
