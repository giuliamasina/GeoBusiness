<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 14/07/24
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false"%>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%@ page import="com.geobusiness.geobusiness.model.mo.Articolo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloVendita" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloAsta" %>
<%@ page import="java.sql.Date" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    List<Articolo> articoli = (List<Articolo>) request.getAttribute("articoli");
    List<ArticoloVendita> articolivendita = (List<ArticoloVendita>) request.getAttribute("articolivendita");
    List<ArticoloAsta> articoliasta = (List<ArticoloAsta>) request.getAttribute("articoliasta");
    String categoria = (String) request.getAttribute("categoria");
    Float da = (Float) request.getAttribute("da");
    Float a = (Float) request.getAttribute("a");
    String tipoarticolo = (String) request.getAttribute("tipoarticolo");
    int i;
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
            background-color: #CAB18C;
            width: 100%;
            display: flex;
            flex-direction: column;
            position:relative;
            top: 90px;
            bottom: 90px;
        }
        main h1 {
            font-size: 40px;
            position:relative;
            left: 120px;
            top: 65px;
            margin-bottom: 60px;
        }
        main form {
            display: flex;
            flex-direction: row;
        }
        main form h1 {
            font-size: 22px;
            position:relative;
            left: 30px;
            right:10px;
            bottom:20px;
            top:55px;
        }
        .form-group {
            position: relative;
            left: 30px;
            top:60px;
        }
        .form-group label {
            position: relative;
            font-size: 15px;
            margin-left: 20px;
        }
        .form-group-select {
            position: relative;
            left: 30px;
            top:60px;
        }
        .form-group-select label{
            position: relative;
            font-size: 15px;
            margin-left: 7px;
        }
        main form h2 {
            font-size: 19px;
            position: relative;
            left: 50px;
            top: 45px;
            right: 35px;
        }
        main form button {
            position: relative;
            left: 50px;
            top: 60px;
            cursor: pointer;
            width: 50px;
            height: 30px;
        }
        main section {
            display: flex;
            flex-direction: row;
            top:40px;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
        }
        main section figure {
            width:300px;
            height: 340px;
        }
        main section figure img {
            width:300px;
            height: 300px;
        }
        main section figure figcaption {
            position: relative;
            top:5px;
            left: 10px;
        }
    </style>
    <script language="JavaScript">

    </script>
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
            <li><a href="Dispatcher?controllerAction=Profile.view&utenteId=<%=loggedUser.getId()%>">Profilo</a></li>
            <%}%>
        </ul>
    </nav>
</header>

<main>
    <h1>Tutti i fossili</h1>

    <form name="filter" action="Dispatcher" method="post">
        <input type="hidden" name="categoria" />
        <input type="hidden" name="da"/>
        <input type="hidden" name="a"/>
        <input type="hidden" name="tipoarticolo"/>
        <input type="hidden" name="controllerAction" value="Shopping.filterview"/>
    </form>

    <form name="filterForm" action="Dispatcher" method="post">
            <h1>Filtri:</h1>
            <div class="form-group-select">
                <div class="inline">
                    <input type="radio" id="asta" name="tipoarticolo" value="asta">
                    <label for="asta">Asta</label>
                    <input type="radio" id="fisso" name="tipoarticolo" value="fisso">
                    <label for="fisso">Prezzo fisso</label>
                </div>
            </div>
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria">
                    <option value="nessuna">Nessuna</option>
                    <option value="Ammoniti">Ammoniti</option>
                    <option value="Trilobiti">Trilobiti</option>
                    <option value="Ambre">Ambre</option>
                    <option value="Pesci">Pesci</option>
                    <option value="Rettili">Rettili</option>
                </select>
            </div>
            <h2>Prezzo:</h2>
            <div class="form-group">
                <label for="da">da</label>
                <input type="number" id="da" name="da">
            </div>
            <div class="form-group">
                <label for="a">a</label>
                <input type="number" id="a" name="a">
            </div>
            <input type="hidden" name="controllerAction" value="Shopping.filterview"/>
            <button type="submit">Cerca</button>
    </form>

    <section id="articoli">
        <%if(!articolivendita.isEmpty()){
            for(i=0;i<articolivendita.size();i++){
            String name = articolivendita.get(i).getNome();
            String category = articolivendita.get(i).getCategoria();
            Float price = articolivendita.get(i).getPrezzo();
            String image = articolivendita.get(i).getImmagine(); %>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.itemview&articolovendita=<%=articolivendita.get(i).getId()%>">
                <img src="<%=image%>">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.itemview&articolovendita=<%=articolivendita.get(i).getId()%>">
                <figcaption><%= name%></figcaption>
            </a>
            <figcaption><%= price%></figcaption>
        </figure>
        <%}
        }else {%>
        <p>Non sono state trovati articoli a prezzo fisso</p>
        <%}%>
        <% if(!articoliasta.isEmpty()){
            for(i=0;i<articoliasta.size();i++){
            String name = articoliasta.get(i).getNome();
            String category = articoliasta.get(i).getCategoria();
            Date data=articoliasta.get(i).getData_scadenza();
            String image = articolivendita.get(i).getImmagine(); %>
        <figure>
            <a href="Dispatcher?controllerAction=Shopping.auctionview&articoloasta=<%=articoliasta.get(i).getId()%>">
                <img src="<%=image%>">
            </a>
            <a href="Dispatcher?controllerAction=Shopping.auctionview&articoloasta=<%=articoliasta.get(i).getId()%>">
                <figcaption><%=name%></figcaption>
            </a>
            <figcaption>Scade il:   <%=data%></figcaption>
        </figure>
         <%}
         } else {%>
            <p>Non sono state trovate aste</p>
        <%}%>

    </section>
</main>

</body>
</html>