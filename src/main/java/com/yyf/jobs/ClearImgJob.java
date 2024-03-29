package com.yyf.jobs;

import com.yyf.constant.RedisConstant;
import com.yyf.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 自定义任务，定时清理垃圾图片
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    //清理图片
    public void clearImg(){
        System.out.println("定时清理垃圾图片");
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.UPLOAD_ZIP_RESOURCES, RedisConstant.UPLOAD_ZIP_DB_RESOURCES);
        if(set != null){
            for (String fileName : set) {
                System.out.println("定时清理垃圾图片： " + fileName);
                //根据图片名称从七牛云服务器删除文件
                QiniuUtils.deleteFileFromQiniu(fileName);
                //从redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.UPLOAD_ZIP_RESOURCES,fileName);
            }
        }
    }
}
