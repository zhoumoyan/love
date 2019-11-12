package cn.LoveUO.service.impl;

import cn.LoveUO.entity.Diary;
import cn.LoveUO.mapper.DiaryMapper;
import cn.LoveUO.service.IDiaryService;
import cn.LoveUO.service.ex.*;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;



/**
 * 处理用户数据的业务层实现类
 */
@SuppressWarnings("ALL")
@Service
public class DiaryServiceImpl implements IDiaryService {

	@Autowired
	private DiaryMapper diaryMapper;

    @Override
    public void addDiary(Diary diary) throws InsertException {
        // 创建当前时间对象：new Date()
        Date now = new Date();
        // 封装2项日志数据
        diary.setCreatedTime(now);
        diary.setModifiedTime(now);
        // 执行添加日记，获取受影响的行数：diaryMapper.insert(user)
        Integer rows = diaryMapper.addnew(diary);
        // 判断受影响的行数是否不是预期值(1)
        if (rows != 1) {
            // 是：抛出异常InsertException
            throw new InsertException("保存失败！保存日记时出现未知错误！");
        }
    }

    @Override
    public Page<Diary> findAllDiary() throws DiaryNotFoundException {
        Page<Diary> diaries=diaryMapper.findAllDiary();
        if(diaries.size()==0){
            throw new DiaryNotFoundException("获取日记失败，出现未知错误");
        }
        return diaries;
    }

    @Override
    public Page<Diary> showPersonDiaries(Integer uid) throws DiaryNotFoundException {
        Page<Diary> diaries=diaryMapper.findPersonDiary(uid);
        if(diaries.size()==0){
            throw new DiaryNotFoundException("获取日记失败，你还未写过日记");
        }
        return diaries;
    }

    @Override
    public Integer updateAvatar(Diary diary) {
        Integer result=diaryMapper.updateAvatar(diary);
        if(null==result){
            throw new UpdateAvatarException("日记更新头像失败");
        }
        return result;
    }

    @Override
    public Diary showDiaryInfoById(Integer did) {
        Diary diary=diaryMapper.showDiaryByDid(did);
        return diary;
    }

    @Override
    public cn.LoveUO.entity.Page getPage() {

        Integer total=diaryMapper.countDiaries();
        cn.LoveUO.entity.Page page =new cn.LoveUO.entity.Page();
        page.setTotal(total);
        int count=5;
        int totalPage;
        // 假设总数是50，是能够被5整除的，那么就有10页
        if (0 == total % count)
            totalPage = total /count;
            // 假设总数是51，不能够被5整除的，那么就有11页
        else
            totalPage = total / count + 1;

        if(0==totalPage)
            totalPage = 1;

        page.setTotalPage(totalPage);

        return page;
    }


}




























































