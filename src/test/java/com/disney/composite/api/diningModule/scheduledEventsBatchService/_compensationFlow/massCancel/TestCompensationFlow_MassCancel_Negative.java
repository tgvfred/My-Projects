package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow.massCancel;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_MassCancel_Negative extends BaseTest{
	private String facilityId = "90002032";
	private String facilityName = "Pioneer Hall";
	private String productId = "54220";
	private String productType = "ShowDiningProduct";
	private String serviceDate = Randomness.generateCurrentXMLDate(1) + "T18:15:00";
	private String massCancelReason;
	private Book book;
	
	@Override
	@BeforeSuite(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		
		book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.setFacilityId(facilityId);
		book.setFacilityName(facilityName);
		book.setProductId(productId);
		book.setProductType(productType);
		book.setServiceStartDateTime(serviceDate);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking a show dining reservation: " + book.getFaultString(), book);
		
		RetrieveMassCancelReasons reasons = new RetrieveMassCancelReasons(environment);
		reasons.sendRequest();
		TestReporter.logAPI(!reasons.getResponseStatusCode().equals("200"), "An error occurred retrieving mass cancel reasons: " + reasons.getFaultString(), reasons);
		try{massCancelReason = reasons.getMassCancelIds().get("0");}
		catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void TestCompensationFlow_MassCancel_Negative_RIMFail(){
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//Tests for the MassCancel operation will return errors until a
		//fix can be identified, implemented, and passed to the lower 
		//environments for the production issue being tracked by backlog
		//story S-12702.
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		throw new SkipException("Tests for the MassCancel operation will return errors until a fix can be identified, implemented, and passed to the lower environments for the production issue");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void TestCompensationFlow_MassCancel_Negative_DineFail(){
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//Tests for the MassCancel operation will return errors until a
		//fix can be identified, implemented, and passed to the lower 
		//environments for the production issue being tracked by backlog
		//story S-12702.
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		throw new SkipException("Tests for the MassCancel operation will return errors until a fix can be identified, implemented, and passed to the lower environments for the production issue");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void TestCompensationFlow_MassCancel_Negative_FolioFail(){
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//Tests for the MassCancel operation will return errors until a
		//fix can be identified, implemented, and passed to the lower 
		//environments for the production issue being tracked by backlog
		//story S-12702.
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		//******************************************************************
		throw new SkipException("Tests for the MassCancel operation will return errors until a fix can be identified, implemented, and passed to the lower environments for the production issue");
	}
}
