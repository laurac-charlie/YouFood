<%
	boolean logged = request.getSession().getAttribute("loggedUser") != null;
	String contextPath = getServletContext().getContextPath();
%>
<header>
	<nav>
		<ul style="list-style: none;">
			<li id="h2"><a href="<%= contextPath %>/home"> YouFood Inc </a></li>
			<% if(logged) { %>
			<li><a href="<%= contextPath %>/restaurant/list.jsp">Restaurants</a></li>
			<li><a href="<%= contextPath %>/menu/list.jsp">Menus</a></li>
			<li><a href="<%= contextPath %>/meals/list.jsp">Plats</a></li>
			<li><a href="<%= contextPath %>/productType/list.jsp">Types de produit</a></li>
			<li><a href="<%= contextPath %>/orders/list.jsp">Commandes</a></li>
			<li><a href="<%= contextPath %>/users/list.jsp">Utilisateurs</a></li>
			<li><a href="<%= contextPath %>/logout">Deconnexion</a></li>
			<% } else { %>
			<li><a href="<%= contextPath %>/menu/current">Menus</a></li>
			<li><a href="<%= contextPath %>/login">Connexion</a></li>
			<% } %>
		</ul>
	</nav>
</header>