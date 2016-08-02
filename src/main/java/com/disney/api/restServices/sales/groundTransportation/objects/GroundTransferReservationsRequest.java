package com.disney.api.restServices.sales.groundTransportation.objects;

import java.util.ArrayList;
import java.util.List;

import com.disney.utils.dataFactory.staging.Reservation;

@SuppressWarnings("unused")
public class GroundTransferReservationsRequest {
	private Reservation res;
	private String travelPlanId = "";
	private String sourceOfTxn = "DME";
	private String startDate = "";
	private String endDate = "";
	private List<GroundTransfers> groundTransfers = new ArrayList<>();
	
	public String getTravelPlanId() {return travelPlanId;}
	public void setTravelPlanId(String travelPlanId) {this.travelPlanId = travelPlanId;	}
	public String getSourceOfTxn() {return sourceOfTxn;}
	public void setSourceOfTxn(String sourceOfTxn) {this.sourceOfTxn = sourceOfTxn;}
	public String getStartDate() {return startDate;}
	public void setStartDate(String startDate) {this.startDate = startDate;}
	public String getEndDate() {return endDate;}
	public void setEndDate(String endDate) {this.endDate = endDate;}
	public List<GroundTransfers> getGroundTransfers() {return groundTransfers;}
	public void setGroundTransfers(List<GroundTransfers> groundTransfers) {this.groundTransfers = groundTransfers;}
	
	public void addRoundTripTransfer(Reservation res, String destinationLocationId, String originalLocationId, String firstPickupDate, String secondPickUpDate){
		this.res = res;
		this.travelPlanId = res.getTravelPlanId();
		this.startDate = res.getArrivalDate();
		this.endDate = res.getDepartureDate();
		groundTransfers.get(0).addTransfer(res, "Inbound" , destinationLocationId, originalLocationId, firstPickupDate);
		groundTransfers.get(0).addTransfer(res, "Outbound" , originalLocationId, destinationLocationId, secondPickUpDate);
	}
	public void addInboundTransfer(Reservation res, String destinationLocationId, String originalLocationId, String pickupDate){
		this.res = res;
		this.travelPlanId = res.getTravelPlanId();
		this.startDate = res.getArrivalDate();
		this.endDate = res.getDepartureDate();
		groundTransfers.get(0).addTransfer(res, "Inbound" , destinationLocationId, originalLocationId, pickupDate);
	}
	public void addOutboundTransfer(Reservation res, String destinationLocationId, String originalLocationId, String pickupDate){
		this.res = res;
		this.travelPlanId = res.getTravelPlanId();
		this.startDate = res.getArrivalDate();
		this.endDate = res.getDepartureDate();
		groundTransfers.get(0).addTransfer(res, "Outbound" , destinationLocationId, originalLocationId, pickupDate);
	}
	public GroundTransferReservationsRequest(Reservation res){
		groundTransfers.add(new GroundTransfers(res));
		
	}
	
	public static class GroundTransfers{
		private Reservation res;
		GroundTransfers(Reservation res){
			this.res = res;
			guest = new Guest(res);
		}
		private String salesChannel = "Internal and External WholeSaler";
		private String transferType = "DME";
		private String guestPhone = null;
		private String primaryIndicator = "Y";
		private Guest guest;
		private List<Transfers> transfers= new ArrayList<>();
		
		public String getSalesChannel() {return salesChannel;}
		public void setSalesChannel(String salesChannel) {this.salesChannel = salesChannel;}
		
		public String getTransferType() {return transferType;}
		public void setTransferType(String transferType) {this.transferType = transferType;}
		
		public String getGuestPhone() {	return guestPhone;}
		public void setGuestPhone(String guestPhone) {this.guestPhone = guestPhone;}
		
		public String getPrimaryIndicator() {return primaryIndicator;}
		public void setPrimaryIndicator(String primaryIndicator) {this.primaryIndicator = primaryIndicator;}
		
		public Guest getGuest() {return guest;}		
		public void setGuest( Guest guest) {this.guest = guest;}

		public List<Transfers> getTransfers() {return transfers;}		
		public void setTransfers(List<Transfers> transfers) {this.transfers = transfers;}
	
		public void addTransfer(Reservation res, String type, String destinationLocationId, String originalLocationId, String date){
			Transfers tran = new Transfers(res, type, destinationLocationId, originalLocationId, date);
			transfers.add(tran);
		}
		public static class Guest{
			private Reservation res;
			private String guestId = "";
			private String guestSrc = "GoMaster";
			private int transactionalId = 0;
			private String firstName = "";
			private String lastName = "";
			private String middleName = null;
			private String title = "Mr.";
			private String emailAddress = "automationUser@test.com";
			private String suffix = null;
			private String ageNb = null;
			
			Guest(Reservation res){
				this.res = res;
				this.guestId = res.getPrimaryGuestOdsId();
				this.transactionalId =Integer.parseInt(res.getGuestId());
				this.setFirstName(res.getPrimaryGuestFirstName());
				this.setLastName(res.getPrimaryGuestLastName());
				//this.setTitle(res.getPrimaryGuestTitle());
				this.setEmailAddress(res.getPrimaryGuestEmail());
				//this.setFirstName(res.getPrimaryGuestFirstName());
				//this.setFirstName(res.getPrimaryGuestFirstName());
			}
			public String getGuestId() {return guestId;	}			
			public void setGuestId(String guestId) {this.guestId = guestId;	}			
			
			public String getGuestSrc() {return guestSrc;}			
			public void setGuestSrc(String guestSrc) {this.guestSrc = guestSrc;}			
			
			public int getTransactionalId() {return transactionalId;}			
			public void setTransactionalId(int transactionalId) {this.transactionalId = transactionalId;}			
			
			public String getFirstName() {return firstName;	}			
			public void setFirstName(String firstName) {this.firstName = firstName;	}			
			
			public String getLastName() {return lastName;}			
			public void setLastName(String lastName) {this.lastName = lastName;}			
			
			public String getMiddleName() {return middleName;}			
			public void setMiddleName(String middleName) {this.middleName = middleName;}
			
			public String getTitle() {return title;	}			
			public void setTitle(String title) {this.title = title;	}			
			
			public String getEmailAddress() {return emailAddress;}			
			public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}			
			
			public String getSuffix() {return suffix;}			
			public void setSuffix(String suffix) {this.suffix = suffix;}		
			
			public String getAgeNb() {return ageNb;}			
			public void setAgeNb(String ageNb) {this.ageNb = ageNb;}
		}
		
		public static class Transfers{
			private Reservation res;
			Transfers(Reservation res, String type, String destinationLocationId, String originalLocationId, String transferDate){
				this.res = res;
				this.type = type;
				this.destLocId = destinationLocationId;
				this.origLocId = originalLocationId;
				this.transferDate = transferDate;
				flight = new Flight(transferDate);
				room = new Room(res);
			}
			private String type= "Inbound";
			private String destLocId = "";
			private String origLocId = "";
			private String transferDate= "";
			private String numBags = null;
			private Room room;
			private Flight flight;
			
			public String getType() {return type;}			
			public void setType(String type) {this.type = type;}			
			
			public String getDestLocId() {return destLocId;}			
			public void setDestLocId(String destLocId) {this.destLocId = destLocId;}

			public String getOrigLocId() {return origLocId;}			
			public void setOrigLocId(String origLocId) {this.origLocId = origLocId;}

			public String getTransferDate() {return transferDate;}			
			public void setTransferDate(String transferDate) {this.transferDate = transferDate;}

			public String getNumBags() {return numBags;}			
			public void setNumBags(String numBags) {this.numBags = numBags;}

			public Flight getFlight() {return flight;}			
			public void setFlight(Flight flight) {this.flight = flight;}
			public Room getRoom() {return room;}		
			public void setRoom(Room room) {this.room = room;}
			
			public static class Flight{
				Flight(String date){
					this.flightDate = date;
				}
				private String carrierId= "27";
				private String flightNumber= "1999";
				private String flightCode= "DL";
				private String flightDate= "2016-06-01T16:33:05-04:00";
				
				public String getCarrierId() {return carrierId;}			
				public void setCarrierId(String carrierId) {this.carrierId = carrierId;}

				public String getFlightNumber() {return flightNumber;}			
				public void setFlightNumber(String flightNumber) {this.flightNumber = flightNumber;}

				public String getFlightCode() {return flightCode;}			
				public void setFlightCode(String flightCode) {this.flightCode = flightCode;}

				public String getFlightDate() {return flightDate;}			
				public void setFlightDate(String flightDate) {this.flightDate = flightDate;}
			}
			public static class Room{
				private Reservation res;
				private String roomResId= "";
				private String tcgId= "";
				private String roomResIdType= "Dreams_TPS";
				private String roomResIdSrc= "Dreams";
				private String roomNumber= null;
				private String groupName= null;
				private String groupCode= null;
				private String teamName= null;
				private String facilityId= "";
				private String travelAgencyName= null;
				private String travelAgencyId= null;
				private String startDate= "";
				private String endDate= "";

				Room(Reservation res){

					this.res = res;
					this.roomResId = res.getTravelPlanSegmentId();
					this.tcgId = res.getTravelComponentGroupingId();
					this.facilityId = res.getFacilityId();
					this.startDate = res.getArrivalDate();
					this.endDate = res.getDepartureDate();
				}
				public String getRoomResId() {return roomResId;}			
				public void setRoomResId(String roomResId) {this.roomResId = roomResId;}

				public String getTcgId() {return tcgId;}			
				public void setTcgId(String tcgId) {this.tcgId = tcgId;}

				public String getRoomResIdType() {return roomResIdType;}			
				public void setRoomResIdType(String roomResIdType) {this.roomResIdType = roomResIdType;}

				public String getRoomResIdSrc() {return roomResIdSrc;}			
				public void setRoomResIdSrc(String roomResIdSrc) {this.roomResIdSrc = roomResIdSrc;}

				public String getRoomNumber() {return roomNumber;}			
				public void setRoomNumber(String roomNumber) {this.roomResId = roomNumber;}

				public String getGroupName() {return groupName;}			
				public void setGroupName(String groupName) {this.groupName = groupName;}

				public String getGroupCode() {return groupCode;}			
				public void setGroupCode(String groupCode) {this.groupCode = groupCode;}

				public String getTeamName() {return teamName;}			
				public void setTeamName(String teamName) {this.teamName = teamName;}

				public String getFacilityId() {return facilityId;}			
				public void setFacilityId(String facilityId) {this.facilityId = facilityId;}

				public String getTravelAgencyName() {return travelAgencyName;}			
				public void setTravelAgencyName(String travelAgencyName) {this.travelAgencyName = travelAgencyName;}

				public String getTravelAgencyId() {return travelAgencyId;}			
				public void setTravelAgencyId(String travelAgencyId) {this.travelAgencyId = travelAgencyId;}

				public String getStartDate() {return startDate;}			
				public void setStartDate(String startDate) {this.startDate = startDate;}

				public String getEndDate() {return endDate;}			
				public void setEndDate(String endDate) {this.endDate = endDate;}
				
			}
		}
		
	}
}
