package cn.LoveUO.controller.ex;

public class CheckCodeException extends  RuntimeException{
    public CheckCodeException() {
        super();
    }

    public CheckCodeException(String message) {
        super(message);
    }

    public CheckCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckCodeException(Throwable cause) {
        super(cause);
    }

    protected CheckCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
