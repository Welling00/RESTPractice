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
public class AthenticationException extends RuntimeException{

    private static final long serialVersionUID = 254040936124177416L;

    public AthenticationException(String message){
        super(message);
    }
    
}
