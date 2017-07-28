package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.AccommodationSalesComponentServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;
import com.disney.utils.exceptions.NoDataFromVirtualTableException;

public class Cancel extends AccommodationSalesComponentServicePort {
    // Instantiates the request with no fields pre-populated
    public Cancel(String environment) {
        super(environment);

        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();

        setRequestNodeValueByXPath("/Envelope/Body/cancel/request", BaseSoapCommands.REMOVE_NODE.toString());
    }

    // Instantiates the request in accordance to a scenario
    public Cancel(String environment, String scenario) {
        super(environment);

        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();

        try {
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        } catch (NoDataFromVirtualTableException e) {
            setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);
            setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        }
    }

    /*
     * Utility Functions
     */
    private void trySetRequestNodeValueByXPath(String xpath, String value) {
        if (getNumberOfRequestNodesByXPath(xpath) > 0) {
            setRequestNodeValueByXPath(xpath, value.isEmpty() ? BaseSoapCommands.REMOVE_NODE.toString() : value);
        } else if (!value.isEmpty()) {
            resolveMissingPath(xpath);
            setRequestNodeValueByXPath(xpath, value);
        }
    }

    private void resolveMissingPath(String xpath) {
        int lastindex = xpath.lastIndexOf('/');
        String parentxpath = xpath.substring(0, lastindex);
        String node = xpath.substring(lastindex + 1, xpath.length()).split("\\[")[0];
        if (getNumberOfRequestNodesByXPath(parentxpath) <= 0) {
            resolveMissingPath(parentxpath);
        }
        setRequestNodeValueByXPath(parentxpath, BaseSoapCommands.ADD_NODE.commandAppend(node));
    }

    private String tryGetResponseNodeValueByXPath(String xpath) {
        return getNumberOfResponseNodesByXPath(xpath) > 0 ? getResponseNodeValueByXPath(xpath) : null;
    }

    /*
     * Request Setters
     */
    public void setCancelDate(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/cancelDate", value);
    }

    public void setExternalReferenceType(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceType", value);
    }

    public void setExternalReferenceCode(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceCode", value);
    }

    public void setExternalReferenceNumber(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceNumber", value);
    }

    public void setExternalReferenceSource(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/externalReferenceDetail/externalReferenceSource", value);
    }

    public void setWaived(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/waived", value);
    }

    public void setTravelComponentGroupingId(String value) {
        trySetRequestNodeValueByXPath("/Envelope/Body/cancel/request/travelComponentGroupingId", value);
    }

    /*
     * Response Getters
     */
    public String getCancellationNumber() {
        return tryGetResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/cancellationNumber");
    }

    public String getTravelPlanId() {
        return tryGetResponseNodeValueByXPath("/Envelope/Body/cancelResponse/response/travelPlanId");
    }
}
