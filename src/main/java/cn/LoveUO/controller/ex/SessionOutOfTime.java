package cn.LoveUO.controller.ex;

public class SessionOutOfTime extends RuntimeException{

    private static final long serialVersionUID = -6778436949527425745L;

    public SessionOutOfTime() {
        super();
    }

    public SessionOutOfTime(String message) {
        super(message);
    }

    public SessionOutOfTime(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionOutOfTime(Throwable cause) {
        super(cause);
    }

    protected SessionOutOfTime(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
