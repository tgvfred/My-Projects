package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;

import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class GrandFloridianVillas extends ReservationDecorator implements Reservation{
	private int reservations;

	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "The Villas at Disney's Grand Floridian Resort & Spa");
		setResortCode(ResortColumns.MINI_CAMPUS, "The Villas at Disney's Grand Floridian Resort & Spa");
		setFacilityId(ResortColumns.MINI_CAMPUS, "The Villas at Disney's Grand Floridian Resort & Spa");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "The Villas at Disney's Grand Floridian Resort & Spa");
		setRoomTypeCode("86");
	}	
	
	public GrandFloridianVillas (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public GrandFloridianVillas (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public GrandFloridianVillas (String environment, String reservations){
		super(environment);
		this.reservations = Integer.parseInt(reservations);
		setResortInfo();
	}
	
	@Override
	public void quickBook(){
		for (int resv  = 0; resv < reservations ; resv++){
			super.quickBook();
		}
	}
	
	@Override
	public void bookRoomOnly(){
		for (int resv  = 0; resv < reservations ; resv++){
			super.bookRoomOnly();
		}
	}
	
	@Override
	public void bookGroupBooking(){
		for (int resv  = 0; resv < reservations ; resv++){
			super.bookGroupBooking();
		}
	}
	
	@Override
	public void bookDVCCash(){
		for (int resv  = 0; resv < reservations ; resv++){
			super.bookDVCCash();
		}
	}
	
	@Override
	public void bookDVCMemberPoints(){
		for (int resv  = 0; resv < reservations ; resv++){
			super.bookDVCMemberPoints();
		}
	}
}
