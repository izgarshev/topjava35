<%--
  Created by IntelliJ IDEA.
  User: pablo
  Date: 04.05.2025
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal form</title>
</head>
<body>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post">
    <label for="id">Идентификатор</label>
    <input type="text" name="id" id="id" value="${meal.id}" disabled>
    <br>
    <label for="dateTime">Дата</label>
    <input type="datetime-local" name="dateTime" id="dateTime" value="${meal.dateTime}" required>
    <br>
    <label for="calories">Калории</label>
    <input type="number" name="calories" id="calories" min="0" max="5000" value="${meal.calories}" required>
    <br>
    <label for="description">Описание</label>
    <input type="text" name="description" id="description" value="${meal.description}" required>
    <br>
    <button type="submit">Сохранить</button>
    <button type="button" onclick="window.history.back()">Отмена</button>
</form>
</body>
</html>
