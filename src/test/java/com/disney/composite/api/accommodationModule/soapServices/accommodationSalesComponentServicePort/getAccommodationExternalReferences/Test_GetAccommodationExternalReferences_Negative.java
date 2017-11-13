package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.getAccommodationExternalReferences;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.GetAccommodationExternalReferences;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class Test_GetAccommodationExternalReferences_Negative extends AccommodationBaseTest {

    private ScheduledEventReservation diningRes;

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            diningRes.cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences", "negative" })
    public void testGetAccommodationExternalReferences_nullRequest() {

        String faultString = "Required parameters are missing : Missing Required Parameters";

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.sendRequest();

        TestReporter.assertEquals(faultString, get.getFaultString(), "Verify that the fault string [" + get.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(get, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences", "negative" })
    public void testGetAccommodationExternalReferences_tpsOnly() {

        String faultString = "Required parameters are missing : Missing Required Parameters";

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        get.sendRequest();

        TestReporter.assertEquals(faultString, get.getFaultString(), "Verify that the fault string [" + get.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(get, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences", "negative" })
    public void testGetAccommodationExternalReferences_negativeTcg() {

        String faultString = "Required parameters are missing : Missing Required Parameters";

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelComponentGroupingId("-1");
        get.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        get.sendRequest();

        TestReporter.assertEquals(faultString, get.getFaultString(), "Verify that the fault string [" + get.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(get, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences", "negative" })
    public void testGetAccommodationExternalReferences_showDiningReservation() {

        String tcg;
        String faultString = " No Accommodation Component found. : No Accommodation Found";

        // Removed dining.setTravelPlanId(getBook().getTravelPlanId()); to make it a stand-alone, include it to add to the Accommodation
        ScheduledEventReservation dining = new ShowDiningReservation(getEnvironment().toLowerCase().replace("_cm", ""));
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        String sql = "select * from res_mgmt.tc_grp a where a.tps_id = '" + dining.getConfirmationNumber() + "' and a.tc_grp_typ_nm = 'SHOWDINING'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(getEnvironment()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcg = rs.getValue("TC_GRP_NB");

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelComponentGroupingId(tcg);
        get.setTravelPlanSegmentId(dining.getConfirmationNumber());
        get.sendRequest();

        TestReporter.assertEquals(faultString, get.getFaultString(), "Verify that the fault string [" + get.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(get, AccommodationErrorCode.NO_ACCOMMODATION_FOUND);
    }
}
