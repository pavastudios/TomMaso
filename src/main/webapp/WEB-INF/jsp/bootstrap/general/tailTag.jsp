<script src="${pageContext.request.contextPath}/js/jdenticon.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/marked.min.js"></script>
<script src="${pageContext.request.contextPath}/js/easymde.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.js"></script>
<script src="${pageContext.request.contextPath}/js/fontawesome.min.js"></script>
<script src="${pageContext.request.contextPath}/js/purify.js"></script>
<script src="${pageContext.request.contextPath}/js/aos.js"></script>
<script src="${pageContext.request.contextPath}/js/highlight.min.js"></script>

<script>
    const navbarModel = new bootstrap.Modal(document.getElementById('createBlogModalNavbar'));
    //Create blog code
    $( "#createBlogNavbar" ).click(function() {
        const blogname = $("#blognameNavbar").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/create<%=request.getAttribute("rewrite")%>',
            data: { name: blogname },
            success: function (data) {
                console.log(data);
                navbarModel.hide();
                if (data["error"] !== undefined){
                    $(".modal-error").show();
                    $(".modal-error").text(data["error"]);
                }
            }
        });
    });

    const navbarLogin = new bootstrap.Modal(document.getElementById('navbarLogin'));
    //Create blog code
    $( "#navbarLoginSubmit" ).click(function() {
        const username = $("#username-login").val();
        const password = $("#password-login").val();
        const remember = $("#remember-login").is(":checked") ? "on" : ""
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/login<%=request.getAttribute("rewrite")%>',
            data: {
                username: username,
                password: password,
                remember: remember
            },
            success: function (data) {
                console.log(data);
                navbarLogin.hide();
                location.reload();
            },
            error: function (data) {
                $(".modal-error").show();
                $(".modal-error").text("Login fallito");
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
            url: '${pageContext.request.contextPath}/sign-up<%=request.getAttribute("rewrite")%>',
            data: {
                username: username,
                password1: password1,
                password2: password2 ,
                email: email ,
                remember: remember
            },
            success: function (data) {
                console.log(data);
                navbarRegister.hide();
                location.reload();
                if (data["error"] !== undefined){
                    $(".modal-error").show();
                    $(".modal-error").text(data["error"]);
                }
            }
        });
    });

    const navbarRecover = new bootstrap.Modal(document.getElementById('navInsertMail'));
    $("#recoverPsw").click(function () {
        const email = $("#recover-mail").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/forgot<%=request.getAttribute("rewrite")%>',
            data: {
                email: email,
            },
            success: function (data) {
                console.log(data);
                navbarRecover.hide();
                navRecuperaPsw.show();
                if (data["error"] !== undefined){
                    $(".modal-error").show();
                    $(".modal-error").text(data["error"]);
                }
            }
        });
    });

    const navRecuperaPsw = new bootstrap.Modal(document.getElementById('navRecuperaPsw'));
    $("#sendMail").click(function () {
        const code = $("#code").val();
        const psw1 = $("#password1").val();
        const psw2 = $("#password2").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/forgot<%=request.getAttribute("rewrite")%>',
            data: {
                password1: psw1,
                password2: psw2,
                code: code,
            },
            success: function (data) {
                console.log(data);
                navRecuperaPsw.hide();
                if (data["error"] !== undefined){
                    $(".modal-error").show();
                    $(".modal-error").text(data["error"]);
                }
            }
        });
    });
</script>

<script>
    AOS.init({
        once: true,
    });
</script>

<script>
    var isUser=true;
    function updateActionSearch(){
        const query=$("#navbarSearchText").val();
        if(isUser){
            $("#navbarSearchForm").attr("action","${pageContext.request.contextPath}/user/"+query+"<%=request.getAttribute("rewrite")%>");
        }else{
            $("#navbarSearchForm").attr("action","${pageContext.request.contextPath}/home/"+query+"<%=request.getAttribute("rewrite")%>");
        }
    }

    $("#navbarSearchBlog").click(function () {
        $("#navbarSearchType").text("Blog");
        isUser=false;
        updateActionSearch();
    });
    $("#navbarSearchUser").click(function () {
        $("#navbarSearchType").text("Utente");
        isUser=true;
        updateActionSearch();
    });
    $("#navbarSearchText").change(function () {
        updateActionSearch();
    });
    function urlRewriteTag(tagName){
        $("["+tagName+"]").each(function () {
            if($(this).attr(tagName)==="#")return;
            $(this).attr(tagName,$(this).attr(tagName)+"<%=request.getAttribute("rewrite")%>");
        });
    }
    $(function (){
        updateActionSearch();
        urlRewriteTag("href");
        urlRewriteTag("src");
        urlRewriteTag("action");
    });
</script>