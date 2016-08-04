package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects.RootChargeAccountResponse;

@Generated("org.jsonschema2pojo")
public class RetrieveResponse {

	private RootChargeAccountResponse rootChargeAccountResponse;
	private Object nodeChargeAccountResponse;

	/**
	 * 
	 * @return
	 * The rootChargeAccountResponse
	 */
	public RootChargeAccountResponse getRootChargeAccountResponse() {
		return rootChargeAccountResponse;
	}

	/**
	 * 
	 * @param rootChargeAccountResponse
	 * The rootChargeAccountResponse
	 */
	public void setRootChargeAccountResponse(RootChargeAccountResponse rootChargeAccountResponse) {
		this.rootChargeAccountResponse = rootChargeAccountResponse;
	}

	/**
	 * 
	 * @return
	 * The nodeChargeAccountResponse
	 */
	public Object getNodeChargeAccountResponse() {
		return nodeChargeAccountResponse;
	}

	/**
	 * 
	 * @param nodeChargeAccountResponse
	 * The nodeChargeAccountResponse
	 */
	public void setNodeChargeAccountResponse(Object nodeChargeAccountResponse) {
		this.nodeChargeAccountResponse = nodeChargeAccountResponse;
	}

}