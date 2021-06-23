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
    function urlRewriteTag(tagName){
        const x="<%=request.getAttribute("rewrite")%>";
        $("["+tagName+"]").each(function () {
            if($(this).attr(tagName)==="#")return;
            $(this).attr(tagName,$(this).attr(tagName)+x);
        });
    }
    $(function (){
        updateActionSearch();
        if("<%=request.getAttribute("rewrite")%>"!=="") {
            urlRewriteTag("href");
            urlRewriteTag("src");
            urlRewriteTag("action");
        }
    });
    const navbarSearchText=$("#navbarSearchText");
    var isUser=true;
    function updateActionSearch(){
        const query=navbarSearchText.val();
        navbarSearchText.val(query.replace(/[\\\/]/g,""));
        if(isUser){
            $("#navbarSearchForm").attr("action","${pageContext.request.contextPath}/user/"+query+"<%=request.getAttribute("rewrite")%>");
        }else{
            $("#navbarSearchForm").attr("action","${pageContext.request.contextPath}/home/"+query+"<%=request.getAttribute("rewrite")%>");
        }
    }
    const navbarModel = new bootstrap.Modal(document.getElementById('createBlogModalNavbar'));
    //Create blog code
    $("#createBlogNavbar").click(function () {
        const blogname = $("#blognameNavbar").val();
        if (!blogname.match(/^[a-zA-Z0-9-\\._]+$/)) {
            showError("Nome non valido!");
            return;
        }
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/blog/create<%=request.getAttribute("rewrite")%>',
            data: {name: blogname},
            success: function (data) {
                if (data["error"] !== undefined) {
                    showError(data["error"]);
                    return;
                }
                navbarModel.hide();
                if (location.pathname.endsWith("profile")) {
                    location.reload();
                }
            }
        });

    });

    const navbarLogin = new bootstrap.Modal(document.getElementById('navbarLogin'));
    //Create blog code
    $("#navbarLoginSubmit").click(function () {
        const username = $("#username-login").val();
        const password = $("#password-login").val();
        const remember = $("#remember-login").is(":checked") ? "on" : "";
        if(password.length<5){
            showError("La password deve contenere almeno 5 caratteri!");
            return;
        }
        if(username.length<5){
            showError("L'username deve contenere almeno 5 caratteri!");
            return;
        }
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/login<%=request.getAttribute("rewrite")%>',
            data: {
                username: username,
                password: password,
                remember: remember
            }, error: function () {
                showError("Impossibile eseguire login!");
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
    $("#navbarRegisterSubmit").click(function () {
        const username = $("#username-register").val();
        const email = $("#email-register").val();
        const password1 = $("#password1-register").val();
        const password2 = $("#password2-register").val();
        const remember = $("#remember-register").is(":checked") ? "on" : "";
        if(password1!=password2){
            showError("Le password non corrispondono!");
            return;
        }
        if(password1.length<5){
            showError("La password deve contenere almeno 5 caratteri!");
            return;
        }
        if(username.length<5){
            showError("L'username deve contenere almeno 5 caratteri!");
            return;
        }
        if(email.indexOf("@")==-1){
            showError("Email non valida!");
            return;
        }
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/sign-up<%=request.getAttribute("rewrite")%>',
            data: {
                username: username,
                password1: password1,
                password2: password2,
                email: email,
                remember: remember
            }, error: function () {
                showError("Impossibile registrare l'account");
            },
            success: function (data) {
                console.log(data);
                navbarRegister.hide();
                location.reload();
            }
        });
    });

    const navbarRecover = new bootstrap.Modal(document.getElementById('navInsertMail'));
    $("#recoverPsw").click(function () {
        const email = $("#recover-mail").val();
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/api/user/send-forgot-code<%=request.getAttribute("rewrite")%>',
            data: {
                email: email,
            },
            success: function (data) {
                if (data["error"] !== undefined) {
                    showError(data["error"]);
                    return;
                }
                console.log(data);
                navbarRecover.hide();
                navRecuperaPsw.show();
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
            url: '${pageContext.request.contextPath}/api/user/change-password<%=request.getAttribute("rewrite")%>',
            data: {
                password1: psw1,
                password2: psw2,
                code: code,
            },
            success: function (data) {
                if (data["error"] !== undefined) {
                    showError(data["error"]);
                    return;
                }
                navRecuperaPsw.hide();
            }
        });
    });
</script>

<script src="${pageContext.request.contextPath}/js/personal.js"></script>