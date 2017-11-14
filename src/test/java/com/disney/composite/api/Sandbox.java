package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.utils.tdm.rfidMediaRepo.RFIDMediaRepo;
import com.disney.utils.tdm.rfidMediaRepo.objects.RFIDMedia;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "Package", true);
        RFIDMedia media = RFIDMediaRepo.getRfidMedia("Bashful");
    }

}
