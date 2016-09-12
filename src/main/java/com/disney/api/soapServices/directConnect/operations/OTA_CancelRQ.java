/**
 * 
 */
package com.disney.api.soapServices.directConnect.operations;

import com.disney.api.soapServices.directConnect.DirectConnect;
import com.disney.utils.XMLTools;

/**
 * @author mccak082
 *
 */
public class OTA_CancelRQ extends DirectConnect{
	public OTA_CancelRQ (String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("OTA_CancelRQ")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
		/**Payload Section **/
		public void setPayloadName(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/@Name", value);}
		public void setPayloadVersion(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/@Version", value);}
		public void setPayloadLocation(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@Location",value);}
		public void setPayloadRequestId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@RequestId", value);}
		public void setPayloadRequestorId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@RequestorId", value);}
		public void setPayloadResponderId(String value){setRequestNodeValueByXPath("/Envelope/HeaderInterface/PayloadInfo/@ResponderId", value);}
		public void setPayloadDestinationId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@DestinationId", value);}
		public void setPayloadRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@RetryIndicator", value);}
		public void setPayloadSourceId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@SourceId", value);}
		public void setPayloadPassword(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/Authentication/@Password", value);}
		public void setPayloadUsername(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/Authentication/@Username", value);}
		public void setPayloadDescriptorName(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/PayloadDescriptor/@Name", value);}
		/**Main Section **/
		public void setOTACancelType(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/@CancelType",value);}
		public void setOTACancelEchoToken(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/@EchoToken",value);}
		public void setOTACancelPrimaryLangID(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/@PrimaryLangID",value);}
		public void setOTACancelTimeSTamp(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/@TimeStamp",value);}
		public void setOTACancelVersion(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/@Version",value);}
		/**
	     * Gets the number of nodes for a given xpath in the SOAP Request
	     * @param xpath - xpath for which to search
	     * @return - number of node for a given xpath
	     */
	    public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
		
	    public void setUniqueID (String ID, String ID_Context, String Type){
			// Determine if the index exists. If not, create it and the necessary
	        // child nodes. If so, then set the child node values
			int numberOfUniqueIDs= 1;
			try{
				numberOfUniqueIDs = getNumberOfRequestNodesByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID");
				getRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]");
			    numberOfUniqueIDs+=1;
			}catch(Exception e){}
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]","fx:addNode:Node:@ID");
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]","fx:addNode:Node:@ID_Context");
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]","fx:addNode:Node:@Type");
			//set values
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]/@ID",ID);
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]/@ID_Context",ID_Context);
			setRequestNodeValueByXPath("/Envelope/Body/OTA_CancelRQ/UniqueID["+numberOfUniqueIDs+"]/@Type",Type);

	    }
	    public String getRequestId(){
			return getResponseNodeValueByXPath("/Envelope/Header/Interface/Acknowledgement/@RequestId");
		}
}	


