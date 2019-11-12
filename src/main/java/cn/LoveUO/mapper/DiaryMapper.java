package cn.LoveUO.mapper;

import cn.LoveUO.entity.Diary;
import com.github.pagehelper.Page;

import java.util.Date;

/**
 * 处理用户数据的持久层接口
 */
public interface DiaryMapper {
	/**
	 * 插入日记数据
	 * @param diary
	 * @return
	 */
	Integer addnew(Diary diary);

	/**
	 * 根据completedTime时间查询日记对象
	 * @param completedTime
	 * @return
	 */
	Diary findByCompleteTime(Date completedTime);

	/**
	 * 查询所有日记对象总数
	 * @return
	 */
	Integer countDiaries();
	/**
	 * 查询所有日记对象
	 * @return
	 */
	Page<Diary> findPersonDiary(Integer uid);

	/**
	 * 更新日记表中的头像地址
	 * @param diary
	 * @return
	 */
	Integer updateAvatar(Diary diary);

	/**
	 * 根据did查询日记信息
	 * @param did
	 * @return
	 */
	Diary showDiaryByDid(Integer did);

	/**
	 * 获取所有日记信息
	 * @return
	 */
	Page<Diary> findAllDiary();
}








