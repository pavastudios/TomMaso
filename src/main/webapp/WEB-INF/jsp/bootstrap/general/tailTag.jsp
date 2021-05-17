<script src="${pageContext.request.contextPath}/js/jdenticon.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/marked.min.js"></script>
<script href="${pageContext.request.contextPath}/js/purify.js"></script>
<script href="${pageContext.request.contextPath}/js/easymde.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/fontawesome.min.js"></script>
<script>
    const navbarModel = new bootstrap.Modal(document.getElementById('createBlogModalNavbar'));
    //Create blog code
    $( "#createBlogNavbar" ).click(function() {
        const blogname = $("#blognameNavbar").first().val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/create',
            data: { name: blogname },
            success: function (data) {
                console.log(data);
                navbarModel.hide();
            }
        });
    });
</script>