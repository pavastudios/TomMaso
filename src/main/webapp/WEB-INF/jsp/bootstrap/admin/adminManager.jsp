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
  <%@ include file="../general/headTags.jsp" %>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
  <title>Admin - TomMASO</title>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>
<div>
  <div class="row justify-content-center">

    <div id="ricerca" class="container-ricerca col-12 col-md-5">
      <b>Ricerca:</b>
      <div class="input row">
        <div class="col-7">
          <input type="text" id="nomee" class="form-control">
        </div>
        <div class="col-5">
          <button id="cerca"  onclick="greve()" class="btn btn-dark">Cerca</button>
        </div>

      </div>

      <div id="output">

      </div>
    </div>

    <div class="container-ricerca col-12 col-md-5">
      <b>Admins:</b>
      <div id="lista">

      </div>

    </div>

  </div>

</div>


<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

<script>

   function greve(){
   $.ajax({
     type: 'POST',
     url: '${pageContext.request.contextPath}/cerca-admin<%=request.getAttribute("rewrite")%>',
      data: {
        "nome": $("#nomee").val(),
        "admin":"false",
      },error: function (){
       showError("Utente non trovato");
     },
     success: function (data) {
        $('#output').html(data)
      }
    });
    }

   $(document).ready(function (){
     $.ajax({
       type: 'POST',
       url: '${pageContext.request.contextPath}/cerca-admin<%=request.getAttribute("rewrite")%>',
       data: {
         "nome": null,
         "admin":"true",
       },
       error: function (){
         showError("Impossibile trovare l'account");
       },
       success: function (data) {


         $('#lista').html(data)
       }
     });
   });
</script>


</body>
</html>
