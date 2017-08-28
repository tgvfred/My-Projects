package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestRetrieveRates_Negative extends AccommodationBaseTest {

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates" })
    public void TestRetrieveRates_nullTcg() {
        String faultString = "Required parameters are missing : Invalid Travel Component grouping Id#0";

        TestReporter.logScenario("Test Retrieve Rates Null TCG");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieveRates.sendRequest();

        TestReporter.assertTrue(retrieveRates.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieveRates.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieveRates, AccommodationErrorCode.REQUIRED_PARAM_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates" })
    public void TestRetrieveRates_showDiningReservation() {
        String tcgId;
        ScheduledEventReservation dining = new ShowDiningReservation(getEnvironment().toLowerCase().replace("_cm", ""));
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        String sql = "select * from res_mgmt.tc_grp a where a.tps_id = '" + dining.getConfirmationNumber() + "' and a.tc_grp_typ_nm = 'SHOWDINING'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(getEnvironment()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcgId = rs.getValue("TC_GRP_NB");
        // String faultString = "Accommodation Component not found : NO ACCOMMODATION FOUND WITH ID#" + tcgId;
        String faultString = "Unexpected Error occurred : retrieveRates : java.lang.NullPointerException";

        TestReporter.logScenario("Negative Dinning reservation rates");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();

        TestReporter.assertTrue(retrieveRates.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieveRates.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieveRates, AccommodationErrorCode.UNEXPECTED_ERROR_OCCURRED);

    }
}
