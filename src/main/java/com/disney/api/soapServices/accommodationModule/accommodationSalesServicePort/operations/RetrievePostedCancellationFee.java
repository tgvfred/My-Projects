package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrievePostedCancellationFee extends AccommodationSalesServicePort {
    public RetrievePostedCancellationFee(String environment, String scenario) {

        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePostedCancellationFee")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/retrievePostedCancellationFee/retrievePostedCancellationFeeInputs.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    // Request

    public void setCancelDate(String Value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/cancelDate", Value);

    }

    public void setid(String Value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/identityDetails/id", Value);

    }

    public void setid(String Value, String index) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/identityDetails[" + index + "]/id", Value);

    }

    public void setIdentityDetails(String Value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/identityDetails", Value);

    }

    public void setIdentityLevel(String Value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFee/request/identityDetails/identityLevel", Value);

    }

    // Response

    public String getWaived() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/waived");
    }

    public String getOverridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/overridden");
    }

    public String getOverridePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/cancelChargeDetail/overridePrice");
    }

    public String getProductPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/cancelChargeDetail/productPrice");
    }

    public String getSellingPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePostedCancellationFeeResponse/response/cancelChargeDetail/sellingPrice");
    }
}
