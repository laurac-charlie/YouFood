<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
	<head>
		<title>YouFood - Ajouter un plat</title>
		<link type="text/css" href="../style/style.css" rel="stylesheet" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">
			function check() 
			{
				var sucess = true;
				$("font").remove();
				$("input").each(function(){
					if($(this).val() == "")
					{
						$(this).css("background-color","#F3C200");
						$(this).after("<font color=\"red\"> Vous devez rentrer un "+$(this).attr("name")+".</font>");
						sucess = false;
					}
					
				});
				
				if($("#description").val() == "")
				{
					$("#description").css("background-color","#F3C200");
					$("#description").after("<font color=\"red\"> Vous devez rentrer une description.</font>");
					sucess = false;
				}
				
				if(isNaN($("#prix").val()))
				{
					$("#prix").css("background-color","#F3C200");
					$("#prix").after("<font color=\"red\"> Vous devez rentrer un prix valide. ex : 39.9</font>");
					sucess = false;
				}
				
				return sucess;
			}
			$(function(){
				<% boolean fail = false; %>
				<% if(request.getAttribute("fail") != null) { %>
				$("#message").animate({height: "toggle"},1000).delay(4000).slideUp(1000);
				<%fail = true;}%>
			});
			window.onload = function () {document.forms['formulaire'].onsubmit = check;};
			
		</script>
	</head>
	<body>
	<%@ include file="/template/header.jsp" %>
		<section id="content">
			<form class="create-form"  action="new" method="post" name="formulaire" >
				<div>
					<label for="name">Nom :</label><input type="text" id="name" name="nom" maxlength="64" size="48" autofocus onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<label for="description">Description :</label><textarea id="description" name="description" rows="4" cols="50" onfocus="javascript:couleur(this);"></textarea>
				</div>
				<div>
					<label for="prix">Prix :</label><input id="prix" name="prix" maxlength="7" size="6" onfocus="javascript:couleur(this);"/>
				</div>
				<div>
					<label for="disponible">Disponible :</label><input type="checkbox" value="disponible" id="disponible" name="disponible" checked/>
				</div>
				<div>
					<label for="submit">&nbsp;</label><input type="submit" name="submit" value="Submit"  />
				</div>
			</form>
		</section>
		<section id="message">
			<div>Le plat n'a pas pu être correctement enregistré. Veuillez réessayer.</div>
		</section>
	<%@ include file="/template/footer.jsp" %>
	</body>
</html>