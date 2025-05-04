<%--
  Created by IntelliJ IDEA.
  User: pablo
  Date: 04.05.2025
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<h3><a href="index.html">Домой</a></h3>
<hr>
<h2>Meals</h2>
<jsp:useBean id="meals" scope="request" type="java.util.ArrayList"/>
<h3><a href="meals?action=create">Добавить еду</a></h3>
<table>
    <thead>
    <tr class="bold">
        <td>id</td>
        <td>Дата</td>
        <td>Калории</td>
        <td>Описание</td>
        <td>Обновить</td>
        <td>Удалить</td>
    </tr>
    </thead>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${meal.id}</td>
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Обновить</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
