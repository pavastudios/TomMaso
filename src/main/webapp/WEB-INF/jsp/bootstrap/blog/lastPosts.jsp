<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.storage.FileUtility" %>
<%@ page import="com.pavastudios.TomMaso.storage.model.Blog" %>
<%@ page import="java.nio.file.attribute.BasicFileAttributes" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.LinkOption" %>
<%@ page import="com.pavastudios.TomMaso.utility.Utility" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <%@include file="../general/headTags.jsp"%>
    <%
        List<File> markdowns= (List<File>) request.getAttribute("pages");
        Blog blog= (Blog) request.getAttribute("blog");
    %>
    <title>Ultimi post - <%=blog.getNome()%></title>
</head>
<body>
<%@include file="../general/navbar.jsp"%>

<div class="container main-container">
    <%for(int i=0;i<markdowns.size();i++){
        File f=markdowns.get(i);
        String[] urlParts=FileUtility.relativeUrl(f).split("/",3);
        if(urlParts.length==3)
            urlParts[2] = URLEncoder.encode(urlParts[2], "UTF-8");
        String url = String.join("/", urlParts).replace("+","%20");
        BasicFileAttributes attributes= Files.readAttributes(f.toPath(),BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
    %>
    <div class="row mt-2">
        <div class="card px-0 col-12">
            <div class="card-header">
                <span><%=org.jsoup.nodes.Entities.escape(f.getName())%></span>
                <span class="float-end"><%=Utility.DATE_FORMAT.format(new Date(attributes.creationTime().toMillis()))%></span>
            </div>
            <div class="card-body" id="page-<%=i%>"></div>
            <div class="card-footer">
                <a class="float-end" href="${pageContext.request.contextPath}/blogs<%=url%>">
                    <button class="btn btn-primary">
                        Continua a leggere
                    </button>
                </a>
            </div>
        </div>
    </div>
    <%}%>
</div>

<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
<script>
    async function lollo(id,markdown){
        let x=marked(markdown);
        x=DOMPurify.sanitize(x);
        document.getElementById(id).innerHTML = x;
        $("#"+id+" table").addClass("table table-striped");
        $("#"+id+" img").addClass("img-fluid");
    }
    <%for(int i=0;i<markdowns.size();i++){
        File f=markdowns.get(i);
        String head= FileUtility.headFile(f,10);
        String escaped=FileUtility.escapeForMarked(head);
        %>
    lollo('page-<%=i%>',"<%=escaped.replace("script","scr\"+\"ipt")%>");
    <%}%>
</script>
</body>
</html>
