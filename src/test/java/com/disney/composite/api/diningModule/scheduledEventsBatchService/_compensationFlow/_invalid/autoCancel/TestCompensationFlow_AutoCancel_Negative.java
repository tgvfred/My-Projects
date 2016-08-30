//package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow._invalid.autoCancel;
//
//import org.testng.SkipException;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Optional;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.disney.api.soapServices.folioModule.chargeGroup.operations.RetrieveNonGuaranteedGuestChargeGroups;
//import com.disney.composite.BaseTest;
//import com.disney.test.utils.Randomness;
//import com.disney.utils.TestReporter;
//import com.disney.utils.dataFactory.database.LogItems;
//
//public class TestCompensationFlow_AutoCancel_Negative extends BaseTest{
//	private String date = Randomness.generateCurrentXMLDate(150);
//	private String sourceAccountingCenter = "3";
//	private ThreadLocal<RetrieveNonGuaranteedGuestChargeGroups> retrieve = new ThreadLocal<RetrieveNonGuaranteedGuestChargeGroups>();
//	
//	@Override
//	@BeforeMethod(alwaysRun = true)
//	@Parameters("environment")
//	public void setup(@Optional String environment){
//		this.environment = environment;
//		TestReporter.logScenario("RetrieveNonGuaranteedGuestChargeGroups");
//		retrieve.set(new RetrieveNonGuaranteedGuestChargeGroups(environment));
//		retrieve.get().setRunDate(date);
//		retrieve.get().setSourceAccountingCenter(sourceAccountingCenter);
//		retrieve.get().sendRequest();
//		TestReporter.logAPI(!retrieve.get().getResponseStatusCode().equals("200"), "An error occurred retrieving non-guaranteed guest charge groups: " + retrieve.get().getFaultString(), retrieve.get());
//		
//		LogItems logValidItems = new LogItems();
//		logValidItems.addItem("ChargeGroupIF", "retrieveNonGuaranteedGuestChargeGroups", false);
//		validateLogs(retrieve.get(), logValidItems, 10000);
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative", "compensation"})
//	public void TestCompensationFlow_AutoCancel_Negative_RIMFail(){	
//		throw new SkipException("The testing solution for this scenario has not been determined.");
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative", "compensation"})
//	public void TestCompensationFlow_AutoCancel_Negative_DineFail(){	
//		throw new SkipException("The testing solution for this scenario has not been determined.");
//	}
//	
//	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative", "compensation"})
//	public void TestCompensationFlow_AutoCancel_Negative_FolioFail(){	
//		throw new SkipException("The testing solution for this scenario has not been determined.");
//	}
//}
