package edu.fzu.moodkeeper.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Log.info("上传数据"+json);
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
                diaryDO.setImagePath(recordListDTO.imagePath);
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

    @RequestMapping("/delete")
    @ResponseBody
    public CommonReturnType delete(@RequestParam(name = "userId") String userId, @RequestParam(name = "uuid") String uuid) {
        System.out.println("userId=" + userId + " ;uuid=" + uuid);
        try {
            diaryService.delete(userId,uuid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CommonReturnType.create("删除完成");
    }

    @RequestMapping("/update")
    @ResponseBody
    public CommonReturnType update(@RequestParam(name = "json") String json) {
        System.out.println("json=>"+json);
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            int diaryId = jsonObject.get("diary_id").getAsInt();
            int userId = jsonObject.get("user_id").getAsInt();
            int moodId = jsonObject.get("mood_id").getAsInt();
            int weatherId = jsonObject.get("weather_id").getAsInt();
            int categoryId = jsonObject.get("category_id").getAsInt();
            int state = jsonObject.get("state").getAsInt();
            String diaryName = jsonObject.get("diary_name").getAsString();
            String diaryContent = jsonObject.get("diary_content").getAsString();
            String diaryDate = jsonObject.get("diary_date").getAsString();//May 12, 2021 12:00:00 AM
            String anchor = jsonObject.get("anchor").getAsString();//
            String uuid = jsonObject.get("uuid").getAsString();
            String imagePath = "";
            if(jsonObject.has("image_path")) {
                imagePath = jsonObject.get("image_path").getAsString();
            }
            Date diary_date = parseDate(diaryDate);
            Date anchorDate = parseDate(anchor);

            System.out.println("userId==" + userId + "uuid=" + uuid);

            DiaryDO diaryDO = diaryService.queryByUUIDAndUserId(uuid);
            diaryDO.setMoodId(moodId);
            diaryDO.setMoodId(weatherId);
            diaryDO.setCategoryId(categoryId);
            diaryDO.setState(state);
            diaryDO.setDiaryName(diaryName);
            diaryDO.setDiaryContent(diaryContent);
            diaryDO.setDiaryDate(diary_date);
            diaryDO.setAnchor(anchorDate);
            diaryDO.setImagePath(imagePath);
            diaryService.updateByUserIdAndUuid(diaryDO);

            System.out.println("解析数据---》" + diaryDO.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonReturnType.create("更新完成");
    }

    private Date parseDate(String str) {
        try {
//            May 12, 2021 00:00:00
            //May 12, 2021 12:00:00 AM
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy hh:mm:ss", new Locale("en"));//May 12, 2021 12:00:00 AM
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

}
