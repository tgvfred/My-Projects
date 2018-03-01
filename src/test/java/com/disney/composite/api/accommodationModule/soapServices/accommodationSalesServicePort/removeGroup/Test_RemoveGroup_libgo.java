package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.removeGroup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.StageRemoveGroupDataHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RemoveGroup;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_RemoveGroup_libgo extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsLibgoBooking(true);
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesBatchService", "stageRemoveGroupData" })
    public void testStageRemoveGroupData_libgo() {
        RemoveGroup removeGroup = new RemoveGroup(environment);
        removeGroup.setExternalReferenceInfo("01905", getExternalRefNumber(), getExternalRefSource());
        removeGroup.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        removeGroup.sendRequest();

        TestReporter.logAPI(!removeGroup.getResponseStatusCode().equals("200"), "There was an error in response: " + removeGroup.getFaultString(), removeGroup);

        StageRemoveGroupDataHelper.validateResMgmtInfo(getBook());
        StageRemoveGroupDataHelper.validateResHistoryInfo(getBook());
        StageRemoveGroupDataHelper.validateTcGuestInfo(getBook());
        StageRemoveGroupDataHelper.validateTPPartyInfo(getBook(), getHouseHold());
        StageRemoveGroupDataHelper.validateRIMInfo(getBook());
        StageRemoveGroupDataHelper.validateFolioRootInfo(getBook());
        StageRemoveGroupDataHelper.validateFolioNodeInfo(getBook());
    }
}