package exceptions;

public class ComponentException extends RuntimeException {

    public ComponentException(String msg){
        super(msg);
    }

    public ComponentException(String msg, Throwable err){
        super(msg, err);
    }
}
