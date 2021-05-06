<%@ page import="com.pavastudios.TomMaso.model.Utente" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>TomMASO</title>
    <%@ include file="general/headTags.jsp" %>
</head>
<body>
<%@include file="general/navbar.jsp"%>
<!-- Sezione Hero-->
<section class="uk-section uk-section-large uk-section-secondary uk-background-fixed uk-background-center-center"
         style="background-image: url(${pageContext.request.contextPath}/images/bg.jpg);">
    <div class="uk-container uk-container-small uk-text-center">
        <h1>Lorem</h1>
        <p class="uk-text-large">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Odio, aut!</p>
        <div class="data-uk-margin">
            <div class="uk-button uk-button-primary uk-first-column">
                Inizia
            </div>
        </div>
    </div>
</section>

<!-- Sezione Cards-->
<section class="uk-section uk-section-default uk-box-shadow-small uk-section-xsmall">
    <div class="uk-container">
        <div class="uk-flex uk-flex-middle">
            <h1 class="uk-heading-primary animate uk-scrollspy-inview uk-animation-slide-bottom-small uk-align-center">
                Perchè scegliere TomMASO?
            </h1>
        </div>
    </div>

    <!-- Card motivi -->

    <div class="uk-grid uk-grid-small uk-child-width-1-3@m uk-margin-medium-top uk-grid-match"
         data-uk-scrollspy="cls: uk-animation-slide-bottom-small; target: > div > .uk-card; delay: 200" data-uk-grid>

        <div>
            <div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column"
                 data-uk-scrollspy-class="uk-animation-slide-left-small">
                <div class="uk-card-header uk-text-center">
                    <h4 class="uk-text-bold">Primo Motivo</h4>
                </div>
                <div class="uk-card-body uk-flex-1">
                    <div class="uk-text-center">
                        <span uk-icon="icon: code; ratio: 3.5"></span>
                    </div>
                    <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate
                        aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi
                        corporis, voluptates magni eos.</p>
                </div>
            </div>
        </div>

        <div>
            <div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column">
                <div class="uk-card-header uk-text-center">
                    <h4 class="uk-text-bold">Secondo Motivo</h4>
                </div>
                <div class="uk-card-body uk-flex-1">
                    <div class="uk-text-center">
                        <span uk-icon="icon: file-edit; ratio: 3.5"></span>
                    </div>
                    <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate
                        aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi
                        corporis, voluptates magni eos.</p>
                </div>
            </div>
        </div>

        <div>
            <div class="uk-card uk-card-default uk-flex uk-card-hover uk-flex-column"
                 data-uk-scrollspy-class="uk-animation-slide-right-small">
                <div class="uk-card-header uk-text-center">
                    <h4 class="uk-text-bold">Terzo Motivo</h4>
                </div>
                <div class="uk-card-body uk-flex-1">
                    <div class="uk-text-center">
                        <span uk-icon="icon: users; ratio: 3.5"></span>
                    </div>
                    <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate
                        aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi
                        corporis, voluptates magni eos.</p>
                </div>
            </div>
        </div>

    </div>

</section>

<!-- Card prezzi -->
<section class="uk-section uk-section-muted uk-padding-remove-bottom">
    <div class="uk-container uk-container-small">
        <header class="uk-text-center">
            <h1 class="uk-heading-primary">Prezzi</h1>
            <p class="uk-width-3-5 uk-margin-auto">
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Quibusdam omnis in porro reiciendis beatae.
            </p>
        </header>
        <div class="uk-grid uk-grid-small uk-child-width-1-3@m uk-margin-medium-top uk-grid-match"
             data-uk-scrollspy="cls: uk-animation-slide-bottom-small; target: > div > .uk-card; delay: 200"
             data-uk-grid>

            <div>
                <div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column"
                     data-uk-scrollspy-class="uk-animation-slide-left-small">
                    <div class="uk-card-header uk-text-center">
                        <h4 class="uk-text-bold">Gratis</h4>
                    </div>
                    <div class="uk-card-body uk-flex-1">
                        <div class="uk-flex uk-flex-middle uk-flex-center">
								<span style="font-size: 4rem; font-weight: 200; line-height: 1em">
									<span style="font-size: 0.5em">€</span>0<small>.00</small>
								</span>
                        </div>
                        <div class="uk-text-small uk-text-center uk-text-muted">Pagamento annuale</div>
                        <ul>
                            <li>Feature 1</li>
                            <li>Feature 2</li>
                            <li>Feature 3</li>

                        </ul>
                    </div>
                    <div class="uk-card-footer">
                        <a href="#" class="uk-button uk-button-primary uk-width-1-1">Acquista</a>
                    </div>
                </div>
            </div>

            <div>
                <div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column">
                    <div class="uk-card-header uk-text-center">
                        <h4 class="uk-text-bold">Professionale</h4>
                    </div>
                    <div class="uk-card-body uk-flex-1">
                        <div class="uk-flex uk-flex-middle uk-flex-center">
								<span style="font-size: 4rem; font-weight: 200; line-height: 1em">
									<span style="font-size: 0.5em">€</span>9<small>.99</small>
								</span>
                        </div>
                        <div class="uk-text-small uk-text-center uk-text-muted">Pagamento annuale</div>
                        <ul>
                            <li>Feature 1</li>
                            <li>Feature 2</li>
                            <li>Feature 3</li>
                            <li>Feature 4</li>
                        </ul>
                    </div>
                    <div class="uk-card-footer">
                        <a href="" class="uk-button uk-button-primary uk-width-1-1">Acquista</a>
                    </div>
                </div>
            </div>

            <div>
                <div class="uk-card uk-card-default uk-flex uk-card-hover uk-flex-column"
                     data-uk-scrollspy-class="uk-animation-slide-right-small">
                    <div class="uk-card-header uk-text-center">
                        <h4 class="uk-text-bold">Aziendale</h4>
                    </div>
                    <div class="uk-card-body uk-flex-1">
                        <div class="uk-flex uk-flex-middle uk-flex-center">
								<span style="font-size: 4rem; font-weight: 200; line-height: 1em">
									<span style="font-size: 0.5em">$</span>49<small>.99</small>
								</span>
                        </div>
                        <div class="uk-text-small uk-text-center uk-text-muted">Pagamento annuale</div>
                        <ul>
                            <li>Feature 1</li>
                            <li>Feature 2</li>
                            <li>Feature 3</li>
                            <li>Feature 4</li>
                            <li>Feature 5</li>
                            <li>Feature 6</li>
                        </ul>
                    </div>
                    <div class="uk-card-footer">
                        <a href="" class="uk-button uk-button-primary uk-width-1-1">Acquista</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>

<%@include file="general/footer.jsp"%>
</body>
</html>