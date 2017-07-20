package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCreateComments {
private String environment = "";
String commentId = Randomness.randomAlphaNumeric(10);
String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
String parentId = "1236307865";
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {this.environment = environment;}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateComments"})
	public void testCreateComments_parentTP_emptyCommentOwnerDetails() {

		String expectedGSR = "true";
//		String sql = "SELECT * " +
//                " FROM RES_MGMT.RES_MGMT_REQ " +
//                " WHERE ROWNUM <= 1 ";

//        Database db = new OracleDatabase(environment, Database.DREAMS);
//        Recordset rs = new Recordset(db.getResultSet(sql));
        //rs.print();
		        
		CreateComments create = new CreateComments(environment, "Main");
		create.setParentIds(parentId);
		create.setIsActive("true");
		create.setSendToGSR("true");
		create.setConfidential("true");
		create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentText(commentText);
		create.setCommentLevel("TC");
		create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/commentsInfo/commentOwnerDetail", BaseSoapCommands.REMOVE_NODE.toString());
		create.setCreatedBy("Rachel " + Randomness.randomAlphaNumeric(4));
		create.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
		create.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
		create.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
		create.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
		create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
		create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
		create.sendRequest();
		
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", create);
		TestReporter.assertTrue(create.getSendToGSR().equals("true"), "Verify that the sendToGSR node [" + create.getSendToGSR() + "] is what is expected [" + expectedGSR + "]"); 
		
		RetrieveComments retrieve = new RetrieveComments(environment, "Main");
		retrieve.setParentIds(parentId);
		retrieve.sendRequest();
		
		//Validate comment data in RES_MGMT_REQ table
		String GSR_IN = (create.getSendToGSR().equals("true")) ? "Y" : "N";
		String CFDNTL_IN = (create.getSendToGSR().equals("true")) ? "Y" : "N";
				
		String RES_MGMT_REQ_VALIDATE_sql = "SELECT * " +
                " FROM RES_MGMT.RES_MGMT_REQ " +
                " WHERE TC_ID = " + parentId + " " +
                " AND GSR_IN = '" + GSR_IN + "' " +
                " AND CFDNTL_IN = '" + CFDNTL_IN + "' " +
                " AND RES_MGMT_REQ_TX = '" + create.getCommentText() + "' ";
                
		Database RES_MGMT_REQ_VALIDATE_db = new OracleDatabase(environment, Database.DREAMS);
		Recordset RES_MGMT_REQ_VALIDATE_rs = new Recordset(RES_MGMT_REQ_VALIDATE_db.getResultSet(RES_MGMT_REQ_VALIDATE_sql));
		TestReporter.assertFalse(RES_MGMT_REQ_VALIDATE_rs == null, "Verify that the comment exists in the RES_MGMT_REQ table");

		//If sendToGsr=true, validate GSR data in EXT_INTF.GSR_RCD, EXT_INTF.GSR_GUEST, and EXT_INTF.GSR_TXN tables
		String GSR_RCD_sql = "SELECT * " +
				" FROM EXT_INTF.GSR_RCD " +
				" WHERE CMT_TXT = '" + create.getCommentText() + "' ";
		Database GSR_RCD_db = new OracleDatabase(environment, Database.DREAMS);
		Recordset GSR_RCD_rs = new Recordset(GSR_RCD_db.getResultSet(GSR_RCD_sql));
		TestReporter.assertFalse(GSR_RCD_rs == null, "Verify that the comment exists in the GSR_RCD table");
		
		//Validate comment with a call to retrieveComments
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
		validate(create, retrieve);
		
	}

	private void validate(CreateComments create, RetrieveComments retrieve){
			
		TestReporter.logStep("Retrieve Comment from Database.");
        TestReporter.setAssertFailed(false);
              
        for(int i=1;i <= 100;i++){
        	String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
        	if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))){
        		TestReporter.softAssertEquals(create.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
        		TestReporter.softAssertEquals(create.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create.getSendToGSR() + "]");
        		TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
        		TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
        		TestReporter.softAssertEquals(create.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create.getCommentText() + "]");
        		TestReporter.softAssertEquals(create.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create.getCommentLevel() + "]");
        		TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");
        		String[] createdDate = create.getCreatedDate().split("T");
        		String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
        		TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
        		TestReporter.assertAll();
        		break;
        	}else{
        	continue;
        	}
        }
      }
}