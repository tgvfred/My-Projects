package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects.RootChargeAccountResponse;

public class RetrieveResponse {

	private List<RootChargeAccountResponse> rootChargeAccountResponse = new ArrayList<RootChargeAccountResponse>();
	private Object nodeChargeAccountResponse;

	/**
	 * 
	 * @return
	 * The rootChargeAccountResponse
	 */
	public List<RootChargeAccountResponse> getRootChargeAccountResponse() {
		return rootChargeAccountResponse;
	}

	/**
	 * 
	 * @param rootChargeAccountResponse
	 * The rootChargeAccountResponse
	 */
	public void setRootChargeAccountResponse(RootChargeAccountResponse rootChargeAccountResponse) {
		this.rootChargeAccountResponse.add(rootChargeAccountResponse);
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