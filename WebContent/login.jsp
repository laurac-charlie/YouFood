<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="style/style.css" />
		<script type="text/javascript" src="script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">
			function check() 
			{
				var msg = "";
			 	
				$("font").remove();
				if ($("#login").val() == "")	
				{
						$("#login").css("background-color","#F3C200");
						$("#login").after("<font color=\"red\"> Vous devez entrer un login.</font>");
						msg += "fail";
				}
				
				if ($("#password").val() == "")
				{
					$("#password").css("background-color","#F3C200");
					$("#password").after("<font color=\"red\"> Vous devez entrer un mot de passe.</font>");
					msg += "fail";
				}

				if(msg == "")
					return true;
				else
					return false;
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
			<br>
			<form class="create-form"  action="login" method="post" name="formulaire" >
				<div>
					<label for="login">Login:</label><input type="text" id="login" name="login" maxlength="64" autofocus onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<label for="password">Password :</label><input type="password" id="password" name="password" maxlength="32" onfocus="javascript:couleur(this);" />
				</div>
				<div>
					<input type="submit" name="submit" value="Submit"/>
				</div>
			</form>
			<br><br>
		</section>
		<section id="message">
			<div>Votre login ou votre mot de passe est erroné.</div>
		</section>
	<%@ include file="/template/footer.jsp" %>
	</body>
</html>