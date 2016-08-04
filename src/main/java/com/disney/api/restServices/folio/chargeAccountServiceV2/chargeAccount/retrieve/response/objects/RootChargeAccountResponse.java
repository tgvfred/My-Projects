package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RootChargeAccountResponse {

	private List<Object> nodeChargeAccountResponse = new ArrayList<Object>();
	private CommonChargeAccountResponse commonChargeAccountResponse;
	private List<Object> ownedNodeChargeAccountResponse = new ArrayList<Object>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The nodeChargeAccountResponse
	 */
	public List<Object> getNodeChargeAccountResponse() {
		return nodeChargeAccountResponse;
	}

	/**
	 * 
	 * @param nodeChargeAccountResponse
	 * The nodeChargeAccountResponse
	 */
	public void setNodeChargeAccountResponse(List<Object> nodeChargeAccountResponse) {
		this.nodeChargeAccountResponse = nodeChargeAccountResponse;
	}

	/**
	 * 
	 * @return
	 * The commonChargeAccountResponse
	 */
	public CommonChargeAccountResponse getCommonChargeAccountResponse() {
		return commonChargeAccountResponse;
	}

	/**
	 * 
	 * @param commonChargeAccountResponse
	 * The commonChargeAccountResponse
	 */
	public void setCommonChargeAccountResponse(CommonChargeAccountResponse commonChargeAccountResponse) {
		this.commonChargeAccountResponse = commonChargeAccountResponse;
	}

	/**
	 * 
	 * @return
	 * The ownedNodeChargeAccountResponse
	 */
	public List<Object> getOwnedNodeChargeAccountResponse() {
		return ownedNodeChargeAccountResponse;
	}

	/**
	 * 
	 * @param ownedNodeChargeAccountResponse
	 * The ownedNodeChargeAccountResponse
	 */
	public void setOwnedNodeChargeAccountResponse(List<Object> ownedNodeChargeAccountResponse) {
		this.ownedNodeChargeAccountResponse = ownedNodeChargeAccountResponse;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}