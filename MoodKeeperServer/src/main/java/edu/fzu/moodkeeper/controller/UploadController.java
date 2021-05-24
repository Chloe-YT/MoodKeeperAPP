package edu.fzu.moodkeeper.controller;

import edu.fzu.moodkeeper.response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//上传文件控制器
//直接上传到服务器
@Controller
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(DiaryController.class);
    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static" + File.separator + "img" + File.separator + "head" + File.separator;

    @PostMapping("/uploadImg")
    @ResponseBody
    public CommonReturnType uploadImg(@RequestParam("uploadFile") MultipartFile uploadFile) {

        ApplicationHome ah = new ApplicationHome(getClass());
        File homeFile = ah.getSource();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            String realPath = new String(homeFile.getAbsolutePath() + "/" + UPLOAD_PATH_PREFIX);
            logger.info("-----------上传文件保存的路径【" + realPath + "】-----------");
            String format = sdf.format(new Date());
            //存放上传文件的文件夹
            File file = new File(realPath + format);
            logger.info("存放上传文件的文件夹【" + file + "】");
            logger.info("绝对路径【" + file.getAbsolutePath() + "】");
            if (!file.isDirectory()) {
                //递归生成文件夹
                file.mkdirs();
            }
            //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                //构建真实的文件路径
                File newFile = new File(file.getAbsolutePath() + File.separator + newName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                uploadFile.transferTo(newFile);
                String path = "img"+newFile.getPath().split("static\\\\img")[1].replaceAll("\\\\","/");
                System.out.println(path);
                return CommonReturnType.create(path);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonReturnType.create("");
    }
}