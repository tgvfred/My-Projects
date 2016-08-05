package com.disney.composite.api.folioChargeService.postCharges;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.folioCharge.operations.PostCharges;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class Test_PostCharges extends BaseTest{
	@BeforeClass
	@Parameters({ "environment" })
	@Override
	public void setup(@Optional String environment){
		//TestReporter.setDebugLevel(1);
		this.environment = environment;
	}
	@Test(groups = {"api", "regression", "como", "folio", "folioCharge", "ostCharges",})
	public void testPostCharges(){
		hh = new HouseHold(1);
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.book("TableServiceAddOn");

		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber())));
		String tcId = "";
		String tcgId = "";
		String productTypeName = "";
		
		for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
			if(rs.getValue("PROD_TYP_NM").toLowerCase().contains("product")){
				tcId = rs.getValue("TC_ID");
				tcgId = rs.getValue("TC_GRP_NB");
				productTypeName = rs.getValue("PROD_TYP_NM");
			}
		}
		
		PostCharges postCharges = new PostCharges(environment, "MinimalInfo");
		postCharges.setAllAmounts("200", "USD");
		postCharges.setProduct(res.getProductId(), productTypeName);
		postCharges.setTravelComponentGroupId(tcgId);
		postCharges.setTravelComponentId(tcId);
		postCharges.setLocationId(res.getFacilityId());
		postCharges.sendRequest();
		
		String chargeId = postCharges.getChargeId();
		
		TestReporter.logAPI(!postCharges.getResponseStatusCode().contains("200"), postCharges.getFaultString() ,postCharges);
		TestReporter.assertTrue(Regex.match("[0-9]+", chargeId), "The Charge ID ["+chargeId+"] is not numeric as expected.");
		
	}
}
