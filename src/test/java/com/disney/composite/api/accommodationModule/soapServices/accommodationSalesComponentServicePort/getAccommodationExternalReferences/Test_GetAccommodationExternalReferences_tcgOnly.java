package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.getAccommodationExternalReferences;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.GetAccommodationExternalReferences;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_GetAccommodationExternalReferences_tcgOnly extends AccommodationBaseTest {

    private String extNum;
    private String extSrc;

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "GetAccommodationExternalReferences" })
    public void testGetAccommodationExternalReferences_tcgOnly() {

        GetAccommodationExternalReferences get = new GetAccommodationExternalReferences(environment, "Main");
        get.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        get.sendRequest();
        TestReporter.logAPI(!get.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", get);

        // Compares the response values to the DB values
        validation(get.getExternalReferenceNumber(), get.getExternalReferenceSource());

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            GetAccommodationExternalReferences clone = (GetAccommodationExternalReferences) get.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(get, true), "Validating Response Comparison");
        }

    }

    public void validation(String num, String src) {

        String sql = "select * from res_mgmt.TC_EXTNL_REF a where a.TC_EXTNL_REF_VL = '" + num + "'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        extNum = rs.getValue("TC_EXTNL_REF_VL");
        extSrc = rs.getValue("TC_EXTNL_SRC_NM");

        TestReporter.assertEquals(extNum, num, "Verify the External Reference Number [" + num + "] matches the External Reference Number found"
                + " in the DB [" + extNum + "]");

        TestReporter.assertEquals(extSrc, src, "Verify the External Reference Source [" + src + "] matches the External Reference Source found"
                + " in the DB [" + extSrc + "]");
    }

}
