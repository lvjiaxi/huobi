package com.iduoyu.Time;

import com.iduoyu.Api.HuobiApi;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTask extends TimerTask {
    //线程开启
    public void run() {

        synchronized (this) {
            //记录开启时间
            Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            System.out.println("当前时间：" + format.format(new Date()));

            //测试线程是否安全  是否有抢线程情况
//            try {
//                Thread.sleep(11000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            //开始写入数据
            double zilusdt = HuobiApi.lastPrice("zilusdt");//获取最新价格
            System.out.println(zilusdt);
        }


    }
}
