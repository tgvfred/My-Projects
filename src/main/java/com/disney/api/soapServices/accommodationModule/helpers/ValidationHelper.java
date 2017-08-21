package com.disney.api.soapServices.accommodationModule.helpers;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.COMMENT_TEXT;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.GATHERING_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.GATHERING_NAME;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.GATHERING_TYPE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_EXP_DATE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_POLICY_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.MEMBERSHIP_PROD_CHANNEL_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_DESCRIPTION;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_ID;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_ROUTINGS_NAME;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.PROFILE_TYPE;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.getAgeTypeByAge;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.disney.AutomationException;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class ValidationHelper {
    private String environment;
    private Boolean bundleAdded;
    private Boolean diningAddedOn;
    private String partyId;
    private Boolean guestIdExpected;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Boolean isBundleAdded() {
        return bundleAdded;
    }

    public void setBundleAdded(Boolean bundleAdded) {
        this.bundleAdded = bundleAdded;
    }

    public Boolean isDiningAddedOn() {
        return diningAddedOn;
    }

    public void setDiningAddedOn(Boolean diningAddedOn) {
        this.diningAddedOn = diningAddedOn;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Boolean getGuestIdExpected() {
        return guestIdExpected;
    }

    public void setGuestIdExpected(Boolean guestIdExpected) {
        this.guestIdExpected = guestIdExpected;
    }

    public ValidationHelper(String environment) {
        if (environment == null || StringUtils.isEmpty(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
    }

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, String tcgId, Boolean isDiningAddedOn, Boolean bundledAdded) {
        setDiningAddedOn(isDiningAddedOn);
        setBundleAdded(bundledAdded);
        validateModificationBackend(numRecords, travelStatusName, securityValue, arrivalDate, departuredate, extRefType, extRefValue, tpId, tpsId, tcgId);
    }

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, String tcgId, Boolean bundledAdded) {
        setBundleAdded(bundledAdded);
        validateModificationBackend(numRecords, travelStatusName, securityValue, arrivalDate, departuredate, extRefType, extRefValue, tpId, tpsId, tcgId);
    }

    public Recordset validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, String tcgId) {
        TestReporter.logStep("Validated reservation backend data after modification");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = null;

        Map<String, String> tcgs = new HashMap<>();
        tcgs.put(tcgId, tcgId);
        if (isValid(isDiningAddedOn()) && isDiningAddedOn() == true) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        } else if (isValid(isBundleAdded()) && isBundleAdded() == true) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        }
        String sql = "select * "
                + "from res_mgmt.tps a "
                + "left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "left outer join res_mgmt.tc c  on b. tc_grp_nb = c.tc_grp_nb "
                + "left outer join res_mgmt.tc_gst d on c.tc_id = d.tc_id "
                + "left outer join res_mgmt.tps_extnl_ref e on a. tps_id = e.tps_id "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        TestReporter.softAssertEquals(rs.getRowCount(), numRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numRecords + "].");
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.log("Verify Row: " + String.valueOf(i));
            TestReporter.softAssertEquals(rs.getValue("TP_ID", i), tpId, "Verify that the TP ID [" + rs.getValue("TP_ID", i) + "] is that which is expected [" + tpId + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ID", i), tpsId, "Verify that the TPS ID [" + rs.getValue("TPS_ID", i) + "] is that which is expected [" + tpsId + "].");
            TestReporter.softAssertTrue(tcgs.containsKey(rs.getValue("TC_GRP_NB", i)), "Verify that the TCG ID [" + rs.getValue("TC_GRP_NB", i) + "] is included in the TCGs which are expected [" + tcgs + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), travelStatusName, "Verify that the travel status [" + rs.getValue("TRVL_STS_NM", i) + "] is that which is expected [" + travelStatusName + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT", i).split(" ")[0], arrivalDate, "Verify that the arrival date [" + rs.getValue("TPS_ARVL_DT", i).split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT", i).split(" ")[0], departuredate, "Verify that the departure date [" + rs.getValue("TPS_DPRT_DT", i).split(" ")[0] + "] is that which is expected [" + departuredate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_TYP_NM", i), extRefType, "Verify that the external ref type name [" + rs.getValue("TPS_EXTNL_REF_TYP_NM", i) + "] is that which is expected [" + extRefType + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_VL", i), extRefValue, "Verify that the external ref value [" + rs.getValue("TPS_EXTNL_REF_VL", i) + "] is that which is expected [" + extRefValue + "].");
        }
        TestReporter.assertAll();
        return rs;
    }

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, String tcgId, String blockCode) {
        Recordset rs = validateModificationBackend(numRecords, travelStatusName, securityValue, arrivalDate, departuredate, extRefType, extRefValue, tpId, tpsId, tcgId);
        rs.moveFirst();
        do {
            TestReporter.softAssertEquals(rs.getValue("BLK_CD"), blockCode, "Verify that the block code [" + rs.getValue("BLK_CD") + "] is that which is expected [" + blockCode + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, Map<String, String> mTcgs) {
        TestReporter.logStep("Validated reservation backend data after modification");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = null;

        Map<String, String> tcgs = new HashMap<>();
        tcgs.putAll(mTcgs);
        if (isDiningAddedOn() != null && isDiningAddedOn() == true) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        } else if (isBundleAdded() == true) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        }
        rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getReservationInfoByTpId(tpId)));
        // rs.print();

        TestReporter.softAssertEquals(rs.getRowCount(), numRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numRecords + "].");
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.log("Verify Row: " + String.valueOf(i));
            TestReporter.softAssertEquals(rs.getValue("TP_ID", i), tpId, "Verify that the TP ID [" + rs.getValue("TP_ID", i) + "] is that which is expected [" + tpId + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ID", i), tpsId, "Verify that the TPS ID [" + rs.getValue("TPS_ID", i) + "] is that which is expected [" + tpsId + "].");
            TestReporter.softAssertTrue(tcgs.containsKey(rs.getValue("TC_GRP_NB", i)), "Verify that the TCG ID [" + rs.getValue("TC_GRP_NB", i) + "] is included in the TCGs which are expected [" + tcgs + "].");
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), travelStatusName, "Verify that the travel status [" + rs.getValue("TRVL_STS_NM", i) + "] is that which is expected [" + travelStatusName + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT", i).split(" ")[0], arrivalDate, "Verify that the arrival date [" + rs.getValue("TPS_ARVL_DT", i).split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT", i).split(" ")[0], departuredate, "Verify that the departure date [" + rs.getValue("TPS_DsPRT_DT", i).split(" ")[0] + "] is that which is expected [" + departuredate + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_TYP_NM", i), extRefType, "Verify that the external ref type name [" + rs.getValue("TPS_EXTNL_REF_TYP_NM", i) + "] is that which is expected [" + extRefType + "].");
            TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_VL", i), extRefValue, "Verify that the external ref value [" + rs.getValue("TPS_EXTNL_REF_VL", i) + "] is that which is expected [" + extRefValue + "].");
        }
        TestReporter.assertAll();
    }

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, Map<String, String> arrivalDates, Map<String, String> departureDates,
            String extRefType, String extRefValue, String tpId, Map<String, String> tpsIds, Map<String, String> mTcgs) {
        TestReporter.logStep("Validated reservation backend data after modification");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = null;

        Map<String, String> tcgs = new HashMap<>();
        tcgs.putAll(mTcgs);
        if (isValid(isDiningAddedOn()) && isDiningAddedOn()) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        } else if (isValid(isBundleAdded()) && isBundleAdded()) {
            String sql = "select b.tc_grp_nb "
                    + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                    + "where a.tp_id = " + tpId + " "
                    + "and a.tps_id = b.tps_id "
                    + "and b.tc_grp_typ_nm = 'PACKAGE'";
            rs = new Recordset(db.getResultSet(sql));
            tcgs.put(rs.getValue("TC_GRP_NB", 1), rs.getValue("TC_GRP_NB", 1));
        }
        String sql = "select * "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "left outer join res_mgmt.tps_extnl_ref d on a.tps_id = d.tps_id "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        TestReporter.softAssertEquals(rs.getRowCount(), numRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numRecords + "].");

        do {

            // Validate dining information
            if (rs.getValue("TC_GRP_TYP_NM").equalsIgnoreCase("SHOWDINING")) {
                TestReporter.log("Verify Dining");
                TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpId, "Verify that the TP ID [" + rs.getValue("TP_ID") + "] is that which is expected [" + tpId + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsIds.get("dining"), "Verify that the TPS ID [" + rs.getValue("TPS_ID") + "] is that which is expected [" + tpsIds.get("dining") + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), mTcgs.get("dining"), "Verify that the TCG ID [" + rs.getValue("TC_GRP_NB") + "] is that which is expected [" + mTcgs.get("dining") + "].");
                TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatusName, "Verify that the travel status [" + rs.getValue("TRVL_STS_NM") + "] is that which is expected [" + travelStatusName + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT").split(" ")[0], arrivalDates.get("dining").split("T")[0], "Verify that the arrival date [" + rs.getValue("TPS_ARVL_DT").split(" ")[0] + "] is that which is expected [" + arrivalDates.get("dining").split("T")[0] + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT").split(" ")[0], departureDates.get("dining").split("T")[0], "Verify that the departure date [" + rs.getValue("TPS_DsPRT_DT").split(" ")[0] + "] is that which is expected [" + departureDates.get("dining").split("T")[0] + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_TYP_NM"), "NULL", "Verify that the external ref type name [" + rs.getValue("TPS_EXTNL_REF_TYP_NM") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_VL"), "NULL", "Verify that the external ref value [" + rs.getValue("TPS_EXTNL_REF_VL") + "] is that which is expected [NULL].");
            } else if (rs.getValue("TC_GRP_TYP_NM").equalsIgnoreCase("ACCOMMODATION")) {
                TestReporter.log("Verify Accommodation");
                TestReporter.softAssertEquals(rs.getValue("TP_ID"), tpId, "Verify that the TP ID [" + rs.getValue("TP_ID") + "] is that which is expected [" + tpId + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ID"), tpsIds.get("accommodation"), "Verify that the TPS ID [" + rs.getValue("TPS_ID") + "] is that which is expected [" + tpsIds.get("accommodation") + "].");
                TestReporter.softAssertEquals(rs.getValue("TC_GRP_NB"), mTcgs.get("accommodation"), "Verify that the TCG ID [" + rs.getValue("TC_GRP_NB") + "] is that which is expected [" + mTcgs.get("accommodation") + "].");
                TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM"), travelStatusName, "Verify that the travel status [" + rs.getValue("TRVL_STS_NM") + "] is that which is expected [" + travelStatusName + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_ARVL_DT").split(" ")[0], arrivalDates.get("accommodation"), "Verify that the arrival date [" + rs.getValue("TPS_ARVL_DT").split(" ")[0] + "] is that which is expected [" + arrivalDates.get("accommodation") + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_DPRT_DT").split(" ")[0], departureDates.get("accommodation"), "Verify that the departure date [" + rs.getValue("TPS_DsPRT_DT").split(" ")[0] + "] is that which is expected [" + departureDates.get("accommodation") + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_TYP_NM"), extRefType, "Verify that the external ref type name [" + rs.getValue("TPS_EXTNL_REF_TYP_NM") + "] is that which is expected [" + extRefType + "].");
                TestReporter.softAssertEquals(rs.getValue("TPS_EXTNL_REF_VL"), extRefValue, "Verify that the external ref value [" + rs.getValue("TPS_EXTNL_REF_VL") + "] is that which is expected [" + extRefValue + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateGuestInformation(String tpId, HouseHold hh, String membershipTypeName, String membershipRefId) {
        Recordset rs = validateGuestInformation(tpId, hh);
        TestReporter.softAssertEquals(rs.getValue("DVC_MBR_TYP_NM", 1), membershipTypeName, "Verify that the Dvc membership type name [" + rs.getValue("DVC_MBR_TYP_NM", 1) + "] is that which is expected [" + membershipTypeName + "].");
        TestReporter.softAssertEquals(rs.getValue("MBRSHP_ID", 1), membershipRefId, "Verify that the membership ID [" + rs.getValue("MBRSHP_ID", 1) + "] is that which is expected [" + membershipRefId + "].");
        TestReporter.assertAll();
    }

    public Recordset validateGuestInformation(String tpId, HouseHold hh) {
        TestReporter.logStep("Validated guest information");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getTpPartyGuestInfoByTpId_NoMembership(tpId)));
        // rs.print();
        TestReporter.softAssertEquals(rs.getValue("IDVL_FST_NM", 1), hh.primaryGuest().getFirstName(), "Verify that the guest first name [" + rs.getValue("IDVL_FST_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getFirstName() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_MID_NM", 1), hh.primaryGuest().getMiddleName(), "Verify that the guest middle [" + rs.getValue("IDVL_MID_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getMiddleName() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_LST_NM", 1), hh.primaryGuest().getLastName(), "Verify that the guest last name [" + rs.getValue("IDVL_LST_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getLastName() + "].");
        TestReporter.softAssertTrue(rs.getValue("ADDR_LN_1_TX", 1).toUpperCase().contains(hh.primaryGuest().primaryAddress().getAddress1().toUpperCase()), "Verify that the address line 1 [" + rs.getValue("ADDR_LN_1_TX", 1) + "] contains that which is expected [" + hh.primaryGuest().primaryAddress().getAddress1() + "].");
        if (!hh.primaryGuest().getOdsId().equals("0")) {
            TestReporter.softAssertEquals(rs.getValue("CITY_NM", 1), hh.primaryGuest().primaryAddress().getCity(), "Verify that the address city [" + rs.getValue("CITY_NM", 1) + "] is that which is expected [" + hh.primaryGuest().primaryAddress().getCity() + "].");
        }
        TestReporter.softAssertEquals(rs.getValue("RGN_CD", 1), hh.primaryGuest().primaryAddress().getStateAbbv(), "Verify that the state [" + rs.getValue("RGN_CD", 1) + "] is that which is expected [" + hh.primaryGuest().primaryAddress().getStateAbbv() + "].");
        TestReporter.softAssertEquals(rs.getValue("PSTL_CD", 1), hh.primaryGuest().primaryAddress().getZipCode(), "Verify that the zip code [" + rs.getValue("PSTL_CD", 1) + "] is that which is expected [" + hh.primaryGuest().primaryAddress().getZipCode() + "].");
        TestReporter.softAssertEquals(rs.getValue("TXN_PTY_EML_ADDR_TX", 1).toUpperCase(), hh.primaryGuest().primaryEmail().getEmail().toUpperCase(), "Verify that the email address [" + rs.getValue("TXN_PTY_EML_ADDR_TX", 1) + "] is that which is expected [" + hh.primaryGuest().primaryEmail().getEmail() + "].");
        TestReporter.softAssertEquals(rs.getValue("PHN_NB", 1), hh.primaryGuest().primaryPhone().getNumber(), "Verify that the phone number [" + rs.getValue("PHN_NB", 1) + "] is that which is expected [" + hh.primaryGuest().primaryPhone().getNumber() + "].");
        TestReporter.assertAll();
        return rs;
    }

    public Recordset validateGuestInformation(String tpId, HouseHold hh, Map<Integer, Guest> additionalGuests) {
        TestReporter.logStep("Validated guest information");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select b.IDVL_FST_NM, b.IDVL_LST_NM, b.IDVL_MID_NM, e.* "
                + "from res_mgmt.tp_pty a "
                + "join guest.txn_idvl_pty b on b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "join guest.TXN_PTY_LCTR d on a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "join guest.TXN_PTY_ADDR_LCTR e on d.TXN_PTY_LCTR_ID = e.TXN_PTY_ADDR_LCTR_ID "
                + "where a.tp_id = " + tpId;
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        Map<Integer, Guest> allGuests = new HashMap<>();
        allGuests.put(1, hh.primaryGuest());
        for (int i = 1; i <= additionalGuests.size(); i++) {
            allGuests.put(i + 1, additionalGuests.get(i));
        }
        // System.out.println();
        boolean addressFound = false;
        boolean emailFound = false;
        boolean phoneFound = false;
        for (int i = 1; i < allGuests.size(); i++) {
            Guest guest = allGuests.get(i);
            do {
                addressFound = (rs.getValue("IDVL_FST_NM").equalsIgnoreCase(guest.getFirstName()) &&
                        rs.getValue("IDVL_MID_NM").equalsIgnoreCase(guest.getMiddleName()) &&
                        rs.getValue("IDVL_LST_NM").equalsIgnoreCase(guest.getLastName()) &&
                        rs.getValue("ADDR_LN_1_TX").equalsIgnoreCase(guest.primaryAddress().getAddress1()) &&
                        rs.getValue("CITY_NM").equalsIgnoreCase(guest.primaryAddress().getCity()) &&
                        rs.getValue("RGN_CD").equalsIgnoreCase(guest.primaryAddress().getStateAbbv()) &&
                        rs.getValue("PSTL_CD").equalsIgnoreCase(guest.primaryAddress().getZipCode()));
                // System.out.println();
                rs.moveNext();
                // TestReporter.assertAll();
            } while (rs.hasNext() && !addressFound);
            rs.moveFirst();
            if (addressFound) {
                break;
            }
        }

        sql = "select e.* "
                + "from res_mgmt.tp_pty a "
                + "join guest.txn_idvl_pty b on b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "join guest.TXN_PTY_LCTR d on a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "join guest.TXN_PTY_EML_LCTR e on d.TXN_PTY_LCTR_ID = e.TXN_PTY_EML_LCTR_ID "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        // System.out.println();
        for (int i = 1; i < allGuests.size(); i++) {
            Guest guest = allGuests.get(i);
            do {
                emailFound = (rs.getValue("TXN_PTY_EML_ADDR_TX").equalsIgnoreCase(guest.primaryEmail().getEmail()));
                // System.out.println();
                rs.moveNext();
                // TestReporter.assertAll();
            } while (rs.hasNext() && !emailFound);
            rs.moveFirst();
            if (emailFound) {
                break;
            }
        }

        sql = "select e.* "
                + "from res_mgmt.tp_pty a "
                + "join guest.txn_idvl_pty b on b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "join guest.TXN_PTY_LCTR d on a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "join guest.TXN_PTY_PHN_LCTR e on d.TXN_PTY_LCTR_ID = e.TXN_PTY_PHN_LCTR_ID "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        System.out.println();
        for (int i = 1; i < allGuests.size(); i++) {
            Guest guest = allGuests.get(i);
            do {
                phoneFound = (rs.getValue("PHN_NB").equalsIgnoreCase(guest.primaryPhone().getNumber()));
                // System.out.println();
                rs.moveNext();
                // TestReporter.assertAll();
            } while (rs.hasNext() && !phoneFound);
            rs.moveFirst();
            if (phoneFound) {
                break;
            }
        }
        TestReporter.assertTrue(addressFound, "Verify that the guest address information matches that which is expected.");
        TestReporter.assertTrue(emailFound, "Verify that the guest email information matches that which is expected.");
        TestReporter.assertTrue(phoneFound, "Verify that the guest phone information matches that which is expected.");
        return rs;
    }

    public void validateGuestInformation(String tpId, HouseHold hh, String membershipTypeName, String membershipRefId, String tpPtyId) {
        TestReporter.logStep("Validated guest information");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getTpPartyGuestInfoByTpId(tpId)
                .replace(" = e.TXN_PTY_ADDR_LCTR_ID", " = e.TXN_PTY_ADDR_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")
                .replace(" = f.TXN_PTY_EML_LCTR_ID", " = f.TXN_PTY_EML_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")
                .replace(" = g.TXN_PTY_PHN_LCTR_ID", " = g.TXN_PTY_PHN_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")));
        Map<String, Boolean> values = new HashMap<>();
        values.put(hh.primaryGuest().getFirstName(), false);
        values.put(hh.primaryGuest().getLastName(), false);
        values.put(membershipTypeName, false);
        values.put(membershipRefId, false);
        values.put(hh.primaryGuest().primaryAddress().getAddress1(), false);
        values.put(hh.primaryGuest().primaryAddress().getCity(), false);
        values.put(hh.primaryGuest().primaryAddress().getStateAbbv(), false);
        values.put(hh.primaryGuest().primaryAddress().getZipCode(), false);
        values.put(hh.primaryGuest().primaryEmail().getEmail(), false);
        values.put(hh.primaryGuest().primaryPhone().getNumber(), false);
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("IDVL_FST_NM", i).equalsIgnoreCase(hh.primaryGuest().getFirstName())) {
                values.put(hh.primaryGuest().getFirstName(), true);
            }
            if (rs.getValue("IDVL_LST_NM", i).equalsIgnoreCase(hh.primaryGuest().getLastName())) {
                values.put(hh.primaryGuest().getLastName(), true);
            }
            if (rs.getValue("DVC_MBR_TYP_NM", i).equalsIgnoreCase(membershipTypeName)) {
                values.put(membershipTypeName, true);
            }
            if (rs.getValue("MBRSHP_ID", i).equalsIgnoreCase(membershipRefId)) {
                values.put(membershipRefId, true);
            }
            if (rs.getValue("ADDR_LN_1_TX", i).contains(hh.primaryGuest().primaryAddress().getAddress1())) {
                values.put(hh.primaryGuest().primaryAddress().getAddress1(), true);
            }
            if (rs.getValue("CITY_NM", i).equalsIgnoreCase(hh.primaryGuest().primaryAddress().getCity())) {
                values.put(hh.primaryGuest().primaryAddress().getCity(), true);
            }
            if (rs.getValue("RGN_CD", i).equalsIgnoreCase(hh.primaryGuest().primaryAddress().getStateAbbv())) {
                values.put(hh.primaryGuest().primaryAddress().getStateAbbv(), true);
            }
            if (rs.getValue("PSTL_CD", i).equalsIgnoreCase(hh.primaryGuest().primaryAddress().getZipCode())) {
                values.put(hh.primaryGuest().primaryAddress().getZipCode(), true);
            }
            if (rs.getValue("TXN_PTY_EML_ADDR_TX", i).equalsIgnoreCase(hh.primaryGuest().primaryEmail().getEmail())) {
                values.put(hh.primaryGuest().primaryEmail().getEmail(), true);
            }
            if (rs.getValue("PHN_NB", i).equalsIgnoreCase(hh.primaryGuest().primaryPhone().getNumber())) {
                values.put(hh.primaryGuest().primaryPhone().getNumber(), true);
            }
        }
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().getFirstName()), "Verify that the guest first name [" + hh.primaryGuest().getFirstName() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().getLastName()), "Verify that the guest last name [" + hh.primaryGuest().getLastName() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(membershipTypeName), "Verify that the membership type name [" + membershipTypeName + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(membershipRefId), "Verify that the membershipr reference ID [" + membershipRefId + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryAddress().getAddress1()), "Verify that the guest address [" + hh.primaryGuest().primaryAddress().getAddress1() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryAddress().getCity()), "Verify that the guest city [" + hh.primaryGuest().primaryAddress().getCity() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryAddress().getStateAbbv()), "Verify that the guest state [" + hh.primaryGuest().primaryAddress().getStateAbbv() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryAddress().getZipCode()), "Verify that the guest zip code [" + hh.primaryGuest().primaryAddress().getZipCode() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryEmail().getEmail()), "Verify that the guest email [" + hh.primaryGuest().primaryEmail().getEmail() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.softAssertTrue(values.get(hh.primaryGuest().primaryPhone().getNumber()), "Verify that the guest phone number [" + hh.primaryGuest().primaryPhone().getNumber() + "] is found for TP party ID [" + tpPtyId + "].");
        TestReporter.assertAll();
    }

    public void verifyTcStatusByTcg(String tcg, String status) {
        TestReporter.logStep("Verify TC Status By TCG");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        String sql = Dreams_AccommodationQueries.getTcStatusByTcg(tcg);
        if (isDiningAddedOn() != null && isDiningAddedOn() == true) {
            sql += "and b.PROD_TYP_NM != 'RESERVABLE_RESOURCE_COMPONENT'";
        }
        if (status.equals("Checking In") || status.equals("Checked In")) {
            sql += "and b.PROD_TYP_NM = 'AccommodationProduct'";
        }

        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), status, "Verify that the status [" + rs.getValue("TRVL_STS_NM", i) + "] of TC ID [" + rs.getValue("TC_ID", i) + "] is that which is expected [" + status + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyModificationIsFoundInResHistory(String tpId) {
        TestReporter.logStep("Verify MOdification Is Found In Res History");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getReservationHistoryByTpId(tpId)));
        // rs.print();
        Boolean modified = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Modified")) {
                modified = true;
            }
        }
        TestReporter.softAssertTrue(modified,
                "Verify that the reservation history shows a modification for TPS ID [" + tpId + "].");
        TestReporter.assertAll();
    }

    public void verifyBookingIsFoundInResHistory(String tpId) {
        TestReporter.logStep("Verify Booking Is Found In Res History");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getReservationHistoryByTpId(tpId)));
        // rs.print();
        Boolean modified = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Booked")) {
                modified = true;
            }
        }
        TestReporter.softAssertTrue(modified,
                "Verify that the reservation history shows a modification for TPS ID [" + tpId + "].");
        TestReporter.assertAll();
    }

    public void verifyChargeGroupsStatusCount(String status, int numberRecords, String tpId) {
        TestReporter.logStep("Verify Charge Groups Status");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        String sql = "select x.tp_id, x.tps_id, y.TC_GRP_NB "
                + "from res_mgmt.tps x, res_mgmt.tc_grp y "
                + "where x.tp_id = '" + tpId + "' "
                + "and x.tps_id = y.tps_id";
        Recordset rs = new Recordset(db.getResultSet(sql));
        String ids = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (i > 1) {
                ids += ",";
            }
            ids += "'" + rs.getValue("TP_ID", i) + "','" + rs.getValue("TPS_ID", i) + "','" + rs.getValue("TC_GRP_NB", i) + "'";
        }

        sql = "select b.CHRG_GRP_ID, c.CHRG_GRP_STS_NM, c.CHRG_GRP_TYP_NM "
                + "from folio.extnl_ref a, folio.chrg_grp_extnl_ref b, folio.chrg_grp c "
                + "where a.EXTNL_REF_VAL in (" + ids + ") "
                + "and a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "and b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "and c.CHRG_GRP_STS_NM = '" + status + "'";
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getRowCount(), numberRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numberRecords + "].");
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM", i), status,
                    "Verify that the [" + rs.getValue("CHRG_GRP_TYP_NM", i) + "] charge group [" + rs.getValue("CHRG_GRP_ID", i) + "] status [" + rs.getValue("CHRG_GRP_STS_NM", i) + "] is [" + status + "] as expected.");
        }
        TestReporter.assertAll();
    }

    public void verifyInventoryAssigned(String tcgId, int numberOfResources, String tpId) {
        TestReporter.logStep("Verify RIM InventoryReleased");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = Dreams_AccommodationQueries.getReservationInfoByTpId(tpId).replace(", res_mgmt.tc_gst d", "").replace("and rownum = 1", "");
        sql = sql + "and b.tc_grp_nb = '" + tcgId + "'";
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            String id = rs.getValue("ASGN_OWN_ID", i);
            if (!id.equals("NULL") && !id.equals("0")) {
                Recordset rs2 = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getResourceAssignmentOwnerDetailsByAssignmentOwner(id)));
                TestReporter.softAssertTrue(rs2.getRowCount() == numberOfResources, "Verify that [" + numberOfResources + "] records are returned for assignment owner ID [" + id + "] which indicates that inventory were consumed from RIM.");
            }
        }
        TestReporter.assertAll();
    }

    public void verifyNumberOfChargesByStatus(String status, int numCharges, String tpId) {
        TestReporter.logStep("Verify Number Of Charges");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select x.tp_id, x.tps_id, y.TC_GRP_NB "
                + "from res_mgmt.tps x, res_mgmt.tc_grp y "
                + "where x.tp_id = '" + tpId + "' "
                + "and x.tps_id = y.tps_id";
        Recordset rs = new Recordset(db.getResultSet(sql));
        String ids = "";

        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (i > 1) {
                ids += ",";
            }
            ids += "'" + rs.getValue("TP_ID", i) + "','" + rs.getValue("TPS_ID", i) + "','" + rs.getValue("TC_GRP_NB", i) + "'";
        }

        sql = "select b.CHRG_GRP_ID, c.CHRG_GRP_STS_NM, c.CHRG_GRP_TYP_NM, d.CHRG_ID, d.CHRG_TYP_NM "
                + "from folio.extnl_ref a, folio.chrg_grp_extnl_ref b, folio.chrg_grp c, folio.chrg d "
                + "where a.EXTNL_REF_VAL in (" + ids + ") "
                + "and a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "and b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "and c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "and c.CHRG_GRP_STS_NM = '" + status + "'";

        rs = new Recordset(db.getResultSet(sql));

        do {
            if (rs.getValue("CHRG_TYP_NM").equals("Fee Charge")) {
                numCharges++;
            }
            rs.moveNext();
        } while (rs.hasNext());
        rs.moveFirst();

        TestReporter.softAssertEquals(rs.getRowCount(), numCharges, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numCharges + "].");

        TestReporter.assertAll();
    }

    public void verifyNumberOfTpPartiesByTpId(int numParties, String tpId) {
        String[] partyIds = searchForPartyByTpId(getEnvironment(), tpId);
        TestReporter.softAssertEquals(partyIds.length, numParties, "Verify that the number of party ids [" + partyIds.length + "] is that which is expected [" + numParties + "].");
    }

    public void verifyOdsGuestIdCreated(Boolean expected, String tpId) {
        setGuestIdExpected(expected);
        verifyOdsGuestIdCreated(tpId);
    }

    public void verifyOdsGuestIdCreated(Boolean expected, String partyId, String tpId) {
        this.partyId = partyId;
        setGuestIdExpected(expected);
        verifyOdsGuestIdCreated(tpId);
    }

    public void verifyOdsGuestIdCreated(String tpId, Boolean guestIdExpected) {
        setGuestIdExpected(guestIdExpected);
        verifyOdsGuestIdCreated(tpId);
    }

    public void verifyOdsGuestIdCreated(String tpId) {
        TestReporter.logStep("Verify ODS Guest IDs For TP ID [" + tpId + "]");
        String[] partyIds = null;
        if (partyId == null) {
            partyIds = searchForPartyByTpId(getEnvironment(), tpId);
        } else {
            partyIds = new String[1];
            partyIds[0] = partyId;
        }

        for (int i = 0; i < partyIds.length; i++) {
            String sql = "";
            if (partyId != null) {
                sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                        + "from guest.TXN_PTY_EXTNL_REF b "
                        + "where b.TXN_PTY_ID = '" + partyIds[i] + "'";
            } else {
                sql = "select a.TXN_PTY_ID, b.TXN_PTY_EXTNL_REF_VAL "
                        + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                        + "where a.TXN_PTY_ID = '" + partyIds[i] + "' "
                        + "and a.TXN_PTY_ID = b.TXN_PTY_ID";
            }

            Database db = new OracleDatabase(environment, Database.DREAMS);
            Recordset rs;

            int tries = 0;
            int maxTries = 5;
            do {
                Sleeper.sleep(Randomness.randomNumberBetween(1, 3) * 1000);
                rs = new Recordset(db.getResultSet(sql));
                tries++;
            } while (tries <= maxTries && rs.getRowCount() < 1);
            // rs.print();

            if (rs.getRowCount() == 0) {
                throw new SQLValidationException("Failed to find Guest External Ref", sql);
            }
            String odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL", 1);

            sql = "select * "
                    + "from odsp.txn_idvl a "
                    + "where a.TXN_IDVL_ID = '" + odsGuestId + "'";

            db = new OracleDatabase(environment, Database.GOMASTER);

            tries = 0;
            maxTries = 5;
            do {
                Sleeper.sleep(Randomness.randomNumberBetween(1, 3) * 1000);
                rs = new Recordset(db.getResultSet(sql));
                tries++;
            } while (rs.getRowCount() == 0 && tries < maxTries);
            if (getGuestIdExpected() == null) {
                TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that an ODS record was returned for ODS guest ID [" + odsGuestId + "] guest ID was returned for TP party ID [" + partyIds[i] + "].");
            } else {
                if (getGuestIdExpected() == true) {
                    TestReporter.softAssertTrue(rs.getRowCount() == 1, "Verify that an ODS record was returned for ODS guest ID [" + odsGuestId + "] guest ID was returned for TP party ID [" + partyIds[i] + "].");
                } else {
                    TestReporter.softAssertTrue(rs.getRowCount() == 0, "Verify that no ODS record was returned for ODS guest ID [" + odsGuestId + "] guest ID was returned for TP party ID [" + partyIds[i] + "].");
                }
            }
        }
        TestReporter.assertAll();
    }

    public String[] searchForPartyByTpId(String environment, String tpId) {
        String sql = "select a.TXN_PTY_ID "
                + "from res_mgmt.tp_pty a "
                + "where a.tp_id = '" + tpId + "' ";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String[] ids = new String[rs.getRowCount()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = rs.getValue("TXN_PTY_ID", i + 1);
        }
        return ids;
    }

    public void verifyChargeDetail(int numChargesExpected, String tpId) {
        TestReporter.logStep("Verify charge details was created in Folio");
        String sql = Dreams_AccommodationQueries.getChargeInformationByTp(tpId);
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Failed to find a charge details for travel plan Id [ " + tpId + " ]", sql);
        }

        do {
            if (rs.getValue("CHRG_TYP_NM").equals("Fee Charge")) {
                numChargesExpected++;
            }
            rs.moveNext();
        } while (rs.hasNext());
        rs.moveFirst();

        TestReporter.softAssertEquals(rs.getRowCount(), numChargesExpected, "Verify that the number of charge details [" + rs.getRowCount() + "] is that which is expected [" + numChargesExpected + "].");

        TestReporter.assertAll();
    }

    public void verifyNameOnCharges(String tpId, String tpsId, String tcgId, Guest guest) {
        TestReporter.logStep("Verify the name on the charges.");
        String sql = "select d.CHRG_BY_NM "
                + "from folio.extnl_ref a, folio.chrg_grp_extnl_ref b, folio.chrg_grp c "
                + "left join folio.chrg d on c.chrg_grp_id = d.chrg_grp_id "
                + "where a.EXTNL_REF_VAL in ('" + tpId + "','" + tpsId + "','" + tcgId + "') "
                + "and a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "and b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "and d.chrg_id is not null";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String expectedName = guest.getLastName() + "," + guest.getFirstName();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            Boolean match = rs.getValue("CHRG_BY_NM", i).toLowerCase().equals(expectedName.toLowerCase()) || rs.getValue("CHRG_BY_NM", i).toUpperCase().equals(guest.getFirstName().toUpperCase() + " " + guest.getLastName().toUpperCase());
            TestReporter.softAssertEquals(true, match, "Verify that the name on the charges [" + rs.getValue("CHRG_BY_NM", i) + "] is that which is expcted [" + expectedName + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyTpPartyId(String tpPtyId, String tpId) {
        TestReporter.logStep("Verify TP Party ID");
        String sql = "select a.TXN_PTY_ID "
                + "from res_mgmt.tp_pty a "
                + "where a.tp_id = '" + tpId + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("TXN_PTY_ID", i), tpPtyId, "Verify that the TP party ID [" + rs.getValue("TXN_PTY_ID", i) + "] is that which is expcted [" + tpPtyId + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyTpPartyIds(Map<String, String> tpPtyIds, String tpId) {
        TestReporter.logStep("Verify TP Party ID");
        String sql = "select a.TXN_PTY_ID "
                + "from res_mgmt.tp_pty a "
                + "where a.tp_id = '" + tpId + "'";
        System.out.println();
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        Map<String, String> temp = new HashMap<>();
        temp.putAll(tpPtyIds);
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (tpPtyIds.containsKey(rs.getValue("TXN_PTY_ID", i))) {
                temp.remove(rs.getValue("TXN_PTY_ID", i));
                TestReporter.softAssertTrue(tpPtyIds.containsKey(rs.getValue("TXN_PTY_ID", i)), "Verify that the TP party ID [" + rs.getValue("TXN_PTY_ID", i) + "] contained in the list of expected TP party IDs [" + tpPtyIds + "].");
            }
        }
        if (temp.size() > 0) {
            TestReporter.softAssertTrue(false, "TP Party IDs [" + temp + "] were not found to be associated with TP ID [" + tpId + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyOdsGuestIdChanged(String odsGuestId, Boolean changeExpected, String tpId) {
        TestReporter.logStep("Verify any changes in ODS guest ID");
        String sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                + "where a.tp_id = '" + tpId + "' "
                + "and a.TXN_PTY_ID = b.TXN_PTY_ID "
                + "order by b.TXN_PTY_EXTNL_REF_VAL asc";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        String newOdsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL", 1);

        sql = "select a.TXN_IDVL_ID "
                + "from odsp.txn_idvl a "
                + "where a.TXN_IDVL_ID = '" + newOdsGuestId + "'";
        db = new OracleDatabase(environment, Database.GOMASTER);
        rs = new Recordset(db.getResultSet(sql));
        Boolean changes = !rs.getValue("TXN_IDVL_ID", 1).equals(odsGuestId);

        String value = "";
        if (changeExpected) {
            value = "not";
        }
        TestReporter.assertEquals(changes, changeExpected, "Verify that the new ODS guest ID [" + rs.getValue("TXN_IDVL_ID", 1) + "] is " + value + " the same as the original ID [" + odsGuestId + "].");
    }

    public void verifyOdsGuestIdContained(String odsGuestId, String tpId) {
        TestReporter.logStep("Verify any changes in ODS guest ID");
        String sql = "select b.TXN_PTY_EXTNL_REF_VAL "
                + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                + "where a.tp_id = '" + tpId + "' "
                + "and a.TXN_PTY_ID = b.TXN_PTY_ID "
                + "order by b.TXN_PTY_EXTNL_REF_VAL asc";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        String odsGuestIds = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            odsGuestIds += "'" + rs.getValue("TXN_PTY_EXTNL_REF_VAL", i) + "'";
            if (i < rs.getRowCount()) {
                odsGuestIds += ",";
            }
        }

        sql = "select a.TXN_IDVL_ID "
                + "from odsp.txn_idvl a "
                + "where a.TXN_IDVL_ID in (" + odsGuestIds + ")";
        db = new OracleDatabase(environment, Database.GOMASTER);
        rs = new Recordset(db.getResultSet(sql));

        Boolean found = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("TXN_IDVL_ID", i).equals(odsGuestId)) {
                found = true;
                break;
            }

        }
        TestReporter.assertTrue(found, "Verify that the new ODS guest ID [" + rs.getValue("TXN_IDVL_ID", 1) + "] contains the original ID [" + odsGuestId + "].");
    }

    public void verifyGoMasterInfoForNewGuest(Guest guest, String odsGuestId) {
        TestReporter.logStep("Verify GoMaster information for Guest");
        String sql = "select * "
                + "from odsp.addr b "
                + "where b.LCTR_ID in ( "
                + "        select a.LCTR_ID "
                + "        from odsp.txn_idvl_addr a "
                + "        where a.txn_idvl_id = '" + odsGuestId + "')";
        Database db = new OracleDatabase(environment, Database.GOMASTER);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        Map<String, Boolean> valueFound = new HashMap<>();
        valueFound.put(guest.primaryAddress().getCountryAbbv(), false);
        valueFound.put(guest.primaryAddress().getStateAbbv(), false);
        valueFound.put(guest.primaryAddress().getAddress1(), false);
        for (int i = 1; i <= rs.getRowCount(); i++) {
            // System.out.println();
            if (rs.getValue("CNTRY_ID", i).toLowerCase().contains(guest.primaryAddress().getCountryAbbv().toLowerCase())) {
                valueFound.put(guest.primaryAddress().getCountryAbbv(), true);
            }
            if (rs.getValue("ST_NM", i).equalsIgnoreCase(guest.primaryAddress().getStateAbbv())) {
                valueFound.put(guest.primaryAddress().getStateAbbv(), true);
            }
            if (rs.getValue("ADDR_RAW_ADDR_VL", i).toLowerCase().replace("road", "rd")
                    .contains(guest.primaryAddress().getAddress1().toLowerCase().replace("road", "rd"))) {
                valueFound.put(guest.primaryAddress().getAddress1(), true);
            }
        }
        for (Entry<String, Boolean> entry : valueFound.entrySet()) {
            TestReporter.softAssertTrue(entry.getValue(), "Verify that [" + entry.getKey() + "] was found in the address info for ODS guest ID [" + odsGuestId + "].");
        }

        sql = "select * "
                + "from odsp.EML b "
                + "where b.LCTR_ID in ( "
                + "        select a.LCTR_ID "
                + "        from odsp.TXN_IDVL_EML a "
                + "        where a.txn_idvl_id = '" + odsGuestId + "')";
        rs = new Recordset(db.getResultSet(sql));
        valueFound = new HashMap<>();
        valueFound.put(guest.primaryEmail().getEmail(), false);
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("EML_NM", i).equalsIgnoreCase(guest.primaryEmail().getEmail())) {
                valueFound.put(guest.primaryEmail().getEmail(), true);
            }
        }
        for (Entry<String, Boolean> entry : valueFound.entrySet()) {
            TestReporter.softAssertTrue(entry.getValue(), "Verify that [" + entry.getKey() + "] was found in the email info for ODS guest ID [" + odsGuestId + "].");
        }

        sql = "select * "
                + "from odsp.PHN b "
                + "where b.LCTR_ID in ( "
                + "        select a.LCTR_ID "
                + "        from odsp.TXN_IDVL_PHN a "
                + "        where a.txn_idvl_id = '" + odsGuestId + "')";
        rs = new Recordset(db.getResultSet(sql));
        valueFound = new HashMap<>();
        valueFound.put(guest.primaryPhone().getNumber(), false);
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("PHN_RAW_PHN_NB", i).replace(")", "").replace("(", "").replace("-", "").equals(guest.primaryPhone().getNumber()) ||
                    rs.getValue("PHN_PHN_NB").equals(guest.primaryPhone().getNumber())) {
                valueFound.put(guest.primaryPhone().getNumber(), true);
            }
        }
        for (Entry<String, Boolean> entry : valueFound.entrySet()) {
            TestReporter.softAssertTrue(entry.getValue(), "Verify that [" + entry.getKey() + "] was found in the phone info for ODS guest ID [" + odsGuestId + "].");
        }
        TestReporter.assertAll();
    }

    public String getAssignmentOwnerId(String tpId) {
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getAccommodationComponentAssignemtnOwnerIDByTpId(tpId)));
        return rs.getValue("ASGN_OWN_ID", 1);
    }

    public void verifyAssignmentOwnerIdChanged(String assignmentOwnerId, Boolean changeExpected, String tpId) {
        TestReporter.logStep("Verify and changes in assignment owner ID");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getAccommodationComponentAssignemtnOwnerIDByTpId(tpId)));
        String value = "";
        if (changeExpected) {
            value = "not";
        }
        Boolean changed = !rs.getValue("ASGN_OWN_ID", 1).equals(assignmentOwnerId);
        TestReporter.assertEquals(changed, changeExpected, "Verify that the assignment owner ID [" + rs.getValue("ASGN_OWN_ID", 1) + "] is " + value + " that which is expected [" + assignmentOwnerId + "].");
    }

    public void verifyAssignmentOwnerIdChanged_TCG(String assignmentOwnerId, Boolean changeExpected, String tcgId) {
        TestReporter.logStep("Verify and changes in assignment owner ID");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getAssignmentOwnerIdByTcg(tcgId)));
        String value = "";
        if (changeExpected) {
            value = "not";
        }
        Boolean changed = !rs.getValue("ASGN_OWN_ID", 1).equals(assignmentOwnerId);
        TestReporter.assertEquals(changed, changeExpected, "Verify that the assignment owner ID [" + rs.getValue("ASGN_OWN_ID", 1) + "] is " + value + " that which is expected [" + assignmentOwnerId + "].");
    }

    public void verifyRIMPartyMIx(String tpId, String adultCount, String childCount, Boolean recordsExpected) {
        TestReporter.logStep("Verify the RIM party Mix");
        String sql = "select b.ADLT_CN, b.CHLD_CN "
                + "from rsrc_inv.rsrc_own_ref a, rsrc_inv.rsrc_own b "
                + "where a.EXTNL_OWN_REF_VAL = '" + tpId + "' "
                + "and a.RSRC_OWN_ID = b.RSRC_OWN_ID";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = null;
        int tries = 0;
        int maxTries = 60;
        Boolean success = false;
        rs = new Recordset(db.getResultSet(sql));

        if (recordsExpected) {
            do {
                Sleeper.sleep(1000);
                rs = new Recordset(db.getResultSet(sql));
                do {
                    if (rs.getValue("ADLT_CN").equals(adultCount)) {
                        success = true;
                    } else {
                        rs.moveNext();
                    }
                } while (rs.hasNext() && !success);
                tries++;
            } while (tries < maxTries && !success);
            TestReporter.softAssertEquals(rs.getValue("ADLT_CN"), adultCount, "Verify that the adult count [" + rs.getValue("ADLT_CN") + "] is that which is expected [" + adultCount + "].");
            TestReporter.softAssertEquals(rs.getValue("CHLD_CN"), childCount, "Verify that the child count [" + rs.getValue("CHLD_CN") + "] is that which is expected [" + childCount + "].");
        } else {
            TestReporter.softAssertTrue(rs.getRowCount() == 0, "Verify that no records were retruned.");
        }
        TestReporter.assertAll();
    }

    public void verifyRIMPartyMIx(String tpId, String[] adultCount, String[] childCount) {
        TestReporter.logStep("Verify the RIM party Mix");
        String sql = "select b.ADLT_CN, b.CHLD_CN "
                + "from rsrc_inv.rsrc_own_ref a, rsrc_inv.rsrc_own b "
                + "where a.EXTNL_OWN_REF_VAL = '" + tpId + "' "
                + "and a.RSRC_OWN_ID = b.RSRC_OWN_ID "
                + "order by a.RSRC_OWN_ID asc";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (int i = 0; i < rs.getRowCount(); i++) {

            TestReporter.softAssertEquals(rs.getValue("ADLT_CN", i + 1), adultCount[i], "Verify that the adult count [" + rs.getValue("ADLT_CN", i + 1) + "] is that which is expected [" + adultCount[i] + "].");
            TestReporter.softAssertEquals(rs.getValue("CHLD_CN", i + 1), childCount[i], "Verify that the child count [" + rs.getValue("CHLD_CN", i + 1) + "] is that which is expected [" + childCount[i] + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyInventoryTrackingIdInRIM(String tpId, String inventoryTrackingId, Boolean recordsExpected) {
        TestReporter.logStep("Verify Inventory Tracking ID in RIM");
        String sql = "select a.EXTNL_OWN_REF_VAL "
                + "from rsrc_inv.RSRC_OWN_REF a "
                + "where a.RSRC_OWN_ID in( "
                + "        select b.RSRC_OWN_ID "
                + "        from rsrc_inv.RSRC_OWN_REF b "
                + "        where b.EXTNL_OWN_REF_VAL = '" + tpId + "' "
                + ") "
                + "and a.OWN_REF_TYP_NM = 'INVTRY_TRACKING_ID'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        Map<String, String> invTrackingIds = new HashMap<>();
        if (recordsExpected) {
            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that records were returned as expected.");
            for (int i = 1; i <= rs.getRowCount(); i++) {
                invTrackingIds.put(rs.getValue("EXTNL_OWN_REF_VAL", i), rs.getValue("EXTNL_OWN_REF_VAL", i));
            }
            TestReporter.assertTrue(invTrackingIds.containsKey(inventoryTrackingId), "Verify that the inventory tracking ID [" + rs.getValue("EXTNL_OWN_REF_VAL", 1) + "] is contained in the list of IDs [" + invTrackingIds + "].");
        } else {
            TestReporter.assertTrue(rs.getRowCount() == 0, "Verify that no records were returned as expected.");
        }
    }

    public void validateTpAddedToTravelWith(String gathering, String tpId) {
        TestReporter.logStep("Verify that the TP ID [" + tpId + "] was found to be associated with the gathering ID [" + gathering + "].");
        String sql = "select * "
                + "from res_mgmt.tp_gthr "
                + "WHERE GTHR_CD = '" + gathering + "'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        Boolean tpIdFound = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("TP_ID", i).equals(tpId)) {
                tpIdFound = true;
                break;
            }
        }
        TestReporter.assertTrue(tpIdFound, "Verify that the TP ID [" + tpId + "] was found to be associated with the gathering ID [" + gathering + "].");
    }

    public void verifyModificationComment(Retrieve retrieve, String expectedComment) {
        TestReporter.logStep("Verify Comment");
        String actualComment = "";
        try {
            actualComment = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/commentText");
        } catch (XPathNotFoundException e) {
        }
        TestReporter.assertEquals(actualComment, expectedComment, "Verify that the comment text [" + actualComment + "] is that which is expected [" + expectedComment + "].");
    }

    public void verifyModificationRoomComment(Retrieve retrieve, Map<String, String> expectedValues, String isDefault) {
        TestReporter.logStep("Verify Accommodation Comment");
        int numComments = 0;
        try {
            numComments = XMLTools.getNodeList(retrieve.getResponseDocument(), "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments").getLength();
        } catch (XPathNotFoundException e) {
        }
        TestReporter.assertTrue(numComments == 1, "Verify that an accommodation comment was found in the retrieve.");
        TestReporter.softAssertEquals(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/auditDetail/createdBy"), expectedValues.get("createdBy"),
                "Verify that the comment created-by [" + retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/auditDetail/createdBy") + "] is that which is expected [" + expectedValues.get("createdBy") + "].");
        TestReporter.softAssertEquals(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/auditDetail/createdDate").split("T")[0], expectedValues.get("createdDate").split("T")[0],
                "Verify that the comment created-date [" + retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/auditDetail/createdDate").split("T")[0] + "] is that which is expected [" + expectedValues.get("createdDate").split("T")[0] + "].");
        TestReporter.softAssertEquals(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/commentText"), expectedValues.get("commentText"),
                "Verify that the comment text [" + retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/commentText") + "] is that which is expected [" + expectedValues.get("commentText") + "].");
        TestReporter.softAssertEquals(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/default"), isDefault,
                "Verify that the comment text [" + retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/default") + "] is that which is expected [" + isDefault + "].");
        TestReporter.assertAll();
    }

    public void verifyModificationTpsComment(String tpsId, String expectedCommentText) {
        TestReporter.logStep("Verify Comment");
        String sql = "select a.RES_MGMT_REQ_TX "
                + "from res_mgmt.res_mgmt_req a "
                + "where a.tps_id = '" + tpsId + "' "
                + "and a.RES_MGMT_REQ_TYP_NM = 'TravelPlanSegmentComment'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() == 1, "Verify that a TPS comment was found in the DREAMS database.");
        String actualComment = rs.getValue("RES_MGMT_REQ_TX", 1);
        TestReporter.assertEquals(actualComment, expectedCommentText, "Verify that the TPS comment text [" + actualComment + "] is that which is expected [" + expectedCommentText + "].");
    }

    public void validateSpecialNeeds(String tpId, String flag) {
        if (flag.equals("true")) {
            flag = "Y";
        } else if (flag.equals("false")) {
            flag = "N";
        }
        TestReporter.logStep("Validate Special Needs in RIM");
        String sql = "select d.SPCL_NEED_REQ_IN "
                + "from res_mgmt.tps a "
                + "left join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "left join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "left join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWN_ID = d.ASGN_OWNR_ID "
                + "where a.tp_id = '" + tpId + "' "
                + "and c.PROD_TYP_NM = 'AccommodationProduct'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("SPCL_NEED_REQ_IN", i), flag, "Verify that the RIM special needs flag [" + rs.getValue("SPCL_NEED_REQ_IN", i) + "] is that which is expected [" + flag + "]");
        }
        TestReporter.assertAll();
    }

    public void validatePayment(String tpId, int numExpectedPayments, String paymentAmount) {
        String sql = "select f.* "
                + "from folio.extnl_ref a "
                + "left outer join folio.chrg_grp_extnl_ref b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.chrg c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.chrg_item d on c.chrg_id = d.chrg_id "
                + "left outer join folio.chrg_grp_folio e on b.CHRG_GRP_ID = e.ROOT_CHRG_GRP_ID "
                + "left outer join folio.folio_item f on e.CHRG_GRP_FOLIO_ID = f.FOLIO_ID "
                + "where a.EXTNL_REF_VAL in ( "
                + "        (select to_char(a.tp_id) "
                + "        from res_mgmt.tps a "
                + "        left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "        where a.tp_id = " + tpId + "), "
                + "        (select to_char(a.tps_id) "
                + "        from res_mgmt.tps a "
                + "        left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "        where a.tp_id = " + tpId + "), "
                + "        (select to_char(b.tc_grp_nb) "
                + "        from res_mgmt.tps a "
                + "        left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "        where a.tp_id = " + tpId + "))";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        int numPayments = 0;
        do {
            if (rs.getValue("FOLIO_ITEM_TYP_NM").equals("PAYMENT_ITEM")) {
                numPayments++;
                if (paymentAmount != null) {
                    TestReporter.softAssertEquals(rs.getValue("FOLIO_ITEM_AM"), "-" + paymentAmount, "Verify that a payment in the amount of [" + paymentAmount + "] is found to be associated with TP ID [" + tpId + "]. Instead found [" + rs.getValue("FOLIO_ITEM_AM") + "].");
                }
            }
            rs.moveNext();
        } while (rs.hasNext());
        rs.moveFirst();

        TestReporter.softAssertEquals(numPayments, numExpectedPayments, "Verify that the number of payments [" + numPayments + "] is that which is expected [" + numExpectedPayments + "].");
        TestReporter.assertAll();
    }

    public String validateAdmissionComponentAdded(String travelComponentGroupingId) {
        String admissionTcId = null;
        String sql = "select * "
                + "from res_mgmt.tc a "
                + "where a.tc_grp_nb = " + travelComponentGroupingId;
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        boolean found = false;
        do {
            if (rs.getValue("TC_TYP_NM").equals("AdmissionComponent")) {
                found = true;
                admissionTcId = rs.getValue("TC_ID");
                rs.moveLast();
            } else {
                rs.moveNext();
            }
        } while (rs.hasNext() && !found);
        TestReporter.assertTrue(found, "Verify that an admission TC [" + admissionTcId + "] was found for TCG [" + travelComponentGroupingId + "].");
        return admissionTcId;
    }

    public String validateAdmissionComponentDetails(String admissionComponentId, String code) {
        TestReporter.logStep("Verify admission component details");
        String sql = "select * "
                + "from res_mgmt.adm_cmpnt a "
                + "WHERE a.ADM_TC_ID IN (" + admissionComponentId + ")";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getValue("ATS_TKT_CD").equals(code), "Verify that the admission component ticket code [" + rs.getValue("ATS_TKT_CD") + "] is that which is expected [" + code + "].");
        return rs.getValue("TKT_PRC_AM");
    }

    public void validateAdmissionComponentPrice(String tpId, String ticketComponentPrice) {
        TestReporter.logStep("Verify admission component charge and price");
        String sql = "select d.* "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID "
                + "where a.EXTNL_REF_VAL in ( "
                + "        (select to_char(a.tp_id) "
                + "        from res_mgmt.tps a "
                + "        where a.tp_id = '" + tpId + "'), "
                + "        (select to_char(a.tps_id) "
                + "        from res_mgmt.tps a "
                + "        where a.tp_id = '" + tpId + "'), "
                + "        (select to_char(b.tc_grp_nb) "
                + "        from res_mgmt.tps a "
                + "        join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "        where a.tp_id = '" + tpId + "') "
                + ")";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        boolean found = false;
        boolean match = false;
        do {
            if (rs.getValue("REV_CLS_NM").equals("Admission")) {
                found = true;
                if (rs.getValue("CHRG_AM").equals(ticketComponentPrice)) {
                    match = true;
                }
                rs.moveLast();
            } else {
                rs.moveNext();
            }
        } while (rs.hasNext() && !found);
        TestReporter.softAssertTrue(found, "Verify that an admission charge was found.");
        TestReporter.softAssertTrue(match, "Verify that the admission charge amount is that which is expected [" + ticketComponentPrice + "].");
        TestReporter.assertAll();

    }

    public void validateTPV3(String tpId, String status, String arrivalDate, String departureDate, String tpPartyId, Guest guest, int accommCount, int packageCount, String ada, String groupCode, String facilityId) {
        TestReporter.logStep("Validating TPV3 record creation");
        String sql = "select a.* "
                + "from sales_tp.tp a "
                + "join sales_tp.tp_pty b on a.tp_id = b.tp_id "
                + "join sales_tp.sls_ord c on b.tp_id = c.tp_id "
                + "where a.tp_id = " + tpId;
        Database db = new OracleDatabase(getEnvironment(), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() > 0) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        TestReporter.log("Validating TP table");
        TestReporter.softAssertEquals(rs.getValue("MKTG_TP_AGE_GRP_NM"), "FAMILY_BOTH", "Verify that the market TP age group name [" + rs.getValue("MKTG_TP_AGE_GRP_NM") + "] is that which is expected [FAMILY_BOTH].");
        TestReporter.softAssertEquals(rs.getValue("TP_STRT_DT").split(" ")[0], arrivalDate, "Verify that the start date [" + rs.getValue("TP_STRT_DT").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
        TestReporter.softAssertEquals(rs.getValue("TP_END_DT").split(" ")[0], departureDate, "Verify that the end date [" + rs.getValue("TP_END_DT").split(" ")[0] + "] is that which is expected [" + departureDate + "].");

        TestReporter.log("Validating TP_PTY table");
        sql = sql.replace("a.*", "b.*");
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getValue("AGE_TYP_NM"), "Adult", "Verify that the guest age type [" + rs.getValue("AGE_TYP_NM") + "] is that which is expected [Adult].");
        TestReporter.softAssertEquals(rs.getValue("RL_NM"), "Guest", "Verify that the guest role [" + rs.getValue("RL_NM") + "] is that which is expected [Guest].");
        TestReporter.softAssertEquals(rs.getValue("TXN_GST_ID"), tpPartyId, "Verify that the guest ID [" + rs.getValue("TXN_GST_ID") + "] is that which is expected [" + tpPartyId + "].");
        TestReporter.softAssertEquals(rs.getValue("PRMY_GST_IN"), "Y", "Verify that the primary guest indicator [" + rs.getValue("PRMY_GST_IN") + "] is that which is expected [Y].");
        TestReporter.softAssertEquals(rs.getValue("AGE_NB"), guest.getAge(), "Verify that the guest age [" + rs.getValue("AGE_NB") + "] is that which is expected [" + guest.getAge() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_LST_NM"), guest.getLastName(), "Verify that the guest last name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + guest.getLastName() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_FST_NM"), guest.getFirstName(), "Verify that the guest first name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + guest.getFirstName() + "].");

        TestReporter.log("Validating SLS_ORD table");
        sql = sql.replace("b.*", "c.*");
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getValue("DEST_NM"), "WDW", "Verify that the sales order destination name [" + rs.getValue("DEST_NM") + "] is that which is expected [WDW].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_STS_NM"), status, "Verify that the sales order status [" + rs.getValue("SLS_ORD_ARVL_DT") + "] is that which is expected [" + status + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0], arrivalDate, "Verify that the sales order start date [" + rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0], departureDate, "Verify that the sales order end date [" + rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0] + "] is that which is expected [" + departureDate + "].");

        TestReporter.log("Validating SLS_ORD_ITEM table");
        sql = "select d.* "
                + "from sales_tp.tp a "
                + "join sales_tp.sls_ord c on a.tp_id = c.tp_id "
                + "join sales_tp.sls_ord_item d on c.sls_ord = d.sls_ord "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getRowCount(), accommCount + packageCount, "Verify that the number of sales order items [" + rs.getRowCount() + "[ is that which is expected [" + accommCount + packageCount + "].");
        int accommFound = 0;
        int packageFound = 0;
        do {
            if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("PACKAGE")) {
                packageFound++;
            } else if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("ACCOMMODATION")) {
                accommFound++;
            }
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0], arrivalDate, "Verify that the sales order item start date [" + rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0], departureDate, "Verify that the sales order item end date [" + rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0] + "] is that which is expected [" + departureDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_STS_NM"), status, "Verify that the sales order item status [" + rs.getValue("SLS_ORD_ITEM_STS_NM") + "] is that which is expected [" + status + "].");
            TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify that the sales order item facility ID [" + rs.getValue("FAC_ID") + "] is that which is expected [" + facilityId + "].");
            TestReporter.softAssertEquals(rs.getValue("GRP_CD"), groupCode, "Verify that the sales order item group code [" + rs.getValue("GRP_CD") + "] is that which is expected [" + groupCode + "].");
            TestReporter.softAssertEquals(rs.getValue("SPCL_NEED_REQ_IN"), ada, "Verify that the sales order item ADA flag [" + rs.getValue("SPCL_NEED_REQ_IN") + "] is that which is expected [" + ada + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertEquals(accommFound, accommCount, "Verify that the number of accommodation items found [" + accommFound + "] is that which is expected [" + accommCount + "].");
        TestReporter.softAssertEquals(packageFound, packageCount, "Verify that the number of package items found [" + packageFound + "] is that which is expected [" + packageCount + "].");

        TestReporter.assertAll();
    }

    public void validateTPV3(String tpId, String status, Map<String, String> arrivalDates, Map<String, String> departureDates,
            Map<String, String> partyId, Guest guest, int accommCount, int packageCount, int dinnerShowCount, String ada, String groupCode, String facilityId) {
        TestReporter.logStep("Validating TPV3 record creation");
        String sql = "select a.* "
                + "from sales_tp.tp a "
                + "join sales_tp.tp_pty b on a.tp_id = b.tp_id "
                + "join sales_tp.sls_ord c on b.tp_id = c.tp_id "
                + "where a.tp_id = " + tpId;
        Database db = new OracleDatabase(getEnvironment(), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() > 0) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        TestReporter.log("Validating TP table");
        TestReporter.softAssertEquals(rs.getValue("MKTG_TP_AGE_GRP_NM"), "FAMILY_BOTH", "Verify that the market TP age group name [" + rs.getValue("MKTG_TP_AGE_GRP_NM") + "] is that which is expected [FAMILY_BOTH].");
        TestReporter.softAssertTrue(arrivalDates.containsValue(rs.getValue("TP_STRT_DT").split(" ")[0]), "Verify that the start date [" + rs.getValue("TP_STRT_DT").split(" ")[0] + "] is contained in a map of values that are expected [" + arrivalDates + "].");
        TestReporter.softAssertTrue(departureDates.containsValue(rs.getValue("TP_END_DT").split(" ")[0]), "Verify that the end date [" + rs.getValue("TP_END_DT").split(" ")[0] + "] is contained in a map of values that are  [" + departureDates + "].");

        TestReporter.log("Validating TP_PTY table");
        sql = sql.replace("a.*", "b.*");
        rs = new Recordset(db.getResultSet(sql));
        do {
            TestReporter.softAssertEquals(rs.getValue("AGE_TYP_NM"), "Adult", "Verify that the guest age type [" + rs.getValue("AGE_TYP_NM") + "] is that which is expected [Adult].");
            TestReporter.softAssertEquals(rs.getValue("RL_NM"), "Guest", "Verify that the guest role [" + rs.getValue("RL_NM") + "] is that which is expected [Guest].");
            TestReporter.softAssertEquals(rs.getValue("IDVL_LST_NM"), guest.getLastName(), "Verify that the guest last name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + guest.getLastName() + "].");
            TestReporter.softAssertEquals(rs.getValue("IDVL_FST_NM"), guest.getFirstName(), "Verify that the guest first name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + guest.getFirstName() + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.log("Validating SLS_ORD table");
        sql = sql.replace("b.*", "c.*");
        rs = new Recordset(db.getResultSet(sql));
        do {

            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_STS_NM"), status, "Verify that the sales order status [" + rs.getValue("SLS_ORD_ARVL_DT") + "] is that which is expected [" + status + "].");
            TestReporter.softAssertTrue(arrivalDates.containsValue(rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0]), "Verify that the start date [" + rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0] + "] is contained in a map of values that are expected [" + arrivalDates + "].");
            TestReporter.softAssertTrue(departureDates.containsValue(rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0]), "Verify that the end date [" + rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0] + "] is contained in a map of values that are  [" + departureDates + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.log("Validating SLS_ORD_ITEM table");
        sql = "select d.* "
                + "from sales_tp.tp a "
                + "join sales_tp.sls_ord c on a.tp_id = c.tp_id "
                + "join sales_tp.sls_ord_item d on c.sls_ord = d.sls_ord "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getRowCount(), accommCount + packageCount + dinnerShowCount, "Verify that the number of sales order items [" + rs.getRowCount() + "[ is that which is expected [" + (accommCount + packageCount + dinnerShowCount) + "].");
        int accommFound = 0;
        int packageFound = 0;
        int dinnerFound = 0;
        do {
            if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("PACKAGE")) {
                packageFound++;
            } else if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("ACCOMMODATION")) {
                accommFound++;
            } else if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("DINNER_SHOW")) {
                dinnerFound++;
            }
            TestReporter.softAssertTrue(arrivalDates.containsValue(rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0]), "Verify that the start date [" + rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0] + "] is contained in a map of values that are expected [" + arrivalDates + "].");
            TestReporter.softAssertTrue(departureDates.containsValue(rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0]), "Verify that the end date [" + rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0] + "] is contained in a map of values that are  [" + departureDates + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_STS_NM"), status, "Verify that the sales order item status [" + rs.getValue("SLS_ORD_ITEM_STS_NM") + "] is that which is expected [" + status + "].");
            TestReporter.softAssertEquals(rs.getValue("GRP_CD"), groupCode, "Verify that the sales order item group code [" + rs.getValue("GRP_CD") + "] is that which is expected [" + groupCode + "].");
            TestReporter.softAssertEquals(rs.getValue("SPCL_NEED_REQ_IN"), ada, "Verify that the sales order item ADA flag [" + rs.getValue("SPCL_NEED_REQ_IN") + "] is that which is expected [" + ada + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertEquals(accommFound, accommCount, "Verify that the number of accommodation items found [" + accommFound + "] is that which is expected [" + accommCount + "].");
        TestReporter.softAssertEquals(packageFound, packageCount, "Verify that the number of package items found [" + packageFound + "] is that which is expected [" + packageCount + "].");
        TestReporter.softAssertEquals(dinnerFound, dinnerShowCount, "Verify that the number of dinner show items found [" + dinnerFound + "] is that which is expected [" + dinnerShowCount + "].");

        TestReporter.assertAll();
    }

    public void validateTPV3(String tpId, String status, String arrivalDate, String departureDate, Guest guest, int accommCount, int packageCount, String ada, String groupCode, String facilityId, Map<Integer, Guest> additionalGuests) {
        TestReporter.logStep("Validating TPV3 record creation");
        String sql = "select a.* "
                + "from sales_tp.tp a "
                + "join sales_tp.tp_pty b on a.tp_id = b.tp_id "
                + "join sales_tp.sls_ord c on b.tp_id = c.tp_id "
                + "where a.tp_id = " + tpId;
        Database db = new OracleDatabase(getEnvironment(), Database.SALESTP);
        Recordset rs = null;

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() > 0) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        TestReporter.log("Validating TP table");
        TestReporter.softAssertEquals(rs.getValue("MKTG_TP_AGE_GRP_NM"), "FAMILY_BOTH", "Verify that the market TP age group name [" + rs.getValue("MKTG_TP_AGE_GRP_NM") + "] is that which is expected [FAMILY_BOTH].");
        TestReporter.softAssertEquals(rs.getValue("TP_STRT_DT").split(" ")[0], arrivalDate, "Verify that the start date [" + rs.getValue("TP_STRT_DT").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
        TestReporter.softAssertEquals(rs.getValue("TP_END_DT").split(" ")[0], departureDate, "Verify that the end date [" + rs.getValue("TP_END_DT").split(" ")[0] + "] is that which is expected [" + departureDate + "].");

        TestReporter.log("Validating TP_PTY table");
        sql = sql.replace("a.*", "b.*");
        rs = new Recordset(db.getResultSet(sql));
        int numExpectedRecords = additionalGuests.size() + 1;
        TestReporter.assertTrue(rs.getRowCount() == numExpectedRecords, "Verify that the number of parties [" + rs.getRowCount() + "] is that which is expected [" + numExpectedRecords + "].");
        do {
            Guest locGuest = null;
            boolean found = false;
            if (rs.getValue("IDVL_FST_NM").equals(guest.getFirstName()) && rs.getValue("IDVL_LST_NM").equals(guest.getLastName())) {
                locGuest = guest;
                found = true;
            } else {
                for (Entry<Integer, Guest> entry : additionalGuests.entrySet()) {
                    if (entry.getValue().getFirstName().equals(rs.getValue("IDVL_FST_NM")) && entry.getValue().getLastName().equals(rs.getValue("IDVL_LST_NM"))) {
                        locGuest = entry.getValue();
                        found = true;
                        break;
                    }
                }
            }
            TestReporter.assertTrue(found, "Verify that the guest [" + rs.getValue("IDVL_FST_NM") + " " + rs.getValue("IDVL_LST_NM") + "] was an expected guest.");

            TestReporter.softAssertEquals(rs.getValue("AGE_TYP_NM").toUpperCase(), getAgeTypeByAge(locGuest.getAge()).toUpperCase(), "Verify that the guest age type [" + rs.getValue("AGE_TYP_NM").toUpperCase() + "] is that which is expected [" + getAgeTypeByAge(locGuest.getAge()).toUpperCase() + "].");
            TestReporter.softAssertEquals(rs.getValue("RL_NM"), "Guest", "Verify that the guest role [" + rs.getValue("RL_NM") + "] is that which is expected [Guest].");
            String primaryGuest = locGuest.isPrimary() ? "Y" : "N";
            TestReporter.softAssertEquals(rs.getValue("PRMY_GST_IN"), primaryGuest, "Verify that the primary guest indicator [" + rs.getValue("PRMY_GST_IN") + "] is that which is expected [" + primaryGuest + "].");
            TestReporter.softAssertEquals(rs.getValue("AGE_NB"), locGuest.getAge(), "Verify that the guest age [" + rs.getValue("AGE_NB") + "] is that which is expected [" + locGuest.getAge() + "].");
            TestReporter.softAssertEquals(rs.getValue("IDVL_LST_NM"), locGuest.getLastName(), "Verify that the guest last name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + locGuest.getLastName() + "].");
            TestReporter.softAssertEquals(rs.getValue("IDVL_FST_NM"), locGuest.getFirstName(), "Verify that the guest first name [" + rs.getValue("TP_STRT_DT") + "] is that which is expected [" + locGuest.getFirstName() + "].");
            rs.moveNext();
        } while (rs.hasNext());

        TestReporter.log("Validating SLS_ORD table");
        sql = sql.replace("b.*", "c.*");
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getValue("DEST_NM"), "WDW", "Verify that the sales order destination name [" + rs.getValue("DEST_NM") + "] is that which is expected [WDW].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_STS_NM"), status, "Verify that the sales order status [" + rs.getValue("SLS_ORD_ARVL_DT") + "] is that which is expected [" + status + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0], arrivalDate, "Verify that the sales order start date [" + rs.getValue("SLS_ORD_ARVL_DT").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
        TestReporter.softAssertEquals(rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0], departureDate, "Verify that the sales order end date [" + rs.getValue("SLS_ORD_DPRT_DT").split(" ")[0] + "] is that which is expected [" + departureDate + "].");

        TestReporter.log("Validating SLS_ORD_ITEM table");
        sql = "select d.* "
                + "from sales_tp.tp a "
                + "join sales_tp.sls_ord c on a.tp_id = c.tp_id "
                + "join sales_tp.sls_ord_item d on c.sls_ord = d.sls_ord "
                + "where a.tp_id = " + tpId;
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getRowCount(), accommCount + packageCount, "Verify that the number of sales order items [" + rs.getRowCount() + "[ is that which is expected [" + accommCount + packageCount + "].");
        int accommFound = 0;
        int packageFound = 0;
        do {
            if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("PACKAGE")) {
                packageFound++;
            } else if (rs.getValue("SLS_ORD_ITEM_TYP_NM").equals("ACCOMMODATION")) {
                accommFound++;
            }
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0], arrivalDate, "Verify that the sales order item start date [" + rs.getValue("SLS_ORD_ITEM_STRT_DTS").split(" ")[0] + "] is that which is expected [" + arrivalDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0], departureDate, "Verify that the sales order item end date [" + rs.getValue("SLS_ORD_ITEM_END_DTS").split(" ")[0] + "] is that which is expected [" + departureDate + "].");
            TestReporter.softAssertEquals(rs.getValue("SLS_ORD_ITEM_STS_NM"), status, "Verify that the sales order item status [" + rs.getValue("SLS_ORD_ITEM_STS_NM") + "] is that which is expected [" + status + "].");
            TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify that the sales order item facility ID [" + rs.getValue("FAC_ID") + "] is that which is expected [" + facilityId + "].");
            TestReporter.softAssertEquals(rs.getValue("GRP_CD"), groupCode, "Verify that the sales order item group code [" + rs.getValue("GRP_CD") + "] is that which is expected [" + groupCode + "].");
            TestReporter.softAssertEquals(rs.getValue("SPCL_NEED_REQ_IN"), ada, "Verify that the sales order item ADA flag [" + rs.getValue("SPCL_NEED_REQ_IN") + "] is that which is expected [" + ada + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.softAssertEquals(accommFound, accommCount, "Verify that the number of accommodation items found [" + accommFound + "] is that which is expected [" + accommCount + "].");
        TestReporter.softAssertEquals(packageFound, packageCount, "Verify that the number of package items found [" + packageFound + "] is that which is expected [" + packageCount + "].");

        TestReporter.assertAll();
    }

    public void validateConfirmationDetails(String tpsId, String deliveryMethod, String partyId, String defaultConfirmation, String confirmationSent, String contactName) {
        TestReporter.logStep("Validate TPS confirmation details");
        String sql = "select * "
                + "from res_mgmt.tps_cnfirm_rcpnt a "
                + "where a.tps_id = " + tpsId;
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() == 1, "Verify that a confirmation recipient was created for TPS ID [" + tpsId + "].");
        TestReporter.softAssertEquals(rs.getValue("DLVR_METH_NM"), deliveryMethod, "Verify that the delivery method [" + rs.getValue("DLVR_METH_NM") + "] is that which is expected [" + deliveryMethod + "].");
        TestReporter.softAssertEquals(rs.getValue("LACD_IN"), "N", "Verify that the LACD indicator [" + rs.getValue("LACD_IN") + "] is that which is expected [N].");
        TestReporter.softAssertEquals(rs.getValue("PTY_ID"), partyId, "Verify that the party ID [" + rs.getValue("PTY_ID") + "] is that which is expected [" + partyId + "].");
        TestReporter.softAssertEquals(rs.getValue("DFLT_CNFIRM_IN"), defaultConfirmation, "Verify that the default confirmation indicator [" + rs.getValue("DFLT_CNFIRM_IN") + "] is that which is expected [" + defaultConfirmation + "].");
        TestReporter.softAssertEquals(rs.getValue("DVC_MBRSHP_IN"), "N", "Verify that the DVC membership indicator [" + rs.getValue("DVC_MBRSHP_IN") + "] is that which is expected [N].");
        TestReporter.softAssertEquals(rs.getValue("CNFIRM_SENT_IN"), confirmationSent, "Verify that the confirmation sent indicator [" + rs.getValue("CNFIRM_SENT_IN") + "] is that which is expected [" + confirmationSent + "].");
        TestReporter.softAssertEquals(rs.getValue("CNFIRM_SPRS_IN"), "N", "Verify that the confirmation suppress indicator [" + rs.getValue("CNFIRM_SPRS_IN") + "] is that which is expected [N].");
        TestReporter.softAssertEquals(rs.getValue("CNTCT_NM"), contactName, "Verify that the contact name [" + rs.getValue("CNTCT_NM") + "] is that which is expected [" + contactName + "].");

        if (!rs.getValue("DLVR_METH_NM").equals(deliveryMethod)) {

            String locatorId = rs.getValue("LCTR_ID");
            // Use the confirmation locator ID to determine the source of the locator (address, email, or phone)
            boolean address = false;
            boolean email = false;
            boolean phone = false;
            sql = "select * "
                    + "from guest.TXN_PTY_ADDR_LCTR a "
                    + "where a.TXN_PTY_ADDR_LCTR_ID = " + locatorId;
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == 1) {
                address = true;
            }

            if (!address) {
                sql = sql.replace("TXN_PTY_ADDR_LCTR", "TXN_PTY_EML_LCTR").replace("TXN_PTY_ADDR_LCTR_ID", "TXN_PTY_EML_LCTR_ID");
                rs = new Recordset(db.getResultSet(sql));
                if (rs.getRowCount() == 1) {
                    email = true;
                }
            }

            if (!address && !email) {
                sql = sql.replace("TXN_PTY_EML_LCTR", "TXN_PTY_PHN_LCTR").replace("TXN_PTY_EML_LCTR_ID", "TXN_PTY_PHN_LCTR_ID");
                rs = new Recordset(db.getResultSet(sql));
                if (rs.getRowCount() == 1) {
                    phone = true;
                }
            }

            String actualLocatorValue = "";
            if (address) {
                actualLocatorValue = "Address/Print";
            } else if (email) {
                actualLocatorValue = "Email/Email";
            } else if (phone) {
                actualLocatorValue = "Phone/SMS";
            }
            TestReporter.softAssertEquals(rs.getValue("DLVR_METH_NM"), deliveryMethod, "The locator ID was found to be of type [" + actualLocatorValue + "].");
        }

        TestReporter.assertAll();
    }

    public void validateGathering(String travelPlanId, Map<String, String> gatheringData) {
        TestReporter.logStep("Validate gathering information");

        TestReporter.log("Validate gathering in Dreams DB");
        String sql = "select * "
                + "from res_mgmt.tp_gthr "
                + "where tp_id = " + travelPlanId;
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that the TP ID [" + travelPlanId + "] is associate with a gathering in the Dreams DB");
        TestReporter.softAssertEquals(rs.getValue("GTHR_CD"), gatheringData.get(GATHERING_ID), "Verify that the gathering code [" + rs.getValue("GTHR_CD") + "] is that which is expected [" + gatheringData.get(GATHERING_ID) + "].");
        TestReporter.softAssertEquals(rs.getValue("GTHR_TYP_NM"), gatheringData.get(GATHERING_TYPE), "Verify that the gathering type [" + rs.getValue("GTHR_TYP_NM") + "] is that which is expected [" + gatheringData.get(GATHERING_TYPE) + "].");
        TestReporter.softAssertEquals(rs.getValue("GTHR_NM"), gatheringData.get(GATHERING_NAME), "Verify that the gathering name [" + rs.getValue("GTHR_NM") + "] is that which is expected [" + gatheringData.get(GATHERING_NAME) + "].");

        TestReporter.log("Validate gathering in Dreams DB");
        sql = "select * "
                + "from sales_tp.tp_gthr "
                + "where tp_id = " + travelPlanId;
        db = new OracleDatabase(getEnvironment(), Database.SALESTP);
        rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that the TP ID [" + travelPlanId + "] is associate with a gathering in the SALESTP DB");
        TestReporter.softAssertEquals(rs.getValue("GTHR_CD"), gatheringData.get(GATHERING_ID), "Verify that the gathering code [" + rs.getValue("GTHR_CD") + "] is that which is expected [" + gatheringData.get(GATHERING_ID) + "].");
        TestReporter.softAssertEquals(rs.getValue("GTHR_TYP_NM"), gatheringData.get(GATHERING_TYPE), "Verify that the gathering type [" + rs.getValue("GTHR_TYP_NM") + "] is that which is expected [" + gatheringData.get(GATHERING_TYPE) + "].");
        TestReporter.softAssertEquals(rs.getValue("GTHR_NM"), gatheringData.get(GATHERING_NAME), "Verify that the gathering name [" + rs.getValue("GTHR_NM") + "] is that which is expected [" + gatheringData.get(GATHERING_NAME) + "].");
        TestReporter.assertAll();
    }

    public void validateVIP(String tpId, String vipLevel) {
        TestReporter.logStep("Validate the VIP level in Dreams and the VIP indicator in RIM");
        String sql = "select a.VIP_LVL_NM, d.VIP_IN "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWN_ID = d.ASGN_OWNR_ID "
                + "left outer join rsrc_inv.RSRC_ASGN_REQ e on d.ASGN_OWNR_ID = e.ASGN_OWNR_ID "
                + "where a.tp_id =  " + tpId;
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String strVipLevel = "";
        String vipIndicator = "Y";
        switch (vipLevel) {
            case "NONE":
                strVipLevel = "0";
                vipIndicator = "N";
                break;
            case "ONE":
                strVipLevel = "1";
                break;
            case "TWO":
                strVipLevel = "2";
                break;
            case "THREE":
                strVipLevel = "3";
                break;
            default:
                strVipLevel = "4";
                break;
        }
        TestReporter.softAssertEquals(rs.getValue("VIP_LVL_NM"), strVipLevel, "Verify that the VIP level [" + rs.getValue("VIP_LVL_NM") + "] is that which is expected [" + strVipLevel + "].");
        TestReporter.softAssertEquals(rs.getValue("VIP_IN"), vipIndicator, "Verify that the VIP indicatore [" + rs.getValue("VIP_IN") + "] is that which is expected [" + vipIndicator + "].");
        TestReporter.assertAll();
    }

    public void validateComments(String tpId, Map<String, String> commentsData, String commentType, String confidential, String gsr, int numComments, String resMgmtType, String profile) {
        TestReporter.logStep("Validate [" + commentType + "] comments");
        String sql = "select * "
                + "from res_mgmt.res_mgmt_req a "
                + "where a.tp_id = " + tpId + " "
                + "and a.CMT_REQ_TYP_NM = '" + commentType + "'";

        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() == numComments, "Verify that the number of comments [" + rs.getRowCount() + "] is that which is expected [" + numComments + "].");
        do {
            TestReporter.log("Testing comment ID [" + rs.getValue("RES_MGMT_REQ_ID") + "]");
            TestReporter.softAssertEquals(rs.getValue("RES_MGMT_REQ_TYP_NM"), resMgmtType, "Verify that the res mgmt type [" + rs.getValue("RES_MGMT_REQ_TYP_NM") + "] is that which is expected [" + resMgmtType + "].");
            TestReporter.softAssertEquals(rs.getValue("CMT_REQ_TYP_NM"), commentType, "Verify that the comment type [" + rs.getValue("CMT_REQ_TYP_NM") + "] is that which is expected [" + commentType + "].");
            TestReporter.softAssertEquals(rs.getValue("RES_MGMT_PRFL_ID"), profile, "Verify that the profil ID [" + rs.getValue("RES_MGMT_PRFL_ID") + "] is that which is expected [" + profile + "].");
            TestReporter.softAssertEquals(rs.getValue("RES_MGMT_REQ_TX"), commentsData.get(COMMENT_TEXT), "Verify that the comment text [" + rs.getValue("RES_MGMT_REQ_TX") + "] is that which is expected [" + commentsData.get(COMMENT_TEXT) + "].");
            TestReporter.softAssertEquals(rs.getValue("CFDNTL_IN"), confidential, "Verify that the res mgmt type [" + rs.getValue("CFDNTL_IN") + "] is that which is expected [" + confidential + "].");
            TestReporter.softAssertEquals(rs.getValue("GSR_IN"), gsr, "Verify that the res mgmt type [" + rs.getValue("GSR_IN") + "] is that which is expected [" + gsr + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void verifyGuestMembership(Map<String, String> membershipData) {
        TestReporter.logStep("Validate guest membership");
        String sql = "select b.* "
                + "from res_mgmt.tp_pty a "
                + "join guest.TXN_IDVL_PTY_MBSHP b on b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "where a.tp_id =  " + membershipData.get("TP");

        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that the guest is associated with a membership.");

        TestReporter.softAssertEquals(rs.getValue("MBRSHP_ID"), membershipData.get(MEMBERSHIP_ID), "Verify that the [" + rs.getValue("MBRSHP_ID") + "] is that which is expected [" + membershipData.get(MEMBERSHIP_ID) + "].");
        TestReporter.softAssertEquals(rs.getValue("MBRSHP_END_DT").split(" ")[0], membershipData.get(MEMBERSHIP_EXP_DATE).split(" ")[0], "Verify that the [" + rs.getValue("MBRSHP_END_DT") + "] is that which is expected [" + membershipData.get(MEMBERSHIP_EXP_DATE) + "].");
        TestReporter.softAssertEquals(rs.getValue("PLCY_ID"), membershipData.get(MEMBERSHIP_POLICY_ID), "Verify that the [" + rs.getValue("PLCY_ID") + "] is that which is expected [" + membershipData.get(MEMBERSHIP_POLICY_ID) + "].");
        TestReporter.softAssertEquals(rs.getValue("PROD_CHAN_ID"), membershipData.get(MEMBERSHIP_PROD_CHANNEL_ID), "Verify that the [" + rs.getValue("PROD_CHAN_ID") + "] is that which is expected [" + membershipData.get(MEMBERSHIP_PROD_CHANNEL_ID) + "].");
        TestReporter.assertAll();
    }

    public void verifyTravelAgency(AccommodationBaseTest base) {
        TestReporter.logStep("Validate Travel Agency");
        Map<String, String> ta = base.getBook().getAgencyDetails();

        TestReporter.log("Validate reservation management");
        String sql = "select a.TRVL_AGCY_PTY_ID TPS_TRVL_AGCY_PTY_ID, a.TRVL_AGT_PTY_ID, c.TRVL_AGCY_PTY_ID TC_TRVL_AGCY_PTY_ID "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where a.tp_id =  " + base.getBook().getTravelPlanId() + " "
                + "and c.TC_TYP_NM = 'AccommodationComponent'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String sql2 = "select * "
                + "from guest.TXN_ORG_PTY a "
                + "where a.TXN_ORG_PTY_ID = " + rs.getValue("TPS_TRVL_AGCY_PTY_ID");
        Recordset rs2 = new Recordset(db.getResultSet(sql2));
        TestReporter.softAssertEquals(rs2.getValue("TXN_ORG_NM"), ta.get("name"), "Verify that the TA name [" + rs2.getValue("TXN_ORG_NM") + "] is that which is expected [" + ta.get("name") + "].");

        TestReporter.softAssertTrue(!rs.getValue("TPS_TRVL_AGCY_PTY_ID").equals("NULL"), "Verify that the TPS travel agency party ID [" + rs.getValue("TPS_TRVL_AGCY_PTY_ID") + "] is not null.");
        TestReporter.softAssertTrue(!rs.getValue("TRVL_AGT_PTY_ID").equals("NULL"), "Verify that the travel agent party ID [" + rs.getValue("TRVL_AGT_PTY_ID") + "] is not null.");
        TestReporter.softAssertTrue(!rs.getValue("TC_TRVL_AGCY_PTY_ID").equals("NULL"), "Verify that the TC travel agency party ID[" + rs.getValue("TC_TRVL_AGCY_PTY_ID") + "] is not null.");

        TestReporter.log("Validate folio root charge group");
        sql = "select d.TRVL_AGT_ID "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.ROOT_CHRG_GRP d on c.CHRG_GRP_ID = d.ROOT_CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL = '" + base.getBook().getTravelPlanId() + "'";
        rs = new Recordset(db.getResultSet(sql));
        if (!(isValid(base.getIsLibgoBooking()) && base.getIsLibgoBooking() == true) &&
                !(isValid(base.isWdtcBooking()) && base.isWdtcBooking() == true)) {
            sql2 = "select * "
                    + "from guest.TXN_ORG_PTY a "
                    + "where a.TXN_ORG_PTY_ID = " + rs.getValue("TRVL_AGT_ID");
            rs2 = new Recordset(db.getResultSet(sql2));
            TestReporter.softAssertEquals(rs2.getValue("TXN_ORG_NM"), ta.get("name"), "Verify that the TA name [" + rs2.getValue("TXN_ORG_NM") + "] is that which is expected [" + ta.get("name") + "].");
            TestReporter.softAssertTrue(!rs.getValue("TRVL_AGT_ID").equals("NULL"), "Verify that the travel agent ID [" + rs.getValue("TRVL_AGT_ID") + "] is not null.");
        } else {
            do {
                TestReporter.softAssertTrue(rs.getValue("TRVL_AGT_ID").equals("NULL"), "Verify that there is no travel agent associated with the group reservation");
                rs.moveNext();
            } while (rs.hasNext());
        }

        TestReporter.log("Validate folio product charge");
        sql = "SELECT e.TRVL_AGT_ID "
                + "FROM  FOLIO.EXTNL_REF a "
                + "INNER JOIN FOLIO.CHRG_GRP_EXTNL_REF b ON a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "INNER JOIN FOLIO.NODE_CHRG_GRP c ON b.CHRG_GRP_ID = c.ROOT_CHRG_GRP_ID "
                + "INNER JOIN FOLIO.CHRG d ON c.NODE_CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "INNER JOIN FOLIO.PROD_CHRG e ON d.CHRG_ID = e.CHRG_ID "
                + "WHERE a.EXTNL_SRC_NM = 'DREAMS_TP' "
                + "AND a.EXTNL_REF_VAL = '" + base.getBook().getTravelPlanId() + "'";
        rs = new Recordset(db.getResultSet(sql));
        if (!(isValid(base.getIsLibgoBooking()) && base.getIsLibgoBooking() == true) &&
                !(isValid(base.isWdtcBooking()) && base.isWdtcBooking() == true)) {
            sql2 = "select * "
                    + "from guest.TXN_ORG_PTY a "
                    + "where a.TXN_ORG_PTY_ID = " + rs.getValue("TRVL_AGT_ID");
            rs2 = new Recordset(db.getResultSet(sql2));
            TestReporter.softAssertEquals(rs2.getValue("TXN_ORG_NM"), ta.get("name"), "Verify that the TA name [" + rs2.getValue("TXN_ORG_NM") + "] is that which is expected [" + ta.get("name") + "].");
            TestReporter.softAssertTrue(!rs.getValue("TRVL_AGT_ID").equals("NULL"), "Verify that the travel agent ID [" + rs.getValue("TRVL_AGT_ID") + "] is not null.");
        } else {
            do {
                TestReporter.softAssertTrue(rs.getValue("TRVL_AGT_ID").equals("NULL"), "Verify that there is no travel agent associated with the group reservation");
                rs.moveNext();
            } while (rs.hasNext());
        }
        TestReporter.assertAll();
    }

    public void validateProfile(ReplaceAllForTravelPlanSegment book, Map<String, String> profileData) {
        String sql = "select a.RES_MGMT_REQ_ID, RES_MGMT_REQ_TYP_NM PROFILE_TYPE, CMT_REQ_TYP_NM, RES_MGMT_PRFL_ID PROFILE_ID, RES_MGMT_REQ_TX PROFILE_DESCRIPTION, CFDNTL_IN, GSR_IN, REQ_INACTV_DTS, RES_MGMT_RTE_NM "
                + "from res_mgmt_req a "
                + "left outer join res_mgmt.RES_MGMT_REQ_RTE b on a.RES_MGMT_REQ_ID = b.RES_MGMT_REQ_ID "
                + "where a.tp_id = 472292078811";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        String commentId = null;
        TestReporter.logStep("Validate profile data for comment ID [" + commentId + "]");
        do {
            if (rs.getValue("PROFILE_ID").equals(profileData.get(PROFILE_ID))) {
                commentId = rs.getValue("RES_MGMT_REQ_ID");
                TestReporter.softAssertEquals(rs.getValue("PROFILE_TYPE"), profileData.get(PROFILE_TYPE), "Verify that the profile type [" + rs.getValue("PROFILE_TYPE") + "] is that which is expected [" + profileData.get(PROFILE_TYPE) + "].");
                // TestReporter.softAssertEquals(rs.getValue("CMT_REQ_TYP_NM"), profileData.get(PROFILE), "Verify that the profile
                // ["+rs.getValue("CMT_REQ_TYP_NM")+"] is that which is expected ["+profileData.get(key)+"].");
                TestReporter.softAssertEquals(rs.getValue("PROFILE_ID"), profileData.get(PROFILE_ID), "Verify that the profile ID [" + rs.getValue("PROFILE_ID") + "] is that which is expected [" + profileData.get(PROFILE_ID) + "].");
                TestReporter.softAssertEquals(rs.getValue("PROFILE_DESCRIPTION"), profileData.get(PROFILE_DESCRIPTION), "Verify that the profile description [" + rs.getValue("PROFILE_DESCRIPTION") + "] is that which is expected [" + profileData.get(PROFILE_DESCRIPTION) + "].");
                TestReporter.softAssertEquals(rs.getValue("CFDNTL_IN"), "N", "Verify that the profile confidential indicator [" + rs.getValue("CFDNTL_IN") + "] is that which is expected [N].");
                TestReporter.softAssertEquals(rs.getValue("GSR_IN"), "N", "Verify that the profile GSR [" + rs.getValue("GSR_IN") + "] is that which is expected [N].");
                TestReporter.softAssertEquals(rs.getValue("REQ_INACTV_DTS"), "NULL", "Verify that the profile inactive DTS [" + rs.getValue("REQ_INACTV_DTS") + "] is that which is expected [NULL].");
                TestReporter.softAssertEquals(rs.getValue("RES_MGMT_RTE_NM"), profileData.get(PROFILE_ROUTINGS_NAME), "Verify that the profile routing type [" + rs.getValue("RES_MGMT_RTE_NM") + "] is that which is expected [" + profileData.get(PROFILE_ROUTINGS_NAME) + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void verifyFolioGuaranteeStatus(AccommodationBaseTest base, Map<String, String> groupDelegateSmallBalanceWriteoff, Map<String, String> guaranteeTypes, String groupPayDepositPayoff) {
        TestReporter.logStep("Verify the folio guarantee status");
        String sql = "select a.EXTNL_SRC_NM, c.CHRG_GRP_TYP_NM, c.GRP_DLGT_SML_BAL_WRTOFF_IN, d.GRP_PAY_DPST_APPLY_IN, e.GUAR_TYP_NM "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.root_chrg_grp d on c.CHRG_GRP_ID = d.ROOT_CHRG_GRP_ID "
                + "left outer join folio.node_chrg_grp e on c.CHRG_GRP_ID = e.NODE_CHRG_GRP_ID "
                + "where a.EXTNL_REF_VAL in ( "
                + "        (select to_char(a.tp_id) "
                + "        from res_mgmt.tps a "
                + "        where a.tp_id = '" + base.getBook().getTravelPlanId() + "'), "
                + "        (select to_char(a.tps_id) "
                + "        from res_mgmt.tps a "
                + "        where a.tp_id = '" + base.getBook().getTravelPlanId() + "'), "
                + "        (select to_char(b.tc_grp_nb) "
                + "        from res_mgmt.tps a "
                + "        join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "        where a.tp_id = '" + base.getBook().getTravelPlanId() + "') "
                + ")";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        do {
            if (rs.getValue("EXTNL_SRC_NM").equals(ServiceConstants.FolioExternalReference.DREAMS_TCG)) {
                TestReporter.log("Validate TCG node charge group");
                TestReporter.softAssertEquals(rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN"), groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TCG),
                        "Verify that the group delegate small balance writeoff indicator [" + rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN") + "] is that which is expected [" + groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TCG) + "].");
                TestReporter.softAssertEquals(rs.getValue("GUAR_TYP_NM"), guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TCG),
                        "Verify that the guarantee type [" + rs.getValue("GUAR_TYP_NM") + "] is that which is expected [" + guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TCG) + "].");
            } else if (rs.getValue("EXTNL_SRC_NM").equals(ServiceConstants.FolioExternalReference.DREAMS_TPS)) {
                TestReporter.log("Validate TPS node charge group");
                TestReporter.softAssertEquals(rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN"), groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TPS),
                        "Verify that the group delegate small balance writeoff indicator [" + rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN") + "] is that which is expected [" + groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TPS) + "].");
                TestReporter.softAssertEquals(rs.getValue("GUAR_TYP_NM"), guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TPS),
                        "Verify that the guarantee type [" + rs.getValue("GUAR_TYP_NM") + "] is that which is expected [" + guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TPS) + "].");
            } else {
                TestReporter.log("Validate TP node charge group");
                TestReporter.softAssertEquals(rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN"), groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TP),
                        "Verify that the group delegate small balance writeoff indicator [" + rs.getValue("GRP_DLGT_SML_BAL_WRTOFF_IN") + "] is that which is expected [" + groupDelegateSmallBalanceWriteoff.get(ServiceConstants.FolioExternalReference.DREAMS_TP) + "].");
                TestReporter.softAssertEquals(rs.getValue("GUAR_TYP_NM"), guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TP),
                        "Verify that the guarantee type [" + rs.getValue("GUAR_TYP_NM") + "] is that which is expected [" + guaranteeTypes.get(ServiceConstants.FolioExternalReference.DREAMS_TP) + "].");
                TestReporter.softAssertEquals(rs.getValue("GRP_PAY_DPST_APPLY_IN"), groupPayDepositPayoff,
                        "Verify that the group pays deposit indicator [" + rs.getValue("GRP_PAY_DPST_APPLY_IN") + "] is that which is expected [" + groupPayDepositPayoff + "].");
            }
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void verifyRIMGuaranteeStatus(AccommodationBaseTest base, String wholesaler, String guaranteed) {
        TestReporter.logStep("Verify the RIM guarantee status");
        String sql = "select d.WHSL_IN, d.GUAR_IN, d.BLK_CD "
                + "from res_mgmt.tps a "
                + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "join rsrc_inv.RSRC_ASGN_OWNR d on c.ASGN_OWN_ID = d.ASGN_OWNR_ID "
                + "where a.tp_id =  " + base.getBook().getTravelPlanId();
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        do {
            TestReporter.softAssertEquals(rs.getValue("WHSL_IN"), wholesaler, "Verify that the wholesaler indicator [" + rs.getValue("WHSL_IN") + "] is that which is expected [" + wholesaler + "].");
            TestReporter.softAssertEquals(rs.getValue("GUAR_IN"), guaranteed, "Verify that the guaranteed indicator [" + rs.getValue("GUAR_IN") + "] is that which is expected [" + guaranteed + "].");
            String blockCode;
            if (isValid(base.isWdtcBooking()) && base.isWdtcBooking()) {
                blockCode = "01825";
            } else if (isValid(base.getIsLibgoBooking()) && base.getIsLibgoBooking()) {
                blockCode = "01905";
            } else {
                blockCode = "NULL";
            }
            TestReporter.softAssertEquals(rs.getValue("BLK_CD"), blockCode, "Verify that the block code [" + rs.getValue("BLK_CD") + "] is that which is expected [" + blockCode + "].");
            rs.moveNext();
        } while (rs.hasNext());
        TestReporter.assertAll();
    }

    public void validateResortAndRoomType(String tpId, String facilityId, String roomTypeCode) {
        TestReporter.logStep("Validate facility and room type code.");
        String sql = "select c.FAC_ID, d.RSRC_INVTRY_TYP_CD "
                + "from rsrc_inv.rsrc_own_ref a "
                + "join rsrc_inv.rsrc_own b on a.RSRC_OWN_ID = b.RSRC_OWN_ID "
                + "join rsrc_inv.rsrc_asgn_ownr c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID "
                + "join rsrc_inv.rsrc_invtry_typ d on c.RSRC_INVTRY_TYP_ID = d.RSRC_INVTRY_TYP_ID "
                + "where a.EXTNL_OWN_REF_VAL = '" + tpId + "'";
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.softAssertEquals(rs.getValue("FAC_ID"), facilityId, "Verify that the facility ID [" + rs.getValue("FAC_ID") + "] is that which is expected [" + facilityId + "].");
        TestReporter.softAssertEquals(rs.getValue("RSRC_INVTRY_TYP_CD"), roomTypeCode, "Verify that the facility ID [" + rs.getValue("RSRC_INVTRY_TYP_CD") + "] is that which is expected [" + roomTypeCode + "].");
        TestReporter.assertAll();

    }
}
