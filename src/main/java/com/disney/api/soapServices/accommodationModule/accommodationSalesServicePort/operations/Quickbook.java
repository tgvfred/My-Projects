package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

/**
 * @deprecated deprecated as Quickbook is moving to AccommodationFulfillment.
 *             Usage of this calls will redirect to {@link com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.Quickbook}
 * @author phlej001
 *
 */
@Deprecated
public class Quickbook extends com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.Quickbook {

    /**
     * @deprecated deprecated as Quickbook is moving to AccommodationFulfillment.
     *             Usage of this calls will redirect to {@link com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.Quickbook}
     * @author phlej001
     *
     */
    public Quickbook(String environment, String scenario) {
        super(environment, scenario);
    }
}