<!DOCTYPE html>
<html>
<head>
    <%@ include file="headTags.jsp"%>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/sign-up" method="POST">

    <label for="mail">Email:</label>
    <input type="email" id="mail" name="mail"/><br/>

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
