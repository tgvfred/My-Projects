package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;

import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class BayLakeTowers extends ReservationDecorator implements Reservation{
	private int reservations;
	
	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "Bay Lake Tower");
		setResortCode(ResortColumns.MINI_CAMPUS, "Bay Lake Tower");
		setFacilityId(ResortColumns.MINI_CAMPUS, "Bay Lake Tower");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "Bay Lake Tower");
		setRoomTypeCode("4X");
	}
	
	public BayLakeTowers (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public BayLakeTowers (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public BayLakeTowers (String environment, String reservations){
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
