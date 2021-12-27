<%--
  Created by IntelliJ IDEA.
  User: anton
  Date: 05/05/2021
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Utente" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Chat" %>


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

  <input type="text" name="chat" value="<%=chat.getIdChat()%>" id="chat-id" hidden>
  <% if(chat.isPartecipant(ses.getUtente())){ %>
  <div class="row justify-content-center mt-5">
    <textarea class="col-12 form-control" placeholder="Invia messaggio" id="messaggio"></textarea>
    <button class="col-12 btn btn-primary" id="invia">Invia <i class="fas fa-paper-plane me-4"></i></button>
  </div>
  <%}%>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

  <!--TEMPLATE MESSAGGIO-->
  <div class="col-12 row px-0 mt-2 msg-card" id="message-template">
    <a class="msg-sender col-2" href="PAGINA_UTENTE">
      <svg id="nav-svg-user" class="w-100 msg-svg rounded-circle" data-jdenticon-value=""></svg>
    </a>
    <div class="card px-0 col-10">
      <div class="card-header">
        <span class="msg-sender-name">NOME_UTENTE</span>
        <div class="float-end">
          <span class="msg-date" style="margin-right: 8px">DATE_MESSAGE</span>
          <%if(chat.isPartecipant(ses.getUtente())){%>
          <button data-bs-toggle="modal" data-bs-target="#reportMessageModal" class="report btn btn-danger" onclick="setMessageId(this)"><i class="fas fa-flag"></i></button>
          <%}%>
        </div>
      </div>
      <div class="card-body">
        CONTENUTO_MESSAGGIO
      </div>
    </div>

  </div>

<!-- report comment Modal -->
<div class="modal modal-fullscreen-md-down fade" id="reportMessageModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Segnala mesasggio:</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p class="lead">Inserisci il motivo:</p>
        <input type="text" name="name" id="id-message" hidden>
        <input type="text" name="name" id="reason" class="input-text" maxlength="50">
      </div>
      <p class="text-danger modal-error"></p>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
        <button type="button" class="btn btn-danger" id="reportCommentModalOk">Segnala</button>
      </div>
    </div>
  </div>
</div>

<script>
  function setMessageId(btn) {
    $("#id-message").val(btn.parentElement.parentElement.parentElement.parentElement.id.split("-")[1]);
  }
  $("#reportCommentModalOk").click(function () {
    let reason=$("#reason").val();
    let idMessage=parseInt($("#id-message").val());
    $.ajax({
      type: 'POST',
      url: '${pageContext.request.contextPath}/api/report/message<%=request.getAttribute("rewrite")%>',
      data: {
        "reason": reason,
        "id-message":idMessage
      },
      success: function (data) {
        if (data["error"] !== undefined){
          showError(data["error"]);
          return;
        }
        if(data["error"]===undefined)
          location.reload();
      }
    });
  });
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
    clon.find(".report").attr("hidden",mes["mittente"]["username"]=="<%=ses.getUtente().getUsername()%>");
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
