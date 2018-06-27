/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.exceptions;

import com.psl7504.app.ws.ui.model.response.ErrorMessage;
import com.psl7504.app.ws.ui.model.response.ErrorMessages;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author psl7504
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    public Response toResponse(Throwable exception) {
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        
        ErrorMessage errorMessage = new ErrorMessage(sw.toString(),
                 ErrorMessages.INTERNAL_SERVER_ERROR.name(), "http://appdeveloperblog.com");

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(errorMessage).build();

    }
    
    

}
