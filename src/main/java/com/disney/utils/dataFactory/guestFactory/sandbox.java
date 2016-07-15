package com.disney.utils.dataFactory.guestFactory;

import org.junit.Test;

public class sandbox {
@Test
public void main(){
	Guest guest = new Guest();
	guest.sendToApi("Development", true);
	System.out.println(guest.primaryEmail().getEmail());
}
}
