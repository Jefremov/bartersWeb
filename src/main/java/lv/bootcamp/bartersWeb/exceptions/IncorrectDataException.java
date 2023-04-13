package lv.bootcamp.bartersWeb.exceptions;

public class IncorrectDataException extends Exception{

    public IncorrectDataException(String errorMessage){
        super(errorMessage);
    }
}
