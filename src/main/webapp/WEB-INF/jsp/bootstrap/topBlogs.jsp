<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <%@include file="general/headTags.jsp"%>
    <%
        List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");

    %>
    <script>
        //no propic code
        function useJidenticon(id){
            document.getElementById("propic-"+id).setAttribute("hidden","");
            document.getElementById("propic-svg-"+id).removeAttribute("hidden");
        }
    </script>
    <style>
        @media screen and (max-device-width: 576px){
            .carta { padding: 20px; }
        }
    </style>
</head>
<body>

<%@include file="general/navbar.jsp"%>

<div class="container">
    <div class="row">
    <%for(Blog blog:blogs){%>
        <div class="col-lg-4 col-sm-12 col-md-6 pb-lg-4 pt-sm-4 pt-md-4 px-3">
    <div class="carta px-0" href="<%=request.getContextPath()+"/home/"+blog.getNome()%>">
        <div class="card border-dark">
            <div class="card-body text-center">
                <img id="propic-b<%=blog.getNome()%>" class="rounded-circle w-100" src="${pageContext.request.contextPath}/blogs/<%=blog.getNome()%>/propic.png" onerror="useJidenticon('b<%=blog.getNome()%>')">
                <svg id="propic-svg-b<%=blog.getNome()%>" class="rounded-circle" data-jdenticon-value="<%=blog.getNome()%>" hidden></svg>
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

<%@include file="general/footer.jsp"%>
<%@include file="general/tailTag.jsp"%>
<script>
    $(".carta").click(function () {
        location.href=$(this).attr("href");
    });
</script>
</body>
</html>
