package edu.fzu.moodkeeper.controller;

import com.google.gson.Gson;
import edu.fzu.moodkeeper.dataobject.DiaryDO;
import edu.fzu.moodkeeper.response.CommonReturnType;
import edu.fzu.moodkeeper.service.DiaryService;
import edu.fzu.moodkeeper.service.model.DiaryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller("diary")
@RequestMapping("/diary")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class DiaryController extends BaseController {
    private static Logger Log = LoggerFactory.getLogger(DiaryController.class);


    @Autowired
    private DiaryService diaryService;


    @RequestMapping("/list")
    @ResponseBody
    public CommonReturnType list(@RequestParam(name = "userId") String userId, @RequestParam(name = "uuids") String uuids) {
        System.out.println("userId=" + userId + "  ;uuids=" + uuids);

        try {
            List<String> list = Arrays.asList(uuids.split(","));
            List<DiaryDO> diaryDOS = diaryService.queryNotIncludeUuids(userId, list);
            if (diaryDOS == null || diaryDOS.size() <= 0) {
                return CommonReturnType.create("");//无数据
            }
            for (int i = 0; i < diaryDOS.size(); i++) {
                System.out.println(diaryDOS.get(i).toString());
            }

            Gson gson = new Gson();
            return CommonReturnType.create(gson.toJson(diaryDOS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonReturnType.create("");
    }


    @RequestMapping("/inserts")
    @ResponseBody
    public CommonReturnType inserts(@RequestParam(name = "json") String json) {
        Log.info("上传数据");
        boolean result = false;
        try {
            Gson gson = new Gson();
            DiaryModel diaryModel = gson.fromJson(json, DiaryModel.class);
            int size = diaryModel.diary.recordList.size();
            for (int i = 0; i < size; i++) {
                DiaryModel.DiaryDTO.RecordListDTO recordListDTO = diaryModel.diary.recordList.get(i);
                DiaryDO diaryDO = new DiaryDO();
                diaryDO.setDiaryDate(new Date(recordListDTO.diaryDate));
                diaryDO.setDiaryContent(recordListDTO.diaryContent);
                diaryDO.setUserId((long) recordListDTO.userId);
                diaryDO.setAnchor(new Date(recordListDTO.anchor));
                diaryDO.setCategoryId(recordListDTO.categoryId);
                diaryDO.setMoodId(recordListDTO.moodId);
                diaryDO.setWeatherId(recordListDTO.weatherId);
                diaryDO.setDiaryName(recordListDTO.diaryName);
                diaryDO.setState(recordListDTO.state);
                diaryDO.setUuid(recordListDTO.uuid);

                int count = diaryService.queryByUUID(diaryDO);

                if (count == 0) {
                    diaryService.insert(diaryDO);
                    result = true;
                }
            }
            Log.info("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.info("导入异常");
        }
        String data;
        if (result) {
            data = "更新数据完成";
        } else {
            data = "更新数据失败";
        }

        return CommonReturnType.create(data);
    }

}
