/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.ui.entrypoints;

import com.psl7504.app.ws.service.AuthenticationService;
import com.psl7504.app.ws.service.impl.AuthenticationServiceImpl;
import com.psl7504.app.ws.shared.dto.UserDTO;
import com.psl7504.app.ws.ui.model.request.LoginCradentials;
import com.psl7504.app.ws.ui.model.response.AuthenticationDetails;
import javax.naming.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author psl7504
 */
@Path("/authentication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class AuthenticationEntryPoint {

    public AuthenticationDetails userLogin(LoginCradentials loginCredentials) throws AuthenticationException {
        AuthenticationDetails returnValue = new AuthenticationDetails();

        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticateUser = authenticationService.authentication(loginCredentials.getUserName(), loginCredentials.getUserPassword());
        
        //Reset Access Token
        authenticationService.resetSecurityCredentials(loginCredentials.getUserPassword(), authenticateUser);
        
        String accessToken = authenticationService.issueAccessToken(authenticateUser);
        
        returnValue.setId(authenticateUser.getUserID());
        returnValue.setToken(accessToken);
        
        return returnValue;
    }

}
