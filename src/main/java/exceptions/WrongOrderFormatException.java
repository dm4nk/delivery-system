package exceptions;

public class WrongOrderFormatException extends Exception{
    public WrongOrderFormatException(String message){
        super(message);
    }
}
