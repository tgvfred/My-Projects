package com.disney.composite.api.travelPlanSalesOrderServiceV1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.Create;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations.UpdateGatedAdmission;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestUpdateGatedAdmission {
	private String environment;
	private HouseHold hh;
	private String startDate;
	private String endDate;
	private String inactiveDate;
	private Create create;
	private RetrieveDetailsByTravelPlanId retrieve;
	private UpdateGatedAdmission update;
	private String statusBeforeUpdate;
	private String statusAfterUpdate;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;

		startDate = Randomness.generateCurrentXMLDate(0);
		endDate = Randomness.generateCurrentXMLDate(1);
		inactiveDate = Randomness.generateCurrentXMLDate(4);
		generatHousehold();
		
		create = new Create(environment, "DayGuest");
		create.setSalesOrderArrivalDate(startDate);
		create.setSalesOrderDepartureDate(endDate);
		create.setSalesOrderGatedAdmissionRequestsEndDate(endDate);
		create.setSalesOrderGatedAdmissionRequestsStartDate(startDate);
		create.setTravelPlanRequestEndDate(endDate);
		create.setTravelPlanRequestStartDate(startDate);
		create.setSalesOrderGatedAdmissionRequestsSalesOrderItemCommentsRequestInactiveDate(inactiveDate);
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setGuestAddressCity(hh.primaryGuest().primaryAddress().getCity());
		create.setGuestAddressCountry(hh.primaryGuest().primaryAddress().getCountry());
		create.setGuestAddressLine1(hh.primaryGuest().primaryAddress().getAddress1());
		create.setGuestAddressRegionName(hh.primaryGuest().primaryAddress().getState());
		create.setGuestAddressStateCode(hh.primaryGuest().primaryAddress().getStateAbbv());
		create.setGuestAddressZipCode(hh.primaryGuest().primaryAddress().getZipCode());
		create.sendRequest();
		TestReporter.assertEquals(create.getResponseStatusCode(), "200", "An error occurred during creation.\nRequest:\n"+create.getRequest()+"\nResonse:\n"+create.getResponse());
		
		retrieve = new RetrieveDetailsByTravelPlanId(environment, "Main");
		retrieve.setTravelPlanId(create.getTravelPlanId());
		retrieve.sendRequest();
		TestReporter.assertEquals(retrieve.getResponseStatusCode(), "200", "An error occurred during retrieval.\nRequest:\n"+retrieve.getRequest()+"\nResonse:\n"+retrieve.getResponse());
		statusBeforeUpdate = retrieve.getSalesOrdersStatus();
	}
	
	@Test(groups = {"api", "regression", "tpso", "travelPlanSalesOrderServiceV1"})
	public void testUpdateGatedAdmission(){
		update = new UpdateGatedAdmission(environment, "Main");
		update.setCreationHistoryCreationDate(startDate);
		update.setGatedAdmissionUpdateRequestsCode(create.getSalesOrderGatedAdmissionRequestsCode());
		update.setGatedAdmissionUpdateRequestsDescription(retrieve.getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesDescription());
		update.setGatedAdmissionUpdateRequestsEndDate(endDate);
		update.setGatedAdmissionUpdateRequestsEnterpriseFacilityId(create.getSalesOrderGatedAdmissionRequestsEnterpriseFacilityId());
		update.setGatedAdmissionUpdateRequestsId(retrieve.getSalesOrdersSalesOrderItemGroupsGatedAdmissionDetailsResponsesId());
		update.setGatedAdmissionUpdateRequestsLocationId(create.getSalesOrderGatedAdmissionRequestsLocationId());
		update.setGatedAdmissionUpdateRequestsStartDate(startDate);
		update.setGuestsGuestNameFirstName(retrieve.getGuestsGuestNameFirstName());
		update.setGuestsGuestNameLastName(retrieve.getGuestsGuestNameLastName());
		update.setGuestsGuestReferenceId(retrieve.getGuestsGuestReferenceId());
		update.setGuestsId(retrieve.getGuestsId());
		update.setSalesOrderItemGuestGuestReferenceId(retrieve.getGuestsId());
		update.sendRequest();
		TestReporter.assertEquals(update.getResponseStatusCode(), "200", "An error occurred during update.\nRequest:\n"+update.getRequest()+"\nResponse:\n"+update.getResponse());
		statusAfterUpdate = update.getExperienceMediasSalesOrderItemGuestsFulfillmentStatus();
		TestReporter.assertNotEquals(statusBeforeUpdate, statusAfterUpdate, "The status before ["+statusBeforeUpdate+"] and after ["+statusBeforeUpdate+"] the update  were found to be the same, indicating that the update was not successful or enough time had not be allotted to allow the update to take place.");
		TestReporter.assertEquals(statusAfterUpdate, "Checked In", "The status after the update ["+statusAfterUpdate+"] was not not [Checked In] as expected.");
	}
	
	private void generatHousehold(){
		hh = new HouseHold(1);
		hh.sendToApi(environment);
	}
}