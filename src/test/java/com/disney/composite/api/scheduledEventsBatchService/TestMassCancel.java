//******************************************************************
//******************************************************************
//******************************************************************
//******************************************************************
//******************************************************************
// Tests for the MassCancel operation will return errors until a
// fix can be identified, implemented, and passed to the lower 
// environments for the production issue being tracked by backlog
// story S-12702.
//******************************************************************
//******************************************************************
//******************************************************************
//******************************************************************
//******************************************************************



//package com.disney.composite.api.scheduledEventsBatchService;
//
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Optional;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.disney.api.soapServices.applicationError.DiningErrorCode;
//import com.disney.api.soapServices.scheduledEventsServicePort.operations.MassCancel;
//import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
//import com.disney.api.soapServices.showDiningService.operations.Book;
//import com.disney.composite.BaseTest;
//import com.disney.utils.Randomness;
//import com.disney.utils.TestReporter;
//import com.disney.utils.dataFactory.database.LogItems;
//import com.disney.utils.dataFactory.guestFactory.HouseHold;
//import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
//
//public class TestMassCancel extends BaseTest{
//	private String facilityId = "90002032";
//	private String facilityName = "Pioneer Hall";
//	private String productId = "54220";
//	private String productType = "ShowDiningProduct";
//	private String serviceDate = Randomness.generateCurrentXMLDate(1) + "T18:15:00";
//	private String massCancelReason;
//	private Book book;
//	
//	@Override
//	@BeforeSuite(alwaysRun=true)
//	@Parameters("environment")
//	public void setup(@Optional String environment){
//		this.environment = environment;
//		hh = new HouseHold(1);
//		
//		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
//		book.setParty(hh);
//		book.setFacilityId(facilityId);
//		book.setFacilityName(facilityName);
//		book.setProductId(productId);
//		book.setProductType(productType);
//		book.setServiceStartDateTime(serviceDate);
//		book.sendRequest();
//		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
//		
//		RetrieveMassCancelReasons reasons = new RetrieveMassCancelReasons(environment);
//		reasons.sendRequest();
//		TestReporter.logAPI(!reasons.getResponseStatusCode().equals("200"), "An error occurred retrieving mass cancel reasons: " + reasons.getFaultString(), reasons);
//		try{massCancelReason = reasons.getMassCancelIds().get("0");}
//		catch(Exception e){}
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
//	public void testMassCancel(){
//		MassCancel cancel = new MassCancel(environment, "Main");
//		cancel.setFacilityId(facilityId);
//		cancel.setServiceDate(serviceDate);
//		if(massCancelReason != null)cancel.setMassCancelReasonId(massCancelReason);
//		cancel.sendRequest();
//		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred invoking Mass Cancel: " + cancel.getFaultString(), cancel);
//		
//		LogItems logValidItems = new LogItems();
//		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", false);
//		validateLogs(cancel, logValidItems, 10000);
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
//	public void testMassCancel_InvalidStatus(){
//		MassCancel cancel = new MassCancel(environment, "Main");
//		cancel.setFacilityId(facilityId);
//		cancel.setServiceDate(serviceDate);
//		cancel.setMassCancelReasonId(massCancelReason);
//		cancel.sendRequest();
//		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred invoking Mass Cancel: " + cancel.getFaultString(), cancel);
//		validateApplicationError(cancel, DiningErrorCode.INVALID_TRAVEL_STATUS);
//		TestReporter.logAPI(!cancel.getFaultString().contains("INVALID RESERVATION STATUS.CANNOT CHANGE THE STATUS TO ARRIVED"), cancel.getFaultString() ,cancel);
//
//		LogItems logValidItems = new LogItems();
//		logValidItems.addItem("ScheduledEventsServiceIF", "massCancel", true);
//		validateLogs(cancel, logValidItems, 10000);
//		
//		logValidItems = new LogItems();
//		validateNotInLogs(cancel, logValidItems);
//	}
//}
