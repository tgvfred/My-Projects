package com.disney.composite.api;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_AccommodationQueries;

public class Sandbox {
    private String environment = "Latest";
    private String tpsId;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        Database db = new OracleDatabase(environment, Database.DVC_POINTS);
        Recordset rs = new Recordset(
                db.getResultSet(
                        Dreams_AccommodationQueries
                                .getReservationInfoByTpId("480047528288")));
        TestReporter.assertTrue(rs.getRowCount() > 0,
                "Verify that folio charges were found form folio transaction ID [" + 82451375 + "].");
        tpsId = rs.getValue("TPS_ID");
    }

    @Test
    public void roomOnlyBooking() {
        Retrieve retrieve = new Retrieve(environment, "ByTPS_ID");
        retrieve.setTravelPlanSegmentId(tpsId);
        retrieve.setLocationId("51");
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"),
                "Verify that no error occurred retrieving: " + retrieve.getFaultString(),
                retrieve);
    }
}