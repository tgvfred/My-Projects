package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.overrideAccommodationRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.UpgradeResortRoomType;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.OverrideAccommodationRatesRequest;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOverrideAccommodationRates_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullRequest() {
        String fault = "OverrideAccommodationRatesRequest is Null";

        TestReporter.logScenario("Test - Override Accommodation Rates - Null Request");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment);

        // validation of removing optionKeyValue
        oar.setRequest(BaseSoapCommands.REMOVE_NODE.toString());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullTCG() {

        String fault = "Required parameters are missing : ExternalReferences and TCG Id needs to be provided";

        TestReporter.logScenario("Test - OVerride Accommodation Rates - Null TCG");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        oar.setTcgId(BaseSoapCommands.REMOVE_NODE.toString());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullRateDetails() {
        String fault = "Required parameters are missing : Rate Details is Null";

        TestReporter.logScenario("Test - Override Accommodation Rates  - Null RateDetails");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        oar.setRateDetails(BaseSoapCommands.REMOVE_NODE.toString());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_emptyRateDetails() {

        String fault = "Required parameters are missing : Rate Details is Null";

        TestReporter.logScenario("Test - Override Accommdation Rates - empty Rate Details");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        // validation of removing AccommodationSalesOptionsEnum
        oar.setRateDetails(BaseSoapCommands.BLANK.toString());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullExtRefNumber() {
        String fault = "External Reference is required : External Reference Number is missing !";

        TestReporter.logScenario("Test - Override Accommodation Rates  - Null ExtRef Number");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "externalRefDetail");
        // validation

        oar.setExternalReferenceType(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceNumber(BaseSoapCommands.REMOVE_NODE.toString());
        oar.setExternalReferenceCode(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceSource(BaseSoapCommands.BLANK.toString());
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.EXTERNAL_REFERENCE_NUMBER_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullExtRefSourceAndCode() {

        String fault = "External Reference Source or Code required : External Reference Code is missing !";

        TestReporter.logScenario("Test - Override Accommodation Rates - Null ExtRefSource and Code");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "externalRefDetail");
        // validation

        oar.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        oar.setExternalReferenceSource(BaseSoapCommands.REMOVE_NODE.toString());
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_EXTERNAL_REFERENCE_CODE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_invalidExtRefCode() {
        String fault = "Invalid External Reference Code : Unable to retrieve External Reference Source - Invalid External Reference Code";

        TestReporter.logScenario("Test - Override Accommodation Rates - invalid ExtRefCode");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "externalRefDetail");
        oar.setExternalReferenceCode("-1");
        oar.setExternalReferenceType(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceNumber(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceSource(BaseSoapCommands.BLANK.toString());
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.INVALID_EXTERNAL_REFERNCE_CODE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_invalidExtRefSource() {

        String fault = "Booking Source not found :  Invalid Booking Source 'INVALID'";

        TestReporter.logScenario("Test - Override Accommodation Rates - Invalid ExtRefSource");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "externalRefDetail");
        oar.setExternalReferenceCode(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceType(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceNumber(BaseSoapCommands.BLANK.toString());
        oar.setExternalReferenceSource("INVALID");
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.BOOKING_SOURCE_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_invalidOverrideRate() {

        String fault = "Override rate cannot be more than rack rate. : null";

        TestReporter.logScenario("Test - Override Accommodation Rates - Invalid OverrideRate");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        // validation of removing AccommodationSalesOptionsEnum
        oar.setOverridden("true");
        oar.setBasePrice("10.0");
        oar.setRackRateRate("1.0");
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.OVERRIDE_RATE_RACK_RATE_ERROR);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_upgradeRes() {

        String fault = "Rate override failed : Rate override cannot be performed on an upgraded accommodation!";

        TestReporter.logScenario("Test - Override Accommodation Rates - upgradeRes");
        // Book room only booking (1 night, 1 adult)
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());

        setDepartureDate(getDaysOut() + getNights());
        setValues();

        isComo.set("false");
        bookReservation();

        // Upgrade reservation using AccommodationFulfillmentServicePort#upgradeResortRoomType
        UpgradeResortRoomType urrt = new UpgradeResortRoomType(environment, "upgradeResortRoomType");
        urrt.setTcg(getBook().getTravelComponentGroupingId());
        urrt.setTc(getBook().getTravelComponentId());
        urrt.setRequestNodeValueByXPath("/Envelope/Body/upgradeResortRoomType/request/upgradeRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("startDate"));
        urrt.setRequestNodeValueByXPath("/Envelope/Body/upgradeResortRoomType/request/upgradeRoomDetail/startDate", "2018-09-09");

        urrt.setFacilityId(getFacilityId());
        urrt.setLocationIdString(BaseSoapCommands.REMOVE_NODE.toString());
        urrt.setRoomTypeCode(getRoomTypeCode());

        urrt.sendRequest();

        // urrt.setRequestNodeValueByXPath("/Envelope/Body/upgradeResortRoomType/request/upgradeRoomDetail",
        // BaseSoapCommands.ADD_NODE.commandAppend("endDate"));
        // urrt.setRequestNodeValueByXPath("/Envelope/Body/upgradeResortRoomType/request/upgradeRoomDetail/endDate", getArrivalDate());
        // urrt.setEndDate(getArrivalDate());
        urrt.setFacilityId(getFacilityId());
        urrt.setLocationIdString(BaseSoapCommands.REMOVE_NODE.toString());
        urrt.setRoomTypeCode(getRoomTypeCode());
        urrt.sendRequest();
        TestReporter.logAPI(!urrt.getResponseStatusCode().equals("200"), "Verify that no error occurred upgrading a room: " + urrt.getFaultString(), urrt);

        // Override the rate for the one night
        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        oar.setTcgId(getBook().getTravelComponentGroupingId());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.RATE_OVERRIDE_FAILURE);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_nullOverrideReason() {
        String fault = "Required parameters are missing : REASON CODE IS REQUIRED!";

        TestReporter.logScenario("Test - Override Accommodation Rates   - Null OverrideReason");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        // validation

        oar.setOverrideReason(BaseSoapCommands.REMOVE_NODE.toString());

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_invalidOverrideReason() {

        String fault = "Change Reason is invalid  : Reason code not found";

        TestReporter.logScenario("Test - Override Accommodation Rates   - Invalid OverrideReason");
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        oar.setTcgId(getBook().getTravelComponentGroupingId());

        oar.setOverrideReason("INVALID");

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.INVALID_CHANGE_REASON);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_invalidTCG() {
        String fault = "No Accommodation Component found. : null";

        TestReporter.logScenario("Test - Override Accommodation Rates   - Invalid TCG");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
        // validation

        oar.setTcgId("1");

        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.NO_ACCOMMODATION_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_autoCancelled() {

        String fault = "Cancelled accommodations cannot be overriden : null";

        TestReporter.logScenario("Test - Override Accommodation Rates   - Auto Cancelled");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        String sql = " select a.tps_id, c.tc_grp_nb, a.TPS_ARVL_DT" +
                " from res_mgmt.tps a" +
                " join res_mgmt.tc_grp c on a.tps_id = c.tps_id" +
                " where a.create_usr_id_cd = 'AutoJUnit.us'" +
                " and a.trvl_sts_nm = 'Auto Cancelled'" +
                " and a.TPS_ARVL_DT > sysdate";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        oar.setTpsID(rs.getValue("tps_id"));
        oar.setTcgId(rs.getValue("tc_grp_nb"));
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.CANNOT_OVERRIDE_CANCELLED_ACCOMMODATIONS);
    }

    // giving java null pointer exception -works in database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative" })
    public void TestOverrideAccommodationRates_cancelled() {
        String fault = "Cancelled accommodations cannot be overriden : null";

        TestReporter.logScenario("Test - Override Accommodation Rates   - Cancelled");

        OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");

        String sql = " select a.tps_id, c.tc_grp_nb, a.TPS_ARVL_DT" +
                " from res_mgmt.tps a" +
                " join res_mgmt.tc_grp c on a.tps_id = c.tps_id" +
                " where a.create_usr_id_cd = 'AutoJUnit.us'" +
                " and a.trvl_sts_nm = 'Cancelled'" +
                " and a.TPS_ARVL_DT > sysdate";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        oar.setTpsID(rs.getValue("tps_id"));
        oar.setTcgId(rs.getValue("tc_grp_nb"));
        oar.sendRequest();

        TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() + " ]", oar);
        validateApplicationError(oar, AccommodationErrorCode.CANNOT_OVERRIDE_CANCELLED_ACCOMMODATIONS);
    }

    /*
     * @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "overrideAccommodationRates", "negative", "debug" })
     * public void TestOverrideAccommodationRates_invalidLocationId() {
     *
     * String fault = "ResManagementMapper.getSettlementLocationByFacility:ResManagementMapper.getSettlementLocationByFacility:";
     *
     * TestReporter.logScenario("Test - Override Accommodation Rates   - Invalid LocationId");
     *
     * setEnvironment(environment);
     * setDaysOut(0);
     * setNights(1);
     * setArrivalDate(getDaysOut());
     * setDepartureDate(getDaysOut() + getNights());
     * setValues();
     * bookReservation();
     *
     * // Cancel cancel = new Cancel(environment);
     * //
     * // cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
     * // //System.out.println(cancel.getRequest());
     * // cancel.sendRequest();
     *
     * OverrideAccommodationRatesRequest oar = new OverrideAccommodationRatesRequest(environment, "Main");
     *
     * oar.setTcgId(getBook().getTravelComponentGroupingId());
     *
     * oar.setLocationId("-1");
     *
     * oar.sendRequest();
     * //System.out.println(oar.getResponse());
     * TestReporter.logAPI(!oar.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + oar.getFaultString() +
     * " ]", oar);
     * validateApplicationError(oar, AccommodationErrorCode.INVALID_FACILITY);
     * }
     */

}