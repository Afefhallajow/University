package Simple.University.System.demo.Exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}