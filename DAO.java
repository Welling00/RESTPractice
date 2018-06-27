/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.io.dao;

import com.psl7504.app.ws.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author psl7504
 */
public interface DAO {
    void openConection();
    UserDTO getUserbyUserName(String userName);
    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String id);
    void updateUserProfile(UserDTO userProfile);
    void deleteUserProfile(UserDTO userProfile);
    List<UserDTO> getUsers(int start, int limmit);
    void closeConection();
    
}
