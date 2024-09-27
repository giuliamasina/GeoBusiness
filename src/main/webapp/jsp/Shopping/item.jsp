<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 14/07/24
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloVendita" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Venditore" %>
<%@ page import="com.geobusiness.geobusiness.model.dao.CompratoreDAO" %>
<%@ page import="com.geobusiness.geobusiness.model.dao.DAOFactory" %>
<%@ page import="com.geobusiness.geobusiness.services.config.Configuration" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Compratore" %>

<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";

    ArticoloVendita articolovendita = (ArticoloVendita) request.getAttribute("articolovendita");
    Venditore venditore = (Venditore) request.getAttribute("venditore");

    int isCompratore = 0;   // dovrò mettere tutto questo anche nelle altre view per entrare in Profilo (bisogna sapere se ho un compratore o un venditore)
    Compratore compratore = null;
    DAOFactory daoFactory = null;
    daoFactory = DAOFactory.getDAOFactory(Configuration.DAO_IMPL,null);
    daoFactory.beginTransaction();
    if(loggedOn){
        CompratoreDAO compratoreDAO = daoFactory.getCompratoreDAO();
        compratore = compratoreDAO.findByUsername(loggedUser.getUsername());
        
        if(compratore == null){
            isCompratore = 0;
        }
        else{
            isCompratore = 1;
        }
    }
    daoFactory.commitTransaction();
    //System.out.println(loggedUser.getId());
    //System.out.println(venditore.getId());

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
            top: 80px;
            bottom: 90px;
        }
        #tuo {
            text-align: center;
            margin-top: 1px; /*prima era 25*/
            margin-bottom: 0;
            padding: 20px;
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
        #username1{
            position: relative;
            font-size: 17px;
        }
        #username2{
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
            width: 120px;
            height: 60px;
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
    <script>
        function notLoggedOn(){
            alert("Devi accedere come compratore per acquistare un articolo")
        }
        function notCompratore(){
            alert("Devi essere un utente compratore per acquistare un articolo")
        }
    </script>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <form name="viewVendForm" action="Dispatcher" method="post">
        <%if(!loggedOn) {%>
        <input type="hidden" name="Id_compratore">
        <%} else {%>
        <input type="hidden" name="Id_compratore" value="<%=loggedUser.getId()%>">
        <%}%>
        <input type="hidden" name="Id_venditore" value="<%=venditore.getId()%>">
        <input type="hidden" name="controllerAction" value="Profile.viewVendPerComp">
    </form>
    <form name="deleteItemForm" action="Dispatcher" method="post">
        <%if(!loggedOn) {%>
        <input type="hidden" name="Id_venditoree">
        <%}else{%>
        <input type="hidden" name="Id_venditore" value="<%=loggedUser.getId()%>">
        <%}%>
        <input type="hidden" name="Id_articolo" value="<%=articolovendita.getId()%>">
        <input type="hidden" name="controllerAction" value="Profile.deleteItem">
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

<%if(loggedOn) {%>
<%if(venditore.getId().equals(loggedUser.getId())) {%>
    <h2 id="tuo">Questo articolo è tuo</h2>
<%}
} %>

<main>
    <section>
        <img src="<%=articolovendita.getImmagine()%>" alt="Image 1">
    </section>
    <section>
        <h1 id="nome"><%=articolovendita.getNome()%></h1>
        <h2 id="descrizione">Descrizione</h2>
        <%if(articolovendita.getDescription() != null && !articolovendita.getDescription().isEmpty()) {%>
            <p><%=articolovendita.getDescription()%></p>
        <%} else{  %>
            <p>Nessuna descrizione disponibile</p>
        <%}%>
    </section>
    <section id="info">
        <h2 id="venditore">Venditore:</h2>
        <%if(isCompratore == 1){%>
        <a href="javascript:viewVendForm.submit()">
            <h3 id="username1"><%=venditore.getUsername()%></h3>
        </a>
        <%} else{%>
        <a>
            <h3 id="username2"><%=venditore.getUsername()%></h3>
        </a>
        <%}%>
        <h2 id="prezzo">Prezzo: <%=articolovendita.getPrezzo()%> €</h2>
        <%if (!loggedOn) {%>
        <a>
            <button type="button" onclick="notLoggedOn()">Compra</button>
        </a>
        <%} else if(venditore.getId().equals(loggedUser.getId()) ){%>
        <a href="javascript:deleteItemForm.submit()">
            <button type="button">Elimina</button>
        </a>
        <%} else if(isCompratore==0){%>
        <a>
            <button type="button" onclick="notCompratore()">Compra</button>
        </a>
        <%} else if(isCompratore==1){%>
        <a href="Dispatcher?controllerAction=Shopping.buyview&articolovendita=<%=articolovendita.getId()%>">
            <button type="button">Compra</button>
        </a>
        <%}%>
    </section>
</main>
<footer>
    <!--<p>my footer</p>-->
</footer>
</body>
</html>
