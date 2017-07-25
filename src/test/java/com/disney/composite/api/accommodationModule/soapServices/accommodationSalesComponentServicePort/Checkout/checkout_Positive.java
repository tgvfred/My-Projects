package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.Checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AddAccommodationHelper;

import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
//import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.accommodationSales.AddAccommodationHelper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class checkout_Positive extends AccommodationBaseTest {
	private CheckInHelper helper;
	private AddAccommodationHelper accommHelper;

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
		setIsWdtcBooking(true);
		setAddNewGuest(true);
		bookReservation();
	}

	public String validateResMgmt(String TcId, Checkout checkout) {
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

	public void validateRIM(String assignOwnerId, Checkout checkout) {
		// String assignOwnerIdValue = assignOwnerId;
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

	public void validateFolio(String TcgId, Checkout checkout) {
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

	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_wdtc() {

		helper = new CheckInHelper(getEnvironment(), getBook());
		helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

		String tcgId = getBook().getTravelComponentGroupingId();
		String checkoutDate = Randomness.generateCurrentXMLDate();
		String refNumber = getExternalRefNumber();
		String refSource = getExternalRefSource();

		Checkout checkout = new Checkout(getEnvironment(), "main");
		checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setTravelComponentGroupingId(tcgId);
		checkout.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setExternalReferenceNumber(refNumber);
		checkout.setExternalReferenceSource(refSource);
		checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setCheckoutDate(checkoutDate);
		checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();

		String assignOwnerId = validateResMgmt(getBook().getTravelComponentId(), checkout);
		validateRIM(assignOwnerId, checkout);
		validateFolio(getBook().getTravelComponentGroupingId(), checkout);
	}

	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_roomOnly_multAccomm_checkInBoth_checkoutOne() {
		accommHelper = new AddAccommodationHelper(getEnvironment(), getBook());
		accommHelper.addAccommodation(getResortCode(), getRoomTypeCode(), getPackageCode(), getDaysOut(), getNights(),
				getLocationId());
		helper = new CheckInHelper(getEnvironment(), getBook());
		helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
		
		String status = "false";
		String tcgId = getBook().getTravelComponentGroupingId();
		String locationId = getLocationId();
		String checkoutDate = Randomness.generateCurrentXMLDate();
		String refType = "RESERVATION";
		String refNumber = getExternalRefNumber();
		String refSource = getExternalRefSource();

		Checkout checkout = new Checkout(getEnvironment(), "main");
		checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setIsBellServiceRequired(status);
		checkout.setIsSameRoomNumberAssigned(status);
		checkout.setTravelComponentGroupingId(tcgId);
		checkout.setExternalReferenceType(refType);
		checkout.setExternalReferenceNumber(refNumber);
		checkout.setExternalReferenceSource(refSource);
		checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setCheckoutDate(checkoutDate);
		checkout.setLocationId(locationId);
		checkout.sendRequest();
		
	}

	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_roomOnly_multAccomm_checkInOne_checkoutOne() {

	}

	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_roomOnly_shared_checkoutOne() {

	}


	//@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_bundle() {
		/*bundleHelper = new AddBundleHelper(Environment.getBaseEnvironmentName(getEnvironment()), getHouseHold());
		bundleHelper.addBundle(getBook().getTravelPlanId(), getDaysOut());*/
		
		String status = "false";
		String tcgId = getBook().getTravelComponentGroupingId();
		String locationId = getLocationId();
		String checkoutDate = Randomness.generateCurrentXMLDate();

		String refType = "RESERVATION";
		String refNumber = getExternalRefNumber();
		String refSource = getExternalRefSource();

		Checkout checkout = new Checkout(getEnvironment(), "main");
		checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setIsBellServiceRequired(status);
		checkout.setIsSameRoomNumberAssigned(status);
		checkout.setTravelComponentGroupingId(tcgId);
		checkout.setExternalReferenceType(refType);
		checkout.setExternalReferenceNumber(refNumber);
		checkout.setExternalReferenceSource(refSource);
		checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setCheckoutDate(checkoutDate);
		checkout.setLocationId(locationId);
		checkout.sendRequest();

	}

	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_tcExtRefOnly() {

	}

}
