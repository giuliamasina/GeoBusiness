<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/07/24
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloVendita" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Venditore" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    ArticoloVendita articolovendita = (ArticoloVendita) request.getAttribute("articolovendita");
    Venditore venditore = (Venditore) request.getAttribute("venditore");
    String consegna = (String) request.getAttribute("consegna");
%>
<html>
<head>
    <title>Compra</title>
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
        body main {
            display: flex;
            flex-direction: row;
            background-color: #CAB18C;
        }
        body main section {
            display: flex;
            flex-direction: column;
            width:500px;
        }
        body main section img{
            margin-top:80px;
            margin-left: 80px;
            width: 300px;
            height: 300px;
        }
        #nome,#venditore {
            margin-left: 80px;
            font-size: 25px;
        }
        #inserisci {
            margin-left: 80px;
            margin-top: 120px;
        }
        body main section label {
            margin-left: 80px;
            font-size: 21px;
            margin-top:20px;
        }
        input[type="text"],
        input[type="number"]{
            border: 1px solid black;
            padding-left: 10px;
            padding-bottom: 5px;
            background-color: #B19770;
            width: 260px;
            font-size: 19px;
            margin-left: 80px;
        }
        main section button {
            position: relative;
            top: 70px;
            left: 130px;
            width: 120px;
            height: 60px;
            border: none;
            border-radius: 0;
            background-color: #F8D4A0;
            cursor: pointer;
            font-size: 20px;
        }
        main section button:hover {
            background-color: #DFBB88;
        }
        #dettagli {
            margin-top: 120px;
            margin-left: 90px;
        }
        #consegna {
            margin-left: 90px;
            font-size: 21px;
            font-style: normal;
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

    <nav>
        <ul>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="javascript:logoutForm.submit()">Log-out</a></li>
        </ul>
    </nav>
</header>

<main>
    <section>
        <img src="https://via.placeholder.com/150" alt="Image 1">
        <h1 id="nome"><%=articolovendita.getNome()%></h1>
        <h2 id="venditore">Venditore: <%=venditore.getUsername()%></h2>
    </section>
    <section>
        <h1 id="inserisci">Inserisci i dati del pagamento</h1>
        <label for="card">Numero carta di credito: </label>
        <input type="text" id="card" name="card" required >
        <label for="expire">Scade il: </label>
        <input type="text" id="expire" name="expire" required>
        <label for="security">Numero di sicurezza</label>
        <input type="text" id="security" name="security" required>
        <button type="button">Compra</button>
    </section>
    <section>
        <h1 id="dettagli">Dettagli consegna</h1>
        <h2 id="consegna">Ordine verrà consegnato a: <%=consegna%></h2>
    </section>
</main>

<footer>
    <p>my footer</p>
</footer>

</body>
</html>
