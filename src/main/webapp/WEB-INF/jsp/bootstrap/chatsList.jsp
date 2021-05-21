<%@ page import="com.pavastudios.TomMaso.model.Chat" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 21/05/2021
  Time: 15.27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
      Utente login = ses.getUtente();
      List<Chat> chats = (List<Chat>) request.getAttribute("chats");
    %>
    <title>Lista chat di <%=login.getUsername()%> - TomMASO</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>

<div class="row row-cols-1 row-cols-md-3 g-4">
<% for(Chat c : chats){
    Utente other = c.otherUser(login);
%>
  <div class="col-4">
    <div class="card h-100 w-100">
      <div>
        <%=other.propicHtml(request.getServletContext())%>
      </div>
      <div class="card-body">
          <a href="${pageContext.request.contextPath}/create-chat?id=<%=c.getIdChat()%>"><h5 class="card-title text-center"><%=other.getUsername()%></h5></a>
      </div>
    </div>
  </div>
<%}%>
</div>
<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
</body>
</html>
