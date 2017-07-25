package com.disney.composite.api.accommodationModule.soapServices.accommodationServiceV2;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationServiceV2.operations.Book;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestBook {

	@Test
	public void sandbox(){
		System.out.println(Randomness.generateCurrentXMLDate(-30));
	}
}
