package cn.LoveUO.service;


import cn.LoveUO.entity.Diary;
import cn.LoveUO.entity.User;
import cn.LoveUO.service.ex.*;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {

	/**
	 * 用户注册
	 * @param user 用户数据
	 * @throws UsernameDuplicateException 用户名冲突异常，尝试注册的用户名已经被占用
	 * @throws EmailDuplicateException 邮箱冲突异常，尝试注册的邮箱已经被占用
	 * @throws InsertException 插入数据异常，具体原因不明确
	 */
	void reg(User user)
		throws UsernameDuplicateException, EmailDuplicateException,InsertException;
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户信息
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 密码错误
	 */
	User login(String username, String password)
			throws UserNotFoundException,
			PasswordNotMatchException;
	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws PasswordNotMatchException 密码错误
	 * @throws UpdateException 更新数据异常
	 */
	void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException,
			PasswordNotMatchException, UpdateException;

	/**
	 * 修改个人资料
	 * @param user 个人资料
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeInfo(User user) throws UserNotFoundException, UpdateException;

	/**
	 * 根据用户id获取用户基本信息
	 * @param uid 用户id
	 * @return 匹配的用户基本信息，如果没有匹配的数据，将抛出异常
	 * @throws UserNotFoundException 用户数据不存在
	 */
	User getByUid(Integer uid) throws UserNotFoundException;

	/**
	 * 修改头像
	 * @param uid 用户的id
	 * @param avatar 新的头像的路径
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changeAvatar(Integer uid, String avatar)
			throws UserNotFoundException, UpdateException;

	/**
	 * 修改支付状态
	 * @param uid 用户的id
	 * @param pay 新的支付状态
	 * @throws UserNotFoundException 用户数据不存在
	 * @throws UpdateException 更新数据异常
	 */
	void changePay(Integer uid, Integer pay)
			throws UserNotFoundException, UpdateException;

	/**
	 * 查询用户名是否存在
	 * @param username
	 * @return
	 * @throws UserNotFoundException
	 */
	User findByUsername(String username) throws UserNotFoundException;

	/**
	 * 给email地址发送验证邮件
	 * @param user
	 * @param request
	 * @return
	 */
    Boolean sendEmail(User user, HttpServletRequest request);
}






