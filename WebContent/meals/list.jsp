<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.MealDAO" %>
<%@page import="com.supinfo.youfood.entity.Meal" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="java.util.List" %>
<% 
	request.setAttribute("meals", MealDAO.getInstance().getAllMeal());
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
		request.setAttribute("admin", "admin");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Youfood - Liste des plats</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript">
			$(document).ready( function() 
			{
				//Delete Link click
				$(".button_close").click(function()
				{
					var id = $(this).attr("id");
					var url = "delete";
					url = "<%= request.getContextPath() %>" + "/meals/delete";
					$.post( url, { "mealId" : id},function( data ){
								alert("Le plat a été supprimé.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/meals/list.jsp");
						});
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
			<c:choose>
				<c:when test="${empty meals}">
					<p align="center">Aucun restaurant à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty admin}">
						<br/>
						<a href="<%= request.getContextPath() %>/meals/new">&nbsp;&nbsp;&nbsp;&nbsp;Ajouter un plat</a>
						<br/>
						<br/>
					</c:if>
					<c:if test="${not empty admin and not empty meals}">
					<table class="display_table" id="meal_table">
						<tr id="title">
							<th>Id</th>
							<th>Nom</th>
							<th>Prix</th>
							<th>Disponible</th>
							<th>Dernière modification</th>
						</tr>
						<c:forEach items="${meals}" var="meal">
							<tr id="${meal.id}">
								<td><c:out value="${meal.id}"/></td>
								<td><c:out value="${meal.name}"/></td>
								<td><c:out value="${meal.price}"/></td>
								<td><c:out value="${meal.available}"/></td>
								<td><c:out value="${meal.lastModification}"/></td>
								<td id="button">
									<a class="button_view" href="description.jsp?idMeal=${meal.id}" title="voir ou modifier"></a>
									<a class="button_close" href="#" title="supprimer" id="${meal.id}"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					</c:if>
					<br/>
				</c:otherwise>
			</c:choose>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>