<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<script>
    //no propic code
    function useJidenticonNav(id){
        document.getElementById("nav-"+id).setAttribute("hidden","");
        document.getElementById("nav-svg-"+id).removeAttribute("hidden");
    }
</script>

<!-- Barra di navigazione -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
            TomMASO
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/chi-siamo">Chi siamo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/top">Top Blogs</a>
                </li>
            </ul>
            <div class="d-sm-flex justify-content-sm-center">

                <ul class="navbar-nav ml-auto d-flex justify-content-center align-content-center flex-md-row flex-sm-column ms-md-2">

                    <%if(ses.getUtente() == null) { %>
                    <li class="nav-item">
                        <a class="nav-link ms-md-2 me-md-2" href="#" data-bs-toggle="modal" data-bs-target="#navbarLogin">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ms-md-2" href="#" data-bs-toggle="modal" data-bs-target="#navbarRegister">Registrati</a>
                    </li>
                    <% } else {
                        Utente xUser=ses.getUtente();
                    %>

                    <div class="flex-shrink-0 dropdown">
                        <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                            <img id="nav-user" class="rounded-circle" src="${pageContext.request.contextPath}/users/<%=xUser.getUsername()%>/propic.png" alt="<%=xUser.getUsername()%>" width="32" height="32" onerror="useJidenticonNav('user')">
                            <svg width="50" height="50" id="nav-svg-user" class="rounded-circle" data-jdenticon-value="<%=xUser.getUsername()%>" hidden></svg>
                        </a>
                        <ul class="dropdown-propic-nav dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profilo</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/chats">Chat</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </ul>
                    </div>
                    <% }%>
                </ul>
            </div>
        </div>
    </div>

    <!-- Login Modal -->
    <div class="modal fade" id="navbarLogin" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="navbarLoginTitle">Login</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body pb-0">
                    <form id="formNavbarLogin" action="${pageContext.request.contextPath}/user-update" method="POST" enctype="multipart/form-data">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="addon-user">@</span>
                            <input id="username-login" name="username" type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-user" maxlength="50">
                        </div>
                        <div class="input-group mb-3">
                            <input id="password-login" name="password" type="password" class="form-control" placeholder="Password" aria-label="Password">
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" id="remember-login" type="checkbox" value="" name="remember">
                            <label class="form-check-label" for="remember-login">
                                Ricorda accesso
                            </label>

                        </div>
                        <p class="text-danger modal-error"></p>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <button type="button" class="btn btn-primary" id="navbarLoginSubmit">Entra</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Registration Modal -->
    <div class="modal fade" id="navbarRegister" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="navbarRegisterTitle">Registrazione</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="formNavbarRegister" action="${pageContext.request.contextPath}/user-update" method="POST" enctype="multipart/form-data">
                        <div class="input-group mb-3">
                            <span class="input-group-text" id="addon-user-register">@</span>
                            <input id="username-register" name="username-register" type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="addon-user" maxlength="50">
                        </div>
                        <div class="input-group mb-3">
                            <input id="email-register" name="email-register" type="email" class="form-control" placeholder="Email" aria-label="Email">
                        </div>
                        <div class="input-group mb-3">
                            <input id="password1-register" name="psw1-register" type="password" class="form-control" placeholder="Password" aria-label="Password1">
                        </div>
                        <div class="input-group mb-3">
                            <input id="password2-register" name="psw2-register" type="password" class="form-control" placeholder="Ripeti password" aria-label="Password2">
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" id="remember-register" type="checkbox" value="" name="remember">
                            <label class="form-check-label" for="remember-register">
                                Ricorda accesso
                            </label>
                        </div>
                    </form>
                    <p class="text-danger modal-error"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <button type="button" class="btn btn-primary" id="navbarRegisterSubmit">Registrati</button>
                </div>
            </div>
        </div>
    </div>

</nav>
