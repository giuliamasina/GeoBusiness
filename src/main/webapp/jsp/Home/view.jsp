<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 11/07/24
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Home";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GeoBusiness</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            background-color: #CAB18C;
            padding: 0;
            margin:0;
            height: 1700px;
        }
        header {
            background-color: #5B533D;
            display: flex;
            height:90px;
        }
        header h1 {
            margin-top: 50px;
            margin-left: 55px;
            color: white;
        }
        nav {
            margin-left: 900px;
            margin-top: 49px;
        }
        nav ul {
            display: flex;
            list-style-type: none;
        }
        nav ul li {

        }
        nav ul li a {
            color: white;
            text-decoration: none;
            padding: 10px;
        }
        nav ul li a:hover {
            background-color: #444;
        }
        main {
            padding: 0;
            height: 800px;
            background-color: #CAB18C;
            width: 100%;
            display: flex;
            flex-direction: column;
            position:relative;
            top: 90px;
            bottom: 90px;
        }
        main h2 {
            position: relative;
            top: 110px;
            left: 175px;
        }
        main section h1 {
            font-size: 40px;
            position:relative;
            left: 570px;
            top: 1px;
            margin-bottom: 60px;
        }
        main section {
            display: flex;
            flex-direction: row;
            top:40px;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
        }
        main form {
            position: relative;
            top: 10px;
            left: 850px;
        }
        main section figure {
            height:380px;
            width:355px;
            text-align: center;
            border-radius: 0;
            background-color: #CAB18C;
            flex-basis: calc(25% - 15px);
        }
        main section figure img {
            width:355px;
            height:355px;
            max-width: 100%;
            border-radius: 0;
            margin: 5px;
        }
        #categorie {
            position: relative;
            left:95px;
            top:100px;
        }
        footer {
            background-color: #5B533D;
            color: white;
            text-align: center;
            padding: 10px 0;
            width: 100%;
            margin-bottom: 0;
        }
    </style>

</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>

    <nav>
        <ul>
            <%if (!loggedOn) {%>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewsign">Iscriviti</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewlogin">Log-in</a></li>
            <%} else {%>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="javascript:logoutForm.submit()">Log-out</a></li>
            <li><a href="Dispatcher?controllerAction=Profile.view&username=<%=loggedUser.getUsername()%>">Profilo</a></li>
            <%}%>
        </ul>
    </nav>
</header>

<main>
    <section>
        <h1>Inizia il tuo museo personale</h1>
    </section>
    <form name="goToShopForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Shopping.view"/>
        <a href="javascript:goToShopForm.submit()">
            <button type="submit">Tutti i fossili</button>
        </a>
    </form>
    <h2>Categorie</h2>
    <section id="categorie">
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Ammoniti&da=&a=">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Ammoniti&da=&a=">
                <figcaption>Ammoniti</figcaption>
            </a>
        </figure>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Trilobiti&da=&a=">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Trilobiti&da=&a=">
                <figcaption>Trilobiti</figcaption>
            </a>
        </figure>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Ambre&da=&a=">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Ambre&da=&a=">
                <figcaption>Ambre</figcaption>
            </a>
        </figure>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Pesci&da=&a=">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Pesci&da=&a=">
                <figcaption>Pesci</figcaption>
            </a>
        </figure>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Rettili&da=&a=">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.filterview&categoria=Rettili&da=&a=">
                <figcaption>Rettili</figcaption>
            </a>
        </figure>
        <!-- Add more figures as needed -->
    </section>
</main>

<footer>
    <p>&copy; 2024 My Website. All rights reserved.</p>
</footer>

</body>
</html>
