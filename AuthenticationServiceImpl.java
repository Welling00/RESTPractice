/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.service.impl;

import com.psl7504.app.ws.exceptions.AthenticationException;
import com.psl7504.app.ws.io.dao.DAO;
import com.psl7504.app.ws.io.dao.impl.MySQLDAO;
import com.psl7504.app.ws.service.AuthenticationService;
import com.psl7504.app.ws.service.UserService;
import com.psl7504.app.ws.shared.dto.UserDTO;
import com.psl7504.app.ws.ui.model.response.ErrorMessages;
import com.psl7504.app.ws.utils.UserProfileUtils;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;

/**
 *
 * @author psl7504
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    DAO database;
    
    public UserDTO authentication(String userName, String password) throws AuthenticationException {

        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUserbyUserName(userName);

        if (storedUser == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        String encryptedPassword = null;

        encryptedPassword = new UserProfileUtils().
                generateSecurePassword(password, storedUser.getSalt());

        boolean authentication = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authentication = true;
            }
        }

        if (!authentication) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        return storedUser;

    }

    public String issueAccessToken(UserDTO userProfile) throws AthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserID();

        byte[] encryptionAccessToken = null;
        try {
            encryptionAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (Exception e) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new AthenticationException("Failed to authenticate");
        }

        String encryptionAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptionAccessToken);
        
        //Split token into equal parts
        int tokenLength = encryptionAccessTokenBase64Encoded.length();
        
        String tokentoSaveDatabase = encryptionAccessTokenBase64Encoded.substring(0,tokenLength / 2);
        returnValue = encryptionAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
        
        userProfile.setToken(tokentoSaveDatabase);
        
        updateUserProfile(userProfile);
        
        return returnValue;

    }
    
    private void updateUserProfile(UserDTO userProfile){
        this.database = new MySQLDAO();
        try {
            database.openConection();
            database.updateUserProfile(userProfile);
        }finally{
            database.closeConection();
                   
        }
    }

    public void resetSecurityCredentials(String password, UserDTO userProfile) {

        //generate a new salt
        UserProfileUtils userProfileUtils = new UserProfileUtils();
        String salt = userProfileUtils.generateSalt(30);
        
        //generate new password
        String securePassword = userProfileUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePassword);
        
        //update user profile
        updateUserProfile(userProfile);
                
    }
}
