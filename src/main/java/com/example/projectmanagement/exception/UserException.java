package com.example.projectmanagement.exception;

public class UserException extends Exception{
    public UserException(String message) {
        super(message);
    }
}
//Purpose: super is used to call a method from
// the superclass that has been overridden in the subclass.
