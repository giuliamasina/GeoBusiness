<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/08/24
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="static sun.tools.jconsole.inspector.Utils.getClass" %>
<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Shopping";
    String http = "http://localhost:12345/";

    List<Articolo> articoli = (List<Articolo>) request.getAttribute("articoli");
    List<ArticoloVendita> articolivendita = new ArrayList<>();
    articolivendita = (List<ArticoloVendita>) request.getAttribute("articolivendita");
    List<ArticoloAsta> articoliasta = new ArrayList<>();
    articoliasta = (List<ArticoloAsta>) request.getAttribute("articoliasta");
    Compratore compratore = (Compratore) request.getAttribute("compratore");
    List<Recensione> recensioni = new ArrayList<>();
    recensioni = (List<Recensione>) request.getAttribute("recensioni");

    int i;
%>
<html>
<head>
    <title>Profilo</title>
    <style>
        body {
            display: flex;
            flex-direction: column;
            background-color: #CAB18C;
            padding: 0;
            margin:0;
            height: 1600px; /*prima era 1500*/
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
            height: 800px;
            background-color: #CAB18C;
            width: 100%;
            display: flex;
            flex-direction: column;
            position:relative;
            top: 90px;
            bottom: 90px;
            margin-left: 90px;  /*prima non c'era*/
        }
        main h1 {
            font-size: 22px;
            position:relative;
            left: 30px;
            top: 25px;
            right:15px;
            bottom:55px;
            #margin-bottom: 60px;
        }
        main h3 {
            font-size: 17px;
            position: relative;
            left: 35px;
            top: 20px;
            right: 15px;
            bottom: 25px;
        }
        main a button {
            position: relative;
            width: 150px;
            height: 40px;
            border: none;
            border-radius: 0;
            background-color: #D85D5D;
            cursor: pointer;
            font-size: 17px;
            left: 45px;
            top:30px;
            bottom: 30px;
            margin-bottom: 30px;
        }
        main a button:hover {
            background-color: #BD5555;
        }
        main section h1 {
            font-size: 22px;
            position:relative;
            left: 30px;
            right:15px;
            bottom:20px;
            top:25px;
        }
        main section h3 {
            font-size: 17px;
            position: relative;
            left: 35px;
            top: 20px;
            right: 35px;
            bottom: 25px;
        }
        main section {
            display: flex;
            flex-direction: row;
            top:50px;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
        }
        main section p {
            position: relative;
            left: 35px;
            top:45px;
        }
        .box{
            display: inline-block; /* Posiziona gli elementi uno accanto all'altro */
            padding: 10px;
            margin: 5px;
            /*border: 1px solid #000; /* Riquadro */
        }
        main section figure {
            position: relative;
            width:300px;
            height: 340px;
            top: 20px;
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
        #recensioni{
            top: 20px;
        }
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <form name="deleteProfileForm" action="Dispatcher" method="post">
        <input type="hidden" name="ID" value="<%=compratore.getId()%>">
        <input type="hidden" name="controllerAction" value="Profile.deleteProfile">
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

    <h1>Dettagli profilo</h1>
    <h3>Username: <%=compratore.getUsername()%></h3>
    <h3>Email: <%=compratore.getEmail()%></h3>
    <h3>Indirizzo di consegna: <%=compratore.getIndirizzo_consegna()%></h3>
    <h3>Numero di fossili comprati: <%=articolivendita.size()%></h3>
    <a href="javascript:deleteProfileForm.submit()">
        <button type="button">Elimina profilo</button>
    </a>

    <h1>Tutti i tuoi ordini</h1>
    <section>
        <%if(!articolivendita.isEmpty()){
            for(i=0;i<articolivendita.size();i++){
                String name = articolivendita.get(i).getNome();
                String category = articolivendita.get(i).getCategoria();
                Float price = articolivendita.get(i).getPrezzo();
                String image = http+articolivendita.get(i).getImmagine();
                //System.out.println(image);%>
        <figure>
            <a href="Dispatcher?controllerAction=Profile.viewArtComprato&articolovendita=<%=articolivendita.get(i).getId()%>">
                <img src="<%=image%>">
            </a>
            <a href="Dispatcher?controllerAction=Profile.viewArtComprato&articolovendita=<%=articolivendita.get(i).getId()%>">
                <figcaption><%= name%></figcaption>
            </a>
            <figcaption><%= price%> €</figcaption>
        </figure>
        <%}
        }else {%>
        <p>Non sono state trovati articoli a prezzo fisso</p>
        <%}%>
    </section>

    <h1>Aste a cui hai partecipato</h1>
    <section>
        <% if(!articoliasta.isEmpty()){
            for(i=0;i<articoliasta.size();i++){
                if (articoliasta.get(i).getStatus() == 0){
                String name = articoliasta.get(i).getNome();
                String category = articoliasta.get(i).getCategoria();
                Timestamp data=articoliasta.get(i).getData_scadenza();
                String image = http+articoliasta.get(i).getImmagine(); %>
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
        } else {%>
        <p>Non sono state trovate aste</p>
        <%}%>
    </section>
    <!--<h1>Recensioni lasciate</h1>
    <section id="recensioni">
        <%for(i=0;i<recensioni.size();i++) {%>
        <div class="box">
            <h3>Valutazione: <%=recensioni.get(i).getValutazione()%></h3>
            <p><%=recensioni.get(i).getCommento()%></p>
            <p><%=recensioni.get(i).getDataPubblicazione()%></p>
        </div>
        <%}%>
    </section> -->
</main>

</body>
</html>
