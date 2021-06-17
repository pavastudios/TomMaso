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
  <%
    Chat chat= (Chat) request.getAttribute("chat");
    Utente altro=chat.otherUser(ses.getUtente());
  %>
  <title>Chat con <%=altro.getUsername()%> - TomMASO</title>
</head>
<body>

<%@ include file="../general/navbar.jsp" %>

<div class="container main-container">

    <div id="chat" class="row chat">
    </div>

  <div class="row justify-content-center mt-5">
    <input type="text" name="chat" value="<%=chat.getIdChat()%>" id="chat-id" hidden>

    <textarea class="col-12 form-control" placeholder="Invia messaggio" id="messaggio"></textarea>
    <button class="col-12 btn btn-primary" id="invia">Invia <i class="fas fa-paper-plane me-4"></i></button>
  </div>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

  <!--TEMPLATE MESSAGGIO-->
  <div class="col-12 row px-0 mt-2 msg-card" id="message-template">
    <a class="msg-sender col-2" href="PAGINA_UTENTE">
      <img id="nav-user" class="w-100 msg-propic rounded-circle" src="">
      <svg id="nav-svg-user" class="w-100 msg-svg rounded-circle" data-jdenticon-value="" hidden></svg>
    </a>
    <div class="card px-0 col-10">
      <div class="card-header">
        <span class="msg-sender-name">NOME_UTENTE</span>
        <span class="msg-date float-end">DATE_MESSAGE</span>
      </div>
      <div class="card-body">
        CONTENUTO_MESSAGGIO
      </div>
    </div>

  </div>
<script>
  let lastUpdated = -1;
  const messageTemplate=$("#message-template");
  messageTemplate.hide();
  function addMessage(mes) {
    const other="<%=altro.getUsername()%>";
    const clon = messageTemplate.clone();
    const data=new Date(mes["data_invio"]);
    clon.attr("id","m-"+mes["id"]);
    clon.find(".msg-sender").attr("href","${pageContext.request.contextPath}/user/"+mes["mittente"]["username"]);
    clon.find(".msg-propic").attr("id","nav-msg-"+mes["id"]);
    clon.find(".msg-svg"   ).attr("id","nav-svg-msg-"+mes["id"]);
    clon.find(".msg-svg"   ).attr("data-jdenticon-value",mes["mittente"]["username"]);
    clon.find(".msg-sender-name").text(mes["mittente"]["username"]);
    clon.find(".msg-date").text(data.toLocaleString("it-IT",{timeZone:'Europe/Rome'}));
    clon.find(".card-body").text(mes["testo"]);
    clon.find(".msg-propic").attr("src","${pageContext.request.contextPath}/users/"+mes["mittente"]["username"]+"/propic.png");
    clon.find(".msg-propic").attr("onerror","useJidenticonNav('msg-"+mes["id"]+"')");
    clon.show();
    if(mes["mittente"]["username"]===other) {
      clon.find(".card").after(clon.find(".msg-sender").first());
      //clon.find(".card").before(clon.find(".xxx").first());
    }
    clon.appendTo($("#chat"));
  }

  $(function () {
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/fetch-chat<%=request.getAttribute("rewrite")%>',
      data: {
        "chat-id": $("#chat-id").val()
      },
      success: function (data) {
        if (data["error"] !== undefined){
          showError(data["error"]);
          return;
        }
        data=data["response"];
        lastUpdated=data[data.length-1]["id"];
        for(let i=0; i<data.length; i++){
          const mes = data[i];
          if($("#m-"+mes["id"]).length===0){
            addMessage(mes);
          }
        }
        jdenticon();
      }
    });
  })

  setInterval(function() {
    //document.getElementById("chat").innerHTML=this.responseText;
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/fetch-from-id<%=request.getAttribute("rewrite")%>',
      data: {
        "chat-id": $("#chat-id").val(),
        "from-id": lastUpdated,
      },
      success: function (data) {
        if (data["error"] !== undefined){
          showError(data["error"]);
          return;
        }
        data=data["response"];
        if(data.length==0)return;
        lastUpdated=data[data.length-1]["id"];
        for(let i=0; i<data.length; i++){
          const mes = data[i];
          if($("#m-"+mes["id"]).length===0){
            addMessage(mes);
          }
        }
        jdenticon();
      }
    });
  }, 5000); //1 second

  $("#invia").click(function () {
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/chat/send-message<%=request.getAttribute("rewrite")%>',
      data: {
        "chat-id": $("#chat-id").val(),
        "message": $("#messaggio").val(),
      },
      success: function (data) {
        if (data["error"] !== undefined){
          showError(data["error"]);
          return;
        }
        data=data["response"];
        addMessage(data);
        jdenticon();
      }
    });
  });
</script>
</body>
</html>
