<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18/05/2021
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TomMaso</title>
    <%@ include file="general/headTags.jsp" %>
</head>
<body>
    <%@ include file="general/navbar.jsp" %>

    <!-- Hero -->
    <div class="container col-xxl-8 px-4 py-5">
        <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
            <div class="col-10 col-sm-8 col-lg-6">
                <img src="${pageContext.request.contextPath}/images/logo.png" class="scale-on-hover d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700" height="500" loading="lazy">
            </div>
            <div class="col-lg-6">
                <h1 class="display-5 fw-bold lh-1 mb-3">TomMASO</h1>
                <p class="lead">TomMASO è una piattaforma semplice e user-friendly che permette agli utenti registrati di creare un proprio spazio tramite l’utilizzo di un’intuitiva interfaccia grafica. TomMASO è indirizzato a tutti quegli utenti che hanno competenze minime nello svilluppo web. Si è resa possibile la creazione di spazi personali anche da smartphone e tablet grazie alla tecnologia adoperata che rende la piattaforma completamente responsive.</p>
                <div class="d-grid gap-2 d-md-flex justify-content-md-start">
                    <button type="button" class="btn btn-primary btn-lg px-4 me-md-2" data-bs-toggle="modal" data-bs-target="#navbarRegister">Inizia subito</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Features -->
    <section>
        <div class="container px-4 py-5" id="hanging-icons">
            <h2 class="pb-2 text-center border-bottom">Perchè scegliere TomMASO?</h2>
            <div class="row g-4 py-5 row-cols-1 row-cols-lg-3">
                <div class="col d-flex align-items-start">
                    <div>
                        <h2>Motivo 1</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto aspernatur consequatur, eos
                            error, ex, excepturi fugit ipsum minus quaerat quis quo quos recusandae repudiandae sequi.</p>
                    </div>
                </div>
                <div class="col d-flex align-items-start">
                    <div>
                        <h2>Motivo 2</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto aspernatur consequatur, eos
                            error, ex, excepturi fugit ipsum minus quaerat quis quo quos recusandae repudiandae sequi.</p>
                    </div>
                </div>
                <div class="col d-flex align-items-start">
                    <div>
                        <h2>Motivo 3</h2>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Architecto aspernatur consequatur, eos
                            error, ex, excepturi fugit ipsum minus quaerat quis quo quos recusandae repudiandae sequi.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Prezzi
    <div class="container py-3">
        <section>
            <div class="pricing-header p-3 pb-md-4 mx-auto text-center">
                <h1 class="display-4 fw-normal">Prezzi</h1>
                <p class="fs-5 text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Beatae deserunt
                    doloremque ea eius ipsam magnam maxime quibusdam rem temporibus velit?</p>
            </div>
        </section>

        <main>
            <div class="row row-cols-1 row-cols-md-3 mb-3 text-center mb-5">
                <div class="col" data-aos="zoom-in">
                    <div class="card mb-4 rounded-3 shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal">Gratis</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">€0<small class="text-muted fw-light">/mese</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">
                                <li>Feature #1</li>
                                <li>Feature #2</li>
                                <li>Feature #3</li>
                                <li>Feature #4</li>
                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-outline-primary">Registrati gratis</button>
                        </div>
                    </div>
                </div>
                <div class="col" data-aos="zoom-in">
                    <div class="card mb-4 rounded-3 shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal">Professionale</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">€15<small class="text-muted fw-light">/mese</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">
                                <li>Feature #1</li>
                                <li>Feature #2</li>
                                <li>Feature #3</li>
                                <li>Feature #4</li>
                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-primary">Inizia subito</button>
                        </div>
                    </div>
                </div>
                <div class="col" data-aos="zoom-in">
                    <div class="card mb-4 rounded-3 shadow-sm border-primary">
                        <div class="card-header py-3 text-white bg-primary border-primary">
                            <h4 class="my-0 fw-normal">Aziendale</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title pricing-card-title">€29<small class="text-muted fw-light">/mese</small></h1>
                            <ul class="list-unstyled mt-3 mb-4">
                                <li>Feature #1</li>
                                <li>Feature #2</li>
                                <li>Feature #3</li>
                                <li>Feature #4</li>
                            </ul>
                            <button type="button" class="w-100 btn btn-lg btn-primary">Contattaci</button>
                        </div>
                    </div>
                </div>
            </div>
        </main>    
    </div> -->
    <%@ include file="general/footer.jsp"%>
    <%@ include file="general/tailTag.jsp"%>
</body>
</html>
