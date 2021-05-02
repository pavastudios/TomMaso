<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 02/05/2021
  Time: 17.13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - TomMASO</title>
</head>
<body>
<form action="./login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"/><br/>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password"/><br/>

    <label for="remember">Remember me</label>
    <input type="checkbox" id="remember" name="remember"/><br/>

    <input type="submit" value="Conferma"/>
</form>

</body>
</html>
