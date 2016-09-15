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
public class TestUpdatePreArrivalInformation_Negative {
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
		public void testupdatePreArrivalInfomation_Negative_NoAuthorization () throws IOException{
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
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequestWithMissingAuthToken(request);
			TestReporter.assertTrue(response.getStatusCode() == 401, "Validate status code returned ["+response.getStatusCode()+"] was [401]");
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_NoTPS () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId("");
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("Travel Plan Segment Id is required"),"The response file returned the proper error: Travel Plan Segment Id is required");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_InvalidTPS () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("Travel Plan Segment Not Found"),"The response file returned the proper error: Travel Plan Segment Not Found");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_InvalidContactType () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("TEST");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("UOWManager transaction processing failed"),"The response file returned the proper error: UOWManager transaction processing failed");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_BlankContactType () throws IOException{
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
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("UOWManager transaction processing failed"),"The response file returned the proper error: UOWManager transaction processing failed");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_BlankContactValue () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("UOWManager transaction processing failed"),"The response file returned the proper error: UOWManager transaction processing failed");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_NoStartDate () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime("");
			request.getPreArrivalInfo().setDepartureTime(Randomness.generateCurrentXMLDatetime(+31));
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("UPDATE ETA ETD ERROR : Assignment Owner start date is different."),"The response file returned the proper error: UPDATE ETA ETD ERROR : Assignment Owner start date is different.UPDATE ETA ETD ERROR : Assignment Owner start date is different.");
			
		}
		@Test(groups={"api","rest", "regression", "travelPlanSegment", "travelPlanSegmentService", "updatePreArrivalInformation"})
		public void testupdatePreArrivalInfomation_Negative_NoEndDate () throws IOException{
			// set log levels for debugging
			TestReporter.setDebugLevel(TestReporter.INFO);
			//Create new request
			UpdatePreArrivalInformationRequest request = new UpdatePreArrivalInformationRequest();
			
			//Populate new Request
			request.setTravelPlanSegmentId(TPSId);
			request.getPreArrivalInfo().setArrivalTime(Randomness.generateCurrentXMLDatetime(+30));
			request.getPreArrivalInfo().setDepartureTime("");
			//Set the contact details
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactType("Email");
			request.getPreArrivalInfo().getOnsiteContactDetails().get(0).setContactValue("test@disney.tst");
			
			RestResponse response= Rest.travelPlanSegment(environment).travelPlanSegmentService().updatePreArrivalInformation().sendPutRequest(request);
			TestReporter.assertTrue(response.getStatusCode() == 500, "Validate status code returned ["+response.getStatusCode()+"] was [500]");
			RetrieveDetailsResponse [] RetrieveDetailsResponse = response.mapJSONToObject(RetrieveDetailsResponse[].class);
			System.out.println(RetrieveDetailsResponse);
			TestReporter.assertTrue(response.getResponse().contains("UPDATE ETA ETD ERROR : Assignment Owner end date is different."),"The response file returned the proper error: UPDATE ETA ETD ERROR : Assignment Owner end date is different.");
			
		}
}
