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
    Integer da = (Integer) request.getAttribute("da");
    Integer a = (Integer) request.getAttribute("a");

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
    <h1>Tutti i fossili</h1>

    <form name="filterForm" action="Dispatcher" method="post">
            <h1>Filtri:</h1>
            <div class="form-group-select">
                <div class="inline">
                    <input type="radio" id="asta" name="ruolo" value="asta">
                    <label for="asta">Asta</label>
                    <input type="radio" id="fisso" name="ruolo" value="fisso">
                    <label for="fisso">Prezzo fisso</label>
                </div>
            </div>
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria">
                    <option value="nessuna">Nessuna</option>
                    <option value="ammoniti">Ammoniti</option>
                    <option value="trilobiti">Trilobiti</option>
                    <option value="ambre">Ambre</option>
                    <option value="pesci">Pesci</option>
                    <option value="rettili">Rettili</option>
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
        <%for (i = 0; i < articoli.size(); i++) {
        if (articolivendita.contains(articoli.get(i))) {
         int id=articoli.get(i).getId();
         int j;
          for(j=0; j<articolivendita.size(); j++){
              if(id == articolivendita.get(j).getId())  break;
          }
         String name=articoli.get(i).getNome();
         String category=articoli.get(i).getCategoria();
         Float price=articolivendita.get(j).getPrezzo();
         String image=articolivendita.get(j).getImmagine();  %>
        <figure>


            <a href="#">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="#">
                <figcaption><%= name%></figcaption>
            </a>
            <figcaption><%= price%></figcaption>
        </figure>
        <% }
        if(articoliasta.contains(articoli.get(i))) {
            int id=articoli.get(i).getId();
            int j;
            for(j=0; j<articoliasta.size(); j++){
                if(id == articoliasta.get(j).getId())  break;
            }
            String name=articoli.get(i).getNome();
            Date data=articoliasta.get(j).getData_scadenza();
            String image=articoliasta.get(j).getImmagine(); %>
        <figure>
            <a href="#">
                <img src="https://via.placeholder.com/150" alt="Image 1">
            </a>
            <a href="#">
                <figcaption><%=name%></figcaption>
            </a>
            <figcaption>Scade il:   <%=data%></figcaption>
        </figure>
        <%}
        }%>
    </section>
</main>

</body>
</html>
