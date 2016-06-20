package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;

import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class OldKeyWest extends ReservationDecorator implements Reservation{
	private int reservations;

	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "Disney's Old Key West Resort");
		setResortCode(ResortColumns.MINI_CAMPUS, "Disney's Old Key West Resort");
		setFacilityId(ResortColumns.MINI_CAMPUS, "Disney's Old Key West Resort");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "Disney's Old Key West Resort");
		setRoomTypeCode("KB");
	}	
	
	public OldKeyWest (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public OldKeyWest (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public OldKeyWest (String environment, String reservations){
		super(environment);
		this.reservations = Integer.parseInt(reservations);
		setResortInfo();
	}
	
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
