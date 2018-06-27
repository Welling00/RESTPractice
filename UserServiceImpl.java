/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.service.impl;

import com.psl7504.app.ws.exceptions.CouldNotCreatRecordException;
import com.psl7504.app.ws.exceptions.CouldNotDeleteRecoredException;
import com.psl7504.app.ws.exceptions.CouldNotUpdateRecoredException;
import com.psl7504.app.ws.exceptions.NoRecordFoundException;
import com.psl7504.app.ws.io.dao.DAO;
import com.psl7504.app.ws.io.dao.impl.MySQLDAO;
import com.psl7504.app.ws.shared.dto.UserDTO;
import com.psl7504.app.ws.ui.model.response.ErrorMessages;
import com.psl7504.app.ws.utils.UserProfileUtils;
import com.psl7504.app.ws.service.UserService;
import java.util.List;

/**
 *
 * @author psl7504
 */
public class UserServiceImpl implements UserService {

    DAO database;

    public UserServiceImpl() {
        this.database = new MySQLDAO();
    }

    UserProfileUtils userProfileUtils = new UserProfileUtils();

    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;

        //Valadate the required fields
        userProfileUtils.validateRequiredFields(user);

        //Check if user already exists
        UserDTO existingUser = this.getUserbyUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNotCreatRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        //Generate secure public user id
        String userID = userProfileUtils.generateUserID(30);
        user.setUserID(userID);

        //Generate salt
        String salt = userProfileUtils.generateSalt(30);
        user.setSalt(salt);

        //Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setEncryptedPassword(encryptedPassword);

        //Record data into a database
        returnValue = this.saveUser(user);

        //Return back the user profile
        return returnValue;
    }

    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConection();
            returnValue = this.database.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());
        } finally {
            this.database.closeConection();
        }
        return returnValue;
    }

    @Override
    public UserDTO getUserbyUserName(String userName) {

        UserDTO userDto = null;

        if (userName == null || userName.isEmpty()) {
            return userDto;
        }

        //Connect to DataBase
        try {
            this.database.openConection();
            userDto = this.database.getUserbyUserName(userName);
        } finally {
            this.database.closeConection();
        }
        return userDto;
    }

    private UserDTO saveUser(UserDTO user) {

        UserDTO returnValue = null;

        //Connect to Database   
        try {
            this.database.openConection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConection();
        }

        return returnValue;
    }

    public List<UserDTO> getUsers(int start, int limmit) {

        List<UserDTO> user = null;
        try {
            this.database.openConection();
            user = this.database.getUsers(start, limmit);
        } finally {
            this.database.closeConection();
        }
        return user;
    }

    public void updateUserDetails(UserDTO userDetails) {
        try {
            this.database.openConection();
            this.database.updateUserProfile(userDetails);
        } catch (Exception e) {
            throw new CouldNotUpdateRecoredException(e.getMessage());
        } finally {
            this.database.closeConection();
        }

    }

    public void deleteUser(UserDTO userDTO) {
        try {
            this.database.openConection();
            this.database.deleteUserProfile(userDTO);
        } catch (Exception e) {
            throw new CouldNotDeleteRecoredException(e.getMessage());
        } finally {
            this.database.closeConection();
        }

        //verify user was deleted
        try {
            userDTO = getUser(userDTO.getUserID());
        } catch (NoRecordFoundException e) {
            userDTO = null;
        }

        if (userDTO != null) {
            throw new CouldNotDeleteRecoredException(
                    ErrorMessages.COULD_NOT_DELETE_RECORED.getErrorMessage());

        }
    }
}
