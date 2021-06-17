<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 21/05/2021
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Admin - TomMASO</title>

  <%@ include file="../general/headTags.jsp" %>

</head>
<body>
<%
  List<Utente> u = (List<Utente>) request.getAttribute("result");
%>

  <%if(u.isEmpty()||u.get(0)==null){%>
  <b color="red">Non sono stati trovati utenti</b>
  <%}else{%>

  <% for(Utente x:u){%>
  <div class="template">
    <form method="post" action="${pageContext.request.contextPath}/cambia-ruolo" class="form">
      <div class="name">
        <p class="nome" ><%=x.getUsername()%></p>
      </div>

      <input type="text" name="nome" class="name" value="<%=x.getUsername()%>"hidden readonly required>
      <div class="send">
      <%if(x.getIsAdmin()){%>
      <input type="submit" class="btn btn-danger" value="Rimuovi admin" onclick="refresh()">
      <%}else{%>
      <input type="submit" class="btn btn-success" value="Aggiungi admin" onclick="refresh()">
      <%}%>
      </div>
    </form>
  </div>
  <%}}%>

  <script>
    function refresh(){
      location.reload();
    }
  </script>
</body>
</html>