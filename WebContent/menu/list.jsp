<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.MenuDAO" %>
<%@page import="com.supinfo.youfood.entity.Menu" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="java.util.List" %>
<% 
	request.setAttribute("menus", MenuDAO.getInstance().getAllMenu());
	request.setAttribute("current", MenuDAO.getInstance().getCurrent());
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
		request.setAttribute("admin", "admin");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Youfood - Liste des plats</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">
			$(document).ready( function() 
			{
				$("#ajouter").click(function()
				{
					$("#popup").animate({height: "toggle"},1000).delay(2000);
				});
				
				//Delete Link click
				$(".button_close").click(function()
				{
					var id = $(this).attr("id");
					var url = "delete";
					url = "<%= request.getContextPath() %>" + "/menu/delete";
					$.post( url, { "menuId" : id},function( data ){
								alert("Le menu a été supprimé.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/menu/list.jsp");
						});
				});
				
				//Add Link click
				$(".button_add").click(function()
				{
					var nom = $("#nom").val();
					$("font").remove();
					if(nom != "")
					{
						var url = "new";
						url = "<%= request.getContextPath() %>" + "/menu/new";
						$.post( url, { "nom" : nom},function( data ){
								alert("Le menu a été ajouté.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/menu/list.jsp");
							});
					}
					else
					{
						$("#nom").css("background-color","#F3C200");
						$("#nom").after("<font color=\"red\"> Vous devez rentrer un nom explicite pour le menu.</font>");
						//$("#nom").focus();
						//$("#popup").animate({height: "toggle"},1000).delay(2000);
					}
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="/template/header.jsp" %>
		<section id="content">
			<div>Menu courant : <a href="description.jsp?idMenu=${current.id}"><c:out value="${current.name }" /></a></div>
			<c:choose>
				<c:when test="${empty menus}">
					<p align="center">Aucun restaurant à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty admin}">
						<br/>
						<a href="#" id="ajouter">&nbsp;&nbsp;&nbsp;&nbsp;Ajouter un menu</a>
						<br/>
						<br/>
					</c:if>
					<c:if test="${not empty admin and not empty menus}">
					<table class="display_table" id="menu_table">
						<tr id="title">
							<th>Id</th>
							<th>Nom</th>
							<th>Date de création</th>
						</tr>
						<c:forEach items="${menus}" var="menu">
							<tr id="${menu.id}">
								<td><c:out value="${menu.id}"/></td>
								<td><c:out value="${menu.name}"/></td>
								<td><c:out value="${menu.creationDate}"/></td>
								<td id="button">
									<a class="button_view" href="description.jsp?idMenu=${menu.id}" title="voir ou modifier"></a>
									<a class="button_close" href="#" title="supprimer" id="${menu.id}"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
					</c:if>
					<br/>
				</c:otherwise>
			</c:choose>
		</section>
		<section id="popup" style="margin-left : 20%; -webkit-box-shadow : none">
			<div>
				<label for="nom">Nom du menu :</label><input type="text" id="nom" name="nom" maxlength="34" size="20" onfocus="javascript:couleur(this);" />&nbsp;&nbsp;&nbsp;<a href="#" class="button_add"></a>
			</div>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>