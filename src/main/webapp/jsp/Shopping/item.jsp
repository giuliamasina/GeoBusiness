<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 14/07/24
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";
%>
<html>
<head>
    <title>Fossili</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            background-color: #CAB18C;
            padding: 0;
            margin:0;
            height: 1300px;
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
        nav ul li a:hover{

        }
        main {
            padding: 0;
            height: 800px;
            background-color: #B19770;
            width: 100%;
            display: flex;
            flex-direction: row;
            position:relative;
            top: 90px;
            bottom: 90px;
        }
        main section {
            display: flex;
            flex-direction: column;
        }
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>
    <nav>
        <ul>
            <%if (!loggedOn) {%>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewsign">Iscriviti</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewlogin">Log-in</a></li>
            <%} else {%>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="Dispatcher?controllerAction=Home.view">Log-out</a></li>
            <%}%>
        </ul>
    </nav>
</header>

<main>
    <section>
        <img src="https://via.placeholder.com/150" alt="Image 1">
        <h2>Descrizione</h2>
        <p></p>
    </section>
        <h1>Nome</h1>
        <h2>Prezzo: </h2>
        <button type="button">Compra</button>
    <section>

    </section>
</main>
</body>
</html>
