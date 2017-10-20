
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCancellationFee extends AccommodationSalesServicePort {

    public RetrieveCancellationFee(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancellationFee")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public RetrieveCancellationFee(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancellationFee")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    public void setTravelPlanSegmentID(String TPS_ID) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/id", TPS_ID);
    }

    /*
     * public String getName() {
     * return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/revenueType/name");
     * }
     */

    public String getRevenueID() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/revenueClassification/id");
    }

    // New setters

    public void setCancelDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/cancelDate", value);
    }

    public void setID(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/id", value);
    }

    public void setReferenceType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail/externalReferenceType", value);
    }

    public void setReferenceCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail/externalReferenceCode", value);
    }

    public void setReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail/externalReferenceNumber", value);
    }

    public void setReferenceSource(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/externalReferenceDetail/externalReferenceSource", value);
    }

    public void setIdentityLevel(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationFee/request/identityDetails/identityLevel", value);
    }

    // New Getters
    public String getOverridePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/overridePrice");
    }

    public String getProductPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/productPrice");
    }

    public String getLevel() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/revenueType/level");
    }

    public String getName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/revenueType/name");
    }

    public String getSellingPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/cancelChargeDetail/sellingPrice");
    }

    public String getFeeName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/feeName");
    }

    public String getWaived() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/waived");
    }

    public String getOverridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/overridden");
    }

    public String getClassName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationFeeResponse/response/revenueClassification/name");
    }
}
