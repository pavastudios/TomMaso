<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="headTags.jsp"%>
	<!-- Identicon -->
	<script src="https://cdn.jsdelivr.net/npm/jdenticon@3.1.0/dist/jdenticon.min.js" async
        integrity="sha384-VngWWnG9GS4jDgsGEUNaoRQtfBGiIKZTiXwm9KpgAeaRn6Y/1tAFiyXqSzqC8Ga/"
        crossorigin="anonymous">
	</script>
	<link rel="stylesheet" href="navbar.css">

	<style>
		.profilepic{
			border-radius: 50%;
			border: 1px black solid;
			max-width: 70%;
		}
		.blogpic{
			border-radius: 50%;
			border: 1px black solid;
			width: 50%;
			height: 50%;
		}
		.add{
			width: 200px;
			height: 200px;
			border-radius: 50%;
			display: flex;
			align-items: center;
			justify-content: center;
		}
		.plus{
			color: whitesmoke;
			
		}
		.bio-text{
			resize: none;
			overflow: auto;
		}
		@media screen and (max-width: 600px){
			.profileside{
				max-height: 600px;
			}
		}

	</style>

	<title>TomMASO - Profilo di $UTENTE$</title>
</head>
<body>
	<!-- Barra di navigazione -->
	<nav class="uk-navbar-container uk-box-shadow-small" uk-navbar>

		<div class="uk-navbar-left">
	
			<ul class="uk-navbar-nav">
				<li class="uk-active"><a href="#"><img src="logo.png" style="max-width: 50px;" srcset=""></a></li>
				<li><a href="#" class="uk-visible@m">Home</a></li>
				<li><a href="#" class="uk-visible@m">Chi Siamo</a></li>
				<li><a href="#" class="uk-visible@m">Top Blog</a></li>
			</ul>
	
		</div>
	
		<div class="uk-navbar-right uk-visible@m">
			<ul class="uk-navbar-nav uk-visible@m">
				<li><a href="#">Sign Up</a></li>
				<li><a href="#">Login</a></li>
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

	<!-- Pagina -->
	<div class="uk-grid uk-grid-small uk-flex">
		<!-- Side bar-->
		<div class="uk-width-expand uk-box-shadow-small profileside" uk-height-viewport="expand: true;">
			<div class="uk-section uk-flex uk-flex-center uk-flex-middle uk-flex-column">
				<svg class="profilepic" data-jdenticon-value="TomMASO"></svg>
				<div class="uk-text-large uk-margin-top">
					Nome Utente
				</div>
				<span class="uk-badge">Founder</span>
				<div class="uk-text-meta uk-text-normal uk-text-break uk-text-center uk-margin-top uk-margin-bottom">Email: tommaso@pava.com</div>
				<div class="uk-text-meta uk-text-break uk-text-center uk-text-muted uk-text-italic">"Lorem, ipsum dolor sit amet consectetur adipisicing elit. Aspernatur, fuga vero a non sequi error?"</div>
				<button class="uk-button uk-button-default uk-margin-top uk-margin-small-bottom" href="#modal-profile" uk-toggle>Modifica profilo</button>
			</div>
		</div>
		
		<!-- Modale Modifica Profilo -->
		<div id="modal-profile" uk-modal>
			<div class="uk-modal-dialog">
				<button class="uk-modal-close-default" type="button" uk-close></button>
				<div class="uk-modal-header">
					<h2 class="uk-modal-title">Modifica Profilo</h2>
				</div>
				<form action="#" method="POST" class="uk-form-stacked uk-grid-small" uk-grid>
					<!-- Username -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top uk-inline">
						<div class="uk-form-controls">
							<span class="uk-form-icon uk-margin-left" uk-icon="icon: user"></span>
							<input class="uk-input uk-border-pill" id="username" type="text" placeholder="Username" name="username">
						</div>
					</div>
					<!-- Bio -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top">
						<div class="uk-form-controls">
							<label class="uk-form-label uk-text-muted" for="bio-text">Bio</label>
							<textarea id="#bio-text" class="uk-textarea bio-text"></textarea>
						</div>
					</div>
					<!-- Foto profilo -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top">
						<div uk-form-custom>
							<label for="pro-pic" class="uk-text-muted uk-form-label">Seleziona l'immagine</label>
							<input accept="image/*" type="file" id="#pro-pic">
							<button class="uk-button uk-button-default" type="button" tabindex="-1">...</button>
						</div>
					</div>
					<!-- Vecchia Psw -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top uk-inline">
						<div class="uk-form-controls">
							<span class="uk-form-icon uk-margin-left" uk-icon="icon: unlock"></span>
							<input class="uk-input uk-border-pill" id="oldpsw" type="password" placeholder="Vecchia password" name="oldpsw">
						</div>
					</div>
					<!-- Nuova Psw -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top uk-inline">
						<div class="uk-form-controls">
							<span class="uk-form-icon uk-margin-left" uk-icon="icon: lock"></span>
							<input class="uk-input uk-border-pill" id="newpsw1" type="password" placeholder="Nuova password" name="newpsw1">
						</div>
					</div>
					<!-- Ripeti psw -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top uk-inline uk-margin-bottom">
						<div class="uk-form-controls">
							<span class="uk-form-icon uk-margin-left" uk-icon="icon: lock"></span>
							<input class="uk-input uk-border-pill" id="newpsw2" type="password" placeholder="Ripeti nuova password" name="newpsw2">
						</div>
					</div>
					<div class="uk-text-right uk-margin-right uk-margin-small-bottom uk-margin-auto-left">
						<button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
						<input class="uk-button uk-button-primary" value="Salva" type="submit"></button>
					</div>
				</form>		
			</div>
		</div>

		<!-- Blogs -->
		<div class="uk-width-3-4@m uk-width-3-4@l uk-width-1-1@s">
			<div id="app" class="uk-grid-small uk-animation-fade uk-flex-middle uk-child-width-1-9 uk-child-width-1-2@m uk-child-width-1-3@l uk-padding-small" uk-grid uk-height-match="target: > div > .uk-card">
				
				<!-- Card Blog Esistente-->
				<div>
					<div class="uk-card uk-card-default uk-card-hover card">
						<div class="uk-card-header">
							<div class="uk-grid-small uk-flex-middle" uk-grid>
								<div class="uk-card-media-top uk-flex uk-flex-center ">
									<img src="logo.png" class="blogpic" alt="" uk-img>
								</div>							
							<div class="uk-width-expand">
								<h3 class="uk-card-title uk-margin-remove-bottom uk-flex uk-flex-center uk-flex uk-flex-center">Blog #1</h3>
								<p class="uk-text uk-margin-remove"><time datetime="2016-04-01T19:00">Blog generico</time></p>
								<p class="uk-text-meta uk-margin-remove-top"><time datetime="2016-04-01T19:00">Statistica1: 0, Statistica2: 0</time></p>
							</div>
							</div>
						</div>
						<div class="uk-card-footer boundary1">
							<div class="uk-inline">
								<a href="#" class="uk-button uk-button-text" type="button">Impostazioni</a>
								<div uk-dropdown="offset: 0; mode: click; pos: bottom-left; boundary: .boundary1; boundary-align: true; animation: uk-animation-slide-top">
									<ul class="uk-nav uk-dropdown-nav">
										<li><a href="#" style="color: #41b3a3;"><span uk-icon="code"></span> Gestisci</a></li>
										<li><a href="#" style="color: #e8a87c"><span uk-icon="pencil"></span> Rinomina</a></li>
										<li><a href="#" style="color: #c38d9e;"><span uk-icon="trash"></span> Elimina</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- Card Nuovo Blog-->
				<div>
					<div class="uk-card uk-card-primary uk-card-hover uk-height-expand uk-flex uk-flex-center uk-flex-middle">
						<div class="uk-card-header">
							<div class="uk-grid-small uk-flex-middle" uk-grid>
								<div class="uk-card-media-top uk-flex uk-flex-center">
								</div>							
							<div class="uk-width-expand">
								<h3 class="uk-card-title uk-margin-remove-bottom uk-flex uk-flex-center uk-flex-middle uk-margin-medium-top"><a href="#new-blog" uk-toggle><span uk-icon="icon: plus; ratio: 5;" class="plus"></span></a></h3>
								<a class="uk-button uk-button-text uk-flex uk-flex-center uk-text-lead uk-margin-medium-top uk-text-bold" href="#new-blog" uk-toggle>Nuovo Blog</a>
							</div>
							</div>
						</div>
					</div>
					
					<!-- Modale nuovo blog -->
					<div id="new-blog" uk-modal>
						<div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
							<h2 class="uk-modal-title">Nuovo Blog</h2>
							<form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
								<div class="uk-width-1-1 uk-margin-top uk-inline">
									<div class="uk-form-controls">
										<span class="uk-form-icon uk-margin-medium-left" uk-icon="icon: pencil"></span>
										<input class="uk-input uk-border-pill" id="blogname" type="text" placeholder="Nome del blog" name="blogname">
									</div>
								</div>
								<div class="uk-text-right uk-margin-right uk-margin-auto-left">
									<button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
									<input class="uk-button uk-button-primary" value="Crea" type="submit"></button>
								</div>
							</form>
						</div>
					</div>

				</div>
				<!-- /Card Nuovo Blog/-->

			</div>
		</div>
	</div>
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
</body>
</html>