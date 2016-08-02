package com.disney.api.restServices.sales;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.sales.groundTransportation.GroundTransferReservations;
import com.disney.utils.dataFactory.staging.Reservation;

public class Sales {
	private RestService restService;
	
	public Sales(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("Sales");
	}
	public GroundTransferReservations groundTransferReservations( Reservation res){
		return new GroundTransferReservations(restService, res);
	}
}
