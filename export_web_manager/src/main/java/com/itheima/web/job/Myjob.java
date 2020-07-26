package com.itheima.web.job;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fanbo
 * @date 2020/7/26 19:54
 */
public class Myjob {

    public void excute(){
        //业务逻辑
        System.out.println("===================="+
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
