package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.OverrideRate;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RemoveRateOverride;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class RemoveRateOverrideHelper {

    private String chargeAmount;

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void validateCharge(String env, ReplaceAllForTravelPlanSegment book) {

        String initialChargeAmount = runValidationSql(env, book.getTravelComponentId());
        callOverrideRate(env, book);
        String secondChargeAmount = runValidationSql(env, book.getTravelComponentId());
        validateChargeAmountsDifferent(initialChargeAmount, secondChargeAmount);
        callRemoveRateOverride(env, book);
        String finalChargeAmount = runValidationSql(env, book.getTravelComponentId());
        validateChargeAmountsEqual(initialChargeAmount, finalChargeAmount);

    }

    private String runValidationSql(String env, String tcId) {

        String sql = "select * "
                + " from folio.chrg_extnl_ref a"
                + " join folio.chrg b on a.chrg_id = b.chrg_id"
                + " join folio.chrg_item c on b.chrg_id = c.chrg_id"
                + " where a.CHRG_EXTNL_REF_VL = '" + tcId + "'";

        Database db = new OracleDatabase(env, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for sql", sql);
        }

        for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
            if (rs.getValue("REV_TYP_NM").equals("Base")) {
                chargeAmount = rs.getValue("CHRG_ITEM_AM");
                break;
            }
        }

        return chargeAmount;
    }

    private void callOverrideRate(String env, ReplaceAllForTravelPlanSegment book) {

        TestReporter.logStep("Call Override Rate service");

        OverrideRate override = new OverrideRate(env, "Main");
        override.setTravelPlanSegementId(book.getTravelPlanSegmentId());
        override.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/additionalAdultChargesOverridden", BaseSoapCommands.REMOVE_NODE.toString());
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/additionalCharge", Randomness.randomNumber(2).toString() + ".0");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/additionalChargeOverridden", "true");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/basePrice", Randomness.randomNumberBetween(1, 5) + ".0");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/dayCount", "0");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/overidden", "false");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/shared", "false");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/rackRate/date", Randomness.generateCurrentXMLDate() + "T00:00:00");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/rackRate/rate", "1" + Randomness.randomNumber(2).toString());
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/netPrice", Randomness.randomNumber(3).toString() + ".0");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/pointsValue", "0");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/overrideReason", "RTGSTINC");
        override.setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/locationId", "1");
        override.sendRequest();

        TestReporter.logAPI(!override.getResponseStatusCode().equals("200"), "Error sending request: " + override.getFaultString(), override);
    }

    private void callRemoveRateOverride(String env, ReplaceAllForTravelPlanSegment book) {

        TestReporter.logStep("Call Remove Rate Override service");

        RemoveRateOverride remove = new RemoveRateOverride(env, "removeRateOverride");
        remove.settravelPlanSegmentId(book.getTravelPlanSegmentId());
        remove.setaccomComponentId(book.getTravelComponentId());
        remove.setlocationId("0");
        remove.sendRequest();

        TestReporter.logAPI(!remove.getResponseStatusCode().equals("200"), "Error sending request", remove);
    }

    private void validateChargeAmountsDifferent(String charge1, String charge2) {
        TestReporter.logStep("Validate that charge amounts are different, showing that the rate has been overridden");
        TestReporter.assertFalse(charge1.equals(charge2), "Verify that the first charge amount [" + charge1 + "] is different than the second charge amount [" + charge2 + "]");

    }

    private void validateChargeAmountsEqual(String charge1, String charge2) {
        TestReporter.logStep("Validate that charge amounts are the same, showing that the rate override has been removed");
        TestReporter.assertEquals(charge1, charge2, "Verify that the first charge amount [" + charge1 + "] is equal to the second charge amount [" + charge2 + "]");

    }
}
