package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;

import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class FortWilderness extends ReservationDecorator implements Reservation{
	private int reservations;

	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "Disney's Fort Wilderness Resort & Campground");
		setResortCode(ResortColumns.MINI_CAMPUS, "Disney's Fort Wilderness Resort & Campground");
		setFacilityId(ResortColumns.MINI_CAMPUS, "Disney's Fort Wilderness Resort & Campground");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "Disney's Fort Wilderness Resort & Campground");
		setRoomTypeCode("FB");
	}
	
	public FortWilderness (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public FortWilderness (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public FortWilderness (String environment, String reservations){
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
	public void quickBookWithAddress(){
		for (int resv  = 0; resv < reservations ; resv++){			
			super.quickBookWithAddress();
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
}
