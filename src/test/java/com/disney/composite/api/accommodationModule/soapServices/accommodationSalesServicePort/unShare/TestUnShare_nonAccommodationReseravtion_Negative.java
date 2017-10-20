package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.unShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams_DVCQueries;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestUnShare_nonAccommodationReseravtion_Negative extends AccommodationBaseTest {

    private UnShare unshare;
    String tpId;
    String tcgId;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "negative" })
    public void Test_unShare_nonAccommodationReservation_Negative() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Folio Fix in Progress, for now operation not supported.");
        // }
        // }
        ScheduledEventReservation dining = new ShowDiningReservation(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
        tpId = dining.getTravelPlanId();
        Database db = new OracleDatabase(getEnvironment(), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(Dreams_DVCQueries.getAllTCGsByTpId(tpId)));
        tcgId = rs.getValue("TC_GRP_NB");

        unshare = new UnShare(environment, "Main");
        unshare.setTravelComponentGroupingId(tcgId);
        unshare.setLocationId("51");
        unshare.sendRequest();

        String faultString = "Accommodations not found : No TravelComponentGrouping found";

        validateApplicationError(unshare, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);

        TestReporter.assertEquals(unshare.getFaultString(), faultString, "Verify that the fault string [" + unshare.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}
