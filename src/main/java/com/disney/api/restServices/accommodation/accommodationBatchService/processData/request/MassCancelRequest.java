
package com.disney.api.restServices.accommodation.accommodationBatchService.processData.request;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.accommodation.accommodationBatchService.processData.request.objects.MassCancelAccommodationRequestDetail;

public class MassCancelRequest {

    private String processName;
    private List<MassCancelAccommodationRequestDetail> massCancelAccommodationRequestDetails = new ArrayList<MassCancelAccommodationRequestDetail>();

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public List<MassCancelAccommodationRequestDetail> getMassCancelAccommodationRequestDetails() {
        return massCancelAccommodationRequestDetails;
    }

    public void setMassCancelAccommodationRequestDetails(List<MassCancelAccommodationRequestDetail> massCancelAccommodationRequestDetails) {
        this.massCancelAccommodationRequestDetails = massCancelAccommodationRequestDetails;
    }

}