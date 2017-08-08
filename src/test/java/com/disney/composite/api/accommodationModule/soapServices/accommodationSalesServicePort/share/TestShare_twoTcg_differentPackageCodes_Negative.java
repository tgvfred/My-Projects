package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_differentPackageCodes_Negative extends AccommodationBaseTest {

    private Share share;
    String firstTCG;
    private ReplaceAllForTravelPlanSegment book, book1;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        book = new ReplaceAllForTravelPlanSegment(environment, "RoomOnlyNoTickets");
        book.sendRequest();

        firstTCG = getBook().getTravelComponentGroupingId();

        book1 = new ReplaceAllForTravelPlanSegment(environment, "RoomOnlyNoTickets");
        PackageCodes pkg = new PackageCodes();
        String packageCode = pkg.retrievePackageCode(getEnvironment(), String.valueOf(getDaysOut()),
                getLocationId(), "RSR", "", getResortCode(), getRoomTypeCode(), "");
        getBook().setRoomDetailsPackageCode(packageCode);
        book1.sendRequest();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_twoTcg_differentRoomTypes_Negative() {

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE , PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE!";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.CANNOT_CHANGE_PACKAGE);
    }

}
