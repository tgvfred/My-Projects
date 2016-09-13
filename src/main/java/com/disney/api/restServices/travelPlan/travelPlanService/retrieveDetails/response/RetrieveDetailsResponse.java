package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.AreaPeriod;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.AuditDetail;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.ComponentGrouping;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.ExternalReference;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.Guest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.Period;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.Phone;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.PrimaryGuest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects.TravelPlanAddress;

public class RetrieveDetailsResponse {
	private AreaPeriod areaPeriod;
	private Object arrivalTime;
	private Object cancelDate;
	private Long cancellationNumber;
	private Object celebrating;
	private Long celebrationCount;
	private Object celebrationId;
	private List<Object> comments = new ArrayList<Object>();
	private List<AuditDetail> auditDetails = new ArrayList<AuditDetail>();
	private List<ComponentGrouping> componentGroupings = new ArrayList<ComponentGrouping>();
	private Boolean concierge;
	private List<Object> confirmationDetails = new ArrayList<Object>();
	private Object contactName;
	private Object departureTime;
	private Object depositRequirementSummary;
	private Object email;
	private ExternalReference externalReference;
	private Object unlinkedTravelPlanId;
	private Object membershipExternalReference;
	private Object gatheringDetail;
	private Boolean guaranteed;
	private List<Guest> guests = new ArrayList<Guest>();
	private List<Object> invoiceTransactions = new ArrayList<Object>();
	private Object linkageNumber;
	private List<Object> onsiteContactDetails = new ArrayList<Object>();
	private Period period;
	private Phone phone;
	private Object phoneTag;
	private String securityLevel;
	private Boolean sentToProperty;
	private Object settlementMop;
	private String status;
	private Object taxExemptDetail;
	private Object termsAndConditions;
	private Object travelAgency;
	private TravelPlanAddress travelPlanAddress;
	private PrimaryGuest primaryGuest;
	private Object travelAgent;
	private Long travelPlanId;
	private Long siebelTravelPlanId;
	private Long travelPlanSegmentId;
	private String vipLevel;
	private Boolean bundleDetailPresent;
	private Boolean onsiteMessagingEnabled;
	private Object bypassResortDesk;

	/**
	* 
	* @return
	* The areaPeriod
	*/
	public AreaPeriod getAreaPeriod() {
	return areaPeriod;
	 }

	/**
	* 
	* @param areaPeriod
	* The areaPeriod
	*/
	public void setAreaPeriod(AreaPeriod areaPeriod) {
	this.areaPeriod = areaPeriod;
	 }

	/**
	* 
	* @return
	* The arrivalTime
	*/
	public Object getArrivalTime() {
	return arrivalTime;
	 }

	/**
	* 
	* @param arrivalTime
	* The arrivalTime
	*/
	public void setArrivalTime(Object arrivalTime) {
	this.arrivalTime = arrivalTime;
	 }

	/**
	* 
	* @return
	* The cancelDate
	*/
	public Object getCancelDate() {
	return cancelDate;
	 }

	/**
	* 
	* @param cancelDate
	* The cancelDate
	*/
	public void setCancelDate(Object cancelDate) {
	this.cancelDate = cancelDate;
	 }

	/**
	* 
	* @return
	* The cancellationNumber
	*/
	public Long getCancellationNumber() {
	return cancellationNumber;
	 }

	/**
	* 
	* @param cancellationNumber
	* The cancellationNumber
	*/
	public void setCancellationNumber(Long cancellationNumber) {
	this.cancellationNumber = cancellationNumber;
	 }

	/**
	* 
	* @return
	* The celebrating
	*/
	public Object getCelebrating() {
	return celebrating;
	 }

	/**
	* 
	* @param celebrating
	* The celebrating
	*/
	public void setCelebrating(Object celebrating) {
	this.celebrating = celebrating;
	 }

	/**
	* 
	* @return
	* The celebrationCount
	*/
	public Long getCelebrationCount() {
	return celebrationCount;
	 }

	/**
	* 
	* @param celebrationCount
	* The celebrationCount
	*/
	public void setCelebrationCount(Long celebrationCount) {
	this.celebrationCount = celebrationCount;
	 }

	/**
	* 
	* @return
	* The celebrationId
	*/
	public Object getCelebrationId() {
	return celebrationId;
	 }

	/**
	* 
	* @param celebrationId
	* The celebrationId
	*/
	public void setCelebrationId(Object celebrationId) {
	this.celebrationId = celebrationId;
	 }

	/**
	* 
	* @return
	* The comments
	*/
	public List<Object> getComments() {
	return comments;
	 }

	/**
	* 
	* @param comments
	* The comments
	*/
	public void setComments(List<Object> comments) {
	this.comments = comments;
	 }

	/**
	* 
	* @return
	* The auditDetails
	*/
	public List<AuditDetail> getAuditDetails() {
	return auditDetails;
	 }

	/**
	* 
	* @param auditDetails
	* The auditDetails
	*/
	public void setAuditDetails(List<AuditDetail> auditDetails) {
	this.auditDetails = auditDetails;
	 }

	/**
	* 
	* @return
	* The componentGroupings
	*/
	public List<ComponentGrouping> getComponentGroupings() {
	return componentGroupings;
	 }

	/**
	* 
	* @param componentGroupings
	* The componentGroupings
	*/
	public void setComponentGroupings(List<ComponentGrouping> componentGroupings) {
	this.componentGroupings = componentGroupings;
	 }

	/**
	* 
	* @return
	* The concierge
	*/
	public Boolean getConcierge() {
	return concierge;
	 }

	/**
	* 
	* @param concierge
	* The concierge
	*/
	public void setConcierge(Boolean concierge) {
	this.concierge = concierge;
	 }

	/**
	* 
	* @return
	* The confirmationDetails
	*/
	public List<Object> getConfirmationDetails() {
	return confirmationDetails;
	 }

	/**
	* 
	* @param confirmationDetails
	* The confirmationDetails
	*/
	public void setConfirmationDetails(List<Object> confirmationDetails) {
	this.confirmationDetails = confirmationDetails;
	 }

	/**
	* 
	* @return
	* The contactName
	*/
	public Object getContactName() {
	return contactName;
	 }

	/**
	* 
	* @param contactName
	* The contactName
	*/
	public void setContactName(Object contactName) {
	this.contactName = contactName;
	 }

	/**
	* 
	* @return
	* The departureTime
	*/
	public Object getDepartureTime() {
	return departureTime;
	 }

	/**
	* 
	* @param departureTime
	* The departureTime
	*/
	public void setDepartureTime(Object departureTime) {
	this.departureTime = departureTime;
	 }

	/**
	* 
	* @return
	* The depositRequirementSummary
	*/
	public Object getDepositRequirementSummary() {
	return depositRequirementSummary;
	 }

	/**
	* 
	* @param depositRequirementSummary
	* The depositRequirementSummary
	*/
	public void setDepositRequirementSummary(Object depositRequirementSummary) {
	this.depositRequirementSummary = depositRequirementSummary;
	 }

	/**
	* 
	* @return
	* The email
	*/
	public Object getEmail() {
	return email;
	 }

	/**
	* 
	* @param email
	* The email
	*/
	public void setEmail(Object email) {
	this.email = email;
	 }

	/**
	* 
	* @return
	* The externalReference
	*/
	public ExternalReference getExternalReference() {
	return externalReference;
	 }

	/**
	* 
	* @param externalReference
	* The externalReference
	*/
	public void setExternalReference(ExternalReference externalReference) {
	this.externalReference = externalReference;
	 }

	/**
	* 
	* @return
	* The unlinkedTravelPlanId
	*/
	public Object getUnlinkedTravelPlanId() {
	return unlinkedTravelPlanId;
	 }

	/**
	* 
	* @param unlinkedTravelPlanId
	* The unlinkedTravelPlanId
	*/
	public void setUnlinkedTravelPlanId(Object unlinkedTravelPlanId) {
	this.unlinkedTravelPlanId = unlinkedTravelPlanId;
	 }

	/**
	* 
	* @return
	* The membershipExternalReference
	*/
	public Object getMembershipExternalReference() {
	return membershipExternalReference;
	 }

	/**
	* 
	* @param membershipExternalReference
	* The membershipExternalReference
	*/
	public void setMembershipExternalReference(Object membershipExternalReference) {
	this.membershipExternalReference = membershipExternalReference;
	 }

	/**
	* 
	* @return
	* The gatheringDetail
	*/
	public Object getGatheringDetail() {
	return gatheringDetail;
	 }

	/**
	* 
	* @param gatheringDetail
	* The gatheringDetail
	*/
	public void setGatheringDetail(Object gatheringDetail) {
	this.gatheringDetail = gatheringDetail;
	 }

	/**
	* 
	* @return
	* The guaranteed
	*/
	public Boolean getGuaranteed() {
	return guaranteed;
	 }

	/**
	* 
	* @param guaranteed
	* The guaranteed
	*/
	public void setGuaranteed(Boolean guaranteed) {
	this.guaranteed = guaranteed;
	 }

	/**
	* 
	* @return
	* The guests
	*/
	public List<Guest> getGuests() {
	return guests;
	 }

	/**
	* 
	* @param guests
	* The guests
	*/
	public void setGuests(List<Guest> guests) {
	this.guests = guests;
	 }

	/**
	* 
	* @return
	* The invoiceTransactions
	*/
	public List<Object> getInvoiceTransactions() {
	return invoiceTransactions;
	 }

	/**
	* 
	* @param invoiceTransactions
	* The invoiceTransactions
	*/
	public void setInvoiceTransactions(List<Object> invoiceTransactions) {
	this.invoiceTransactions = invoiceTransactions;
	 }

	/**
	* 
	* @return
	* The linkageNumber
	*/
	public Object getLinkageNumber() {
	return linkageNumber;
	 }

	/**
	* 
	* @param linkageNumber
	* The linkageNumber
	*/
	public void setLinkageNumber(Object linkageNumber) {
	this.linkageNumber = linkageNumber;
	 }

	/**
	* 
	* @return
	* The onsiteContactDetails
	*/
	public List<Object> getOnsiteContactDetails() {
	return onsiteContactDetails;
	 }

	/**
	* 
	* @param onsiteContactDetails
	* The onsiteContactDetails
	*/
	public void setOnsiteContactDetails(List<Object> onsiteContactDetails) {
	this.onsiteContactDetails = onsiteContactDetails;
	 }

	/**
	* 
	* @return
	* The period
	*/
	public Period getPeriod() {
	return period;
	 }

	/**
	* 
	* @param period
	* The period
	*/
	public void setPeriod(Period period) {
	this.period = period;
	 }

	/**
	* 
	* @return
	* The phone
	*/
	public Phone getPhone() {
	return phone;
	 }

	/**
	* 
	* @param phone
	* The phone
	*/
	public void setPhone(Phone phone) {
	this.phone = phone;
	 }

	/**
	* 
	* @return
	* The phoneTag
	*/
	public Object getPhoneTag() {
	return phoneTag;
	 }

	/**
	* 
	* @param phoneTag
	* The phoneTag
	*/
	public void setPhoneTag(Object phoneTag) {
	this.phoneTag = phoneTag;
	 }

	/**
	* 
	* @return
	* The securityLevel
	*/
	public String getSecurityLevel() {
	return securityLevel;
	 }

	/**
	* 
	* @param securityLevel
	* The securityLevel
	*/
	public void setSecurityLevel(String securityLevel) {
	this.securityLevel = securityLevel;
	 }

	/**
	* 
	* @return
	* The sentToProperty
	*/
	public Boolean getSentToProperty() {
	return sentToProperty;
	 }

	/**
	* 
	* @param sentToProperty
	* The sentToProperty
	*/
	public void setSentToProperty(Boolean sentToProperty) {
	this.sentToProperty = sentToProperty;
	 }

	/**
	* 
	* @return
	* The settlementMop
	*/
	public Object getSettlementMop() {
	return settlementMop;
	 }

	/**
	* 
	* @param settlementMop
	* The settlementMop
	*/
	public void setSettlementMop(Object settlementMop) {
	this.settlementMop = settlementMop;
	 }

	/**
	* 
	* @return
	* The status
	*/
	public String getStatus() {
	return status;
	 }

	/**
	* 
	* @param status
	* The status
	*/
	public void setStatus(String status) {
	this.status = status;
	 }

	/**
	* 
	* @return
	* The taxExemptDetail
	*/
	public Object getTaxExemptDetail() {
	return taxExemptDetail;
	 }

	/**
	* 
	* @param taxExemptDetail
	* The taxExemptDetail
	*/
	public void setTaxExemptDetail(Object taxExemptDetail) {
	this.taxExemptDetail = taxExemptDetail;
	 }

	/**
	* 
	* @return
	* The termsAndConditions
	*/
	public Object getTermsAndConditions() {
	return termsAndConditions;
	 }

	/**
	* 
	* @param termsAndConditions
	* The termsAndConditions
	*/
	public void setTermsAndConditions(Object termsAndConditions) {
	this.termsAndConditions = termsAndConditions;
	 }

	/**
	* 
	* @return
	* The travelAgency
	*/
	public Object getTravelAgency() {
	return travelAgency;
	 }

	/**
	* 
	* @param travelAgency
	* The travelAgency
	*/
	public void setTravelAgency(Object travelAgency) {
	this.travelAgency = travelAgency;
	 }

	/**
	* 
	* @return
	* The travelPlanAddress
	*/
	public TravelPlanAddress getTravelPlanAddress() {
	return travelPlanAddress;
	 }

	/**
	* 
	* @param travelPlanAddress
	* The travelPlanAddress
	*/
	public void setTravelPlanAddress(TravelPlanAddress travelPlanAddress) {
	this.travelPlanAddress = travelPlanAddress;
	 }

	/**
	* 
	* @return
	* The primaryGuest
	*/
	public PrimaryGuest getPrimaryGuest() {
	return primaryGuest;
	 }

	/**
	* 
	* @param primaryGuest
	* The primaryGuest
	*/
	public void setPrimaryGuest(PrimaryGuest primaryGuest) {
	this.primaryGuest = primaryGuest;
	 }

	/**
	* 
	* @return
	* The travelAgent
	*/
	public Object getTravelAgent() {
	return travelAgent;
	 }

	/**
	* 
	* @param travelAgent
	* The travelAgent
	*/
	public void setTravelAgent(Object travelAgent) {
	this.travelAgent = travelAgent;
	 }

	/**
	* 
	* @return
	* The travelPlanId
	*/
	public Long getTravelPlanId() {
	return travelPlanId;
	 }

	/**
	* 
	* @param travelPlanId
	* The travelPlanId
	*/
	public void setTravelPlanId(Long travelPlanId) {
	this.travelPlanId = travelPlanId;
	 }

	/**
	* 
	* @return
	* The siebelTravelPlanId
	*/
	public Long getSiebelTravelPlanId() {
	return siebelTravelPlanId;
	 }

	/**
	* 
	* @param siebelTravelPlanId
	* The siebelTravelPlanId
	*/
	public void setSiebelTravelPlanId(Long siebelTravelPlanId) {
	this.siebelTravelPlanId = siebelTravelPlanId;
	 }

	/**
	* 
	* @return
	* The travelPlanSegmentId
	*/
	public Long getTravelPlanSegmentId() {
	return travelPlanSegmentId;
	 }

	/**
	* 
	* @param travelPlanSegmentId
	* The travelPlanSegmentId
	*/
	public void setTravelPlanSegmentId(Long travelPlanSegmentId) {
	this.travelPlanSegmentId = travelPlanSegmentId;
	 }

	/**
	* 
	* @return
	* The vipLevel
	*/
	public String getVipLevel() {
	return vipLevel;
	 }

	/**
	* 
	* @param vipLevel
	* The vipLevel
	*/
	public void setVipLevel(String vipLevel) {
	this.vipLevel = vipLevel;
	 }

	/**
	* 
	* @return
	* The bundleDetailPresent
	*/
	public Boolean getBundleDetailPresent() {
	return bundleDetailPresent;
	 }

	/**
	* 
	* @param bundleDetailPresent
	* The bundleDetailPresent
	*/
	public void setBundleDetailPresent(Boolean bundleDetailPresent) {
	this.bundleDetailPresent = bundleDetailPresent;
	 }

	/**
	* 
	* @return
	* The onsiteMessagingEnabled
	*/
	public Boolean getOnsiteMessagingEnabled() {
	return onsiteMessagingEnabled;
	 }

	/**
	* 
	* @param onsiteMessagingEnabled
	* The onsiteMessagingEnabled
	*/
	public void setOnsiteMessagingEnabled(Boolean onsiteMessagingEnabled) {
	this.onsiteMessagingEnabled = onsiteMessagingEnabled;
	 }

	/**
	* 
	* @return
	* The bypassResortDesk
	*/
	public Object getBypassResortDesk() {
	return bypassResortDesk;
	 }

	/**
	* 
	* @param bypassResortDesk
	* The bypassResortDesk
	*/
	public void setBypassResortDesk(Object bypassResortDesk) {
	this.bypassResortDesk = bypassResortDesk;
	 }
}
