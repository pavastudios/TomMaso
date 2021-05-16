<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 16/05/2021
  Time: 17.38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tommaso - Upload</title>
</head>
<body>
<form action="/uploadFile" method="post" enctype="multipart/form-data">
  <input type="file" name="file"/>
  <input type="submit" value="upload"/>
</form>
</body>
</html>
