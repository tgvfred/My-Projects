package com.disney.utils.dataFactory.staging.bookSEReservation;

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
	
	// Getters used to retrieve field values
	public String getEnvironment();
	public String getConfirmationNumber();
	public String getTravelPlanId();
	public String getCancellationNumber();
	public String getStatus();
	public String getArrivedStatus();
	public String getFacilityId();
	public String getProductId();
	public String getServicePeriodId();
	public String getServiceStartDate();
	// Interfaces for methods to generate and set a household
	public HouseHold party();
	public void setParty(HouseHold party);	
	// Interfaces for methods to book scheduled events
	public void book(String facilityID, String startDate, String servicePeriod, String productId);
	public void book(String diningBookScenario);
	// Interfaces for methods to cancel scheduled reservations, as well as methods to update scheduled event reservations to 'Arrived' or 'No Show'
	public void cancel();
	public void arrived();
	public void noShow();
	// Interfaces for methods to add a travel agency to a scheduled event reservation
	public void addTravelAgency();
	public void addTravelAgency(String agencyId);
	public void addTravelAgency(String agencyIataNumber, String agencyOdsId, String guestAgencyId, String agentId, String guestAgentId, String confirmationLocatorValue, String guestConfirmationLocationId);
	// Interface for a sub-class that will contain methods to modify scheduled event reservations
	public Modify modify();
}
