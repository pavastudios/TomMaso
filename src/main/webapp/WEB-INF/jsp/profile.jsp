<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 03/05/2021
  Time: 17.41
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@ include file="headTags.jsp" %>
    <% Utente u = (Utente) request.getAttribute("user"); %>
    <% List<Blog> blogs = (List<Blog>) request.getAttribute("blogs"); %>
    <title><%= u.getUsername() %> - Profilo</title>
</head>
<body>

<h1><%= u.getUsername() + "\n" + u.getEmail()%>
</h1>
<img src="<%= u.getPropicURL()%>" alt="No picture."/>
<form action="${pageContext.request.contextPath}/api/blog/create" method="POST">
    <input type="text" name="name"/>
    <input type="submit" value="Crea"/>
</form>
<ul>
    <% for (Blog b : blogs) { %>
    <li><%=b.getNome()%>
    </li>
    <%}%>
</ul>
</body>
</html>
