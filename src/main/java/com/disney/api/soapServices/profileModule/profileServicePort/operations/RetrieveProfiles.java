package com.disney.api.soapServices.profileModule.profileServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.profileModule.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class RetrieveProfiles extends ProfileServicePort{
	private String[] code;
	private String[] description;
	private String[] profileId;
	private String[] profileType;
	private String[] lastUpdateBy;
	private String[] lastUpdateOn;
	private String[] createdBy;
	private String[] createdOn;
	private String[] active;
	private String[] priority;
	private String[] routingType;
	private NodeList infos;
	
	public RetrieveProfiles(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProfiles")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the enterprise facility ID
	 * @param value - enterprise facility ID
	 */
	public void setEnterpriseFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/enterpriseFacilityId", value);}
	/**
	 * Sets the resort code
	 * @param value - resort code
	 */
	public void setResortCode(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/resortCode", value);}
	/**
	 * Sets the room type code
	 * @param value - room type code
	 */
	public void setRoomTypeCode(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/roomTypeCode", value);}
	/**
	 * Sets the package ID
	 * @param value - package ID
	 */
	public void setPackageId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/packageId", value);}
	/**
	 * Sets the retrieve only default profiles value
	 * @param value - retrieve only default profiles value
	 */
	public void setRetrieveOnlyDefaultProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/retrieveOnlyDefaultProfiles", value);}
	/**
	 * Sets the retrieve only selectable profiles value
	 * @param value - retrieve only selectable profiles value
	 */
	public void setRetrieveOnlySelectableProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/retrieveOnlySelectableProfiles", value);}
	/**
	 * Sets the profile type
	 * @param value - profile type
	 */
	public void setProfileType(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/profileType", value);}
	/**
	 * Sets the include inactive profiles value
	 * @param value - include inactive profiles value
	 */
	public void setIncludeInactiveProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfiles/retrieveProfilesRequest/includeInactiveProfiles", value);}
	/**
	 * Sets the profile infos data from teh SOAP response
	 */
	public void setProfileInfos(){
		infos = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProfilesResponse/return/profileInfos");
		int numInfos = infos.getLength();
		code = new String[numInfos];
		description = new String[numInfos];
		profileId = new String[numInfos];
		profileType = new String[numInfos];
		lastUpdateBy = new String[numInfos];
		lastUpdateOn = new String[numInfos];
		createdBy = new String[numInfos];
		createdOn = new String[numInfos];
		active = new String[numInfos];
		priority = new String[numInfos];
		routingType = new String[numInfos];
		for(int i = 0; i < infos.getLength(); i++){
			code[i] = XMLTools.getNodeList(infos.item(i), "code").item(0).getTextContent();
			description[i] = XMLTools.getNodeList(infos.item(i), "description").item(0).getTextContent();
			profileId[i] = XMLTools.getNodeList(infos.item(i), "profileId").item(0).getTextContent();
			profileType[i] = XMLTools.getNodeList(infos.item(i), "profileType").item(0).getTextContent();
			lastUpdateBy[i] = XMLTools.getNodeList(infos.item(i), "lastUpdateBy").item(0).getTextContent();
			lastUpdateOn[i] = XMLTools.getNodeList(infos.item(i), "lastUpdateOn").item(0).getTextContent();
			createdBy[i] = XMLTools.getNodeList(infos.item(i), "createdBy").item(0).getTextContent();
			createdOn[i] = XMLTools.getNodeList(infos.item(i), "createdOn").item(0).getTextContent();
			active[i] = XMLTools.getNodeList(infos.item(i), "active").item(0).getTextContent();
			priority[i] = XMLTools.getNodeList(infos.item(i), "priority").item(0).getTextContent();
			try{routingType[i] = XMLTools.getNodeList(infos.item(i), "routing/routingType").item(0).getTextContent();}
			catch(Exception e){routingType[i] = "";}
		}
	}
	/**
	 * Retrieves profile infos codes
	 * @return - profile infos codes
	 */
	public String[] getProfileInfosCodes(){return code;};
	/**
	 * Retrieves profile infos descriptions
	 * @return - profile infos descriptions
	 */
	public String[] getProfileInfosDescriptions(){return description;};
	/**
	 * Retrieves profile infos ids
	 * @return - profile infos ids
	 */
	public String[] getProfileInfosProfileIds(){return profileId;};
	/**
	 * Retrieves profile infos types
	 * @return - profile infos types
	 */
	public String[] getProfileInfosProfileTypes(){return profileType;};
	/**
	 * Retrieves profile infos last updated bys
	 * @return - profile infos last updated bys
	 */
	public String[] getProfileInfosLastUpdateBys(){return lastUpdateBy;};
	/**
	 * Retrieves profile infos last updated ons
	 * @return - profile infos last updated ons
	 */
	public String[] getProfileInfosLastUpdateOns(){return lastUpdateOn;};
	/**
	 * Retrieves profile infos created bys
	 * @return - profile infos created bys
	 */
	public String[] getProfileInfosCreatedBys(){return createdBy;};
	/**
	 * Retrieves profile infos created ons
	 * @return - profile infos created ons
	 */
	public String[] getProfileInfosCreatedOns(){return createdOn;};
	/**
	 * Retrieves profile infos actives
	 * @return - profile infos actives
	 */
	public String[] getProfileInfosActives(){return active;};
	/**
	 * Retrieves profile infos a prioritys
	 * @return - profile infos a prioritys
	 */
	public String[] getProfileInfosAPrioritys(){return priority;};
	/**
	 * Retrieves profile infos routing types
	 * @return - profile infos routing types
	 */
	public String[] getProfileInfosRoutingTypes(){return routingType;}
}
