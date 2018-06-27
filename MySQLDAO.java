/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.io.dao.impl;

import com.psl7504.app.ws.io.dao.DAO;
import com.psl7504.app.ws.io.entity.UserEntity;
import com.psl7504.app.ws.shared.dto.UserDTO;
import com.psl7504.app.ws.utils.HibernateUtils;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author psl7504
 */
public class MySQLDAO implements DAO {

    Session session;

    public void openConection() {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        session = sessionFactory.openSession();
    }
    
     public UserDTO getUser(String id) {
         CriteriaBuilder cb = session.getCriteriaBuilder();
         
         //create Critera against a particular persistent class class
         CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
         
        //Query roots always reference entitie
         Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
         criteria.select(profileRoot);
         criteria.where(cb.equal(profileRoot.get("userId"), id));
        
         //Fetch single result
         UserEntity userEntity = session.createQuery(criteria).getSingleResult();
         
         UserDTO userDTO = new UserDTO();
         BeanUtils.copyProperties(userEntity, userDTO);
        
        return userDTO;
    }

    public UserDTO getUserbyUserName(String userName) {
        UserDTO user = null;

        CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistaent class
        //inform information that will be queried
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entitie
        //selects user entity 
        //configure it by recored 'email'
        Root<UserEntity> profileRoot = criteria.from(UserEntity.class);
        criteria.select(profileRoot);
        criteria.where(cb.equal(profileRoot.get("email"), userName));

        //Fetch single result
        //usering value passed to criteria
        Query<UserEntity> query = session.createQuery(criteria);
        List<UserEntity> resultList = query.getResultList();
        //checking if it is valid
        if (resultList != null && resultList.size() > 0) {
            UserEntity userEntity = resultList.get(0);
            user = new UserDTO();
            BeanUtils.copyProperties(userEntity, user);
        }

        return user;
    }

    public void closeConection() {
        if (session != null) {
            session.close();
        }

    }

    public UserDTO saveUser(UserDTO user) {

        UserDTO returnValue = null;
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        
        session.beginTransaction();
        session.save(userEntity);
        session.getTransaction().commit();
        
        returnValue = new UserDTO();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    public void updateUserProfile(UserDTO userProfile) {
        
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);
        
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();
        
       
        
    }

    public List<UserDTO> getUsers(int start, int limit) {

     CriteriaBuilder cb = session.getCriteriaBuilder();

        //Create Criteria against a particular persistent class
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);

        //Query roots always reference entities
        Root<UserEntity> userRoot = criteria.from(UserEntity.class);
        criteria.select(userRoot);

        // Fetch results from start to a number of "limit"
        List<UserEntity> searchResults = session.createQuery(criteria).
                setFirstResult(start).
                setMaxResults(limit).
                getResultList();
 
        List<UserDTO> returnValue = new ArrayList<UserDTO>();
        for (UserEntity userEntity : searchResults) {
            UserDTO userDto = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }

    public void deleteUserProfile(UserDTO userProfile) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userProfile, userEntity);
        
        session.beginTransaction();
        session.update(userEntity);
        session.getTransaction().commit();

    }


   

}
