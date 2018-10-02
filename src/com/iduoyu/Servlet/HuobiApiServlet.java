package com.iduoyu.Servlet;

import com.iduoyu.Api.*;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "HuobiApiServlet",urlPatterns = "/huobi")
public class HuobiApiServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double usdToCny = HexunApi.usdToCny();
        double zilusdt = HuobiApi.lastPrice("zilusdt");//获取最新价格
        double ethusdt = HuobiApi.lastPrice("ethusdt");//获取最新价格
        double zilToCny = zilusdt * usdToCny;
        double ethToCny = ethusdt * usdToCny;
        String price="{\""+"zilToCny"+"\":"+zilToCny+","+"\""+"ethToCny"+"\":"+ethToCny+"}";
        PrintWriter writer = response.getWriter();
        writer.print(price);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
