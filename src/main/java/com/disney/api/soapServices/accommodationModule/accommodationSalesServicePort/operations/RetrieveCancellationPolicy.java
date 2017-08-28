package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetrieveCancellationPolicy extends AccommodationSalesServicePort {
    public RetrieveCancellationPolicy(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancellationPolicy")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/travelComponentGroupingId", tcg_id);
    }

    public void setTravelPlanSegmentId(String tps_id) {
        setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/travelPlanSegmentId", tps_id);
    }

    public void setBlockCode(String blockCode) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/blockCode", blockCode);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request", BaseSoapCommands.ADD_NODE.commandAppend("blockCode"));
            setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request/blockCode", blockCode);
        }

    }

    public String getcancelFee() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicyResponse/cancellationPolicyResponse/cancelFee");
    }

    public String getPolicyText() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicyResponse/cancellationPolicyResponse/policyText");
    }

    public String getFeeWaived() {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicyResponse/cancellationPolicyResponse/feeWaived");
    }

}