<%--
  Created by IntelliJ IDEA.
  User: lvjiaxi
  Date: 9/30/18
  Time: 2:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="/js/jquery-3.1.1.js"></script>
  </head>
  <body>
  <span id="eth"> eth:</span>
  <span id="zil"> zil:</span>
  <script>
      $.ajax({
          type: 'post', //可选get
          url: '/huobi', //这里是接收数据的程序
          dataType: 'json', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
          success: function(data) {
              //这里是ajax提交成功后，程序返回的数据处理函数。data是返回的数据，数据类型在dataType参数里定义！
              $("#eth").append(data.ethToCny+"元");
              $("#zil").append(data.zilToCny+"元");

          },
          error: function() {
              alert('对不起失败了');
          }
      })
  </script>
  </body>
</html>
