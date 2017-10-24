
package com.disney.api.restServices.accommodation.accommodationBatchService.processData.request.objects;

public class MassCancelAccommodationRequestDetail {

    private String cancelContactName;
    private String cancelDate;
    private String cancelReasonCode;
    private ExternalReferenceDetail externalReferenceDetail;
    private Boolean isOverridden;
    private Boolean isWaived;
    private Integer overriddenCancelFee;
    private Integer travelComponentGroupingId;

    public String getCancelContactName() {
        return cancelContactName;
    }

    public void setCancelContactName(String cancelContactName) {
        this.cancelContactName = cancelContactName;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReasonCode() {
        return cancelReasonCode;
    }

    public void setCancelReasonCode(String cancelReasonCode) {
        this.cancelReasonCode = cancelReasonCode;
    }

    public ExternalReferenceDetail getExternalReferenceDetail() {
        return externalReferenceDetail;
    }

    public void setExternalReferenceDetail(ExternalReferenceDetail externalReferenceDetail) {
        this.externalReferenceDetail = externalReferenceDetail;
    }

    public Boolean getIsOverridden() {
        return isOverridden;
    }

    public void setIsOverridden(Boolean isOverridden) {
        this.isOverridden = isOverridden;
    }

    public Boolean getIsWaived() {
        return isWaived;
    }

    public void setIsWaived(Boolean isWaived) {
        this.isWaived = isWaived;
    }

    public Integer getOverriddenCancelFee() {
        return overriddenCancelFee;
    }

    public void setOverriddenCancelFee(Integer overriddenCancelFee) {
        this.overriddenCancelFee = overriddenCancelFee;
    }

    public Integer getTravelComponentGroupingId() {
        return travelComponentGroupingId;
    }

    public void setTravelComponentGroupingId(Integer travelComponentGroupingId) {
        this.travelComponentGroupingId = travelComponentGroupingId;
    }

}