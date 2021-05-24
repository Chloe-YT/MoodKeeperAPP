package edu.fzu.moodkeeper.service;

import edu.fzu.moodkeeper.dao.DiaryDOMapper;
import edu.fzu.moodkeeper.dataobject.DiaryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl implements DiaryService {

    /**
     * Controller层接收View层传来的参数，
     * 将参数转给Service层
     * Service层再调用DAO层访问数据
     * Service层再将DAO层返回给Controller层
     * Controller层将Service层返回传递给View层
     */
    @Autowired
    private DiaryDOMapper diaryDOMapper;

    public DiaryServiceImpl() {
    }


    @Override
    public List<DiaryDO> list() {
        return null;
    }

    @Override
    public void insert(DiaryDO diaryDO) {
        diaryDOMapper.insert(diaryDO);
    }

    @Override
    public int queryByUUID(DiaryDO diaryDO) {
        List<DiaryDO> diaryDOS = diaryDOMapper.selectByUUID(diaryDO);
        return diaryDOS.size();
    }

    @Override
    public int delete(String userId, String uuid) {
        return diaryDOMapper.deleteByUserIdAndUuid(userId,uuid);
    }

    @Override
    public DiaryDO queryByUUIDAndUserId(String uuid) {
        return diaryDOMapper.queryByUUIDAndUserId(uuid);
    }

    @Override
    public void updateByUserIdAndUuid(DiaryDO diaryDO) {
        diaryDOMapper.updateByUserIdAndUuid(diaryDO);
    }

    @Override
    public List<DiaryDO> queryNotIncludeUuids(String userId, List<String> list) {
        List<DiaryDO> diaryDOS = diaryDOMapper.queryNotIncludeUuids(userId, list);
        return diaryDOS;
    }

}
