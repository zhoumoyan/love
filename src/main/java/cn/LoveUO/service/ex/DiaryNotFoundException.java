package cn.LoveUO.service.ex;

public class DiaryNotFoundException extends ServiceException{
    public DiaryNotFoundException() {
        super();
    }

    public DiaryNotFoundException(String message) {
        super(message);
    }

    public DiaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiaryNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DiaryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
