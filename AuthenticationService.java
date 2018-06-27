/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.service;

import com.psl7504.app.ws.exceptions.AthenticationException;
import com.psl7504.app.ws.shared.dto.UserDTO;
import javax.naming.AuthenticationException;

/**
 *
 * @author psl7504
 */
public interface AuthenticationService {
    UserDTO authentication(String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AthenticationException; 
    void resetSecurityCredentials (String password, UserDTO userName);
}
