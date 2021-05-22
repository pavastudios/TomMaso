<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 20/05/2021
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pavastudios.TomMaso.model.Chat" %>
<%
  List<Utente> lista = (List<Utente>) request.getAttribute("listaContattati");
  List<Chat> chats = (List<Chat>) request.getAttribute("listaChat");
%>
<html>
<head>
  <%@ include file="../bootstrap/general/headTags.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
  <title>Title</title>
</head>
<body>
<%@ include file="../bootstrap/general/navbar.jsp" %>





<div class="container">
  <div class="row justify-content-center">

    <div id="ricerca" class="ricerca col-12 col-md-6">
      <div class="input">
        <input type="text" id="nomee">
        <button id="cerca"  onclick="greve()">Cerca</button>
      </div>

      <div id="output">

      </div>
    </div>

    <div id="lista"  class="lista col-12 col-md-6">

    </div>

  </div>

</div>

<script>

   function greve(){
   $.ajax({
     type: 'POST',
     url: '${pageContext.request.contextPath}/cerca-admin<%=request.getAttribute("rewrite")%>',
      data: {
        "nome": $("#nomee").val(),
        "admin":"false",
      },
      success: function (data) {
        console.log(data);
        if(data["error"]!==undefined)
          return;

        $('#output').html(data)
      }
    });
    };

   setInterval(function() {
     const time=Date.now();
     $.ajax({
       type: 'POST',
       url: '${pageContext.request.contextPath}/cerca-admin<%=request.getAttribute("rewrite")%>',
       data: {
         "nome": null,
         "admin":"true",
       },
       success: function (data) {
         console.log(data);
         if(data["error"]!==undefined)
           return;

         $('#lista').html(data)
       }
     });
   }, 2000); //5 second
</script>

<%@include file="../bootstrap/general/footer.jsp"%>
<%@include file="../bootstrap/general/tailTag.jsp"%>

</body>
</html>
