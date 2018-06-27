package com.psl7504.app.ws.ui.entrypoints;

import com.psl7504.app.ws.annotations.Secured;
import com.psl7504.app.ws.service.impl.UserServiceImpl;
import com.psl7504.app.ws.shared.dto.UserDTO;
import com.psl7504.app.ws.ui.model.request.CreateUserRequestModel;
import com.psl7504.app.ws.ui.model.response.UserProfileRest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;
import com.psl7504.app.ws.service.UserService;
import com.psl7504.app.ws.ui.model.request.DeleteUserProfileResponseModel;
import com.psl7504.app.ws.ui.model.request.UpdateUserRequestModel;
import com.psl7504.app.ws.ui.model.response.RequestOperation;
import com.psl7504.app.ws.ui.model.response.ResponseStatus;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author psl7504
 */
@Path("/users")
public class UserEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public UserProfileRest creatUser(CreateUserRequestModel requestObject) {
        UserProfileRest returnValue = new UserProfileRest();

        //Prepare UserDTO
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);

        //Create new User
        UserService userService = new UserServiceImpl();
        UserDTO createUserProfile = userService.createUser(userDto);

        //Prepare Response
        BeanUtils.copyProperties(createUserProfile, returnValue);
        return returnValue;
    }

    @Secured
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
    public UserProfileRest getUserProfile(@PathParam("id") String id) {
        UserProfileRest returnValue = null;
        UserService userService = new UserServiceImpl();
        UserDTO userProfile = userService.getUser(id);

        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);

        return returnValue;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start,
            @DefaultValue("50") @QueryParam("limit") int limit) {

        UserService userService = new UserServiceImpl();
        List<UserDTO> user = userService.getUsers(start, limit);

        //prepare return value
        List<UserProfileRest> returnsValue = new ArrayList<UserProfileRest>();
        for (UserDTO userDto : user) {
            UserProfileRest userModel = new UserProfileRest();
            BeanUtils.copyProperties(userDto, userModel);
            userModel.setHref("/users/" + userDto.getUserID());
            returnsValue.add(userModel);
        }

        return returnsValue;

    }

    @Secured
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest updateUserDetails(@PathParam("id") String id,
            UpdateUserRequestModel userDetails) {

        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);

        // Set only those fields you would like to be updated with this request
        if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
            storedUserDetails.setFirstName(userDetails.getFirstName());
        }
        storedUserDetails.setLastName(userDetails.getLastName());

        // Update User Details
        userService.updateUserDetails(storedUserDetails);

        // Prepare return value 
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Secured
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeleteUserProfileResponseModel deleteUserProfile(@PathParam("id") String id) {
        DeleteUserProfileResponseModel returnValue = new DeleteUserProfileResponseModel();
        returnValue.setRequestOperation(RequestOperation.DELETE);
        
        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);
 
        userService.deleteUser(storedUserDetails);

        returnValue.setResponseStatus(ResponseStatus.SUCCESS);
 
        return returnValue;
    }
}
