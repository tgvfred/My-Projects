package com.disney.utils.dataFactory.folioInterface;

import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class Folio {
	private Reservation res;
	private ScheduledEventReservation seRes;
	private String environment;
	
	public Folio(ScheduledEventReservation seRes){
		this.seRes = seRes;
		this.environment = seRes.getEnvironment();
	}

	public Folio(ScheduledEventReservation seRes, String environment){
		this.seRes = seRes;
		this.environment = environment;
	}

	public Folio(Reservation res){
		this.res = res;
		this.environment = res.getEnvironment();
	}

	public Folio(Reservation res, String environment){
		this.res = res;
		this.environment = environment;
	}
	
	public FolioInterfacePayment payment(){
		if(res == null) return new FolioInterfacePayment(seRes, environment);		
		return new FolioInterfacePayment(res);
	}
	
	public FolioInterfaceSettlement settlement(){
		if(res == null) return new FolioInterfaceSettlement(seRes, environment); 
		return new FolioInterfaceSettlement(res);		
	}
}
