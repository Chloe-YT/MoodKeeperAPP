package edu.fzu.moodkeeper.service;

import edu.fzu.moodkeeper.dataobject.DiaryDO;

import java.util.List;

public interface DiaryService {
    public List<DiaryDO> list();

    public void insert(DiaryDO diaryDO);

    int queryByUUID(DiaryDO diaryDO);

    int delete(String userId,String uuid);

    DiaryDO queryByUUIDAndUserId(String uuid);

    void updateByUserIdAndUuid(DiaryDO diaryDO);

    List<DiaryDO> queryNotIncludeUuids(String userId, List<String> list);
}
