package com.disney.api.soapServices.accommodationModule.helpers;

import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.getAgeTypeByAge;
import static com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest.isValid;

import com.disney.AutomationException;
import com.disney.api.WebService;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.packagingService.operations.FindTicketPriceGridByPackage;
import com.disney.api.soapServices.pricingModule.packagingService.operations.GetTicketProducts;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TicketsHelper {
    private String environment;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;
    private String packageCode;
    private WebService ws;
    private String ticketGroupName;
    private String admissionProductId;
    private String code;
    private Boolean mediaCustomizationOtpOut;
    private Boolean adultTicket;
    private String baseXpath;
    private BaseSoapService bs;
    private int numTickets;
    private Boolean baseInfoEstablished;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public WebService getWs() {
        return ws;
    }

    public void setWs(WebService ws) {
        this.ws = ws;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getTcgId() {
        return tcgId;
    }

    public void setTcgId(String tcgId) {
        this.tcgId = tcgId;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getTicketGroupName() {
        return ticketGroupName;
    }

    public void setTicketGroupName(String ticketGroupName) {
        this.ticketGroupName = ticketGroupName;
    }

    public String getAdmissionProductId() {
        return admissionProductId;
    }

    public void setAdmissionProductId(String admissionProductId) {
        this.admissionProductId = admissionProductId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getMediaCustomizationOtpOut() {
        return mediaCustomizationOtpOut;
    }

    public void setMediaCustomizationOtpOut(Boolean mediaCustomizationOtpOut) {
        this.mediaCustomizationOtpOut = mediaCustomizationOtpOut;
    }

    public Boolean getAdultTicket() {
        return adultTicket;
    }

    public void setAdultTicket(Boolean adultTicket) {
        this.adultTicket = adultTicket;
    }

    public Boolean getBaseInfoEstablished() {
        return baseInfoEstablished;
    }

    public void setBaseInfoEstablished(Boolean baseInfoEstablished) {
        this.baseInfoEstablished = baseInfoEstablished;
    }

    public TicketsHelper(String environment, WebService ws, String packageCode) {
        if (!isValid(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (!isValid(packageCode)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setPackageCode(packageCode);
        }
        if (!isValid(ws)) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setWs(ws);
        }
    }

    public TicketsHelper(String environment, WebService ws) {
        if (!isValid(environment)) {
            throw new AutomationException("The environment field cannot be null or empty.");
        } else {
            setEnvironment(environment);
        }
        if (!isValid(ws)) {
            throw new AutomationException("The book object cannot be null.");
        } else {
            setWs(ws);
        }
    }

    public void determineTicketCode(String ticketDescription) {
        if (isValid(packageCode)) {
            findTicketPriceGridByPackage();
        } else {
            setTicketGroupName("634");
        }

        GetTicketProducts get = getTicketProducts(ticketDescription);
        setCode(get.getCodeByTicketDescription(ticketDescription));
    }

    public void determineTicketCode(String ticketDescription, String ageType) {
        findTicketPriceGridByPackage();

        GetTicketProducts get = getTicketProducts(ticketDescription);
        setCode(get.getCodeByTicketDescriptionAndAgeType(ticketDescription, ageType));
    }

    public void findTicketPriceGridByPackage() {
        FindTicketPriceGridByPackage find = new FindTicketPriceGridByPackage(getEnvironment());
        find.setPackageCode(packageCode);
        find.sendRequest();
        TestReporter.assertTrue(find.getResponseStatusCode().equals("200"), "Verify that no error occurred finding tickets for package code [" + packageCode + "].");
        setTicketGroupName(find.getTicketGroupName());
    }

    public GetTicketProducts getTicketProducts(String ticketDescription) {
        GetTicketProducts get = new GetTicketProducts(getEnvironment(), "Main");
        get.setTicketGroupName(getTicketGroupName());
        get.sendRequest();
        TestReporter.assertTrue(get.getResponseStatusCode().equals("200"), "Verify that no error occurred finding ticket products for ticket group name [" + getTicketGroupName() + "].");
        setAdmissionProductId(get.getAdmissionProductIdByTicketDescription(ticketDescription));
        return get;
    }

    private void determineBaseInfo() {
        baseXpath = null;
        String baseTicketNode = null;
        bs = null;
        if (ws instanceof ReplaceAllForTravelPlanSegment) {
            bs = (BaseSoapService) ws;
            baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails";
            if (!isValid(getBaseInfoEstablished())) {
                numTickets = bs.getNumberOfRequestNodesByXPath(baseXpath + "/ticketGroup");
                try {
                    bs.setRequestNodeValueByXPath(baseXpath + "/ticketGroup", getTicketGroupName());
                } catch (XPathNotFoundException e) {
                    bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ticketGroup"));
                    bs.setRequestNodeValueByXPath(baseXpath + "/ticketGroup", getTicketGroupName());
                }
            }
            baseTicketNode = "ticketDetails";
        } else {

        }
        if (!isValid(getBaseInfoEstablished())) {
            bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend(baseTicketNode));
            numTickets++;
        }

        baseXpath = "/Envelope/Body/replaceAllForTravelPlanSegment/request/roomDetails/" + baseTicketNode;
        setBaseInfoEstablished(true);
    }

    public void addTickets(String ticketDescription, Guest guest) {
        if (!isValid(getCode())) {
            determineTicketCode(ticketDescription);
        }
        determineBaseInfo();

        baseXpath = baseXpath + "[" + numTickets + "]";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("adultTicket"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("hardTicketedEvent"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("baseAdmissionProductId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("code"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("componentId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("dayCount"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("partOfPackage"));
        int isPkg = bs.getNumberOfRequestNodesByXPath("//replaceAllForTravelPlanSegment/request/externalReference");
        if (isPkg > 0) {
            bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("selectable"));
        }
        // Add an guestReference node
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestReference"));
        baseXpath = baseXpath + "/guestReference";
        String tempXpath = baseXpath;
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("age"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("ageType"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("correlationID"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("experienceMediaDetails"));

        // Add an guest node
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guest"));
        baseXpath = baseXpath + "/guest";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("title"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("firstName"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("lastName"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("middleName"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("partyId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("doNotMailIndicator"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("doNotPhoneIndicator"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("preferredLanguage"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("dclGuestId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("active"));

        // Add a phoneDetails node
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("phoneDetails"));
        baseXpath = baseXpath + "/phoneDetails";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorUseType"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("deviceType"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("extension"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("number"));

        // Add an addressDetails node
        baseXpath = tempXpath + "/guest";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressDetails"));
        baseXpath = tempXpath + "/guest/addressDetails";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("addressLine1"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("city"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("country"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("postalCode"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("state"));

        // Add an emailDetails node
        baseXpath = tempXpath + "/guest";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("emailDetails"));
        baseXpath = tempXpath + "/guest/emailDetails";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("locatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("guestLocatorId"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("primary"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("address"));

        // Add an experienceMediaDetails node
        baseXpath = tempXpath + "/experienceMediaDetails";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("id"));
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("mediaCustomization"));
        baseXpath = baseXpath + "/mediaCustomization";
        bs.setRequestNodeValueByXPath(baseXpath, BaseSoapCommands.ADD_NODE.commandAppend("optOut"));

        setTickets(ticketDescription, guest);
    }

    public void setTickets(String ticketDescription, Guest guest) {
        if (!isValid(getCode())) {
            determineTicketCode(ticketDescription);
        } else {

        }
        determineBaseInfo();

        baseXpath = baseXpath + "[" + numTickets + "]";
        if (isValid(getAdultTicket()) && getAdultTicket() == true) {
            bs.setRequestNodeValueByXPath(baseXpath + "/adultTicket", "true");
        } else {
            bs.setRequestNodeValueByXPath(baseXpath + "/adultTicket", "false");
        }
        bs.setRequestNodeValueByXPath(baseXpath + "/hardTicketedEvent", "false");
        bs.setRequestNodeValueByXPath(baseXpath + "/baseAdmissionProductId", getAdmissionProductId());
        bs.setRequestNodeValueByXPath(baseXpath + "/code", getCode());
        bs.setRequestNodeValueByXPath(baseXpath + "/componentId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/dayCount", "2");
        bs.setRequestNodeValueByXPath(baseXpath + "/partOfPackage", "false");
        try {
            bs.setRequestNodeValueByXPath(baseXpath + "/selectable", "true");
        } catch (XPathNotFoundException ex) {
        }

        baseXpath = baseXpath + "/guestReference";
        String tempXpath = baseXpath;
        bs.setRequestNodeValueByXPath(baseXpath + "/age", guest.getAge());
        bs.setRequestNodeValueByXPath(baseXpath + "/ageType", getAgeTypeByAge(guest.getAge()));
        bs.setRequestNodeValueByXPath(baseXpath + "/correlationID", "0");

        // Add an guest node
        baseXpath = baseXpath + "/guest";
        bs.setRequestNodeValueByXPath(baseXpath + "/title", guest.getTitle());
        bs.setRequestNodeValueByXPath(baseXpath + "/firstName", guest.getFirstName());
        bs.setRequestNodeValueByXPath(baseXpath + "/lastName", guest.getLastName());
        bs.setRequestNodeValueByXPath(baseXpath + "/middleName", guest.getMiddleName());
        bs.setRequestNodeValueByXPath(baseXpath + "/partyId", guest.getPartyId());
        bs.setRequestNodeValueByXPath(baseXpath + "/doNotMailIndicator", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/doNotPhoneIndicator", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/preferredLanguage", guest.getLanguagePreference());
        bs.setRequestNodeValueByXPath(baseXpath + "/dclGuestId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/guestId", guest.getGuestId());
        bs.setRequestNodeValueByXPath(baseXpath + "/active", "0");

        // Add a phoneDetails node
        baseXpath = baseXpath + "/phoneDetails";
        bs.setRequestNodeValueByXPath(baseXpath + "/locatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/guestLocatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/locatorUseType", "HOUSEHOLD");
        bs.setRequestNodeValueByXPath(baseXpath + "/primary", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/deviceType", "HANDSET");
        bs.setRequestNodeValueByXPath(baseXpath + "/extension", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/number", guest.primaryPhone().getNumber());

        // Add an addressDetails node
        baseXpath = tempXpath + "/guest/addressDetails";
        bs.setRequestNodeValueByXPath(baseXpath + "/locatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/guestLocatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/primary", "1");
        bs.setRequestNodeValueByXPath(baseXpath + "/addressLine1", guest.primaryAddress().getAddress1());
        bs.setRequestNodeValueByXPath(baseXpath + "/city", guest.primaryAddress().getCity());
        bs.setRequestNodeValueByXPath(baseXpath + "/country", guest.primaryAddress().getCountry());
        bs.setRequestNodeValueByXPath(baseXpath + "/postalCode", guest.primaryAddress().getZipCode());
        bs.setRequestNodeValueByXPath(baseXpath + "/state", guest.primaryAddress().getState());

        // Add an emailDetails node
        baseXpath = tempXpath + "/guest/emailDetails";
        bs.setRequestNodeValueByXPath(baseXpath + "/locatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/guestLocatorId", "0");
        bs.setRequestNodeValueByXPath(baseXpath + "/primary", "true");
        bs.setRequestNodeValueByXPath(baseXpath + "/address", guest.primaryEmail().getEmail());

        // Add an experienceMediaDetails node
        baseXpath = tempXpath + "/experienceMediaDetails";
        bs.setRequestNodeValueByXPath(baseXpath + "/id", "0");
        baseXpath = baseXpath + "/mediaCustomization";
        if (isValid(getMediaCustomizationOtpOut()) && getMediaCustomizationOtpOut() == true) {
            bs.setRequestNodeValueByXPath(baseXpath + "/optOut", "true");
        } else {
            bs.setRequestNodeValueByXPath(baseXpath + "/optOut", "false");
        }

    }
}