<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html>
	<head>
		<title>YouFood - Ajouter un restaurant</title>
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
				
				if($("#adresse").val() == "")
				{
					$("#adresse").css("background-color","#F3C200");
					$("#adresse").after("<font color=\"red\"> Vous devez rentrer une adresse valide.</font>");
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
					<label for="adress">Adresse :</label><textarea id="adresse" name="adress" rows="3" cols="50" onfocus="javascript:couleur(this);" /></textarea>
				</div>
				<div>
					<label for="gerant">Nom du gérant :</label><input type="text" id="gerant" name="gerant" maxlength="34" size="20" onfocus="javascript:couleur(this);"  />
				</div>
				<div>
					<label for="description">Description :</label><textarea id="description" name="description" rows="4" cols="50"></textarea>
				</div>
				<div>
					<label for="nb_seat">Nombre de sièges :</label>
					<select name="nb_seat" >
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="15">15</option>
						<option value="20">20</option>
						<option value="25">25</option>
						<option value="30">30</option>
						<option value="35">35</option>
						<option value="40">40</option>
						<option value="45">45</option>
						<option value="50">50</option>
						<option value="55">55</option>
						<option value="60">60</option>
						<option value="65">65</option>
						<option value="60">70</option>
					</select>
				</div>
				
				<div>
					<label for="submit">&nbsp;</label><input type="submit" name="submit" value="Submit"  />
				</div>
			</form>
		</section>
		<section id="message">
				<div>Le restaurant n'a pas pu être correctement enregistré. Veuillez réessayer.</div>
		</section>
	<%@ include file="/template/footer.jsp" %>
	</body>
</html>