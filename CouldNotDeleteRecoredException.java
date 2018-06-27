/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psl7504.app.ws.exceptions;

/**
 *
 * @author psl7504
 */
public class CouldNotDeleteRecoredException extends RuntimeException{

    private static final long serialVersionUID = 6918316736508647895L;

    public CouldNotDeleteRecoredException(String message){
        super(message);
    }
    
    
}
