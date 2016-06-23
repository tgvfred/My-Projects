package com.disney.utils.dataFactory.staging.bookSEReservation;

import com.disney.utils.dataFactory.guestFactory.HouseHold;

public interface Modify {
	//Define interfaces to modify a party mix
	public void modifyPartyMix(HouseHold party);
	public void modifyPartyMix(HouseHold party, String scenario);
	
	//Define interfaces to modify dates and/or service periods
	public void modifyServiceStartDate(String serviceStartDate);
	public void modifyServiceStartDate(String serviceStartDate, String scenario);
	public void modifyServiceStartDateAndServicePeriod(String serviceStartDate, String servicePeriod);
	public void modifyServiceStartDateAndServicePeriod(String serviceStartDate, String servicePeriod, String scenario);
	
	//Define interfaces to modify using predefined scenarios
	public void modifyScenario(String scenario);
	
	//Define interfaces to modify the facility facility
	public void modifyFacility(String facilityId);
	public void modifyFacility(String facilityId, String productId);
	
	//Define interfaces to set modify field values
	public void setModifyScenario(String scenario);
}
