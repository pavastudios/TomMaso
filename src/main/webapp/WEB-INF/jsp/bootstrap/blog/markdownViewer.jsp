<%@ page import="java.io.File" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Commento" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.utility.Utility" %>
<%@ page import="com.pavastudios.TomMaso.storage.FileUtility" %>
<%@ page import="org.jsoup.nodes.Entities" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Blog" %><%--
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
        Blog blog=(Blog)request.getAttribute("blog");
        List<Commento> comments = (List<Commento>) request.getAttribute("comments");
        String content = Entities.escape(FileUtility.escapeMDFile(file)).replace("&gt;",">");
    %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/comments.css">
    <title><%=file.getName()%></title>

</head>
<body>
<%@include file="../general/navbar.jsp"%>
<div class="container main-container">
    <%if(ses.isLogged()&&!ses.getUtente().equals(blog.getProprietario())){%>
    <button data-bs-toggle="modal" data-bs-target="#reportPostModal" class="report-comment btn btn-danger float-end"><i class="fas fa-flag"></i></button>
    <%}%>
    <div id="content">

    </div>
</div>

<h3 class="text-center">Area commenti</h3>
<div class="container mt-5">
    <% for(Commento commento:comments){
        String username=commento.getMittente()==null?"Utente eliminato":commento.getMittente().getUsername();
    %>
    <div class="row mt-2" id="comment-<%=commento.getIdCommento()%>">
        <a class="col-2" href="${pageContext.request.contextPath}/user/<%=username%>">
            <%if(commento.getMittente()!=null){%>
            <svg class="w-100 propic rounded-circle" data-jdenticon-value="<%=commento.getMittente().getUsername()%>" ></svg>
            <%}%>
        </a>
        <div class="card px-0 col-10">
            <div class="card-header">
                <span><%=username%></span>
                <div class="float-end">
                    <span style="margin-right: 8px"><%=Utility.DATE_FORMAT.format(commento.getDataInvio())%></span>
                    <%if(ses.isLogged()&&!ses.getUtente().equals(commento.getMittente())){%>
                    <button data-bs-toggle="modal" data-bs-target="#reportCommentModal" class="report-comment btn btn-danger"><i class="fas fa-flag"></i></button>
                    <%}%>
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
        <p class="modal-error"></p>
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
                <input type="text" name="name" id="reason-comment" class="input-text" maxlength="50">
                <p class="lead modal-error" hidden></p>
            </div>
            <p class="text-danger modal-error"></p>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="reportCommentModalOk">Segnala</button>
            </div>
        </div>
    </div>
</div>

<!-- report post Modal -->
<div class="modal modal-fullscreen-md-down fade" id="reportPostModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Segnala post:</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p class="lead">Inserisci il motivo:</p>
                <input type="text" name="name" id="reason-post" class="input-text" maxlength="50">
                <p class="lead modal-error" hidden></p>
            </div>
            <p class="text-danger modal-error"></p>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="reportPostModalOk">Segnala</button>
            </div>
        </div>
    </div>
</div>
<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>

<script>
    $("#reportPostModalOk").click(function () {
        let reason=$("#reason-post").val();
        let url="/"+location.pathname.split("/").splice(2).join("/");
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/report/post<%=request.getAttribute("rewrite")%>',
            data: {
                "reason": reason,
                "url-post":url
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
    $("#reportCommentModalOk").click(function () {
        let reason=$("#reason-comment").val();
        let idComment=parseInt($("#id-comment").val());
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/report/comment<%=request.getAttribute("rewrite")%>',
            data: {
                "reason": reason,
                "id-comment":idComment
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
            },
            error: function(){
                showError("Commento invalido");
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
