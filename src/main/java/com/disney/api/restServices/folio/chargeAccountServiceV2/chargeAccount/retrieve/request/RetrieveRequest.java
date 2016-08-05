package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.request;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.request.objects.ChargeAccountIdentifier;

public class RetrieveRequest {

	private List<ChargeAccountIdentifier> chargeAccountIdentifiers = new ArrayList<ChargeAccountIdentifier>();
	
	/**
	 * 
	 * @return
	 * The chargeAccountIdentifiers
	 */
	public List<ChargeAccountIdentifier> getChargeAccountIdentifiers() {
		return chargeAccountIdentifiers;
	}

	public void addChargeAccountId(String id){
		ChargeAccountIdentifier ca = new ChargeAccountIdentifier();
		ca.setChargeAccountId(id);
		this.chargeAccountIdentifiers.add(ca);
	}
	
}