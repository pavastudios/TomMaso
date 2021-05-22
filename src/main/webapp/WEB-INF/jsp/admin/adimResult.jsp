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
    <title>Title</title>
  <%
    List<Utente> u = (List<Utente>) request.getAttribute("result");
  %>


</head>
<body>
  <%if(u.isEmpty()){%>
  <b color="red">Non sono stati trovati utenti</b>
  <%}else{%>

  <% for(Utente x:u){%>
  <div id="template">
    <form method="post" action="${pageContext.request.contextPath}/cambia-ruolo" id="form">
      <p class="nome"><%=x.getUsername()%></p>
      <input type="text" name="nome" class="name" value="<%=x.getUsername()%>"hidden readonly required>
      <%if(x.getIsAdmin()){%>
      <input type="submit" class="send" value="Rimuovi admin" onclick="refresh()">
      <%}else{%>
      <input type="submit" class="send" value="Aggiungi admin" onclick="refresh()">
      <%}%>
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
