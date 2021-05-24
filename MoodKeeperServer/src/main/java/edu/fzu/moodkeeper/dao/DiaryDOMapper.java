package edu.fzu.moodkeeper.dao;

import edu.fzu.moodkeeper.dataobject.DiaryDO;

import java.util.List;

public interface DiaryDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    int deleteByPrimaryKey(Long diaryId);

    int deleteByUserIdAndUuid(String userId, String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    int insert(DiaryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    int insertSelective(DiaryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    DiaryDO selectByPrimaryKey(Long diaryId);

    /**
     * 根据uuid查询和userId
     * @return
     */
    List<DiaryDO> selectByUUID(DiaryDO record);

    /**
     * 根据uuid查询和userId
     * @return
     */
    List<DiaryDO> queryNotIncludeUuids(String userId, List<String> list);

    DiaryDO queryByUUIDAndUserId(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    int updateByPrimaryKeySelective(DiaryDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table diary
     *
     * @mbg.generated Mon May 10 17:08:35 CST 2021
     */
    int updateByPrimaryKey(DiaryDO record);

    int updateByUserIdAndUuid(DiaryDO record);
}