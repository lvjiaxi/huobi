package com.iduoyu.Servlet;

import com.iduoyu.Time.MyTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Timer;


/**
 * 此类用于开启定时采集，设置时间为10秒抓取一次
 */
@WebServlet(name = "OpenServlet",urlPatterns = "/open")
public class OpenServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Timer timer = new Timer();
        timer.schedule(new MyTask(), 1000, 10000);  //1秒后执行，并且每隔10000毫秒重复执行

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        timer.cancel();  //终止计时器，放弃所有已安排的任务
//        timer.purge();  //释放内存
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}


