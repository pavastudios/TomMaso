<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 05/05/2021
  Time: 17.42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general/headTags.jsp" %>
    <%
        File[] files = (File[])request.getAttribute("files");
    %>
    <title>Gestione blogs</title>
</head>
<body>
<ul>
<% for (File f: files) {%>
    <li><a href="./<%=f.getName()%>"><%=f.getName()%></a></li><br/>
<%}%>
</ul>
</body>
</html>
