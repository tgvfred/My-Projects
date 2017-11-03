package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.mq.sbc.RoomInventoryDecrement;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Sandbox {
    private String environment = "Latest";

    // @Test
    // public void packageBooking() {
    // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "Package", true);
    // RoomResHelper res = new RoomResHelper(environment, "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
    // System.out.println(res.getRoomRes().getItineraryId());
    // }
    //
    // @Test
    // public void roomOnlyBooking() {
    // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "RoomOnly", false);
    // RoomResHelper res = new RoomResHelper(environment, "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
    // System.out.println(res.getRoomRes().getItineraryId());
    // }

    @Test
    public void roomOnlyBooking() {
        // OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "Package", true);
        RoomInventoryDecrement avail = new RoomInventoryDecrement(environment, "WDW");
        avail.setLOS("1");
        avail.setStartDate(Randomness.generateCurrentXMLDate(0));
        avail.setEndDate(Randomness.generateCurrentXMLDate(1));
        avail.setPackageCode("A0X1J");
        avail.setResortCode("1E");
        avail.setRoomType("EA");
        avail.sendRequest();
        TestReporter.logAPI(!avail.isSuccess(), "Failed to get Freeze Room", avail);
        System.out.println(avail.getFreezeID());
    }
}
