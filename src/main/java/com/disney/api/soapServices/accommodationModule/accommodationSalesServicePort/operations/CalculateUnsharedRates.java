package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class CalculateUnsharedRates extends AccommodationSalesServicePort {
    public CalculateUnsharedRates(String environment, String scenario) {
        super(environment);
        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("calculateUnsharedRates")));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        generateServiceContext();
    }

    // setters
    public void setUnsharedChainUnsharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainUnsharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedChainSharedRoomDetailTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedChainShareRoomDetailsTPSId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/travelPlanSegmentId", value);
    }

    public void setUnsharedAccommodationUnSharedRoomDetailsTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedAccommodationUnSharedRoomDetailsTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedAccommodationSharedRoomDetailsTCGId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId", value);
    }

    public void setUnsharedAccommodationSharedRoomDetailsTCId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId", value);
    }

    public void setUnsharedAccommodationTPSId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/travelPlanSegmentId", value);
    }

    // getters
    public String getfreezeId() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/freezeId");
    }

    public String getinventoryStatus() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/inventoryStatus");
    }

    public String getUnsharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getUnsharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getUnsharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/basePrice");
    }

    public String getUnsharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/date");
    }

    public String getUnsharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/dayCount");
    }

    public String getUnsharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/overidden");
    }

    public String getUnsharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/shared");
    }

    public String getUnsharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/netPrice");
    }

    public String getUnsharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/rateDetails/pointsValue");
    }

    public String getSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/basePrice");
    }

    public String getSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/date");
    }

    public String getSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/dayCount");
    }

    public String getSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/overidden");
    }

    public String getSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/shared");
    }

    public String getSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/netPrice");
    }

    public String getSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/rateDetails/pointsValue");
    }

    public String getShareChainSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getShareChainSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getShareChainSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/basePrice");
    }

    public String getShareChainSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/date");
    }

    public String getShareChainSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/dayCount");
    }

    public String getShareChainSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/overidden");
    }

    public String getShareChainSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/shared");
    }

    public String getShareChainSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/netPrice");
    }

    public String getShareChainSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/rateDetails/pointsValue");
    }

    public String getUnSharedRoomDetailsAdditionalCharge() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/additionalCharge");
    }

    public String getUnSharedRoomDetailsAdditionalChargeOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
    }

    public String getUnSharedRoomDetailsBasePrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/basePrice");
    }

    public String getUnSharedRoomDetailsDate() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/date");
    }

    public String getUnSharedRoomDetailsDayCount() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/dayCount");
    }

    public String getUnSharedRoomDetailsOveridden() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/overidden");
    }

    public String getUnSharedRoomDetailsShared() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/shared");
    }

    public String getUnSharedRoomDetailsNetPrice() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/netPrice");
    }

    public String getUnSharedRoomDetailsPointsValue() {
        return getResponseNodeValueByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/rateDetails/pointsValue");
    }
}