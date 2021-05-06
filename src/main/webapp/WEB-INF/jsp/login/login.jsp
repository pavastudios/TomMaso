<!DOCTYPE html>
<html>
<head>
    <title>TomMASO - Login</title>
    <%@ include file="../general/headTags.jsp" %>
</head>
<body class="login uk-cover-container uk-background-secondary uk-flex uk-flex-center uk-flex-middle uk-height-viewport uk-overflow-hidden uk-light"
      style="background-image: url(${pageContext.request.contextPath}/images/bg.jpg);" data-uk-height-viewport>
<!--%@include file="../general/navbar.jsp"%-->
<!-- Overlay -->
<div class="uk-position-cover uk-overlay-primary"></div>

<div class="uk-position-bottom-center uk-position-small uk-visible@m uk-position-z-index">
    <span class="uk-text-small uk-text-muted">© 2021 TomMASO - <a href="#">Created by P.A.V.A. Studios</a></span>
</div>
<div class="uk-width-medium uk-padding-small uk-position-z-index" uk-scrollspy="cls: uk-animation-fade">

    <div class="uk-text-center uk-margin">
        <img src="${pageContext.request.contextPath}/images/logo.png" style="max-width: 50px;" alt="Logo">
    </div>

    <!-- Login -->
    <form class="toggle-class" method="POST" action="${pageContext.request.contextPath}/login">
        <fieldset class="uk-fieldset">
            <div class="uk-margin-small">
                <div class="uk-inline uk-width-1-1">
                    <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: user"></span>
                    <input class="uk-input uk-border-pill" required placeholder="Username" type="text" name="username">
                </div>
            </div>
            <div class="uk-margin-small">
                <div class="uk-inline uk-width-1-1">
                    <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: lock"></span>
                    <input class="uk-input uk-border-pill" required placeholder="Password" type="password"
                           name="password">
                </div>
            </div>
            <div class="uk-margin-small">
                <label><input class="uk-checkbox" type="checkbox" name="remember"> Ricordami</label>
            </div>
            <div class="uk-margin-bottom">
                <button type="submit" class="uk-button uk-button-primary uk-border-pill uk-width-1-1">Log In</button>
            </div>
        </fieldset>
    </form>

    <!-- Recupero Password -->
    <form method="POST" class="toggle-class" action="${pageContext.request.contextPath}/forgot" hidden>
        <div class="uk-margin-small">
            <div class="uk-inline uk-width-1-1">
                <span class="uk-form-icon uk-form-icon-flip" data-uk-icon="icon: mail"></span>
                <input class="uk-input uk-border-pill" name="email" placeholder="E-mail" required type="text">
            </div>
        </div>
        <div class="uk-margin-bottom">
            <button type="submit" class="uk-button uk-button-primary uk-border-pill uk-width-1-1">Invia email</button>
        </div>
    </form>

    <!-- Azioni -->
    <div>
        <div class="uk-text-center">
            <a class="uk-link-reset uk-text-small toggle-class"
               data-uk-toggle="target: .toggle-class ;animation: uk-animation-fade">Password dimenticata?</a>
            <a class="uk-link-reset uk-text-small toggle-class"
               data-uk-toggle="target: .toggle-class ;animation: uk-animation-fade" hidden><span
                    data-uk-icon="arrow-left"></span> Ritorna al login</a>
        </div>
    </div>
</div>
<!--%@include file="../general/footer.jsp"%-->
</body>
</html>