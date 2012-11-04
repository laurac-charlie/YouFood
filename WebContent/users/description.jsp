<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.RestaurantDAO" %>
<%@page import="com.supinfo.youfood.entity.Restaurant" %>
<%@page import="com.supinfo.youfood.entity.Waiter" %>
<%@page import="com.supinfo.youfood.dao.WaiterDAO" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="com.supinfo.youfood.dao.AdministratorDAO" %>
<% 
	int id = 0;
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
	{
		request.setAttribute("admin", "admin");
		request.setAttribute("waiter",null);
		request.setAttribute("theAdmin",null);
		if(request.getParameter("idWaiter") != null)
		{
			try
			{
				id = Integer.parseInt(request.getParameter("idWaiter").toString());
			}
			catch(Exception e)
			{id = 0;}
			Waiter theWaiter = WaiterDAO.getInstance().findId(id);
			request.setAttribute("waiter",theWaiter);
			request.setAttribute("list_restaurants", RestaurantDAO.getInstance().getAllRestaurant());
		}
		if(request.getParameter("idAdmin") != null)
		{
			try
			{
			id = Integer.parseInt(request.getParameter("idAdmin").toString());
			}
			catch(Exception e)
			{id = 0;}
			Administrator theAdmin = AdministratorDAO.getInstance().findId(id);
			request.setAttribute("theAdmin",theAdmin);
		}
	}
	
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>YooFood - Description de l'utilisateur</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
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
						$(this).after("<font color=\"red\"> Vous devez rentrer un "+$(this).attr("name")+".</font>");
						sucess = false;
					}
				});
				
				if($("#password").val().length < 5)
				{
					$("#password").css("background-color","#F3C200");
					$("#password").after("<font color=\"red\"> Vous devez entrer un mot de passe d'au moins 5 caractères.</font>");
					sucess = false;
				}
				
				if($("#login").val().length < 5)
				{
					$("#login").css("background-color","#F3C200");
					$("#login").after("<font color=\"red\"> Vous devez entrer un login d'au moins 5 caractères.</font>");
					sucess = false;
				}
				
				return sucess;
			}
			
			$(document).ready( function() 
			{
				$("select>option[value=${waiter.theRestaurant.id}]").attr("selected","selected");
				
				$(".button_refresh").click(function()
				{
					if(check())
					{
						if($(this).is("div#admin > a"))
						{
							var url = "update";					
							url = "<%= request.getContextPath() %>" + "/users/update";
							$.post( url, { "adminId" : "${theAdmin.id}",
										   "firstName" : $("#prenom").val(),
										   "lastName" : $("#nom").val(),
										   "login" : $("#login").val(),
										   "password" : $("#password").val()},function( data ){
									$("#message").html(data);
									$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
								});
						}
						if($(this).is("div#waiter > a"))
						{
							var url = "update";					
							url = "<%= request.getContextPath() %>" + "/users/update";
							$.post( url, { "waiterId" : "${waiter.id}",
										   "firstName" : $("#prenom").val(),
										   "lastName" : $("#nom").val(),
										   "login" : $("#login").val(),
										   "password" : $("#password").val(),
										   "resId" : $("select>option:selected").attr("value")},function( data ){
									$("#message").html(data);
									$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
								});
						}
					}
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
			<br/>
			<a href="list.jsp">&nbsp;&nbsp;&nbsp;•&nbsp;Retourner à la liste</a>
			<c:choose>
				<c:when test="${(empty waiter and empty theAdmin) or empty admin }">
					<p align="center">Aucun utilisateur à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty waiter}">
						<div>
							<label for="nom">Nom :</label><input type="text" id="nom" name="nom" maxlength="64" size="48" value="${waiter.lastName }" autofocus onfocus="javascript:couleur(this);" />
						</div>
						<div>
							<label for="prenom">Prénom :</label><input type="text" id="prenom" name="prenom" size="48" value="${waiter.firstName }" onfocus="javascript:couleur(this);" />
						</div>
						<div id="diplayable">
							<label for="restaurant">Restaurant où il travaille :</label>
							<select name="restaurant" id="list_restaurants" > 
								<option value="0">&nbsp; -- Aucun -- &nbsp;&nbsp;&nbsp;</option>
								<c:forEach items="${list_restaurants}" var="restaurant">
									<option value="${restaurant.id}"><c:out value="${restaurant.name}"/></option>
								</c:forEach>
							</select>
						</div>
						<div>
							<label for="login">Login :</label><input type="text" id="login" name="login" maxlength="64" size="48" value="${waiter.login }" onfocus="javascript:couleur(this);"  />
						</div>
						<div>
							<label for="password">Password :</label><input type="password" id="password" name="password" maxlength="32" size="48" value="${waiter.password }" onfocus="javascript:couleur(this);" />
						</div>				
						<div id="waiter"><label for="actualiser">&nbsp;</label>&nbsp;&nbsp;<a href="#" id="${waiter.id }" class="button_refresh" title="actualiser"></a></div>
					</c:if>
					<c:if test="${not empty theAdmin}">
						<div>
							<label for="nom">Nom :</label><input type="text" id="nom" name="nom" maxlength="64" size="48" value="${theAdmin.lastName }" autofocus onfocus="javascript:couleur(this);" />
						</div>
						<div>
							<label for="prenom">Prénom :</label><input type="text" id="prenom" name="prenom" size="48" value="${theAdmin.firstName }" onfocus="javascript:couleur(this);" />
						</div>
						<div>
							<label for="login">Login :</label><input type="text" id="login" name="login" maxlength="64" size="48" value="${theAdmin.login }" onfocus="javascript:couleur(this);"  />
						</div>
						<div>
							<label for="password">Password :</label><input type="password" id="password" name="password" maxlength="32" size="48" value="${theAdmin.password }" onfocus="javascript:couleur(this);" />
						</div>				
						<div id="admin"><label for="actualiser">&nbsp;</label>&nbsp;&nbsp;<a href="#" id="${theAdmin.id }" class="button_refresh" title="actualiser"></a></div>
					</c:if>
					<br/>
				</c:otherwise>
			</c:choose>
			<br/>
		</section>
		<section id="message">
			<div>Le restaurant a été mis à jour.</div>
		</section>
		<%@ include file="/template/footer.jsp" %>	
	</body>
</html>