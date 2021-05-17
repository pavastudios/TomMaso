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
<%@ page import="com.pavastudios.TomMaso.model.Chat" %>
<%
    List<Utente> lista = (List<Utente>) request.getAttribute("listaContattati");
    List<Chat> chats = (List<Chat>) request.getAttribute("listaChat");
%>
<html>
<head>
    <%@ include file="../general/headTags.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tempchat.css" type="text/css"/>
    <title>Title</title>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>
<div   class="scritti">
    <% for(Chat chat:chats){
        Utente other=chat.otherUser(ses.getUtente());
    %>
        <a href="${pageContext.request.contextPath}/crea-chat?id=<%=chat.getIdChat()%>">
            <button><%=other.getUsername()%></button>
        </a>
    <% } %>
</div>

<div id="ricerca"  class="ricerca">
    <div id="input">
        <input type="text" name="nickname" placeholder="Nome utente" id="nome">
        <button id="cerca">Cerca</button>
    </div>
    <div id="elenco">
        <button id="found-username"></button>
    </div>
</div>
<script>

    const foundBtn = $("#found-username");
    $(function () {
        foundBtn.hide();
    });
    $("#cerca").click(function(){
        foundBtn.hide();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/user/find-user',
            data: {
                "username": $("#nome").val(),
            },
            success: function (data) {
                console.log(data);
                if(data["error"]!==undefined)
                    return;
                foundBtn.text(data["response"]["username"]);
                foundBtn.show();
            }
        });
    });
    foundBtn.click(function () {
        foundBtn.hide();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/chat/create-chat',
            data: {
                "with": foundBtn.text(),
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
</script>

<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

</body>
</html>
