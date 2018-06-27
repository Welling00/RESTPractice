/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.service;

import com.psl7504.app.ws.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author psl7504
 */
public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO getUser(String id);
    UserDTO getUserbyUserName(String userName);
    List<UserDTO> getUsers(int start, int limit);
    void updateUserDetails(UserDTO userDetails);
    void deleteUser(UserDTO UserDTO);
    
}
