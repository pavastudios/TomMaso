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
  Utente loggato = (Utente) request.getAttribute("loggato");
  Utente altro = (Utente) request.getAttribute("altro");
%>

<html>
<head>
  <%@ include file="../general/headTags.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
  <title>Title</title>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>
<div id="chat" class="chat">
</div>

<div class="inviomessaggio">
  <input type="text" placeholder="Invia messaggio" id="messaggio">
  <input type="text" name="loggato" value="<%=loggato.getUsername()%>" id="loggato" hidden required readonly>
  <input type="text" name="altro" value="<%=altro.getUsername()%>" id="altro" hidden required readonly>
  <button id="invia">Invia</button>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
</body>

<script>

  setInterval(function() {
    var richiesta = new XMLHttpRequest();

    richiesta.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("chat").innerHTML=this.responseText;
      }
    };
    richiesta.open("get","/TomMaso_war_exploded/genera-chat?unto="+document.getElementById("altro").value);
    richiesta.send();
  }, 1000); //1 second

  var invia=document.getElementById("invia");

  invia.addEventListener("click", function (){
    var richiesta = new XMLHttpRequest();

    richiesta.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("messaggio").innerHTML="";
      }
    };
    richiesta.open("get","/TomMaso_war_exploded/invia-messaggio?" +
            "loggato="+document.getElementById("loggato").value+
            "&altro="+document.getElementById("altro").value+
            "&mex="+document.getElementById("messaggio").value
    );
    richiesta.send();
  });
</script>
</html>
