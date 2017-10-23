package com.disney.api.restServices.accommodation.accommodationBatchService.processData.request;

import java.util.ArrayList;
import java.util.List;

public class UpdateProcessStatusRequest {

    private List<Integer> processDataIdList = new ArrayList<>();
    private String processType;
    private String processingStatus;
    private String tpsId;
    private String errorMessage;

    public List<Integer> getProcessDataIdList() {
        return processDataIdList;
    }

    public void setProcessDataIdList(List<Integer> processDataIdList) {
        this.processDataIdList = processDataIdList;
    }

    public String getProcessType() {
        return processType;
    }

    /**
     * @param processType
     *            valid types MASS_REINSTATE, MASS_CANCEL, MASS_PRINTCOLLATERAL, MASS_KTTW, MASS_CHECKIN, MASS_MODIFY, ROOMINGLIST, REMOVEGROUP
     */
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    /**
     *
     * @param processingStatus
     *            valid types 'SUBMITTED', 'FAILED', 'BOOKED', 'INPROGRESS', 'SUCCESS'
     */
    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getTpsId() {
        return tpsId;
    }

    public void setTpsId(String tpsId) {
        this.tpsId = tpsId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}