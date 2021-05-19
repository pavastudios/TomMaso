<%@ page import="com.pavastudios.TomMaso.utility.Session" %><%--
  Created by IntelliJ IDEA.
  User: dar9586
  Date: 02/05/21
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% Session ses = (Session) session.getAttribute(Session.SESSION_FIELD); %>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="${pageContext.request.contextPath}/images/logo.ico" type="image/x-icon"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/easymde.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/highlightjs.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/aos.css">

<style>
    .no-dec {text-decoration: none !important;}
    .no-dec:hover {text-decoration: none !important;}
    .no-dec:link {text-decoration: none !important;}
    .dropdown-propic-nav {position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(-110px, 0px);}

    .separatore {
        color: rgba(108,117,125,0.79);
        letter-spacing: 0.1rem;
        display: flex;
        align-items: center;
        --text-divider-gap: 1rem;
    }

    .separatore::before,
    .separatore::after {
        content: '';
        height: 1px;
        background-color: silver;
        flex-grow: 1;
    }

    .separatore::before {
        margin-right: var(--text-divider-gap);
    }

    .separatore::after {
        margin-left: var(--text-divider-gap);
    }
</style>
