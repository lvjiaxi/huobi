package com.iduoyu.Api;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 记录日开盘和收盘价格，24小时成交量，成交笔数，计算当日涨幅
 */
public class HuobiMarketTickers {
    @Test
    public void huobiMarketTickers(){
        String temp = "https://api.huobi.pro/market/tickers";//采集火币接口、
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
            JSONArray data = (JSONArray) jsonObject.get("data");
            String regs="[a-z][a-z][a-z]usdt";
            Pattern compile = Pattern.compile(regs);
            for (int i = 0; i < data.size(); i++) {

                String symbol = data.getJSONObject(i).getString("symbol");
                Matcher m = compile.matcher(symbol);//进行匹配
                if (m.find()){  //查找如果存在就是true，不存在就是false，下面可以进行数据操作
                    String coin = data.getJSONObject(i).getString("symbol");//货币对名称-------入库
                    double open = data.getJSONObject(i).getDouble("open");//日开盘价
                    double close = data.getJSONObject(i).getDouble("close");//日收盘价
                    String vol = data.getJSONObject(i).getString("vol");//24小时成交额-------入库
                    String amount = data.getJSONObject(i).getString("amount");//24小时成交量-------入库
                    double coinpriceclose = HuobiApi.lastPrice(coin);//货币最新报价-------入库
                    double change= (close-open)/open*100; //涨幅率 百分之
                    change = (double)Math.round(change*100)/100;//涨幅率 百分之取小数点两位-------入库
                    int exchange=1; //交易所为huobi-------入库


                }

            }
//            String data1 = data.getString("data").replace("[", "").replace("]", "");
//            JSONObject newInfo = JSONObject.fromObject(data1);



        }  catch (IOException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
