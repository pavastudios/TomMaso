<%@ page import="com.pavastudios.TomMaso.model.Chat" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 21/05/2021
  Time: 15.27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
        Utente login = ses.getUtente();
        List<Utente>admins= (List<Utente>) request.getAttribute("admins");
    %>
    <title>Lista admin - TomMASO</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>
<div class="text-center"><h2 class="mt-3">Lista admin</h2></div>
<div class="container main-container">
    <div class="row">
        <% for(Utente other : admins){%>
        <div class="col-lg-3 col-md-6 col-sm-12 mt-4">
            <div class="card">
                <div class="card-header px-5 cursore"  href="${pageContext.request.contextPath}/user/<%=other.getUsername()%>">
                    <%=other.propicHtml(request.getServletContext())%>
                    <h5 class="card-title text-center"><%=other.getUsername()%></h5>
                </div>
                <div class="card-body">
                    <button type="button" name="<%=other.getUsername()%>" class="remove-admin col-12 btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteModal"><i class="fas fa-trash"></i></button>
                </div>
            </div>
        </div>
        <%}%>
        <!--Add button-->
        <div class="add-file col-lg-3 col-md-6 col-sm-12 mt-4 cursore">
            <div class="card border-dark h-100 align-middle" data-bs-toggle="modal" data-bs-target="#addAdmin">
                <div class="card-body d-flex align-items-center justify-content-center">
                    <i class="fas fa-plus fa-10x"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteBlogTitle">Elimina blog</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body lead">
                Rimuovere <span id="deleteAdminName"></span> dagli admin
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="confirm-delete">Elimina</button>
            </div>
        </div>
    </div>
</div>
<!-- create blog Modal -->
<div class="modal fade" id="addAdmin" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Aggiungere nuovo admin</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row mb-3">
                    <label for="addAdminName" class="col-sm-2 col-form-label lead">Username</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" id="addAdminName">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" id="addAdminConfirm">Aggiungi</button>
            </div>
        </div>
    </div>
</div>


<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script>
    $(".card-header").click(function (){
        location.href= $(this).attr("href");
    });
    $(".remove-admin").click(function (){
        $("#deleteAdminName").text($(this).attr("name"))
    });
    $("#confirm-delete").click(function(){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/user/remove-admin',
            data: {
                "username":  $("#deleteAdminName").text()
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
    $("#addAdminConfirm").click(function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/user/add-admin',
            data: {
                "username":  $("#addAdminName").val()
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
</script>
</body>
</html>
