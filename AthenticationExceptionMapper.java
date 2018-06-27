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
import javax.ws.rs.ext.Provider;

/**
 *
 * @author psl7504
 */
@Provider
class AthenticationExceptionMapper implements ExceptionMapper<AthenticationException>{

    public Response toResponse(AthenticationException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage()
        , ErrorMessages.AUTHENTICATION_FAILED.name(), "");
        
        
        return Response.status(Response.Status.UNAUTHORIZED).entity(errorMessage).build();
    }
}
