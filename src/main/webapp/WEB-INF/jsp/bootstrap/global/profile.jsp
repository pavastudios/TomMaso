<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jsoup.nodes.Entities" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <%@include file="../general/headTags.jsp"%>


    <style>
        .input-text {
            width: 100%;
        }

        @media screen and (max-device-width: 576px){
            .modify-button { margin-bottom: 20px; }
            .carta { padding: 20px; }
            .add_button { padding: 20px; }
        }
        @media screen and (min-device-width: 768px){
            .modify-button {
                margin-bottom: 20px;
            }
            .propic { max-width: 80%; padding-bottom: 20px; }
        }
    </style>

    <%
        Utente login=ses.getUtente();
        Utente user= (Utente) request.getAttribute("user");
        List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");
    %>

    <title>TomMASO - Profilo di <%= user.getUsername() %></title>
</head>
<body>

<%@include file="../general/navbar.jsp"%>
<div class="container-fluid">

    <div class="row py-5">
        <!-- Sidebaby -->
        <div class="col-lg-3 col-sm-12 col-md-12 text-center h-100">
            <div class="row d-flex justify-content-center">
                <svg class="w-100 propic rounded-circle" data-jdenticon-value="<%=user.getUsername()%>" ></svg>
            </div>
            <div class="row">
                <h4><%=user.getUsername()%></h4>
            </div>
            <%if(user.getIsAdmin()){%>
            <span class="badge bg-primary">Admin</span>
            <%}%>
            <% if(login!=null&&!user.equals(login)){%>
            <div class="row d-flex justify-content-center ">
                <form action="#" method="post" id="createChat">
                    <input type="submit" class="col-9 btn btn-outline-dark modify-button" value="Contatta"/>
                </form>
            </div>
            <%}%>
        </div>
        <div class="col-lg-9 col-sm-12">
            <div class="row">
                <!-- Blogs -->
                <%for(Blog blog:blogs){%>
                <div class="carta col-lg-4 col-sm-12 col-md-6 pb-lg-4 pt-sm-4 pt-md-4">
                    <div class="card border-dark">
                        <div class="card-header text-center" blog="<%=blog.getNome()%>">
                            <svg id="propic-svg-b<%=blog.getNome()%>" class="rounded-circle" data-jdenticon-value="<%=blog.getNome()%>"></svg>
                            <h5 class="card-title"><%=blog.getNome()%></h5>
                        </div>
                        <%if(blog.hasAccess(ses.getUtente())){%>
                        <div class="card-footer d-grid w-100">
                            <div class="row">
                                <a class="col-6 px-0" href="${pageContext.request.contextPath}/blog-manage/<%=blog.getNome()%>"><button type="button" class="code-anim col-12 btn btn-outline-primary"><i class="fas fa-code"></i></button></a>
                                <button data-bs-toggle="modal" data-bs-target="#deleteModal" type="button" class="delete-anim del-btn col-6 btn btn-outline-danger" blog-name="<%=blog.getNome()%>"><i class="fas fa-trash"></i></button>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
                <%}%>
                <%if(user.equals(ses.getUtente())){%>
                <!-- + Button -->
                <div class="add_button pb-lg-4 pt-sm-4 pt-md-4 col-lg-4 col-md-6 col-sm-12" id="add_button">
                    <div class="card border-dark h-100 align-middle cursore" data-bs-toggle="modal" data-bs-target="#createBlogModalNavbar">
                        <div class="card-body d-flex align-items-center justify-content-center">
                            <i class="fas fa-plus fa-10x"></i>
                        </div>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    </div>
</div>
<!-- create blog Modal -->
<div class="modal fade" id="createBlogModalNavbar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Crea nuovo blog</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row mb-3">
                    <label for="blognameNavbar" class="col-sm-2 col-form-label lead">Nome</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" id="blognameNavbar" maxlength="50">
                    </div>
                </div>
                <p class="text-danger modal-error"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" id="createBlogNavbar">Crea</button>
            </div>
        </div>
    </div>
</div>
<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteBlogTitle">Elimina blog</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body lead">
                Eliminare il blog selezionato?
                <input type="text" id="deleteBlogHid" value="" hidden/>
            </div>
            <p class="text-danger modal-error"></p>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="confirm-delete">Elimina</button>
            </div>
        </div>
    </div>
</div>

<!-- Update user Modal -->
<div class="modal fade" id="updateProfile" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteBlogTitle2">Modifica profilo</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="formUpdateUser" action="${pageContext.request.contextPath}/user-update" method="POST" enctype="multipart/form-data">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="addon-user">@</span>
                        <input name="username" type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-user">
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="addon-bio">Aggiorna bio</span>
                        <textarea name="bio" class="form-control" aria-label="Aggiorna bio"></textarea>
                    </div>
                    <div class="input-group mb-3">
                        <input name="oldpsw" type="password" class="form-control" placeholder="Vecchia password" aria-label="Vecchia password">
                    </div>
                    <div class="input-group mb-3">
                        <input name="newpsw1" type="password" class="form-control" placeholder="Nuova password" aria-label="Nuova password">
                    </div>
                    <div class="input-group mb-3">
                        <input name="newpsw2" type="password" class="form-control" placeholder="Ripeti nuova password" aria-label="Ripeti nuova password">
                    </div>
                    <p class="lead">Carica foto profilo:</p>
                    <div class="input-group mb-3">
                        <input name="propic" accept="image/*" type="file" class="form-control" placeholder="Nuova foto profilo">
                    </div>
                </form>
                <p class="text-danger modal-error"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="ok-update">Modifica profilo</button>
            </div>
        </div>
    </div>
</div>

<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
<script>
    $("#createChat").submit(function(e){
        e.preventDefault();
        let withName="<%=user.getUsername()%>";
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/chat/create-chat<%=request.getAttribute("rewrite")%>',
            data: {"with": withName},
            success: function () {
                document.location.href = '${pageContext.request.contextPath}/chat/'+withName+'<%=request.getAttribute("rewrite")%>';
            },error:function () {
                document.location.href = '${pageContext.request.contextPath}/chat/'+withName+'<%=request.getAttribute("rewrite")%>';
            }
        });
    });
    //set add button height
    var card = $(".carta:first");
    var add_button = $("#add_button");

    function update() {
        add_button.height(card.height());
    }
    $(function (){
        update();
        setInterval(update, 500);
    });

    //update user code
    $( "#ok-update" ).click(function() {
        $("#formUpdateUser").submit();
    });

    //Delete blog code
    $(".del-btn").click(function(){
        $("#deleteBlogHid").text($(this).attr("blog-name"))
    });
    $("#confirm-delete").click(function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/delete-blog<%=request.getAttribute("rewrite")%>',
            data: { "blog-name": $("#deleteBlogHid").text() },
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
    $(".card-header").click(function () {
        location.href="${pageContext.request.contextPath}/home/"+$(this).attr("blog");
    })
</script>
</body>
</html>
