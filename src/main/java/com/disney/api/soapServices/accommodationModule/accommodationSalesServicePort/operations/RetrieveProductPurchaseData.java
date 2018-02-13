package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class RetrieveProductPurchaseData extends AccommodationSalesServicePort {
    public RetrieveProductPurchaseData(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProductPurchaseData")));
        // generateServiceContext();setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/retrieveProductPurchaseData/retrieveProductPurchaseDataInputs.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public String getPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/priceDetail/price");
    }

    public String getStartDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/retrieveProductSaleInformationDetail/useYear/startDate");
    }

    public String getEndDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/retrieveProductSaleInformationDetail/useYear/endDate");
    }

    public String getproductId() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/productDetail/productId");
    }

    public String getproductTypeName() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/productDetail/productTypeName");
    }

    public void setProductClassName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/productClassName", value);
    }

    public void setUseYearListStartDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/useYearList/startDate", value);
    }

    public void setUseYearListEndDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/useYearList/endDate", value);
    }

    public void setProductId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/productId", value);
    }

    public void setIncludePolicies(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/includePolicies", value);
    }

    public void setBookDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/bookDate", value);
    }

    public void setUsageDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/usageDate", value);
    }

    public void setNumberOfUnits(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/numberOfUnits", value);
    }

    public void setMembershipId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/membershipId", value);
    }

    public void setAgeType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseData/request/ageType", value);
    }
}