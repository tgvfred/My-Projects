package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForCancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForCancel;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassCancelTransactional;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.BookReservations;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetStagedRecordsForCancel_TwoTcgs extends AccommodationBaseTest {

    private String processId = null;
    private String contactName = "Cancel Name";
    private String reasonCode = "AIR";
    private String isOverridden = "false";
    private String isWaived = "false";
    private String cancelFee = "0";

    private BookReservations book;
    private String packageCode;
    private static final int maxTries = 3;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setValues(Environment.getBaseEnvironmentName(getEnvironment()));
        createHouseHold();
        getHouseHold().sendToApi(Environment.getBaseEnvironmentName(getEnvironment()));
        book = new BookReservations(Environment.getBaseEnvironmentName(getEnvironment()), "RoomOnly_TwoRooms");
        int daysOUt = 0;
        int nights = 1;
        setArrivalDate(daysOUt);
        setDepartureDate(daysOUt + nights);

        book.setAreaPeriodStartDate(getArrivalDate());
        book.setAreaPeriodEndDate(getDepartureDate());
        book.setResortAreaStartDate(getArrivalDate());
        book.setResortAreaEndDate(getDepartureDate());

        int tries = 0;
        PackageCodes pkg = new PackageCodes();
        packageCode = null;
        boolean success = false;
        do {
            try {
                packageCode = pkg.retrievePackageCode(Environment.getBaseEnvironmentName(getEnvironment()), String.valueOf(daysOUt),
                        getLocationId(), "DRC RO", "", getResortCode(), getRoomTypeCode(), "");
                success = true;
            } catch (AssertionError e) {
                setValues();
            }
        } while (!success && ++tries < maxTries);

        TestReporter.assertTrue(success, "Successfully found package code");
        book.setPackageCode(packageCode);
        book.setResortCode(getResortCode());
        book.setRoomTypeCode(getRoomTypeCode());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        book.setBookingDate(Randomness.generateCurrentXMLDate(), "2");
        book.setFreezeId(Randomness.randomNumber(8), "2");
        book.setGuarenteeStatus("NONE", "2");
        book.setOverrideFreeze("false", "2");
        book.setPackageCode(packageCode, "2");
        book.setResortCode(getResortCode(), "2");
        book.setResortAreaEndDate(Randomness.generateCurrentXMLDate(1), "2");
        book.setResortAreaStartDate(Randomness.generateCurrentXMLDate(), "2");
        book.setGuestReservationDetailsAge(getHouseHold().primaryGuest().getAge(), "2");
        book.setGuestReservationDetailsAgeType("ADULT", "2");
        book.setGuestReservationDetailsGuestFirstName(getHouseHold().primaryGuest().getFirstName(), "2");
        book.setGuestReservationDetailsGuestLastName(getHouseHold().primaryGuest().getLastName(), "2");
        book.setGuestReservationDetailsGuestPartyId(getHouseHold().primaryGuest().getPartyId(), "2");
        book.setGuestReservationDetailsGuestLocatorId(getHouseHold().primaryGuest().primaryAddress().getLocatorId(), "2");
        book.setGuestReservationDetailsGuestGuestLocatorId(getHouseHold().primaryGuest().primaryAddress().getLocatorId(), "2");
        book.setGuestReservationDetailsGuestLocatorUseType("UNKNOWN", "2");
        book.setGuestReservationDetailsGuestPrimary("false", "2");
        book.setGuestReservationDetailsGuestAddress1(getHouseHold().primaryGuest().primaryAddress().getAddress1(), "2");
        book.setGuestReservationDetailsGuestCity(getHouseHold().primaryGuest().primaryAddress().getCity(), "2");
        book.setGuestReservationDetailsGuestCountry(getHouseHold().primaryGuest().primaryAddress().getCountry(), "2");
        book.setGuestReservationDetailsGuestPostalCode(getHouseHold().primaryGuest().primaryAddress().getZipCode(), "2");
        book.setGuestReservationDetailsGuestState(getHouseHold().primaryGuest().primaryAddress().getStateAbbv(), "2");
        book.setGuestReservationDetailsGuestDoNotMailIndicator("false", "2");
        book.setGuestReservationDetailsGuestDoNotPhoneIndicator("false", "2");
        book.setGuestReservationDetailsGuestPreferredLanguage(getHouseHold().primaryGuest().getLanguagePreference(), "2");
        book.setGuestReservationDetailsGuestDclGuestId("0", "2");
        book.setGuestReservationDetailsGuestGuestId("0", "2");
        book.setGuestReservationDetailsGuestActive("false", "2");
        book.setGuestReservationDetailsPurposeOfVisit("Leisure", "2");
        book.setGuestReservationDetailsRole("Guest", "2");
        book.setGuestReservationDetailsCorrelationID("1", "2");
        book.setRoomTypeCode(getRoomTypeCode(), "2");
        book.setRsrReservation("false", "2");
        book.setTravelComponentGroupingId("0", "2");
        book.setTravelComponentId("0", "2");
        book.setTravelStatus("Booked", "2");
        book.setShared("false", "2");
        book.setSpecialNeedsRequested("false", "2");

        book.sendRequest();
        TestReporter.assertTrue(book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a prereq reservation: " + book.getFaultString());

        setTpId(book.getTravelPlanId());
        setTpsId(book.getTravelPlanSegmentId());
        setTcgId(book.getTravelComponentGroupingId());
        setTcId(book.getTravelComponentId());

        // Uses first book tcg to make massCancelAccommodationRequestDetails
        StageMassCancelTransactional stageMassCancel = new StageMassCancelTransactional(environment, "Main");
        stageMassCancel.setCancelContactName(contactName);
        stageMassCancel.setCancelDate(Randomness.generateCurrentXMLDate());
        stageMassCancel.setCancelReasonCode(reasonCode);
        stageMassCancel.setIsOverridden(isOverridden);
        stageMassCancel.setIsWaived(isWaived);
        stageMassCancel.setOVerridenCancelFEe(cancelFee);
        stageMassCancel.setTCg(book.getTravelComponentGroupingId("1"));

        // uses bundle tcg to make second massCancelAccommodationRequestDetails
        buildSecondDetails(stageMassCancel);

        stageMassCancel.sendRequest();
        TestReporter.logAPI(!stageMassCancel.getResponseStatusCode().equals("200"), "An error occurred with StageMassCancelTransactional request.", stageMassCancel);

        processId = stageMassCancel.getResponseProcessId();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWSPort", "getStagedRecordsForCancel" })
    public void testGetStagedRecordsForCancel_TwoTcgs() {

        // Uses group res process ID to get the group res process run ID
        String sql = "select b.GRP_RES_PROC_ID, b.GRP_RES_PROC_RUN_ID, b.GRP_RES_PROC_RUN_STS_NM "
                + "from res_mgmt.GRP_RES_PROC a "
                + "join res_mgmt.GRP_RES_PROC_RUN b on a.GRP_RES_PROC_ID = b.GRP_RES_PROC_ID "
                + "where a.GRP_RES_PROC_ID in ('" + processId + "')";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String runID = rs.getValue("GRP_RES_PROC_RUN_ID", 1);
        String runIDTwo = rs.getValue("GRP_RES_PROC_RUN_ID", 2);

        GetStagedRecordsForCancel getStaged = new GetStagedRecordsForCancel(environment, "Main_TwoTcgs");
        getStaged.setProcessDataId(runID);
        getStaged.setProcessDataIdTwo(runIDTwo);
        getStaged.sendRequest();
        TestReporter.logAPI(!getStaged.getResponseStatusCode().equals("200"), "An error occurred getting staged records for cancel.", getStaged);

        TestReporter.softAssertTrue(getStaged.getNumberOfResponseNodesByXPath("/Envelope/Body/getStagedRecordsForCancelResponse/return") == 1, "Verify that one return node was returned.");
        TestReporter.softAssertEquals(getStaged.getCancelContactName(), contactName, "Verify the cancel contact name [" + getStaged.getCancelContactName() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + contactName + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getCancelDate().substring(0, 10), Randomness.generateCurrentXMLDate(), "Verify the cancel date [" + getStaged.getCancelDate() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + Randomness.generateCurrentXMLDate() + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getCancelReasonCode(), reasonCode, "Verify the cancel reason code [" + getStaged.getCancelReasonCode() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + reasonCode + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getOverridden(), isOverridden, "Verify the isOverridden value [" + getStaged.getOverridden() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + isOverridden + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getWaived(), isWaived, "Verify the isWaived value [" + getStaged.getWaived() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + isWaived + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getOverriddenCancelFee().substring(0, 1), cancelFee, "Verify the overriddenCancelFee [" + getStaged.getOverriddenCancelFee() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + cancelFee + "] used in the StageMassCancelTransactional request");
        TestReporter.softAssertEquals(getStaged.getTravelComponentGroupingId(), book.getTravelComponentGroupingId("2"), "Verify the TcgId [" + getStaged.getTravelComponentGroupingId() + "] "
                + "brought back in the GetStagedRecordsForCancel response matches the value [" + book.getTravelComponentGroupingId("2") + "] used in the StageMassCancelTransactional request");

        TestReporter.assertAll();
        validateDBProcessData(processId);

        if (Environment.isSpecialEnvironment(environment)) {
            GetStagedRecordsForCancel clone = (GetStagedRecordsForCancel) getStaged.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getStaged, true), "Validating Response Comparison");
        }

    }

    private void validateDBProcessData(String processId) {

        String sql = "select * "
                + "from res_mgmt.GRP_RES_PROC "
                + "WHERE GRP_RES_PROC_ID = '" + processId + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String processName = rs.getValue("GRP_RES_PROC_NM");

        TestReporter.assertEquals(processName, "MASS_CANCEL", "Verify that the process name: [" + processName + "] from res_mgmt.GRP_RES_PROC provided by the stageMassCancelTransactional process ID "
                + "matches the expected value: [MASS_CANCEL] ");
        TestReporter.assertEquals(rs.getRowCount(), 1, "Validate one record in the DB for the processId in the response");

    }

    private void buildSecondDetails(StageMassCancelTransactional stageMassCancel) {

        stageMassCancel.setRequestNodeValueByXPath("/Envelope/Body/stageMassCancelTransactional/massCancelRequest",
                BaseSoapCommands.ADD_NODE.commandAppend("massCancelAccommodationRequestDetails"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("cancelContactName"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelContactName", contactName);
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("cancelDate"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelDate", Randomness.generateCurrentXMLDate());
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("cancelReasonCode"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelReasonCode", reasonCode);
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("isOverridden"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/isOverridden", isOverridden);
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("isWaived"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/isWaived", isWaived);
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("overriddenCancelFee"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/overriddenCancelFee", cancelFee);
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]",
                BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
        stageMassCancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/travelComponentGroupingId", book.getTravelComponentGroupingId("2"));

    }

}
