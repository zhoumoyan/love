package cn.LoveUO.controller;

import cn.LoveUO.entity.Diary;
import cn.LoveUO.entity.User;
import cn.LoveUO.service.IDiaryService;
import cn.LoveUO.service.IUserService;
import cn.LoveUO.service.ex.UserNotFoundException;
import cn.LoveUO.util.ResponseResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 处理日记相关请求的控制器类
 */
@RestController
@RequestMapping("diaries")
public class DiaryController extends BaseController{
    @Autowired
    private IDiaryService diaryService;
    @Autowired
    private IUserService userService;
    @PostMapping("change_info")
    public ResponseResult<Void> addDiary(Diary diary, HttpSession session) {
        // 封装uid、username、avatar数据
        // CreatedUser和ModifiedUser2项日志数据
        Integer uid=getUidFromSession(session);
        User user=userService.getByUid(uid);
        diary.setUid(uid);
        diary.setUsername(user.getUsername());
        diary.setAvatar(user.getAvatar());
        diary.setCreatedUser(user.getUsername());
        diary.setModifiedUser(user.getUsername());
        // 执行注册
        diaryService.addDiary(diary);
        // 返回响应结果
        return new ResponseResult<Void>(SUCCESS);
    }
    @GetMapping("allInfo")
    public ResponseResult<List> showAllInfo(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Page<Diary> diaryList= diaryService.findAllDiary();
        return new ResponseResult<>(SUCCESS,diaryList);
    }

    @GetMapping("personInfo")
    public ResponseResult<List> showPersonInfo(HttpSession session,Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        Integer uid=getUidFromSession(session);
        Page<Diary> diaries=diaryService.showPersonDiaries(uid);
        return new ResponseResult<>(SUCCESS,diaries);
    }

    @GetMapping("getUid")
    public ResponseResult<Void> getUid(HttpSession session){
        if(null==session.getAttribute("uid")){
            throw new UserNotFoundException("用户未登录！");
        }
        return new ResponseResult<>(SUCCESS);
    }

    @GetMapping("info/{did}")
    public ResponseResult<Diary> showDiaryInfoById(@PathVariable("did") Integer did){
        Diary diary=diaryService.showDiaryInfoById(did);
        return new ResponseResult<>(SUCCESS,diary);
    }

    @GetMapping("getPage")
    public ResponseResult<cn.LoveUO.entity.Page> getPage(){

        cn.LoveUO.entity.Page page=diaryService.getPage();
        return new ResponseResult<>(SUCCESS,page);
    }

}
