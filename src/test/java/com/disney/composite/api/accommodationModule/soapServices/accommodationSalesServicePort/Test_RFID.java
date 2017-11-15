package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.Test;

import com.disney.utils.tdm.rfidMediaRepo.RFIDMediaRepo;
import com.disney.utils.tdm.rfidMediaRepo.objects.RFIDMedia;

public class Test_RFID {

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences" })
    public void testRFID() {
        RFIDMedia media = RFIDMediaRepo.getRfidMedia("Bashful");

    }

}
