<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.RestaurantDAO" %>
<%@page import="com.supinfo.youfood.entity.Restaurant" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="java.util.List" %>
<% 
	request.setAttribute("restaurants", RestaurantDAO.getInstance().getAllRestaurant());
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
		request.setAttribute("admin", "admin");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Youfood - Liste des restaurants</title>
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
					url = "<%= request.getContextPath() %>" + "/restaurant/delete";
					$.post( url, { "restaurantId" : id},function( data ){
								alert("Le restaurant a été supprimé.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/restaurant/list.jsp");
						});
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
			<c:choose>
				<c:when test="${empty restaurants}">
					<p align="center">Aucun restaurant à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty admin}">
						<br/>
						<a href="<%= request.getContextPath() %>/restaurant/new">&nbsp;&nbsp;&nbsp;&nbsp;Ajouter un restaurant</a>
						<br/>
						<br/>
					</c:if>
					<table class="display_table" id="restaurant_table">
						<tr id="title">
							<c:if test="${not empty admin and not empty restaurants}">
								<th>Id</th>
							</c:if>
							<th>Nom</th>
							<c:if test="${not empty admin and not empty restaurants}">
								<th>Gérant</th>
							</c:if>
							<th>Adresse</th>
						</tr>
						<c:forEach items="${restaurants}" var="restaurant">
							<tr id="${restaurant.id}">
								<c:if test="${not empty admin and not empty restaurants}">
									<td><c:out value="${restaurant.id}"/></td>
								</c:if>
								<td><c:out value="${restaurant.name}"/></td>
								<c:if test="${not empty admin and not empty restaurants}">
									<td><c:out value="${restaurant.gerant}"/></td>
								</c:if>
									<td><c:out value="${restaurant.adress}"/></td>
								<c:if test="${not empty admin and not empty restaurants}">
									<td id="button">
										<a class="button_view" href="description.jsp?idRes=${restaurant.id}" title="voir ou modifier"></a>
										<a class="button_close" href="#" title="supprimer" id="${restaurant.id}"></a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
					<br/>
				</c:otherwise>
			</c:choose>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>