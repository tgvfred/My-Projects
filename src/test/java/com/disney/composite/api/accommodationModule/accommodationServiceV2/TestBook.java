package com.disney.composite.api.accommodationModule.accommodationServiceV2;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationServiceV2.operations.Book;
import com.disney.utils.TestReporter;

public class TestBook {

	@Test
	public void sandbox(){
		Book book = new Book("Grumpy", "MinimalInfo");
		System.out.println(book.getRequest());
		book.sendRequest();
		
		System.out.println(book.getResponse());
		TestReporter.logAPI(false, "blah", book);
	}
}
