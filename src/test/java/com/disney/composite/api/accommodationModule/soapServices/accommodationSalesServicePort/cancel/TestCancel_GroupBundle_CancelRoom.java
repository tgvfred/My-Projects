package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindMiscPackages;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.DVCSalesDreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_GroupBundle_CancelRoom extends TravelPlanBaseTest {

    private HouseHold hh;
    private Book book;
    private AddBundle add;
    private RetrieveDetailsByTravelPlanId details;
    private int arrivalDaysOut = 0;
    private int departureDaysOut = 4;
    private String firstBundleTcg;
    private static final int maxTries = 3;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
        String locEnv;
        if (environment.toLowerCase().contains("_cm")) {
            locEnv = environment.toLowerCase().replace("_cm", "");
        } else {
            locEnv = environment;
        }

        generatHousehold();

        setValues();
        book = new Book(locEnv, "bookWholesaleOneAdultNoTickets");
        try {
            book.setExternalRefNum(Randomness.randomNumber(12));
        } catch (XPathNotFoundException e) {
            book.setRequestNodeValueByXPath("//book/request", "fx:addnode;node:externalReference");
            book.setRequestNodeValueByXPath("//book/request/externalReference", "fx:addnode;node:externalReferenceNumber");
            book.setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceNumber", Randomness.randomNumber(12));
            book.setRequestNodeValueByXPath("//book/request/externalReference", "fx:addnode;node:externalReferenceSource");
            book.setRequestNodeValueByXPath("//book/request/externalReference/externalReferenceSource", getExternalRefSource());
        }

        int bookTries = 0;
        PackageCodes pkg = new PackageCodes();
        boolean bookSuccess = false;
        do {
            book.setArrivalDate(String.valueOf(arrivalDaysOut));
            book.setDeptDate(String.valueOf(departureDaysOut));
            book.setBookingDate(Randomness.generateCurrentXMLDatetime(arrivalDaysOut));

            int tries = 0;
            String packageCode = "";
            pkg = new PackageCodes();
            boolean success = false;
            do {
                try {
                    packageCode = pkg.retrievePackageCode(locEnv, String.valueOf("0"),
                            getLocationId(), "WDW PKG", "*WDTC", getResortCode(), getRoomTypeCode(), "R MYW Pkg + Deluxe Dining");
                    success = true;
                } catch (AssertionError e) {
                    setValues();
                }
            } while (!success && ++tries < maxTries);

            TestReporter.assertTrue(success, "Successfully found package code");

            book.setBlockCode("01825");
            book.setPackageCode(packageCode);
            book.setResortCode(getResortCode());
            book.setRoomTypeCode(getRoomTypeCode());
            book.setLocationID(getLocationId());
            book.setPrimaryGuestFirstName(hh.primaryGuest().getFirstName());
            book.setPrimaryGuestFirstNameGuestRefDetails(hh.primaryGuest().getFirstName());
            book.setPrimaryGuestFirstNameTravelPlan(hh.primaryGuest().getFirstName());
            book.setPrimaryGuestLastName(hh.primaryGuest().getLastName());
            book.setPrimaryGuestLastNameGuestRefDetails(hh.primaryGuest().getLastName());
            book.setPrimaryGuestLastNameTravelPlan(hh.primaryGuest().getLastName());
            book.setTravelPlanGuestid(hh.primaryGuest().getGuestId());
            book.setTravelPlanPartyId(hh.primaryGuest().getPartyId());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", hh.primaryGuest().primaryAddress().getAddress1());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", hh.primaryGuest().primaryAddress().getCity());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName", hh.primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", hh.primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/roomDetail/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", hh.primaryGuest().primaryAddress().getZipCode());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/addressLine1", hh.primaryGuest().primaryAddress().getAddress1());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/city", hh.primaryGuest().primaryAddress().getCity());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/postalCode", hh.primaryGuest().primaryAddress().getZipCode());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/state", hh.primaryGuest().primaryAddress().getState());
            book.setRequestNodeValueByXPath("//book/request/travelPlanGuest/addressDetails/regionName", hh.primaryGuest().primaryAddress().getState());
            book.setPhoneNumber(hh.primaryGuest().primaryPhone().getNumber());
            book.setEmail(hh.primaryGuest().primaryEmail().getEmail());
            book.sendRequest();
            if (book.getResponseStatusCode().equals("200")) {
                bookSuccess = true;
            }
        } while (!bookSuccess && ++bookTries < maxTries);
        TestReporter.assertEquals(book.getResponseStatusCode(), "200", "An error occurred booking a prereq reservations: " + book.getFaultString());
        setBook(book);
        retrieveReservation(environment);

        details = new RetrieveDetailsByTravelPlanId(locEnv, "Main");
        details.setTravelPlanId(getBook().getTravelPlanId());
        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());

        add = new AddBundle(locEnv, "Main");
        add.setGuestsGuestNameFirstName(hh.primaryGuest().getFirstName());
        add.setGuestsGuestNameLastName(hh.primaryGuest().getLastName());
        add.setGuestsGuestReferenceId(details.getGuestsId());
        add.setGuestsId(details.getGuestsId());
        add.setPackageBundleRequestsBookDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
        add.setPackageBundleRequestsContactName(hh.primaryGuest().getFirstName() + " " + hh.primaryGuest().getLastName());
        add.setPackageBundleRequestsEndDate(Randomness.generateCurrentXMLDate(departureDaysOut - 2) + "T00:00:00");
        add.setPackageBundleRequestsSalesOrderItemGuestsGUestReferenceId(details.getGuestsId());
        add.setPackageBundleRequestsStartDate(Randomness.generateCurrentXMLDate(arrivalDaysOut + 1) + "T00:00:00");
        add.setTravelPlanId(book.getTravelPlanId());
        add.retrieveSalesOrderId(book.getTravelPlanId());
        add.setSalesOrderId(add.getBundleSalesOrderIds()[0]);

        FindMiscPackages find = new FindMiscPackages(locEnv, "MinimalInfo");
        find.setArrivalDate(Randomness.generateCurrentXMLDate(arrivalDaysOut));
        find.setBookDate(Randomness.generateCurrentXMLDate());
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred adding a bundle to TP ID [" + book.getTravelPlanId() + "]: " + add.getFaultString());
        add.setPackageBundleRequestsCode(find.getPackageCode());

        add.sendRequest();
        TestReporter.assertEquals(add.getResponseStatusCode(), "200", "An error occurred while adding a bundle.\nRequest:\n" + add.getRequest() + "\nResonse:\n" + add.getResponse());

        firstBundleTcg = findBundleTcg(book.getTravelPlanId());

        details.sendRequest();
        TestReporter.assertEquals(details.getResponseStatusCode(), "200", "An error occurred while retrieveing the details.\nRequest:\n" + details.getRequest() + "\nResonse:\n" + details.getResponse());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "Cancel" })
    public void testCancel_GroupBundle_CancelRoom() {
        TestReporter.logScenario("Test Cancel");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred canceling a Reservation with TPS ID [" + book.getTravelPlanSegmentId() + "]: " + cancel.getFaultString(), cancel);

        TestReporter.assertNotNull(cancel.getCancellationNumber(), "Verify that a cancellation number was returned.");
        TestReporter.log("Cancellation number: " + cancel.getCancellationNumber());

        retrieveReservation();
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancelDate").split("T")[0], DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the cancel date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancelDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber"),
                cancel.getCancellationNumber(),
                "Verify that the cancellation number [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/cancellationNumber") + "] is that which is expected [" + cancel.getCancellationNumber() + "].");
        int index = 0;
        int numAuditDetails;
        try {
            numAuditDetails = XMLTools.getNodeList(XMLTools.makeXMLDocument(getRetrieve().getResponse()), "/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails/status").getLength();
            for (int i = 1; i <= numAuditDetails; i++) {
                if (getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(i) + "]/status").equals("Cancelled")) {
                    index = i;
                    break;
                }
            }
        } catch (XPathNotFoundException e) {
            throw e;
        }
        TestReporter.assertTrue(index > 0, "Verify that a cancellation audit details node was returned in the retrieve.");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/createdDate").split("T")[0],
                DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the audit details cancellation created date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/createdDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/updatedDate").split("T")[0],
                DateTimeConversion.ConvertToDateYYYYMMDD("0"),
                "Verify that the audit details cancellation updated date [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/updatedDate").split("T")[0] + "] is that which is expected [" + DateTimeConversion.ConvertToDateYYYYMMDD("0") + "].");
        TestReporter.softAssertEquals(
                getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/status"),
                "Cancelled",
                "Verify that the audit details cancellation status [" + getRetrieve().getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/auditDetails[" + String.valueOf(index) + "]/status") + "] is that which is expected [Cancelled].");
        TestReporter.assertAll();

        validations(cancel);
    }

    private void validations(Cancel cancel) {
        CancelHelper cancelHelper = new CancelHelper(removeCM(environment), book.getTravelPlanId());
        cancelHelper.verifyChargeGroupsCancelled();
        cancelHelper.verifyCancellationIsFoundInResHistory(book.getTravelPlanSegmentId(), book.getTravelComponentGroupingId(), book.getTravelComponentId());
        // cancelHelper.verifyCancellationComment(getRetrieve(), "Air not available CancellationNumber : " + cancel.getCancellationNumber());
        cancelHelper.verifyNumberOfCharges(0);
        cancelHelper.verifyInventoryReleased(book.getTravelComponentGroupingId());
        cancelHelper.verifyNumberOfTpPartiesByTpId(2);
        cancelHelper.verifyTcStatusByTcg(book.getTravelComponentGroupingId(), "Cancelled");
        cancelHelper.verifyTcStatusByTcg(firstBundleTcg, "Booked");
        cancelHelper.verifyExchangeFeeFound(false);
        cancelHelper.verifyChargeGroupsStatusCount("Cancelled", 1);
        cancelHelper.verifyChargeGroupsStatusCount("UnEarned", 1);
        cancelHelper.verifyNumberOfChargesByStatus("Cancelled", 0);
        cancelHelper.verifyNumberOfChargesByStatus("UnEarned", 0);
        // Verify the reasonID matches the reason code used for the given TCId
        // cancelHelper.verifyProductReasonID(book.getTravelComponentId());

    }

    public static String removeCM(String cmEnv) {
        return Environment.getBaseEnvironmentName(cmEnv).toLowerCase();
    }

    private void generatHousehold() {
        String locEnv;
        if (environment.toLowerCase().contains("_cm")) {
            locEnv = environment.toLowerCase().replace("_cm", "");
        } else {
            locEnv = environment;
        }
        hh = new HouseHold(1);
        hh.sendToApi(locEnv);
    }

    public String findBundleTcg(String tpId) {
        String baseSql = DVCSalesDreams.getReservationInfoByTpId(tpId).replace("and rownum = 1", "").replace("*", "unique(c.TC_GRP_NB)");
        String sql = "select PROD_TYP_NM from res_mgmt.tc a where a.tc_grp_nb in({INPUT})";
        Database db = new OracleDatabase(DVCSalesBaseTest.removeCM(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(baseSql));

        for (int i = 1; i <= rs.getRowCount(); i++) {
            String locSql = sql;
            locSql = locSql.replace("{INPUT}", rs.getValue("TC_GRP_NB", i));
            Recordset rs2 = new Recordset(db.getResultSet(locSql));
            if (rs2.getRowCount() > 1) {
                for (int j = 1; j <= rs2.getRowCount(); j++) {
                    if (rs2.getValue("PROD_TYP_NM", j).contains("Memory Maker")) {
                        return rs.getValue("TC_GRP_NB", i);
                    }
                }
            }
        }
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No bundle was found to be associated with TP ID [" + tpId + "].");
        }
        return null;
    }
}
