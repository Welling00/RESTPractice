/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.exceptions;

import com.psl7504.app.ws.ui.model.response.ErrorMessage;
import com.psl7504.app.ws.ui.model.response.ErrorMessages;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author psl7504
 */
public class CouldNotDeleteRecoredExceptionMapper implements ExceptionMapper<CouldNotCreatRecordException>{

    public Response toResponse(CouldNotCreatRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage()
        , ErrorMessages.COULD_NOT_DELETE_RECORED.name(), "http://appdeveloperblog.com");
        
        
        return Response.status(Response.Status.EXPECTATION_FAILED).entity(errorMessage).build();
    }
    
}
