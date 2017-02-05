import java.lang.Exception;

public class SemAutorizacaoException extends Exception{
    public SemAutorizacaoException(String msg){
        super(msg);
    }
}