import java.lang.Exception;

public class EstadoInvalidoException extends Exception{
    public EstadoInvalidoException(String msg){
        super(msg);
    }
}