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
<%@ page import="com.geobusiness.geobusiness.model.dao.DAOFactory" %>
<%@ page import="com.geobusiness.geobusiness.model.dao.CompratoreDAO" %>
<%@ page import="com.geobusiness.geobusiness.services.config.Configuration" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    ArticoloVendita articolovendita = (ArticoloVendita) request.getAttribute("articolovendita");
    Venditore venditore = (Venditore) request.getAttribute("venditore");
    String consegna = (String) request.getAttribute("consegna");
    Integer Id_compratore = (Integer) request.getAttribute("Id_compratore");
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
        #nome,#venditore,#prezzo {
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
    <script>
        function buy(){
            var text = document.getElementById("card").value;
            var text2 = document.getElementById("expire").value;
            var text3 = document.getElementById("security").value;
            if(text !== "" && text2 !== "" && text3 !== "") {
                alert("Grazie dell'acquisto!")
                document.buyForm.submit();
            }else{
                alert("Inserisci tutti i dati richiesti");
            }
        }
    </script>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <form name="buyForm" action="Dispatcher" method="post">
        <input type="hidden" name="Id_articolo" value="<%=articolovendita.getId()%>"/>
        <input type="hidden" name="Id_compratore" value="<%=Id_compratore%>"/>
        <input type="hidden" name="controllerAction" value="Shopping.compra"/>
    </form>
    <nav>
        <ul>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="javascript:logoutForm.submit()">Log-out</a></li>
            <li><a href="Dispatcher?controllerAction=Profile.view&username=<%=loggedUser.getUsername()%>">Profilo</a></li>
        </ul>
    </nav>
</header>

<main>
    <section>
        <img src="<%=articolovendita.getImmagine()%>" alt="Image 1">
        <h1 id="nome"><%=articolovendita.getNome()%></h1>
        <h2 id="venditore">Venditore: <%=venditore.getUsername()%></h2>
        <h2 id="prezzo">Prezzo: <%=articolovendita.getPrezzo()%> €</h2>
    </section>
    <section>
        <h1 id="inserisci">Inserisci i dati del pagamento</h1>
        <label for="card">Numero carta di credito: </label>
        <input type="text" id="card" name="card" inputmode="numeric" pattern="[0-9]{13,19}" minlength="16" maxlength="19" placeholder="1234 5678 9012 3456" required >
        <label for="expire">Scade il: </label>
        <input type="text" id="expire" name="expire" pattern="(0[1-9]|1[0-2])\/[0-9]{2}" placeholder="MM/YY" required>
        <label for="security">CVV: </label>
        <input type="text" id="security" name="security" pattern="\d{3}" maxlength="3" placeholder="123" required>
        <a href="javascript:buyForm.submit()">
            <button type="button" onclick="buy()">Compra</button>
        </a>
    </section>
    <section>
        <h1 id="dettagli">Dettagli consegna</h1>
        <h2 id="consegna">Ordine verrà consegnato a: <%=consegna%></h2>
    </section>
</main>

<footer>
    <!--<p>my footer</p>-->
</footer>

</body>
</html>
