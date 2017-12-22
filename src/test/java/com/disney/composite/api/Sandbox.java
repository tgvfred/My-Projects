package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.helpers.pricingHelpers.RoomTypes;
import com.disney.api.soapServices.pricingModule.packagingService.operations.helpers.PackageCodeHelper;
import com.disney.utils.Randomness;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "Package", true);

        // Wholesaler
        // PackageCodeHelper helper = new PackageCodeHelper("Latest", Randomness.generateCurrentXMLDate(), RoomTypes.getRoomOnly(), "DRC RO", "1G", "GA", Randomness.generateCurrentXMLDate(390));
        // helper = new PackageCodeHelper("Latest", Randomness.generateCurrentXMLDate(), RoomTypes.getRoomPlusMywTicket(), "DREAMS - United States", "1G", "GA", Randomness.generateCurrentXMLDate(50));
        // helper = new PackageCodeHelper("Latest", Randomness.generateCurrentXMLDate(), RoomTypes.getWeddings(), "DREAMS - United States", "1G", "GA", Randomness.generateCurrentXMLDate(50));

        // WDTC
        PackageCodeHelper helper = new PackageCodeHelper("Latest", Randomness.generateCurrentXMLDate(), RoomTypes.getRoomOnly(), "RSR", "1G", "GA", Randomness.generateCurrentXMLDate(50));
        // setPackageCode(helper.getPackageCode());
        System.out.println(helper.getPackageCode());
    }

}
