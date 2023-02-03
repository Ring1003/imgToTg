# imgToTg

Java后端

![](https://i3.wp.com/telegra.ph/file/c1da2a44a677a876b04d4.png)

图片存储于 [telegra.ph](https://telegra.ph) 由 [wordpress](https://wordpress.com) 提供大陆加速访问 

修改api文件，并配置nginx代理，可实现由自己的域名访问并加速图片


### 20230203 更新
1,添加Redis配置

2,计算图片md5值,存入redis <MD5,url> , 相同图片多次上传会直接从redis取出url并返回





感谢大佬 [missuo](https://github.com/missuo/Telegraph-Image-Hosting) 和 [yumusb](https://github.com/yumusb/autoPicCdn) 的代码参考



