package cn.LoveUO.controller.ex;

public class AvatarNotFindException extends FileStateException{
    public AvatarNotFindException() {
        super();
    }

    public AvatarNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AvatarNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvatarNotFindException(String message) {
        super(message);
    }

    public AvatarNotFindException(Throwable cause) {
        super(cause);
    }
}
