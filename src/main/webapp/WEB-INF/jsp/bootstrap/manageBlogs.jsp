<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="com.pavastudios.TomMaso.utility.FileUtility" %>
<%@ page import="com.sun.xml.internal.txw2.output.CharacterEscapeHandler" %>
<%@ page import="org.jsoup.nodes.Entities" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <%@include file="general/headTags.jsp"%>
    <%!
        private String iconFromFile(ServletContext cont, File f){
            FileUtility.FileType type=FileUtility.getFileType(cont,f);
            switch(type){
                case DIRECTORY:return "fa-folder";
                case AUDIO:return "fa-music";
                case MARKDOWN:return "fa-code";
                case TEXT:return "fa-file-alt";
                case VIDEO:return "fa-video";
                case IMAGE:return "fa-image";
            }
            return "fa-file";
        }
    %>
    <%
        Utente user=ses.getUtente();
        File[] files = (File[])request.getAttribute("files");
        String parent = (String)request.getAttribute("parentUrl");
        boolean root = (boolean)request.getAttribute("root");
    %>
    <title>Gestione Blog - TomMASO</title>
</head>
<body>

<%@include file="general/navbar.jsp"%>
<%
    List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");
%>
<div class="container-fluid">

    <div class="row py-5">

        <div class="col-12">
            <div class="row">

                <!--back button-->
                <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                    <a href="<%=root?request.getContextPath()+"/profile":parent%>">
                    <div class="card border-dark h-100 align-middle">
                        <div class="card-body d-flex align-items-center justify-content-center">
                            <i class="fas fa-reply fa-10x"></i>
                        </div>
                    </div>
                    </a>
                </div>

                <% for (File f: files) {
                    String relUrl= FileUtility.relativeUrl(f);
                %>
                <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                    <div class="card border-dark">
                        <%if(f.isFile()){%>

                        <a class="uk-link-heading" href="<%=request.getContextPath()+"/blogs"+relUrl%>">
                                <%}else{%>
                            <a class="uk-link-heading" href="<%=request.getContextPath()+"/blog-manage"+relUrl%>">
                                    <%}%>
                        <div class="card-header text-center">
                            <i class="fa-10x fas <%=iconFromFile(request.getServletContext(),f)%>"></i>
                            <h5 class="card-title text-truncate"><%=Entities.escape(f.getName())%></h5>
                        </div>
                            </a>

                        <div class="card-footer d-grid w-100">
                            <div class="row gap-0">
                                <%
                                    if(FileUtility.getFileType(request.getServletContext(), f)==FileUtility.FileType.MARKDOWN){
                                        String path = f.getAbsolutePath();
                                %>
                                <a class="col-4" rel-url="<%=relUrl%>" href="<%=request.getContextPath()%>/edit-md/<%=path.substring(path.indexOf("blogs")+6)%>"><button type="button" class="col-12 btn btn-outline-primary"><i class="fas fa-pen"></i></button></a>
                                <%}%>
                                <a class="col-4 move-blog" rel-url="<%=relUrl%>" data-bs-toggle="modal" data-bs-target="#moveModal" href="#"><button type="button" class="col-12 btn btn-outline-warning"><i class="fas fa-copy"></i></button></a>
                                <a class="col-4 delete-blog" rel-url="<%=relUrl%>" data-bs-toggle="modal" data-bs-target="#deleteModal" href="#"><button type="button" class="col-12 btn btn-outline-danger" ><i class="fas fa-trash"></i></button></a>
                            </div>
                        </div>
                    </div>
                </div>
                <%}%>
                <!--Add button-->
                <div class="col-lg-3 col-md-6 col-sm-12 mb-3">
                    <a data-bs-toggle="modal" data-bs-target="#uploadModal">
                        <div class="card border-dark h-100 align-middle">
                            <div class="card-body d-flex align-items-center justify-content-center">
                                <i class="fas fa-plus fa-10x"></i>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Upload Modal -->
<div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalUpload">Aggiungi file:</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <%
                    String url = (String) request.getAttribute("javax.servlet.forward.request_uri");
                    url = url.split("/",4)[3];
                %>
                <fieldset>
                    <legend>Carica file</legend>
                    <form action="<%=request.getContextPath()+"/upload-file/"+url%>" method="post" enctype="multipart/form-data">
                        <input type="file" name="file" id="file"/>
                        <input type="submit" class="btn btn-primary" value="Conferma"/>
                    </form>
                </fieldset>
                <fieldset>
                    <legend>Nuova pagina</legend>
                    <form action="<%=request.getContextPath()+"/edit-md/"+url%>" method="post" id="createMD">
                        <input type="text" name="titleMD" id="titleMD" placeholder="Inserire titolo" required/>
                        <input type="submit" class="btn btn-primary" value="Conferma"/>
                    </form>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<!-- Move Modal -->
<div class="modal fade" id="moveModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Spostare il file</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="#" method="post">
                    <input type="text" name="from-name" id="moveBlogHid" hidden>
                    <input type="text" name="to-name" id="moveBlog">
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="moveConfirm">Save changes</button>
            </div>
        </div>
    </div>
</div>
<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteBlogTitle">Eliminare</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Eliminare il file?
                <input type="text" id="deleteBlogHid" value="" hidden/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger" id="deleteBlog">Elimina</button>
            </div>
        </div>
    </div>
</div>

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script>
    $(".move-blog").click(function(){
        $("#moveBlogHid").val($(this).attr("rel-url"));
        $("#moveBlog").val($(this).attr("rel-url"));
    });
    $(".delete-blog").click(function(){
        $("#deleteBlogTitle").text("Eliminare definitivamente '"+$(this).attr("rel-url")+"'?");
        $("#deleteBlogHid").val($(this).attr("rel-url"));
    });
    $("#deleteBlog").click(function () {
        const url = $("#deleteBlogHid").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/delete',
            data: {
                "url": url,
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });
    $("#moveConfirm").click(function(){
        var fromUrl=$("#moveBlogHid").val();
        var toUrl=$("#moveBlog").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/move',
            data: {
                "from-url": fromUrl,
                "to-url": toUrl
            },
            success: function (data) {
                console.log(data);
                if(data["error"]===undefined)
                    location.reload();
            }
        });
    });

    $("#titleMD").change(function(){
        const original ="<%=request.getContextPath()+"/edit-md/"+url+"/"%>";
        console.log($("#titleMD").val());
        $("#createMD").attr("action",original+$("#titleMD").val());
    });

</script>
</body>
</html>
