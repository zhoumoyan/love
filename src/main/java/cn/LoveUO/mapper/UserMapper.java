package cn.LoveUO.mapper;

import cn.LoveUO.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {

	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @return 受影响的行数
	 */
	Integer addnew(User user);


	
	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */

	User findByUsername(String username);
	/**
	 * 根据邮箱查询用户数据
	 * @param  email 邮箱
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByEmail(String email);

	/**
	 * 更新密码
	 * @param uid 用户id
	 * @param password 新密码
	 * @param modifiedUser 最后修改执行人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updatePassword(
			@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime
	);

	/**
	 * 根据用户id查询用户数据
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User findByUid(Integer uid);

	/**
	 * 更新用户资料
	 * @param user 用户资料
	 * @return 受影响的行数
	 */
	Integer updateInfo(User user);

	/**
	 * 更新头像
	 * @param uid 用户id
	 * @param avatar 新的头像的路径
	 * @param modifiedUser 最后修改执行人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateAvatar(
			@Param("uid") Integer uid,
			@Param("avatar") String avatar,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime
	);

	/**
	 * 更改支付状态
	 * @param uid 用户id
	 * @param pay 支付状态pay
	 * @param modifiedUser 最后修改执行人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updatePay(@Param("uid") Integer uid,
					  @Param("pay") Integer pay,
					  @Param("modifiedUser") String modifiedUser,
					  @Param("modifiedTime") Date modifiedTime
	);
	
}








