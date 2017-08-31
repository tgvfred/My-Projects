package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.cancel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindMiscPackages;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.AddBundle;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.api.soapServices.travelPlanModule.TravelPlanBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.DVCSalesDreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.date.DateTimeConversion;

public class TestCancel_GroupBundle_CancelBundle_Negative extends TravelPlanBaseTest {

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
    public void testCancel_GroupBundle_CancelBundle_Negative() {
        TestReporter.logScenario("Test Cancel_GroupBundle_CancelBundle_Negative");

        String faultString = "External Reference is required : External Reference Number is missing !";

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(firstBundleTcg);
        cancel.sendRequest();

        TestReporter.assertTrue(cancel.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + cancel.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(cancel, AccommodationErrorCode.INVALID_EXT_REF_DETAILS);

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
