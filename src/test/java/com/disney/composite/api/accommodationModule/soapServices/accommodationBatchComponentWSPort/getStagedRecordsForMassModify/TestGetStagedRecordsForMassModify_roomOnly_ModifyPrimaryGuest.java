// package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.getStagedRecordsForMassModify;
//
// import org.testng.annotations.Test;
//
// import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.GetStagedRecordsForMassModify;
// import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageModifyData;
// import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
// import com.disney.api.soapServices.accommodationModule.helpers.GetStagedRecordsForMassModifyHelper;
// import com.disney.utils.Environment;
// import com.disney.utils.TestReporter;
// import com.disney.utils.dataFactory.database.Database;
// import com.disney.utils.dataFactory.database.Recordset;
// import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
//
// public class TestGetStagedRecordsForMassModify_roomOnly_ModifyPrimaryGuest extends AccommodationBaseTest {
// private String assignOwner = null;
//
// @Test(groups = { "api", "regression", "getStagedRecordsForMassModify", "accommodation" })
// public void testGetStagedRecordsForMassModify_roomOnly_ModifyPrimaryGuest() {
// String sql = "select a.TRVL_AGCY_PTY_ID, a.TRVL_AGT_PTY_ID, a.TPS_SECUR_VL, a.TPS_GUAR_IN, a.TPS_ARVL_DT, a.TPS_DPRT_DT, c.TC_STRT_DTS, c.TC_END_DTS, c.ASGN_OWN_ID "
// + "from res_mgmt.tps a "
// + "join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
// + "join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
// + "where a.tps_id = " + getBook().getTravelPlanSegmentId();
// Database db = new OracleDatabase(environment, Database.DREAMS);
// Recordset rs = new Recordset(db.getResultSet(sql));
// do {
// if (!rs.getValue("ASGN_OWN_ID").equals("NULL")) {
// assignOwner = rs.getValue("ASGN_OWN_ID");
// }
// rs.moveNext();
// } while (rs.hasNext());
//
// String tcId = getBook().getTravelComponentId();
// String tpsId = getBook().getTravelPlanSegmentId();
// String tcgId = getBook().getTravelComponentGroupingId();
// String firstName = getHouseHold().primaryGuest().getFirstName();
// String lastName = getHouseHold().primaryGuest().getLastName();
//
// StageModifyData stage = new StageModifyData(environment, "MainProcLst");
// stage.setMassModifyRoomDetailTcId(tcId);
// stage.setMassModifyRoomDetailTpsId(tpsId);
// stage.setMassModifyRoomDetailTcgID(tcgId);
// stage.setMassModifyRoomDetailPrimaryGuestDetailFirstName(firstName);
// stage.setMassModifyRoomDetailPrimaryGuestDetailLastName(lastName);
// stage.sendRequest();
//
// TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Error sending request: " + stage.getFaultString(), stage);
//
// sql = "select a.GRP_RES_PROC_RUN_ID " +
// " from res_mgmt.GRP_RES_PROC_RUN a " +
// " where a. GRP_RES_PROC_ID = " + stage.getResponseProcessId() + " ";
//
// db = new OracleDatabase(environment, Database.DREAMS);
// rs = new Recordset(db.getResultSet(sql));
//
// TestReporter.logStep("Retrieve staged record for Mass Modify");
// GetStagedRecordsForMassModify mod = new GetStagedRecordsForMassModify(environment);
// mod.setProcessDataId(rs.getValue("GRP_RES_PROC_RUN_ID"));
// mod.sendRequest();
//
// TestReporter.logAPI(!mod.getResponseStatusCode().equals("200"), "Error sending request: " + mod.getFaultString(), mod);
//
// TestReporter.softAssertEquals(mod.getFirstName(), firstName, "Verify that the retrieved firstName [" + mod.getFirstName() + "] matches the expected [" + firstName + "]");
// TestReporter.softAssertEquals(mod.getLastName(), lastName, "Verify that the retrieved lastName [" + mod.getLastName() + "] matches the expected [" + lastName + "]");
// TestReporter.assertAll();
// }
//
// private void validations() {
// GetStagedRecordsForMassModifyHelper helper = new GetStagedRecordsForMassModifyHelper(Environment.getBaseEnvironmentName(getEnvironment()), this);
// helper.validateNumberOfTcs(1);
// helper.validateReservationDetails("NULL", "NULL", "NULL", "N", getArrivalDate(), getDepartureDate(), assignOwner, false);
// }
// }
