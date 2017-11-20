package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.Test;

import com.disney.api.helpers.OfferQueryHelper;
import com.disney.api.helpers.RoomResHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieve_SbcRes_package extends AccommodationBaseTest {

    private String tpID;
    private String tpsID;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String partyId;
    private String extRefNum;
    private String blockCode;
    private String extRefType;
    private String tpsExtRefID;
    private String tpsSourceName;
    private String tpsExtRefValue;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_SbcRes_package() {

        packageBooking();

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(tpID);
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.setFlag(true);
        helper.setValidateProfile(false);
        helper.baseValidation(getBook(), retrieve);

        TestReporter.softAssertEquals(firstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + firstName + "]");
        TestReporter.softAssertEquals(lastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + lastName + "]");
        TestReporter.softAssertEquals(phone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + phone + "]");
        TestReporter.softAssertEquals(address, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + address + "]");
        TestReporter.softAssertEquals(email, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + email + "]");

        TestReporter.softAssertEquals(retrieve.getGuestIdReferencesType(), "PASSPORT", "Verify Guest ID ref type  is PASSPORT as expected "
                + "[" + retrieve.getGuestIdReferencesType() + "]");
        TestReporter.softAssertEquals(retrieve.getGuestId(), partyId, "Verify Guest ID ref value: [" + retrieve.getGuestId() + "] matches the party ID as expected "
                + "[" + partyId + "]");
        TestReporter.softAssertEquals(retrieve.getPPGuestIdReferencesType(), "PASSPORT", "Verify PP Guest ID ref type  is PASSPORT as expected "
                + "[" + retrieve.getPPGuestIdReferencesType() + "]");
        TestReporter.softAssertEquals(retrieve.getGuestId(), partyId, "Verify PP Guest ID ref value: [" + retrieve.getGuestId() + "] matches the party ID as expected "
                + "[" + partyId + "]");
        TestReporter.softAssertEquals(retrieve.getTCGGuestIdReferencesType(), "PASSPORT", "Verify TCG Guest ID ref type  is PASSPORT as expected "
                + "[" + retrieve.getTCGGuestIdReferencesType() + "]");
        TestReporter.softAssertEquals(retrieve.getGuestId(), partyId, "Verify TCG Guest ID ref value: [" + retrieve.getGuestId() + "] matches the party ID as expected "
                + "[" + partyId + "]");
        TestReporter.softAssertEquals(retrieve.getTPSGuestIdReferencesType(), "PASSPORT", "Verify TPS Guest ID ref type  is PASSPORT as expected "
                + "[" + retrieve.getTPSGuestIdReferencesType() + "]");
        TestReporter.softAssertEquals(retrieve.getGuestId(), partyId, "Verify TPS Guest ID ref value: [" + retrieve.getGuestId() + "] matches the party ID as expected "
                + "[" + partyId + "]");

        TestReporter.softAssertEquals(retrieve.getBlockCode(), blockCode, "Verify block code from retrieve: [" + retrieve.getBlockCode() + "] matches the block code from the reservation "
                + "[" + blockCode + "]");
        TestReporter.softAssertEquals(retrieve.getSecurityLevel(), "NTP", "Verify security level from retrieve: [" + retrieve.getSecurityLevel() + "] is as expected NTP");
        TestReporter.softAssertEquals(retrieve.getExternalRefNum(), extRefNum, "Verify the ext ref number from retrieve: [" + retrieve.getExternalRefNum() + "] matches the ext ref number from the reservation "
                + "[" + extRefNum + "]");
        TestReporter.softAssertEquals(retrieve.getExternalRefSource(), tpsSourceName, "Verify the ext ref number from retrieve: [" + retrieve.getExternalRefSource() + "] comes back as expected "
                + "[" + tpsSourceName + "]");

        TestReporter.softAssertEquals(retrieve.getTPSExternalRefType(), extRefType, "Verify the ext ref type from retrieve: [" + retrieve.getTPSExternalRefType() + "] matches the ext ref number from the reservation "
                + "[" + extRefType + "]");
        TestReporter.softAssertEquals(retrieve.getTPSExternalRefCode(), tpsExtRefID, "Verify the ext ref code from retrieve: [" + retrieve.getTPSExternalRefCode() + "] matches the ext ref number from the reservation "
                + "[" + tpsExtRefID + "]");
        TestReporter.softAssertEquals(retrieve.getTPSExternalRefNumber(), tpsExtRefValue, "Verify the ext ref number from retrieve: [" + retrieve.getTPSExternalRefNumber() + "] matches the ext ref number from the reservation "
                + "[" + tpsExtRefValue + "]");
        TestReporter.softAssertEquals(retrieve.getTPSExternalRefSource(), tpsSourceName, "Verify the ext ref source from retrieve: [" + retrieve.getTPSExternalRefSource() + "] matches the ext ref number from the reservation "
                + "[" + tpsSourceName + "]");
        TestReporter.assertAll();

        // Old vs New
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (retrieve.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk[text()='false']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation[text()='false']");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

    }

    public void packageBooking() {
        OfferQueryHelper offer = new OfferQueryHelper(Environment.getBaseEnvironmentName(getEnvironment()), "WDW", "Package", true);
        RoomResHelper res = new RoomResHelper(Environment.getBaseEnvironmentName(getEnvironment()), "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);

        Sleeper.sleep(2000);
        tpID = res.getRoomRes().getItineraryId();
        tpsID = res.getRoomRes().getLogiParentId();
        firstName = res.getGuest().primaryGuest().getFirstName();
        lastName = res.getGuest().primaryGuest().getLastName();
        address = res.getGuest().primaryGuest().primaryAddress().getAddress1();
        email = res.getGuest().primaryGuest().primaryEmail().getEmail();
        phone = res.getGuest().primaryGuest().primaryPhone().getNumber();
        partyId = res.getGuest().primaryGuest().getPartyId();
        extRefNum = res.getRoomRes().getLogiChildId();
        blockCode = res.getRoomRes().getGroupCode();

        String sql = "Select * "
                + "From RES_MGMT.TPS_EXTNL_REF "
                + "Where TPS_EXTNL_REF_VL = '" + tpsID + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(getEnvironment()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        extRefType = rs.getValue("TPS_EXTNL_REF_TYP_NM");
        tpsExtRefID = rs.getValue("TPS_EXTNL_REF_ID");
        tpsSourceName = rs.getValue("TPS_SRC_NM");
        tpsExtRefValue = rs.getValue("TPS_EXTNL_REF_VL");

    }

}
