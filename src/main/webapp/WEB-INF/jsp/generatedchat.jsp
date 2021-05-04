<%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 04/05/21
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Chat" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="com.pavastudios.TomMaso.db.queries.Queries" %>
<%@ page import="com.pavastudios.TomMaso.utility.RememberMeUtility" %>
<%@ page import="java.sql.SQLException" %>
<%
    Chat c= (Chat) request.getAttribute("chat");
%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="chat">
    <% for(Utente u: lista) %>

</div>
</body>

<script>

</script>
</html>
