<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="com.pavastudios.TomMaso.model.Commento" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.utility.Utility" %>
<%@ page import="com.pavastudios.TomMaso.utility.FileUtility" %>
<%@ page import="org.jsoup.nodes.Entities" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 17/05/2021
  Time: 17.33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
        File file = (File) request.getAttribute("file");
        List<Commento> comments = (List<Commento>) request.getAttribute("comments");
        String content = FileUtility.escapeMDFile(file);
    %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comments.css">
    <title><%=file.getName()%></title>
</head>
<body>
<%@include file="general/navbar.jsp"%>
<div id="content" class="container main-container">
</div>

<h3 class="text-center">Area commenti</h3>
<div class="container mt-5">
    <% for(Commento commento:comments){ %>
    <div class="row mt-2">
        <a class="col-2" href="${pageContext.request.contextPath}/user/<%=commento.getMittente().getUsername()%>">
            <%=commento.getMittente().propicHtml(request.getServletContext())%>
        </a>
        <div class="card px-0 col-10">
            <div class="card-header">
                <span><%=commento.getMittente().getUsername()%></span>
                <span class="float-end"><%=Utility.DATE_FORMAT.format(commento.getDataInvio())%></span>
            </div>
            <div class="card-body">
                <%=Entities.escape(commento.getTesto())%>
            </div>
        </div>
    </div>
    <%}%>
    <% if(ses.getUtente()!=null){%>
    <!-- Comment Area -->
    <div class="row mt-5">
        <textarea class="col-12" name="commento" id="comment" cols="30" rows="5"></textarea>
        <button class="btn btn-primary" id="sendComment">Invia commento</button>
    </div>
    <%}%>
</div>

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>

<script>
    $("#sendComment").click(function () {
        const comment=$("#comment").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/comment/send-comment<%=request.getAttribute("rewrite")%>',
            data: {
                "comment": comment,
                "page":window.location.pathname
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
                if (data["error"] !== undefined){
                    $(".modal-error").show();
                    $(".modal-error").text(data["error"]);
                }
            }
        });
    });
</script>





<script>
    let cont = marked("<%=content%>");
    cont = DOMPurify.sanitize(cont)
    document.getElementById('content').innerHTML = cont;
</script>
</body>
</html>
