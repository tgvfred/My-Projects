package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.TestReporter;

public class sandBox extends AccommodationSalesServicePort {

    public sandBox(String env) {
        super(env);
    }

    public String getTPAddressDetailsCity(String value) {
        try {
            return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/city[text()," + value + "]");
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(false, "This value: [" + value + "] cannot be found in the retrieve response");
        }
        return "";
    }

    public String getTPAddressDetailsCountry(String value) {
        try {
            return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/country[text()," + value + "]");
        } catch (XPathNotFoundException e) {
            TestReporter.assertTrue(false, "This value: [" + value + "] cannot be found in the retrieve response");
        }
        return "";
    }

    public String getTPAddressDetailsPostalCode(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/postalCode[text()," + value + "]");
    }

    public String getTPAddressDetailsState(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/state[text()," + value + "]");
    }

    public String getTPAddressDetailsRegion(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/regionName[text()," + value + "]");
    }

    // TP Email Details --------------------------------------------------------------------------------------------------------------

    public int getTPEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails");
    }

    public String getTPEmailDetailsPrimary(String value) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails/primary[text()," + value + "]");
    }

    public String getTPEmailDetailsAddress(String value) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails/address[text()," + value + "]");
    }

    // TP Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTPPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails");
    }

    public String getTPPhoneDetailsPrimary(String value) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails/primary[text()," + value + "]");
    }

    public String getTPPhoneDetailsNumber(String value) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails/number[text()," + value + "]");
    }

    // PP Address Details ---------------------------------------------------------------------------------------------------------------

    public int getPrimaryPtyAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails");
    }

    public String getPPAddressDetailsPrimary(String value) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/primary[text()," + value + "]");
    }

    public String getPPAddressDetailsAddressLine1(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/addressLine1[text()," + value + "]");
    }

    public String getPPAddressDetailsCity(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/city[text()," + value + "]");
    }

    public String getPPAddressDetailsCountry(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/country[text()," + value + "]");
    }

    public String getPPAddressDetailsPostalCode(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/postalCode[text()," + value + "]");
    }

    public String getPPAddressDetailsState(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/state[text()," + value + "]");
    }

    public String getPPAddressDetailsRegion(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/regionName[text()," + value + "]");
    }

    public String getPPFirstName(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/firstName[text()," + value + "]");
    }

    public String getPPLastName(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/lastName[text()," + value + "]");
    }

    public String getPPPhone(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails/number[text()," + value + "]");
    }

    public String getPPAddress(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/addressLine1[text()," + value + "]");
    }

    public String getPPEmail(String value) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails/address[text()," + value + "]");
    }

    // PP Email Details --------------------------------------------------------------------------------------------------------------

    public int getPPEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails");
    }

    public String getPPEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails[" + index + "]/primary");
    }

    public String getPPEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails[" + index + "]/address");
    }

    // PP Phone Details --------------------------------------------------------------------------------------------------------------

    public int getPPPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails");
    }

    public String getPPPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails[" + index + "]/primary");
    }

    public String getPPPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails[" + index + "]/number");
    }

    // Component Groupings Address Details --------------------------------------------------------------------------------------------

    public int getCompGroupingsAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails");
    }

    public String getTCGAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/primary");
    }

    public String getTCGAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/addressLine1");
    }

    public String getTCGAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/city");
    }

    public String getTCGAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/country");
    }

    public String getTCGAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/postalCode");
    }

    public String getTCGAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/state");
    }

    public String getTCGAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails[" + index + "]/regionName");
    }

    // TCG Email Details --------------------------------------------------------------------------------------------------------------

    public int getTCGEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails");
    }

    public String getTCGEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails[" + index + "]/primary");
    }

    public String getTCGEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails[" + index + "]/address");
    }

    // TCG Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTCGPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails");
    }

    public String getTCGPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails[" + index + "]/primary");
    }

    public String getTCGPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails[" + index + "]/number");
    }

    // TPS Address Details --------------------------------------------------------------------------------------------

    public int getTPSAddressDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails");
    }

    public String getTPSAddressDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/primary");
    }

    public String getTPSAddressDetailsAddressLine1(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/addressLine1");
    }

    public String getTPSAddressDetailsCity(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/city");
    }

    public String getTPSAddressDetailsCountry(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/country");
    }

    public String getTPSAddressDetailsPostalCode(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/postalCode");
    }

    public String getTPSAddressDetailsState(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/state");
    }

    public String getTPSAddressDetailsRegion(String index) {
        return getResponseNodeValueByXPath("Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails[" + index + "]/regionName");
    }

    // TPS Email Details --------------------------------------------------------------------------------------------------------------

    public int getTPSEmailDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails");
    }

    public String getTPSEmailDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails[" + index + "]/primary");
    }

    public String getTPSEmailDetailsAddress(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails[" + index + "]/address");
    }

    // TPS Phone Details --------------------------------------------------------------------------------------------------------------

    public int getTPSPhoneDetailsCount() {
        return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails");
    }

    public String getTPSPhoneDetailsPrimary(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails[" + index + "]/primary");
    }

    public String getTPSPhoneDetailsNumber(String index) {
        return getResponseNodeValueByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails[" + index + "]/number");
    }

}
