package com.disney.api.soapServices.chargeGroup.operations;

import com.disney.api.soapServices.chargeGroup.ChargeGroup;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.XMLTools;

/**
 * @author Waightstill W Avery
 *
 */
public class ModifyChargeGroups extends ChargeGroup{
	public ModifyChargeGroups(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modifyChargeGroups")));
	
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	
	
	
	public void setGuestFirstName(String value){
		setGuestContainerResponsiblePartyFirstName(value);
		setResponsiblePartyFirstName(value);
	}
	public void setGuestLastName(String value){
		setGuestContainerResponsiblePartyLastName(value);
		setResponsiblePartyLastName(value);
	}
	public void setTravelPlanComponentGroupId(String value){
		setGuestContainedPrimaryReferenceValue(value);
	}
	public void setTravelPlanSegmentId(String value){
		setGuestContainedAncestorValue(value);
		setGuestContainerPrimaryReferenceValue(value);
	}
	public void setTravelPlanId(String value){
		setRootPrimaryReferenceValue(value);
		setGuestContainedRootReferenceValue(value);
		setGuestContainerRootReferenceValue(value);
	}
	
	public String getReturnParameter(){return getResponseNodeValueByXPath("/Envelope/Body/modifyChargeGroupsResponse/returnParameter");}
	
	//**************************
	//**************************
	//	rootChargeGroupRequestTO
	//**************************
	//**************************
	/**
	 * Remove entire rootChargeGroupRequestTO node
	 */
	public void removeRootChargeGroupRequestTO(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/description", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets rootChargeGroupRequestTO description
	 * @param value
	 */
	public void setRootDescription(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/description", value);}
	/**
	 * Remove entire rootChargeGroupRequestTO/period node
	 */
	public void removeRootPeriod(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/period", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Set rootChargeGroupRequestTO period end date
	 * @param value - rootChargeGroupRequestTO period end date
	 */
	public void setRootPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/period/endDate", value);}
	/**
	 * Set rootChargeGroupRequestTO period start date
	 * @param value - rootChargeGroupRequestTO period start date
	 */
	public void setRootPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/period/startDate", value);}
	/**
	 * Remove entire rootChargeGroupRequestTO/primaryReference node
	 */
	public void removeRootPrimaryReference(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Set rootChargeGroupRequestTO primary reference name
	 * @param value - rootChargeGroupRequestTO primary reference name
	 */
	public void setRootPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/primaryReference/referenceName", value);}
	/**
	 * Set rootChargeGroupRequestTO primary reference value
	 * @param value - rootChargeGroupRequestTO primary reference value
	 */
	public void setRootPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/primaryReference/referenceValue", value);}
	/**
	 * Set rootChargeGroupRequestTO default folio required
	 * @param value - rootChargeGroupRequestTO default folio required 
	 */
	public void setRootDefaultFolioRequired(String value){setRequestNodeValueByXPath("	/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/defaultFolioRequired", value);}
	/**
	 * Set rootChargeGroupRequestTO source accounting center
	 * @param value - rootChargeGroupRequestTO source accounting center 
	 */
	public void setRootSourceAccountingCenter(String value){setRequestNodeValueByXPath("	/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/sourceAccountingCenterID", value);}
	/**
	 * Set rootChargeGroupRequestTO responsible party
	 * @param value - rootChargeGroupRequestTO responsible party 
	 */
	public void setRootResponsibleParty(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/responsibleParty", value);}
	/**
	 * Set rootChargeGroupRequestTO folio type
	 * @param value - rootChargeGroupRequestTO folio type 
	 */
	public void setRootFolioType(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/folioType", value);}
	/**
	 * Set rootChargeGroupRequestTO delegate small balance write off
	 * @param value - rootChargeGroupRequestTO delegate small balance write off 
	 */
	public void setRootDelegateSmallBalanceWriteOff(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/delegateSmallBalanceWriteOff", value);}
	/**
	 * Set rootChargeGroupRequestTO responsible party first name
	 * @param value - rootChargeGroupRequestTO responsible party first name 
	 */
	public void setResponsiblePartyFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/responsiblePartyFirstName", value);}
	/**
	 * Set rootChargeGroupRequestTO responsible party last name
	 * @param value - rootChargeGroupRequestTO responsible party last name 
	 */
	public void setResponsiblePartyLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/rootChargeGroupRequestTO/responsiblePartyLastName", value);}

	
	//************************************
	//************************************
	//	guestContainedChargeGroupRequestTO
	//************************************
	//************************************
	/**
	 * Remove entire guestContainedChargeGroupRequestTO node
	 */
	public void removeGuestContainedChargeGroupRequestTO(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestContainedChargeGroupRequestTO description
	 * @param value - guestContainedChargeGroupRequestTO description
	 */
	public void setGuestContainedDescription(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/description", value);}
	/**
	 * Remove entire guestContainedChargeGroupRequestTO period node
	 */
	public void removeGuestContainedPeriod(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/period", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestContainedChargeGroupRequestTO period end date
	 * @param value - guestContainedChargeGroupRequestTO period end date 
	 */
	public void setGuestContainedPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/period/endDate", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO period start date
	 * @param value - guestContainedChargeGroupRequestTO period start date 
	 */
	public void setGuestContainedPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/period/startDate", value);}
	/**
	 * Remove entire guestContainedChargeGroupRequestTO primary reference node
	 */
	public void removeGuestContainedPrimaryReference(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestContainedChargeGroupRequestTO primary reference name
	 * @param value - guestContainedChargeGroupRequestTO primary reference name 
	 */
	public void setGuestContainedPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/primaryReference/referenceName", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO primary reference value
	 * @param value - guestContainedChargeGroupRequestTO primary reference value 
	 */
	public void setGuestContainedPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/primaryReference/referenceValue", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO transaction facility
	 * @param value - guestContainedChargeGroupRequestTO transaction facility 
	 */
	public void setGuestContainedTransactionFacility(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/transactionFacility", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO source accounting center
	 * @param value - guestContainedChargeGroupRequestTO source accounting center 
	 */
	public void setGuestContainedSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/sourceAccountingCenterID", value);}
	/**
	 * Remove entire guestContainedChargeGroupRequestTO source accounting center node
	 */
	public void removeGuestContainedAncestor(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/ancestor", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestContainedChargeGroupRequestTO ancestor name
	 * @param value - guestContainedChargeGroupRequestTO ancestor name 
	 */
	public void setGuestContainedAncestorName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/ancestor/referenceName", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO ancestor value
	 * @param value - guestContainedChargeGroupRequestTO ancestor value
	 */
	public void setGuestContainedAncestorValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/ancestor/referenceValue", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO delegate small balance write off
	 * @param value - guestContainedChargeGroupRequestTO delegate small balance write off 
	 */
	public void setGuestContainedDelegateSmallBalanceWriteOff(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/delegateSmallBalanceWriteOff", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO guarantee type
	 * @param value - guestContainedChargeGroupRequestTO guarantee type 
	 */
	public void setGuestContainedGuaranteeType(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/guaranteeType", value);}
	/**
	 * Remove entire guestContainedChargeGroupRequestTO root node
	 */
	public void removeGuestContainedRoot(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/root", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestContainedChargeGroupRequestTO root reference name
	 * @param value - guestContainedChargeGroupRequestTO root reference name 
	 */
	public void setGuestContainedRootReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/root/referenceName", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO root reference value
	 * @param value - guestContainedChargeGroupRequestTO  root reference value
	 */
	public void setGuestContainedRootReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/root/referenceValue", value);}
	/**
	 * Sets guestContainedChargeGroupRequestTO RSR
	 * @param value - guestContainedChargeGroupRequestTO RSR 
	 */
	public void setGuestContainedRsr(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestContainedChargeGroupRequestTO/rsr", value);}
	
	
	//************************************
	//************************************
	//	guestChargeGroupContainerRequestTO
	//************************************
	//************************************	
	/**
	 * Remove entire guestChargeGroupContainerRequestTO node
	 */
	public void removeGuestChargeGroupContainerRequestTO(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestChargeGroupContainerRequestTO description
	 * @param value - guestChargeGroupContainerRequestTO description
	 */
	public void setGuestContainerDescription(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/description", value);}
	/**
	 * Remove entire guestChargeGroupContainerRequestTO period node
	 */
	public void removeGuestContainerPeriod(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/period", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestChargeGroupContainerRequestTO period end date
	 * @param value - guestChargeGroupContainerRequestTO period end date
	 */
	public void setGuestContainerPeriodEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/period/endDate", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO period start date
	 * @param value - guestChargeGroupContainerRequestTO
	 */
	public void setGuestContainerPeriodStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/period/startDate", value);}
	/**
	 * Remove entire guestChargeGroupContainerRequestTO primary reference node
	 */
	public void removeGuestContainerPrimaryReference(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestChargeGroupContainerRequestTO primary reference name
	 * @param value - guestChargeGroupContainerRequestTO primary reference name
	 */
	public void setGuestContainerPrimaryReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/primaryReference/referenceName", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO primary reference value
	 * @param value - guestChargeGroupContainerRequestTO primary reference value
	 */
	public void setGuestContainerPrimaryReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/primaryReference/referenceValue", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO folio required
	 * @param value - guestChargeGroupContainerRequestTO
	 */
	public void setGuestContainerDefaultFolioRequired(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/defaultFolioRequired", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO source accountning center
	 * @param value - guestChargeGroupContainerRequestTO
	 */
	public void setGuestContainerSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/sourceAccountingCenterID", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO responsible party
	 * @param value - guestChargeGroupContainerRequestTO
	 */
	public void setGuestContainerResponsibleParty(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/responsibleParty", value);}
	/**
	 * Remove entire guestChargeGroupContainerRequestTO root reference node
	 */
	public void removeGuestContainerRootReference(){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/rootReference", BaseSoapCommands.REMOVE_NODE.toString());}
	/**
	 * Sets guestChargeGroupContainerRequestTO root reference name
	 * @param value - guestChargeGroupContainerRequestTO root reference name
	 */
	public void setGuestContainerRootReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/rootReference/referenceName", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO root reference value
	 * @param value - guestChargeGroupContainerRequestTO root reference value
	 */
	public void setGuestContainerRootReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/rootReference/referenceValue", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO responsible party first name
	 * @param value - guestChargeGroupContainerRequestTO responsible party first name
	 */
	public void setGuestContainerResponsiblePartyFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/responsiblePartyFirstName", value);}
	/**
	 * Sets guestChargeGroupContainerRequestTO responsible party last name
	 * @param value - guestChargeGroupContainerRequestTO responsible party last name
	 */
	public void setGuestContainerResponsiblePartyLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modifyChargeGroups/guestChargeGroupContainerRequestTO/responsiblePartyLastName", value);}
}
