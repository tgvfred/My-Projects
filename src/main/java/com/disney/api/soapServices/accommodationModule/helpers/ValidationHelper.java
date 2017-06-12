package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.SkipException;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.folioModule.folioServicePort.operations.RetrieveFolioDetails;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.DB2Database;
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
        this.environment = environment;
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

    public void validateModificationBackend(int numRecords, String travelStatusName, String securityValue, String arrivalDate, String departuredate,
            String extRefType, String extRefValue, String tpId, String tpsId, String tcgId) {
        TestReporter.logStep("Validated reservation backend data after modification");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = null;

        Map<String, String> tcgs = new HashMap<>();
        tcgs.put(tcgId, tcgId);
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

    public void validateDvcEiFee(String tpId, String tpsId, String tcgId, int numRecords) {
        TestReporter.logStep("Validated charge item status for EI reservations");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getChargeItemStatusByTpTpsTcgIds(tpId, tpsId, tcgId).concat(" and c.REV_CLS_NM = 'DVC Inbound Exchange Fee'")));

        TestReporter.assertEquals(rs.getRowCount(), numRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numRecords + "].");
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
        rs.print();
        TestReporter.softAssertEquals(rs.getValue("IDVL_FST_NM", 1), hh.primaryGuest().getFirstName(), "Verify that the guest first name [" + rs.getValue("IDVL_FST_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getFirstName() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_MID_NM", 1), hh.primaryGuest().getMiddleName(), "Verify that the guest middle [" + rs.getValue("IDVL_MID_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getMiddleName() + "].");
        TestReporter.softAssertEquals(rs.getValue("IDVL_LST_NM", 1), hh.primaryGuest().getLastName(), "Verify that the guest last name [" + rs.getValue("IDVL_LST_NM", 1) + "] is that which is expected [" + hh.primaryGuest().getLastName() + "].");
        TestReporter.softAssertTrue(rs.getValue("ADDR_LN_1_TX", 1).contains(hh.primaryGuest().primaryAddress().getAddress1()), "Verify that the address line 1 [" + rs.getValue("ADDR_LN_1_TX", 1) + "] contains that which is expected [" + hh.primaryGuest().primaryAddress().getAddress1() + "].");
        TestReporter.softAssertEquals(rs.getValue("CITY_NM", 1), hh.primaryGuest().primaryAddress().getCity(), "Verify that the address city [" + rs.getValue("CITY_NM", 1) + "] is that which is expected [" + hh.primaryGuest().primaryAddress().getCity() + "].");
        String state = null;
        try {
            state = AccommodationBaseTest.getStateAbbv(hh.primaryGuest().primaryAddress().getStateAbbv());
        } catch (AutomationException e) {
        }
        if (!rs.getValue("RGN_CD", 1).equals(state)) {
            state = hh.primaryGuest().primaryAddress().getStateAbbv();
        }
        TestReporter.softAssertEquals(rs.getValue("RGN_CD", 1), state, "Verify that the state [" + rs.getValue("RGN_CD", 1) + "] is that which is expected [" + state + "].");
        TestReporter.softAssertEquals(rs.getValue("PSTL_CD", 1), hh.primaryGuest().primaryAddress().getZipCode(), "Verify that the zip code [" + rs.getValue("PSTL_CD", 1) + "] is that which is expected [" + hh.primaryGuest().primaryAddress().getZipCode() + "].");
        TestReporter.softAssertEquals(rs.getValue("TXN_PTY_EML_ADDR_TX", 1), hh.primaryGuest().primaryEmail().getEmail(), "Verify that the email address [" + rs.getValue("TXN_PTY_EML_ADDR_TX", 1) + "] is that which is expected [" + hh.primaryGuest().primaryEmail().getEmail() + "].");
        TestReporter.softAssertEquals(rs.getValue("PHN_NB", 1), hh.primaryGuest().primaryPhone().getNumber(), "Verify that the phone number [" + rs.getValue("PHN_NB", 1) + "] is that which is expected [" + hh.primaryGuest().primaryPhone().getNumber() + "].");
        TestReporter.assertAll();
        return rs;
    }

    public void validateGuestInformation(String tpId, HouseHold hh, String membershipTypeName, String membershipRefId, String tpPtyId) {
        TestReporter.logStep("Validated guest information");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getTpPartyGuestInfoByTpId(tpId)
                .replace(" = e.TXN_PTY_ADDR_LCTR_ID", " = e.TXN_PTY_ADDR_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")
                .replace(" = f.TXN_PTY_EML_LCTR_ID", " = f.TXN_PTY_EML_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")
                .replace(" = g.TXN_PTY_PHN_LCTR_ID", " = g.TXN_PTY_PHN_LCTR_ID and b.TXN_IDVL_PTY_ID = " + tpPtyId + " ")));
        // +"and b.idvl_lst_nm = '"+hh.primaryGuest().getLastName()+"' "
        // +
        // "and e.ADDR_LN_1_TX = '"+hh.primaryGuest().primaryAddress().getAddress1()+"' "
        // +
        // "and f.TXN_PTY_EML_ADDR_TX = '"+hh.primaryGuest().primaryEmail().getEmail()+"' "
        // +
        // "and g.PHN_NB = '"+hh.primaryGuest().primaryPhone().getNumber()+"'"));
        // rs.print();
        // IDVL_FST_NM|IDVL_MID_NM|IDVL_LST_NM|DVC_MBR_TYP_NM|MBRSHP_ID
        // |ADDR_LN_1_TX |CITY_NM |RGN_CD|PSTL_CD|TXN_PTY_EML_ADDR_TX |PHN_NB |
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

    public void verifyDinePlanAdded(String tpsId) {
        TestReporter.logStep("Verify That a Dine Plan Was Added");
        String sql = "select a.tc_grp_nb, a.TC_GRP_TYP_NM, b.PROD_TYP_NM "
                + "from res_mgmt.tc_grp a, res_mgmt.tc b "
                + "where a.tps_id = '" + tpsId + "' "
                + "and a.tc_grp_nb = b.tc_grp_nb";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        Boolean tcgAddOnFound = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("TC_GRP_TYP_NM", i).equals("ADD_ON_PACKAGE")) {
                tcgAddOnFound = true;
                break;
            }
        }
        TestReporter.assertTrue(tcgAddOnFound, "Verify that a TCG add-on package was found for TPS ID [" + tpsId + "].");

        rs.moveFirst();
        Boolean tcDisneyDineProductFound = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("PROD_TYP_NM", i).equals("DDP") || rs.getValue("PROD_TYP_NM", i).equals("DLX")) {
                tcDisneyDineProductFound = true;
                break;
            }
        }
        TestReporter.assertTrue(tcDisneyDineProductFound, "Verify that a TC disney dine product was found for TPS ID [" + tpsId + "].");
    }

    public void verifyDinePlanRemoved(String tpsId) {
        TestReporter.logStep("Verify That a Dine Plan Was Removed");
        String sql = "select b.tc_grp_nb "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b "
                + "where a.tp_id = " + tpsId + " "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        TestReporter.assertTrue(rs.getRowCount() == 0, "Verify that no TC disney dine product was found for TPS ID [" + tpsId + "].");
    }

    public void verifyDinePlanTcgAssociatedWithParentTcg(String tps, String tcg) {
        String sql1 = "select a.TC_GRP_NB "
                + "from res_mgmt.tc_grp a "
                + "where a.tps_id = '" + tps + "' "
                + "and a.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";
        String sql2 = "select a.ADD_ON_TC_GRP_NB "
                + "from res_mgmt.tc_grp a "
                + "where a.TC_GRP_NB = '" + tcg + "'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql1));
        String dineTcg = rs.getValue("TC_GRP_NB", 1);

        rs = new Recordset(db.getResultSet(sql2));
        String addOnTcg = rs.getValue("ADD_ON_TC_GRP_NB", 1);

        TestReporter.softAssertEquals(dineTcg, addOnTcg, "Verify that the add-on TCG [" + addOnTcg + "] is that which is expected [" + dineTcg + "].");
    }

    public void verifyDinePlanNotAdded(String tpsId) {
        throw new SkipException("Need to add validation for dine plan being removed.");
        // TestReporter.logStep("Verify That a Dine Plan Was Added");
        // String sql = "select a.tc_grp_nb, a.TC_GRP_TYP_NM, b.PROD_TYP_NM "
        // + "from res_mgmt.tc_grp a, res_mgmt.tc b "
        // + "where a.tps_id = '"+tpsId+"' "
        // + "and a.tc_grp_nb = b.tc_grp_nb";
        //
        // Database db = new OracleDatabase(environment, Database.DREAMS);
        // Recordset rs = new Recordset(db.getResultSet(sql));
        // //rs.print();
        //
        // Boolean tcgAddOnFound = false;
        // for(int i = 1; i <= rs.getRowCount(); i++){
        // if(rs.getValue("TC_GRP_TYP_NM", i).equals("ADD_ON_PACKAGE")){
        // tcgAddOnFound = true;
        // break;
        // }
        // }
        // TestReporter.assertTrue(tcgAddOnFound,
        // "Verify that a TCG add-on package was found for TPS ID ["+tpsId+"].");
        //
        // rs.moveFirst();
        // Boolean tcDisneyDineProductFound = false;
        // for(int i = 1; i <= rs.getRowCount(); i++){
        // if(rs.getValue("PROD_TYP_NM", i).equals("DDP")){
        // tcDisneyDineProductFound = true;
        // break;
        // }
        // }
        // TestReporter.assertTrue(tcDisneyDineProductFound,
        // "Verify that a TC disney dine product was found for TPS ID ["+tpsId+"].");
    }

    public void verifyDinePlanTcgNotAssociatedWithParentTcg(String tps, String tcg) {
        throw new SkipException("Need to add validation for dine plan being removed.");
        // String sql1 = "select a.TC_GRP_NB "
        // + "from res_mgmt.tc_grp a "
        // + "where a.tps_id = '"+tps+"' "
        // + "and a.tc_grp_typ_nm = 'ADD_ON_PACKAGE'";
        // String sql2 = "select a.ADD_ON_TC_GRP_NB "
        // + "from res_mgmt.tc_grp a "
        // + "where a.TC_GRP_NB = '"+tcg+"'";
        //
        // Database db = new OracleDatabase(environment, Database.DREAMS);
        // Recordset rs = new Recordset(db.getResultSet(sql1));
        // String dineTcg = rs.getValue("TC_GRP_NB", 1);
        //
        // rs = new Recordset(db.getResultSet(sql2));
        // String addOnTcg = rs.getValue("ADD_ON_TC_GRP_NB", 1);
        //
        // TestReporter.softAssertEquals(dineTcg, addOnTcg,
        // "Verify that the add-on TCG ["+addOnTcg+"] is that which is expected ["+dineTcg+"].");
    }

    public void verifyTcStatusByTcg(String tcg, String status) {
        TestReporter.logStep("Verify TC Status By TCG");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        String sql = Dreams_AccommodationQueries.getTcStatusByTcg(tcg);
        if (isDiningAddedOn() != null && isDiningAddedOn() == true) {
            sql += "and b.PROD_TYP_NM != 'RESERVABLE_RESOURCE_COMPONENT'";
        }
        if (status.equals("Checking In")) {
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
        TestReporter.logStep("Verify Booking Is Found In Res History");
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

    public void verifyExchangeFeeFound(Boolean feeExpected, String tpId) {
        TestReporter.logStep("Verify Number Of Charges");
        RetrieveFolioDetails retrieveFolioDetails = new RetrieveFolioDetails(environment);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceName", "DREAMS_TP");
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceValue", tpId);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/transactionID", "fx:removenode");

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getUniqueNodeChargeGroups(tpId)));
        // rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(Dreams_AccommodationQueries.getUniqueRootChargeGroups(tpId)));
        // rs.print();
        String rootChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                rootChargeGroups += ",";
            }
        }

        String sql = "select e.* "
                + "from folio.chrg e where e.CHRG_GRP_ID in (" + nodeChargeGroups + "," + rootChargeGroups + ") "
                + "and e.chrg_ds = 'Disney Collection Exchange Fee'";

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        if (feeExpected) {
            TestReporter.softAssertTrue(rs.getRowCount() > 0, "Verify that an exhange fee was found as expected.");
        } else {
            TestReporter.softAssertTrue(rs.getRowCount() == 0, "Verify that no exhange fee was found as expected.");
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

        sql = "select b.CHRG_GRP_ID, c.CHRG_GRP_STS_NM, c.CHRG_GRP_TYP_NM, d.CHRG_ID "
                + "from folio.extnl_ref a, folio.chrg_grp_extnl_ref b, folio.chrg_grp c, folio.chrg d "
                + "where a.EXTNL_REF_VAL in (" + ids + ") "
                + "and a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "and b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "and c.CHRG_GRP_ID = d.CHRG_GRP_ID "
                + "and c.CHRG_GRP_STS_NM = '" + status + "'";
        rs = new Recordset(db.getResultSet(sql));
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

    /**
     * This method validates data in the DVC database and should contain the
     * following information with the keys precisely as listed:
     * STAYDATE - arrival date
     * TPID - travel plan id
     * TPSID - travel plan segment id
     * TCGID - travel component grouping id
     * INVTRACKID - inventory tracking id
     * RESTYPE - reservation type (M$, MP, etc.))
     * HOMETYPE - room type code
     * EXPDPTDATE - departure date
     * ROOMNUMBER - room number (empty if no room assigned)
     * SPECNEEDS - special needs flag (Y or N)
     * MEMBERID - DVC member id
     * RESORTID - resort code (BLT, AKV, etc.)
     * STATUS - reservation status (Booked, Cancelled, etc.)
     *
     * @param dataToValidate
     *            - map of data points to validate
     */
    public void verifyReservationInDvc(Map<String, String> dataToValidate, int expectedRecords, Boolean multiNights) {
        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        String sql = "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,c.ITI,c.EXIST_ITI,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
                + "from dvcwishes.WPMRESPRCP a, dvcwishes.WPMRESDTLP b, dvcinvsys.WPIINVTRKP c "
                + "where a.guid = b.guid "
                + "and b.tpid = '" + dataToValidate.get("TPID") + "' "
                + "and c.EXIST_ITI != '' "
                + "and b.INVTRACKID = c.ITI";

        Database db = new DB2Database(environment, "dvcwishes");
        int tries = 0;
        int maxTries = 60;
        Boolean success = false;
        Recordset rs = null;
        do {
            Sleeper.sleep(1000);
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == expectedRecords) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);

        // rs.print();
        if (rs.getRowCount() != expectedRecords) {
            TestReporter.log("SQL:" + sql);
        }
        TestReporter.softAssertEquals(rs.getRowCount(), expectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + expectedRecords + "].");
        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records wre returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (multiNights == null || multiNights == false) {
                    TestReporter.softAssertEquals(rs.getValue("STAYDATE", i), dataToValidate.get("STAYDATE").replaceAll("-", ""), "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is that which is expected [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "].");
                } else {
                    TestReporter.softAssertTrue(Long.parseLong(rs.getValue("STAYDATE", i)) >= Long.parseLong(dataToValidate.get("STAYDATE").replaceAll("-", "")) && Long.parseLong(rs.getValue("STAYDATE", i)) <= Long.parseLong(dataToValidate.get("EXPDPTDATE").replaceAll("-", "")),
                            "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is between the expected arrival date [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "] and the expected departure date [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
                }
                TestReporter.softAssertEquals(rs.getValue("TPID", i), dataToValidate.get("TPID"), "Verify that the TP ID [" + rs.getValue("TPID", i) + "] is that which is expected [" + dataToValidate.get("TPID") + "].");
                TestReporter.softAssertEquals(rs.getValue("TPSID", i), dataToValidate.get("TPSID"), "Verify that the TPS ID [" + rs.getValue("TPSID", i) + "] is that which is expected [" + dataToValidate.get("TPSID") + "].");
                TestReporter.softAssertEquals(rs.getValue("TCGID", i), dataToValidate.get("TCGID"), "Verify that the TCG ID [" + rs.getValue("TCGID", i) + "] is that which is expected [" + dataToValidate.get("TCGID") + "].");
                TestReporter.softAssertEquals(rs.getValue("INVTRACKID", i).replaceAll("\\s", ""), dataToValidate.get("INVTRACKID").replaceAll("\\s", ""), "Verify that the inventory tracking ID [" + rs.getValue("INVTRACKID", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("INVTRACKID").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("RESTYPE", i), dataToValidate.get("RESTYPE"), "Verify that the reservation type [" + rs.getValue("RESTYPE", i) + "] is that which is expected [" + dataToValidate.get("RESTYPE") + "].");
                TestReporter.softAssertEquals(rs.getValue("HOMETYPE", i), dataToValidate.get("HOMETYPE"), "Verify that the room type code [" + rs.getValue("HOMETYPE", i) + "] is that which is expected [" + dataToValidate.get("HOMETYPE") + "].");
                TestReporter.softAssertEquals(rs.getValue("EXPDPTDATE", i), dataToValidate.get("EXPDPTDATE").replaceAll("-", ""), "Verify that the departure date [" + rs.getValue("EXPDPTDATE", i) + "] is that which is expected [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("ROOMNUMBER", i).replaceAll("\\s", ""), dataToValidate.get("ROOMNUMBER").replaceAll("\\s", ""), "Verify that the room number [" + rs.getValue("ROOMNUMBER", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("ROOMNUMBER").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("SPECNEEDS", i), dataToValidate.get("SPECNEEDS"), "Verify that the special needs flag [" + rs.getValue("SPECNEEDS", i) + "] is that which is expected [" + dataToValidate.get("SPECNEEDS") + "].");
                TestReporter.softAssertEquals(rs.getValue("MEMBERID", i), dataToValidate.get("MEMBERID"), "Verify that the DVC member ID [" + rs.getValue("MEMBERID", i) + "] is that which is expected [" + dataToValidate.get("MEMBERID") + "].");
                TestReporter.softAssertEquals(rs.getValue("RESORTCODE", i).replaceAll("\\s", ""), dataToValidate.get("RESORTID").replaceAll("\\s", ""), "Verify that the resort code [" + rs.getValue("RESORTCODE", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("RESORTID").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("STATUS", i).replaceAll("\\s", ""), dataToValidate.get("STATUS").replaceAll("\\s", ""), "Verify that the reservation status [" + rs.getValue("STATUS", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("STATUS").replaceAll("\\s", "") + "].");
                TestReporter.log("DVC Reservation Number: " + rs.getValue("RESNUMBER", i));
            }
            TestReporter.assertAll();
        }
    }

    /**
     * This method validates data in the DVC database and should contain the
     * following information with the keys precisely as listed:
     * STAYDATE - arrival date
     * TPID - travel plan id
     * TPSID - travel plan segment id
     * TCGID - travel component grouping id
     * INVTRACKID - inventory tracking id
     * RESTYPE - reservation type (M$, MP, etc.))
     * HOMETYPE - room type code
     * EXPDPTDATE - departure date
     * ROOMNUMBER - room number (empty if no room assigned)
     * SPECNEEDS - special needs flag (Y or N)
     * MEMBERID - DVC member id
     * RESORTID - resort code (BLT, AKV, etc.)
     * STATUS - reservation status (Booked, Cancelled, etc.)
     *
     * @param dataToValidate
     *            - map of data points to validate
     */
    public void verifyReservationInDvc_EI(Map<String, String> dataToValidate, int expectedRecords, Boolean multiNights, Boolean debug) {
        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        // String sql =
        // "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,c.ITI,c.EXIST_ITI,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
        // +
        // "from dvcwishes.WPMRESPRCP a, dvcwishes.WPMRESDTLP b, dvcinvsys.WPIINVTRKP c "
        // + "where a.guid = b.guid "
        // + "and b.tpid = '"+dataToValidate.get("TPID")+"' "
        // + "and c.EXIST_ITI != '' "
        // + "and b.INVTRACKID = c.ITI";
        String sql = "select a.STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,c.STATUS "
                + "from dvcwishes.WPMRESPRCP a, dvcwishes.WPMRESDTLP b, dvcinvsys.WPIINVTRKP c "
                + "where a.guid = b.guid "
                + "and b.tpid = '" + dataToValidate.get("TPID") + "' "
                + "and c.EXIST_ITI != '' "
                + "and b.RESTYPE = '" + dataToValidate.get("RESTYPE") + "' "
                + "and c.MEMBERID = '" + dataToValidate.get("MEMBERID") + "' "
                + "and c.STATUS = 'BOOKED'"
                + "order by a.STAYDATE asc";

        Database db = new DB2Database(environment, "dvcwishes");
        int tries = 0;
        int maxTries = 60;
        Boolean success = false;
        Recordset rs = null;
        do {
            if (debug == null) {
                Sleeper.sleep(1000);
            }
            rs = new Recordset(db.getResultSet(sql));
            if (rs.getRowCount() == expectedRecords) {
                success = true;
            }
            tries++;
        } while (tries < maxTries && !success);
        // rs.print();
        TestReporter.softAssertEquals(rs.getRowCount(), expectedRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + expectedRecords + "].");
        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records wre returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (multiNights == null || multiNights == false) {
                    TestReporter.softAssertEquals(rs.getValue("STAYDATE", i), dataToValidate.get("STAYDATE").replaceAll("-", ""), "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is that which is expected [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "].");
                } else {
                    TestReporter.softAssertTrue(Long.parseLong(rs.getValue("STAYDATE", i)) >= Long.parseLong(dataToValidate.get("STAYDATE").replaceAll("-", "")) && Long.parseLong(rs.getValue("STAYDATE", i)) <= Long.parseLong(dataToValidate.get("EXPDPTDATE").replaceAll("-", "")),
                            "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is between the expected arrival date [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "] and the expected departure date [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
                }
                TestReporter.softAssertEquals(rs.getValue("TPID", i), dataToValidate.get("TPID"), "Verify that the TP ID [" + rs.getValue("TPID", i) + "] is that which is expected [" + dataToValidate.get("TPID") + "].");
                TestReporter.softAssertEquals(rs.getValue("TPSID", i), dataToValidate.get("TPSID"), "Verify that the TPS ID [" + rs.getValue("TPSID", i) + "] is that which is expected [" + dataToValidate.get("TPSID") + "].");
                TestReporter.softAssertEquals(rs.getValue("TCGID", i), dataToValidate.get("TCGID"), "Verify that the TCG ID [" + rs.getValue("TCGID", i) + "] is that which is expected [" + dataToValidate.get("TCGID") + "].");
                TestReporter.softAssertEquals(rs.getValue("INVTRACKID", i).replaceAll("\\s", ""), dataToValidate.get("INVTRACKID").replaceAll("\\s", ""), "Verify that the inventory tracking ID [" + rs.getValue("INVTRACKID", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("INVTRACKID").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("RESTYPE", i), dataToValidate.get("RESTYPE"), "Verify that the reservation type [" + rs.getValue("RESTYPE", i) + "] is that which is expected [" + dataToValidate.get("RESTYPE") + "].");
                TestReporter.softAssertEquals(rs.getValue("HOMETYPE", i), dataToValidate.get("HOMETYPE"), "Verify that the room type code [" + rs.getValue("HOMETYPE", i) + "] is that which is expected [" + dataToValidate.get("HOMETYPE") + "].");
                TestReporter.softAssertEquals(rs.getValue("EXPDPTDATE", i), dataToValidate.get("EXPDPTDATE").replaceAll("-", ""), "Verify that the departure date [" + rs.getValue("EXPDPTDATE", i) + "] is that which is expected [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("ROOMNUMBER", i).replaceAll("\\s", ""), dataToValidate.get("ROOMNUMBER").replaceAll("\\s", ""), "Verify that the room number [" + rs.getValue("ROOMNUMBER", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("ROOMNUMBER").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("SPECNEEDS", i), dataToValidate.get("SPECNEEDS"), "Verify that the special needs flag [" + rs.getValue("SPECNEEDS", i) + "] is that which is expected [" + dataToValidate.get("SPECNEEDS") + "].");
                TestReporter.softAssertEquals(rs.getValue("MEMBERID", i), dataToValidate.get("MEMBERID"), "Verify that the DVC member ID [" + rs.getValue("MEMBERID", i) + "] is that which is expected [" + dataToValidate.get("MEMBERID") + "].");
                TestReporter.softAssertEquals(rs.getValue("RESORTCODE", i).replaceAll("\\s", ""), dataToValidate.get("RESORTID").replaceAll("\\s", ""), "Verify that the resort code [" + rs.getValue("RESORTCODE", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("RESORTID").replaceAll("\\s", "") + "].");
                TestReporter.softAssertEquals(rs.getValue("STATUS", i).replaceAll("\\s", ""), dataToValidate.get("STATUS").replaceAll("\\s", ""), "Verify that the reservation status [" + rs.getValue("STATUS", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("STATUS").replaceAll("\\s", "") + "].");
                TestReporter.log("DVC Reservation Number: " + rs.getValue("RESNUMBER", i));
            }
            TestReporter.assertAll();
        }
    }

    public void verifyDinePlanComponentStatus(String tpId, String status) {
        TestReporter.logStep("Verify Dine Components are Cancelled");
        String sql = "select c.TC_ID, c.TRVL_STS_NM "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = " + tpId + " "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_typ_nm = 'ADD_ON_PACKAGE' "
                + "and b.tc_grp_nb = c.tc_grp_nb";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), status, "Verify that the TC ID [" + rs.getValue("TC_ID", i) + "] status [" + status + "] is that which is expected [Cancelled].");
        }
        TestReporter.assertAll();
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
        // System.out.println();
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
        // System.out.println();
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
        valueFound.put(guest.primaryAddress().getCity(), false);
        valueFound.put(guest.primaryAddress().getAddress1(), false);
        valueFound.put(guest.primaryAddress().getZipCode(), false);
        String state = null;
        try {
            state = AccommodationBaseTest.getStateAbbv(guest.primaryAddress().getStateAbbv());
        } catch (AutomationException e) {
            state = guest.primaryAddress().getStateAbbv();
        }
        for (int i = 1; i <= rs.getRowCount(); i++) {
            // System.out.println();
            if (rs.getValue("CNTRY_ID", i).toLowerCase().contains(guest.primaryAddress().getCountryAbbv().toLowerCase())) {
                valueFound.put(guest.primaryAddress().getCountryAbbv(), true);
            }
            if (rs.getValue("ST_NM", i).equalsIgnoreCase(state)) {
                valueFound.put(guest.primaryAddress().getStateAbbv(), true);
            }
            if (rs.getValue("CTY_NM",
                    i).equalsIgnoreCase(guest.primaryAddress().getCity())) {
                valueFound.put(guest.primaryAddress().getCity(), true);
            }
            if (rs.getValue("ADDR_RAW_ADDR_VL", i).toLowerCase().replace("road", "rd")
                    .contains(guest.primaryAddress().getAddress1().toLowerCase().replace("road", "rd"))) {
                valueFound.put(guest.primaryAddress().getAddress1(), true);
            }
            if (guest.primaryAddress().getZipCode().contains(rs.getValue("PSTL_CD",
                    i))) {
                valueFound.put(guest.primaryAddress().getZipCode(), true);
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
            if (rs.getValue("PHN_RAW_PHN_NB", i).replace(")", "").replace("(", "").replace("-", "").equals(guest.primaryPhone().getNumber())) {
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
                if (rs.getValue("ADLT_CN", 1).equals(adultCount)) {
                    success = true;
                }
                tries++;
            } while (tries < maxTries && !success);
            TestReporter.softAssertEquals(rs.getValue("ADLT_CN", 1), adultCount, "Verify that the adult count [" + rs.getValue("ADLT_CN", 1) + "] is that which is expected [" + adultCount + "].");
            TestReporter.softAssertEquals(rs.getValue("CHLD_CN", 1), childCount, "Verify that the child count [" + rs.getValue("CHLD_CN", 1) + "] is that which is expected [" + childCount + "].");
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

    public void verifyDinePlanCharges(String tpId, int records) {
        TestReporter.logStep("Verify the number of Dine Plan charges");
        String sql = "select distinct e.* "
                + "from FOLIO.CHRG_GRP_FOLIO a "
                + "left outer join FOLIO.FOLIO b on b.FOLIO_ID= a.CHRG_GRP_FOLIO_ID "
                + "left outer join FOLIO.FOLIO_ITEM c on c.FOLIO_ID= b.FOLIO_ID "
                + "left outer join FOLIO.CHRG_ITEM d on d.CHRG_ITEM_ID = c.FOLIO_ITEM_ID "
                + "left outer join FOLIO.CHRG e on e.CHRG_ID = d.CHRG_ID "
                + "left outer join FOLIO.PMT f on f.FOLIO_ITEM_ID = c.FOLIO_ITEM_ID "
                + "left outer join FOLIO.CHRG_GRP g on g.CHRG_GRP_ID = a.ROOT_CHRG_GRP_ID "
                + "left outer join FOLIO.CHRG_GRP_EXTNL_REF h on h.CHRG_GRP_ID=g.CHRG_GRP_ID "
                + "left outer join FOLIO.EXTNL_REF i on i.EXTNL_REF_ID=h.EXTNL_REF_ID "
                + "left outer join FOLIO.prod_chrg t on t.chrg_id=d.chrg_id "
                + "left outer join FOLIO.chrg_mkt_pkg u on u.chrg_mkt_pkg_id=t.chrg_mkt_pkg_Id "
                + "where i.EXTNL_REF_VAL ='" + tpId + "' "
                + "and e.chrg_id is not null "
                + "and e.REV_CLS_NM != 'Accommodation'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        TestReporter.assertEquals(rs.getRowCount(), records, "Verify that the number of dine plan records [" + rs.getRowCount() + "] is that which is expected [" + records + "].");
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
}
