package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cCheckout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Add;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AddAccommodationHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CancelHelper;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.AddBundleHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.date.DateTimeConversion;

public class checkout_Cancel extends AccommodationBaseTest {
	private CheckInHelper checkInHelper;
	private AddAccommodationHelper accommHelper;
	
	public String validateResMgmt(String TcId) {
		String tcId = getBook().getTravelComponentId();

		TestReporter.logStep("Verify Res Mgmt");
		String sql = "select c.* " + " from res_mgmt.tps a "
				+ " left outer join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
				+ " left outer join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb " + " where tc_id = "
				+ getBook().getTravelComponentId();
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));

		// rs.print();
		String assignOwnerId = null;
		for (int i = 1; i <= rs.getRowCount(); i++) {
			if (rs.getValue("TC_ID", i).equals(TcId)) {
				assignOwnerId = rs.getValue("ASGN_OWN_ID");

				TestReporter.softAssertTrue(rs.getValue("TC_ID").equals(tcId), "Verify TcId is set");
			}
			TestReporter.assertAll();
		}
		return assignOwnerId;
	}
	public void validateRIM(String assignOwnerId) {
		TestReporter.logStep("Validate RIM");
		String sql = " Select RSRC_INVTRY_TYP_ID, AUTO_ASGN_RSRC_ID, OWNR_STS_NM, ASGN_OWNR_ID "
				+ " From rsrc_inv.RSRC_ASGN_OWNR " + " Where ASGN_OWNR_ID = " + assignOwnerId;
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();

		for (int i = 1; i <= rs.getRowCount(); i++) {
			if (rs.getValue("ASGN_OWNR_ID", i).equals(assignOwnerId)) {

				TestReporter.assertNotNull(assignOwnerId, "The assigned Owner field was not null");
				TestReporter.assertTrue(rs.getValue("ASGN_OWNR_ID").equals(assignOwnerId),
						"verify assigned owner is set");
			}
		}
	}
	public void validateFolio(String TcgId) {
		TestReporter.logStep("Verify Folio");
		String sql = "select FOLIO_STS_NM " + " from folio.EXTNL_REF a "
				+ " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
				+ " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
				+ " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
				+ " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
				+ " left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID "
				+ " left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID " + " where a.EXTNL_REF_VAL in ('"
				+ getBook().getTravelPlanId() + "','" + getBook().getTravelPlanSegmentId() + "','"
				+ getBook().getTravelComponentGroupingId() + "')" +
				// "where a.EXTNL_REF_VAL in
				// ('472059709301','472051342254','472052990544')" +
				" and folio_sts_nm is not null ";
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();
		String folioStat = "Earned";
		for (int i = 1; i <= rs.getRowCount(); i++) {
			if (rs.getValue("FOLIO_STS_NM", i).equals(folioStat)) {
				TestReporter.assertNotNull(rs.getValue("FOLIO_STS_NM"), "Verify the status is populated with a value.");
				TestReporter.assertEquals(rs.getValue("FOLIO_STS_NM"), folioStat,
						"Verify that the folio status matches the status in the database.");
			}
		}
	}

	@Override
	@Parameters("environment")
	@BeforeMethod(alwaysRun = true)
	public void setup(String environment) {
		setEnvironment(environment);
		setDaysOut(0);
		setNights(1);
		setArrivalDate(getDaysOut());
		setDepartureDate(getDaysOut() + getNights());
		setValues(getEnvironment());
		bookReservation();
	}
	 
	@Test(groups = { "api", "regression", "checkout", "Accommodation", "debug" })
	public void TestCheckout_roomOnly_multAccomm_cancelOne_checkInOne_checkoutOne() {
		//Add an accommodation
		TestReporter.logScenario("Add Accommodation");
		accommHelper = new AddAccommodationHelper(getEnvironment(), getBook());
		Add add = accommHelper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(), getLocationId());

		//Cancel One
        TestReporter.logScenario("Cancel");
        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation.", cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");
    	
        //Checkin One and then Checkout One 
        TestReporter.logScenario("Checkin One");
        checkInHelper = new CheckInHelper(getEnvironment(), add);
        checkInHelper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
		
        TestReporter.logScenario("Checkout One");
        checkInHelper.checkOut(getLocationId());
         
        //Validations
        String assignOwnerId =  validateResMgmt(getBook().getTravelComponentId());		    
		validateRIM(assignOwnerId);
		validateFolio(getBook().getTravelComponentGroupingId());
	}

}
		
