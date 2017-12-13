package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.FacilityDatabase;
import com.disney.utils.dataFactory.database.NoRecordsInRecordsetException;
import com.disney.utils.dataFactory.database.Recordset;

public class TestShare_twoTcg_differentResorts_Negative extends AccommodationBaseTest {

    private Share share;
    String firstTcg;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {

        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());
        setValues();
        bookReservation();

        firstTcg = getBook().getTravelComponentGroupingId();
        setDaysOut(1);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());

        String previousResort = getResortCode();
        String previousRoomTypeCode = getRoomTypeCode();

        String sql = "SELECT RM_TYP_CD FROM TFDB_3.SYS_FAC A JOIN TFDB_3.RM_TYP B ON A.FAC_ID = B.FAC_ID WHERE SYS_FAC_CD = '" + previousResort + "' AND RM_TYP_CD <> '" + previousRoomTypeCode + "' LIMIT 1";
        Database db = new Database(FacilityDatabase.getInfo(Environment.getBaseEnvironmentName(environment)));
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new NoRecordsInRecordsetException("Failed to find rooms in Facility DB for Facility Code [ " + previousResort + " ]", sql);
        }

        setRoomTypeCode(rs.getValue("RM_TYP_CD"));

        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void test_Share_twoTcg_differentResorts_Negative() {

        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTcg);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE, PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE!";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.CANT_CHANGE_BLOCK_RESORT_PACKAGE_FOR_SHARED_ACCOMMODATION);
    }

}
