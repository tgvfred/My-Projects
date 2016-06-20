package com.disney.utils.dataFactory.staging.bookResortReservation.resorts;

import com.disney.utils.dataFactory.ResortInfo.ResortColumns;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.ReservationDecorator;

public class Contemporary extends ReservationDecorator implements Reservation{
	private int reservations;

	private void setResortInfo(){
		setLocationId(ResortColumns.MINI_CAMPUS, "Disney's Contemporary Resort");
		setResortCode(ResortColumns.MINI_CAMPUS, "Disney's Contemporary Resort");
		setFacilityId(ResortColumns.MINI_CAMPUS, "Disney's Contemporary Resort");
		setSouceAccountingCenterId(ResortColumns.MINI_CAMPUS, "Disney's Contemporary Resort");
		setRoomTypeCode("CB");
	}
	
	public Contemporary (String environment){
		super(environment);
		this.reservations = 1;
		setResortInfo();
	}
	
	public Contemporary (String environment, int reservations){
		super(environment);
		this.reservations = reservations;
		setResortInfo();
	}
	
	public Contemporary (String environment, String reservations){
		super(environment);
		this.reservations = Integer.parseInt(reservations);
		setResortInfo();
	}
	
	public Contemporary (Reservation oldRes){
		super(oldRes.getEnvironment());
		this.reservations = 1;
		setResortInfo();
		super.setPrimaryGuestFirstName(oldRes.getPrimaryGuestFirstName());
		super.setPrimaryGuestLastName(oldRes.getPrimaryGuestLastName());
		super.setNumberOfAdults(oldRes.getNumberOfAdults());
		super.setNumberOfRooms(oldRes.getNumberOfRooms());
		super.setArrivalDaysOut(oldRes.getArrivalDaysOut());
		super.setDepartureDaysOut(oldRes.getDepartureDaysOut());
		super.setPackageCode(oldRes.getPackageCode());	
		//super.setInventoryTrackingID(oldRes.getInventoryTrackingID());
		super.setTravelPlanId(oldRes.getTravelPlanId());
		super.setTravelPlanSegmentId(oldRes.getTravelPlanSegmentId());
		super.setTravelComponentGroupingId(oldRes.getTravelComponentGroupingId());
		super.setTravelComponentId(oldRes.getTravelComponentId());
		super.setGuestId(oldRes.getGuestId());
		super.setPartyId(oldRes.getPartyId());
		super.setFolioId(oldRes.getFolioId());
		super.setDepositDue(oldRes.getDepositDue());
		super.setBalanceDue(oldRes.getBalanceDue());
		super.setOriginalTransactionId(oldRes.getOriginalTransactionId());
		super.setLatestTransactionId(oldRes.getLatestTransactionId());
		super.setPaymentId(oldRes.getPaymentId());
		super.setRRNNumber(oldRes.getRRNNumber());
		super.setRRNKey(oldRes.getRRNKey());
		super.setDVCMemberID(oldRes.getDVCMemberID());		//same as the DVC member ID
		//super.setMembershipRefID(oldRes.getMembershipRefID());
		//super.setVacationType(oldRes.getVacationType());
		//super.setResortLocation(oldRes.getResortLocation());
		super.setDVCresortCode(oldRes.getDVCresortCode());
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
