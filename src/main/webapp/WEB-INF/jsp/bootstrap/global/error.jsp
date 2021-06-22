<%--
  Created by IntelliJ IDEA.
  User: pasqu
  Date: 20/05/2021
  Time: 18.56
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Errore - TomMASO</title>
    <%@ include file="../general/headTags.jsp" %>
</head>
<body>
<%@ include file="../general/navbar.jsp" %>

<div class="container mt-md-5">
    <!-- Hero -->
    <%
    String error=request.getAttribute("javax.servlet.error.message").toString();
    if(error!=null&&error.length()>1)
        error=Character.toUpperCase(error.charAt(0))+error.substring(1);
    else
        error=exception.toString();
    %>
    <div class="container col-xxl-8 px-4 py-5">
        <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
            <div class="col-10 col-sm-8 col-lg-6">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700" height="500" loading="lazy">
            </div>
            <div class="col-lg-6">
                <h1 class="display-5 fw-bold lh-1 mb-3">Errore <%=request.getAttribute("javax.servlet.error.status_code")%></h1>
                <h2><%=error%></h2>
            </div>
        </div>
    </div>
</div>

<%@ include file="../general/footer.jsp"%>
<%@ include file="../general/tailTag.jsp"%>
</body>
</html>
