package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.utils.XMLTools;

public class StageMassModifyTransactional extends AccommodationBatchComponentWSPort {
    private String defaultProcessName = MASS_MODIFY;
    private String processName;

    public StageMassModifyTransactional(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageMassModifyTransactional")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        setProcessName(getLocalDefaultProcessName());
    }

    public String getLocalProcessName() {
        return processName;
    }

    public void setLocalProcessName(String processName) {
        this.processName = processName;
    }

    public String getLocalDefaultProcessName() {
        return defaultProcessName;
    }

    public void setProcessName(String value) {
        setRequestNodeValueByXPath("//processName", value);
    }

    public void setMassModifyRoomDetailTcId(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/tcId", value);
    }

    public void setMassModifyRoomDetailTpsId(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/tpsId", value);
    }

    public void setMassModifyRoomDetailTcgID(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/tcgId", value);
    }

    public void setMassModifyRoomDetailPackageCode(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/packageCode", value);
    }

    public void setMassModifyRoomDetailResortCode(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/resortCode", value);
    }

    public void setMassModifyRoomDetailRoomType(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/roomType", value);
    }

    public void setMassModifyRoomDetailConfirmationIndicator(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/confirmationIndicator", value);
    }

    public void setMassModifyRoomDetailPeriodEndDates(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/period/endDate", value);
    }

    public void setMassModifyRoomDetailPeriodStartDate(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/period/startDate", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailFirstName(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/firstName", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailLastName(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/lastName", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailPartyId(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/partyId", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailDoNotMail(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/doNotMailIndicator", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/doNotPhoneIndicator", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/preferredLanguage", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailGuestId(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/guestId", value);
    }

    public void setMassModifyRoomDetailPrimaryGuestDetailACtive(String value) {
        setRequestNodeValueByXPath("//massModifyRoomDetail/primaryGuestDetail/active", value);
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
