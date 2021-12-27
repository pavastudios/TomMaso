<%@ page import="com.pavastudios.TomMaso.storagesystem.model.Blog" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <%@include file="../general/headTags.jsp"%>
    <%
        List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");

    %>

    <style>
        @media screen and (max-device-width: 576px){
            .carta { padding: 20px; }
        }
    </style>
</head>
<body>

<%@include file="../general/navbar.jsp"%>

<div class="container main-container">
    <div class="row">
    <%for(Blog blog:blogs){%>
        <div class="col-lg-4 col-sm-12 col-md-6 pb-lg-4 pt-sm-4 pt-md-4 px-3">
    <div class="carta px-0" href="<%=request.getContextPath()+"/home/"+blog.getNome()%>">
        <div class="card border-dark">
            <div class="card-body text-center">
                <svg id="propic-svg-b<%=blog.getNome()%>" class="rounded-circle" data-jdenticon-value="<%=blog.getNome()%>"></svg>
            </div>
            <div class="card-header text-center">
                <h5 class="card-title"><%=blog.getNome()%></h5>
            </div>
        </div>
    </div>
        </div>
    <%}%>
    </div>
</div>

<%@include file="../general/footer.jsp"%>
<%@include file="../general/tailTag.jsp"%>
<script>
    $(".carta").click(function () {
        location.href=$(this).attr("href");
    });
</script>
</body>
</html>
