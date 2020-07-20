package com.itheima.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.*;


/**
 * @author fanbo
 * @date 2020/7/18 10:31
 */
public class UploadUtil {
    private static final String accessKey = "OGsWj1B9rUm8TGeB5lInAATO229jELlranGs1TOl";
    private static final String secretKey = "oEx2JP8SmSg9IJDce23BZgfcSJBLE113Ylm9hJSA";
    private static final String bucket = "fan196995";
    private static final String url = "http://qdp50of5b.bkt.clouddn.com/";

    public String upload(byte[] uploadBytes) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
/*        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";*/
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
//        byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
/*                System.out.println(putRet.key);
                System.out.println(putRet.hash);*/
                key = putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        return url + key;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\develop\\mn4.jpg");
        FileInputStream input = new FileInputStream(file);
        byte[] byt = new byte[input.available()];
        input.read(byt);
        System.out.println(new UploadUtil().upload(byt));
    }
}



