<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 05/05/2021
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%
  Utente u=(Utente)request.getAttribute("utente");
%>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<form method="POST" action="/TomMaso_war_exploded/crea-chat">
  <input type="text" name="unto" value="<%=u.getUsername()%>" hidden>
  <input type="submit"  value="<%=u.getUsername()%>">
</form>
</body>
</html>
