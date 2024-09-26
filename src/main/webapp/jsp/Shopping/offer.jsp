<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/07/24
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloAsta" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Venditore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.sql.Timestamp" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    ArticoloAsta articoloasta = (ArticoloAsta) request.getAttribute("articoloasta");
    Venditore venditore = (Venditore) request.getAttribute("venditore");
    List<Float> offerte = (List<Float>) request.getAttribute("offerte");
    List<Timestamp> date_offerte = (List<Timestamp>) request.getAttribute(("date_offerte"));
    //Integer Id_compratore = (Integer) request.getAttribute("Id_compratore");
    int i;
%>
<html>
<head>
    <title>Offerta</title>
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
        body main section label {
            margin-left: 80px;
            font-size: 25px;
            margin-bottom: 70px;
            margin-top:120px;
            font-weight: bold;
        }
        input[type="text"]{
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
        #details {
            margin-top: 120px;
            margin-left: 90px;
        }
        #offers {
            margin-left: 90px;
            font-size: 21px;
            font-weight: normal;
            margin-top: 10px;
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
        function checkAndConfirm(maxoffer) {
            var number = document.getElementById("offer").value;
            if (number <= maxoffer) {
                // Display a pop-up alert
                alert("La tua offerta deve essere maggiore delle altre");
            } else if(number > maxoffer){
                // Display a pop-up alert if the number is not lower
                alert("Grazie per la tua offerta!");
                document.offerForm.offerta.value = number;
                document.offerForm.submit();
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
    <form name="offerForm">
        <input type="hidden" name="Id_articolo" value="<%=articoloasta.getId()%>"/>
        <input type="hidden" name="Id_compratore" value="<%=loggedUser.getId()%>"/>
        <input type="hidden" name="offerta"/>
        <input type="hidden" name="controllerAction" value="Shopping.faiofferta"/>
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
        <img src="https://via.placeholder.com/150" alt="Image 1">
        <h1 id="nome"><%=articoloasta.getNome()%></h1>
        <h2 id="venditore">Venditore: <%=venditore.getUsername()%></h2>
    </section>
    <section>
        <label for="offer">Inserisci la tua offerta</label>
        <input type="number" id="offer" name="offer" required >
        <h1 id="inserisci">Inserisci i dati del pagamento</h1>
        <label for="card">Numero carta di credito: </label>
        <input type="text" id="card" name="card" required >
        <label for="expire">Scade il: </label>
        <input type="text" id="expire" name="expire" required>
        <label for="security">Numero di sicurezza</label>
        <input type="text" id="security" name="security" required>
        <%if(offerte != null && !offerte.isEmpty()) {%>
            <button type="button" onclick="checkAndConfirm(<%=offerte.get(0)%>)">Fai Offerta</button>
        <%} else{%>
            <button type="button" onclick="checkAndConfirm(0)">Fai Offerta</button>
        <%}%>
    </section>
    <section>
        <h1 id="details">Tutte le offerte</h1>
        <%if(offerte != null && !offerte.isEmpty()) {%>
        <%for(i=0; i<offerte.size();i++){%>
            <h3 id="offers">$ <%=offerte.get(i)%>    <%=date_offerte.get(i)%></h3>
        <%}
        } else{%>
            <h3>Non sono state fatte offerte</h3>
        <%}%>
    </section>

</main>

<footer>
    <p>my footer</p>
</footer>

</body>
</html>
