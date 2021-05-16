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
        <a class="navbar-brand" href="#">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="" width="30" height="30" class="d-inline-block align-text-top">
            TomMASO
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Chi siamo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Top Blogs</a>
                </li>
            </ul>
            <ul class="navbar-nav ml-auto">
                <%if(user == null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Registrati</a>
                    </li>
                <% } else {%>
                    <div class="flex-shrink-0 dropdown">
                        <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser2" data-bs-toggle="dropdown" aria-expanded="false">
                            <img id="nav-user" class="rounded-circle" src="${pageContext.request.contextPath}/users/<%=user.getUsername()%>/propic.png" alt="<%=user.getUsername()%>" width="32" height="32" class="rounded-circle" onerror="useJidenticonNav('user')">
                            <svg width="50" height="50" id="nav-svg-user" class="propic rounded-circle" data-jdenticon-value="<%=user.getUsername()%>" hidden></svg>
                        </a>
                        <ul class="dropdown-propic-nav dropdown-menu text-small shadow" aria-labelledby="dropdownUser2">
                            <li><a class="dropdown-item" href="#">Nuovo blog...</a></li>
                            <li><a class="dropdown-item" href="#">Profilo</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#">Logout</a></li>
                        </ul>
                    </div>
                <% }%>
            </ul>
        </div>
    </div>
</nav>
