<%@ page import="com.geobusiness.geobusiness.model.mo.Utente" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloVendita" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Venditore" %><%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 25/09/24
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    ArticoloVendita articolovendita = (ArticoloVendita) request.getAttribute("articolovendita");
    Venditore venditore = (Venditore) request.getAttribute("venditore");
%>
<html>
<head>
    <title>Fossile</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            background-color: #CAB18C;
            padding: 0;
            margin:0;
            height: 1000px;
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
            height: 600px;
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
            margin: 20px;
        }
        #info {
            margin-left: 120px;
        }
        #info a h3{
            margin-left: 127px;  /*prima non c'era*/
        }
        main section img{
            width: 400px;
            height: 400px;
            position: relative;
            left: 90px;
            top:60px;
        }
        #descrizione {
            position: relative;
            top: 80px;
            left: 70px;
            font-size: 20px;
        }
        main section p {
            position: relative;
            top: 80px;
            left: 70px;
            width: 380px;
        }
        #nome {
            position: relative;
            top: 80px;
            left: 70px;
        }
        #venditore {
            position: relative;
            margin-top: 90px;
            left: 120px;
            font-size: 20px;
        }
        #username{
            position: relative;
            font-size: 17px;
        }
        #prezzo {
            position: relative;
            left: 120px;
            font-size: 20px;
        }
        main section a button {
            position: relative;
            top: 70px;
            left: 130px;
            width: 135px; /*prima era 120*/
            height: 75px; /*prima era 60*/
            border: none;
            border-radius: 0;
            background-color: #F8D4A0;
            cursor: pointer;
            font-size: 20px;
        }
        main section a button:hover {
            background-color: #DFBB88;
        }
        footer {
            background-color: #5B533D;
            display: flex;
            width: 100%;
            height: 110px;
            margin-top: auto;
        }
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <form name="viewVendForm" action="Dispatcher" method="post">
        <input type="hidden" name="Id_compratore" value="<%=loggedUser.getId()%>">
        <input type="hidden" name="Id_venditore" value="<%=venditore.getId()%>">
        <input type="hidden" name="controllerAction" value="Profile.viewVendPerComp">
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
        <img src="<%=articolovendita.getImmagine()%>" alt="Image 1">
    </section>
    <section>
        <h1 id="nome"><%=articolovendita.getNome()%></h1>
        <h2 id="descrizione">Descrizione</h2>
        <p><%=articolovendita.getDescription()%></p>
    </section>
    <section id="info">
        <h2 id="venditore">Venditore:</h2>
        <a href="javascript:viewVendForm.submit()">
            <h3 id="username"><%=venditore.getUsername()%></h3>
        </a>
        <h2 id="prezzo">Prezzo: <%=articolovendita.getPrezzo()%> €</h2>
        <a href="javascript:viewVendForm.submit()">
            <button>Lascia una recensione al venditore</button>
        </a>
    </section>
</main>

</body>
</html>
