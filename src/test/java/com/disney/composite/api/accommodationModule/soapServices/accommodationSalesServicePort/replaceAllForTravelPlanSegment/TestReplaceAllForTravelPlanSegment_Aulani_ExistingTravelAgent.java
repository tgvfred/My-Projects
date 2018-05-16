package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.mq.sbc.ItineraryConfirmation;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations.UpdateIndividual;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.pricingModule.enums.DistributionProductChannel;
import com.disney.api.soapServices.pricingModule.enums.PlanType;
import com.disney.api.soapServices.pricingModule.packagingService.operations.helpers.PackageCodeHelper;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.UpdateItineraryConfirmationHelper;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.ManageConfirmationRecipient;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestReplaceAllForTravelPlanSegment_Aulani_ExistingTravelAgent extends AccommodationBaseTest {

    private String tpPtyId;
    private String odsGuestId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        setAddTravelAgency(true);
        setSendRequest(false);
        bookReservation();
        isComo.set("true");
        getBook().setRoomDetailsResortCode("17");
        getBook().setRoomDetailsRoomTypeCode("7A");
        PackageCodeHelper pkg = new PackageCodeHelper(environment, Randomness.generateCurrentXMLDate(), PlanType.ROOM_ONLY, DistributionProductChannel.AULANI_SALES_CENTER_ROOM_ONLY, "17", "7A", Randomness.generateCurrentXMLDate(0));
        getBook().setRoomDetailsPackageCode(pkg.getPackageCode());
        getBook().sendRequest();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService",
            "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_Aulani_ExistingTravelAgent() {

        String randemail = Randomness.randomString(5);
        String email = randemail + "@disney.com";
        String agentId = getBook().getRequestNodeValueByXPath(
                "/Envelope/Body/replaceAllForTravelPlanSegment/request/travelAgency/agentId");
        String agencyId = getBook().getRequestNodeValueByXPath(
                "/Envelope/Body/replaceAllForTravelPlanSegment/request/travelAgency/agencyOdsId");

        // Grabs the First and Last name of the Travel Agent
        String sql1 = "select * " + "from odsp.TXN_IDVL a " + "where a.TXN_IDVL_ID = '" + agentId + "' ";

        Database db1 = new OracleDatabase(
                Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())),
                Database.GOMASTER);
        Recordset rs1 = new Recordset(db1.getResultSet(sql1));
        if (rs1.getRowCount() == 0) {
            throw new AutomationException(
                    "No TXN_PTY_EXTNL_REF_VAL was found in GUEST.TXN_PTY_EXTNL_REF table for Guest ID ["
                            + getBook().getGuestId() + "].");
        }

        // Updates the existing Travel Agents Email
        UpdateIndividual update = new UpdateIndividual(environment, "Main");
        update.setEmail(email);
        update.setFirstName(rs1.getValue("IDVL_FRST_NM"));
        update.setLastName(rs1.getValue("IDVL_LST_NM"));
        update.setId(agentId);
        update.setRequestNodeValueByXPath(
                "/Envelope/Body/updateIndividual/updateIndividualRequest/individual/telecomAddresses",
                BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"),
                "Verify that no error occurred when checking the Itinerary Confirmation: " + update.getFaultString(),
                update);

        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"),
                "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

        // Sets up the confirmation to "Email Guest Agent"
        ManageConfirmationRecipient manage = new ManageConfirmationRecipient(getEnvironment(), "PhoneAndEmail");
        manage.setTpsId(getBook().getTravelPlanSegmentId());
        manage.setConfirmationType(UpdateItineraryConfirmationHelper.EMAIL_GUEST_AGENT);
        manage.setDefaultConfirmationIndicator("true");
        manage.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        manage.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        manage.setGuestMiddleName(getHouseHold().primaryGuest().getMiddleName());
        manage.setPhoneNumber(getHouseHold().primaryGuest().primaryPhone().getNumber());
        manage.setEmailAddress(getHouseHold().primaryGuest().primaryEmail().getEmail());
        manage.sendRequest();
        TestReporter.logAPI(!manage.getResponseStatusCode().equals("200"),
                "Verify that no error occurred when checking the Itinerary Confirmation: " + manage.getFaultString(),
                manage);

        // Confirms that the confirmation was a success
        ItineraryConfirmation confirmation = new ItineraryConfirmation(environment, "WDW", "ItineraryConfirmationRQ");
        confirmation.setGlobalGuestID(agentId);
        confirmation.setItineraryId(getBook().getTravelPlanId());
        confirmation.setParentId(getBook().getTravelPlanSegmentId());
        confirmation.setChildId(getBook().getTravelComponentGroupingId());
        confirmation.setSubChildId(getBook().getTravelComponentId());
        confirmation.setSystemOfRecord("DPMS");
        confirmation.setEndDate(getDepartureDate());
        confirmation.setLocation("WDW");
        confirmation.setRequestNodeValueByXPath(
                "/ItineraryConfirmationRQ/ItineraryConfirmationSpec/@ConfirmationTypeName", "Email Guest Agent");
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec",
                BaseSoapCommands.ADD_NODE.commandAppend("AgencyReference"));
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference",
                BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("PrimaryAgentFlag"));
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference",
                BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("AgencyType"));
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference",
                BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("AgencyId"));
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference",
                BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("AgentId"));
        confirmation.setRequestNodeValueByXPath(
                "/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference/@PrimaryAgentFlag", "true");
        confirmation.setRequestNodeValueByXPath(
                "/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference/@AgencyType",
                "SYSTEM SUPPORT TEST AGENCY");
        confirmation.setRequestNodeValueByXPath(
                "/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference/@AgencyId", agencyId);
        confirmation.setRequestNodeValueByXPath(
                "/ItineraryConfirmationRQ/ItineraryConfirmationSpec/AgencyReference/@AgentId", agentId);
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec",
                BaseSoapCommands.ADD_NODE.commandAppend("Email"));
        confirmation.setRequestNodeValueByXPath("/ItineraryConfirmationRQ/ItineraryConfirmationSpec/Email", email);
        confirmation.sendRequest();
        TestReporter.logAPI(!confirmation.isSuccess(),
                "Verify that no error occurred when checking the Itinerary Confirmation: "
                        + confirmation.getFaultString(),
                confirmation);
        validations();

        // Test validations
        TestReporter.logStep("Validating ExperienceMediaDetails Node Found");
        TestReporter.assertTrue(
                getBook().getNumberOfResponseNodesByXPath(
                        "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/experienceMediaDetails") == 1,
                "Verify an ExperienceMediaDetails Node was found in the Response.");

    }

    private void validations() {
        tpPtyId = getBook().getGuestId();

        ValidationHelper validations = new ValidationHelper(getEnvironment());

        // Validate reservation
        validations.validateModificationBackend(2, "Booked", "", getArrivalDate(), getDepartureDate(), "NULL", "NULL",
                getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(),
                getBook().getTravelComponentGroupingId());
        validations.verifyBookingIsFoundInResHistory(getBook().getTravelPlanId());
        validations.verifyTcStatusByTcg(getBook().getTravelComponentGroupingId(), "Booked");

        // Validate Folio
        validations.verifyNameOnCharges(getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(),
                getBook().getTravelComponentGroupingId(), getHouseHold().primaryGuest());
        validations.verifyNumberOfChargesByStatus("UnEarned", 1, getBook().getTravelPlanId());
        validations.verifyChargeDetail(4, getBook().getTravelPlanId());
        validations.verifyChargeGroupsStatusCount("UnEarned", 3, getBook().getTravelPlanId());

        // Validate RIM
        validations.verifyInventoryAssigned(getBook().getTravelComponentGroupingId(), 1, getBook().getTravelPlanId());
        validations.validateSpecialNeeds(getBook().getTravelPlanId(), "false");
        validations.verifyRIMPartyMIx(getBook().getTravelPlanId(), "1", "0", true);

        // Validate guest
        validations.validateGuestInformation(getBook().getTravelPlanId(), getHouseHold());
        validations.verifyNumberOfTpPartiesByTpId(1, getBook().getTravelPlanId());
        validations.verifyTpPartyId(tpPtyId, getBook().getTravelPlanId());
        validations.verifyOdsGuestIdCreated(true, getBook().getTravelPlanId());

        // Validate TPS confirmation
        String firstName = getBook().getRequestNodeValueByXPath(
                "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName");
        String lastName = getBook().getRequestNodeValueByXPath(
                "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName");
        String contactName = firstName + " " + lastName;
        validations.validateConfirmationDetails(getBook().getTravelPlanSegmentId(), "Email", tpPtyId, "Y", "N",
                contactName, "N");

        // Validate the Old to the New
        if (Environment.isSpecialEnvironment(environment)) {
            ReplaceAllForTravelPlanSegment clone = (ReplaceAllForTravelPlanSegment) getBook().clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedBaselineXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/guestId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/guestReferenceDetails/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentGroupingId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/travelComponentId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/ticketDetails/guestReference/guest/partyId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanSegmentId");
            clone.addExcludedXpathValidations(
                    "/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/travelPlanId");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(getBook(), true),
                    "Validating Response Comparison");

            try {
                Cancel cancel = new Cancel(
                        Environment.getBaseEnvironmentName(Environment.getBaseEnvironmentName(getEnvironment())),
                        "Main");
                cancel.setCancelDate(Randomness.generateCurrentXMLDate());
                cancel.setTravelComponentGroupingId(clone.getTravelComponentGroupingId());
                cancel.sendRequest();
            } catch (Exception e) {

            }
        }
    }
}
