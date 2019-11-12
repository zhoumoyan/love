package cn.LoveUO.service.ex;

public class UpdateAvatarException extends UpdateException{
    public UpdateAvatarException() {
        super();
    }

    public UpdateAvatarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UpdateAvatarException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateAvatarException(String message) {
        super(message);
    }

    public UpdateAvatarException(Throwable cause) {
        super(cause);
    }
}
