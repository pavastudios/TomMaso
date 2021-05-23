<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 20/05/2021
  Time: 18.56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi siamo - TomMASO</title>
    <%@ include file="../general/headTags.jsp" %>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>

<div class="container mt-md-5">
    <!-- Hero -->
    <div class="container col-xxl-8 px-4 py-5">
        <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
            <div class="col-10 col-sm-8 col-lg-6">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700" height="500" loading="lazy">
            </div>
            <div class="col-lg-6">
                <h1 class="display-5 fw-bold lh-1 mb-3">Chi siamo</h1>
                <p class="lead">Il team di TomMASO è composto da:</p>
                <ul class="list-group">
                    <li class="list-group-item">Allocca Antonio <span class="float-end">0512106933</span></li>
                    <li class="list-group-item">Di Costanzo Pasquale <span class="float-end">0512106675</span></li>
                    <li class="list-group-item">Esposito Vincenzo <span class="float-end">0512108127</span></li>
                    <li class="list-group-item">Toppi Antonio <span class="float-end">0512106882</span></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<%@ include file="../general/footer.jsp"%>
<%@ include file="../general/tailTag.jsp"%>
</body>
</html>
