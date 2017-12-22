package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response.objects;

public class AccommodationDetail {

    private String planTypeName;
    private String arrivalDate;
    private String arrivateDate;
    private String departureDate;
    private String packagePlanType;
    private String endDate;
    private String startDate;
    private Integer facilityId;
    private String status;

    public String getPlanTypeName() {
        return planTypeName;
    }

    public void setPlanTypeName(String planTypeName) {
        this.planTypeName = planTypeName;
    }

    public String getPackagePlanType() {

        return packagePlanType;
    }

    public void setPackagePlanType(String packagePlanType) {
        this.packagePlanType = packagePlanType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getArrivalDate() {
        return arrivalDate;

    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivateDate() {
        return arrivateDate;

    }

    public void setArrivateDate(String arrivateDate) {
        this.arrivateDate = arrivateDate;
    }

    public String getDepartureDate() {
        return departureDate;

    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
