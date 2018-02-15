package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestAutoReinstate_diningOnly_minimalInfo_negative extends AccommodationBaseTest {

    AutoReinstate auto;
    Cancel cancel;
    private ScheduledEventReservation diningRes;
    String tcg;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate", "negative" })
    public void Test_AutoReinstate_diningOnly_minimalInfo_negative() {

        ScheduledEventReservation diningRes = new ShowDiningReservation(getEnvironment().toLowerCase().replace("_cm", ""));
        diningRes.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        String sql = "select * from res_mgmt.tc_grp a where a.tps_id = '" + diningRes.getConfirmationNumber() + "' and a.tc_grp_typ_nm = 'SHOWDINING'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(getEnvironment()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcg = rs.getValue("TC_GRP_NB");

        diningRes.cancel();

        auto = new AutoReinstate(environment, "Main");
        auto.setTravelComponentGroupingId(tcg);
        auto.sendRequest();

        String faultString = "Accommodation Component not found";

        validateApplicationError(auto, AccommodationErrorCode.ACCOMMODATION_COMPONENT_NOT_FOUND);
        TestReporter.assertEquals(faultString, auto.getFaultString(), "Verify that the fault string [" + auto.getFaultString() + "] is that which is expected.[" + faultString + "]");
    }

}
