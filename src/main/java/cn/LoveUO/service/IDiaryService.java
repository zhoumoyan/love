package cn.LoveUO.service;


import cn.LoveUO.entity.Diary;
import cn.LoveUO.service.ex.*;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 处理日记数据的业务层接口
 */
public interface IDiaryService {

	/**
	 * 写日记
	 * @param diary 日记数据
	 * @throws InsertException 插入数据异常，具体原因不明确
	 */
	void addDiary(Diary diary) throws InsertException;

	/**
	 * 获取所有日记
	 * @return  所有日记对象
	 * @throws DiaryNotFoundException 获取日记异常，具体原因不明确
	 */
	 Page<Diary> findAllDiary() throws DiaryNotFoundException;

	/**
	 * 获取个人所有日记
	 * @return  个人所有日记对象
	 * @throws DiaryNotFoundException 获取日记异常，具体原因不明确
	 */
	Page<Diary>  showPersonDiaries(Integer uid) throws DiaryNotFoundException;

	/**
	 * 更新头像
	 * @param diary
	 * @return
	 * @throws UpdateAvatarException 更新头像异常，具体原因不明确
	 */
    Integer updateAvatar(Diary diary) throws UpdateAvatarException;


	Diary showDiaryInfoById(Integer did);


	cn.LoveUO.entity.Page getPage();
}






