<%@ page import="com.pavastudios.TomMaso.model.Blog" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="general/headTags.jsp"%>

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
	<%@include file="general/navbar.jsp"%>
	<%
		Utente login=ses.getUtente();
		Utente user= (Utente) request.getAttribute("user");
		List<Blog> blogs= (List<Blog>) request.getAttribute("blogs");
		boolean owner=user.equals(login);
	%>
	<!-- Pagina -->
	<div class="uk-grid uk-grid-small uk-flex">
		<!-- Side bar-->
		<div class="uk-width-expand uk-box-shadow-small profileside" uk-height-viewport="expand: true;">
			<div class="uk-section uk-flex uk-flex-center uk-flex-middle uk-flex-column">
				<svg class="profilepic" data-jdenticon-value="<%=user.getUsername()%>>"></svg>
				<div class="uk-text-large uk-margin-top">
					<%=user.getUsername()%>
				</div>
				<%if(user.getIsAdmin()){%>
					<span class="uk-badge">Admin</span>
				<%}%>
				<div class="uk-text-meta uk-text-normal uk-text-break uk-text-center uk-margin-top uk-margin-bottom">Email: <%=user.getEmail()%></div>
				<div class="uk-text-meta uk-text-break uk-text-center uk-text-muted uk-text-italic">"<%=user.getBio().isEmpty()%>"</div>
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
							<input class="uk-input uk-border-pill" id="username" type="text" placeholder="Username" name="username" value="<%=user.getUsername()%>">
						</div>
					</div>
					<!-- Bio -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top">
						<div class="uk-form-controls">
							<label class="uk-form-label uk-text-muted" for="bio-text">Bio</label>
							<textarea id="bio-text" class="uk-textarea bio-text"><%=user.getBio()%></textarea>
						</div>
					</div>
					<!-- Foto profilo -->
					<div class="uk-width-1-1 uk-margin-left uk-margin-right uk-margin-top">
						<div uk-form-custom>
							<label for="pro-pic" class="uk-text-muted uk-form-label">Seleziona l'immagine</label>
							<input accept="image/*" type="file" id="pro-pic">
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
				<%for(Blog blog:blogs){%>
				<div>
					<div class="uk-card uk-card-default uk-card-hover card">
						<div class="uk-card-header">
							<div class="uk-grid-small uk-flex-middle" uk-grid>
								<div class="uk-card-media-top uk-flex uk-flex-center ">
									<svg class="blogpic" data-jdenticon-value="<%=blog.getNome()%>>" uk-img></svg>
								</div>							
							<div class="uk-width-expand">
								<h3 class="uk-card-title uk-margin-remove-bottom uk-flex uk-flex-center uk-flex uk-flex-center"><%=blog.getNome()%></h3>
								<!--p class="uk-text uk-margin-remove"><time datetime="2016-04-01T19:00">Blog generico</time></p>
								<p class="uk-text-meta uk-margin-remove-top"><time datetime="2016-04-01T19:00">Statistica1: 0, Statistica2: 0</time></p-->
							</div>
							</div>
						</div>
						<%if(owner){%>
						<div class="uk-card-footer boundary<%=blog.getIdBlog()%>">
							<div class="uk-inline">
								<a href="#" class="uk-button uk-button-text" type="button">Impostazioni</a>
								<div uk-dropdown="offset: 0; mode: click; pos: bottom-left; boundary: .boundary<%=blog.getIdBlog()%>; boundary-align: true; animation: uk-animation-slide-top">
									<ul class="uk-nav uk-dropdown-nav">
										<li><a href="${pageContext.request.contextPath}/blog-manage/<%=blog.getNome()%>" style="color: #41b3a3;"><span uk-icon="code"></span> Gestisci</a></li>
										<li><a href="#rename-blog" blog-name="<%=blog.getNome()%>" class="rename-link" style="color: #e8a87c" uk-toggle><span uk-icon="pencil"></span> Rinomina</a></li>
										<li><a href="#delete-blog" style="color: #c38d9e;" blog-name="<%=blog.getNome()%>" class="finder" uk-toggle><span uk-icon="trash"></span> Elimina</a></li>
									</ul>
								</div>
							</div>
						</div>
						<%}%>
					</div>
				</div>
				<%}%>
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

					<!-- Modale rinomina blog -->
					<div id="rename-blog" uk-modal>
						<div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
							<h2 class="uk-modal-title">Rinomina blog</h2>
							<form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
								<div class="uk-width-1-1 uk-margin-top uk-inline">
									<div class="uk-form-controls">
										<span class="uk-form-icon uk-margin-medium-left" uk-icon="icon: pencil"></span>
										<input type="text" id="renameFormHid" name="from-name" hidden>
										<input class="uk-input uk-border-pill" id="renameForm" name="to-name" type="text" placeholder="Nuovo nome del blog" name="blogname">

									</div>
								</div>
								<div class="uk-text-right uk-margin-right uk-margin-auto-left">
									<button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
									<input class="uk-button uk-button-primary" value="Crea" id="rename-blog-ok" type="button">
								</div>
							</form>
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
									<input class="uk-button uk-button-primary" value="Crea" id="createBlog" type="button"></button>
								</div>
							</form>
						</div>
					</div>
				</div>

				<!-- Modale eliminazione -->
				<div id="delete-blog" uk-modal>
					<div class="uk-modal-dialog uk-modal-body uk-margin-auto-vertical">
						<h2 class="uk-modal-title">Eliminare definitivamente '<span id="deleteName"></span>'?</h2>
						<form class="uk-form-stacked uk-grid" method="POST" action="#" uk-grid>
							<div class="uk-text-right uk-margin-right uk-margin-auto-left">
								<button class="uk-button uk-button-default uk-modal-close" type="button">Esci</button>
								<input type="text" id="confirm-delete-hid" hidden>
								<input class="uk-button uk-button-primary" value="Elimina" id="confirm-delete" type="button">
							</div>
						</form>
					</div>
				</div>


			</div>
		</div>
	</div>

	<%@include file="general/footer.jsp"%>
	<%@include file="general/tailTag.jsp"%>

	<script>
		$(".rename-link").click(function () {
			$("#renameFormHid").val($(this).attr("blog-name"));
			$("#renameForm").val("");
		});
		$( "#rename-blog-ok" ).click(function() {
			var oldname=$("#renameFormHid").val();
			var newname=$("#renameForm").val();
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/api/blog/rename',
				data: {
					"from-name": oldname,
					"to-name": newname
				},
				success: function (data) {
					console.log(data);
					if(data["error"]===undefined)
						location.reload();
				}
			});
		});
		$( "#createBlog" ).click(function() {
			var blogname=$("#blogname").first().val();
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/api/blog/create',
				data: { name: blogname },
				success: function (data) {
					console.log(data);
					if(data["error"]===undefined)
						location.reload();
				}
			});
		});
		$(".finder").click(function(){
			$("#deleteName").text($(this).attr("blog-name"))
			$("#confirm-delete-hid").text($(this).attr("blog-name"))
		});
		$("#confirm-delete").click(function (){

			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/api/blog/delete-blog',
				data: { "blog-name": $("#confirm-delete-hid").text() },
				success: function (data) {
					console.log(data);
					if(data["error"]===undefined)
						location.reload();
				}
			});
		});
	</script>
</body>
</html>