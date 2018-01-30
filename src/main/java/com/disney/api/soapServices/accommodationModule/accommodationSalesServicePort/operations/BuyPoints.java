
package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5. Now in DVCSales
 * @author phlej001
 *
 */
@Deprecated
public class BuyPoints extends AccommodationSalesServicePort {

    public BuyPoints(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("buyPoints")));

        generateServiceContext();
        // setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/buyPoints/buyPoints.xls", scenario));
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public String getTravelPlanID() {
        return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelPlanId");
    }

    public String gettravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentId");
    }

    public String getTravelPlanId() {
        return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelPlanId");
    }

    public String getTravelComponentId() {
        return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentId");
    }

    public String getTravelComponentGroupingId() {
        return getResponseNodeValueByXPath("/Envelope/Body/buyPointsResponse/response/travelComponentGroupingId");
    }

    public void setTPGuestFirstName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/firstName", value);
    };

    public void setTPGuestLastName(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/lastName", value);
    };

    public void setTPGuestPartyId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/partyId", value);
    };

    public void setTPGuestGuestId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestId", value);
    };

    public void setTPGuestAddressLine1(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/addressDetails/addressLine1", value);
    }

    public void setTPGuestAddressCity(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/addressDetails/city", value);
    }

    public void setTPGuestAddressCountry(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/addressDetails/country", value);
    }

    public void setTPGuestAddressPostalCode(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/addressDetails/postalCode", value);
    }

    public void setTPGuestAddressState(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/addressDetails/state", value);
    }

    public void setTPGuestPhoneNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/phoneDetails/number", value);
    }

    public void setExtRefNumber(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/enttlInfo/externalReference/externalReferenceNumber", value);
    }

    public void setTPGuestEmailAddress(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/emailDetails/address", value);
        } catch (XPathNotFoundException e) {
            try {
                setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/emailDetails", "fx:addnode;node:address");
                setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/emailDetails/address", value);
            } catch (XPathNotFoundException e2) {
                setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest", "fx:addnode;node:emailDetails");
                setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/emailDetails", "fx:addnode;node:address");
                setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/emailDetails/address", value);
            }
        }
    }

    public void setTPGuestIdReferences(String type, String value) {
        try {
            XMLTools.getNodeList(getRequestDocument(), "/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences");
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest", "fx:addnode;node:guestIdReferences");
        }
        setTPGuestIdReferenceType(type);
        setTPGuestIdReferenceValue(value);
    }

    public void setTPGuestIdReferenceType(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences/type", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences", "fx:addnode;node:type");
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences/type", value);
        }
    }

    public void setTPGuestIdReferenceValue(String value) {
        try {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences/value", value);
        } catch (XPathNotFoundException e) {
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences", "fx:addnode;node:value");
            setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/guestIdReferences/value", value);
        }
    }

    public void setTPGuestMembershipDetailExpirationDate(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/expirationDate", value);
    }

    public void setTPGuestMembershipDetailMembershipType(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/memberShipType", value);
    }

    public void setTPGuestMembershipDetailMembershipId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/membershipId", value);
    }

    public void setTPGuestMembershipDetailPolicyId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/policyId", value);
    }

    public void setTPGuestMembershipDetailProductChannelId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/productChannelId", value);
    }

    public void setTPGuestMembershipDetailGuestMembershipId(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/travelPlanGuest/membershipDetail/guestMembershipId", value);
    }

    public void setEnttlInfoUseMonth(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/enttlInfo/useMonth", value);
    }

    public void setEnttlInfoUseYear(String value) {
        setRequestNodeValueByXPath("/Envelope/Body/buyPoints/request/enttlInfo/useYear", value);
    }
}
