<!DOCTYPE html>
<html lang="en">
<head>
	<title>TomMASO</title>

	<%@ include file="headTags.jsp"%>
</head>
<body>
	<!-- Barra di navigazione -->
	<nav class="uk-navbar-container" uk-navbar>

		<div class="uk-navbar-left">
	
			<ul class="uk-navbar-nav">
				<li class="uk-active"><a href="#"><img src="logo.png" style="max-width: 50px;" srcset=""></a></li>
				<li><a href="#">Home</a></li>
				<li><a href="#">Chi Siamo</a></li>
				<li><a href="#">Top Blog</a></li>
			</ul>
	
		</div>
	
		<div class="uk-navbar-right">
	
			<ul class="uk-navbar-nav">
				<li><a href="#">Sign Up</a></li>
				<li><a href="#">Login</a></li>
			</ul>
	
		</div>
	
	</nav>
	
	<!-- Sezione Hero-->
	<section class="uk-section uk-section-large uk-section-secondary uk-background-fixed uk-background-center-center" style="background-image: url(bg.jpg);">
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

		<div class="uk-grid uk-grid-small uk-child-width-1-3@m uk-margin-medium-top uk-grid-match" data-uk-scrollspy="cls: uk-animation-slide-bottom-small; target: > div > .uk-card; delay: 200" data-uk-grid>

			<div>
				<div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column" data-uk-scrollspy-class="uk-animation-slide-left-small">
					<div class="uk-card-header uk-text-center">
						<h4 class="uk-text-bold">Primo Motivo</h4>
					</div>
					<div class="uk-card-body uk-flex-1">
						<div class="uk-text-center">
							<span uk-icon="icon: code; ratio: 3.5"></span>
						</div>
						<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi corporis, voluptates magni eos.</p>
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
						<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi corporis, voluptates magni eos.</p>
					</div>
				</div>
			</div>

			<div>
				<div class="uk-card uk-card-default uk-flex uk-card-hover uk-flex-column" data-uk-scrollspy-class="uk-animation-slide-right-small">
					<div class="uk-card-header uk-text-center">
						<h4 class="uk-text-bold">Terzo Motivo</h4>
					</div>
					<div class="uk-card-body uk-flex-1">
						<div class="uk-text-center">
							<span uk-icon="icon: users; ratio: 3.5"></span>
						</div>
						<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Placeat sequi iusto quod voluptate aliquam, dignissimos ducimus totam sed possimus commodi eum saepe porro tempora adipisci quasi corporis, voluptates magni eos.</p>
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
			<div class="uk-grid uk-grid-small uk-child-width-1-3@m uk-margin-medium-top uk-grid-match" data-uk-scrollspy="cls: uk-animation-slide-bottom-small; target: > div > .uk-card; delay: 200" data-uk-grid>
				
				<div>
					<div class="uk-card uk-card-default uk-card-hover uk-flex uk-flex-column" data-uk-scrollspy-class="uk-animation-slide-left-small">
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
					<div class="uk-card uk-card-default uk-flex uk-card-hover uk-flex-column" data-uk-scrollspy-class="uk-animation-slide-right-small">
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
	</section>

	<!-- Footer -->
	<footer class="uk-section uk-section-secondary uk-padding-remove-bottom uk-margin-top">
		<div class="uk-container">
			<div class="uk-grid uk-grid-large uk-grid-stack" data-uk-grid="">
				<div class="uk-width-1-2@m uk-first-column">
					<h5>Chi Siamo</h5>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud cillum dolore eu fugiat nulla contact to: <a href="#" title="">pava@studios.com</a></p>
					<div>
						<a href="" class="uk-icon-button uk-icon" data-uk-icon="twitter"><svg width="20" height="20" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M19,4.74 C18.339,5.029 17.626,5.229 16.881,5.32 C17.644,4.86 18.227,4.139 18.503,3.28 C17.79,3.7 17.001,4.009 16.159,4.17 C15.485,3.45 14.526,3 13.464,3 C11.423,3 9.771,4.66 9.771,6.7 C9.771,6.99 9.804,7.269 9.868,7.539 C6.795,7.38 4.076,5.919 2.254,3.679 C1.936,4.219 1.754,4.86 1.754,5.539 C1.754,6.82 2.405,7.95 3.397,8.61 C2.79,8.589 2.22,8.429 1.723,8.149 L1.723,8.189 C1.723,9.978 2.997,11.478 4.686,11.82 C4.376,11.899 4.049,11.939 3.713,11.939 C3.475,11.939 3.245,11.919 3.018,11.88 C3.49,13.349 4.852,14.419 6.469,14.449 C5.205,15.429 3.612,16.019 1.882,16.019 C1.583,16.019 1.29,16.009 1,15.969 C2.635,17.019 4.576,17.629 6.662,17.629 C13.454,17.629 17.17,12 17.17,7.129 C17.17,6.969 17.166,6.809 17.157,6.649 C17.879,6.129 18.504,5.478 19,4.74"></path></svg></a>
						<a href="" class="uk-icon-button uk-icon" data-uk-icon="facebook"><svg width="20" height="20" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M11,10h2.6l0.4-3H11V5.3c0-0.9,0.2-1.5,1.5-1.5H14V1.1c-0.3,0-1-0.1-2.1-0.1C9.6,1,8,2.4,8,5v2H5.5v3H8v8h3V10z"></path></svg></a>
						<a href="" class="uk-icon-button uk-icon" data-uk-icon="instagram"><svg width="20" height="20" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M13.55,1H6.46C3.45,1,1,3.44,1,6.44v7.12c0,3,2.45,5.44,5.46,5.44h7.08c3.02,0,5.46-2.44,5.46-5.44V6.44 C19.01,3.44,16.56,1,13.55,1z M17.5,14c0,1.93-1.57,3.5-3.5,3.5H6c-1.93,0-3.5-1.57-3.5-3.5V6c0-1.93,1.57-3.5,3.5-3.5h8 c1.93,0,3.5,1.57,3.5,3.5V14z"></path><circle cx="14.87" cy="5.26" r="1.09"></circle><path d="M10.03,5.45c-2.55,0-4.63,2.06-4.63,4.6c0,2.55,2.07,4.61,4.63,4.61c2.56,0,4.63-2.061,4.63-4.61 C14.65,7.51,12.58,5.45,10.03,5.45L10.03,5.45L10.03,5.45z M10.08,13c-1.66,0-3-1.34-3-2.99c0-1.65,1.34-2.99,3-2.99s3,1.34,3,2.99 C13.08,11.66,11.74,13,10.08,13L10.08,13L10.08,13z"></path></svg></a>
					</div>
				</div>
				<div class="uk-width-1-6@m uk-grid-margin uk-first-column">
					<h5>Servizi</h5>
					<ul class="uk-list">
						<li>TomMaso</li>
						<li>CloudMaso</li>
						<li>DomainMaso</li>
					</ul>
				</div>
				<div class="uk-width-1-6@m uk-grid-margin uk-first-column">
					<h5>Azienda</h5>
					<ul class="uk-list">
						<li>Team</li>
						<li>Dicono di noi</li>
						<li>Contattaci</li>
					</ul>
				</div>			
			</div>
		</div>
		
		<div class="uk-text-center uk-padding uk-padding-remove-horizontal">
			<span class="uk-text-small uk-text-muted">© 2021 TomMASO - <a href="#">Creato da P.A.V.A. Studios</a></span>
		</div>
	</footer>

	<!-- UIkit JS -->
	<script src="https://cdn.jsdelivr.net/npm/uikit@3.6.20/dist/js/uikit.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/uikit@3.6.20/dist/js/uikit-icons.min.js"></script>
</body>
</html>