
package com.disney.api.restServices.accommodation.accommodationBatchService.processData.request.objects;

public class ExternalReferenceDetail {

    private String externalReferenceType;
    private String externalReferenceCode;
    private String externalReferenceNumber;
    private String externalReferenceSource;

    public String getExternalReferenceType() {
        return externalReferenceType;
    }

    public void setExternalReferenceType(String externalReferenceType) {
        this.externalReferenceType = externalReferenceType;
    }

    public String getExternalReferenceCode() {
        return externalReferenceCode;
    }

    public void setExternalReferenceCode(String externalReferenceCode) {
        this.externalReferenceCode = externalReferenceCode;
    }

    public String getExternalReferenceNumber() {
        return externalReferenceNumber;
    }

    public void setExternalReferenceNumber(String externalReferenceNumber) {
        this.externalReferenceNumber = externalReferenceNumber;
    }

    public String getExternalReferenceSource() {
        return externalReferenceSource;
    }

    public void setExternalReferenceSource(String externalReferenceSource) {
        this.externalReferenceSource = externalReferenceSource;
    }

}
