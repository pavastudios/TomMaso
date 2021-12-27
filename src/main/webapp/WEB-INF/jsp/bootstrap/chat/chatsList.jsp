<%@ page import="com.pavastudios.TomMaso.storage.model.Chat" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Utente" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 21/05/2021
  Time: 15.27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../general/headTags.jsp"%>
    <%
      Utente login = ses.getUtente();
      List<Chat> chats = (List<Chat>) request.getAttribute("chats");
    %>
    <title>Lista chat di <%=login.getUsername()%> - TomMASO</title>
</head>
<body>
<%@include file="../general/navbar.jsp"%>
<div class="text-center"><h2 class="mt-3">Lista chat attive</h2></div>
<div class="container main-container">
    <div class="row">
<% for(Chat c : chats){
    Utente other = c.otherUser(login);
%>
  <div class="col-lg-3 col-md-6 col-sm-12 mt-4">
    <div class="card cursore" href="${pageContext.request.contextPath}/chat/<%=c.otherUser(login).getUsername()%>">
      <div class="card-header px-5">
          <svg class="w-100 propic rounded-circle" data-jdenticon-value="<%=other.getUsername()%>" ></svg>
      </div>
      <div class="card-body">
          <h5 class="card-title text-center"><%=other.getUsername()%></h5>
      </div>
    </div>
  </div>
<%}%>
</div>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
<script>
    $(".card").click(function (){
        location.href= $(this).attr("href");
    });
</script>
</body>
</html>
