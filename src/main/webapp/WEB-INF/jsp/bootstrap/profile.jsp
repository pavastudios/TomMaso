<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <%@include file="general/headTags.jsp"%>

    <title>Document</title>
</head>
<body>

<%@include file="general/navbar.jsp"%>
<%
    Utente login=ses.getUtente();
    Utente user= (Utente) request.getAttribute("user");
    List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");
    boolean owner=user.equals(login);
%>
<div class="container-fluid">

    <div class="row py-5">
        <div class="col-3 text-center">
            <div class="row">
                <svg class="rounded-circle" data-jdenticon-value="<%=user.getUsername()%>"></svg>
            </div>
            <div class="row">
                <h4><%=user.getUsername()%></h4>
            </div>
            <%if(user.getIsAdmin()){%>
            <span class="badge bg-primary">Admin</span>
            <%}%>
            <div class="row">
                <p>Email: <%=user.getEmail()%></p>
            </div>
            <div class="row">
                <p>'<%=user.getBio()%>'</p>
            </div>
            <div class="row d-flex justify-content-center ">
                <button type="button" class="col-9 btn btn-outline-dark">Modifica utente</button>
            </div>
        </div>
        <div class="col-9">
            <div class="row">
                <%for(Blog blog:blogs){%>
                <div class="col-4">
                    <div class="card border-dark">
                        <div class="card-header text-center">
                            <svg class="rounded-circle" data-jdenticon-value="<%=blog.getNome()%>"></svg>
                            <h5 class="card-title text-truncate"><%=blog.getNome()%></h5>
                        </div>

                        <div class="card-footer d-grid w-100">
                            <div class="row gap-0">
                                <a class="col-4" href="${pageContext.request.contextPath}/blog-manage/<%=blog.getNome()%>"><button type="button" class="col-12 btn btn-outline-primary"><i class="fas fa-code"></i></button></a>
                                <a class="col-4" href="#"><button type="button" class="col-12 btn btn-outline-warning"><i class="fas fa-pen"></i></button></a>
                                <a class="col-4" href="#"><button type="button" class="col-12 btn btn-outline-danger" ><i class="fas fa-trash"></i></button></a>
                            </div>
                        </div>
                    </div>
                </div>
                <%}%>
                <div class="col-4">
                    <div class="card border-dark h-100 align-middle">
                        <div class="card-body d-flex align-items-center justify-content-center">
                            <i class="fas fa-plus fa-10x"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>

</body>
</html>
