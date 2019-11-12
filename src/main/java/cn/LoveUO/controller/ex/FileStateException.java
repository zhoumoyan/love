package cn.LoveUO.controller.ex;

/**
 * 文件状态异常，可能因为上传过程中文件被移动/删除，或因为其它原因无法访问到该文件
 */
public class FileStateException extends FileUploadException {

	private static final long serialVersionUID = -7150153238399934786L;

	public FileStateException() {
		super();
	}

	public FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileStateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileStateException(String message) {
		super(message);
	}

	public FileStateException(Throwable cause) {
		super(cause);
	}

}
