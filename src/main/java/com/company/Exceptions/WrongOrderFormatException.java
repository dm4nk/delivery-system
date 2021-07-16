package com.company.Exceptions;

public class WrongOrderFormatException extends Exception{
    public WrongOrderFormatException(String message){
        super(message);
    }
}
