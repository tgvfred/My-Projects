package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

/**
 * @deprecated deprecated as Add was only used by DVC and has now moved to
 *             {@link com.disney.api.soapServices.dvcModule.dvcSalesService.accommodationSales.operations.AddAccommodation} <br/>
 *             Will temporarily redirect to AccommodationFulfillment add
 * @author phlej001
 *
 */
@Deprecated
public class Add extends com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations.Add {
    public Add(String environment, String scenario) {
        super(environment, scenario);
    }
}
