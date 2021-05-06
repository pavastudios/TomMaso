<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 05/05/2021
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pavastudios.TomMaso.model.Messaggio" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%
  List<Messaggio> mess = (List<Messaggio>) request.getAttribute("messaggi");
  Utente loggato = (Utente) request.getAttribute("loggato");
  Utente altro = (Utente) request.getAttribute("altro");
%>

<html>
<head>
  <title>Title</title>
</head>
<body>

<div id="chat">
</div>

<form id="mesage" method="POST" action="${pageContext.request.contextPath}/invia-messaggio">
  <input type="text" placeholder="Invia messaggio" name="messaggio">
  <input type="text" name="loggato" value="<%=loggato.getUsername()%>" hidden required readonly>
  <input type="text" name="altro" value="<%=altro.getUsername()%>" hidden required readonly>
  <input type="submit" value="Invia" id="invia">
</form>
</body>

<script>

  var button = document.getElementById("invia");

  button.addEventListener("click",function (){
    var richiesta = new XMLHttpRequest();
    var mex= document.getElementById("messaggio");

    richiesta.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("chat").innerHTML=this.responseText;
      }
    };
    richiesta.open("get","/TomMaso_war_exploded/genera-chat?unto="+document.getElementById("altro").value);
    richiesta.send();
  });
</script>
</html>
