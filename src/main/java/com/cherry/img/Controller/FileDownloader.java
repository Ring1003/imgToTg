package com.cherry.img.Controller;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
public class FileDownloader {


    public static void main(String[] args) throws IOException {
        String filePath = "C:/Users/Administrator/Desktop/2023大练兵/fileUrl.txt";  // 相对或绝对路径，指向包含附件URL列表的文本文件
        String downloadDir = "C:/Users/Administrator/Desktop/2023大练兵/下载附件url/"; // 文件下载目录
        List<String> urlList = readUrlFile(filePath);

        if (urlList != null && !urlList.isEmpty()) {
            for (String url : urlList) {
                download(url, downloadDir);
            }
        }
    }

    /**
     * 读取包含URL列表的文本文件
     *
     * @param filePath 文件路径
     * @return URL列表
     */
    private static List<String> readUrlFile(String filePath) {
        List<String> urlList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                urlList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlList;
    }

    /**
     * 下载文件到指定目录
     *
     * @param url          文件URL
     * @param downloadDir 文件下载目录
     * @throws IOException IO异常
     */
    private static void download(String url, String downloadDir) throws IOException {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String savePath = downloadDir + fileName;
        System.out.println(savePath);
        URL fileUrl = new URL(url);
        URLConnection conn = fileUrl.openConnection();
        try (InputStream inStream = conn.getInputStream();
             FileOutputStream fos = new FileOutputStream(savePath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            System.err.println("----------------error!");
            System.err.println("----------------error,FileName: "+fileName);
            System.err.println("----------------error,SavePath: "+savePath);
            e.printStackTrace();
        }
    }
}
