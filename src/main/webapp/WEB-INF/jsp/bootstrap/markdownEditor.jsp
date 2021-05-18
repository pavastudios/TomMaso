<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 18/05/2021
  Time: 17.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <link rel="stylesheet" href="https://unpkg.com/easymde/dist/easymde.min.css">
    <title>Editor markdown</title>
</head>
<body>
<%@include file="general/navbar.jsp"%>
<%= request.getPathInfo()%>
<form action="" method="post">
    <textarea id="my-text-area" style="width: 100%;" required></textarea>
    <label for="title">Inserire titolo: </label>
    <input type="text" name="title" id="title" class="form-control" placeholder="Titolo" required/><br/>
    <div class="text-center">
        <input type="submit" value="Carica" class="btn btn-dark"/>
    </div>
</form>

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script src="https://unpkg.com/easymde/dist/easymde.min.js"></script>
<script>
    var easyMDE = new EasyMDE({element: $('#my-text-area')[0]});
</script>
</body>
</html>
