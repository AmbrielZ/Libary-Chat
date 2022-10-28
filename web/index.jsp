<%--
  Created by IntelliJ IDEA.
  User: lin zhen
  Date: 2022/10/1
  Time: 13:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <link rel="stylesheet" href="boot/css/bootstrap.min.css">
  <style>
    body {
      background-image: url(https://img.zcool.cn/community/017d0355c1ed0e6ac7253f36abb243.jpg@1280w_1l_2o_100sh.jpg);
      background-repeat: no-repeat;
      /* 当内容高度大于图片高度时，背景图像的位置相对于viewport固定 */
      background-attachment: fixed; /*此条属性必须设置否则可能无效*/
      /* 让背景图基于容器大小伸缩 */
      background-size: cover;
      /* 设置背景颜色，背景图加载过程中会显示背景色 */
      background-color: #CCCCCC;
      color: black;
    }
    #content {
      position: absolute;
      top: 50%; /*顶部到元素*/
      left: 30%;
      width: 40%;
      height: 400px;
      margin-top: -200px; /*边缘到底部*/
      background-color: #34495e;
      text-align-last: center;
      align-items: center;
    }
  </style>
</head>
<body>
<div id="content">
  <button type="button" class="btn btn-primary btn-lg active" onclick="window.location.href='http://localhost:8080/untitled_lib/untitled_lib/web/login.jsp'">登录</button><br><br>
  <button type="button" class="btn btn-default btn-lg active" onclick="window.location.href='http://192.168.226.128:8080/untitled_lib/untitled_lib/web/register.jsp'">注册</button>
</div>

</body>
</html>
