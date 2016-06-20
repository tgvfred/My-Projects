package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;
import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class AulaniVillas extends ReservationDecorator implements Reservation{	

	private int reservations;
	private String packageCode = "V663Z";

	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "Aulani, Disney Vacation Club Villas, Ko Olina, Hawaii");
		setResortCode(ResortColumns.MINI_CAMPUS, "Aulani, Disney Vacation Club Villas, Ko Olina, Hawaii");
		setFacilityId(ResortColumns.MINI_CAMPUS, "Aulani, Disney Vacation Club Villas, Ko Olina, Hawaii");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "Aulani, Disney Vacation Club Villas, Ko Olina, Hawaii");
		setRoomTypeCode("6A");
		setPackageCode(packageCode);
	}
	
	public AulaniVillas (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public AulaniVillas (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public AulaniVillas (String environment, String reservations){
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
