package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchWSPort.retrieveAvailableFields;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.RetrieveAvailableFields;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveAvailableFields extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "Accommodation", "AccommodationBatchService", "retrieveAvailableFields" })
    public void testRetrieveAvailableFields() {

        RetrieveAvailableFields retrieve = new RetrieveAvailableFields(environment);
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "Error sending request", retrieve);

        String sql = "select a.FLD_ID, a.FLD_DATA_TYP_NM, a.FLD_NM, a.REPEAT_FLD_IN, a.DFLT_FLD_IN " +
                " from res_mgmt.data_entry_fld a";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        // Verify the number of fieldTOs in the response is the same as the query recordset
        TestReporter.logStep("Verify the number of fieldTOs in the response is the same as the query recordset");
        String xpath = "/Envelope/Body/retrieveAvaillableFieldsResponse/fieldTOs";
        int numOfFieldTOs = retrieve.getNumberOfResponseNodesByXPath(xpath);

        TestReporter.assertEquals(numOfFieldTOs, rs.getRowCount(), "Verify that the number of fieldTOs [" + numOfFieldTOs + "] matches the number of rows in the database [" + rs.getRowCount() + "]");

        // For each fieldTOs, validate fieldId, isRepeatable, defaultFieldIndicator, fieldName, fieldDataType
        TestReporter.logStep("For each fieldTOs, validate fieldId, isRepeatable, defaultFieldIndicator, fieldName, fieldDataType");
        for (int i = 1; i <= numOfFieldTOs; i++) {
            String iterator = "[" + i + "]/";
            String isRepeatable = retrieve.getResponseNodeValueByXPath(xpath + iterator + "defaultFieldIndicator").equals("true") ? "Y" : "N";
            String defaultFieldIndicator = retrieve.getResponseNodeValueByXPath(xpath + iterator + "defaultFieldIndicator").equals("true") ? "Y" : "N";

            compare(rs.getValue("FLD_ID", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldId"));
            compare(rs.getValue("DFLT_FLD_IN", i), defaultFieldIndicator);
            compare(rs.getValue("REPEAT_FLD_IN", i), isRepeatable);
            compare(rs.getValue("FLD_NM", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldName"));
            compare(rs.getValue("FLD_DATA_TYP_NM", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldDataType"));
            TestReporter.assertAll();

        }
    }

    public void compare(String a, String b) {
        TestReporter.softAssertEquals(a, b, "Verify that the database value [" + a + "] matchest the expected [" + b + "]");
    }
}
