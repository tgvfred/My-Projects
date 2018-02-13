package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.AutomationException;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5. Using in DVCSales
 * @author phlej001
 *
 */
@Deprecated
public class SearchForLinking extends AccommodationSalesServicePort {
    public SearchForLinking(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchForLinking")));
        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();

    }

    public void setTravelComponentGroupingId(String tcg_id) {
        setRequestNodeValueByXPath("/Envelope/Body/searchForLinking/travelComponentGroupingId", tcg_id);
    }

    public void setMembershipCrossReferenceNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/searchForLinking/membershipCrossReferenceNumber", value);
    }

    public String getRequestTravelComponentGroupingId() {
        return getRequestNodeValueByXPath("/Envelope/Body/searchForLinking/travelComponentGroupingId");
    }

    public String getRequestMembershipCrossReferenceNumber() {
        return getRequestNodeValueByXPath("/Envelope/Body/searchForLinking/membershipCrossReferenceNumber");
    }

    public String getResponseTpId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/travelPlanId");
    }

    public String getResponseTpsId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/travelPlanSegmentId");
    }

    public String getResponseAccommodationDetailComponentId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/componentId");
    }

    public String getResponseAccommodationDetailFacilityId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/facilityId");
    }

    public String getResponseAccommodationDetailGroupTeamId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/groupTeamId");
    }

    public String getResponseAccommodationDetailPackageProdId(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/packageProductId");
    }

    public String getResponseAccommodationDetailRsr(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/RSR");
    }

    public String getResponseAccommodationDetailWholesaler(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/wholesaler");
    }

    public String getResponseAccommodationDetailRooomTypeCode(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/roomTypeCode");
    }

    public String getResponseAccommodationDetailTotalPkgPrice(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/totalPackagePrice");
    }

    public String getResponseAccommodationDetailResType(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/reservationType");
    }

    public String getResponseAccommodationDetailExtRefNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/externalReferenceDetails/externalReferenceNumber");
    }

    public String getResponseAccommodationDetailExtRefSource(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/externalReferenceDetails/externalReferenceSource");
    }

    public String getResponseAccommodationDetailPeriodEndDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/period/endDate");
    }

    public String getResponseAccommodationDetailPeriodStartDate(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/period/startDate");
    }

    public String getResponseAccommodationDetailPartyMixAdults(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/partyMixInfo/numberOfAdults");
    }

    public String getResponseAccommodationDetailPartyMixChildren(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/searchForLinkingResponse/linkableAccommodations[" + index + "]/accommodationDetail/partyMixInfo/numberOfChildren");
    }

    public int findAccommIndexByTps(String tpsId) {
        int tpsNode = 0;
        NodeList nList = XMLTools.getNodeList(XMLTools.makeXMLDocument(this.getResponse()), "/Envelope/Body/searchForLinkingResponse/linkableAccommodations/travelPlanSegmentId");
        for (int i = 1; i <= nList.getLength(); i++) {
            if (nList.item(i - 1).getTextContent().equals(tpsId)) {
                tpsNode = i;
                break;
            }
        }
        if (tpsNode == 0) {
            throw new AutomationException("No accommodation was found with the TPS ID [" + tpsId + "].");
        }
        return tpsNode;
    }

    public int getNumberOfLinkableAccommodations() {
        try {
            return getLinkableAccommodations().getLength();
        } catch (XPathNotFoundException e) {
            return 0;
        }
    }

    public NodeList getLinkableAccommodations() {
        return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchForLinkingResponse/linkableAccommodations");
    }
}