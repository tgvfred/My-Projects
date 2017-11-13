package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class UpgradeResortRoomType extends AccommodationFulfillmentServicePort {

    public UpgradeResortRoomType(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("upgradeResortRoomType")));

        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

        removeComments();
        removeWhiteSpace();
    }

    public UpgradeResortRoomType(String environment) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("upgradeResortRoomType")));

        generateServiceContext();

        removeComments();
        removeWhiteSpace();
    }

    public void setStartDate(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/startDate", value);
    }

    public void setEndDate(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/endDate", value);
    }

    public void setTcg(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/travelComponentGroupingId", value);
    }

    public void setTc(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/travelComponentId", value);
    }

    public void setRoomNumber(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/roomNumber", value);
    }

    public void setPackageCode(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/packageCode", value);
    }

    public void setFacilityId(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/facilityId", value);
    }

    public void setUpgradeRoomDetailLocationId(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/locationId", value);
    }

    public void setInventoryOverrideReason(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/inventoryOverrideReason", value);
    }

    public void setInventoryOverrideContactName(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/inventoryOverrideContactName", value);
    }

    public void setRoomTypeCode(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/roomTypeCode", value);
    }

    public void setFreezeId(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/freezeId", value);
    }

    public void setBypassFreeze(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/bypassFreeze", value);
    }

    public void setUpgradeReason(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/upgradeReason", value);
    }

    public void setCommunicationChannel(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/communicationChannel", value);
    }

    public void setSalesChannel(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/salesChannel", value);
    }

    public void setSpecialNeedsRequested(String value) {
        setRequestNodeValueByXPath("//upgradeRoomDetail/specialNeedsRequested", value);
    }

    public void setAuthorizationCode(String value) {
        setRequestNodeValueByXPath("//upgradeChargeDetail/authorizationCode", value);
    }

    public void setChargeToGuest(String value) {
        setRequestNodeValueByXPath("//upgradeChargeDetail/chargeToGuest", value);
    }

    public void setNoCharge(String value) {
        setRequestNodeValueByXPath("//upgradeChargeDetail/noCharge", value);
    }

    public void setScore(String value) {
        setRequestNodeValueByXPath("//score", value);
    }

    public void setScoringCriteria(String value) {
        setRequestNodeValueByXPath("//scoringCriteria", value);
    }

    public void setUnassignedRoom(String value) {
        setRequestNodeValueByXPath("//unassignedRoom", value);
    }

    public void setLocationId(String value) {
        setRequestNodeValueByXPath("//request/locationId", value);
    }

    public void setLocationIdString(String value) {

        setRequestNodeValueByXPath("//upgradeRoomDetail/locationId", value);

    }

}
