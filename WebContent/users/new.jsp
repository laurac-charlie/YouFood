<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.RestaurantDAO" %>
<%@page import="com.supinfo.youfood.entity.Restaurant" %>
<%
	request.setAttribute("restaurants", RestaurantDAO.getInstance().getAllRestaurant());
%>
<!DOCTYPE html >
<html>
	<head>
		<title>YouFood - Ajouter un utilisateur</title>
		<link type="text/css" href="../style/style.css" rel="stylesheet" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">			
			function check() 
			{
				var sucess = true;
				$("font").remove();
				$("input").each(function(){
					if($(this).val() == "" && $(this) != $("#password"))
					{
						$(this).css("background-color","#F3C200");
						$(this).after("<font color=\"red\"> Vous devez entrer un "+$(this).attr("name")+".</font>");
						sucess = false;
					}
				});
				
				if ($("#password").val() == "")
				{
					$("#password").css("background-color","#F3C200");
					$("#password").after("<font color=\"red\"> Votre mot de passe est incorrect.</font>");
					sucess = false;
				}
				
				return sucess;
			}
			$(function(){
				$("#statut").change(function(){
					if($("#statut option:selected").attr("value") != "admin")
						$("#diplayable").show();
					else
						$("#diplayable").hide();
				});
				
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
					<label for="nom">Nom :</label><input type="text" id="nom" name="nom" maxlength="64" size="48" autofocus onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<label for="prenom">Prénom :</label><input type="text" id="prenom" name="prenom" size="48" onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<label for="statut">Statut :</label>
					<select name="statut" id ="statut">
						<option value="waiter">Serveur</option>
						<option value="admin">Administrateur</option>
					</select>
				</div>
				<div id="diplayable">
					<label for="restaurant">Restaurant où il travaille :</label>
					<select name="restaurant" id="list_restaurants" > 
						<option value="0">&nbsp; -- Aucun -- &nbsp;&nbsp;&nbsp;</option>
						<c:forEach items="${restaurants}" var="restaurant">
							<option value="${restaurant.id}"><c:out value="${restaurant.name}"/></option>
						</c:forEach>
					</select>
				</div>
				<div>
					<label for="login">Login :</label><input type="text" id="login" name="login" maxlength="64" size="48" onfocus="javascript:couleur(this);"  />
				</div>
				<div>
					<label for="password">Password :</label><input type="password" id="password" name="password" maxlength="32" size="48" onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<label for="submit">&nbsp;</label><input type="submit" name="submit" value="Submit"  />
				</div>
			</form>
		</section>
		<section id="message">
				<div>L'utilisateur n'a pas pu être correctement enregistré. Veuillez réessayer.</div>
		</section>
	<%@ include file="/template/footer.jsp" %>
	</body>
</html>