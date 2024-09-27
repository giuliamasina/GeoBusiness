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
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalDateTime" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    List<Articolo> articoli = (List<Articolo>) request.getAttribute("articoli");
    List<ArticoloVendita> articolivendita = new ArrayList<>();
    articolivendita = (List<ArticoloVendita>) request.getAttribute("articolivendita");
    List<ArticoloAsta> articoliasta = new ArrayList<>();
    articoliasta = (List<ArticoloAsta>) request.getAttribute("articoliasta");
    //String categoria = (String) request.getAttribute("categoria");
    //Float da = (Float) request.getAttribute("da");
    //Float a = (Float) request.getAttribute("a");
    //String tipoarticolo = (String) request.getAttribute("tipoarticolo");
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
            margin-left: 125px; /*prima non c'era*/
        }
        main h1 {
            font-size: 40px;
            position:relative;
            left: 120px;
            top: 65px;
            margin-bottom: 60px; /*prima era 60*/
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
            top:45px; /*prima era 55*/
            /*margin-bottom: 20px; /*prima non c'era*/
            margin-right: 18px; /*prima non c'era*/
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
            font-size: 16px;
            position: relative;
            left: 50px;
            margin-right: 9px; /*prima non c'era*/
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
    <script>
        function filter(){
            f = document.filterForm;
            f2 = document.filter;
            f.categoria.value = f2.categoria.value;
            f.da.value = f2.da.value;
            f.a.value = f2.a.value;
            f.tipoarticolo.value = f2.tipoarticolo.value;
            f.submit();
        }
    </script>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <form name="filterForm" action="Dispatcher" method="post">
        <input type="hidden" name="categoria" />
        <input type="hidden" name="da"/>
        <input type="hidden" name="a"/>
        <input type="hidden" name="tipoarticolo"/>
        <input type="hidden" name="controllerAction" value="Shopping.filterview"/>
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
    <h1>Tutti i fossili</h1>

    <form name="filter" action="javascript:filter()">
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
                    <option value="Piante">Piante</option>
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
            <!-- <input type="hidden" name="controllerAction" value="Shopping.filterview"/>  -->
            <button type="submit">Cerca</button>
    </form>

    <section id="articoli">
        <%if(articolivendita != null && !articolivendita.isEmpty()){
            for(i=0;i<articolivendita.size();i++){
                if (articolivendita.get(i).getStatus() == 0 && !articolivendita.get(i).isDeleted()) {
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
            <figcaption><%= price%> €</figcaption>
        </figure>
        <%}
        }
        }

            if(articoliasta != null && !articoliasta.isEmpty()){
            for(i=0;i<articoliasta.size();i++){
                if (articoliasta.get(i).getStatus() == 0 && !articoliasta.get(i).isDeleted()) {
            String name = articoliasta.get(i).getNome();
            String category = articoliasta.get(i).getCategoria();
            Timestamp data=articoliasta.get(i).getData_scadenza();
            String image = articoliasta.get(i).getImmagine(); %>
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
         }
         } %>
        <%if((articoliasta == null || articoliasta.isEmpty())&& (articolivendita == null || articolivendita.isEmpty())){%>
            <h3>Nessun risultato</h3>
        <%}%>

    </section>
</main>

</body>
</html>