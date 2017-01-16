package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateGroupTeamName;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.DeleteGroupTeamName;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestDeleteGroupTeamName {
	private String environment = "";
	private Book book = null;
	private CreateGroupTeamName CreateGroupTeamName = null; 
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred durign booking", book);
		
		CreateGroupTeamName = new CreateGroupTeamName(environment, "createGroupTeamName" );
		CreateGroupTeamName.setgroupTeamName("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupcode("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.setgroupTeamViewTO("Donald"+Randomness.randomAlphaNumeric(4));
		CreateGroupTeamName.sendRequest();
		TestReporter.logAPI(!CreateGroupTeamName.getResponseStatusCode().equals("200"), "An error occurred creating a group team name", CreateGroupTeamName);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "DeleteGroupTeamName"})
	public void testDeleteGroupTeamName_MainFlow(){
		TestReporter.logScenario("Test Deleting a Group Team Name");
		DeleteGroupTeamName DeleteGroupTeamName = new DeleteGroupTeamName(environment, "Main" );
		DeleteGroupTeamName.setgroupCode(CreateGroupTeamName.getGroupCode());
		DeleteGroupTeamName.setgroupTeamName(CreateGroupTeamName.getGroupTeamName());
		DeleteGroupTeamName.setTravelPlanSegmentId(book.getTravelPlanSegmentId());
		DeleteGroupTeamName.sendRequest();
		TestReporter.logAPI(!DeleteGroupTeamName.getResponseStatusCode().equals("200"), "An error occurred deleting a group team name", DeleteGroupTeamName);
		TestReporter.assertNotNull(DeleteGroupTeamName.getTeamNameDeleted(), "The response contains a TeamName Deleted");
	}	
}