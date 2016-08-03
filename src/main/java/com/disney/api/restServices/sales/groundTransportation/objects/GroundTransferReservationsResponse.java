package com.disney.api.restServices.sales.groundTransportation.objects;

import java.util.ArrayList;
import java.util.List;

public class GroundTransferReservationsResponse {
	private GroundTransferReservation groundTransferReservation;
	public void setGroundTransferReservation(GroundTransferReservation groundTransferReservation){ this.groundTransferReservation = groundTransferReservation;}
	public GroundTransferReservation getGroundTransferReservation(){return this.groundTransferReservation;}
	
	public static class GroundTransferReservation{
		private String groundTransferReservationId = "";
		private String status = "";
		private String travelPlanId = "";
		private String sharedWithId = "";
		private List<Links> links = new ArrayList<>();
		
		public String getGroundTransferReservationId() {return groundTransferReservationId;}
		public void setGroundTransferReservationId(String groundTransferReservationId) {this.groundTransferReservationId = groundTransferReservationId;	}
		public String getStatus() {return status;}
		public void setStatus(String status) {this.status = status;}
		public String getTravelPlanId() {return travelPlanId;}
		public void setTravelPlanId(String travelPlanId) {this.travelPlanId = travelPlanId;}
		public String getSharedWithId() {return sharedWithId;}
		public void setSharedWithId(String sharedWithId) {this.sharedWithId = sharedWithId;}
		public List<Links> getLinks() {return links;}
		public void setLinks(List<Links> links) {this.links = links;}
		
		public static class Links{
			private String rel = "";
			private String href = "";
			
			public String getRel() {return rel;}
			public void setRel(String rel) {this.rel = rel;	}
			public String getHref() {return href;}
			public void setHref(String href) {this.href = href;}
		}
	}
}
