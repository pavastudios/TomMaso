<%@ page import="com.pavastudios.TomMaso.utility.RememberMeUtility" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.pavastudios.TomMaso.db.queries.Queries" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    if (session.isNew()) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (RememberMeUtility.COOKIE_REMEMBER_ME.equals(c.getName())) {
                Utente u = Queries.findUserByCookie(c.getValue());
                if (u != null)
                    session.setAttribute(RememberMeUtility.SESSION_USER, u);
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>

<!--
Utente()
Blog()
Pagine()
Commenti()
UtentiBloccati()
-->