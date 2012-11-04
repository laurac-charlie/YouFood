<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.supinfo.youfood.dao.RestaurantDAO" %>
<%@page import="com.supinfo.youfood.entity.Restaurant" %>
<%@page import="com.supinfo.youfood.entity.Waiter" %>
<%@page import="com.supinfo.youfood.entity.Administrator" %>
<% 
	int id = 0;
	if(request.getSession().getAttribute("loggedUser") != null && request.getSession().getAttribute("loggedUser") instanceof Administrator) 
	{
		request.setAttribute("admin", "admin");
		request.setAttribute("restaurant",null);
		if(request.getParameter("idRes") != null)
		{
			try
			{
				id = Integer.parseInt(request.getParameter("idRes").toString());
			}
			catch(Exception e)
			{id = 0;}
			Restaurant res = RestaurantDAO.getInstance().findId(id);
			request.setAttribute("restaurant",res);
			request.setAttribute("restaurant_waiters", res.getWaiters());
		}
	}
	
%>
<!DOCTYPE html>
<html>
	<head>
		<title>YooFood - Description du restaureant</title>
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
				
				if($("#adress").val() == "")
				{
					$("#adress").css("background-color","#F3C200");
					$("#adress").after("<font color=\"red\"> Vous devez rentrer une adresse valide.</font>");
					sucess = false;
				}
				
				return sucess;
			}
			
			$(document).ready( function() 
			{
				$("select>option[value=${restaurant.nbOfSeat}]").attr("selected","selected");
				
				$(".button_refresh").click(function()
				{
					if(check())
					{
						var url = "update";					
						url = "<%= request.getContextPath() %>" + "/restaurant/update";
						$.post( url, { "resId" : "${restaurant.id}",
									   "nom" : $("#nom").val(),
									   "adress" : $("#adress").val(),
									   "gerant" : $("#gerant").val(),
									   "description" : $("#description").val(),
									   "nbOfSeat" : $("select>option:selected").attr("value")},function( data ){
								$("#message").html(data);
								$("#message").animate({height: "toggle"},1000).delay(3000).slideUp(1000);
							});
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
				<c:when test="${empty restaurant or empty admin }">
					<p align="center">Aucun restaurant à afficher.</p>
				</c:when>
				<c:otherwise>
					<div>
						<label for="nom">Nom :</label><input type="text" id="nom" name="nom" maxlength="64" size="48" value="${restaurant.name }" autofocus onfocus="javascript:couleur(this);" />
					</div>
					<div>
						<label for="adress">Adresse :</label><textarea id="adress" name="adress" rows="3" cols="50" onfocus="javascript:couleur(this);" ><c:out value="${restaurant.adress }" /></textarea>
					</div>
					<div>
						<label for="gerant">Nom du gérant :</label><input type="text" id="gerant" name="gerant" maxlength="34" size="20" value="${restaurant.gerant }" onfocus="javascript:couleur(this);"  />
					</div>
					<div>
						<label for="description">Description :</label><textarea id="description" name="description" rows="4" cols="50"><c:out value="${restaurant.description }" /></textarea>
					</div>
					<div>
						<label for="nb_seat">Nombre de sièges :</label>
						<select name="nb_seat" >
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="25">25</option>
							<option value="30">30</option>
							<option value="35">35</option>
							<option value="40">40</option>
							<option value="45">45</option>
							<option value="50">50</option>
							<option value="55">55</option>
							<option value="60">60</option>
							<option value="65">65</option>
							<option value="60">70</option>
						</select>
					</div>
					
					<div><label for="actualiser">&nbsp;</label>&nbsp;&nbsp;<a href="#" id="${restaurant.id }" class="button_refresh" title="actualiser"></a></div>
					
					<br/>
					<div>Liste des serveurs du restaurant :</div>
					<br/>
					<table class="display_table" id="waiter_table">
						<tr id="title">
							<th>Nom</th>
							<th>Prénom</th>
							<th>Login</th>
						</tr>
						<c:forEach items="${restaurant_waiters}" var="waiter">
							<tr id="${restaurant.id}">
								<td><c:out value="${waiter.lastName}"/></td>
								<td><c:out value="${waiter.firstName}"/></td>
								<td><c:out value="${waiter.login}"/></td>
							</tr>
						</c:forEach>
					</table>
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