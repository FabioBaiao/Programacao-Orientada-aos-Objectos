import java.lang.Exception;

public class ImovelExisteException extends Exception{
     public ImovelExisteException(String msg){
        super(msg);
    }
}