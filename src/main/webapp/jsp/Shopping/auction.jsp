<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/07/24
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.geobusiness.geobusiness.model.mo.Utente"%>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloAsta" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Venditore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Compratore" %>
<%@ page import="com.geobusiness.geobusiness.model.dao.DAOFactory" %>
<%@ page import="com.geobusiness.geobusiness.services.config.Configuration" %>
<%@ page import="com.geobusiness.geobusiness.model.dao.CompratoreDAO" %>
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

    int isCompratore = 0;
    Compratore compratore = null;
    DAOFactory daoFactory = null;
    String http = "http://localhost:12345/";
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
    <title>Fossili</title>
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
            text-decoration: underline;
        }
        main {
            padding: 0;
            height: 600px;
            background-color: #B19770;
            width: 100%;
            display: flex;
            flex-direction: row;
            position:relative;
            top: 80px;  /* prima 1 */
            /*margin-top: 70px; /*prima non c'era*/
            bottom: 90px;
        }
        #tuo {
            text-align: center;
            margin-top: 1px; /*prima era 60*/
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
        #prezzo,#data {
            position: relative;
            left: 120px;
            font-size: 20px;
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
            alert("Devi accedere come compratore per fare un'offerta")
        }
        function notCompratore(){
            alert("Devi essere un utente compratore per fare un'offerta")
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
        <input type="hidden" name="Id_articolo" value="<%=articoloasta.getId()%>">
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
        <img src="<%=http+articoloasta.getImmagine()%>" alt="Image 1">
    </section>
    <section>
        <h1 id="nome"><%=articoloasta.getNome()%></h1>
        <h2 id="descrizione">Descrizione</h2>
        <%if(articoloasta.getDescription() != null && !articoloasta.getDescription().isEmpty()) {%>
            <p><%=articoloasta.getDescription()%></p>
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
        <h2 id="data">Scade il: <%=articoloasta.getData_scadenza()%></h2>
        <% if (offerte != null && !offerte.isEmpty() && date_offerte != null && !date_offerte.isEmpty()) { %>
        <h2 id="prezzo">Ultima offerta:   <%=offerte.get(0)%> €   <%=date_offerte.get(0)%></h2>
        <%} else {%>
        <h2 id="prezzo">Nessuna offerta fatta</h2>
        <%}%>
        <%if (!loggedOn) {%>
        <a>
            <button type="button" onclick="notLoggedOn()">Fai Offerta</button>
        </a>
        <%} else if(venditore.getId().equals(loggedUser.getId())){%>
        <a href="javascript:deleteItemForm.submit()">
            <button type="button">Elimina</button>
        </a>
        <%} else if(isCompratore==0){%>
        <a>
            <button type="button" onclick="notCompratore()">Fai Offerta</button>
        </a>
        <%} else if(isCompratore==1){%>
        <a href="Dispatcher?controllerAction=Shopping.offerview&articoloasta=<%=articoloasta.getId()%>">
            <button type="button">Fai Offerta</button>
        </a>
        <%}%>
    </section>
</main>

<footer>
    <!--<p>my footer</p>-->
</footer>
</body>
</html>
