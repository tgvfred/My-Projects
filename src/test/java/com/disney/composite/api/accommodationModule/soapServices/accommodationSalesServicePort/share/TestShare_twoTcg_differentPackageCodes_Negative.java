package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes_RSR;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_differentPackageCodes_Negative extends AccommodationBaseTest {

    private Share share;
    String firstTCG;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        firstTCG = getBook().getTravelComponentGroupingId();

        PackageCodes_RSR pkg = new PackageCodes_RSR();
        String packageCode = pkg.retrievePackageCode(getEnvironment(), String.valueOf(getDaysOut()),
                getLocationId(), "RSR", "", getResortCode(), getRoomTypeCode(), "WDW RSR CR");

        setSendRequest(false);
        bookReservation();
        getBook().setRoomDetailsPackageCode(packageCode);
        getBook().setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_twoTcg_differentRoomTypes_Negative() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE, PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE!";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.CANT_CHANGE_BLOCK_RESORT_PACKAGE_FOR_SHARED_ACCOMMODATION);
    }

}
