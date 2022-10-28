<%@ page import="book.book" %><%--
  Created by IntelliJ IDEA.
  User: lin zhen
  Date: 2022/10/1
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        .table1 {
            align-items: center;
            align-self: center;
        }

        td {
            border: 1px solid black;
        }
    </style>

</head>
<body>
<table class="table table-hover">
    <tr>
        <td colspan="10">
            <form method="post" action="/untitled_lib/untitled_lib/web/ServletSearcbDelete">
                <div class="checkbox" id="main">
                    <label>
                        <input type="text" id="ID10" name="ID11">
                    </label>


                    <label>
                        <input name="uconti" type="radio" id="uconti" value="0" checked/>Writer
                    </label>
                    <label>
                        <input name="uconti" type="radio" id="uconti1" value="1"/>ISBN
                    </label>
                    <label>
                        <input name="uconti" type="radio" id="uconti2" value="2"/>NAME
                    </label>
                    <input type='submit' name='submit10' class='btn btn-default' id="submit10" style="visibility: hidden">
                </div>
            </form>
            <input type="checkbox" id="check">
            <label for="check">
                <i class="fas fa-bars" id="btn"></i>
                <i class="fas fa-times" id="cancel"></i>
            </label>
            <div class="sidebar">
                <header>X</header>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/root.jsp"><i class="fas fa-qrcode"></i>书籍加入</a></li>
                    <li><a href="${pageContext.request.contextPath}/Servletbooks"><i class="fas fa-link"></i>书籍删除</a></li>
                </ul>
            </div>
            <section></section>
        </td>
    </tr>
    <tr>
        <td>ID</td>
        <td>ISBN</td>
        <td>NAME</td>
        <td>WRITER</td>
        <td>COUNTRY</td>
        <td>LOCATION</td>
        <td>STATE</td>
        <td>TYPE1</td>
        <td>TYPE2</td>
        <td>TYPE3</td>
    </tr>
    <c:forEach var="item" items="${requestScope.lisearch}">
        <tr>
            <td><form action="/untitled_lib/untitled_lib/web/ServletDeletebook">
                <p>
                    <input name="deleteid" value="${item.id}" readonly>
                    <input type="submit" value="Delete" name="bookid${item.id}">
                </p>
            </form></td>
            <td>${item.isbn}</td>
            <td>${item.name}</td>
            <td>${item.writer}</td>
            <td>${item.country}</td>
            <td>${item.location}</td>
            <td>${item.state}</td>
            <td>${item.type_1}</td>
            <td>${item.type_2}</td>
            <td>${item.type_3}</td>
        </tr>
    </c:forEach>
</table>
<script>
    var input = document.getElementById('ID10');
    var pl=document.getElementById("submit10");
    function f(){
        input.oninput=function (){
            if(input.value.length!==0){
                pl.style.visibility='visible';
            }else{
                pl.style.visibility='hidden';
            }
        }
    }
    f()
</script>
</body>
</html>
