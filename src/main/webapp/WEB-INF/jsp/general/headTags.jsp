<%@ page import="com.pavastudios.TomMaso.utility.Session" %><%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 02/05/21
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% Session ses = (Session) session.getAttribute(Session.SESSION_FIELD); %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8"/>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script href="${pageContext.request.contextPath}/js/marked.min.js"></script>
<script href="${pageContext.request.contextPath}/js/purify.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/materialdesignicons.min.css" type="text/css"/>
<link rel="icon" href="${pageContext.request.contextPath}/images/logo.ico" type="image/x-icon"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/uikit.min.css">
<script src="${pageContext.request.contextPath}/js/jdenticon.min.js"></script>

<style>
    .searchForm {
        margin-block-end: 0 !important;
    }
</style>
