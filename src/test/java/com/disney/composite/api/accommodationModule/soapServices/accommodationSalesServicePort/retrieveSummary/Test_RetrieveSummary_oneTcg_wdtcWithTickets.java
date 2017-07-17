package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.BookReservations;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.Environment;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_wdtcWithTickets extends AccommodationBaseTest{

	private String environment;
	
	private BookReservations book;
	
	@BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void testBefore(String environment) {
        this.environment = environment;
        
        book = new BookReservations(Environment.getBaseEnvironmentName(environment), "WDTC_1Adult_Tickets");
        book.setBlockCode("01825");
        setArrivalDate(45);
        setDepartureDate(1);
        setValues(Environment.getBaseEnvironmentName(environment));
        PackageCodes pkg = new PackageCodes();
        String packageCode = null;
        boolean success = false;
        do {
            try {
                packageCode = pkg.retrievePackageCode(Environment.getBaseEnvironmentName(environment), String.valueOf(getDaysOut()),
                        getLocationId(), "WDW PKG", "*WDTC", getResortCode(), getRoomTypeCode(), "R MYW Pkg + Deluxe Dining");
                success = true;
            } catch (AssertionError e) {
                setValues();
            }
        } while (!success);
        book.setPackageCode(packageCode);
        book.setResortCode(getResortCode());
        book.setRoomTypeCode(getRoomTypeCode());
        book.setAreaPeriodStartDate(getArrivalDate());
        book.setAreaPeriodEndDate(getDepartureDate());
        book.setResortAreaStartDate(getArrivalDate());
        book.setResortAreaEndDate(getDepartureDate());

        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(Environment.getBaseEnvironmentName(environment));
        find.setPackageCode(packageCode);
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + packageCode + "].");
        book.setTicketGroup(find.getTicketGroupName());

        GetTicketProducts get = new GetTicketProducts(Environment.getBaseEnvironmentName(environment), "Main");
        get.setTicketGroupName(find.getTicketGroupName());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + find.getTicketGroupName() + "].");
        String admissionProductId = get.getAdmissionProductIdByTicketDescription("2 Day Base Ticket");
        book.setTicketDetailsBaseAdmissionProductId(admissionProductId);
        book.setTicketDetailsCode(admissionProductId);

        createHouseHold();
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/ticketDetails/guestReference/guest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", getHouseHold().primaryGuest().getFirstName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", getHouseHold().primaryGuest().getLastName());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", getHouseHold().primaryGuest().primaryAddress().getAddress1());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", getHouseHold().primaryGuest().primaryAddress().getCity());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", getHouseHold().primaryGuest().getAllAddresses().get(0).getCountry());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", getHouseHold().primaryGuest().primaryAddress().getZipCode());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", getHouseHold().primaryGuest().primaryAddress().getState());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/phoneDetails/number", getHouseHold().primaryGuest().primaryPhone().getNumber());
        book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/emailDetails/address", getHouseHold().primaryGuest().primaryEmail().getEmail());

        book.sendRequest();
        if (book.getResponse().contains("Error Invoking Pricing Service")) {
            // System.out.println();
        }
        TestReporter.assertTrue(book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a prereq reservation: " + book.getFaultString());
        setTpId(book.getTravelPlanId());
        //retrieveReservation(Environment.getBaseEnvironmentName(environment));
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "Main");
            cancel.setCancelDate(Randomness.generateCurrentXMLDate());
            cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
            cancel.sendRequest();
        } catch (Exception | AssertionError e) {

        }
    }
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary"})
	public void testRetrieveSummary_oneTcg_wdtcWithTickets(){
		
		RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
		retrieve.setRequestTravelComponentGroupingId(book.getTravelPlanSegmentId());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping ["+book.getTravelComponentGroupingId()+"]", retrieve);
		
//		TestReporter.logStep("Verify one GuestReferenceDetails node is found.");
//		TestReporter.assertTrue(retrieve.getGuestReferenceDetails() > 1, "Only one GuestReferenceDetails node found! ");
		
		// Old vs New Validation
		if (Environment.isSpecialEnvironment(environment)) {
			RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
			clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
			clone.sendRequest();
			if (!clone.getResponseStatusCode().equals("200")) {
				TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
			}
			clone.addExcludedBaselineAttributeValidations("@xsi:nil");
			clone.addExcludedBaselineAttributeValidations("@xsi:type");
			clone.addExcludedBaselineXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
			clone.addExcludedXpathValidations("/Envelope/Body/getFacilitiesByEnterpriseIDsResponse/result/effectiveFrom");
			clone.addExcludedBaselineXpathValidations("/Envelope/Header");
			TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
		}
		
	}
	
}
