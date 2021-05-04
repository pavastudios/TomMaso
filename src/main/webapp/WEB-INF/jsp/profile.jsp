<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 03/05/2021
  Time: 17.41
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Utente u = (Utente) request.getAttribute("user"); %>
<html>
<head>
    <%@ include file="headTags.jsp" %>
    <title><%= u.getUsername() %> - Profilo</title>
</head>
<body>

<h1><%= u.getUsername() + "\n" + u.getEmail()%>
</h1>
<img src="<%= u.getPropicURL()%>" alt="No picture."/>
<form action="/create-blog" method="POST">
    <input type="text" name="blogname"/>
    <input type="submit" value="Crea"/>
</form>
</body>
</html>
