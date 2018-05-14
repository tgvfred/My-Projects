package com.disney.composite.api;

import java.util.Date;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.disney.api.helpers.Agency;
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
        Agency agency = new Agency("Latest");
        System.out.println(agency.getAgencyId());
        System.out.println(agency.getOrganizationId());

    }

    private boolean isActive(
            final Date currentDate,
            final Date startDate,
            final Date effectiveTo) {
        return (startDate == null || currentDate.compareTo(startDate) >= 0)
                && (effectiveTo == null
                        || currentDate.compareTo(effectiveTo) <= 0);
    }
}
