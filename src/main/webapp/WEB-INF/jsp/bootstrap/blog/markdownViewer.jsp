<%@ page import="java.io.File" %>
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
    <%@include file="../general/headTags.jsp"%>
    <%
        File file = (File) request.getAttribute("file");
        List<Commento> comments = (List<Commento>) request.getAttribute("comments");
        String content = FileUtility.escapeMDFile(file);
    %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comments.css">
    <title><%=file.getName()%></title>

</head>
<body>
<%@include file="../general/navbar.jsp"%>
<div id="content" class="container main-container">
</div>

<h3 class="text-center">Area commenti</h3>
<div class="container mt-5">
    <% for(Commento commento:comments){ %>
    <div class="row mt-2" id="comment-<%=commento.getIdCommento()%>">
        <a class="col-2" href="${pageContext.request.contextPath}/user/<%=commento.getMittente().getUsername()%>">
            <%=commento.getMittente().propicHtml(request.getServletContext())%>
        </a>
        <div class="card px-0 col-10">
            <div class="card-header">
                <span><%=commento.getMittente().getUsername()%></span>
                <div class="float-end">
                    <span style="margin-right: 8px"><%=Utility.DATE_FORMAT.format(commento.getDataInvio())%></span>
                    <button data-bs-toggle="modal" data-bs-target="#reportCommentModal" class="report-comment btn btn-danger"><i class="fas fa-flag"></i></button>
                </div>
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
        <textarea class="col-12 form-control" name="commento" id="comment" cols="30" rows="5"></textarea>
        <button class="btn btn-primary" id="sendComment">Invia commento</button>
    </div>
    <%}%>
</div>
<!-- report comment Modal -->
<div class="modal modal-fullscreen-md-down fade" id="reportCommentModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Segnala commento:</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p class="lead">Inserisci il motivo:</p>
                <input type="text" name="name" id="id-comment" hidden>
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
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

<script>
    $("#reportCommentModalOk").click(function () {
        let reason=$("#reason").val();
        let idComment=$("#id-comment").val();

    });
    $(".report-comment").click(function () {
        let id=parseInt(this.parentElement.parentElement.parentElement.parentElement.id.split("-")[1]);
        $("#id-comment").val(id);
    });
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
                if (data["error"] !== undefined){
                    showError(data["error"]);
                    return;
                }
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
</script>





<script>
    let cont = marked("<%=content.replace("script","scr\"+\"ipt")%>");
    cont = DOMPurify.sanitize(cont);
    console.log(cont);
    document.getElementById('content').innerHTML = cont;
    $("#content table").addClass("table table-striped");
    $("#content img").addClass("img-fluid");
</script>
</body>
</html>
