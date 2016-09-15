package com.disney.composite.api.RestServices.travelPlanSegment.travelPlanSegment.updatePreArrivalInformation;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.RetrieveDetailsResponse;
import com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request.UpdatePreArrivalInformationRequest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestUpdatePreArrivalInformation {
	private String environment;
	private String TPId;
	private String TPSId; 
	private String StartDate;
	private String EndDate;
		
		/**
		 * This will always be used as is. TestNG will pass in the Environment used
		 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
		 */
		@BeforeMethod(alwaysRun = true)
		@Parameters({  "environment" })
		public void setup(@Optional String environment) {
			TestReporter.setDebugLevel(TestReporter.INFO);
			
			this.environment = environment;
			
			//generate accommodation booking
			Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
			book.sendRequest();
			//grab relevant information
			TPId = book.getTravelPlanId();
			TPSId = book.getTravelPlanSegmentId();
		}
		/**
		 * This is a sample template 	
		 * Expected updates
		 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
		 * 		- serviceName - name of the service
		 * 		- operationName - name of the operation
		 * 		- OperationName - name of the operation
		 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
		 */
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_SingleContactDetail () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
			TestReporter.assertTrue(response.getResponse().contains("true"),"The response was successful with the response of true.");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_TwoContactDetail () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			request.getPreArrivalInfo().addOnsiteContactDetail();
			request.getPreArrivalInfo().getOnsiteContactDetails().get(1).setContactType("Phone");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(1).setContactValue("4075551234");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
			TestReporter.assertTrue(response.getResponse().contains("true"),"The response was successful with the response of true.");
			
		}
}
