<%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 04/05/21
  Time: 16:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pavastudios.TomMaso.model.Messaggio" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="com.pavastudios.TomMaso.db.queries.Entities" %>
<%
    List<Messaggio> mess = (List<Messaggio>) request.getAttribute("messaggi");
    Utente loggato = (Utente) request.getAttribute("loggato");
    Utente altro = (Utente) request.getAttribute("altro");
%>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<div id="chat">
    <% for (Messaggio m : mess) {%>
    <% if (m.getMittente().equals(loggato)) {%>
    <p style="background-color: aqua" align="right"><%=org.jsoup.nodes.Entities.escape(m.getTesto())%>
    </p>
    <% } else { %>
    <p style="background-color: darksalmon" align="left"><%=org.jsoup.nodes.Entities.escape(m.getTesto())%>
    </p>
    <% }
    } %>
</div>
</body>
</html>
