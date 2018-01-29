package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

/**
 * @deprecated deprecated in 8.5
 * @author phlej001
 *
 */
@Deprecated
public class Reconcile extends AccommodationSalesServicePort {

    public Reconcile(String environment, String scenario) {
        super(environment);

        // Generate a request from a project xml file
        setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reconcile")));
        generateServiceContext();
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments();
        removeWhiteSpace();
    }

    public void setTravelPlanId(String TP_ID) {
        setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='reconcile'][1]/*[local-name(.)='request'][1]/*[local-name(.)='reconcilliationData'][1]/*[local-name(.)='tpId'][1]", TP_ID);
    }

    public void setTravelComponentGroupingId(String Tcg_ID) {
        setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='reconcile'][1]/*[local-name(.)='request'][1]/*[local-name(.)='reconcilliationData'][1]/*[local-name(.)='tcgId'][1]", Tcg_ID);
    }

}
