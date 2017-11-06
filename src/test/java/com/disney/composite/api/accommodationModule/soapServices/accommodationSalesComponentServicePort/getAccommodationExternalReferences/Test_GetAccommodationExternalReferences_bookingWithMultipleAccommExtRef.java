package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.getAccommodationExternalReferences;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.mq.sbc.RoomRes;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.GetAccommodationExternalReferences;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_GetAccommodationExternalReferences_bookingWithMultipleAccommExtRef extends AccommodationBaseTest {
    private String extNum;
    private String extSrc;
    private String aNewString;
    RoomRes room = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);
        setSendRequest(false);
        bookReservation();

        // Sample DDR Res
        // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "DDRAH", true);
        // RoomResHelper res = new RoomResHelper(environment, "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
        // room = res.getRoomRes();

        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("externalReferences"));
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences[2]", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceSource"));
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences[2]", BaseSoapCommands.ADD_NODE.commandAppend("externalReferenceNumber"));
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences[2]/externalReferenceSource", getExternalRefSource());
        aNewString = Randomness.randomNumber(20);
        getBook().setRequestNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/externalReferences[2]/externalReferenceNumber", aNewString);
        getBook().setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences" })
    public void testGetAccommodationExternalReferences_bookingWithMultipleAccommExtRef() {

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        get.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        get.sendRequest();
        TestReporter.logAPI(!get.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", get);

        // Compares the response values to the DB values for both response
        validation(get.getExternalReferenceNumberByIndex("1"), get.getExternalReferenceSourceByIndex("1"));
        validation(get.getExternalReferenceNumberByIndex("2"), get.getExternalReferenceSourceByIndex("2"));

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            GetAccommodationExternalReferences clone = (GetAccommodationExternalReferences) get.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(get, true), "Validating Response Comparison");
        }

    }

    public void validation(String num, String src) {

        String sql = "select * from res_mgmt.TC_EXTNL_REF a where a.TC_EXTNL_REF_VL = '" + num + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        extNum = rs.getValue("TC_EXTNL_REF_VL");
        extSrc = rs.getValue("TC_EXTNL_SRC_NM");

        TestReporter.assertEquals(extNum, num, "Verify the External Reference Number [" + num + "] matches the External Reference Number found"
                + " in the DB [" + extNum + "]");

        TestReporter.assertEquals(extSrc, src, "Verify the External Reference Source [" + src + "] matches the External Reference Source found"
                + " in the DB [" + extSrc + "]");
    }

}
