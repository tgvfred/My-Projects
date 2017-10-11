package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.applicationError.GroupsMgmtErrorCode;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;

public class TestReplaceAllForTravelPlanSegment_Negative extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setSendRequest(false);
        isComo.set("true");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRequest() {
        String faultString = "Required parameters are missing : Request can not be null!";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.REQUIRED_PARAMETERS_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_ResortPeriodStartDateAfterEndDate() {
        String faultString = " Resort Period is invalid  : ResortPeriod start date should be less than end date";
        bookReservation();
        getBook().setRoomDetails_ResortPeriod(Randomness.generateCurrentXMLDate(1), Randomness.generateCurrentXMLDate(0));
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_RESORT_PERIOD);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullCommunicationsChannel() {
        String faultString = "communication Channel is required : null";
        bookReservation();
        getBook().setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullSalesChannel() {
        String faultString = "Sales Channel is required : null";
        bookReservation();
        getBook().setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.SALES_CHANNEL_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetailsPackageCode() {
        String faultString = " Package Code is invalid  : PackageCode can not be null or blank";
        bookReservation();
        getBook().setRoomDetailsPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.PACKAGE_CODE_INVALID);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetailsRoomTypeCode() {
        String faultString = " Room Type is invalid  : ROOM TYPE CODE CANNOT BE NULL OR BLANK!";
        bookReservation();
        getBook().setRoomDetailsRoomTypeCode(BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_ROOM_TYPE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetailsResortCode() {
        String faultString = " Resort Code is invalid  : RESORT CODE CANNOT BE NULL OR BLANK!";
        bookReservation();
        getBook().setRoomDetailsResortCode(BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_RESORT_CODE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_invalidTravelPlanId() {
        String faultString = "TRAVEL_PLAN_NOT_FOUND : NO TRAVEL PLAN FOUND WITH ID#";
        bookReservation();
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getUnusedTpId()));
        String invalidTp = rs.getValue("ID");
        faultString += invalidTp;
        getBook().setTravelPlanId(invalidTp);
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.TRAVEL_PLAN_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetails() {
        String faultString = "There should be at least one Room Detail : null";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.ROOM_DETAIL_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetailsResortPeriod() {
        String faultString = " Resort Period is invalid  : ResortPeriod or resort period start date or end date can not be null";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/resortPeriod", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_RESORT_PERIOD);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomReservationDetailGuestReferenceDetails() {
        String faultString = "Guest is Required : GuestReferenceDetails cannot be empty";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.GUEST_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullTravelPlanGuest() {
        String faultString = "Travel Plan Guest is required :  Travel Plan Guest Details not provided ";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/travelPlanGuest", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();
        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.TRAVEL_PLAN_GUEST_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_invalidTravelStatus() {
        String faultString = " Travel Status is invalid  : INVALID TRAVEL STATUS TO BOOK OR MODIFY!";
        bookReservation();
        getBook().setRoomDetailsTravelStatus("Auto Cancelled");
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.TRAVEL_STATUS_INVALID);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullExtRefNumber() {
        String faultString = "External Reference is required : null";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/externalReference/externalReferenceNumber", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.EXTERNAL_REFERENCE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_ORIGINAL_TP_ID_nullExtRefNumber() {
        String faultString = "Invalid External Reference Details : null";
        bookReservation();
        getBook().setExternalReference(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), getExternalRefSource(), "ORIGINAL_TP_ID");
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/externalReference/externalReferenceNumber", BaseSoapCommands.REMOVE_NODE.toString());
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_EXT_REF_DETAILS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_fplosAndFreezeID() {
        String faultString = "FPLOSID AND FREEZEID CANNOT BE USED TOGETHER! : FPLOSID AND FREEZEID CANNOT BE USED TOGETHER!";
        bookReservation();
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("fplosId"));
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/fplosId", "0");
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.DIRECT_CONNECT_BOTH_FPLOSID_AND_FREEZEID_CANNOT_EXIST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullExtRefSourceAndCode() {
        String faultString = "External Reference Source or Code required : EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED";
        bookReservation();
        getBook().setExternalReference(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), externalRefSource);
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullRoomDetailsExtRefSourceAndCode() {
        String faultString = "External Reference Source or Code required : ";
        bookReservation();
        getBook().setRoomDetails_ExternalRefs(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), externalRefSource);
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_nullSourceExtRefSourceAndCode() {
        String faultString = "External Reference Source or Code required : ";
        bookReservation();
        getBook().setRequestNodeValueByXPath("//roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("sourceExternalReference"));
        getBook().setRoomDetails_SourceExtRef(BaseSoapCommands.REMOVE_NODE.toString(), getExternalRefNumber(), BaseSoapCommands.REMOVE_NODE.toString(), externalRefSource);
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_modifyDvcRes() {
        BookDVCCashHelper dvc = new BookDVCCashHelper();
        dvc.setEnvironment(Environment.getBaseEnvironmentName(environment));
        dvc.beforeSuite(dvc.getEnvironment());
        dvc.setUseDvcResort(true);
        dvc.bookDvcReservation("testCancel_M$", 1);
        String tpId = dvc.getFirstBooking().getTravelPlanId();
        String tpsId = dvc.getFirstBooking().getTravelPlanSegmentId();

        String faultString = "INVALID REQUEST! : Book or Modify of a DVC room reservation is not supported.";
        bookReservation();
        getBook().setSecurityValue("DVC");
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().sendRequest();

        try {
            TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
            validateApplicationError(getBook(), AccommodationErrorCode.INVALID_REQUEST);
        } finally {
            dvc.cancel(dvc.getFirstBooking().getTravelComponentGroupingId());
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_invalidGathering() {
        String faultString = "Gathering Detail is not valid : Not a valid Gathering Detail Information";
        bookReservation();
        getBook().setGatheringDetail(BaseSoapCommands.REMOVE_NODE.toString(), Randomness.randomAlphaNumeric(20), "TW");
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_GATHERING_DETAIL);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_invalidBlockCode() {
        String faultString = "Group Profile does not exist with the given code : null";
        setIsWdtcBooking(true);
        bookReservation();
        getBook().setRoomDetailsBlockCode(Randomness.randomAlphaNumeric(8));
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), GroupsMgmtErrorCode.GROUP_PROFILE_DOESNT_EXIST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_resortRoomTypeMismatch() {
        String faultString = "INVALID REQUEST ! : Combination of Resort Code [RESORT] and Room Type Code [ROOM] is not valid!";
        bookReservation();
        String originalResort = getResortCode();
        String originalRoomType = getRoomTypeCode();
        do {
            setValues();
        } while (getResortCode().equals(originalResort) && getRoomTypeCode().equals(originalRoomType));
        getBook().setRoomDetailsResortCode(originalResort);
        getBook().setRoomDetailsRoomTypeCode(getRoomTypeCode());
        getBook().sendRequest();
        faultString = faultString.replace("RESORT", originalResort).replace("ROOM", getRoomTypeCode());

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_modifyNotArrived() {
        String faultString = "Invalid Modify Request : Accommodation is in : NOT_ARRIVED status. Cannot modify this reservation";

        String sql = "select * "
                + "from( "
                + "        select c.tp_id, c.tps_id "
                + "        from res_mgmt.tc_grp a "
                + "        join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb "
                + "        join res_mgmt.tps c on a.tps_id = c.tps_id "
                + "        where a.TC_GRP_TYP_NM = 'ACCOMMODATION' "
                + "        and b.trvl_sts_nm = 'Not Arrived' "
                + "        order by dbms_random.value"
                + ")where rownum = 1";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            TestReporter.assertTrue(true, "No reservations were found in 'Not Arrived' status.");
        } else {
            bookReservation();
            getBook().setTravelPlanId(rs.getValue("TP_ID"));
            getBook().setTravelPlanSegementId(rs.getValue("TPS_ID"));
            getBook().sendRequest();

            TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
            validateApplicationError(getBook(), LiloResmErrorCode.INVALID_MODIFY_REQUEST);
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_modifyDFCheckedOut() {
        String faultString = "Invalid Modify Request : Accommodation is in : DF_CHECKED_OUT status. Cannot modify this reservation";

        String sql = "select * "
                + "from( "
                + "        select c.tp_id, c.tps_id "
                + "        from res_mgmt.tc_grp a "
                + "        join res_mgmt.tc b on a.tc_grp_nb = b.tc_grp_nb "
                + "        join res_mgmt.tps c on a.tps_id = c.tps_id "
                + "        where a.TC_GRP_TYP_NM = 'ACCOMMODATION' "
                + "        and b.trvl_sts_nm = 'DF Checked Out' "
                + "        and c.tps_id not in( "
                + "                select d.tps_id "
                + "                from res_mgmt.ADM_CMPNT_ENTTL a "
                + "                join res_mgmt.tc b on a.ADM_TC_ID = b.tc_id "
                + "                join res_mgmt.tc_grp c on b.tc_grp_nb = c.tc_grp_nb "
                + "                join res_mgmt.tps d on c.tps_id = d.tps_id "
                + "        ) "
                + "        order by dbms_random.value "
                + ") where rownum = 1 ";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            TestReporter.assertTrue(true, "No reservations were found in 'DF Checked Out' status.");
        } else {
            bookReservation();
            getBook().setTravelPlanId(rs.getValue("TP_ID"));
            getBook().setTravelPlanSegementId(rs.getValue("TPS_ID"));
            getBook().sendRequest();

            TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
            validateApplicationError(getBook(), LiloResmErrorCode.INVALID_MODIFY_REQUEST);
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative" })
    public void TestReplaceAllForTravelPlanSegment_invalidTravelAgency() {
        String faultString = " Travel Agency is invalid  : null";

        bookReservation();
        getBook().setTravelAgency("99999998");
        // String invalidValue = Randomness.randomNumber(20);
        getBook().setTravelAgencyAgencyIataNumber(Randomness.randomNumber(20));
        getBook().setTravelAgencyAgencyOdsId(Randomness.randomNumber(15));
        getBook().setTravelAgencyAgentId(Randomness.randomNumber(15));
        getBook().sendRequest();

        TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(getBook(), LiloResmErrorCode.TRAVEL_AGENCY_INVALID);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment", "negative", "debug" })
    public void TestReplaceAllForTravelPlanSegment_ticketsPickedUp() {
        String faultString = "INVALID REQUEST! : Tickets have already been picked up for reservation.";

        String sql = "select d.tp_id, d.tps_id "
                + "from res_mgmt.ADM_CMPNT_ENTTL a "
                + "join res_mgmt.tc b on a.ADM_TC_ID = b.tc_id "
                + "join res_mgmt.tc_grp c on b.tc_grp_nb = c.tc_grp_nb "
                + "join res_mgmt.tps d on c.tps_id = d.tps_id";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        faultString = faultString.replace("RES", rs.getValue("TPS_ID"));

        if (rs.getRowCount() == 0) {
            TestReporter.assertTrue(true, "No reservations were found with tickets picked up.");
        } else {
            bookReservation();
            getBook().setTravelPlanId(rs.getValue("TP_ID"));
            getBook().setTravelPlanSegementId(rs.getValue("TPS_ID"));
            getBook().sendRequest();
            faultString = "Invalid Modify Request : Tickets have already been picked up for reservation: " + rs.getValue("TPS_ID") + ". Cannot modify this reservation";
            TestReporter.assertEquals(getBook().getFaultString(), faultString, "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");
            validateApplicationError(getBook(), LiloResmErrorCode.INVALID_MODIFY_REQUEST);
        }
    }
}
