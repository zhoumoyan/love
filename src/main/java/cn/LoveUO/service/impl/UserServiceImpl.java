package cn.LoveUO.service.impl;

import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import cn.LoveUO.entity.User;
import cn.LoveUO.mapper.UserMapper;
import cn.LoveUO.service.IUserService;
import cn.LoveUO.service.ex.*;
import cn.LoveUO.util.MD5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;


/**
 * 处理用户数据的业务层实现类
 */
@SuppressWarnings("ALL")
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void reg(User user) throws UsernameDuplicateException, EmailDuplicateException,InsertException {
		// 根据参数user中的username查询数据：userMapper.findByUsername(user.getUsername());

		// 判断查询结果是否不为null
		if (userMapper.findByUsername(user.getUsername())!= null) {

			// 是：查询到数据，则用户名已经被占用，抛出异常UsernameDuplicateException
			throw new UsernameDuplicateException(
				"注册失败！用户名(" + user.getUsername() + ")已经被占用！");
		}
        // 根据参数user中的email查询数据：userMapper.findByEmail(user.getEmail());
        // 判断查询结果是否不为null
        if (userMapper.findByEmail(user.getEmail()) != null) {

            // 是：查询到数据，则用户名已经被占用，抛出异常EmailDuplicateException
            throw new EmailDuplicateException(
                    "注册失败！邮箱(" + user.getEmail()+ ")已经被占用！");
        }
		// 没有查询到数据，则用户名没有被占用，允许注册
        // 生成盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 对参数user中的password执行加密
        String md5Password = getMd5Password(user.getPassword(), salt);
        // 封装盐值和加密后的密码
        user.setSalt(salt);
        user.setPassword(md5Password);
		// 封装pay的值：user.setpPay(0);
		user.setPay(0);
		// 创建当前时间对象：new Date()
		Date now = new Date();
		// 封装4项日志数据
		user.setCreatedUser(user.getUsername());
		user.setCreatedTime(now);
		user.setModifiedUser(user.getUsername());
		user.setModifiedTime(now);

		// 执行注册，获取受影响的行数：userMapper.insert(user)
		Integer rows = userMapper.addnew(user);
		// 判断受影响的行数是否不是预期值(1)
		if (rows != 1) {
			// 是：抛出异常InsertException
			throw new InsertException("注册失败！插入数据时出现未知错误！");
		}

	}
    @Override
    public User login(String username, String password) throws UserNotFoundException,
            PasswordNotMatchException {
        // 根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        //System.out.println(result);
        // 判断查询结果是否为null
        if (result == null) {
            // 抛出异常：UserNotFoundException
            throw new UserNotFoundException("登录失败！用户名不存在！");
        }

        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 将参数password和盐值进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断加密后得到的密码与查询结果中的密码是否不匹配
        if (!result.getPassword().equals(md5Password)) {
            // 抛出异常：PasswordNotMatchException
            throw new PasswordNotMatchException(
                    "登录失败！密码错误！");
        }

        // 将查询结果中的password、salt、isDelete都设置为null
        result.setPassword(null);
        result.setSalt(null);
        // 返回查询结果
        return result;
    }

    @Override
    public void changePassword(Integer uid, String oldPassword, String newPassword)
            throws UserNotFoundException, PasswordNotMatchException, UpdateException {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null -> result == null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException(
                    "修改密码失败！用户数据不存在！");
        }


        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 将参数oldPassword结合盐值进行加密得到加密后的原密码oldMd5Password
        String oldMd5Password = getMd5Password(oldPassword, salt);
        // 判断查询结果中的密码与oldMd5Password是否不匹配
        if (!result.getPassword().equals(oldMd5Password)) {
            // 是：抛出PasswordNotMatchException
            throw new PasswordNotMatchException(
                    "修改密码失败！原密码错误！");
        }

        // 将参数newPassword结合盐值进行加密得到加密后的新密码newMd5Password
        String newMd5Password = getMd5Password(newPassword, salt);
        // 从查询结果中获取用户名
        String username = result.getUsername();
        // 创建当前时间对象
        Date now = new Date();
        // 执行更新，并获取受影响的行数
        Integer rows = userMapper.updatePassword(uid, newMd5Password, username, now);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException(
                    "修改密码失败！更新密码时出现未知错误！");
        }
    }


    @Override
    public void changeInfo(User user) throws UserNotFoundException, UpdateException {
        // 根据参数user.getUid()查询用户数据
        User result = userMapper.findByUid(user.getUid());
        // 判断查询结果是否为null -> result == null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException("修改个人资料失败！用户数据不存在！");
        }
        // 在参数user中封装modifiedUser和modifiedTime
        user.setModifiedUser(result.getUsername());
        user.setModifiedTime(new Date());
        // 执行修改，并获取返回的受影响的行数
        Integer rows = userMapper.updateInfo(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：UpdateExcption
            throw new UpdateException("修改个人资料失败！更新个人资料时出现未知错误！");
        }
    }

    @Override
    public User getByUid(Integer uid) throws UserNotFoundException {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：UserNotFoundException
            throw new UserNotFoundException(
                    "获取用户信息失败！用户数据不存在！");
        }
        // 将不必要的字段设置为null：password / salt / isDelete
        result.setPassword(null);
        result.setSalt(null);
        // 返回查询结果
        return result;
    }

    @Override
    public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null -> result == null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException(
                    "修改头像失败！用户数据不存在！");
        }

        // 从查询结果中获取用户名，作为modifiedUser
        String modifiedUser = result.getUsername();
        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 执行更新头像，并获取返回的受影响的行数
        Integer rows = userMapper.updateAvatar(uid, avatar, modifiedUser, now);
        // 判断受影响的行数是否不为1，如果是，则抛出异常
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException(
                    "修改头像失败！更新头像时出现未知错误！");
        }
    }

    public void changePay(Integer uid,Integer pay) throws UserNotFoundException, UpdateException {
        // 根据参数uid查询用户数据
        User result = userMapper.findByUid(uid);
        // 判断查询结果是否为null -> result == null
        if (result == null) {
            // 是：抛出UserNotFoundException
            throw new UserNotFoundException(
                    "更改VIP状态失败！用户数据不存在！");
        }

        // 从查询结果中获取用户名，作为modifiedUser
        String modifiedUser = result.getUsername();
        // 创建当前时间对象，作为modifiedTime
        Date now = new Date();
        // 执行更改支付状态，并获取返回的受影响的行数
        Integer rows = userMapper.updatePay(uid, pay, modifiedUser, now);
        // 判断受影响的行数是否不为1，如果是，则抛出异常
        if (rows != 1) {
            // 是：抛出UpdateException
            throw new UpdateException(
                    "修改VIP状态失败！出现未知错误！");
        }
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        User user =userMapper.findByUsername(username);
        if(user==null) {
            throw new UserNotFoundException("用户名未注册！");
        }
        return user;
    }

    public Boolean sendEmail(User user,HttpServletRequest request) {
        // 收件人电子邮箱
        String to = user.getEmail();
        // 发件人电子邮箱
        String from ="2993846768@qq.com";
        // 指定发送邮件的主机为smtp.qq.com
        String host = "smtp.qq.com";
        //QQ 邮件服务器
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return new javax.mail.PasswordAuthentication("2993846768@qq.com", "bkamduzvtbbqdgce");
                //发件人邮件用户名、密码
            }
        });
        try{// 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("找回密码!");
            // 设置消息体
            message.setText(this.createLink(user,request));
            // 发送消息
            Transport.send(message);
            System.out.println("发送邮件成功！");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }




    /**
     * 获取使用MD5摘要算法加密后的密码
     * @param password 原密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    private String getMd5Password(String password, String salt) {
        // 加密规则：原密码的左右两侧都拼接盐值，并循环加密5次
        String str = salt + password + salt;
        for (int i = 0; i < 5; i++) {
            str = DigestUtils.md5Hex(str).toUpperCase();
        }
        return str;
    }

    /**
     * @Description: 生成邮箱链接地址
     * @author Mr.chen
     * @date 2016-11-3 下午01:50:14
     */
    public String createLink(User user,HttpServletRequest request){

        //生成密钥
        String secretKey=UUID.randomUUID().toString();
        //设置过期时间
        Date outDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
        //System.out.println(System.currentTimeMillis());
        long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的

        //此处应该更新User表中的过期时间、密钥信息
        user.setOutDate(date);
        user.setValidataCode(secretKey);
        userMapper.updateInfo(user);
        //将用户名、过期时间、密钥生成链接密钥
        String key =user.getUsername() + "$" + date + "$" + secretKey;

        String digitalSignature = MD5Util.getMd5(key) ;// 数字签名

        String path=request.getContextPath();

        String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;

        String resetPassHref = basePath + "/users/toFindPassword/"+ digitalSignature +"/"+user.getUid();

        String emailContent = "请勿回复本邮件.点击下面的链接,重设密码,本邮件超过30分钟,链接将会失效，需要重新申请找回密码." + resetPassHref;

        return emailContent;
    }

}
