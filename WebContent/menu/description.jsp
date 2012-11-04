<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.MenuDAO" %>
<%@page import="com.supinfo.youfood.entity.Menu" %>
<%@page import="com.supinfo.youfood.dao.MealDAO" %>
<%@page import="com.supinfo.youfood.entity.Meal" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<% 
	int id = 0;
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
	{
		request.setAttribute("admin", "admin");
		request.setAttribute("list_meals",MealDAO.getInstance().getAllMeal() );
		
	}
	if(request.getParameter("idMenu") != null)
	{
		try
		{
		id = Integer.parseInt(request.getParameter("idMenu").toString());
		}
		catch(Exception e)
		{id = 0;}
		Menu theMenu = MenuDAO.getInstance().findId(id);
		request.setAttribute("menu",theMenu);
		request.setAttribute("menu_meals", theMenu.getMeals());
	}
	
%>
<!DOCTYPE html>
<html>
	<head>
		<title>YooFood - Description du menu</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">
		function check()
		{
			var sucess = true;
			$("font").remove();
			if($("#nom").val() == "")
			{
				$("#nom").css("background-color","#F3C200");
				$("#nom").after("<font color=\"red\"> Vous devez rentrer un nom pour le menu.</font>");
				sucess = false;
			}
			return sucess;
		}
		
		$(document).ready( function() 
		{
			$(".button_refresh").click(function()
			{
				if(check())
				{
					var nom = $("#nom").val();
					var url = "update";
					url = "<%= request.getContextPath() %>" + "/menu/update?type=nom";
					$.post( url, { "menuId" : "${menu.id}" , "nom" : nom},function( data ){
						$("#message").html(data);
							$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
						});
				}
			});
			
			$(".button_add").click(function()
			{
				var id = $("#list_meals option:selected").attr("value");
				if(id != 0)
				{
				var url = "update";
				url = "<%= request.getContextPath() %>" + "/menu/update?type=add";
				$.post( url, { menuId : "${menu.id}","mealId" : id },function( data ){
						//var content = $(data);
						$("#message").empty().append($(data).filter("div"));
						$("#message").animate({height: "toggle"},1000,function(){
							$("#message").delay(3000).animate({height: "toggle"},1000,function(){location.reload();});
							});
						//$("tr:last").html($(data));
						//$("tr:last").after($("tr"));
					});
				}
			});
			
			$(".button_close").click(function()
			{
				var id = $(this).attr("id");
				var url = "update";
				url = "<%= request.getContextPath() %>" + "/menu/update?type=delete";
				$.post( url, { menuId : "${menu.id}", "mealId" : id},function( data ){
						$("#message").html(data);
						$("#message").animate({height: "toggle"},1000,function(){
							$("#message").delay(3000).animate({height: "toggle"},1000,function(){location.reload();});
							});
					});
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
				<c:when test="${empty menu}">
					<p align="center">Aucun menu à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty admin }">
					<div>
						<label for="nom">Nom du menu :</label><input type="text" id="nom" name="nom" maxlength="34" size="20" value="${menu.name }" onfocus="javascript:couleur(this);" >&nbsp;&nbsp;<a href="#" id="${menu.id }" class="button_refresh"></a>
					</div>
					<div>
						<label for="meal"> Ajouter un plat : </label>
						<select name="list_meals" id="list_meals" > 
							<option value="0"> -- Plat -- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</option>
							<c:forEach items="${list_meals}" var="meal">
								<option value="${meal.id}"><c:out value="${meal.name}"/></option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;<a href="#" id="add" class="button_add"></a>
					</div>
					</c:if>
					<c:if test="${empty admin }">
						<div><label for="nom">Nom du menu :</label> <c:out value="${menu.name}"></c:out></div>
					</c:if>
					<br/>
					<div>Liste des plats du menus :</div>
					<br/>
					<table class="display_table" id="meal_table">
						<tr id="title">
							<th>Nom</th>
							<th>Prix</th>
							<th>Disponible</th>
							<th>Dernière modification</th>
						</tr>
						<c:forEach items="${menu_meals}" var="meal">
							<tr id="${meal.id}">
								<td><c:out value="${meal.name}"/></td>
								<td><c:out value="${meal.price}"/></td>
								<td><c:out value="${meal.available}"/></td>
								<td><c:out value="${meal.lastModification}"/></td>
								<c:if test="${not empty admin }">
									<td id="button">
										<a class="button_view" href="../meals/description.jsp?idMeal=${meal.id}" target="_blank" title="voir ou modifier"></a>
										<a class="button_close" href="#" title="retirer du menu" id="${meal.id}"></a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			<br/>
		</section>
		<section id="message">
			<div>Le menu a été mis à jour.</div>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>