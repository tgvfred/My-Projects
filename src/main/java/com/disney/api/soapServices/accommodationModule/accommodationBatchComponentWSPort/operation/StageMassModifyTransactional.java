package com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.AccommodationBatchComponentWSPort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
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

    public void setInventoryReasonCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/inventoryReasonCode", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("inventoryReasonCode"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/inventoryReasonCode", value);
        }
    }

    public void setInventoryReasonContactName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/inventoryContactName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("inventoryContactName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/inventoryContactName", value);
        }
    }

    public void setAdultTicket(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/adultTicket", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("ticketdetail"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("adultTicket"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/adultTicket", value);
        }
    }

    public void setHardTicketedEvent(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/hardTicketedEvent", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("hardTicketedEvent"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/hardTicketedEvent", value);
        }
    }

    public void setBaseAdmissionProductId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/baseAdmissionProductId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("baseAdmissionProductId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/baseAdmissionProductId", value);
        }
    }

    public void setCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/code", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("code"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/code", value);
        }
    }

    public void setComponentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/componentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("componentId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/componentId", value);
        }
    }

    public void setDayCount(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/dayCount", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("dayCount"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/dayCount", value);
        }
    }

    public void setAge(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/age", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("guestReference"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("age"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/age", value);
        }
    }

    public void setAgeType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/ageType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/ageType", value);
        }
    }

    public void setTicketGuestFirstName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/firstName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("guest"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/firstName", value);
        }
    }

    public void setTicketGuestLastName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/lastName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/guestReference/guest/lastName", value);
        }
    }

    public void setPartOfPackage(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/partOfPackage", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("partOfPackage"));
            setRequestNodeValueByXPath("/Envelope/Body/stageMassModifyTransactional/massModifyRequest/massModifyRoomDetail/ticketdetail/partOfPackage", value);
        }
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
