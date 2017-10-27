package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class RetrieveShareChainHelper {
    private String tcg;
    private String chargeAmount;
    private String chargeFulfilmentDate;
    private String dvcPointsValue;
    private String chargeItemAmount;

    public String getTcg() {
        return tcg;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public String chargeFulfilmentDate() {
        return chargeFulfilmentDate;
    }

    public String getDvcPointsValue() {
        return dvcPointsValue;
    }

    public String getChargeItemAmount() {
        return chargeItemAmount;
    }

    public void getTcgUsingTps(String env, String tpsId) {

        String sql = "select tc_grp_nb"
                + " from res_mgmt.tc_grp"
                + " where tc_grp.tps_id = " + tpsId + " ";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcg = rs.getValue("TC_GRP_NB");

    }

    public void getChargeItemDetails(String env, String tcId) {

        String sql = "select b.CHRG_AM, b.CHRG_FFL_DTS, b.DVC_PTS_VL, c.CHRG_ITEM_AM"
                + " from folio.chrg_extnl_ref a"
                + " join folio.chrg b on a.chrg_id = b.chrg_id"
                + " join folio.chrg_item c on b.chrg_id = c.chrg_id"
                + " where a.CHRG_EXTNL_REF_VL = '" + tcId + "' "
                + " and c.CHRG_ITEM_TYP_NM = 'Base'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for sql", sql);
        }

        chargeAmount = rs.getValue("CHRG_AM");
        chargeFulfilmentDate = rs.getValue("CHRG_FFL_DTS");
        dvcPointsValue = rs.getValue("DVC_PTS_VL");
        chargeItemAmount = rs.getValue("CHRG_ITEM_AM");

    }

    public void validateNodeCount(RetrieveShareChain retrieve, String xpath, int count) {
        TestReporter.assertTrue(retrieve.getNumberOfResponseNodesByXPath(xpath) == count, "Verify there is only one node returned [" + retrieve.getNumberOfResponseNodesByXPath(xpath) + "]");
    }

    public void validateBaseNodes(ReplaceAllForTravelPlanSegment book, RetrieveShareChain retrieve) {

        String bookingDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/bookingDate");
        String packageCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/packageCode");
        String roomTypeCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomTypeCode");
        String tcgId = book.getTravelComponentGroupingId();
        String tcId = book.getTravelComponentId();
        String travelStatus = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");
        String locationId = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/locationId");
        String resortPeriodEndDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/endDate");
        String resortPeriodStartDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/startDate");
        String phoneNumber = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");
        String addressLine1 = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");
        String city = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city");
        String country = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country");
        String postalCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode");
        String state = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state");
        String regionName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName");
        String emailAddress = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");
        String preferredLanguage = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage");

        String splitRetrieveBookingDate[] = retrieve.getBookingDate().split("T");
        String splitRetrieveResortEndDate[] = retrieve.getResortPeriodEndDate().split("T");
        String splitRetrieveResortStartDate[] = retrieve.getResortPeriodStartDate().split("T");
        String splitBookingDate[] = bookingDate.split("T");
        String splitResortEndDate[] = resortPeriodEndDate.split("T");
        String splitResortStartDate[] = resortPeriodStartDate.split("T");

        TestReporter.assertEquals(splitBookingDate[0], splitRetrieveBookingDate[0], "Verify the booking date [" + splitRetrieveBookingDate[0] + "] matches the expected [" + splitBookingDate[0] + "]");
        TestReporter.assertEquals(packageCode, retrieve.getPackageCode(), "Verify the package code [" + retrieve.getPackageCode() + "] matches the expected [" + packageCode + "]");
        TestReporter.assertEquals(roomTypeCode, retrieve.roomTypeCode(), "Verify the room type code [" + retrieve.roomTypeCode() + "] matches the expected [" + roomTypeCode + "]");
        TestReporter.assertEquals(tcgId, retrieve.getTravelComponentGroupingId(), "Verify the tcg Id [" + retrieve.getTravelComponentGroupingId() + "] matches the expected [" + tcgId + "]");
        TestReporter.assertEquals(tcId, retrieve.getTravelComponentId(), "Verify the tc Id [" + retrieve.getTravelComponentId() + "] matches the expected [" + tcId + "]");
        TestReporter.assertEquals(travelStatus, retrieve.getTravelStatus(), "Verify the tcg Id [" + retrieve.getTravelStatus() + "] matches the expected [" + travelStatus + "]");
        TestReporter.assertEquals(locationId, retrieve.getLocationId(), "Verify the location Id [" + retrieve.getLocationId() + "] matches the expected [" + locationId + "]");
        TestReporter.assertEquals(splitResortEndDate[0], splitRetrieveResortEndDate[0], "Verify the resort period end date [" + splitRetrieveResortEndDate[0] + "] matches the expected [" + splitResortEndDate[0] + "]");
        TestReporter.assertEquals(splitResortStartDate[0], splitRetrieveResortStartDate[0], "Verify the resort period start date [" + splitRetrieveResortStartDate[0] + "] matches the expected [" + splitResortStartDate[0] + "]");
        TestReporter.assertEquals(phoneNumber, retrieve.getPhoneNumber(), "Verify the phone number [" + retrieve.getPhoneNumber() + "] matches the expected [" + phoneNumber + "]");
        TestReporter.assertEquals(addressLine1, retrieve.getAddressLine1(), "Verify the address line 1 [" + retrieve.getAddressLine1() + "] matches the expected [" + addressLine1 + "]");
        TestReporter.assertEquals(city, retrieve.getCity(), "Verify the city [" + retrieve.getCity() + "] matches the expected [" + city + "]");
        country = country.equals("United States") ? "USA" : country;
        TestReporter.assertEquals(country, retrieve.getCountry(), "Verify the country [" + retrieve.getCountry() + "] matches the expected [" + country + "]");
        TestReporter.assertEquals(postalCode, retrieve.getPostalCode(), "Verify the postal code [" + retrieve.getPostalCode() + "] matches the expected [" + postalCode + "]");
        state = abbreviateState(state);
        TestReporter.assertEquals(state, retrieve.getState(), "Verify the state[" + retrieve.getState() + "] matches the expected [" + state + "]");
        String abbreviateRegionName = abbreviateState(retrieve.getRegionName());
        TestReporter.assertEquals(regionName, abbreviateRegionName, "Verify the region name [" + abbreviateRegionName + "] matches the expected [" + regionName + "]");
        TestReporter.assertEquals(emailAddress, retrieve.getEmailAddress(), "Verify the email address [" + retrieve.getEmailAddress() + "] matches the expected [" + emailAddress + "]");
        preferredLanguage = preferredLanguage.equals("English") ? "eng" : preferredLanguage;
        TestReporter.assertEquals(preferredLanguage, retrieve.getPrefferedLanguage(), "Verify the preferred language [" + retrieve.getPrefferedLanguage() + "] matches the expected [" + preferredLanguage + "]");

    }

    public void validateBaseNodes_multi(ReplaceAllForTravelPlanSegment book, RetrieveShareChain retrieve) {

        String bookingDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/bookingDate");
        String packageCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/packageCode");
        String roomTypeCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomTypeCode");
        String tcgId = book.getTravelComponentGroupingId();
        String tcId = book.getTravelComponentId();
        String travelStatus = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");
        String locationId = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/locationId");
        String resortPeriodEndDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/endDate");
        String resortPeriodStartDate = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/startDate");
        String phoneNumber = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");
        String addressLine1 = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");
        String city = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city");
        String country = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country");
        String postalCode = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode");
        String state = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state");
        String regionName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/regionName");
        String emailAddress = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");
        String preferredLanguage = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/preferredLanguage");

        String splitRetrieveBookingDate[] = retrieve.getBookingDate().split("T");
        String splitRetrieveResortEndDate[] = retrieve.getResortPeriodEndDate().split("T");
        String splitRetrieveResortStartDate[] = retrieve.getResortPeriodStartDate().split("T");
        String splitBookingDate[] = bookingDate.split("T");
        String splitResortEndDate[] = resortPeriodEndDate.split("T");
        String splitResortStartDate[] = resortPeriodStartDate.split("T");

        TestReporter.assertEquals(splitBookingDate[0], splitRetrieveBookingDate[0], "Verify the booking date [" + splitRetrieveBookingDate[0] + "] matches the expected [" + splitBookingDate[0] + "]");
        TestReporter.assertEquals(packageCode, retrieve.getPackageCode(), "Verify the package code [" + retrieve.getPackageCode() + "] matches the expected [" + packageCode + "]");
        TestReporter.assertEquals(roomTypeCode, retrieve.roomTypeCode(), "Verify the room type code [" + retrieve.roomTypeCode() + "] matches the expected [" + roomTypeCode + "]");
        TestReporter.assertEquals(tcgId, retrieve.getTravelComponentGroupingId(), "Verify the tcg Id [" + retrieve.getTravelComponentGroupingId() + "] matches the expected [" + tcgId + "]");
        TestReporter.assertEquals(tcId, retrieve.getTravelComponentId(), "Verify the tc Id [" + retrieve.getTravelComponentId() + "] matches the expected [" + tcId + "]");
        TestReporter.assertEquals(travelStatus, retrieve.getTravelStatus(), "Verify the tcg Id [" + retrieve.getTravelStatus() + "] matches the expected [" + travelStatus + "]");
        TestReporter.assertEquals(locationId, retrieve.getLocationId(), "Verify the location Id [" + retrieve.getLocationId() + "] matches the expected [" + locationId + "]");
        TestReporter.assertEquals(splitResortEndDate[0], splitRetrieveResortEndDate[0], "Verify the resort period end date [" + splitRetrieveResortEndDate[0] + "] matches the expected [" + splitResortEndDate[0] + "]");
        TestReporter.assertEquals(splitResortStartDate[0], splitRetrieveResortStartDate[0], "Verify the resort period start date [" + splitRetrieveResortStartDate[0] + "] matches the expected [" + splitResortStartDate[0] + "]");
        TestReporter.assertEquals(phoneNumber, retrieve.getPhoneNumber(), "Verify the phone number [" + retrieve.getPhoneNumber() + "] matches the expected [" + phoneNumber + "]");
        TestReporter.assertEquals(addressLine1, retrieve.getAddressLine1(), "Verify the address line 1 [" + retrieve.getAddressLine1() + "] matches the expected [" + addressLine1 + "]");
        TestReporter.assertEquals(city, retrieve.getCity(), "Verify the city [" + retrieve.getCity() + "] matches the expected [" + city + "]");
        country = country.equals("United States") ? "USA" : country;
        TestReporter.assertEquals(country, retrieve.getCountry(), "Verify the country [" + retrieve.getCountry() + "] matches the expected [" + country + "]");
        TestReporter.assertEquals(postalCode, retrieve.getPostalCode(), "Verify the postal code [" + retrieve.getPostalCode() + "] matches the expected [" + postalCode + "]");
        state = abbreviateState(state);
        TestReporter.assertEquals(state, retrieve.getState(), "Verify the state[" + retrieve.getState() + "] matches the expected [" + state + "]");
        String abbreviateRegionName = abbreviateState(retrieve.getRegionName());
        TestReporter.assertEquals(regionName, abbreviateRegionName, "Verify the region name [" + abbreviateRegionName + "] matches the expected [" + regionName + "]");
        TestReporter.assertEquals(emailAddress, retrieve.getEmailAddress(), "Verify the email address [" + retrieve.getEmailAddress() + "] matches the expected [" + emailAddress + "]");
        preferredLanguage = preferredLanguage.equals("English") ? "eng" : preferredLanguage;
        TestReporter.assertEquals(preferredLanguage, retrieve.getPrefferedLanguage(), "Verify the preferred language [" + retrieve.getPrefferedLanguage() + "] matches the expected [" + preferredLanguage + "]");

    }

    public void validateGuestDetails(ReplaceAllForTravelPlanSegment book, RetrieveShareChain retrieve) {

        String guestIndex = "";
        int amountOfGuests = book.getNumberOfResponseNodesByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails");
        switch (amountOfGuests) {

            case 1:
                String guestFirstName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/firstName");
                String guestLastName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/lastName");
                String guestMiddleName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/middleName");
                TestReporter.assertEquals(guestFirstName, retrieve.getGuestFirstName(), "Verify the guest first name [" + retrieve.getGuestFirstName() + "] matches the expected [" + guestFirstName + "]");
                TestReporter.assertEquals(guestLastName, retrieve.getGuestLastName(), "Verify the guest last name [" + retrieve.getGuestLastName() + "] matches the expected [" + guestLastName + "]");
                TestReporter.assertEquals(guestMiddleName, retrieve.getGuestMiddleName(), "Verify the guest middle name [" + retrieve.getGuestMiddleName() + "] matches the expected [" + guestMiddleName + "]");
                break;

            case 2:
                for (int i = 1; i < 3; i++) {
                    guestIndex = "[" + i + "]";
                    String twoGuestsFirstName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/firstName");
                    String twoGuestsLastName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/lastName");
                    String twoGuestsMiddleName = book.getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails" + guestIndex + "/guest/middleName");
                    TestReporter.assertEquals(twoGuestsFirstName, retrieve.getMultipleGuestFirstName(1, i), "Verify the guest first name [" + retrieve.getMultipleGuestFirstName(1, i) + "] matches the expected [" + twoGuestsFirstName + "]");
                    TestReporter.assertEquals(twoGuestsLastName, retrieve.getMultipleGuestLastName(1, i), "Verify the guest last name [" + retrieve.getMultipleGuestLastName(1, i) + "] matches the expected [" + twoGuestsLastName + "]");
                    TestReporter.assertEquals(twoGuestsMiddleName, retrieve.getMultipleGuestMiddleName(1, i), "Verify the guest middle name [" + retrieve.getMultipleGuestMiddleName(1, i) + "] matches the expected [" + twoGuestsMiddleName + "]");
                }
                break;
        }
    }

    public void validateRateDetails(String env, RetrieveShareChain retrieve) {
        String basePrice = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/basePrice");
        String date = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/date");
        String netPrice = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/netPrice");
        String pointsValue = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/pointsValue");

        String sql = "select b.CHRG_AM, b.CHRG_FFL_DTS, b.DVC_PTS_VL, c.CHRG_ITEM_AM"
                + " from folio.chrg_extnl_ref a"
                + " join folio.chrg b on a.chrg_id = b.chrg_id"
                + " join folio.chrg_item c on b.chrg_id = c.chrg_id"
                + " where a.CHRG_EXTNL_REF_VL = '" + retrieve.getTravelComponentId() + "'"
                + " and c.CHRG_ITEM_TYP_NM = 'Base'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for sql", sql);
        }

        String splitBasePrice[] = basePrice.split("\\.");
        TestReporter.assertEquals(splitBasePrice[0], rs.getValue("CHRG_ITEM_AM"), "Verify the base price [" + splitBasePrice[0] + "] matches the expected [" + rs.getValue("CHRG_ITEM_AM") + "]");
        String retrieveDate[] = date.split("T");
        String databaseDate[] = rs.getValue("CHRG_FFL_DTS").split(" ");
        TestReporter.assertEquals(retrieveDate[0], databaseDate[0], "Verify the date [" + retrieveDate[0] + "] matches the expected [" + databaseDate[0] + "]");
        String splitNetPrice[] = netPrice.split("\\.0");
        TestReporter.assertEquals(splitNetPrice[0], rs.getValue("CHRG_AM"), "Verify the net price [" + splitNetPrice[0] + "] matches the expected [" + rs.getValue("CHRG_AM") + "]");
        pointsValue = pointsValue.equals("0") ? "NULL" : pointsValue;
        TestReporter.assertEquals(pointsValue, rs.getValue("DVC_PTS_VL"), "Verify the points value [" + pointsValue + "] matches the expected [" + rs.getValue("DVC_PTS_VL") + "]");

    }

    public void validateMultipleRateDetails(String env, RetrieveShareChain retrieve) {
        Map<Integer, String> dates = new HashMap<>();
        int numRateDetails = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails[1]/sharedRoomDetail/rateDetails");
        String basePrice = "";
        String netPrice = "";
        if (numRateDetails > 1) {
            double totPrice = 0.0;
            double totNetPrice = 0.0;
            for (int i = 1; i <= numRateDetails; i++) {
                totPrice += Double.parseDouble(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails[" + i + "]/basePrice"));
                dates.put(i, retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails[" + i + "]/date").split("T")[0]);
                totNetPrice += Double.parseDouble(retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails[" + i + "]/netPrice"));
            }
            basePrice = String.valueOf(totPrice);
            netPrice = String.valueOf(totNetPrice);
        } else {
            basePrice = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/basePrice");
        }

        String date = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/date");
        // String netPrice = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/netPrice");
        String pointsValue = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/rateDetails/pointsValue");

        Database db = new OracleDatabase(env, Database.DREAMS);
        String sql = "select b.CHRG_AM, b.CHRG_FFL_DTS, b.DVC_PTS_VL, c.CHRG_ITEM_AM"
                + " from folio.chrg_extnl_ref a"
                + " join folio.chrg b on a.chrg_id = b.chrg_id"
                + " join folio.chrg_item c on b.chrg_id = c.chrg_id"
                + " where a.CHRG_EXTNL_REF_VL = '" + retrieve.getTravelComponentId() + "'";
        Recordset rs = new Recordset(db.getResultSet(sql));

        String dbNetPrice = "";
        Double calcNetPrice = 0.0;
        do {
            calcNetPrice += Double.parseDouble(rs.getValue("CHRG_AM"));
            rs.moveNext();
        } while (rs.hasNext());
        dbNetPrice = String.valueOf(calcNetPrice);

        rs.moveFirst();

        sql = "select b.CHRG_AM, b.CHRG_FFL_DTS, b.DVC_PTS_VL, c.CHRG_ITEM_AM"
                + " from folio.chrg_extnl_ref a"
                + " join folio.chrg b on a.chrg_id = b.chrg_id"
                + " join folio.chrg_item c on b.chrg_id = c.chrg_id"
                + " where a.CHRG_EXTNL_REF_VL = '" + retrieve.getTravelComponentId() + "'"
                + " and c.CHRG_ITEM_TYP_NM = 'Base'";

        rs = new Recordset(db.getResultSet(sql));

        String dbVAlue = "";
        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for sql", sql);
        } else {
            double tot = 0.0;
            do {
                tot += Double.parseDouble(rs.getValue("CHRG_ITEM_AM"));
                rs.moveNext();
            } while (rs.hasNext());
            dbVAlue = String.valueOf(tot);
        }
        rs.moveFirst();
        // String splitBasePrice[] = basePrice.split("\\.");
        // TestReporter.assertEquals(splitBasePrice[0], rs.getValue("CHRG_ITEM_AM"), "Verify the base price [" + splitBasePrice[0] + "] matches the expected [" + rs.getValue("CHRG_ITEM_AM") + "]");
        TestReporter.assertEquals(Double.parseDouble(basePrice), Double.parseDouble(dbVAlue), "Verify the base price [" + Double.parseDouble(basePrice) + "] matches the expected [" + Double.parseDouble(dbVAlue) + "]");
        String retrieveDate[] = date.split("T");
        String databaseDate[] = rs.getValue("CHRG_FFL_DTS").split(" ");
        // TestReporter.assertEquals(retrieveDate[0], databaseDate[0], "Verify the date [" + retrieveDate[0] + "] matches the expected [" + databaseDate[0] + "]");
        TestReporter.assertTrue(dates.containsValue(databaseDate[0]), "Verify the date [" + retrieveDate[0] + "] is contained in a map of expected values [" + dates + "]");
        // String splitNetPrice[] = netPrice.split("\\.0");
        // TestReporter.assertEquals(splitNetPrice[0], rs.getValue("CHRG_AM"), "Verify the net price [" + splitNetPrice[0] + "] matches the expected [" + rs.getValue("CHRG_AM") + "]");
        // TestReporter.assertEquals(netPrice, dbNetPrice, "Verify the net price [" + netPrice + "] matches the expected [" + dbNetPrice + "]");
        pointsValue = pointsValue.equals("0") ? "NULL" : pointsValue;
        TestReporter.assertEquals(pointsValue, rs.getValue("DVC_PTS_VL"), "Verify the points value [" + pointsValue + "] matches the expected [" + rs.getValue("DVC_PTS_VL") + "]");
    }

    public String abbreviateState(String state) {

        switch (state) {
            case "Alabama":
                state = "AL";
                break;
            case "Alaska":
                state = "AK";
                break;
            case "Alberta":
                state = "AB";
                break;
            case "Arizona":
                state = "AZ";
                break;
            case "Arkansas":
                state = "AR";
                break;
            case "California":
                state = "CA";
                break;
            case "Colorado":
                state = "CO";
                break;
            case "Connecticut":
                state = "CT";
                break;
            case "Delaware":
                state = "DE";
                break;
            case "District Of Columbia":
                state = "DC";
                break;
            case "Florida":
                state = "FL";
                break;
            case "Georgia":
                state = "GA";
                break;
            case "Hawaii":
                state = "HI";
                break;
            case "Idaho":
                state = "ID";
                break;
            case "Illinois":
                state = "IL";
                break;
            case "Indiana":
                state = "IN";
                break;
            case "Iowa":
                state = "IA";
                break;
            case "Kansas":
                state = "KS";
                break;
            case "Kentucky":
                state = "KY";
                break;
            case "Louisiana":
                state = "LA";
                break;
            case "Maine":
                state = "ME";
                break;
            case "Manitoba":
                state = "MB";
                break;
            case "Maryland":
                state = "MD";
                break;
            case "Massachusetts":
                state = "MA";
                break;
            case "Michigan":
                state = "MI";
                break;
            case "Minnesota":
                state = "MN";
                break;
            case "Mississippi":
                state = "MS";
                break;
            case "Missouri":
                state = "MO";
                break;
            case "Montana":
                state = "MT";
                break;
            case "Nebraska":
                state = "NE";
                break;
            case "Nevada":
                state = "NV";
                break;
            case "New Brunswick":
                state = "NB";
                break;
            case "New Hampshire":
                state = "NH";
                break;
            case "New Jersey":
                state = "NJ";
                break;
            case "New Mexico":
                state = "NM";
                break;
            case "New York":
                state = "NY";
                break;
            case "North Carolina":
                state = "NC";
                break;
            case "North Dakota":
                state = "ND";
                break;
            case "Ohio":
                state = "OH";
                break;
            case "Oklahoma":
                state = "OK";
                break;
            case "Oregon":
                state = "OR";
                break;
            case "Pennsylvania":
                state = "PA";
                break;
            case "Puerto Rico":
                state = "PR";
                break;
            case "Rhode Island":
                state = "RI";
                break;
            case "Saskatchewan":
                state = "SK";
                break;
            case "South Carolina":
                state = "SC";
                break;
            case "South Dakota":
                state = "SD";
                break;
            case "Tennessee":
                state = "TN";
                break;
            case "Texas":
                state = "TX";
                break;
            case "Utah":
                state = "UT";
                break;
            case "Vermont":
                state = "VT";
                break;
            case "Virgin Islands":
                state = "VI";
                break;
            case "Virginia":
                state = "VA";
                break;
            case "Washington":
                state = "WA";
                break;
            case "West Virginia":
                state = "WV";
                break;
            case "Wisconsin":
                state = "WI";
                break;
            case "Wyoming":
                state = "WY";
                break;
            default:
                state = "null";
                break;
        }

        return state;
    }
}
