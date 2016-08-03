package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ExternalReference {

	private String referenceName;
	private String referenceValue;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The referenceName
	 */
	public String getReferenceName() {
		return referenceName;
	}

	/**
	 * 
	 * @param referenceName
	 * The referenceName
	 */
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	/**
	 * 
	 * @return
	 * The referenceValue
	 */
	public String getReferenceValue() {
		return referenceValue;
	}

	/**
	 * 
	 * @param referenceValue
	 * The referenceValue
	 */
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}