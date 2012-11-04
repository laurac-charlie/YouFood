<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.ProductTypeDAO" %>
<%@page import="com.supinfo.youfood.entity.ProductType" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<%@page import="java.util.List" %>
<% 
	request.setAttribute("types", ProductTypeDAO.getInstance().getAllProductType());
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
		request.setAttribute("admin", "admin");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Youfood - Liste des types de produits</title>
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
					url = "<%= request.getContextPath() %>" + "/productType/delete";
					$.post( url, { "typeId" : id},function( data ){
								alert("Le type de produit a été supprimé.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/productType/list.jsp");
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
						url = "<%= request.getContextPath() %>" + "/productType/new";
						$.post( url, { "nom" : nom},function( data ){
								alert("Le type de produit a été ajouté.");
								$(location).attr("href","<%= request.getContextPath() %>" + "/productType/list.jsp");
							});
					}
					else
					{
						$("#nom").css("background-color","#F3C200");
						$("#nom").after("<font color=\"red\"> Vous devez rentrer un nom valide.</font>");
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
			<c:choose>
				<c:when test="${empty types}">
					<p align="center">Aucun restaurant à afficher.</p>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty admin}">
						<br/>
						<a href="#" id="ajouter">&nbsp;&nbsp;&nbsp;&nbsp;Ajouter un type de produit</a>
						<br/>
						<br/>
					</c:if>
					<c:if test="${not empty admin and not empty types}">
						<table class="display_table" id="type_table">
							<tr id="title">
								<th>Id</th>
								<th>Nom du type</th>
							</tr>
							<c:forEach items="${types}" var="type">
							<tr id="${restaurant.id}">
								<td><c:out value="${type.id}"/></td>
								<td><c:out value="${type.name}"/></td>
								<td id="button">
									<a class="button_close" href="#" title="supprimer" id="${type.id}"></a>
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
				<label for="nom">Nom du type :</label><input type="text" id="nom" name="nom" maxlength="34" size="20" onfocus="javascript:couleur(this);" />&nbsp;&nbsp;&nbsp;<a href="#" class="button_add"></a>
			</div>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>