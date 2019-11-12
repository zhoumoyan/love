package cn.LoveUO.controller;

import cn.LoveUO.controller.ex.*;
import cn.LoveUO.service.ex.*;
import cn.LoveUO.util.MyEditor;
import cn.LoveUO.util.ResponseResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 控制器类的基类
 */
public abstract class BaseController {
	
	/**
	 * 表示响应成功，用户的操作是正确的
	 */
	protected static final Integer SUCCESS = 200;

	/**
	 * 从Session中获取当前登录的用户的uid
	 * @param session
	 * @return 当前登录的用户的uid
	 */
	protected final Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}

	/**
	 * 从Session中获取当前登的code
	 * @param session
	 * @return 当前的code
	 */
	protected final String getCodeFromSession(HttpSession session) {
		return session.getAttribute("code").toString();
	}
	/**
	 * 从Session中获取当前登录的用户的avatar
	 * @param session
	 * @return 当前登录的用户的avatar
	 */
	protected final String getAvatarFromSession(HttpSession session) {
		if(null==session.getAttribute("avatar")){
			return "";
		}
		return session.getAttribute("avatar").toString();
	}

	/**
	 * 从Session中获取当前登录的用户的username
	 * @param session
	 * @return 当前登录的用户的username
	 */
	protected final String getUsernameFromSession(HttpSession session) {

		return session.getAttribute("username").toString();
	}

	@InitBinder
	public void initBinder(WebDataBinder wdb){
		wdb.registerCustomEditor(Date.class,new MyEditor());
	}

	@ExceptionHandler({ServiceException.class, FileUploadException.class ,CheckCodeException.class})
	public ResponseResult<Void> handleException(Throwable e) {
		ResponseResult<Void> rr = new ResponseResult<Void>();
		rr.setMessage(e.getMessage());
		
		if (e instanceof UsernameDuplicateException) {
			// 3000-尝试注册的用户名已经被占用
			rr.setState(3000);
		}else if (e instanceof CheckCodeException) {
			// 3001-验证码不正确异常
			rr.setState(3001);
		}else if (e instanceof EmailDuplicateException) {
			// 4000-尝试注册的邮箱已经被占用
			rr.setState(4000);
		}else if (e instanceof InsertException) {
			// 5000-插入数据异常
			rr.setState(5000);
		} else if (e instanceof PasswordNotMatchException) {
			// 6000-密码不匹配异常
			rr.setState(6000);
		} else if (e instanceof PasswordNotMatchException) {
			// 7000-用户名重复异常
			rr.setState(7000);
		} else if (e instanceof UpdateException) {
			// 8000-更新数据异常
			rr.setState(8000);
		} else if (e instanceof UserNotFoundException) {
			// 4001-用户数据不存在
			rr.setState(4001);
		} else if (e instanceof PasswordNotMatchException) {
			// 4002-验证密码失败
			rr.setState(4002);
		} else if (e instanceof InsertException) {
			// 5000-插入数据异常
			rr.setState(5000);
		} else if (e instanceof UpdateException) {
			// 5001-更新数据异常
			rr.setState(5001);
		} else if (e instanceof FileEmptyException) {
			// 6000-上传的文件为空
			rr.setState(6000);
		} else if (e instanceof FileSizeException) {
			// 6001-上传的文件大小超出了限制
			rr.setState(6001);
		} else if (e instanceof FileTypeException) {
			// 6002-上传的文件类型是不支持的
			rr.setState(6002);
		} else if (e instanceof FileStateException) {
			// 6003-文件状态异常，可能因为上传过程中文件被移动/删除，或因为其它原因无法访问到该文件
			rr.setState(6003);
		} else if (e instanceof FileUploadIOException) {
			// 6004-上传文件时出现读写异常
			rr.setState(6004);
		} else if (e instanceof DiaryNotFoundException) {
			// 7000-获取日记数据时出现异常
			rr.setState(7000);
		} else if (e instanceof UpdateAvatarException){
			// 7001-获取日记数据时出现异常
			rr.setState(7001);
		}
		return rr;
	}

}







