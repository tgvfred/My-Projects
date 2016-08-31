package com.disney.utils.dataFactory.staging.bookSEReservation;

import com.disney.utils.dataFactory.folioInterface.Folio;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
/**
 * This class contains interfaces that will be implemented by dining and activity service generators
 * @author Justin Phlegar
 * @author Waightstill W Avery
 *
 */
public interface ScheduledEventReservation{
	// Generic scenario name, intended to have all extraneous elements (components, add-ons, comments, etc.) removed
	public static final String NOCOMPONENTSNOADDONS = "NoComponentsNoAddOns";
	public static final String ONECOMPONENTSNOADDONS = "OneComponentsNoAddOns";
	public static final String NOCOMPONENTSNOADDONSTWOADULTS = "NoComponentsNoAddOnsTwoAdults";
	public static final String ONECOMPONENTSNOADDONSTWOADULTS = "OneComponentsNoAddOnsTwoAdults";
	public static final String ADDON = "DinnerShowWithAddOn";
	public static final String SPECIALREQUESTS = "SpecialRequests";
	
	public Folio folio();
	public Folio folio(String environment);
	// Getters used to retrieve field values
	public String getEnvironment();
	public String getConfirmationNumber();
	public String getTravelPlanId();
	public String getCancellationNumber();
	public String getStatus();
	public String getArrivedStatus();
	public String getFacilityId();
	public String getProductId();
	public String getProductName();
	public String getProductType();
	public String getServicePeriodId();
	public String getServiceStartDate();
	public int getNumberOfGuests();
	public String getValidateBookingStatus();
	public String getTableNumber();
	public String getAssignTableNumberStatus();
	public String getPrintTicketStatus();
	public String getReprintTicketStatus();
	public String getRetrieveResponseFacilityID();
	public String getPrimaryGuestAge();
	public String getModifyResponseStatus();
	public String getSourceAccountingCenter();
	public String getReservableResourceId();
	public String getFacilityName();
	// Setters used to set field values
	public void setFacilityId(String facilityId);
	public void setProductName(String productName);
	public void setProductId(String productId);
	public void setProductType(String productType);
	public void setTravelPlanId(String travelPlanId);
	public void setBookingScenario(String scenario);
	public void setSourceAccountingCenter(String sourceAccountingcenter);
	public void setServiceStartDate(String date);
	public void setFacilityName(String name);
	public void setFreezeStartDate(String startDate);
	// Interfaces for methods to generate and set a household
	public HouseHold party();
	public void setParty(HouseHold party);	
	// Interfaces for methods to book and retrieve scheduled events
	public void book(String facilityID, String startDate, String servicePeriod, String productId);
	public void book(String diningBookScenario);
	public void retrieve();
	// Interfaces for methods to cancel scheduled reservations, as well as methods to update scheduled event reservations to 'Arrived' or 'No Show'
	public void cancel();
	public void arrived();
	public void noShow();
	// Interfaces for methods to validate bookings
	public void validateBooking();
	public void validateBooking(String scenario);
	// Interfaces for methods to add a travel agency to a scheduled event reservation
	public void addTravelAgency();
	public void addTravelAgency(String agencyId);
	public void addTravelAgency(String agencyIataNumber, String agencyOdsId, String guestAgencyId, String agentId, String guestAgentId, String confirmationLocatorValue, String guestConfirmationLocationId);
	public String getTravelAgencyId();
	// Interfaces for show dining reservations
	public void assignTableNumbers();
	public void assignTableNumbers(String tableNumber);
	public void printTicket();
	public void reprintTicket();
	// Interface for a sub-class that will contain methods to modify scheduled event reservations
	public Modify modify();
	// ScheduldeEventsService interface
	public ScheduledEventsServices ses();
}
