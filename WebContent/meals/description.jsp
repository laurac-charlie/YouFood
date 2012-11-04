<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.MealDAO" %>
<%@page import="com.supinfo.youfood.entity.Meal" %>
<%@page import="com.supinfo.youfood.dao.ProductTypeDAO" %>
<%@page import="com.supinfo.youfood.entity.ProductType" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<% 
	int id = 0;
	boolean dispo = true;
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
	{
		request.setAttribute("admin", "admin");
		request.setAttribute("meal",null);
		if(request.getParameter("idMeal") != null)
		{
			try
			{
				id = Integer.parseInt(request.getParameter("idMeal").toString());
			}
			catch(Exception e)
			{id = 0;}
			Meal theMeal = MealDAO.getInstance().findId(id);
			dispo = theMeal.isAvailable();
			request.setAttribute("meal",theMeal);
			request.setAttribute("list_types",ProductTypeDAO.getInstance().getAllProductType() );
			request.setAttribute("meal_types", theMeal.getTypes());
		}
	}
	
%>
<!DOCTYPE html>
<html>
	<head>
		<title>YooFood - Description du plat</title>
		<link rel="stylesheet" type="text/css" href="../style/style.css" />
		<script type="text/javascript" src="../script_js/js/jquery.js"></script>
		<script type="text/javascript" src="../script_js/function.js"></script>
		<script type="text/javascript">
		function check() 
		{
			var sucess = true;
			$("font").remove();
			$("input").each(function(){
				if($(this).val() == "")
				{
					$(this).css("background-color","#F3C200");
					$(this).after("<font color=\"red\"> Vous devez rentrer un "+$(this).attr("name")+".</font>");
					sucess = false;
				}
				
			});
			
			if($("#description").val() == "")
			{
				$("#description").css("background-color","#F3C200");
				$("#description").after("<font color=\"red\"> Vous devez rentrer une description.</font>");
				sucess = false;
			}
			
			if(isNaN($("#prix").val()))
			{
				$("#prix").css("background-color","#F3C200");
				$("#prix").after("<font color=\"red\"> Vous devez rentrer un prix valide. ex : 39.9</font>");
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
					var url = "update";
					var checked = false;
					if($("input:checked").length == 1)
						checked = true;
					
					url = "<%= request.getContextPath() %>" + "/meals/update?type=all";
					$.post( url, { "mealId" : "${meal.id}",
								   "nom" : $("#nom").val(),
								   "description" : $("#description").val(),
								   "prix" : $("#prix").val(),
								   "disponible" : checked},function( data ){
							$("#message").html(data);
							$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
						});
				}
			});
			
			$(".button_add").click(function()
			{
				var id = $("#list_types option:selected").attr("value");
				if(id != 0)
				{
				var url = "update";
				url = "<%= request.getContextPath() %>" + "/meals/update?type=add";
				$.post( url, { mealId : "${meal.id}","typeId" : id },function( data ){
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
				url = "<%= request.getContextPath() %>" + "/meals/update?type=delete";
				$.post( url, { mealId : "${meal.id}", "typeId" : id},function( data ){
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
				<c:when test="${empty meal or empty admin }">
					<p align="center">Aucun plat à afficher.</p>
				</c:when>
				<c:otherwise>
					<div>
						<label for="nom">Nom du plat :</label><input type="text" id="nom" name="nom" maxlength="34" size="20" value="${meal.name }" autofocus onfocus="javascript:couleur(this);" >
					</div>
					<div>
						<label for="description">Description :</label><textarea id="description" name="description" rows="4" cols="50" onfocus="javascript:couleur(this);"><c:out value="${meal.description }"/></textarea>
					</div>
					<div>
						<label for="prix">Prix :</label><input id="prix" name="prix" maxlength="7" size="6" value="${meal.price }" onfocus="javascript:couleur(this);"/>
					</div>
					<div>
						<label for="disponible">Disponible :</label><input type="checkbox" value="disponible" id="disponible" name="disponible" <%if(dispo) {%> checked <%} %>/>
					</div>
					
					<div><label for="actualiser">&nbsp;</label>&nbsp;&nbsp;<a href="#" id="${meal.id }" class="button_refresh" title="actualiser"></a></div>
					
					<div>
						<label for="meal"> Ajouter un type : </label>
						<select name="list_types" id="list_types" > 
							<option value="0"> -- Type -- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</option>
							<c:forEach items="${list_types}" var="type">
								<option value="${type.id}"><c:out value="${type.name}"/></option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;<a href="#" id="add" class="button_add" title="ajouter un type"></a>
					</div>
					<br/>
					<div>Liste des types du plat :</div>
					<br/>
					<table class="display_table" id="meal_table">
						<tr id="title">
							<th>Nom</th>
						</tr>
						<c:forEach items="${meal_types}" var="type">
							<tr id="${meal.id}">
								<td><c:out value="${type.name}"/></td>
								<td id="button">
									<a class="button_close" href="#" title="retirer du menu" id="${type.id}"></a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
			<br/>
		</section>
		<section id="message">
			<div>Le plat a été mis à jour.</div>
		</section>
		<%@ include file="/template/footer.jsp" %>
	</body>
</html>