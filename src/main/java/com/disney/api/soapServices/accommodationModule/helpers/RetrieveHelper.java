package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.dvcModule.dvcSalesService.accommodationSales.operations.Book;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.guestFactory.DVCMember;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class RetrieveHelper {

    public boolean sbc = false;
    public boolean first = false;
    public boolean second = false;
    public Boolean validateProfile = true;

    public void setValidateProfile(Boolean validateProfile) {
        this.validateProfile = validateProfile;
    }

    public void setFlag(Boolean boo) {
        this.sbc = boo;
    }

    public void setFirst(Boolean boo) {
        this.first = boo;
    }

    public void setSecond(Boolean boo) {
        this.second = boo;
    }

    public void baseValidation(ReplaceAllForTravelPlanSegment book, Retrieve retrieve) {

        TestReporter.logStep("Base Validation");

        String guestfirstName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName");

        String guestlastName = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName");

        String guestPhone = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/phoneDetails/number");

        String guestAddress = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1");

        String guestEmail = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/emailDetails/address");

        String startPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/startDate");

        String endPeriod = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/resortPeriod/endDate");

        String guestPartyId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");

        String guestGuestId = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");

        String travelStatus = book.getResponseNodeValueByXPath("Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelStatus");

        if (sbc) {
            TpsValidation(retrieve);
            TestReporter.softAssertTrue(!retrieve.getPartyId().isEmpty(), "Verify the party id is in the response [" + retrieve.getPartyId() + "].");

            TestReporter.softAssertTrue(!retrieve.getPPFirstName().isEmpty(), "Verify the primary party first name is in response [" + retrieve.getPPFirstName() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPLastName().isEmpty(), "Verify the primary party last name  is in response [" + retrieve.getPPLastName() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPPhone().isEmpty(), "Verify the primary party phone is in response [" + retrieve.getPPPhone() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPAddress().isEmpty(), "Verify the primary party address is in response [" + retrieve.getPPAddress() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPEmail().isEmpty(), "Verify the primary party email is in response [" + retrieve.getPPEmail() + "].");

            TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoTP().isEmpty(), "Verify the room ready notification information travel plan id is in response [" + retrieve.getRoomReadyNotificationInfoTP() + "].");

            TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoRequired().isEmpty(), "Verify the room ready notification information required in response [" + retrieve.getRoomReadyNotificationInfoRequired() + "].");

            TestReporter.softAssertTrue(!retrieve.getTravelStatus().isEmpty(), "Verify the travel status is in the response [" + retrieve.getTravelStatus() + "]-- ");
            TestReporter.assertAll();
        } else {

            TestReporter.softAssertEquals(guestfirstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + guestfirstName + "]");
            TestReporter.softAssertEquals(guestlastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + guestlastName + "]");
            try {
                TestReporter.softAssertEquals(guestPhone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + guestPhone + "]");
            } catch (XPathNotFoundException e) {
            }
            TestReporter.softAssertEquals(guestAddress, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + guestAddress + "]");
            TestReporter.softAssertEquals(guestEmail, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + guestEmail + "]");

            TestReporter.softAssertEquals(startPeriod, retrieve.getPeriodSD(), "Verify the period start date [" + retrieve.getPeriodSD() + "] matches the expected [" + startPeriod + "]");
            TestReporter.softAssertEquals(endPeriod, retrieve.getPeriodED(), "Verify the period end date [" + retrieve.getPeriodED() + "] matches the expected [" + endPeriod + "]");
            TestReporter.softAssertTrue(!retrieve.getPartyId().isEmpty(), "Verify the party id is in the response [" + retrieve.getPartyId() + "].");

            TestReporter.softAssertTrue(!retrieve.getPPFirstName().isEmpty(), "Verify the primary party first name is in response [" + retrieve.getPPFirstName() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPLastName().isEmpty(), "Verify the primary party last name  is in response [" + retrieve.getPPLastName() + "].");
            try {
                TestReporter.softAssertTrue(!retrieve.getPPPhone().isEmpty(), "Verify the primary party phone is in response [" + retrieve.getPPPhone() + "].");

            } catch (XPathNotFoundException e) {
            }
            TestReporter.softAssertTrue(!retrieve.getPPAddress().isEmpty(), "Verify the primary party address is in response [" + retrieve.getPPAddress() + "].");
            TestReporter.softAssertTrue(!retrieve.getPPEmail().isEmpty(), "Verify the primary party email is in response [" + retrieve.getPPEmail() + "].");

            TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoTP().isEmpty(), "Verify the room ready notification information travel plan id is in response [" + retrieve.getRoomReadyNotificationInfoTP() + "].");

            TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoRequired().isEmpty(), "Verify the room ready notification information required in response [" + retrieve.getRoomReadyNotificationInfoRequired() + "].");

            try {
                TestReporter.assertEquals(guestGuestId, retrieve.getGuestId("1"), "Verify the guest id [" + retrieve.getGuestId("1") + "] matches the expected [" + guestGuestId + "]");
            } catch (AssertionError e) {
                TestReporter.assertEquals(guestGuestId, retrieve.getGuestId("2"), "Verify the guest id [" + retrieve.getGuestId("2") + "] matches the expected [" + guestGuestId + "]2.");
            }

            TestReporter.softAssertTrue(!retrieve.getTravelStatus().isEmpty(), "Verify the travel status is in the response [" + retrieve.getTravelStatus() + "].-- ");

            TpsValidation(retrieve);
            TestReporter.assertAll();
        }
    }

    public void baseValidationDVC(Book book, Retrieve retrieve) {
        TestReporter.logStep("Base Validation for DVC");

        String guestfirstName = book.getPrimaryGuestFirstName();

        String guestlastName = book.getPrimaryGuestLastName();

        String guestPhone = book.getPrimaryPhoneNumber();

        String guestAddress = book.getResponseRoomDetailsGuestRefDtlsAddressLine1("1");

        String guestEmail = book.getResponseRoomDetailsGuestRefDtlsEmailAddress("1");

        String startPeriod = book.getResponseRoomDetailsResortPeriodStartDate("1");

        String endPeriod = book.getResponseRoomDetailsResortPeriodEndDate("1");

        String guestPartyId = book.getPartyId();

        String guestGuestId = book.getGuestId();

        if (second) {
            TestReporter.softAssertEquals(guestGuestId, retrieve.getGuestId("1"), "Verify the guest id [" + retrieve.getGuestId("1") + "] matches the expected [" + guestGuestId + "]");
            TestReporter.softAssertEquals(startPeriod, retrieve.getPeriodSD("2"), "Verify the period start date [" + retrieve.getPeriodSD("2") + "] matches the expected [" + startPeriod + "]");
            TestReporter.softAssertEquals(endPeriod, retrieve.getPeriodED("2"), "Verify the period end date [" + retrieve.getPeriodED("2") + "] matches the expected [" + endPeriod + "]");
        }
        if (first) {
            TestReporter.softAssertEquals(guestGuestId, retrieve.getGuestIdReferencesValue(), "Verify the guest id [" + retrieve.getGuestIdReferencesValue() + "] matches the expected [" + guestGuestId + "]");
            TestReporter.softAssertEquals(startPeriod, retrieve.getPeriodSD(), "Verify the period start date [" + retrieve.getPeriodSD() + "] matches the expected [" + startPeriod + "]");
            TestReporter.softAssertEquals(endPeriod, retrieve.getPeriodED(), "Verify the period end date [" + retrieve.getPeriodED() + "] matches the expected [" + endPeriod + "]");
            setFirst(false);
            setSecond(true);
        }
        if (!first && !second) {
            TestReporter.softAssertEquals(startPeriod, retrieve.getPeriodSD(), "Verify the period start date [" + retrieve.getPeriodSD() + "] matches the expected [" + startPeriod + "]");
            TestReporter.softAssertEquals(endPeriod, retrieve.getPeriodED(), "Verify the period end date [" + retrieve.getPeriodED() + "] matches the expected [" + endPeriod + "]");
        }

        TestReporter.softAssertEquals(guestfirstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + guestfirstName + "]");
        TestReporter.softAssertEquals(guestlastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + guestlastName + "]");
        TestReporter.softAssertEquals(guestPhone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + guestPhone + "]");
        TestReporter.softAssertEquals(guestAddress, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + guestAddress + "]");
        TestReporter.softAssertEquals(guestEmail, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + guestEmail + "]");

        TestReporter.softAssertTrue(!retrieve.getPartyId().isEmpty(), "Verify the party id is in the response [" + retrieve.getPartyId() + "].");

        TestReporter.softAssertTrue(!retrieve.getPPFirstName().isEmpty(), "Verify the primary party first name is in response [" + retrieve.getPPFirstName() + "].");
        TestReporter.softAssertTrue(!retrieve.getPPLastName().isEmpty(), "Verify the primary party last name  is in response [" + retrieve.getPPLastName() + "].");
        TestReporter.softAssertTrue(!retrieve.getPPPhone().isEmpty(), "Verify the primary party phone is in response [" + retrieve.getPPPhone() + "].");
        TestReporter.softAssertTrue(!retrieve.getPPAddress().isEmpty(), "Verify the primary party address is in response [" + retrieve.getPPAddress() + "].");
        TestReporter.softAssertTrue(!retrieve.getPPEmail().isEmpty(), "Verify the primary party email is in response [" + retrieve.getPPEmail() + "].");
        TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoTP().isEmpty(), "Verify the room ready notification information travel plan id is in response [" + retrieve.getPartyId() + "].");
        TestReporter.softAssertTrue(!retrieve.getRoomReadyNotificationInfoRequired().isEmpty(), "Verify the room ready notification information required in response [" + retrieve.getPartyId() + "].");
        TestReporter.softAssertTrue(!retrieve.getTravelStatus().isEmpty(), "Verify the travel status is in the response [" + retrieve.getTravelStatus() + "] ");
        TestReporter.assertAll();
    }

    public void TpsValidation(Retrieve retrieve) {
        TestReporter.logStep("TPS Validation");

        TestReporter.softAssertTrue(!retrieve.getAreadPeriodSD().isEmpty(), "Verify the area period start date is in response [" + retrieve.getAreadPeriodSD() + "].");

        TestReporter.softAssertTrue(!retrieve.getAreadPeriodED().isEmpty(), "Verify the area period end date is in response [" + retrieve.getAreadPeriodED() + "].");

        TestReporter.softAssertTrue(!retrieve.getCancellationNumber().isEmpty(), "Verify the cancellation number in the response [" + retrieve.getCancellationNumber() + "].");

        TestReporter.softAssertTrue(!retrieve.getCelebrationCount().isEmpty(), "Verify the celebration count is in the response[" + retrieve.getCelebrationCount() + "].");

        TestReporter.softAssertTrue(!retrieve.getGuaranteed().isEmpty(), "Verify the Guaranteed is in the response [" + retrieve.getGuaranteed() + "].");

        TestReporter.softAssertTrue(!retrieve.getPeriodSD().isEmpty(), "Verify the period start date is in response [" + retrieve.getPeriodSD() + "].");

        TestReporter.softAssertTrue(!retrieve.getPeriodED().isEmpty(), "Verify the period end date is in response [" + retrieve.getPeriodED() + "].");

        TestReporter.softAssertTrue(!retrieve.getStatus().isEmpty(), "Verify the status is in the response [" + retrieve.getStatus() + "].");

        TestReporter.softAssertTrue(!retrieve.getTPSfirstName().isEmpty(), "Verify the primary guest first name is in the response [" + retrieve.getTPSfirstName() + "].");
        TestReporter.softAssertTrue(!retrieve.getTPSLasttName().isEmpty(), "Verify the primary guest last name is in the response [" + retrieve.getTPSLasttName() + "].");
        TestReporter.softAssertTrue(!retrieve.getTPSaddress().isEmpty(), "Verify the primary guest address is in the response [" + retrieve.getTPSaddress() + "].");
        TestReporter.softAssertTrue(!retrieve.getTPSemail().isEmpty(), "Verify the primary guest email is in the response [" + retrieve.getTPSemail() + "].");
        TestReporter.softAssertTrue(!retrieve.getTPSPartyId().isEmpty(), "Verify the primary guest party id is in the response [" + retrieve.getTPSPartyId() + "].");
        TestReporter.softAssertTrue(!retrieve.getTPSGuestId().isEmpty(), "Verify the primary guest guest id is in the response [" + retrieve.getTPSGuestId() + "].");

        if (retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/confirmationDetails") >= 1) {

            TestReporter.softAssertTrue(!retrieve.getConfirmDetailId().isEmpty(), "Verify the confirmation detail id is in the response [" + retrieve.getConfirmDetailId() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmIndicator().isEmpty(), "Verify the confiramtion indicator is in the response [" + retrieve.getConfirmIndicator() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmType().isEmpty(), "Verify the confirmation type is in the response [" + retrieve.getConfirmType() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmContactName().isEmpty(), "Verify the confirmation contact name is in the response [" + retrieve.getConfirmContactName() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmDefaultConfirmIndicator().isEmpty(), "Verify the confirmation default confirmation indicator is in the response [" + retrieve.getConfirmDefaultConfirmIndicator() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmIndividual().isEmpty(), "Verify the confirmation individual is in the response [" + retrieve.getConfirmIndividual() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmJDOSequenceNumber().isEmpty(), "Verify the confirmation jdo sequence number is in the response [" + retrieve.getConfirmJDOSequenceNumber() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmLocatorId().isEmpty(), "Verify the confirmation locator id is in the response [" + retrieve.getConfirmLocatorId() + "].");

            TestReporter.softAssertTrue(!retrieve.getConfirmPartyId().isEmpty(), "Verify the confirmation party id is in the response [" + retrieve.getConfirmPartyId() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmGDPartyId().isEmpty(), "Verify the confirmation guest details party id is in the response [" + retrieve.getConfirmGDPartyId() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmGDDoNotMailIndicator().isEmpty(), "Verify the confirmation guest details do not mail indicator is in the response [" + retrieve.getConfirmGDDoNotMailIndicator() + "].");

            TestReporter.softAssertTrue(!retrieve.getConfirmGDDoNotPhoneIndictor().isEmpty(), "Verify the  confirmation guest details do not phone indicatoris in the response [" + retrieve.getConfirmGDDoNotPhoneIndictor() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmGDDclGuestId().isEmpty(), "Verify the  confirmation guest details dcl guest id is in the response [" + retrieve.getConfirmGDDclGuestId() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmGDGuestId().isEmpty(), "Verify the  confirmation guest details guest id is in the response [" + retrieve.getConfirmGDGuestId() + "].");
            TestReporter.softAssertTrue(!retrieve.getConfirmGDActive().isEmpty(), "Verify the confirmation guest details active is in the response [" + retrieve.getConfirmGDActive() + "].");

        }

        TestReporter.assertTrue(!retrieve.getTravelPlanId().isEmpty(), "Verify the travel Plan id is in the response[" + retrieve.getTravelPlanId() + "].");

        TestReporter.softAssertTrue(!retrieve.getTravelPlanSegmentId().isEmpty(), "Verify the travel Plan segement id in the response [" + retrieve.getTravelPlanSegmentId() + "].");

        TestReporter.softAssertTrue(!retrieve.getBundleDetailPresent().isEmpty(), "Verify the bundle detail present is in the response[" + retrieve.getBundleDetailPresent() + "].");

        TestReporter.softAssertTrue(!retrieve.getOnsiteMessagingEnabled().isEmpty(), "Verify the onsite messaging enabled is in the response [" + retrieve.getOnsiteMessagingEnabled() + "].");

        tcgValidation(retrieve);

        TestReporter.assertAll();

    }

    public void tcgValidation(Retrieve retrieve) {
        TestReporter.logStep("TCG Validation");
        Sleeper.sleep(1000);
        int NumberOfTravelPlanSegments = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments");
        int NumberOfGuestReferences = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[1]/componentGroupings[1]/accommodation/guestReferences");
        int NumberOfComponentGroupings = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings");
        if (NumberOfTravelPlanSegments == 1) {
            if (NumberOfGuestReferences >= 2) {
                TestReporter.assertTrue(NumberOfGuestReferences >= 2, "The number of guest references nodes are [" + NumberOfGuestReferences + "]");

                for (int j = 1; j <= NumberOfComponentGroupings; j++) {
                    for (int i = 1; i <= NumberOfGuestReferences; i++) {
                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesfirstName(j, i).isEmpty(), "Verify the guest references at node [" + i + "] first name    is in response [" + retrieve.getGuestReferencesfirstName(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesLastName(j, i).isEmpty(), "Verify the guest references last name at node [" + i + "] is in response [" + retrieve.getGuestReferencesLastName(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesphone(j, i).isEmpty(), "Verify the guest references  phone at node  [" + i + "] is in the response [" + retrieve.getGuestReferencesphone(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesaddress(j, i).isEmpty(), "Verify the guest references address at node [" + i + "] is in the response[" + retrieve.getGuestReferencesaddress(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesemail(j, i).isEmpty(), "Verify the  guest references  email  at node [" + i + "] is  in the response [" + retrieve.getGuestReferencesemail(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesPartyId(j, i).isEmpty(), "Verify the guest references  party id at node [" + i + "] is in the response[" + retrieve.getGuestReferencesPartyId(j, i) + "].");

                        TestReporter.softAssertTrue(!retrieve.getGuestReferencesGuestId(j, i).isEmpty(), "Verify the  guest references guest id at node [" + i + "] is in the response [" + retrieve.getGuestReferencesGuestId(j, i) + "].");

                        Sleeper.sleep(2000);
                        TestReporter.assertAll();

                    }
                }
            }
        } else {

            for (int k = 1; k <= NumberOfTravelPlanSegments; k++) {
                String firstName = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/firstName");
                String lastName = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/lastName");
                String address = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/addressDetails/addressLine1");
                String phone = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/phoneDetails/number");
                String email = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/emailDetails/address");
                String partyId = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/partyId");
                String guestId = retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments[" + k + "]/componentGroupings/accommodation/guestReferences/guest/guestId");

                TestReporter.softAssertTrue(!firstName.isEmpty(), "Verify the guest references at node [" + k + "] first name    is in response [" + firstName + "].");

                TestReporter.softAssertTrue(!lastName.isEmpty(), "Verify the guest references last name at node [" + k + "] is in response [" + lastName + "].");

                TestReporter.softAssertTrue(!phone.isEmpty(), "Verify the guest references  phone at node  [" + k + "] is in the response [" + phone + "].");

                TestReporter.softAssertTrue(!address.isEmpty(), "Verify the guest references address at node [" + k + "] is in the response[" + address + "].");

                TestReporter.softAssertTrue(!email.isEmpty(), "Verify the  guest references  email  at node [" + k + "] is  in the response [" + email + "].");

                TestReporter.softAssertTrue(!partyId.isEmpty(), "Verify the guest references  party id at node [" + k + "] is in the response[" + partyId + "].");

                TestReporter.softAssertTrue(!guestId.isEmpty(), "Verify the  guest references guest id at node [" + k + "] is in the response [" + guestId + "].");
                TestReporter.assertAll();

            }

        }

        TestReporter.softAssertTrue(!retrieve.getAccommBookDate().isEmpty(), "Verify the book date is in the response[" + retrieve.getAccommBookDate() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommComponentId().isEmpty(), "Verify the component id is in the response [" + retrieve.getAccommComponentId() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommFacilityId().isEmpty(), "Verify the facility id is in the response[" + retrieve.getAccommFacilityId() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommGroupTeamId().isEmpty(), "Verify the group team id is in the response [" + retrieve.getAccommGroupTeamId() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommPackageCode().isEmpty(), "Verify the package code is in the response[" + retrieve.getAccommPackageCode() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommPeriodSD().isEmpty(), "Verify the period start date is in the response [" + retrieve.getAccommPeriodSD() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommPeriodED().isEmpty(), "Verify the period end date is in the response[" + retrieve.getAccommPeriodED() + "].");

        if (validateProfile) {
            TestReporter.softAssertTrue(!retrieve.getAccommProfiles().isEmpty(), "Verify the profiles is in the response [" + retrieve.getAccommProfiles() + "].");
        }

        TestReporter.softAssertTrue(!retrieve.getAccommRSR().isEmpty(), "Verify the RSR is in the response[" + retrieve.getAccommRSR() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommWholesaler().isEmpty(), "Verify the wholesaler is in the response [" + retrieve.getAccommWholesaler() + "].");

        if (retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/rates") >= 1) {

            TestReporter.softAssertTrue(!retrieve.getAccommRates().isEmpty(), "Verify the rates is in the response[" + retrieve.getAccommRates() + "].");
        }
        TestReporter.softAssertTrue(!retrieve.getAccommResortCode().isEmpty(), "Verify the resort code is in the response [" + retrieve.getAccommResortCode() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommRoomTypeCode().isEmpty(), "Verify the room type code is in the response[" + retrieve.getAccommRoomTypeCode() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommStatus().isEmpty(), "Verify the status is in the response [" + retrieve.getAccommStatus() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommShared().isEmpty(), "Verify the shared is in the response[" + retrieve.getAccommShared() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommReservationType().isEmpty(), "Verify the reservation type is in the response [" + retrieve.getAccommReservationType() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommRoomOnly().isEmpty(), "Verify the room only is in the response [" + retrieve.getAccommRoomOnly() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommSpecialNeedsRequested().isEmpty(), "Verify the special needs requested is in the response[" + retrieve.getAccommSpecialNeedsRequested() + "].");

        TestReporter.softAssertTrue(!retrieve.getAccommTravelComponentGroupingId().isEmpty(), "Verify the travel component grouping id is in the response [" + retrieve.getAccommTravelComponentGroupingId() + "].");

    }

    public void ticketValidation(Retrieve retrieve) {
        TestReporter.logStep("Ticket Validation");

        TestReporter.softAssertTrue(!retrieve.getTicketStatus().isEmpty(), "Verify the ticket details status is in response [" + retrieve.getTicketStatus() + "].");

        TestReporter.softAssertTrue(!retrieve.getTicketGuestId().isEmpty(), "Verify the ticket details guest id is in response [" + retrieve.getTicketGuestId() + "].");

        TestReporter.softAssertTrue(!retrieve.getTicketAgeType().isEmpty(), "Verify the ticket details age type is in the response [" + retrieve.getTicketAgeType() + "].");

        TestReporter.softAssertTrue(!retrieve.getTicketComponentId().isEmpty(), "Verify the ticket details component id is in the response[" + retrieve.getTicketComponentId() + "].");

        TestReporter.softAssertTrue(!retrieve.getTicketCode().isEmpty(), "Verify the ticket details ticket code is in the response [" + retrieve.getTicketCode() + "].");
        TestReporter.assertAll();
    }

    public void sqlGuest_PartyId(String env, String tp) {

        String sql = "select a.TXN_PTY_ID GUEST_ID, b.TXN_PTY_EXTNL_REF_VAL PARTY_ID"
                + " from res_mgmt.tp_pty a"
                + " join guest.TXN_PTY_EXTNL_REF b on a.TXN_PTY_ID = b.TXN_PTY_ID"
                + " where a.tp_id = '" + tp + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.softAssertTrue(!rs.getValue("GUEST_ID", 1).isEmpty(), "The guest id  is [" + rs.getValue("GUEST_ID", 1) + "]");

        TestReporter.softAssertTrue(!rs.getValue("PARTY_ID", 1).isEmpty(), "The party id  is [" + rs.getValue("PARTY_ID", 1) + "]");
        TestReporter.assertAll();
    }

    public void sqlConfirmationDetails(String env, String tps, Retrieve retrieve) {
        String sql = "select *"
                + " from res_mgmt.tps_cnfirm_rcpnt a"
                + " where a.tps_id = '" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

    }

    public void sqlAdmissionComponentDetails(String env, String tcg) {
        String sql = "select b.ADM_TC_ID, b.ATS_TKT_CD"
                + " from res_mgmt.tc a"
                + " join res_mgmt.adm_cmpnt b on a.tc_id = b.adm_tc_id"
                + " where a.tc_grp_nb = '" + tcg + "'"
                + " and a.tc_typ_nm = 'AdmissionComponent'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.softAssertTrue(!rs.getValue("ADM_TC_ID", 1).isEmpty(), "The admission tc id  is [" + rs.getValue("ADM_TC_ID", 1) + "]");

        TestReporter.softAssertTrue(!rs.getValue("ATS_TKT_CD", 1).isEmpty(), "The ticket cd  is [" + rs.getValue("ATS_TKT_CD", 1) + "]");
        TestReporter.assertAll();
    }

    public void sqlTPSDetails(String env, String tps, Retrieve retrieve) {
        String sql = "select *"
                + " from res_mgmt.tps a"
                + " where a.tps_id ='" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.softAssertTrue(rs.getValue("TPS_ID", 1).equals(retrieve.getTravelPlanSegmentId()), "The travel plan segment id  matches [" + rs.getValue("TPS_ID", 1) + "] the response [" + retrieve.getTravelPlanSegmentId() + "].");

        TestReporter.softAssertTrue(rs.getValue("TP_ID", 1).equals(retrieve.getTravelPlanId()), "The travel Plan id  matches [" + rs.getValue("TP_ID", 1) + "] the response [" + retrieve.getTravelPlanId() + "].");

        TestReporter.softAssertTrue(!rs.getValue("TRVL_STS_NM", 1).isEmpty(), "The travel status name is [" + rs.getValue("TRVL_STS_NM", 1) + "].");
        TestReporter.assertAll();
    }

    public void sqlTPSConfirmationDetails(String env, String tps, Retrieve retrieve) {
        String sql = "SELECT *"
                + " FROM RES_MGMT.TPS_CNFIRM_RCPNT a"
                + " LEFT OUTER JOIN GUEST.TXN_PTY_EML_LCTR c ON a.LCTR_ID = c.TXN_PTY_EML_LCTR_ID"
                + " WHERE TPS_ID = '" + tps + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.softAssertTrue(!rs.getValue("TPS_ID", 1).isEmpty(), "The travel plan segment id is [" + rs.getValue("TPS_ID", 1) + "]");
        TestReporter.softAssertTrue(!rs.getValue("TPS_CNFIRM_RCPNT_ID", 1).isEmpty(), "The tps confirmation rcpnt id is [" + rs.getValue("TPS_CNFIRM_RCPNT_ID", 1) + "]");
        TestReporter.assertAll();
    }

    public void sqlPrimaryTPLinkedDVCReservation(String env, String tp, Retrieve retrieve) {
        String sql = "select *"
                + " from res_mgmt.tp a"
                + " join res_mgmt.tps b on a.tp_id = b.tp_id"
                + " where a.tp_id = '" + tp + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        // rs.print();
        if (rs.getRowCount() == 0) {
            throw new AutomationException("No records were returned. SQL: [" + sql + "].");
        }

        TestReporter.softAssertTrue(!rs.getValue("TPS_CNFIRM_RCPNT_ID", 1).isEmpty(), "The tps confirmation rcpnt id is [" + rs.getValue("TPS_CNFIRM_RCPNT_ID", 1) + "]");
        TestReporter.softAssertTrue(!rs.getValue("TPS_ID", 1).isEmpty(), "The travel plan segment id is [" + rs.getValue("TPS_ID", 1) + "]");
        TestReporter.assertAll();
    }

    public void multiAddressCheck(ReplaceAllForTravelPlanSegment book, Retrieve retrieve, HouseHold hh) {
        TestReporter.logStep("Validating Multiple Address Returned");

        TestReporter.softAssertTrue(retrieve.getTPAddressDetailsCount() == 2, "Verify two TP Address Detail blocks are returned.  Count: [" + retrieve.getTPAddressDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getPrimaryPtyAddressDetailsCount() == 2, "Verify two primary party Address Detail blocks are returned.  Count: [" + retrieve.getPrimaryPtyAddressDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getCompGroupingsAddressDetailsCount() == 2, "Verify two Component Groupings Address Detail blocks are returned.  Count: [" + retrieve.getCompGroupingsAddressDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getTPSAddressDetailsCount() == 2, "Verify two TPS Address Detail blocks are returned.  Count: [" + retrieve.getTPSAddressDetailsCount() + "]");

        String firstAddressLine1 = hh.getAllGuests().get(0).primaryAddress().getAddress1();
        String secondAddressLine1 = "5633 Siracusa Ln";
        String firstAddressCity = hh.getAllGuests().get(0).primaryAddress().getCity();
        String secondAddressCity = "Sanford";
        String firstAddressCountry = "USA";
        String secondAddressCountry = "USA";
        String firstAddressPostalCode = hh.getAllGuests().get(0).primaryAddress().getZipCode();
        String secondAddressPostalCode = "32771-5462";
        String firstAddressState = hh.getAllGuests().get(0).primaryAddress().getStateAbbv();
        String secondAddressState = "FL";
        String firstAddressRegionName = hh.getAllGuests().get(0).primaryAddress().getState();
        String secondAddressRegionName = "Florida";

        // For Tp Address Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsPrimary("1"), "true", "Verify the first TP primary returned [" + retrieve.getTPAddressDetailsPrimary("1") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsPrimary("2"), "true", "Verify the second TP primary returned [" + retrieve.getTPAddressDetailsPrimary("2") + "] is as expected "
                + "[true]");
        /*
         * TestReporter.softAssertTrue(!retrieve.getTPAddressDetailsAddressLine1(firstAddressLine1).isEmpty(), "Verify the first TP address1 returned is as expected "
         * + "[" + firstAddressLine1 + "]");
         * TestReporter.softAssertTrue(!retrieve.getTPAddressDetailsAddressLine1(secondAddressLine1).isEmpty(), "Verify the second TP address1 returned is as expected "
         * + "[" + secondAddressLine1 + "]");
         */
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsAddressLine1("1"), firstAddressLine1, "Verify the first TP address returned [" + retrieve.getTPAddressDetailsAddressLine1("1") + "] is as expected "
                + "[" + firstAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsAddressLine1("2"), secondAddressLine1, "Verify the second TP address returned [" + retrieve.getTPAddressDetailsAddressLine1("2") + "] is as expected "
                + "[" + secondAddressCity + "]");

        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsCity("1"), firstAddressCity, "Verify the first TP address city returned [" + retrieve.getTPAddressDetailsCity("1") + "] is as expected "
                + "[" + firstAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsCity("2"), secondAddressCity, "Verify the second TP address city returned [" + retrieve.getTPAddressDetailsCity("2") + "] is as expected "
                + "[" + secondAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsCountry("1"), firstAddressCountry, "Verify the first TP address country returned [" + retrieve.getTPAddressDetailsCountry("1") + "] is as expected "
                + "[" + firstAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsCountry("2"), secondAddressCountry, "Verify the second TP address country returned [" + retrieve.getTPAddressDetailsCountry("2") + "] is as expected "
                + "[" + secondAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsPostalCode("1"), firstAddressPostalCode, "Verify the first TP address postal code returned [" + retrieve.getTPAddressDetailsPostalCode("1") + "] is as expected "
                + "[" + firstAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsPostalCode("2"), secondAddressPostalCode, "Verify the second TP address postal code returned [" + retrieve.getTPAddressDetailsPostalCode("2") + "] is as expected "
                + "[" + secondAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsState("1"), firstAddressState, "Verify the first TP address state returned [" + retrieve.getTPAddressDetailsState("1") + "] is as expected "
                + "[" + firstAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsState("2"), secondAddressState, "Verify the second TP address state returned [" + retrieve.getTPAddressDetailsState("2") + "] is as expected "
                + "[" + secondAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsRegion("1"), firstAddressRegionName, "Verify the first TP address region returned [" + retrieve.getTPAddressDetailsRegion("1") + "] is as expected "
                + "[" + firstAddressRegionName + "]");
        TestReporter.softAssertEquals(retrieve.getTPAddressDetailsRegion("2"), secondAddressRegionName, "Verify the second TP address region returned [" + retrieve.getTPAddressDetailsRegion("2") + "] is as expected "
                + "[" + secondAddressRegionName + "]");

        // For Primary Party Address Details Blocks
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsPrimary("1"), "true", "Verify the first PP primary returned [" + retrieve.getPPAddressDetailsPrimary("1") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsPrimary("2"), "true", "Verify the second PP primary returned [" + retrieve.getPPAddressDetailsPrimary("2") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsAddressLine1("1"), firstAddressLine1, "Verify the first PP address1 returned [" + retrieve.getPPAddressDetailsAddressLine1("1") + "] is as expected "
                + "[" + firstAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsAddressLine1("2"), secondAddressLine1, "Verify the second PP address1 returned [" + retrieve.getPPAddressDetailsAddressLine1("2") + "] is as expected "
                + "[" + secondAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsCity("1"), firstAddressCity, "Verify the first PP address city returned [" + retrieve.getPPAddressDetailsCity("1") + "] is as expected "
                + "[" + firstAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsCity("2"), secondAddressCity, "Verify the second PP address city returned [" + retrieve.getPPAddressDetailsCity("2") + "] is as expected "
                + "[" + secondAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsCountry("1"), firstAddressCountry, "Verify the first PP address country returned [" + retrieve.getPPAddressDetailsCountry("1") + "] is as expected "
                + "[" + firstAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsCountry("2"), secondAddressCountry, "Verify the second PP address country returned [" + retrieve.getPPAddressDetailsCountry("2") + "] is as expected "
                + "[" + secondAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsPostalCode("1"), firstAddressPostalCode, "Verify the first PP address postal code returned [" + retrieve.getPPAddressDetailsPostalCode("1") + "] is as expected "
                + "[" + firstAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsPostalCode("2"), secondAddressPostalCode, "Verify the second PP address postal code returned [" + retrieve.getPPAddressDetailsPostalCode("2") + "] is as expected "
                + "[" + secondAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsState("1"), firstAddressState, "Verify the first PP address state returned [" + retrieve.getPPAddressDetailsState("1") + "] is as expected "
                + "[" + firstAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsState("2"), secondAddressState, "Verify the second PP address state returned [" + retrieve.getPPAddressDetailsState("2") + "] is as expected "
                + "[" + secondAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsRegion("1"), firstAddressRegionName, "Verify the first PP address region returned [" + retrieve.getPPAddressDetailsRegion("1") + "] is as expected "
                + "[" + firstAddressRegionName + "]");
        TestReporter.softAssertEquals(retrieve.getPPAddressDetailsRegion("2"), secondAddressRegionName, "Verify the second PP address region returned [" + retrieve.getPPAddressDetailsRegion("2") + "] is as expected "
                + "[" + secondAddressRegionName + "]");

        // For Component Groupings Address Details Blocks
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsPrimary("1"), "true", "Verify the first TCG primary returned [" + retrieve.getTCGAddressDetailsPrimary("1") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsPrimary("2"), "true", "Verify the second TCG primary returned [" + retrieve.getTCGAddressDetailsPrimary("2") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsAddressLine1("1"), firstAddressLine1, "Verify the first TCG address1 returned [" + retrieve.getTCGAddressDetailsAddressLine1("1") + "] is as expected "
                + "[" + firstAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsAddressLine1("2"), secondAddressLine1, "Verify the second TCG address1 returned [" + retrieve.getTCGAddressDetailsAddressLine1("2") + "] is as expected "
                + "[" + secondAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsCity("1"), firstAddressCity, "Verify the first TCG address city returned [" + retrieve.getTCGAddressDetailsCity("1") + "] is as expected "
                + "[" + firstAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsCity("2"), secondAddressCity, "Verify the second TCG address city returned [" + retrieve.getTCGAddressDetailsCity("2") + "] is as expected "
                + "[" + secondAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsCountry("1"), firstAddressCountry, "Verify the first TCG address country returned [" + retrieve.getTCGAddressDetailsCountry("1") + "] is as expected "
                + "[" + firstAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsCountry("2"), secondAddressCountry, "Verify the second TCG address country returned [" + retrieve.getTCGAddressDetailsCountry("2") + "] is as expected "
                + "[" + secondAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsPostalCode("1"), firstAddressPostalCode, "Verify the first TCG address postal code returned [" + retrieve.getTCGAddressDetailsPostalCode("1") + "] is as expected "
                + "[" + firstAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsPostalCode("2"), secondAddressPostalCode, "Verify the second TCG address postal code returned [" + retrieve.getTCGAddressDetailsPostalCode("2") + "] is as expected "
                + "[" + secondAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsState("1"), firstAddressState, "Verify the first TCG address state returned [" + retrieve.getTCGAddressDetailsState("1") + "] is as expected "
                + "[" + firstAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsState("2"), secondAddressState, "Verify the second TCG address state returned [" + retrieve.getTCGAddressDetailsState("2") + "] is as expected "
                + "[" + secondAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsRegion("1"), firstAddressRegionName, "Verify the first TCG address region returned [" + retrieve.getTCGAddressDetailsRegion("1") + "] is as expected "
                + "[" + firstAddressRegionName + "]");
        TestReporter.softAssertEquals(retrieve.getTCGAddressDetailsRegion("2"), secondAddressRegionName, "Verify the second TCG address region returned [" + retrieve.getTCGAddressDetailsRegion("2") + "] is as expected "
                + "[" + secondAddressRegionName + "]");

        // For TPS Address Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsPrimary("1"), "true", "Verify the first TPS primary returned [" + retrieve.getTPSAddressDetailsPrimary("1") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsPrimary("2"), "true", "Verify the second TPS primary returned [" + retrieve.getTPSAddressDetailsPrimary("2") + "] is as expected "
                + "[true]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsAddressLine1("1"), firstAddressLine1, "Verify the first TPS address1 returned [" + retrieve.getTPSAddressDetailsAddressLine1("1") + "] is as expected "
                + "[" + firstAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsAddressLine1("2"), secondAddressLine1, "Verify the second TPS address1 returned [" + retrieve.getTPSAddressDetailsAddressLine1("2") + "] is as expected "
                + "[" + secondAddressLine1 + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsCity("1"), firstAddressCity, "Verify the first TPS address city returned [" + retrieve.getTPSAddressDetailsCity("1") + "] is as expected "
                + "[" + firstAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsCity("2"), secondAddressCity, "Verify the second TPS address city returned [" + retrieve.getTPSAddressDetailsCity("2") + "] is as expected "
                + "[" + secondAddressCity + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsCountry("1"), firstAddressCountry, "Verify the first TPS address country returned [" + retrieve.getTPSAddressDetailsCountry("1") + "] is as expected "
                + "[" + firstAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsCountry("2"), secondAddressCountry, "Verify the second TPS address country returned [" + retrieve.getTPSAddressDetailsCountry("2") + "] is as expected "
                + "[" + secondAddressCountry + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsPostalCode("1"), firstAddressPostalCode, "Verify the first TPS address postal code returned [" + retrieve.getTPSAddressDetailsPostalCode("1") + "] is as expected "
                + "[" + firstAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsPostalCode("2"), secondAddressPostalCode, "Verify the second TPS address postal code returned [" + retrieve.getTPSAddressDetailsPostalCode("2") + "] is as expected "
                + "[" + secondAddressPostalCode + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsState("1"), firstAddressState, "Verify the first TPS address state returned [" + retrieve.getTPSAddressDetailsState("1") + "] is as expected "
                + "[" + firstAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsState("2"), secondAddressState, "Verify the second TPS address state returned [" + retrieve.getTPSAddressDetailsState("2") + "] is as expected "
                + "[" + secondAddressState + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsRegion("1"), firstAddressRegionName, "Verify the first TPS address region returned [" + retrieve.getTPSAddressDetailsRegion("1") + "] is as expected "
                + "[" + firstAddressRegionName + "]");
        TestReporter.softAssertEquals(retrieve.getTPSAddressDetailsRegion("2"), secondAddressRegionName, "Verify the second TPS address region returned [" + retrieve.getTPSAddressDetailsRegion("2") + "] is as expected "
                + "[" + secondAddressRegionName + "]");

        TestReporter.assertAll();

    }

    public void multiEmailCheck(ReplaceAllForTravelPlanSegment book, Retrieve retrieve, HouseHold hh) {
        TestReporter.logStep("Validating Multiple Email Details Returned");

        TestReporter.softAssertTrue(retrieve.getTPEmailDetailsCount() == 2, "Verify two TP Email Detail blocks are returned.  Count: [" + retrieve.getTPEmailDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getPPEmailDetailsCount() == 2, "Verify two primary party Email Detail blocks are returned.  Count: [" + retrieve.getPPEmailDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getTCGEmailDetailsCount() == 2, "Verify two Component Groupings Email Detail blocks are returned.  Count: [" + retrieve.getTCGEmailDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getTPSEmailDetailsCount() == 2, "Verify two TPS Email Detail blocks are returned.  Count: [" + retrieve.getTPSEmailDetailsCount() + "]");

        String firstEmail = hh.getAllGuests().get(0).primaryEmail().getEmail();
        String secondEmail = "test@testemail.com";
        String firstEmailIsPrimary = String.valueOf(hh.getAllGuests().get(0).primaryEmail().isPrimary());
        String secondEmailIsPrimary = "true";

        // For Tp Email Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPEmailDetailsPrimary("1"), firstEmailIsPrimary, "Verify the first TP email primary returned [" + retrieve.getTPEmailDetailsPrimary("1") + "] is as expected "
                + "[" + firstEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPEmailDetailsPrimary("2"), secondEmailIsPrimary, "Verify the second TP email primary returned [" + retrieve.getTPEmailDetailsPrimary("2") + "] is as expected "
                + "[" + secondEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPEmailDetailsAddress("1"), firstEmail, "Verify the first TP email address returned [" + retrieve.getTPEmailDetailsAddress("1") + "] is as expected "
                + "[" + firstEmail + "]");
        TestReporter.softAssertEquals(retrieve.getTPEmailDetailsAddress("2"), secondEmail, "Verify the second TP email address returned [" + retrieve.getTPEmailDetailsAddress("2") + "] is as expected "
                + "[" + secondEmail + "]");

        // For PP Email Details Blocks
        TestReporter.softAssertEquals(retrieve.getPPEmailDetailsPrimary("1"), firstEmailIsPrimary, "Verify the first PP email primary returned [" + retrieve.getPPEmailDetailsPrimary("1") + "] is as expected "
                + "[" + firstEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getPPEmailDetailsPrimary("2"), secondEmailIsPrimary, "Verify the second PP email primary returned [" + retrieve.getPPEmailDetailsPrimary("2") + "] is as expected "
                + "[" + secondEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getPPEmailDetailsAddress("1"), firstEmail, "Verify the first PP email address returned [" + retrieve.getPPEmailDetailsAddress("1") + "] is as expected "
                + "[" + firstEmail + "]");
        TestReporter.softAssertEquals(retrieve.getPPEmailDetailsAddress("2"), secondEmail, "Verify the second PP email address returned [" + retrieve.getPPEmailDetailsAddress("2") + "] is as expected "
                + "[" + secondEmail + "]");

        // For TCG Email Details Blocks
        TestReporter.softAssertEquals(retrieve.getTCGEmailDetailsPrimary("1"), firstEmailIsPrimary, "Verify the first TCG email primary returned [" + retrieve.getTCGEmailDetailsPrimary("1") + "] is as expected "
                + "[" + firstEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTCGEmailDetailsPrimary("2"), secondEmailIsPrimary, "Verify the second TCG email primary returned [" + retrieve.getTCGEmailDetailsPrimary("2") + "] is as expected "
                + "[" + secondEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTCGEmailDetailsAddress("1"), firstEmail, "Verify the first TCG email address returned [" + retrieve.getTCGEmailDetailsAddress("1") + "] is as expected "
                + "[" + firstEmail + "]");
        TestReporter.softAssertEquals(retrieve.getTCGEmailDetailsAddress("2"), secondEmail, "Verify the second TCG email address returned [" + retrieve.getTCGEmailDetailsAddress("2") + "] is as expected "
                + "[" + secondEmail + "]");

        // For TPS Email Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPSEmailDetailsPrimary("1"), firstEmailIsPrimary, "Verify the first TPS email primary returned [" + retrieve.getTPSEmailDetailsPrimary("1") + "] is as expected "
                + "[" + firstEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPSEmailDetailsPrimary("2"), secondEmailIsPrimary, "Verify the second TPS email primary returned [" + retrieve.getTPSEmailDetailsPrimary("2") + "] is as expected "
                + "[" + secondEmailIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPSEmailDetailsAddress("1"), firstEmail, "Verify the first TPS email address returned [" + retrieve.getTPSEmailDetailsAddress("1") + "] is as expected "
                + "[" + firstEmail + "]");
        TestReporter.softAssertEquals(retrieve.getTPSEmailDetailsAddress("2"), secondEmail, "Verify the second TPS email address returned [" + retrieve.getTPSEmailDetailsAddress("2") + "] is as expected "
                + "[" + secondEmail + "]");

        TestReporter.assertAll();

    }

    public void multiPhoneCheck(ReplaceAllForTravelPlanSegment book, Retrieve retrieve, HouseHold hh) {
        TestReporter.logStep("Validating Multiple Phone Details Returned");

        TestReporter.softAssertTrue(retrieve.getTPPhoneDetailsCount() == 2, "Verify two TP Phone Detail blocks are returned.  Count: [" + retrieve.getTPPhoneDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getPPPhoneDetailsCount() == 2, "Verify two primary party Phone Detail blocks are returned.  Count: [" + retrieve.getPPPhoneDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getTCGPhoneDetailsCount() == 2, "Verify two Component Groupings Phone Detail blocks are returned.  Count: [" + retrieve.getTCGPhoneDetailsCount() + "]");
        TestReporter.softAssertTrue(retrieve.getTPSPhoneDetailsCount() == 2, "Verify two TPS Phone Detail blocks are returned.  Count: [" + retrieve.getTPSPhoneDetailsCount() + "]");

        String firstNumber = hh.getAllGuests().get(0).primaryPhone().getNumber();
        String secondNumber = "9089091111";
        String firstPhoneIsPrimary = String.valueOf(hh.getAllGuests().get(0).primaryPhone().isPrimary());
        String secondPhoneIsPrimary = "false";

        // For Tp Phone Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPPhoneDetailsPrimary("1"), firstPhoneIsPrimary, "Verify the first TP phone primary returned [" + retrieve.getTPPhoneDetailsPrimary("1") + "] is as expected "
                + "[" + firstPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPPhoneDetailsPrimary("2"), secondPhoneIsPrimary, "Verify the second TP phone primary returned [" + retrieve.getTPPhoneDetailsPrimary("2") + "] is as expected "
                + "[" + secondPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPPhoneDetailsNumber("1"), firstNumber, "Verify the first TP phone number returned [" + retrieve.getTPPhoneDetailsNumber("1") + "] is as expected "
                + "[" + firstNumber + "]");
        TestReporter.softAssertEquals(retrieve.getTPPhoneDetailsNumber("2"), secondNumber, "Verify the second TP phone number returned [" + retrieve.getTPPhoneDetailsNumber("2") + "] is as expected "
                + "[" + secondNumber + "]");

        // For Primary Party Phone Details Blocks
        TestReporter.softAssertEquals(retrieve.getPPPhoneDetailsPrimary("1"), firstPhoneIsPrimary, "Verify the first PP phone primary returned [" + retrieve.getPPPhoneDetailsPrimary("1") + "] is as expected "
                + "[" + firstPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getPPPhoneDetailsPrimary("2"), secondPhoneIsPrimary, "Verify the second PP phone primary returned [" + retrieve.getPPPhoneDetailsPrimary("2") + "] is as expected "
                + "[" + secondPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getPPPhoneDetailsNumber("1"), firstNumber, "Verify the first PP phone number returned [" + retrieve.getPPPhoneDetailsNumber("1") + "] is as expected "
                + "[" + firstNumber + "]");
        TestReporter.softAssertEquals(retrieve.getPPPhoneDetailsNumber("2"), secondNumber, "Verify the second PP phone number returned [" + retrieve.getPPPhoneDetailsNumber("2") + "] is as expected "
                + "[" + secondNumber + "]");

        // For TCG Phone Details Blocks
        TestReporter.softAssertEquals(retrieve.getTCGPhoneDetailsPrimary("1"), firstPhoneIsPrimary, "Verify the first TCG phone primary returned [" + retrieve.getTCGPhoneDetailsPrimary("1") + "] is as expected "
                + "[" + firstPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTCGPhoneDetailsPrimary("2"), secondPhoneIsPrimary, "Verify the second TCG phone primary returned [" + retrieve.getTCGPhoneDetailsPrimary("2") + "] is as expected "
                + "[" + secondPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTCGPhoneDetailsNumber("1"), firstNumber, "Verify the first TCG phone number returned [" + retrieve.getTCGPhoneDetailsNumber("1") + "] is as expected "
                + "[" + firstNumber + "]");
        TestReporter.softAssertEquals(retrieve.getTCGPhoneDetailsNumber("2"), secondNumber, "Verify the second TCG phone number returned [" + retrieve.getTCGPhoneDetailsNumber("2") + "] is as expected "
                + "[" + secondNumber + "]");

        // For TPS Phone Details Blocks
        TestReporter.softAssertEquals(retrieve.getTPSPhoneDetailsPrimary("1"), firstPhoneIsPrimary, "Verify the first TPS phone primary returned [" + retrieve.getTPSPhoneDetailsPrimary("1") + "] is as expected "
                + "[" + firstPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPSPhoneDetailsPrimary("2"), secondPhoneIsPrimary, "Verify the second TPS phone primary returned [" + retrieve.getTPSPhoneDetailsPrimary("2") + "] is as expected "
                + "[" + secondPhoneIsPrimary + "]");
        TestReporter.softAssertEquals(retrieve.getTPSPhoneDetailsNumber("1"), firstNumber, "Verify the first TPS phone number returned [" + retrieve.getTPSPhoneDetailsNumber("1") + "] is as expected "
                + "[" + firstNumber + "]");
        TestReporter.softAssertEquals(retrieve.getTPSPhoneDetailsNumber("2"), secondNumber, "Verify the second TPS phone number returned [" + retrieve.getTPSPhoneDetailsNumber("2") + "] is as expected "
                + "[" + secondNumber + "]");

        TestReporter.assertAll();
    }

    public String findTicketComponentId(String tcg, String env) {

        String sql = "SELECT TC_ID "
                + "FROM RES_MGMT.TC "
                + "WHERE TC_GRP_NB = '" + tcg + "' "
                + "and TC.TC_TYP_NM = 'AdmissionComponent'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        return rs.getValue("TC_ID");

    }

    public void dvcMembershipValidations(Retrieve retrieve, DVCMember member) {
        TestReporter.logStep("Validating DVC Membership Information");

        TestReporter.softAssertEquals(retrieve.getMembershipId(), member.getMembershipRefId(), "Verify the membership ID from the retrieve [" + retrieve.getMembershipId() + "] is as expected "
                + "[" + member.getMembershipRefId() + "]");
        TestReporter.softAssertEquals(retrieve.getPPMembershipId(), member.getMembershipRefId(), "Verify the primary party membership ID from the retrieve [" + retrieve.getPPMembershipId() + "] is as expected "
                + "[" + member.getMembershipRefId() + "]");
        TestReporter.softAssertEquals(retrieve.getTCGMembershipId(), member.getMembershipRefId(), "Verify the component groupings membership ID from the retrieve [" + retrieve.getTCGMembershipId() + "] is as expected "
                + "[" + member.getMembershipRefId() + "]");
        TestReporter.softAssertEquals(retrieve.getPGMembershipId(), member.getMembershipRefId(), "Verify the primary guest membership ID from the retrieve [" + retrieve.getPGMembershipId() + "] is as expected "
                + "[" + member.getMembershipRefId() + "]");

        TestReporter.softAssertEquals(retrieve.getExternalRefNum(), member.getMemberMebershipRefId(), "Verify the external reference number from the retrieve [" + retrieve.getExternalRefNum() + "] is as expected "
                + "[" + member.getMemberMebershipRefId() + "]");
        TestReporter.softAssertEquals(retrieve.getExternalRefSource(), "MEMBER", "Verify the external reference source from the retrieve [" + retrieve.getExternalRefSource() + "] is as expected "
                + "[MEMBER]");

        TestReporter.softAssertEquals(retrieve.getMembExternalReferenceType(), "MEMBERSHIP", "Verify the member external reference type from the retrieve [" + retrieve.getMembExternalReferenceType() + "] is as expected "
                + "[MEMBERSHIP]");
        TestReporter.softAssertEquals(retrieve.getMembExternalReferenceNumber(), member.getMembershipRefId(), "Verify the member external reference number from the retrieve [" + retrieve.getMembExternalReferenceNumber() + "] is as expected "
                + "[" + member.getMembershipRefId() + "]");
        TestReporter.softAssertEquals(retrieve.getMembExternalReferenceSource(), "DVC", "Verify the member external reference source from the retrieve [" + retrieve.getMembExternalReferenceSource() + "] is as expected "
                + "[DVC]");

        TestReporter.softAssertEquals(retrieve.getSecurityLevel(), "DVC", "Verify the security level from the retrieve [" + retrieve.getSecurityLevel() + "] is as expected "
                + "[DVC]");
        TestReporter.assertAll();
    }
}
