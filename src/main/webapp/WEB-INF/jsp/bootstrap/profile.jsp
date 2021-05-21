<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jsoup.nodes.Entities" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <%@include file="general/headTags.jsp"%>
    <script>
        //no propic code
        function useJidenticon(id){
            document.getElementById("propic-"+id).setAttribute("hidden","");
            document.getElementById("propic-svg-"+id).removeAttribute("hidden");
        }
    </script>

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

<%@include file="general/navbar.jsp"%>
<div class="container-fluid">

    <div class="row py-5">
        <!-- Sidebaby -->
        <div class="col-lg-3 col-sm-12 col-md-12 text-center h-100">
            <div class="row d-flex justify-content-center">
                <%=user.propicHtml(request.getServletContext())%>
            </div>
            <div class="row">
                <h4><%=user.getUsername()%></h4>
            </div>
            <%if(user.getIsAdmin()){%>
            <span class="badge bg-primary">Admin</span>
            <%}%>
            <%if(user.equals(ses.getUtente())){%>
            <div class="row">
                <p>Email: <%=Entities.escape(user.getEmail())%></p>
            </div>
            <%}%>
            <div class="row">
                <p>'<%=Entities.escape(user.getBio())%>'</p>
            </div>
            <% if(login!=null){%>
            <% if(user.equals(login)){%>
            <div class="row d-flex justify-content-center ">
                <button type="button" class="col-9 btn btn-outline-dark modify-button" data-bs-toggle="modal" data-bs-target="#updateProfile">Modifica utente</button>
            </div>
            <a class="px-0" href="${pageContext.request.contextPath}/chats"><button type="button" class="btn btn-success"><i class="fas fa-comments"></i></button></a>
            <%}else{%>
            <div class="row d-flex justify-content-center ">
                <form action="${pageContext.request.contextPath}/new-chat" method="post">
                    <input type="text" name="receiver" value="<%=user.getIdUtente()%>" hidden/>
                    <input type="submit" class="col-9 btn btn-outline-dark modify-button" value="Contatta"/>
                </form>
            </div>
            <%}}%>
        </div>
        <div class="col-lg-9 col-sm-12">
            <div class="row">
                <!-- Blogs -->
                <%for(Blog blog:blogs){%>
                <div class="carta col-lg-4 col-sm-12 col-md-6 pb-lg-4 pt-sm-4 pt-md-4">
                    <div class="card border-dark">
                        <div class="card-header text-center" blog="<%=blog.getNome()%>">
                            <img id="propic-b<%=blog.getNome()%>" class="rounded-circle w-100" src="${pageContext.request.contextPath}/blogs/<%=blog.getNome()%>/propic.png" onerror="useJidenticon('b<%=blog.getNome()%>')">
                            <svg id="propic-svg-b<%=blog.getNome()%>" class="rounded-circle" data-jdenticon-value="<%=blog.getNome()%>" hidden></svg>
                            <h5 class="card-title"><%=blog.getNome()%></h5>
                        </div>
                        <%if(blog.hasAccess(ses.getUtente())){%>
                        <div class="card-footer d-grid w-100">
                            <div class="row">
                                <a class="col-4 px-0" href="${pageContext.request.contextPath}/blog-manage/<%=blog.getNome()%>"><button type="button" class="col-12 btn btn-outline-primary"><i class="fas fa-code"></i></button></a>
                                <button data-bs-toggle="modal" data-bs-target="#renameBlogModal" type="button" class="ren-btn col-4 btn btn-outline-warning" blog-name="<%=blog.getNome()%>"><i class="fas fa-pen"></i></button>
                                <button data-bs-toggle="modal" data-bs-target="#deleteModal" type="button" class="del-btn col-4 btn btn-outline-danger" blog-name="<%=blog.getNome()%>"><i class="fas fa-trash"></i></button>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
                <%}%>
                <%if(user.equals(ses.getUtente())){%>
                <!-- + Button -->
                <div class="add_button pb-lg-4 pt-sm-4 pt-md-4 col-lg-4 col-md-6 col-sm-12" id="add_button">
                    <div class="card border-dark h-100 align-middle" data-bs-toggle="modal" data-bs-target="#createBlogModal">
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
<div class="modal fade" id="createBlogModal" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel1">Crea nuovo blog</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row mb-3">
                    <label for="blogname" class="col-sm-2 col-form-label lead">Nome</label>
                    <div class="col-sm-10">
                        <input type="text" name="name" id="blogname">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" id="createBlog">Create</button>
            </div>
        </div>
    </div>
</div>
<!-- rename blog Modal -->
<div class="modal modal-fullscreen-md-down fade" id="renameBlogModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Rinomina blog</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p class="lead">Inserisci il nuovo nome:</p>
                <input type="text" name="name" id="renameFormHid" hidden>
                <input type="text" name="name" id="renameForm" class="input-text">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-primary" id="rename-blog-ok">Rinomina</button>
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
                Eliminare il blog selezionato?
                <input type="text" id="deleteBlogHid" value="" hidden/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="confirm-delete">Elimina</button>
            </div>
        </div>
    </div>
</div>

<!-- Update user Modal -->
<div class="modal fade" id="updateProfile" tabindex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
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
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                <button type="button" class="btn btn-danger" id="ok-update">Modifica profilo</button>
            </div>
        </div>
    </div>
</div>

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script>

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
    //Create blog code
    $( "#createBlog" ).click(function() {
        var blogname=$("#blogname").first().val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/create',
            data: { name: blogname },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
    //Rename blog code
    $(".ren-btn").click(function () {
        $("#renameFormHid").val($(this).attr("blog-name"));
        $("#renameForm").val("");
    });
    $( "#rename-blog-ok" ).click(function() {
        var oldname=$("#renameFormHid").val();
        var newname=$("#renameForm").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/rename',
            data: {
                "from-name": oldname,
                "to-name": newname
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
    //Delete blog code
    $(".del-btn").click(function(){
        $("#deleteBlogHid").text($(this).attr("blog-name"))
    });
    $("#confirm-delete").click(function (){
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/delete-blog',
            data: { "blog-name": $("#deleteBlogHid").text() },
            success: function (data) {
                console.log(data);
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
