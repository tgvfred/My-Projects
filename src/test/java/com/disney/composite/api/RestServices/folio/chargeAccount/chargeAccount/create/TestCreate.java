package com.disney.composite.api.RestServices.folio.chargeAccount.chargeAccount.create;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")

public class TestCreate {
private String environment = "Development";
	
	/**
	 * This will always be used as is. TestNG will pass in the Environment used
	 * @param environment - Valid environments for active testing are bashful, sleepy and grumpy
	 */
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(@Optional String environment) {
	//this.environment = environment;
	this.environment = "Development";
}
	
	/**
	 * This is a sample template 	
	 * Expected updates
	 * 		- serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
	 * 		- serviceName - name of the service
	 * 		- operationName - name of the operation
	 * 		- OperationName - name of the operation
	 * 		- DataScenario - data scenario used, data sheets can contain multiple scenarios.
	 */
	@Test(groups={"api","rest", "regression", "folio", "chargeAccountV2", "create"})
	public void testcreate(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		String json = "{\"chargeAccountRequests\": [{\"chargeAccountType\":\"GUEST_ACCOUNT\",\"rootChargeAccountRequest\":{\"chargeAccountCommonRequest\":{\"description\":\"From Booking\",\"period\":{\"startDate\":\"2016-08-27T14:34:07-04:00\"},\"active\":\"true\",\"chargeAccountPaymentMethodDetail\":[{\"active\":\"true\",\"paymentMethodName\":\"Visa\",\"paymentMethodTypeName\":\"CreditCard\",\"chargingPrivilegesIndicator\":\"false\",\"isSubAccountPaymentMethod\":\"false\",\"cardDetailTO\":{\"cardStatus\":\"Valid\",\"creditCardNumber\":\"xxxxxxxxxxxx7840\",\"cvvNumber\":\"423\",\"name\":\"Marisol Centeno\",\"address\":{\"addressLineOne\":\"3395 NE 9th Dr\",\"city\":\"Homestead\",\"country\":\"US\",\"postalCode\":\"33033\",\"state\":\"FL\"},\"cardAuthorizationDetailTO\":{\"expirationMonth\":\"6\",\"expirationYear\":\"20\",\"retrievalReferenceNumber\":\"423730003693\",\"retrievalReferenceNumberKey\":\"HGBkD8\"}}}],\"guestInfoTO\":[{  \"firstName\":\"Marisol\",\"lastName\":\"Centeno\",\"txnGuestId\":\"0\",\"externalReference\":[{\"referenceName\":\"SWID\",\"referenceValue\":\"{9AA2F8BE-530B-4BAB-93ED-7758B06AC4A3}\"}]}]}}}]}";		
		RestResponse response= Rest.folio("Development").chargeAccountService().chargeAccount().retrieveGuests().sendPutRequest(json);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	}
}
