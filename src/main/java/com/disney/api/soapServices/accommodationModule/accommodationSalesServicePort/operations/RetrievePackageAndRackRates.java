package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrievePackageAndRackRates extends AccommodationSalesServicePort {
    public RetrievePackageAndRackRates(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePackageAndRackRates")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public RetrievePackageAndRackRates(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePackageAndRackRates")));
        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }

    public void setaccomComponentId(String tc_id) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/accomComponentId", tc_id);
    }

    public void setTravelPlanSegementId(String tps_id) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/travelPlanSegmentId", tps_id);
    }

    public void setPackageCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/packageCode", value);

    }

    public void setRequest(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request", value);
    }

    public void setPartyMix(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/partyMix", value);
    }

    public void setRoomTypeTO(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/roomTypeTO", value);
    }

    public void setResortPeriod(String value) {

        setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/resortPeriod", value);

    }

    public String getdate() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRatesResponse/response/packageRackRates/date");
    }

    public String getpackagePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRatesResponse/response/packageRackRates/packagePrice");
    }

}
