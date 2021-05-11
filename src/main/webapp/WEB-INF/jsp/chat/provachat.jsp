<%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 04/05/21
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.util.*" %>
<% List<Utente> lista = (List<Utente>) request.getAttribute("listaContattati"); %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<div   class="scritti">
    <form method="POST" action="/TomMaso_war_exploded/crea-chat">
    <% for(Utente u:lista){%>
        <input type="text" name="unto" value="<%=u.getUsername()%>" hidden readonly required>
        <input type="submit"  value="<%=u.getUsername()%>">
    <% } %>
    </form>
</div>

<div id="ricerca"  class="ricerca">
    <div id="input">
        <input type="text" name="nickname" placeholder="Nome utente" id="nome">
        <button id="cerca">Cerca</button>
    </div>
    <div id="elenco">

    </div>
</div>
<script>

    var button = document.getElementById("cerca");

    button.addEventListener("click",function (){
        var richiesta = new XMLHttpRequest();
        var name= document.getElementById("nome");

        richiesta.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("elenco").innerHTML =
                    this.responseText;
            }
        };

        richiesta.open("get","/TomMaso_war_exploded/cerca-utenti?nome="+name.value);
        richiesta.send();
    });
</script>
</body>
</html>
