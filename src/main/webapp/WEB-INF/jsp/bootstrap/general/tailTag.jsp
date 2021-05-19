<script src="${pageContext.request.contextPath}/js/jdenticon.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/marked.min.js"></script>
<script href="${pageContext.request.contextPath}/js/purify.js"></script>
<script href="${pageContext.request.contextPath}/js/easymde.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/fontawesome.min.js"></script>
<script src="https://unpkg.com/aos@next/dist/aos.js"></script>

<script>
    const navbarModel = new bootstrap.Modal(document.getElementById('createBlogModalNavbar'));
    //Create blog code
    $( "#createBlogNavbar" ).click(function() {
        const blogname = $("#blognameNavbar").val();
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

    const navbarLogin = new bootstrap.Modal(document.getElementById('navbarLogin'));
    //Create blog code
    $( "#navbarLoginSubmit" ).click(function() {
        const username = $("#username-login").val();
        const password = $("#password-login").val();
        const remember = $("#remember-login").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/login',
            data: {
                username: username,
                password: password,
                remember: remember
            },
            success: function (data) {
                console.log(data);
                navbarLogin.hide();
                location.reload();
            }
        });
    });

    const navbarRegister = new bootstrap.Modal(document.getElementById('navbarRegister'));
    //Create blog code
    $( "#navbarRegisterSubmit" ).click(function() {
        const username = $("#username-register").val();
        const email = $("#email-register").val();
        const password1 = $("#password1-register").val();
        const password2 = $("#password2-register").val();
        const remember = $("#remember-register").is(":checked") ? "on" : ""
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/sign-up',
            data: {
                username: username,
                password1: password1,
                password2: password2 ,
                email: email , remember:
                remember
            },
            success: function (data) {
                console.log(data);
                navbarRegister.hide();
                location.reload();
            }
        });
    });
</script>

<script>
    AOS.init({
        once: true,
    });
</script>