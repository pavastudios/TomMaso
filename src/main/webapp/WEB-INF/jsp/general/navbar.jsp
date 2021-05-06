<%@ page import="com.pavastudios.TomMaso.model.Utente" %>

<!-- Barra di navigazione -->
<nav class="uk-navbar-container uk-box-shadow-small" uk-navbar>

    <div class="uk-navbar-left">

        <ul class="uk-navbar-nav">
            <li class="uk-active"><a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/images/logo.png" style="max-width: 50px;" srcset=""></a></li>
            <li><a href="#" class="uk-visible@m">Home</a></li>
            <li><a href="#" class="uk-visible@m">Chi Siamo</a></li>
            <li><a href="#" class="uk-visible@m">Top Blog</a></li>
        </ul>

    </div>

    <div class="uk-navbar-right uk-visible@m">
        <ul class="uk-navbar-nav uk-visible@m">
            <% Utente u = ses.getUtente();
                if (u == null) {
            %>
            <li><a href="${pageContext.request.contextPath}/sign-up">Sign Up</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
            <%} else {%>
            <li><a href="${pageContext.request.contextPath}/profile"><%=u.getUsername()%>
            </a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            <%}%>
        </ul>
    </div>

    <div class="uk-navbar-right uk-hidden@m">
        <a class="uk-navbar-toggle" href="#" uk-toggle="target: #offcanvas-nav">
            <span uk-navbar-toggle-icon></span> <span class="uk-margin-small-left">Menu</span>
        </a>
    </div>

    <!-- Off Canvas Sidebar-->
    <div id="offcanvas-nav" uk-offcanvas="mode: reveal; flip: true; selPanel: .uk-offcanvas-bar-light;" class="uk-box-shadow-medium">
        <div class="uk-offcanvas-bar-light">

            <ul class="uk-nav uk-nav-default">
                <li><a href="#" class="uk-text-lead">Home</a></li>
                <li class="uk-parent">
                    <a href="#" class="uk-text-lead">Chi siamo</a>
                    <a href="#" class="uk-text-lead">Top Blog</a>
                </li>
                <li class="uk-nav-divider"></li>
                <li><a href="#" class="uk-text-lead"><span class="uk-margin-small-right" uk-icon="icon: file-edit"></span> Registrati</a></li>
                <li><a href="#" class="uk-text-lead"><span class="uk-margin-small-right" uk-icon="icon: sign-in"></span> Login</a></li>
            </ul>

        </div>
    </div>
</nav>
