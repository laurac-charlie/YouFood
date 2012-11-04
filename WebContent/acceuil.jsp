<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="com.supinfo.youfood.entity.Waiter" %>
<% 	Administrator admin = null;
	Waiter waiter = null;
	String firstName = "";
	String lastName = "";
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
	{
		admin = (Administrator)request.getSession().getAttribute("loggedUser");
		firstName = admin.getFirstName();
		lastName = admin.getLastName();
	}
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Waiter) 
	{
		waiter = (Waiter)request.getSession().getAttribute("loggedUser");
		firstName = waiter.getFirstName();
		lastName = waiter.getLastName();
	}
%>
<!DOCTYPE html >
<html>
	<head>
		<title>YouFood - Acceuil</title>
		<link rel="stylesheet" type="text/css" href="style/style.css" />
		<script type="text/javascript" src="script_js/js/jquery.js"></script>
		<script type="text/javascript">
			$(function(){
				$("select").change(function(){
					$(location).attr("href","<%= request.getContextPath() %>" + "/restaurant/description.jsp?idRes=" + $("#list_restaurants option:selected").attr("value"));
				});
				<% if(request.getSession().getAttribute("first") == "first"){ %>
					$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
				<% request.getSession().setAttribute("first",null);} %>
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
			<br>
			<h3> Bienvenue sur le site de gestion de YouFood Inc. !</h3> <br>
			Grâce à ce portail, vous allez pouvoir gérer vos différents restaurants.<br>
			Vous allez aussi pouvoir étudier et ajuster votre stratégie à partir des données collectées concernant le fonctionnement des restaurants. <br><br>
			Choisissez un restaurant pour avoir une description &nbsp; :
			<select name="list_restaurants" id="list_restaurants" > 
				<option value="default"> -- Restaurant -- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</option>
				<c:forEach items="${list_restaurants}" var="restaurant">
					<option value="${restaurant.id}"><c:out value="${restaurant.name}"/></option>
				</c:forEach>
			</select><br><br>
			Si vous avez déjà votre compte, veuillez <a href="<%= contextPath %>/login">vous connectez</a> pour avoir accès aux fonctions avancées relative à votre compte. <br>
			Si vous n'avez pas de compte, nous vous prions de contacter un administrateur afin qu'il vous en fournisse un.<br><br>
		</section>
		<section id="message">
			<c:if test="${not empty loggedUser }">
				<div>Bonjour <%= lastName %> <%= firstName %>, vous êtes maintenant connecté.</div>
			</c:if>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>