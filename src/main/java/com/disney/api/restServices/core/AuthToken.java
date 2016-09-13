package com.disney.api.restServices.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthToken {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@JsonProperty("refresh_expires_in")
	private String refreshExpiresIn;
	
	@JsonProperty("high_trust_expires_in")
	private String highTrustExpiresIn;
	private String swid;
	private String scope;
	
	@JsonProperty("expires_in")
	private String expiresIn;

	/**
	 * 
	 * @return
	 * The accessToken
	 */
	@JsonProperty("access_token")
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * 
	 * @param accessToken
	 * The access_token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 
	 * @return
	 * The tokenType
	 */
	public String getTokenType() {
		return tokenType;
	}

	/**
	 * 
	 * @param tokenType
	 * The token_type
	 */
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * 
	 * @return
	 * The refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 
	 * @param refreshToken
	 * The refresh_token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 
	 * @return
	 * The refreshExpiresIn
	 */
	public String getRefreshExpiresIn() {
		return refreshExpiresIn;
	}

	/**
	 * 
	 * @param refreshExpiresIn
	 * The refresh_expires_in
	 */
	public void setRefreshExpiresIn(String refreshExpiresIn) {
		this.refreshExpiresIn = refreshExpiresIn;
	}

	/**
	 * 
	 * @return
	 * The highTrustExpiresIn
	 */
	public String getHighTrustExpiresIn() {
		return highTrustExpiresIn;
	}

	/**
	 * 
	 * @param highTrustExpiresIn
	 * The high_trust_expires_in
	 */
	public void setHighTrustExpiresIn(String highTrustExpiresIn) {
		this.highTrustExpiresIn = highTrustExpiresIn;
	}

	/**
	 * 
	 * @return
	 * The swid
	 */
	public String getSwid() {
		return swid;
	}

	/**
	 * 
	 * @param swid
	 * The swid
	 */
	public void setSwid(String swid) {
		this.swid = swid;
	}

	/**
	 * 
	 * @return
	 * The scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * 
	 * @param scope
	 * The scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * 
	 * @return
	 * The expiresIn
	 */
	public String getExpiresIn() {
		return expiresIn;
	}

	/**
	 * 
	 * @param expiresIn
	 * The expires_in
	 */
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

}
