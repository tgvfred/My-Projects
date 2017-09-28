package com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation;

import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.AccommodationBatchServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class StageModifyData extends AccommodationBatchServicePort {
    private String defaultProcessName = MASS_MODIFY;
    private String processName;

    public StageModifyData(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("stageModifyData")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
        // setProcessName(getLocalDefaultProcessName());
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

    public void setMassModifyRoomDetailAgencyIataNumber(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyIataNumber", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("travelAgency"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("agencyIataNumber"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyIataNumber", value);
        }
    }

    public void setMassModifyRoomDetailAgencyName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("agencyName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyName", value);
        }
    }

    public void setMassModifyRoomDetailAgencyOdsId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyOdsId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("agencyOdsId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agencyOdsId", value);
        }
    }

    public void setMassModifyRoomDetailGuestTravelAgencyId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestTravelAgencyId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("guestTravelAgencyId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestTravelAgencyId", value);
        }
    }

    public void setMassModifyRoomDetailagentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("agentId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/agentId", value);
        }
    }

    public void setMassModifyRoomDetailGuestAgentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestAgentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("guestAgentId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestAgentId", value);
        }
    }

    public void setMassModifyRoomDetailComfirmationLocatorValue(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/confirmationLocatorValue", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("confirmationLocatorValue"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/confirmationLocatorValue", value);
        }
    }

    public void setMassModifyRoomDetailGuestComfirmationLocationId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestConfirmationLocationId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("guestConfirmationLocationId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/guestConfirmationLocationId", value);
        }
    }

    public void setPrimaryAddressLocatorId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/locatorId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("primaryAddress"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/locatorId", value);
        }
    }

    public void setPrimaryAddressGuestLocatorId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/guestLocatorId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency", BaseSoapCommands.ADD_NODE.commandAppend("primaryAddress"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/guestLocatorId", value);
        }
    }

    public void setPrimaryAddressLocatorUseType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/locatorUseType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("locatorUseType"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/locatorUseType", value);
        }
    }

    public void setPrimaryAddressPrimary(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/primary", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("primary"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/primary", value);
        }
    }

    public void setPrimaryAddressLine(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/addressLine1", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/addressLine1", value);
        }
    }

    public void setPrimaryAddressCity(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/city", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("city"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/city", value);
        }
    }

    public void setPrimaryAddressCountry(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/country", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("country"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/country", value);
        }
    }

    public void setPrimaryAddressPostalCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/postalCode", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/postalCode", value);
        }
    }

    public void setPrimaryAddressState(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/state", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress", BaseSoapCommands.ADD_NODE.commandAppend("state"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/travelAgency/primaryAddress/state", value);
        }
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

    public void setMassModifyRoomDetailBlockCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/blockCode", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("blockCode"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/blockCode", value);
        }
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

    public void setRoomReservationGuestReferenceAge(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/roomDetails/", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/roomDetails", BaseSoapCommands.ADD_NODE.commandAppend("bookingDate"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/roomDetails/bookingDate", value);
        }
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
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/inventoryReasonCode", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("inventoryReasonCode"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/inventoryReasonCode", value);
        }
    }

    public void setInventoryReasonContactName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/inventoryContactName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("inventoryContactName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/inventoryContactName", value);
        }
    }

    public void setAdultTicket(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/adultTicket", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail", BaseSoapCommands.ADD_NODE.commandAppend("ticketdetail"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("adultTicket"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/adultTicket", value);
        }
    }

    public void setHardTicketedEvent(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/hardTicketedEvent", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("hardTicketedEvent"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/hardTicketedEvent", value);
        }
    }

    public void setBaseAdmissionProductId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/baseAdmissionProductId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("baseAdmissionProductId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/baseAdmissionProductId", value);
        }
    }

    public void setCode(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/code", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("code"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/code", value);
        }
    }

    public void setComponentId(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/componentId", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("componentId"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/componentId", value);
        }
    }

    public void setDayCount(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/dayCount", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("dayCount"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/dayCount", value);
        }
    }

    public void setAge(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/age", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("guestReference"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("age"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/age", value);
        }
    }

    public void setAgeType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/ageType", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/ageType", value);
        }
    }

    public void setTicketGuestFirstName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest/firstName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference", BaseSoapCommands.ADD_NODE.commandAppend("guest"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest", BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest/firstName", value);
        }
    }

    public void setTicketGuestLastName(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest/lastName", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest", BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/guestReference/guest/lastName", value);
        }
    }

    public void setPartOfPackage(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/partOfPackage", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail", BaseSoapCommands.ADD_NODE.commandAppend("partOfPackage"));
            setRequestNodeValueByXPath("/Envelope/Body/stageModifyData/request/massModifyRoomDetail/ticketdetail/partOfPackage", value);
        }
    }

    public String getResponseProcessId() {
        return getResponseNodeValueByXPath("//processId");
    }

    public String getResponseProcessCode() {
        return getResponseNodeValueByXPath("//processCode");
    }

}
