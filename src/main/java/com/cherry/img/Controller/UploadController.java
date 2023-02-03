package com.cherry.img.Controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.cherry.img.pojo.imgInfo;
import com.cherry.img.utils.redis.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 　* 图片上传
 　* @author Meng
 　* @date 2023-01-18 00:01:19
 　*/
@RestController
@RequestMapping("/file")
public class UploadController {
    /** Redis工具类 */
    @Autowired
    RedisUtils redisUtils;


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
        String md5 = "";
        //随机数123,用于拼接代理网址前缀
        int anInt = new Random().nextInt(3)+1;
        String baseUrl = "https://i"+anInt+".wp.com/telegra.ph/";
        try {
            //计算md5
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            md5 = Base64.getEncoder().encodeToString(messageDigest.digest(file.getBytes()));
            System.err.println("输出md5值: "+md5);
            String md5_url = redisUtils.get(md5);
            System.out.println("redis_get:"+md5_url);
            if (StringUtils.isNotEmpty(md5_url)){
                System.out.println("--------文件已存在,直接返回:"+md5_url);
                dataMap.put("url",baseUrl+md5_url);
                dataMap.put("status","success");
                resultMap.put("data",dataMap);
                resultMap.put("code","success");
                return resultMap;
            }

        }catch (Exception e){

        }

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
            dataMap.put("url",baseUrl+imgUrl);
            dataMap.put("status","success");
            redisUtils.set(md5,imgUrl);
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







     @RequestMapping("/setRedis")
     public String setRedis(){
         String ces = redisUtils.set("aacs:111:asd", "测试分级");

         return ces;
     }

     @RequestMapping("/getRedis")
     public String getRedis(){
         String ces = redisUtils.get("aacs:111:asd");

         return ces;
     }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(new Random().nextInt(3)+1);
        }
    }

}
