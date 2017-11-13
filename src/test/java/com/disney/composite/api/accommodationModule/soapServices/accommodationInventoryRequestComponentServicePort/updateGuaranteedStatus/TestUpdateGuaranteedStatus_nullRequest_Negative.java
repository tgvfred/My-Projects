// <<<<<<< HEAD
// package com.disney.composite.api.accommodationModule.soapServices.accommodationInventoryRequestComponentServicePort.updateGuaranteedStatus;
//
// import org.testng.annotations.Test;
//
// import com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort.operations.UpdateGuaranteedStatus;
// import com.disney.api.soapServices.accommodationModule.applicationError.LiloSystemErrorCode;
// import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
// import com.disney.api.soapServices.core.BaseSoapCommands;
// import com.disney.utils.Environment;
// import com.disney.utils.TestReporter;
//
// public class TestUpdateGuaranteedStatus_nullRequest_Negative extends AccommodationBaseTest {
//
// @Test(groups = { "api", "regression", "accommodation", "accommodationInventoryRequestComponentService", "updateGuaranteedStatus", "negative" })
// public void testUpdateGuarantedStatus_nullRequest() {
//
// String fault = "Unexpected Error occurred : updateGuaranteedStatus : java.lang.NullPointerException";
// TestReporter.logScenario("Test - UpdateGuaranteedStatus - Null Request");
// UpdateGuaranteedStatus ugs = new UpdateGuaranteedStatus(Environment.getBaseEnvironmentName(getEnvironment()));
//
// ugs.setRequest(BaseSoapCommands.REMOVE_NODE.toString());
//
// ugs.sendRequest();
//
// TestReporter.logAPI(!ugs.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + ugs.getFaultString() + " ]", ugs);
// validateApplicationError(ugs, LiloSystemErrorCode.UNEXPECTED_ERROR_OCCURRED);
//
// }
// }
// =======
//// package com.disney.composite.api.accommodationModule.soapServices.accommodationInventoryRequestComponentServicePort.updateGuaranteedStatus;
////
//// import org.testng.annotations.Test;
////
//// import com.disney.api.soapServices.accommodationModule.accommodationInventoryRequestComponentServicePort.operations.UpdateGuaranteedStatus;
//// import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
//// import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
//// import com.disney.api.soapServices.core.BaseSoapCommands;
//// import com.disney.utils.Environment;
//// import com.disney.utils.TestReporter;
////
//// public class TestUpdateGuaranteedStatus_nullRequest_Negative extends AccommodationBaseTest {
////
//// @Test(groups = { "api", "regression", "accommodation", "accommodationInventoryRequestComponentService", "updateGuaranteedStatus", "negative" })
//// public void testUpdateGuarantedStatus_nullRequest() {
////
//// String fault = "Unexpected Error occurred : updateGuaranteedStatus : java.lang.NullPointerException";
//// TestReporter.logScenario("Test - UpdateGuaranteedStatus - Null Request");
//// UpdateGuaranteedStatus ugs = new UpdateGuaranteedStatus(Environment.getBaseEnvironmentName(getEnvironment()));
////
//// ugs.setRequest(BaseSoapCommands.REMOVE_NODE.toString());
////
//// ugs.sendRequest();
////
//// TestReporter.logAPI(!ugs.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + ugs.getFaultString() + " ]", ugs);
//// validateApplicationError(ugs, AccommodationErrorCode.UNEXPECTED_ERROR_OCCURRED);
////
//// }
//// }
// >>>>>>> 7b9e9af4293411301176ada10ff882ce7de7a885
