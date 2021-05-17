<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 05/05/2021
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<%@ page import="com.pavastudios.TomMaso.model.Chat" %>


<html>
<head>
  <%@ include file="../general/headTags.jsp" %>
  <style>
    #chat>p{
      width:100%;
    }
  </style>
  <%
    Chat chat= (Chat) request.getAttribute("chat");
    Utente altro=chat.otherUser(ses.getUtente());
  %>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
  <title>Title</title>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>
<div id="chat" class="chat">
</div>

<div class="inviomessaggio">
  <input type="text" placeholder="Invia messaggio" id="messaggio">
  <input type="text" name="chat" value="<%=chat.getIdChat()%>" id="chat-id" hidden>
  <button id="invia">Invia</button>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
</body>

<script>
  let lastUpdated = -1;

  function addMessage(mes) {
    const x = document.createElement("p");
    x.innerText=mes["testo"];
    x.id="m-"+mes["id"];
    if(parseInt(mes["mittente"]["id"])==<%=ses.getUtente().getIdUtente()%>){
      x.style="color:red";//stesso utente
    }else{
      x.style="color:blue";//altro utente
    }
    document.getElementById("chat").appendChild(x);
  }

  $(function () {
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/fetch-chat',
      data: {
        "chat-id": $("#chat-id").val()
      }, success: function (data) {
        console.log(data);
        if(data["error"]!==undefined)
          return;
        data=data["response"];
        lastUpdated=data[data.length-1]["id"];
        for(let i=0; i<data.length; i++){
          const mes = data[i];
          if($("#m-"+mes["id"]).length===0){
            addMessage(mes);
          }
        }
      }
    });
  })

  setInterval(function() {
    const time=Date.now();
    //document.getElementById("chat").innerHTML=this.responseText;
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/fetch-from-id',
      data: {
        "chat-id": $("#chat-id").val(),
        "from-id": lastUpdated,
      },
      success: function (data) {
        console.log(data);
        if(data["error"]!==undefined)
          return;
        data=data["response"];
        lastUpdated=data[data.length-1]["id"];
        for(let i=0; i<data.length; i++){
          const mes = data[i];
          if($("#m-"+mes["id"]).length===0){
            addMessage(mes);
          }
        }
      }
    });
  }, 5000); //1 second

  $("#invia").click(function () {
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/send-message',
      data: {
        "chat-id": $("#chat-id").val(),
        "message": $("#messaggio").val(),
      },
      success: function (data) {
        console.log(data);
        if(data["error"]!==undefined)
          return;
        data=data["response"];
        addMessage(data);
      }
    });
  });
</script>
</html>
