package cn.LoveUO.service.ex;

/**
 * 用户名冲突异常，尝试注册的用户名已经被占用
 */
public class UsernameDuplicateException extends ServiceException {


	private static final long serialVersionUID = 1737217080642781117L;

	public UsernameDuplicateException() {
		super();
	}

	public UsernameDuplicateException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameDuplicateException(String message) {
		super(message);
	}

	public UsernameDuplicateException(Throwable cause) {
		super(cause);
	}

}
