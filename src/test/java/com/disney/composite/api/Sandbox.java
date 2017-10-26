package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.helpers.OfferQueryHelper;
import com.disney.api.helpers.RoomResHelper;

public class Sandbox {
    private String environment = "Stage";

    @Test
    public void packageBooking() {
        OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "Package", true);
        RoomResHelper res = new RoomResHelper(environment, "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
        System.out.println(res.getRoomRes().getItineraryId());
    }

    @Test
    public void roomOnlyBooking() {
        OfferQueryHelper offer = new OfferQueryHelper(environment, "WDW", "RoomOnly", false);
        RoomResHelper res = new RoomResHelper(environment, "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
        System.out.println(res.getRoomRes().getItineraryId());
    }
}
