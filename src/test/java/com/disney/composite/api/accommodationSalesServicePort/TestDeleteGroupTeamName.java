package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestDeleteGroupTeamName {
	private String environment = "";
	Book book = null;
	CreateGroupTeamName CreateGroupTeamName = null; 
	
	@BeforeTest(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		
		System.out.println(book.getRequest());
		book.sendRequest();
		System.out.println(book.getResponse());
	}
	
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateGroupTeamName"})
	public void testCreateGroupTeamName_MainFlow(){
		
		CreateGroupTeamName = new CreateGroupTeamName(environment, "createGroupTeamName" );
		CreateGroupTeamName.setgroupTeamName("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupcode("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupTeamViewTO("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.sendRequest();
		TestReporter.assertEquals(CreateGroupTeamName.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupCode(), "The response contains a Group Code");
		TestReporter.assertNotNull(CreateGroupTeamName.getGroupTeamId(), "The response contains a GroupTeam Id");
	}

	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "DeleteGroupTeamName"},
			dependsOnMethods = "testCreateGroupTeamName_MainFlow")
	public void testDeleteGroupTeamName_MainFlow(){
		DeleteGroupTeamName DeleteGroupTeamName = new DeleteGroupTeamName(environment, "Main" );
		DeleteGroupTeamName.setgroupCode(CreateGroupTeamName.getGroupCode());
		DeleteGroupTeamName.setgroupTeamName(CreateGroupTeamName.getGroupTeamName());
		DeleteGroupTeamName.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		DeleteGroupTeamName.sendRequest();
		System.out.println(DeleteGroupTeamName.getRequest());
		System.out.println(DeleteGroupTeamName.getResponse());
		TestReporter.assertEquals(DeleteGroupTeamName.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(DeleteGroupTeamName.getTeamNameDeleted(), "The response contains a TeamName Deleted");
	}

	
}
