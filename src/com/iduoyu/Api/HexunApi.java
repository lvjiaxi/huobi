package com.iduoyu.Api;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HexunApi {

    public static Double usdToCny(){


        String temp = "http://data.bank.hexun.com/other/cms/foreignexchangejson.ashx?callback=ShowDatalist";
        Double usdToCnyPrice=6.8;
        try {
            URL url = new URL(temp);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
            connection.connect();
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "GBK"));

            // 对数据进行访问
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // 关闭流
            bReader.close();
            // 关闭链接
            connection.disconnect();
            String all= String.valueOf(stringBuilder);
            String replace = all.replace("ShowDatalist(", "").replace(")", "");
            JSONArray jsonArray = JSONArray.fromObject(replace);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
                if ("工商银行".equals(jsonObject.getString("bank"))&&"美元".equals(jsonObject.getString("currency"))){
                     usdToCnyPrice = (jsonObject.getDouble("sellPrice1")+jsonObject.getDouble("buyPrice1"))/100/2;
                    return usdToCnyPrice;
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usdToCnyPrice;

    }
}
