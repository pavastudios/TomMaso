<%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 04/05/21
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.util.*" %>
<% List<Utente> lista = (List<Utente>) request.getAttribute("listaContattati"); %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="/genera-chat">
<% for(Utente u:lista){%>
    <input type="text" name="unto" value="<%=u.getUsername()%>" hidden>
    <input type="submit"  value="<%=u.getUsername()%>">
<% } %>
</form>
</body>
</html>
