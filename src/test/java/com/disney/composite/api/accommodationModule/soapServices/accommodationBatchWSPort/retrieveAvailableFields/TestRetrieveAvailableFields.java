package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchWSPort.retrieveAvailableFields;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.roomingListServicePort.operation.RetrieveAvailableFields;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveAvailableFields extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "Accommodation", "AccommodationBatchService", "retrieveAvailableFields" })
    public void testRetrieveAvailableFields() {

        RetrieveAvailableFields retrieve = new RetrieveAvailableFields(Environment.getBaseEnvironmentName(environment));
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "Error sending request", retrieve);

        String sql = "select a.FLD_ID, a.FLD_DATA_TYP_NM, a.FLD_NM, a.REPEAT_FLD_IN, a.DFLT_FLD_IN " +
                " from res_mgmt.data_entry_fld a";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        String xpath = "/Envelope/Body/retrieveAvaillableFieldsResponse/fieldTOs";
        int numOfFieldTOs = retrieve.getNumberOfResponseNodesByXPath(xpath);
        int expectedNumofItems = 5;
        int numOfChildNodes = XMLTools.getNodeList(retrieve.getResponseDocument(), xpath).item(0).getChildNodes().getLength();

        // Verify the number of fieldTOs in the response is the same as the query recordset
        TestReporter.logStep("Verify the number of fieldTOs in the response is the same as the query recordset");
        TestReporter.assertEquals(numOfFieldTOs, rs.getRowCount(), "Verify that the number of fieldTOs [" + numOfFieldTOs + "] matches the number of rows in the database [" + rs.getRowCount() + "]");

        // For each fieldTOs, validate fieldId, isRepeatable, defaultFieldIndicator, fieldName, fieldDataType matches the values from the database for a given ID
        TestReporter.logStep("For each fieldTOs, validate fieldId, isRepeatable, defaultFieldIndicator, fieldName, fieldDataType matches the values from the database for a given ID");
        for (int i = 1; i <= numOfFieldTOs; i++) {
            String iterator = "[" + i + "]/";
            String isRepeatable = retrieve.getResponseNodeValueByXPath(xpath + iterator + "isRepeatable").equals("true") ? "Y" : "N";
            String defaultFieldIndicator = retrieve.getResponseNodeValueByXPath(xpath + iterator + "defaultFieldIndicator").equals("true") ? "Y" : "N";

            compare(rs.getValue("FLD_ID", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldId"));
            compare(rs.getValue("DFLT_FLD_IN", i), defaultFieldIndicator);
            compare(rs.getValue("REPEAT_FLD_IN", i), isRepeatable);
            compare(rs.getValue("FLD_NM", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldName"));
            compare(rs.getValue("FLD_DATA_TYP_NM", i), retrieve.getResponseNodeValueByXPath(xpath + iterator + "fieldDataType"));
            TestReporter.assertAll();
        }

        // Verify that there are no extra nodes in the response that are not in the database
        TestReporter.logStep("Verify that there are no extra nodes in the response that are not in the database");
        compare(numOfChildNodes, expectedNumofItems);

        // Verify that there are no extra records in the database that are not in the response
        TestReporter.logStep("Verify that there are no extra records in the database that are not in the response");
        compare(rs.getColumnCount(), expectedNumofItems);
    }

    public void compare(String a, String b) {
        if (a.contains("LINE") || b.contains("LINE")) { // Address lines show up as "Address_Line_1" in database and "Address_Line1" on response xml. re-formatted to compare content
            TestReporter.softAssertEquals(a.replace("_", ""), b.replace("_", ""), "Verify that the database value [" + a.replace("_", "") + "] matches the expected [" + b.replace("_", "") + "]");
        } else {
            TestReporter.softAssertEquals(a, b, "Verify that the database value [" + a + "] matches the expected [" + b + "]");
        }
    }

    public void compare(int a, int b) {
        TestReporter.softAssertEquals(a, b, "Verify that the database value [" + a + "] matches the expected [" + b + "]");
    }
}
