<%--
  Created by IntelliJ IDEA.
  User: lin zhen
  Date: 2022/10/5
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="./box.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.12.1/css/all.css">
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
    </style>
</head>
<body>
<form method="post" action="/untitled_lib/untitled_lib/web/ServletInsert">
    <table class="table table-hover">
        <tr>
            <td>
                ISBN:<input type="text" name="isbn">
            </td>
            <input type="checkbox" id="check">
            <label for="check">
                <i class="fas fa-bars" id="btn"></i>
                <i class="fas fa-times" id="cancel"></i>
            </label>
            <div class="sidebar">
                <header>跳转</header>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/root.jsp"><i class="fas fa-qrcode"></i>书籍加入</a></li>
                    <li><a href="${pageContext.request.contextPath}/Servletbooks"><i class="fas fa-link"></i>书籍删除</a></li>
                </ul>
            </div>
            <section></section>
        </tr>
        <tr>
            <td>
                NAME:<input type="text" name="name">
            </td>
        </tr>
        <tr>
            <td>
                WRITER:<input name="writer" type="text">
            </td>
        </tr>
        <tr>
            <td>
                COUNTRY:<input type="text" name="country">
            </td>
        </tr>
        <tr>
            <td>
                LOCATION:<input name="location" type="text">
            </td>
        </tr>
        <tr>
            <td>
                STATE:<input name="state" type="text" maxlength="1">
            </td>
        </tr>
        <tr>
            <td>
                TYPE1:<input name="type1" type="text">
            </td>
        </tr>
        <tr>
            <td>
                TYPE2:<input type="text" name="type2">
            </td>
        </tr>
        <tr>
            <td>
                TYPE3:<input name="type3" type="text">
            </td>
        </tr>

    </table>
    <input type="submit">
</form>
</body>
</html>
