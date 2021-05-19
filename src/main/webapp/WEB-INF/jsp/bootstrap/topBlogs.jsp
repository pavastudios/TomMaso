<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pavastudios.TomMaso.utility.FileUtility" %>
<%@ page import="com.pavastudios.TomMaso.model.Blog" %><%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 19/05/21
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
        List<File> markdowns= (List<File>) request.getAttribute("pages");
        Blog blog= (Blog) request.getAttribute("blog");
    %>
    <title>Title</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>

<div class="container">
    <%for(int i=0;i<markdowns.size();i++){
        File f=markdowns.get(i);

        String url=FileUtility.relativeUrl(f);

    %>
    <div class="row mt-2">
        <div class="card px-0 col-12">
            <div class="card-header">
                <span><%=f.getName()%></span>
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

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script>
    async function lollo(id,markdown){
        document.getElementById(id).innerHTML = marked(markdown);
    }
    <%for(int i=0;i<markdowns.size();i++){
        File f=markdowns.get(i);
        String head= FileUtility.headFile(f,10);
        String escaped=FileUtility.escapeForMarked(head);
        %>
    lollo('page-<%=i%>',"<%=escaped%>")
    <%}%>
</script>
</body>
</html>
