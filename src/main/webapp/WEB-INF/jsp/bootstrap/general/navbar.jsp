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
                    <a class="nav-link" href="#">Chi siamo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Top Blogs</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <%if(ses.getUtente() == null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/sign-up">Registrati</a>
                    </li>
                <% } else {
                    Utente xUser=ses.getUtente();
                %>

                    <div class="flex-shrink-0 dropdown">
                        <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                            <img id="nav-user" class="rounded-circle" src="${pageContext.request.contextPath}/users/<%=xUser.getUsername()%>/propic.png" alt="<%=xUser.getUsername()%>" width="32" height="32" onerror="useJidenticonNav('user')">
                            <svg width="50" height="50" id="nav-svg-user" class="propic rounded-circle" data-jdenticon-value="<%=xUser.getUsername()%>" hidden></svg>
                        </a>
                        <ul class="dropdown-propic-nav dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                            <li><a class="dropdown-item" href="#" data-bs-toggle="modal" data-bs-target="#createBlogModalNavbar">Nuovo blog...</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profilo</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                        </ul>
                    </div>
                <% }%>
            </ul>
        </div>
    </div>
    <!-- create blog Modal -->
    <div class="modal fade" id="createBlogModalNavbar" tabindex="-1" aria-labelledby="exampleModalLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel1">Crea nuovo blog</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row mb-3">
                        <label for="blognameNavbar" class="col-sm-2 col-form-label lead">Nome</label>
                        <div class="col-sm-10">
                            <input type="text" name="name" id="blognameNavbar">
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" id="createBlogNavbar">Create</button>
                </div>
            </div>
        </div>
    </div>
</nav>
