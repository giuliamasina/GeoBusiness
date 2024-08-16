<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/08/24
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Utente" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Articolo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloVendita" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.ArticoloAsta" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.Compratore" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
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

    int i;
%>
<html>
<head>
    <title>Profilo</title>
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
            <li><a href="Dispatcher?controllerAction=Profile.view&username=<%=loggedUser.getUsername()%>">Profilo</a></li>
            <%}%>
        </ul>
    </nav>
</header>

<main>
    <section>
        <h1>Dettagli profilo:</h1>
        <h3>Username: </h3>
        <h3>Indirizzo di consegna: </h3>
        <h3>Numero di fossili comprati: </h3>
        <a>
            <button type="button">Elimina profilo</button>
        </a>
    </section>
    <section>
        <h1>Tutti i tuoi ordini</h1>
        <%if(!articolivendita.isEmpty()){
            for(i=0;i<articolivendita.size();i++){
                String name = articolivendita.get(i).getNome();
                String category = articolivendita.get(i).getCategoria();
                Float price = articolivendita.get(i).getPrezzo();
                String image = articolivendita.get(i).getImmagine(); %>
        <figure>
            <a href="Dispatcher?controllerAction=Profile.itemview&articolovendita=<%=articolivendita.get(i).getId()%>">
                <img src="<%=image%>">
            </a>
            <a href="Dispatcher?controllerAction=Profile.itemview&articolovendita=<%=articolivendita.get(i).getId()%>">
                <figcaption><%= name%></figcaption>
            </a>
            <figcaption><%= price%></figcaption>
        </figure>
        <%}
        }else {%>
        <p>Non sono state trovati articoli a prezzo fisso</p>
        <%}%>
    </section>
    <section>
        <h1>Aste a cui hai partecipato</h1>
        <% if(!articoliasta.isEmpty()){
            for(i=0;i<articoliasta.size();i++){
                String name = articoliasta.get(i).getNome();
                String category = articoliasta.get(i).getCategoria();
                Date data=articoliasta.get(i).getData_scadenza();
                String image = articolivendita.get(i).getImmagine(); %>
        <figure>
            <a href="Dispatcher?controllerAction=Profile.auctionview&articoloasta=<%=articoliasta.get(i).getId()%>">
                <img src="<%=image%>">
            </a>
            <a href="Dispatcher?controllerAction=Profile.auctionview&articoloasta=<%=articoliasta.get(i).getId()%>">
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
