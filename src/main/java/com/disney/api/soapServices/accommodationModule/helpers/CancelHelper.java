package com.disney.api.soapServices.accommodationModule.helpers;

import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointBalanceSummary;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointBalanceSummary.PointSummary;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointsActivity;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointsActivity.PointsActivityDetails;
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
import com.disney.utils.dataFactory.database.sqlStorage.DVCSalesDreams;
import com.disney.utils.dataFactory.guestFactory.DVCMember;

public class CancelHelper {
    private String environment;
    private String tpId;
    private Boolean diningAddedOn = false;
    private Boolean guestIdExpected;
    private String partyId;
    private Boolean skipServiceProductTc;
    private Boolean pointsActivityNotFound;
    private Boolean inventoryReleased;
    private Boolean recordsExpected;

    public Boolean getSkipServiceProductTc() {
        return skipServiceProductTc;
    }

    public void setSkipServiceProductTc(Boolean skipServiceProductTc) {
        this.skipServiceProductTc = skipServiceProductTc;
    }

    public Boolean getGuestIdExpected() {
        return guestIdExpected;
    }

    public void setGuestIdExpected(Boolean guestIdExpected) {
        this.guestIdExpected = guestIdExpected;
    }

    public Boolean getPointsActivityNotFound() {
        return pointsActivityNotFound;
    }

    public void setPointsActivityNotFound(Boolean pointsActivityNotFound) {
        this.pointsActivityNotFound = pointsActivityNotFound;
    }

    public Boolean getInventoryReleased() {
        return inventoryReleased;
    }

    public void setInventoryReleased(Boolean inventoryReleased) {
        this.inventoryReleased = inventoryReleased;
    }

    public Boolean getRecordsExpected() {
        return recordsExpected;
    }

    public void setRecordsExpected(Boolean recordsExpected) {
        this.recordsExpected = recordsExpected;
    }

    public CancelHelper(String environment, String tpId) {
        this.environment = environment;
        this.tpId = tpId;
    }

    public void verifyChargeGroupsCancelled() {
        TestReporter.logStep("Verify Charge Groups Cancelled");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId)));
        // //rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId)));
        // //rs.print();
        String rootChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                rootChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getChargeGroupStatusByChargeGroupIds(nodeChargeGroups + "," + rootChargeGroups)));
        // rs.print();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM", i), "Cancelled",
                    "Verify that the [" + rs.getValue("CHRG_GRP_TYP_NM", i) + "] charge group [" + rs.getValue("CHRG_GRP_ID", i) + "] status [" + rs.getValue("CHRG_GRP_STS_NM", i) + "] is [Cancelled] as expected.");
        }
        TestReporter.assertAll();
    }

    public void verifyCancellationIsFoundInResHistory(String tpsId, String tcgId, String tcId) {
        TestReporter.logStep("Verify Cancellation Is Found In Res History");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getReservationHistoryByTpId(tpId, tpsId, tcgId, tcId)));
        // rs.print();
        boolean cancelled = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Cancelled")) {
                cancelled = true;
            }
        }
        TestReporter.softAssertTrue(cancelled,
                "Verify that the reservation history shows a cencellation for TP ID [" + tpId + "], TPS ID [" + tpsId + "], TCG ID [" + tcgId + "], and TC ID [" + tcId + "].");
        TestReporter.assertAll();
    }

    public void verifyCancellationIsFoundInResHistoryLinked(String tpsId) {
        TestReporter.logStep("Verify Cancellation Is Found In Res History");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getReservationHistoryByTpsId(tpsId)));
        // rs.print();
        boolean cancelled = false;
        for (int i = 1; i <= rs.getRowCount(); i++) {
            if (rs.getValue("RES_HIST_PROC_DS", i).equals("Cancelled")) {
                cancelled = true;
                break;
            }
        }
        TestReporter.softAssertTrue(cancelled,
                "Verify that the reservation history shows a cencellation for TPS ID [" + tpsId + "].");
        TestReporter.assertAll();
    }

    public void verifyCancellationComment(Retrieve retrieve, String expectedComment) {
        boolean found = false;
        TestReporter.logStep("Verify Cancellation Comment");
        Document doc = XMLTools.makeXMLDocument(retrieve.getResponse());
        int numTps = XMLTools.getNodeList(doc, "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments").getLength();
        for (int i = 1; i <= numTps; i++) {
            // System.out.println(retrieve.getResponse());
            int numTcgs = XMLTools.getNodeList(doc, "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + String.valueOf(i) + "]/componentGroupings").getLength();
            for (int j = 1; j <= numTcgs; j++) {
                String actualComment = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/comments/commentText");
                if (actualComment.equals(expectedComment)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        TestReporter.assertTrue(found, "Verify that the cancellation comment [" + expectedComment + "] is found in the retrieve response.");
    }

    public void verifyExchangeFeeFound(Boolean feeExpected) {
        if (feeExpected) {
            TestReporter.logStep("Verify Exchange Fee is present");
        } else {
            TestReporter.logStep("Verify Exchange Fee is not present");
        }
        RetrieveFolioDetails retrieveFolioDetails = new RetrieveFolioDetails(environment);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceName", "DREAMS_TP");
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceValue", tpId);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/transactionID", "fx:removenode");

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId)));
        // rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId)));
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

    public void verifyNumberOfCharges(int numCharges) {
        TestReporter.logStep("Verify Number Of Charges");
        RetrieveFolioDetails retrieveFolioDetails = new RetrieveFolioDetails(environment);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceName", "DREAMS_TP");
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceValue", tpId);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/transactionID", "fx:removenode");

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId)));
        // rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId)));
        // rs.print();
        String rootChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                rootChargeGroups += ",";
            }
        }

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueSourceAccountingCenterIdsByChargeGroupIds(nodeChargeGroups + "," + rootChargeGroups)));
        // rs.print();

        for (int i = 1; i <= rs.getRowCount(); i++) {
            retrieveFolioDetails.setSourceAccountingCenter(rs.getValue("SRC_ACCT_CTR_ID", i));
            retrieveFolioDetails.sendRequest();

            int actualNumCharges;
            try {
                actualNumCharges = XMLTools.getNodeList(XMLTools.makeXMLDocument(retrieveFolioDetails.getResponse()), "/Envelope/Body/retrieveFolioDetailsResponse/returnParameter/folioDetails/chargeGroupsTO/chargesTOList/chargeTOList").getLength();
            } catch (XPathNotFoundException e) {
                actualNumCharges = 0;
            }
            TestReporter.softAssertEquals(actualNumCharges, numCharges, "Verify that the number of charges [" + actualNumCharges + "] is that which is expected [" + numCharges + "] for TP ID [" + tpId + "] and source accounting center [" + rs.getValue("SRC_ACCT_CTR_ID", i) + "].");
        }
        TestReporter.assertAll();
    }

    public void verifyInventoryReleased(String tcgId) {
        TestReporter.logStep("Verify RIM InventoryReleased");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = DVCSalesDreams.getReservationInfoByTpId(tpId).replace(", res_mgmt.tc_gst d", "").replace("and rownum = 1", "");
        sql = sql + "and b.tc_grp_nb = '" + tcgId + "'";
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            String id = rs.getValue("ASGN_OWN_ID", i);
            if (!id.equals("NULL")) {
                Recordset rs2 = new Recordset(db.getResultSet(DVCSalesDreams.getResourceAssignmentOwnerDetailsByAssignmentOwner(id)));
                if (getInventoryReleased() == null || getInventoryReleased() == true) {
                    TestReporter.softAssertTrue(rs2.getRowCount() == 0, "Verify that no records are returned for assignment owner ID [" + id + "] which indicates that inventory were returned back to RIM.");
                } else {
                    TestReporter.softAssertTrue(rs2.getRowCount() == 1, "Verify that records are returned for assignment owner ID [" + id + "] which indicates that inventory was not returned back to RIM.");
                }
            }
        }
        TestReporter.assertAll();
    }

    public void verifyTcStatusByTcg(String tcg, String status) {
        TestReporter.logStep("Verify TC Status By TCG");
        Database db = new OracleDatabase(environment, Database.DREAMS);

        String sql = DVCSalesDreams.getTcStatusByTcg(tcg);
        if (diningAddedOn) {
            sql += "and b.PROD_TYP_NM != 'RESERVABLE_RESOURCE_COMPONENT'";
        }

        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        for (int i = 1; i <= rs.getRowCount(); i++) {
            boolean skip = false;

            if (skipServiceProductTc != null && skipServiceProductTc == true && rs.getValue("PROD_TYP_NM", i).equals("ServiceProduct")) {
                skip = true;
            }

            if (!skip) {
                TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), status, "Verify that the status [" + rs.getValue("TRVL_STS_NM", i) + "] of TC ID [" + rs.getValue("TC_ID", i) + "] is that which is expected [" + status + "].");
            }
        }
        TestReporter.assertAll();
    }

    public void setDiningResAddedOn(boolean diningAddedOn) {
        this.diningAddedOn = diningAddedOn;
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

    public void verifyNumberOfTpPartiesByTpId(int numParties) {
        String[] partyIds = searchForPartyByTpId(DVCSalesBaseTest.removeCM(environment), tpId);
        TestReporter.softAssertEquals(partyIds.length, numParties, "Verify that the number of party ids [" + partyIds.length + "] is that which is expected [" + numParties + "].");
    }

    public void verifyChargeGroupsStatusCount(String status, int numberRecords) {
        TestReporter.logStep("Verify Charge Groups in [ " + status + " ] Status");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId)));
        // rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId)));
        // rs.print();
        String rootChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                rootChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getChargeGroupStatusByChargeGroupIds(nodeChargeGroups + "," + rootChargeGroups).replace("not in ('UnEarned', 'Earned')", "in ('" + status + "')")));
        // rs.print();
        TestReporter.softAssertEquals(rs.getRowCount(), numberRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numberRecords + "].");
        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM", i), status,
                    "Verify that the [" + rs.getValue("CHRG_GRP_TYP_NM", i) + "] charge group [" + rs.getValue("CHRG_GRP_ID", i) + "] status [" + rs.getValue("CHRG_GRP_STS_NM", i) + "] is [" + status + "] as expected.");
        }
        TestReporter.assertAll();
    }

    public void verifyNumberOfChargesByStatus(String status, int numCharges) {
        TestReporter.logStep("Verify Number Of Charges in [ " + status + " ] status");
        RetrieveFolioDetails retrieveFolioDetails = new RetrieveFolioDetails(environment);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceName", "DREAMS_TP");
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/externalRef/referenceValue", tpId);
        retrieveFolioDetails.setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioDetails/transactionID", "fx:removenode");

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId)));
        // rs.print();

        String nodeChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                nodeChargeGroups += ",";
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId)));
        // rs.print();
        String rootChargeGroups = "";
        for (int i = 1; i <= rs.getRowCount(); i++) {
            rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
            if (i != rs.getRowCount()) {
                rootChargeGroups += ",";
            }
        }

        db = new OracleDatabase(environment, Database.DREAMS);
        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueSourceAccountingCenterIdsByChargeGroupIds(nodeChargeGroups + "," + rootChargeGroups).replace("not in ('UnEarned')", "in ('" + status + "')")));
        // rs.print();

        for (int i = 1; i <= rs.getRowCount(); i++) {
            retrieveFolioDetails.setSourceAccountingCenter(rs.getValue("SRC_ACCT_CTR_ID", i));
            retrieveFolioDetails.sendRequest();

            int actualNumCharges;
            try {
                actualNumCharges = XMLTools.getNodeList(XMLTools.makeXMLDocument(retrieveFolioDetails.getResponse()), "/Envelope/Body/retrieveFolioDetailsResponse/returnParameter/folioDetails/chargeGroupsTO/chargesTOList/chargeTOList").getLength();
            } catch (XPathNotFoundException e) {
                actualNumCharges = 0;
            }
            TestReporter.softAssertEquals(actualNumCharges, numCharges, "Verify that the number of charges [" + actualNumCharges + "] is that which is expected [" + numCharges + "] for TP ID [" + tpId + "] and source accounting center [" + rs.getValue("SRC_ACCT_CTR_ID", i) + "].");
        }
        TestReporter.assertAll();
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
    public void verifyReservationInDvc(Map<String, String> dataToValidate) {
        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        String sql = "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
                + " from dvcwishes.WPMRESDTL1 b  left outer join dvcwishes.WPMRESPRC1 a on a.guid = b.guid left outer join dvcinvsys.WPIINVTRKP c on b.INVTRACKID = c.ITI  "
                + "where b.tpid = '" + dataToValidate.get("TPID") + "' and b.INVTRACKID != ''";

        Database db = new DB2Database(environment, "dvcwishes");
        Recordset rs;

        int loops = 0;
        do {
            rs = new Recordset(db.getResultSet(sql));
            Sleeper.sleep(Randomness.randomNumberBetween(7, 10) * 1000);
            loops++;
        } while (loops <= 5 && rs.getRowCount() == 0);

        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records were returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            if (getRecordsExpected() == null || getRecordsExpected() == true) {
                TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that records were returned.");
                for (int i = 1; i <= rs.getRowCount(); i++) {
                    if (rs.getValue("TCGID", i).equals(dataToValidate.get("TCGID"))) {
                        if (dataToValidate.get("STAYDATE") != null) {
                            TestReporter.softAssertEquals(rs.getValue("STAYDATE", i), dataToValidate.get("STAYDATE").replaceAll("-", ""), "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is that which is expected [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "].");
                        }
                        TestReporter.softAssertEquals(rs.getValue("TPID", i), dataToValidate.get("TPID"), "Verify that the TP ID [" + rs.getValue("TPID", i) + "] is that which is expected [" + dataToValidate.get("TPID") + "].");
                        TestReporter.softAssertEquals(rs.getValue("TPSID", i), dataToValidate.get("TPSID"), "Verify that the TPS ID [" + rs.getValue("TPSID", i) + "] is that which is expected [" + dataToValidate.get("TPSID") + "].");
                        TestReporter.softAssertEquals(rs.getValue("TCGID", i), dataToValidate.get("TCGID"), "Verify that the TCG ID [" + rs.getValue("TCGID", i) + "] is that which is expected [" + dataToValidate.get("TCGID") + "].");
                        if (dataToValidate.get("INVTRACKID") != null) {
                            TestReporter.softAssertEquals(rs.getValue("INVTRACKID", i).replaceAll("\\s", ""), dataToValidate.get("INVTRACKID").replaceAll("\\s", ""), "Verify that the inventory tracking ID [" + rs.getValue("INVTRACKID", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("INVTRACKID").replaceAll("\\s", "") + "].");
                        }
                        TestReporter.softAssertEquals(rs.getValue("RESTYPE", i), dataToValidate.get("RESTYPE"), "Verify that the reservation type [" + rs.getValue("RESTYPE", i) + "] is that which is expected [" + dataToValidate.get("RESTYPE") + "].");
                        TestReporter.softAssertEquals(rs.getValue("HOMETYPE", i), dataToValidate.get("HOMETYPE"), "Verify that the room type code [" + rs.getValue("HOMETYPE", i) + "] is that which is expected [" + dataToValidate.get("HOMETYPE") + "].");
                        if (dataToValidate.get("EXPDPTDATE") != null) {
                            TestReporter.softAssertEquals(rs.getValue("EXPDPTDATE", i), dataToValidate.get("EXPDPTDATE").replaceAll("-", ""), "Verify that the departure date [" + rs.getValue("EXPDPTDATE", i) + "] is that which is expected [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
                        }
                        // TestReporter.softAssertEquals(rs.getValue("ROOMNUMBER",
                        // i).replaceAll("\\s", ""),
                        // dataToValidate.get("ROOMNUMBER").replaceAll("\\s",
                        // ""),
                        // "Verify that the room number ["+rs.getValue("ROOMNUMBER",
                        // i).replaceAll("\\s",
                        // "")+"] is that which is expected ["+dataToValidate.get("ROOMNUMBER").replaceAll("\\s",
                        // "")+"].");
                        TestReporter.softAssertEquals(rs.getValue("SPECNEEDS", i), dataToValidate.get("SPECNEEDS"), "Verify that the special needs flag [" + rs.getValue("SPECNEEDS", i) + "] is that which is expected [" + dataToValidate.get("SPECNEEDS") + "].");
                        TestReporter.softAssertEquals(rs.getValue("MEMBERID", i), dataToValidate.get("MEMBERID"), "Verify that the DVC member ID [" + rs.getValue("MEMBERID", i) + "] is that which is expected [" + dataToValidate.get("MEMBERID") + "].");
                        TestReporter.softAssertEquals(rs.getValue("RESORTCODE", i).replaceAll("\\s", ""), dataToValidate.get("RESORTID").replaceAll("\\s", ""), "Verify that the resort code [" + rs.getValue("RESORTCODE", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("RESORTID").replaceAll("\\s", "") + "].");
                        TestReporter.softAssertEquals(rs.getValue("STATUS", i).replaceAll("\\s", ""), dataToValidate.get("STATUS").replaceAll("\\s", ""), "Verify that the reservation status [" + rs.getValue("STATUS", i).replaceAll("\\s", "") + "] is that which is expected [" + dataToValidate.get("STATUS").replaceAll("\\s", "") + "].");
                        TestReporter.log("DVC Reservation Number: " + rs.getValue("RESNUMBER", i));
                    }
                }
                TestReporter.assertAll();
            } else {
                TestReporter.assertTrue(rs.getRowCount() == 0, "Verify that no records were returned.");
            }
        }
    }

    public void verifyRoomAssociated(Map<String, String> dataToValidate, boolean roomExpected) {
        boolean roomFound = false;

        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        String sql = "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
                + "from dvcwishes.WPMRESPRC1 a, dvcwishes.WPMRESDTL1 b, dvcinvsys.WPIINVTRKP c "
                + "where a.guid = b.guid "
                + "and b.tpid = '" + dataToValidate.get("TPID") + "' "
                + "and b.INVTRACKID = c.ITI";

        Database db = new DB2Database(environment, "dvcwishes");
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        // System.out.println();
        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records wre returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            if (rs.getRowCount() == 0) {
                TestReporter.log("<b>SQL<\b>\n" + sql);
            }
            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that records were returned.");
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (rs.getValue("TCGID", i).equals(dataToValidate.get("TCGID"))) {
                    if (rs.getValue("ROOMNUMBER", i).replaceAll("\\s", "").equals(dataToValidate.get("ROOMNUMBER").replaceAll("\\s", ""))) {
                        roomFound = true;
                        break;
                    }
                }
            }
        }
        TestReporter.assertEquals(roomExpected, roomFound, "Verify that the room-found value [" + roomFound + "] is that which is expected [" + roomExpected + "]");
    }

    public void verifyPointsActivity(DVCMember member, String pointsPaid, String tpsId) {
        String activityDescription = "Cancel reservation #" + tpsId + ".";
        boolean found = false;
        Sleeper.sleep(Randomness.randomNumberBetween(3, 5) * 1000);
        TestReporter.logStep("Verify Points Activity");
        RetrievePointBalanceSummary retrievePointBalanceSummary = new RetrievePointBalanceSummary(environment, "MinimalInfo");
        retrievePointBalanceSummary.setMembershipId(member.getMemberNumber());
        retrievePointBalanceSummary.sendRequest();

        PointSummary pointSummary = retrievePointBalanceSummary.getCurrentUseYearPointSummary();

        RetrievePointsActivity retrievePointsActivity = new RetrievePointsActivity(environment, "Main");
        retrievePointsActivity.setExtRefValue(member.getMemberNumber());
        retrievePointsActivity.setStartDate(String.valueOf(Integer.parseInt(pointSummary.getUseYear()) - 1) + "-" + pointSummary.getUseMonth() + "-" + pointSummary.getUseDay());
        retrievePointsActivity.setEndDate(String.valueOf(Integer.parseInt(pointSummary.getExpriationDateYear()) + 1) + "-" + pointSummary.getExpriationDateMonth() + "-" + pointSummary.getExpriationDateDay());
        retrievePointsActivity.sendRequest();
        TestReporter.assertTrue(!retrievePointsActivity.equals("200"), "Verify that no error occurred getting points activity.");

        Map<String, PointsActivityDetails> pointsActivity = retrievePointsActivity.getAllPointsActivity();
        for (Entry<String, PointsActivityDetails> entry : pointsActivity.entrySet()) {
            if (entry.getValue().getHasActivityFlow()) {
                if (entry.getValue().getActivityDesc().contains(activityDescription)) {
                    if (entry.getValue().getAmount().equals(pointsPaid)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (getPointsActivityNotFound() == null || getPointsActivityNotFound() == false) {
            TestReporter.assertTrue(found, "Verify that the points activity was found.");
        } else {
            TestReporter.assertFalse(found, "Verify that the points activity was not found.");
        }
    }

    public void verifyPointsActivityBooked(DVCMember member, String pointsPaid, String tpsId) {
        String activityDescription = "Reservation #" + tpsId;
        boolean found = false;
        Sleeper.sleep(Randomness.randomNumberBetween(3, 5) * 1000);
        TestReporter.logStep("Verify Points Activity");
        RetrievePointBalanceSummary retrievePointBalanceSummary = new RetrievePointBalanceSummary(environment, "MinimalInfo");
        retrievePointBalanceSummary.setMembershipId(member.getMemberNumber());
        retrievePointBalanceSummary.sendRequest();

        PointSummary pointSummary = retrievePointBalanceSummary.getCurrentUseYearPointSummary();

        RetrievePointsActivity retrievePointsActivity = new RetrievePointsActivity(environment, "Main");
        retrievePointsActivity.setExtRefValue(member.getMemberNumber());
        retrievePointsActivity.setStartDate(String.valueOf(Integer.parseInt(pointSummary.getUseYear()) - 1) + "-" + pointSummary.getUseMonth() + "-" + pointSummary.getUseDay());
        retrievePointsActivity.setEndDate(String.valueOf(Integer.parseInt(pointSummary.getExpriationDateYear()) + 1) + "-" + pointSummary.getExpriationDateMonth() + "-" + pointSummary.getExpriationDateDay());
        retrievePointsActivity.sendRequest();
        TestReporter.assertTrue(!retrievePointsActivity.equals("200"), "Verify that no error occurred getting points activity.");

        Map<String, PointsActivityDetails> pointsActivity = retrievePointsActivity.getAllPointsActivity();
        for (Entry<String, PointsActivityDetails> entry : pointsActivity.entrySet()) {
            if (entry.getValue().getHasActivityFlow()) {
                if (entry.getValue().getActivityDesc().contains(activityDescription)) {
                    if (entry.getValue().getAmount().contains(pointsPaid)) {
                        found = true;
                    }
                }
            }
        }

        if (getPointsActivityNotFound() == null || getPointsActivityNotFound() == false) {
            TestReporter.assertTrue(found, "Verify that the points activity was found.");
        } else {
            TestReporter.assertFalse(found, "Verify that the points activity was not found.");
        }
    }

    /**
     * This method validates data in the DVC database and should contain the
     * following information with the keys precisely as listed.
     * Further, this method takes into account Exchange Outbound reservations
     * which are by definition multi-day reservations, so
     * more than one staydate will be listed in the records.
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
    public void verifyReservationInDvcEO(Map<String, String> dataToValidate) {
        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        String sql = "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
                + "from dvcwishes.WPMRESPRC1 a, dvcwishes.WPMRESDTL1 b, dvcinvsys.WPIINVTRKP c "
                + "where a.guid = b.guid "
                + "and b.tpid = '" + dataToValidate.get("TPID") + "' "
                + "and b.INVTRACKID = c.ITI "
                + "and b.RESTYPE = 'EO'";

        Database db = new DB2Database(environment, "dvcwishes");
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records wre returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (rs.getValue("INVTRACKID", i).replaceAll("\\s", "").equals(dataToValidate.get("INVTRACKID").replaceAll("\\s", ""))) {
                    TestReporter.softAssertTrue(Integer.parseInt(rs.getValue("STAYDATE", i)) >= Integer.parseInt(dataToValidate.get("STAYDATE").replaceAll("-", "")) &&
                            Integer.parseInt(rs.getValue("STAYDATE", i)) <= Integer.parseInt(dataToValidate.get("EXPDPTDATE").replaceAll("-", "")),
                            "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is between the expected arrival date [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "] and the expected departure date [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
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
            }
            TestReporter.assertAll();
        }
    }

    /**
     * This method validates data in the DVC database and should contain the
     * following information with the keys precisely as listed.
     * Further, this method takes into account Exchange Outbound reservations
     * which are by definition multi-day reservations, so
     * more than one staydate will be listed in the records.
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
    public void verifyReservationInDvcEI(Map<String, String> dataToValidate) {
        TestReporter.logStep("Verify Reservation Data in the DVC Database");
        String sql = "select STAYDATE,b.TPID,b.TPSID,b.TCGID,b.INVTRACKID,b.RESTYPE,b.HOMETYPE,b.EXPDPTDATE,b.ROOMNUMBER,b.SPECNEEDS,c.MEMBERID,b.RESORTCODE,STATUS "
                + "from dvcwishes.WPMRESPRC1 a, dvcwishes.WPMRESDTL1 b, dvcinvsys.WPIINVTRKP c "
                + "where a.guid = b.guid "
                + "and b.tpid = '" + dataToValidate.get("TPID") + "' "
                + "and b.INVTRACKID = c.ITI "
                + "and b.RESTYPE = 'EI' ";

        Database db = new DB2Database(environment, "dvcwishes");
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();

        if (dataToValidate.get("STAYDATE") == null) {
            TestReporter.assertEquals(rs.getRowCount(), 0, "Verify that no records wre returned from the DVC database for TP ID [" + dataToValidate.get("TPID") + "].");
        } else {
            for (int i = 1; i <= rs.getRowCount(); i++) {
                if (rs.getValue("INVTRACKID", i).replaceAll("\\s", "").equals(dataToValidate.get("INVTRACKID").replaceAll("\\s", ""))) {
                    TestReporter.softAssertTrue(Integer.parseInt(rs.getValue("STAYDATE", i)) >= Integer.parseInt(dataToValidate.get("STAYDATE").replaceAll("-", "")) &&
                            Integer.parseInt(rs.getValue("STAYDATE", i)) <= Integer.parseInt(dataToValidate.get("EXPDPTDATE").replaceAll("-", "")),
                            "Verify that the arrival date [" + rs.getValue("STAYDATE", i) + "] is between the expected arrival date [" + dataToValidate.get("STAYDATE").replaceAll("-", "") + "] and the expected departure date [" + dataToValidate.get("EXPDPTDATE").replaceAll("-", "") + "].");
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
            }
            TestReporter.assertAll();
        }
    }

    public void verifyChargeGroupsStatusCount(String status, int numberRecords, String guestId, boolean expected) {
        TestReporter.logStep("Verify Charge Groups Cancelled");
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueNodeChargeGroups(tpId).replace("select TXN_PTY_ID from res_mgmt.tp_pty f where f.tp_id = '" + tpId + "'", guestId)));
        // rs.print();

        Sleeper.sleep(Randomness.randomNumberBetween(5, 8) * 1000);
        String nodeChargeGroups = "";
        if (!expected) {
            TestReporter.assertTrue(rs.getRowCount() == numberRecords, "Verify that no node charge groups were returned for guest ID [" + guestId + "].");
        } else {
            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that node charge groups were returned for guest ID [" + guestId + "]. SQL: " + DVCSalesDreams.getUniqueNodeChargeGroups(tpId).replace("select TXN_PTY_ID from res_mgmt.tp_pty f where f.tp_id = '" + tpId + "'", guestId));
            for (int i = 1; i <= rs.getRowCount(); i++) {
                nodeChargeGroups += rs.getValue("NODE_CHRG_GRP_ID", i);
                if (i != rs.getRowCount()) {
                    nodeChargeGroups += ",";
                }
            }
        }

        rs = new Recordset(db.getResultSet(DVCSalesDreams.getUniqueRootChargeGroups(tpId).replace("select TXN_PTY_ID from res_mgmt.tp_pty f where f.tp_id = '" + tpId + "'", guestId)));
        // rs.print();
        String rootChargeGroups = "";
        if (!expected) {
            TestReporter.assertTrue(rs.getRowCount() == numberRecords, "Verify that no root charge groups were returned for guest ID [" + guestId + "].");
        } else {
            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that root charge groups were returned for guest ID [" + guestId + "]. SQL: " + DVCSalesDreams.getUniqueNodeChargeGroups(tpId).replace("select TXN_PTY_ID from res_mgmt.tp_pty f where f.tp_id = '" + tpId + "'", guestId));
            for (int i = 1; i <= rs.getRowCount(); i++) {
                rootChargeGroups += rs.getValue("ROOT_CHRG_GRP_ID", i);
                if (i != rs.getRowCount()) {
                    rootChargeGroups += ",";
                }
            }
        }

        if (expected) {
            rs = new Recordset(db.getResultSet(DVCSalesDreams.getChargeGroupStatusByChargeGroupIds(nodeChargeGroups + "," + rootChargeGroups).replace("not in ('UnEarned', 'Earned')", "in ('" + status + "')")));
            // rs.print();
            TestReporter.softAssertEquals(rs.getRowCount(), numberRecords, "Verify that the number of records [" + rs.getRowCount() + "] is that which is expected [" + numberRecords + "].");
            for (int i = 1; i <= rs.getRowCount(); i++) {
                TestReporter.softAssertEquals(rs.getValue("CHRG_GRP_STS_NM", i), status,
                        "Verify that the [" + rs.getValue("CHRG_GRP_TYP_NM", i) + "] charge group [" + rs.getValue("CHRG_GRP_ID", i) + "] status [" + rs.getValue("CHRG_GRP_STS_NM", i) + "] is [" + status + "] as expected.");
            }
            TestReporter.assertAll();
        }
    }

    public void verifyNewGuestIdByTp(String tp, String oldGuestId, String newGuestId) {
        TestReporter.logStep("Verify ODS Guest IDs For TP ID [" + tpId + "]");
        String[] partyIds = searchForPartyByTpId(DVCSalesBaseTest.removeCM(environment), tpId);

        for (int i = 0; i < partyIds.length; i++) {
            String sql = "select a.TXN_PTY_ID, b.TXN_PTY_EXTNL_REF_VAL "
                    + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                    + "where a.TXN_PTY_ID = '" + partyIds[i] + "' "
                    + "and a.TXN_PTY_ID = b.TXN_PTY_ID";

            Database db = new OracleDatabase(environment, Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));
            // rs.print();

            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that guest party IDs were returned for party ID [" + partyIds[i] + "].");
            TestReporter.softAssertTrue(rs.getValue("TXN_PTY_ID", 1).equals(newGuestId), "Verify that the party ID [" + rs.getValue("TXN_PTY_ID", 1) + "] is that which is expected [" + newGuestId + "].");
            TestReporter.softAssertFalse(rs.getValue("TXN_PTY_ID", 1).equals(oldGuestId), "Verify that the party ID [" + rs.getValue("TXN_PTY_ID", 1) + "] is not the old party ID [" + oldGuestId + "].");
            TestReporter.assertAll();
        }
        TestReporter.assertAll();
    }

    public void verifyGuestIdByTp(String tp, String oldGuestId, String newGuestId) {
        TestReporter.logStep("Verify ODS Guest IDs For TP ID [" + tpId + "]");
        String[] partyIds = searchForPartyByTpId(DVCSalesBaseTest.removeCM(environment), tpId);

        for (int i = 0; i < partyIds.length; i++) {
            String sql = "select a.TXN_PTY_ID, b.TXN_PTY_EXTNL_REF_VAL "
                    + "from res_mgmt.tp_pty a, guest.TXN_PTY_EXTNL_REF b "
                    + "where a.TXN_PTY_ID = '" + partyIds[i] + "' "
                    + "and a.TXN_PTY_ID = b.TXN_PTY_ID";

            Database db = new OracleDatabase(environment, Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));
            // rs.print();

            TestReporter.assertTrue(rs.getRowCount() > 0, "Verify that guest party IDs were returned for party ID [" + partyIds[i] + "].");
            TestReporter.softAssertTrue(rs.getValue("TXN_PTY_ID", 1).equals(newGuestId), "Verify that the party ID [" + rs.getValue("TXN_PTY_ID", 1) + "] is that which is expected [" + newGuestId + "].");
            TestReporter.assertAll();
        }
        TestReporter.assertAll();
    }

    public void verifyOdsGuestIdCreated(boolean expected) {
        setGuestIdExpected(expected);
        verifyOdsGuestIdCreated();
    }

    public void verifyOdsGuestIdCreated(boolean expected, String partyId) {
        this.partyId = partyId;
        setGuestIdExpected(expected);
        verifyOdsGuestIdCreated();
    }

    public void verifyOdsGuestIdCreated() {
        TestReporter.logStep("Verify ODS Guest IDs For TP ID [" + tpId + "]");
        String[] partyIds = null;
        if (partyId == null) {
            partyIds = searchForPartyByTpId(DVCSalesBaseTest.removeCM(environment), tpId);
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
            Recordset rs = new Recordset(db.getResultSet(sql));
            // rs.print();
            String odsGuestId = rs.getValue("TXN_PTY_EXTNL_REF_VAL", 1);

            sql = "select * "
                    + "from odsp.txn_idvl a "
                    + "where a.TXN_IDVL_ID = '" + odsGuestId + "'";

            db = new OracleDatabase(environment, Database.GOMASTER);

            int tries = 0;
            int maxTries = 5;
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

    public void verifyDinePlanCancelled(String tpId) {
        TestReporter.logStep("Verify Dining Plan Component was Cancelled");
        String sql = "select a.TRVL_STS_NM "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + tpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.prod_typ_nm = 'DDP'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            TestReporter.softAssertEquals(rs.getValue("TRVL_STS_NM", i), "Cancelled", "Verify that the dine plan was cancelled as expected.");
        }
        TestReporter.assertAll();
    }

    public void verifyCancellationFee() {
        TestReporter.logStep("Verify cancellation fee was created in Folio");
        String sql = DVCSalesDreams.getChargeInformationByTp(tpId) + " and CHRG_TYP_NM = 'Fee Charge'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Failed to find a Fee Charge for travel plan Id [ " + tpId + " ]", sql);
        }

        TestReporter.softAssertEquals(rs.getValue("RECOG_STS_NM"), "APPROVED", "Validate value for RECOG_STS_NM [ " + rs.getValue("RECOG_STS_NM") + " ] is [ APPROVED ] as expected");
        TestReporter.softAssertEquals(rs.getValue("REV_CLS_NM"), "Cancellation Fee", "Validate value for REV_CLS_NM [ " + rs.getValue("RECOG_STS_NM") + " ] is [ Cancellation Fee ] as expected");
        TestReporter.softAssertEquals(rs.getValue("CHRG_ACTV_IN"), "Y", "Validate value for RECOG_STS_NM [ " + rs.getValue("CHRG_ACTV_IN") + " ] is [ Y ] as expected");
        TestReporter.softAssertEquals(rs.getValue("CHRG_DS"), "Cancellation Fee", "Validate value for RECOG_STS_NM [ " + rs.getValue("CHRG_DS") + " ] is [ Cancellation Fee ] as expected");

        TestReporter.assertAll();
    }

    public void verifyProductReasonID(String tcId) {
        TestReporter.logStep("Verify the reason ID based on the reaosn code ");
        String sql = "select * "
                + "from TC_RSN "
                + "where TC_ID = '" + tcId + "'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.assertEquals(rs.getValue("PRDF_TC_RSN_ID"), "1", "Verify that the product reason ID: [" + rs.getValue("PRDF_TC_RSN_ID") + "] is set"
                + " to 1 the ID that corrseponds with the AIR reason code used in the cancel");

    }
}
