package com.iduoyu.Api;

import net.sf.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HuobiApi {

    public static double lastPrice(String cointoWhat){

        String temp = "https://api.huobi.pro/market/trade?symbol="+cointoWhat+"&AccessKeyId=fff-xxx-ssss-kkk";

        double price=0;

        try {

//            trustEveryone();
            // 创建URL对象
            // 1.URL类封装了大量复杂的实现细节，这里将一个字符串构造成一个URL对象
            URL url = new URL(temp);
            // 2.获取HttpURRLConnection对象
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");

            // 3.调用connect法连接远程资源
           connection.connect();
            // 4.访问资源数据，使用getInputStream方法获取一个输入流用以读取信息
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

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
            // 打印获取的结果
            String api= String.valueOf(stringBuilder);
            String reg="\"status\":\"ok\",\"ch\":\".*\",\"ts\":(\\d+),";
            String s = api.replaceAll(reg, "");
            JSONObject jsonObject = JSONObject.fromObject(s);
            JSONObject data = (JSONObject) jsonObject.get("tick");
            String data1 = data.getString("data").replace("[", "").replace("]", "");
            JSONObject newInfo = JSONObject.fromObject(data1);
            price = Double.parseDouble(newInfo.getString("price"));



        }  catch (IOException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    public static void trustEveryone() {   //https 网址安全认证访问
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
