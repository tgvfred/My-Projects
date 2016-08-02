package com.disney.api.restServices.sales.groundTransportation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicHeader;

import com.disney.AutomationException;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.sales.groundTransportation.objects.GroundTransferReservationsRequest;
import com.disney.api.restServices.sales.groundTransportation.objects.GroundTransferReservationsResponse;
import com.disney.test.utils.Randomness;
import com.disney.utils.dataFactory.staging.Reservation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class GroundTransferReservations {
	private List<GroundTransferReservationsRequest> dme = new ArrayList<>();
	private String resource = "/v3/groundtransferreservations";
	private ObjectMapper mapper = new ObjectMapper();
	private RestService rest = null;
	private Reservation res;
	
	public GroundTransferReservations(RestService rest, Reservation res){
		this.res = res;
		this.rest = rest;
		dme.add(new GroundTransferReservationsRequest(res));
	}
	
	public void addRoundTripTransportation(String inboundLocation, String outboundLocation, String startDate, String endDate){
		dme.get(0).addRoundTripTransfer(res, inboundLocation, outboundLocation, startDate, endDate);	
	}
	public void addInboundTransportaion(String inboundLocation, String outboundLocation, String date){
		dme.get(0).addInboundTransfer(res, inboundLocation, outboundLocation, date);
	}
	
	public void addOutboundTransportaion(String inboundLocation, String outboundLocation, String date){
		dme.get(0).addOutboundTransfer(res, outboundLocation, inboundLocation, date);
	}
	
	public GroundTransferReservationsResponse[] sendRequest(){
		String jsonInString ="";
		RestResponse response = null;
		try {
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dme);
			//System.out.println(jsonInString);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Header[] headers =  {
	   		    new BasicHeader("Content-type", "application/json;charset=utf-8")
	   		    ,new BasicHeader("Accept", "application/json")
	   		    ,new BasicHeader("username", "test116.user")
	   		    ,new BasicHeader("messageId", Randomness.generateMessageId())
	   		    ,new BasicHeader("Connection", "keep-alive")
	   		    ,new BasicHeader("conversationId",  Randomness.generateConversationId())
	   		    ,new BasicHeader("requestedTimestamp", Randomness.generateCurrentXMLDatetime() + ".000-04:00")
	   		};
		response=rest.sendPostRequest(resource, headers, jsonInString);
		
		
		GroundTransferReservationsResponse[] dmeRes = null;
		try {
			dmeRes = response.mapJSONToObject(GroundTransferReservationsResponse[].class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			throw new AutomationException("Failed to read response:" + response, e);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			throw new AutomationException("Failed to read response:" + response, e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO Auto-generated catch block
			throw new AutomationException("Failed to read response:" + response, e);
		}
		
		return dmeRes;
	}
}
