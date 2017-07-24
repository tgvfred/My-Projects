package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.Node;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveTeamsByGroupCode extends AccommodationSalesServicePort{
	public RetrieveTeamsByGroupCode(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTeamsByGroupCode")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();

	}

	public void setgroupcode(String Value) {
		setRequestNodeValueByXPath("/Envelope/Body/retrieveTeamsByGroupCode/groupCode",Value);
		
	}
	
	public String getTeamCode() {return getResponseNodeValueByXPath("/Envelope/Body/retrieveTeamsByGroupCodeResponse/response/teamNames");}
	public int getCountOfTeamCode() {return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveTeamsByGroupCodeResponse/response/teamNames");}
	public List<String> getTeamNames(){
		List<String> teamNames = new ArrayList<>();
		NodeList nodes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTeamsByGroupCodeResponse/response/teamNames");
		for(int x = 0 ; x < nodes.getLength() ; x++ ){
			teamNames.add(nodes.item(x).getFirstChild().getTextContent());
		}
		
		return teamNames;
	}
	//public String[] getTeamCodes() {return getResponseNodeValueByXPath("/Envelope/Body/retrieveTeamsByGroupCodeResponse/response/teamNames");}
}
													