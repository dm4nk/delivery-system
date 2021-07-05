package com.company.Exceptions;

public class WrongTaskFormatException extends Exception{
    public WrongTaskFormatException(String message){
        super(message);
    }
}
