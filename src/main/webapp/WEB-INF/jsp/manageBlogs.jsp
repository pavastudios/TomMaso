<%@ page import="java.io.File" %>
<%@ page import="com.pavastudios.TomMaso.utility.FileUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general/headTags.jsp"%>
    <%!
        private String iconFromFile(ServletContext cont, File f){

            if(f.isDirectory())return "folder";
            String mime=cont.getMimeType(f.getAbsolutePath());
            System.out.println(f+": "+mime);
            if(mime==null)return "file-text";
            if(mime.startsWith("image/"))return "image";
            if(mime.startsWith("video/"))return "video-camera";
            if(mime.startsWith("audio/"))return "microphone";
            if(mime.startsWith("text/"))return "file-text";
            return "file";
        }
    %>
    <%
        File[] files = (File[])request.getAttribute("files");
        String parent = (String)request.getAttribute("parentUrl");
        boolean root = (boolean)request.getAttribute("root");
    %>

    <title>Roba</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>

<div class="uk-width-1-1">
    <div id="app" class="uk-grid-small uk-animation-fade uk-flex-middle uk-child-width-1-9 uk-child-width-1-3@m uk-child-width-1-4@l uk-padding-small" uk-grid uk-height-match="target: > div > .uk-card">
        <div>
            <div class="uk-card uk-card-hover uk-height-expand uk-flex uk-flex-center uk-flex-middle">
                <a href="<%=root?request.getContextPath()+"/profile":parent%>">
                    <div class="uk-card-body">
                        <div class="uk-width-1-1 uk-text-center">
                            <span uk-icon="icon: reply; ratio: 5;" class="plus uk-text-secondary"></span>
                        </div>
                    </div>
                </a>
            </div>
        </div>
        <!--CARD-->
        <% for (File f: files) {
            String relUrl=FileUtility.relativeUrl(f);
        %>
        <div>

            <div class="uk-card uk-card-default uk-card-hover card">
                    <%if(f.isFile()){%>

                        <a class="uk-link-heading" href="<%=request.getContextPath()+"/blogs"+relUrl%>">
                    <%}else{%>
                        <a class="uk-link-heading" href="<%=request.getContextPath()+"/blog-manage"+relUrl%>">
                    <%}%>
                    <div class="uk-card-header">
                        <div class="uk-grid-small uk-flex-middle uk-text-center" uk-grid>
                            <div class="uk-card-media-top uk-flex uk-flex-center uk-width-1-1">
                                <span href="#" class="uk-icon-link uk-width-1-1" uk-icon="icon: <%=iconFromFile(request.getServletContext(),f)%>; ratio: 5"></span>
                            </div>
                            <div class="uk-width-1-1 uk-card-title uk-text-truncate">
                                <%=f.getName()%>
                            </div>
                        </div>
                    </div>
                </a>
                <div class="uk-card-footer">
                    <div class="uk-button-group uk-width-1-1 uk-text-center">
                        <a href="#" class="uk-icon-link uk-width-1-3 uk-text-primary" uk-icon="icon: pencil; ratio: 2"></a>
                        <a href="#move-blog" uk-toggle rel-url="<%=relUrl%>" class="move-blog uk-icon-link uk-width-1-3 uk-text-warning" uk-icon="icon: move; ratio: 2"></a>
                        <a href="#delete-file" uk-toggle rel-url="<%=relUrl%>" class="delete-blog uk-icon-link uk-width-1-3 uk-text-danger" uk-icon="icon: trash; ratio: 2"></a>
                    </div>
                </div>
            </div>
        </div>
        <%}%>


        <!--Nuova pagina-->
        <div>

            <div class="uk-card uk-card-primary uk-card-hover uk-height-expand uk-flex uk-flex-center uk-flex-middle">
                <a href="#">
                    <div class="uk-card-body">
                        <div class="uk-width-1-1 uk-text-center">
                            <span uk-icon="icon: plus; ratio: 5;" class="plus"></span>
                        </div>
                    </div>
                </a>
            </div>

        </div>
        <!--Modale muovi blog-->
        <div id="move-blog" uk-modal>
            <div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
                <h2 class="uk-modal-title">Muovi in</h2>
                <form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
                    <div class="uk-width-1-1 uk-margin-top uk-inline">
                        <div class="uk-form-controls">
                            <span class="uk-form-icon uk-margin-medium-left" uk-icon="icon: pencil"></span>
                            <input id="moveBlogHid" name="from-path" hidden>
                            <input class="uk-input uk-border-pill" id="moveBlog" type="text" placeholder="Muovi in" name="to-path">
                        </div>
                    </div>
                    <div class="uk-text-right uk-margin-right uk-margin-auto-left">
                        <button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
                        <input class="uk-button uk-button-primary" value="Sposta"  type="button" id="moveConfirm">
                    </div>
                </form>
            </div>
        </div>


        <!-- Modale nuovo blog -->
        <div id="new-blog" uk-modal>
            <div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
                <h2 class="uk-modal-title">Nuovo file</h2>
                <form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
                    <div class="uk-width-1-1 uk-margin-top uk-inline">
                        <div class="uk-form-controls">
                            <span class="uk-form-icon uk-margin-medium-left" uk-icon="icon: pencil"></span>
                            <input class="uk-input uk-border-pill" id="blogname" type="text" placeholder="Nome del blog" name="blogname">
                        </div>
                    </div>
                    <div class="uk-text-right uk-margin-right uk-margin-auto-left">
                        <button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
                        <input class="uk-button uk-button-primary" value="Crea" id="createBlog" type="button"></button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modale eliminazione -->
        <div id="delete-file" uk-modal>
            <div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
                <h2 id="deleteBlogTitle" class="uk-modal-title"></h2>
                <form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
                    <div class="uk-text-right uk-margin-right uk-margin-auto-left">
                        <button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
                        <input id="deleteBlogHid" type="text" hidden>
                        <input class="uk-button uk-button-primary" id="deleteBlog" type="button" value="Elimina">
                    </div>
                </form>
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

</script>
</body>
</html>