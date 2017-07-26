package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.Checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.accommodationModule.helpers.ValidationHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

//public class checkout_roomOnly_DVC extends AccommodationBaseTest {
//	private CheckInHelper helper;
//	private ThreadLocal<Book> book = new ThreadLocal<Book>();
//	
//	@Override
//	@Parameters("environment")
//	@BeforeMethod(alwaysRun=true)
//	public void setup(String environment){
//		setEnvironment(environment);
//		setDaysOut(0);
//		setNights(1);
//		setArrivalDate(getDaysOut());
//		setDepartureDate(getDaysOut()+getNights());
//		setValues(getEnvironment());
//		//setUseDvcResort(true);
//		//setBook(bookDvcReservation("testBook_MCash", 1));
//        //setTpId(getFirstBooking().getTravelPlanId());
//		bookReservation();
//	}
public class checkout_roomOnly_DVC extends BookDVCCashHelper{
	private CheckInHelper helper;
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
    
    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        
        setUseDvcResort(true);
        setBook(bookDvcReservation("testBook_MCash", 1));
        setTpId(getFirstBooking().getTravelPlanId());
    }
	@Test(groups = { "api", "regression", "checkout", "Accommodation" })
	public void TestCheckout_roomOnly_DVC() {
		
		TestReporter.logScenario("Test Book DVC");
		book.set(new Book(environment, "bookDVCCash" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a DVC Cash reservation", book.get());	
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");

		helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());
        
        ValidationHelper backEndHelper = new ValidationHelper(getEnvironment());
        backEndHelper.validateModificationBackend(1, "Booked", "DVC", getArrivalDate(), getDepartureDate(), "", "", getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), false);
        
        
        String status = "false";
		String tcgId = getBook().getTravelComponentGroupingId();
		String locationId = getLocationId();
		String checkoutDate = Randomness.generateCurrentXMLDate(); 
		String earlyCheckoutReason = "BEREAV";
		String refType = "RESERVATION";
		String refNumber = getExternalRefNumber();
		String refSource = getExternalRefSource();
		
		Checkout checkout = new Checkout(getEnvironment(), "main");
		checkout.setEarlyCheckOutReason(earlyCheckoutReason);
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
}
