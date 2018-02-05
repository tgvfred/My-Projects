package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Quickbook;

public class Sandbox {

    @Test
    public void roomOnlyBooking() {
        Quickbook book = new Quickbook("latest", "UI Booking");
        book.sendRequest();
        System.out.println(book.getResponse());
    }

}
