<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.AdministratorDAO" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="com.supinfo.youfood.dao.WaiterDAO" %>
<%@page import="com.supinfo.youfood.entity.Waiter" %>
<%@page import="java.util.List" %>
<% 
	request.setAttribute("administrators", AdministratorDAO.getInstance().getAllAdministrator());
	request.setAttribute("waiters", WaiterDAO.getInstance().getAllWaiter());
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
		request.setAttribute("admin", "admin");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Youfood - Liste des utilisateurs</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript">
			$(document).ready( function() 
			{
				//Delete Link click
				$(".button_close").click(function()
				{
					var id = $(this).attr("id");
					var type = $(this).attr("accesskey");
					var url = "delete";
					url = "<%= request.getContextPath() %>" + "/users/delete";
					$.post( url, { "userId" : id, "type" : type },function( data ){
								alert("L'utilisateur a été supprimé.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/users/list.jsp");
						});
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
		<c:if test="${not empty admin}">
			<br/>
			<a href="<%= request.getContextPath() %>/users/new">&nbsp;&nbsp;&nbsp;&nbsp;Ajouter un utilisateur</a>
			<br/>
			<c:choose>
				<c:when test="${empty administrators}">
					<p align="center">Aucun administrateur à afficher.</p>
				</c:when>
				<c:otherwise>
					<h4 style="text-align : left">Adminsitrateur</h4><br/>
					<table class="display_table" id="admin_table">
						<tr id="title">
							<th>Id</th>
							<th>Nom</th>
							<th>Prénom</th>
							<th>Login</th>
						</tr>
						<c:forEach items="${administrators}" var="administrator">
							<tr id="${administrator.id}">
								<td><c:out value="${administrator.id}"/></td>
								<td><c:out value="${administrator.lastName}"/></td>
								<td><c:out value="${administrator.firstName}"/></td>
								<td><c:out value="${administrator.login}"/></td>
								<td id="button">
									<a class="button_view" href="description.jsp?idAdmin=${administrator.id}" title="voir ou modifier"></a>
									<c:if test="${administrator.id != 1}"><a class="button_close" href="#" title="supprimer" accesskey="a" id="${administrator.id}"></a></c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${empty waiters}">
					<p align="center">Aucun serveur à afficher.</p>
				</c:when>
				<c:otherwise>
					<h4 style="text-align: left;">Serveurs</h4><br/>
					<table class="display_table" id="waiter_table">
						<tr id="title">
							<th>Id</th>
							<th>Nom</th>
							<th>Prénom</th>
							<th>Login</th>
							<th>Restaurant</th>
						</tr>
						<c:forEach items="${waiters}" var="waiter">
							<tr id="${administrator.id}">
								<td><c:out value="${waiter.id}"/></td>
								<td><c:out value="${waiter.lastName}"/></td>
								<td><c:out value="${waiter.firstName}"/></td>
								<td><c:out value="${waiter.login}"/></td>
								<td><c:out value="${waiter.theRestaurant.name }"/></td>
								<td id="button">
									<a class="button_view" href="description.jsp?idWaiter=${waiter.id}" title="voir ou modifier"></a>
									<a class="button_close" href="#" title="supprimer" accesskey="w" id="${waiter.id}"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					<br/>&nbsp;
				</c:otherwise>
			</c:choose>
		</c:if>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>