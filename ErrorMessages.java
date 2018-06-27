/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.ui.model.response;

/**
 *
 * @author psl7504
 */
public enum ErrorMessages {

    MISSING_REQUIRED_FIELD(
            "Missing required field. Please check "
            + "documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("There was an error"),
    NO_RECORED_FOUND("No Recourd Found"),
    AUTHENTICATION_FAILED("Falure to Athenticate Username or Password"),
    UPDATE_FAILED("User information failed to update"),
    COULD_NOT_DELETE_RECORED("Could not delete recored");
    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;

    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
