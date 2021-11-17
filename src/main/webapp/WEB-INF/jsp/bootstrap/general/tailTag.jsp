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
        if("<%=request.getAttribute("rewrite")%>"!=="") {
            urlRewriteTag("href");
            urlRewriteTag("src");
            urlRewriteTag("action");
        }
    });
    const navbarSearchText=$("#navbarSearchText");

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
</script>

<script src="${pageContext.request.contextPath}/js/personal.js"></script>