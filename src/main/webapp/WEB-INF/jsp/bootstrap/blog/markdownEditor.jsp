<%@ page import="com.pavastudios.TomMaso.utility.FileUtility" %>
<%@ page import="com.pavastudios.TomMaso.test.PersonalFileDir" %>
<%@ page import="java.io.File" %>
<%@ page import="org.jsoup.nodes.Entities" %>
<%@ page import="java.nio.file.Files" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 18/05/2021
  Time: 17.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../general/headTags.jsp"%>
    <link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
    <title>Editor markdown</title>
</head>
<body>
<%@include file="../general/navbar.jsp"%>
<%
    File file = (File) request.getAttribute("file");
    String path = (String) request.getAttribute("path");
    String escaped;
    if (file.exists()) {
        String content = String.join("\n",Files.readAllLines(file.toPath()));
        escaped = Entities.escape(content);
    }else{
        escaped="";
    }
%>
<form action="<%=request.getContextPath()%>/upload-md<%=path%>" method="post">
    <textarea id="my-text-area" style="width: 100%;" name="content"><%=escaped%></textarea>
    <div class="text-center">
        <input type="submit" value="Carica" class="btn btn-primary"/>
    </div>
</form>

<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
<script>
    const easyMDE = new EasyMDE({
        element: $('#my-text-area')[0],
        renderingConfig: {
            codeSyntaxHighlighting: true,
            sanitizerFunction: function(renderedHTML) {
                return DOMPurify.sanitize(renderedHTML);
            },
        },
    });
</script>
</body>
</html>
