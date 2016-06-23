package com.disney.composite.api.mq;

import org.testng.annotations.Test;

import com.disney.api.mq.operations.RoomQuote;
import com.disney.utils.TestReporter;

public class Sandbox {

	@Test
	public void Bashful_WDW(){
		RoomQuote rq = new RoomQuote("Bashful", "WDW", false, "Main");
		rq.sendRequest();
		System.out.println(rq.getResponse());
		TestReporter.logAPI(!rq.isSuccess(), rq.getErrorMessage(), rq);
	}
}
