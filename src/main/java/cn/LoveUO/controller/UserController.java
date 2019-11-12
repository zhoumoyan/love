package cn.LoveUO.controller;

import cn.LoveUO.controller.ex.*;
import cn.LoveUO.entity.Diary;
import cn.LoveUO.entity.User;
import cn.LoveUO.service.IDiaryService;
import cn.LoveUO.service.IUserService;
import cn.LoveUO.util.ConfirmCodeUtil;
import cn.LoveUO.util.MD5Util;
import cn.LoveUO.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 处理用户相关请求的控制器类
 */
@Controller
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDiaryService diaryService;

    @PostMapping("reg")
    @ResponseBody
    public ResponseResult<Void> reg(User user) {
        // 执行注册
        userService.reg(user);
        // 返回响应结果
        return new ResponseResult<Void>(SUCCESS);
    }

    @PostMapping("login")
    @ResponseBody
    public ResponseResult<User> login(String username, String password, HttpSession session) {
        // 执行登录，获取返回值
        User data = userService.login(username, password);
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        session.setAttribute("avatar", data.getAvatar());
        // 响应成功
        return new ResponseResult<>(SUCCESS, data);
    }

    @RequestMapping("change_password")
    @ResponseBody
    public ResponseResult<Void> changePassword(
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword,
            HttpSession session) {
        // 从session中获取当前登录的用户的uid
        Integer uid = getUidFromSession(session);
        // 执行修改密码：userService.changePassowrd(uid,oldPassword,newPassword)
        userService.changePassword(uid, oldPassword, newPassword);
        // 返回
        return new ResponseResult<>(SUCCESS);
    }

    @GetMapping("info")
    @ResponseBody
    public ResponseResult<User> getByUid(HttpSession session) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        // 执行查询
        User user = userService.getByUid(uid);
        // 返回成功与查询结果
        return new ResponseResult<>(SUCCESS, user);
    }

    @RequestMapping("change_info")
    @ResponseBody
    public ResponseResult<Void> changeInfo(User user, HttpSession session) {
        // 从session中获取uid
        Integer uid = getUidFromSession(session);
        // 将uid封装到参数user中
        user.setUid(uid);
        // 执行修改
        userService.changeInfo(user);
        // 返回成功
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 上传的头像的最大大小
     */
    private static final long UPLOAD_MAX_SIZE = 3 * 1024 * 1024;
    /**
     * 允许上传的头像文件的类型列表
     */
    private static final List<String> UPLOAD_CONTENT_TYPES = new ArrayList<>();

    static {
        UPLOAD_CONTENT_TYPES.add("image/jpeg");
        UPLOAD_CONTENT_TYPES.add("image/bmp");
        UPLOAD_CONTENT_TYPES.add("image/png");
        UPLOAD_CONTENT_TYPES.add("image/gif");
    }

    @PostMapping("change_avatar")
    @ResponseBody
    public ResponseResult<String> changeAvatar(
            @RequestParam("avatar") MultipartFile avatar,
            HttpSession session) {
        // 检查是否为空
        if (avatar.isEmpty()) {
            throw new FileEmptyException(
                    "上传头像失败！请选择需要上传的文件！");
        }

        // 检查文件大小
        long size = avatar.getSize();
        System.out.println(size);
        if (size > UPLOAD_MAX_SIZE) {
            throw new FileSizeException(
                    "上传头像失败！请选择不超过" + UPLOAD_MAX_SIZE / 1024 + "KB文件！");
        }

        // 检查文件类型
        String contentType = avatar.getContentType();
        if (!UPLOAD_CONTENT_TYPES.contains(contentType)) {
            throw new FileTypeException(
                    "上传头像失败！不支持选择的文件类型！允许上传的类型有：" + UPLOAD_CONTENT_TYPES);
        }

        // 基于session获取上传文件夹的路径：session.getServletContext().getRealPath("upload")
        String parentPath = session.getServletContext().getRealPath("upload");
        // 检查上传文件夹是否存在，如果不存在，则创建
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 通过参数avatar获取原始文件名：avatar.getOriginalFilename()
        String originalFilename = avatar.getOriginalFilename();
        // 获取文件的扩展名
        int beginIndex = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(beginIndex);
        // 生成目标文件名
        String filename = System.currentTimeMillis() + suffix;

        // 执行保存头像文件
        File dest = new File(parent, filename);
        try {
            avatar.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw new FileStateException(
                    "上传头像失败！文件无法被访问！");
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadIOException(
                    "上传头像失败！读写数据时出现未知错误！");
        }

        // 执行将头像路径存储到数据库中："/upload/" + 目标文件名
        // http://localhost:8080/upload/1.jpg
        Integer uid = getUidFromSession(session);
        String avatarPath = "/upload/" + filename;
        userService.changeAvatar(uid, avatarPath);
        //将头像路径传到session中
        session.setAttribute("avatar", avatarPath);
        //更改日记表中的avatar
        Diary diary = new Diary();
        diary.setUid(uid);
        diary.setAvatar(avatarPath);
        diary.setModifiedTime(new Date());

        diaryService.updateAvatar(diary);

        // 返回成功及头像路径
        return new ResponseResult<>(SUCCESS, avatarPath);
    }

    @PostMapping("get_avatar")
    @ResponseBody
    public ResponseResult<User> getAvatar(HttpSession session) {
        User user = new User();
        String username = getUsernameFromSession(session);
        Integer uid = getUidFromSession(session);
        String avatar = getAvatarFromSession(session);
        user.setAvatar(avatar);
        user.setUsername(username);
        user.setUid(uid);

        return new ResponseResult<>(SUCCESS, user);
    }

    @PostMapping("exit")
    @ResponseBody
    public ResponseResult<Void> exit(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("uid");
        session.removeAttribute("avatar");
        return new ResponseResult<>(SUCCESS);
    }

    @RequestMapping("change_pay_state")
    @ResponseBody
    public ResponseResult<Void> change_pay_state(HttpSession session) {
        //查询uid
        Integer uid = getUidFromSession(session);
        //根据uid查询当前用户的支付状态
        User user = userService.getByUid(uid);
        int pay = 0;
        if (0 == user.getPay()) {
            pay = 1;
        }
        //业务层调用changpay（）改变支付状态
        userService.changePay(uid, pay);
        return new ResponseResult<>(SUCCESS);
    }

    @RequestMapping("getCode")
    @ResponseBody
    public void getCode(HttpSession session, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = ConfirmCodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        session.setAttribute("code", codeMap.get("code").toString());

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -1);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @PostMapping("checkCode")
    @ResponseBody
    public ResponseResult<Void> checkCode(HttpSession session, String code) {
        try {
            String sessionCode = getCodeFromSession(session);
            if (code != null && !"".equals(code) && sessionCode != null && !"".equals(sessionCode)) {
                if (code.equalsIgnoreCase(sessionCode)) {

                } else {
                    throw new CheckCodeException("验证码不正确");
                }
            } else {
                throw new CheckCodeException("验证码不正确");
            }
        } catch (SessionOutOfTime e) {
            //e.printStackTrace();

        }

        return new ResponseResult<>(SUCCESS);
    }
    @PostMapping("checkUsername")
    @ResponseBody
    public ResponseResult<Void> checkUsername(String username,HttpSession session) {
        User user=userService.findByUsername(username);
        session.setAttribute("uid", user.getUid());
        return new ResponseResult<>(SUCCESS);
    }

    @PostMapping("sendEmail")
    @ResponseBody
    public ResponseResult<String> sendEmail(HttpSession session,HttpServletRequest request) {
        //从session中获取uid；
        Integer uid =getUidFromSession(session);
        //根据uid查询user对象
        User user=userService.getByUid(uid);
        String email=user.getEmail();
        //调用业务层的发送邮件的方法；
        Boolean sendEmail=userService.sendEmail(user,request);
        //System.out.println(sendEmail);
        return new ResponseResult<>(SUCCESS,email);
    }
    @RequestMapping(value ="/toFindPassword/{sid}/{uid}", method = RequestMethod.GET)
    public String toFindPassword3(
            @PathVariable String sid,@PathVariable Integer uid,HttpServletRequest request) {
        String msg="验证成功";
        User user=userService.getByUid(uid);
        ResponseResult<String> result= new ResponseResult(SUCCESS,uid);


        if(sid.equals("") || uid.equals("")){
            msg="链接不完整,请重新生成";
            result.setMessage(msg);
        }
        if(user == null) {
            msg = "链接错误,无法找到匹配用户,请重新申请找回密码.";
            result.setMessage(msg);
        }
        //获取链接中的加密字符串
        System.out.println("sid="+sid);
        //获取链接中的用户名
        System.out.println("uid="+uid);

        //获取当前登陆人的加密码
        String key = user.getUsername()+"$"+user.getOutDate()/1000*1000+"$"+user.getValidataCode();//数字签名

        String digitalSignature = MD5Util.getMd5(key);// 数字签名

        if(!digitalSignature.equals(sid)) {
            System.out.println(!digitalSignature.equals(sid));
            msg = "链接不正确,是否已经过期了?重新申请吧";
            result.setMessage(msg);

        }
        //返回到修改密码的界面
        request.setAttribute("result",result);
        return "index";
    }
}










