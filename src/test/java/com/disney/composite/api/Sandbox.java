package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.soapServices.admissionModule.admissionSalesServicePort.helpers.pricingHelpers.RoomTypes;
import com.disney.api.soapServices.pricingModule.packagingService.operations.helpers.PackageCodeHelper;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        PackageCodeHelper helper = new PackageCodeHelper("latest", "2018-02-12", RoomTypes.getRoomOnly(), "DRC RO", "SWN", "SA", "2018-02-05");
        System.out.println(helper.getPackageCode());
    }

}
//