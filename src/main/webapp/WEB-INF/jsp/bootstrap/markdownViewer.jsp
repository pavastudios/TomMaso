<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.BufferedInputStream" %><%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 17/05/2021
  Time: 17.33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
        File file = (File) request.getAttribute("file");
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
        StringBuilder content = new StringBuilder();
        int readChar;
        while((readChar = fis.read()) != -1) {
            switch(readChar){
                case '\n':content.append("\\n");break;
                case '\\':content.append("\\\\");break;
                case '"':content.append("\\\"");break;
                default:content.append((char)readChar);break;
            }

        }
    %>

    <title><%=file.getName()%></title>
</head>
<body>
<%@include file="general/navbar.jsp"%>
<div id="content">
</div>

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>

<script>

    document.getElementById('content').innerHTML = marked("<%=content.toString()%>");
</script>
</body>
</html>
