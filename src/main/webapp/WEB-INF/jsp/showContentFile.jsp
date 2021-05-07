<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="general/headTags.jsp"%>
    <%
        String url = (String) request.getAttribute("url");
        File[] files = (File[])request.getAttribute("files");
    %>

    <title>Roba</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>

<div class="uk-width-1-1">
    <div id="app" class="uk-grid-small uk-animation-fade uk-flex-middle uk-child-width-1-9 uk-child-width-1-3@m uk-child-width-1-4@l uk-padding-small" uk-grid uk-height-match="target: > div > .uk-card">

        <!--CARD-->
        <% for (File f: files) {%>
        <div>

            <div class="uk-card uk-card-default uk-card-hover card">
                <a class="uk-link-heading" href="<%=url+"/"+f.getName()%>">
                    <div class="uk-card-header">
                        <div class="uk-grid-small uk-flex-middle uk-text-center" uk-grid>
                            <div class="uk-card-media-top uk-flex uk-flex-center uk-width-1-1">
                                <span href="#" class="uk-icon-link uk-width-1-1" uk-icon="icon: image; ratio: 5"></span>
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
                        <a href="#" class="uk-icon-link uk-width-1-3 uk-text-warning" uk-icon="icon: move; ratio: 2"></a>
                        <a href="#" class="uk-icon-link uk-width-1-3 uk-text-danger" uk-icon="icon: trash; ratio: 2"></a>
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

    </div>
</div>





<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
</body>
</html>