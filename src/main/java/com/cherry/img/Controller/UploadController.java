package com.cherry.img.Controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.cherry.img.pojo.imgInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 /**
 　* 图片上传
 　* @author Meng
 　* @date 2023-01-18 00:01:19
 　*/
@RestController
@RequestMapping("/file")
public class UploadController {

     /**
     　* 单图片上传
     　* @author Meng
     　* @date 2023-01-18 00:01:59
     　*/
    @RequestMapping("upload")
    public Object uploadImgFile(MultipartFile file, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        Map<Object, Object> dataMap = new HashMap<>();
        String imgUrl = "";

        //MultipartFile转File
        File image = transferToFile(file);
        //telegra上传地址
        String tgUrl = "https://telegra.ph/upload";
        Map<String, Object> map = new HashMap<>();
        map.put("file",image);
        //上传图片并拿到图片地址
        String result = HttpUtil.post(tgUrl, map);
        //上传成功后
        if (result.startsWith("[") && result.endsWith("]")){
            JSONArray jsonArray = JSONUtil.parseArray(result);
            List<imgInfo> imgInfos = JSONUtil.toList(jsonArray, imgInfo.class);
            imgUrl = imgInfos.get(0).getSrc();
            dataMap.put("url","https://i3.wp.com/telegra.ph/"+imgUrl);
            dataMap.put("status","success");
        }else {
            resultMap.put("code","上传失败");
            resultMap.put("msg",result);

            return resultMap;
        }
        resultMap.put("data",dataMap);
        resultMap.put("code","success");
        return resultMap;
    }


    public File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            file=File.createTempFile(filename[0], filename[1] + ".");
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



}
