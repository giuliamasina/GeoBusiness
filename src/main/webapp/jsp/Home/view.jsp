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
            width:100%;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #CAB18C;
            box-sizing: border-box;
        }
        header {
            top:0;
            width: 100%;
            height:90px;
            background-color: #5B533D;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
        }
        header h1 {
            margin: 0;
        }
        nav {
            background-color: #5B533D;
            margin-left:30px;
        }
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }
        nav ul li {
            margin: 0 10px;
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
            padding: 20px;
        }
        section {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
        }
        figure {
            height:380px;
            width:355px;
            text-align: center;
            border-radius: 5px;
            background-color: #CAB18C;
            flex-basis: calc(25% - 15px);
        }
        figure img {
            width:355px;
            height:355px;
            max-width: 100%;
            border-radius: 5px;
            margin: 5px;
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
            <%}%>
        </ul>
    </nav>
</header>

<main>
    <section>
        <h1>Inizia il tuo museo personale</h1>
    </section>
    <form name="goToShop" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Shopping.view"/>
        <button type="submit">Tutti i fossili</button>
    </form>
    <h2>Categorie</h2>
    <section>
        <figure>
            <img src="https://via.placeholder.com/150" alt="Image 1">
            <figcaption>Image 1 Title</figcaption>
        </figure>
        <figure>
            <img src="https://via.placeholder.com/150" alt="Image 2">
            <figcaption>Image 2 Title</figcaption>
        </figure>
        <figure>
            <img src="https://via.placeholder.com/150" alt="Image 3">
            <figcaption>Image 3 Title</figcaption>
        </figure>
        <figure>
            <img src="https://via.placeholder.com/150" alt="Image 4">
            <figcaption>Image 4 Title</figcaption>
        </figure>
        <!-- Add more figures as needed -->
    </section>
</main>

<footer>
    <p>&copy; 2024 My Website. All rights reserved.</p>
</footer>

</body>
</html>
