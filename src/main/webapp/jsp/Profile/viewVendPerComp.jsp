<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 17/09/24
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.*" %>
<%@ page import="java.sql.Timestamp" %>
<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Profile";

    List<Articolo> articoli = (List<Articolo>) request.getAttribute("articoli");
    List<ArticoloVendita> articolivendita = new ArrayList<>();
    articolivendita = (List<ArticoloVendita>) request.getAttribute("articolivendita");
    List<ArticoloAsta> articoliasta = new ArrayList<>();
    articoliasta = (List<ArticoloAsta>) request.getAttribute("articoliasta");
    Venditore venditore = (Venditore) request.getAttribute("venditore");
    Recensione recensione = (Recensione) request.getAttribute("recensione");
    boolean has_bought = (boolean) request.getAttribute("has_bought");
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
            height: 1600px;
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
        .venduto {
            width:300px;
            height: 300px;
            filter: grayscale(40%);
        }
        .text {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: rgba(0, 0, 0, 0.5);
            color: white;  /* Colore del testo */
            padding: 10px;  /* Spaziatura interna del riquadro */
            border-radius: 0; /* Smussa gli angoli del riquadro */
            font-size: 15px;
            #font-weight: bold;
        }
        .box{
            display: inline-block; /* Posiziona gli elementi uno accanto all'altro */
            padding: 10px;
            margin: 5px;
            /*border: 1px solid #000; /* Riquadro */
        }
        main section figure figcaption {
            position: relative;
            top:5px;
            left: 10px;
        }
        #recensioni{
            top: 20px;
        }
        /*roba di chatgpt*/
        /*form[name="review"] {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Aggiunge ombreggiatura
            max-width: 500px;  Imposta una larghezza massima per il form
            margin: 20px auto;  Centra il form orizzontalmente
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        form[name="review"] label {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .rating {
            display: flex;
            justify-content: space-between;
            width: 100%;
        }

        .rating input[type="radio"] {
            display: none;  Nasconde i radio button
        }

        .rating label {
            cursor: pointer;
            font-size: 20px;
            padding: 10px;
            color: #FFD700;  Colore giallo per le stelle
            transition: transform 0.3s;
        }

        .rating input[type="radio"]:checked + label {
            transform: scale(1.2);  Effetto di ingrandimento quando selezionato
        }

        textarea {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 14px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            resize: vertical;
        }

        .submit-btn {
            background-color: #5B533D;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        .submit-btn:hover {
            background-color: #444; /* Cambia colore al passaggio del mouse
        }*/
        form[name="review"] {
            display: flex;               /* Usa flexbox per il layout orizzontale */
            justify-content: flex-start; /* Allinea gli elementi a sinistra */
            align-items: flex-start;     /* Allinea gli elementi in alto */
            max-width: 1000px;           /* Aumenta la larghezza massima del form */
            margin: 20px 0;              /* Rimuovi il centraggio del form */
            padding: 20px;
            background-color: #c9b89d;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            gap: 20px;                   /* Spaziatura tra gli elementi */
        }

        .rating {
            display: flex;               /* Disporre gli input radio orizzontalmente */
            flex-direction: row;         /* Assicura che gli elementi siano in linea */
            gap: 5px;                    /* Spaziatura tra le stelle */
            align-items: center;         /* Allinea verticalmente con il resto del form */
        }

        textarea {
            flex: 1;                     /* Permetti al textarea di occupare lo spazio disponibile */
            resize: vertical;            /* Permetti la modifica verticale del commento */
            min-height: 80px;
            margin-left: 20px;           /* Aggiungi spaziatura tra il rating e il textarea */
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /*prima non c'era*/
            font-size: 14px;  /*prima non c'era*/
            padding: 10px;  /*prima non c'era*/
            border-radius: 5px;  /*prima non c'era*/
        }

        .submit-btn {
            align-self: flex-start;      /* Allinea il bottone in alto */
            padding: 10px 20px;
            background-color: #5B533D;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #444;
        }

        .rating label {
            margin-right: 10px; /* Spaziatura tra le etichette e gli input radio */
        }
        #tua{
            margin-bottom: 6px;
            bottom: 6px;
            color: #444444;
        }
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <!--<form name="reviewForm" action="Dispatcher" method="post">
        <input type="hidden" name="rating"/>
        <input type="hidden" name="comment"/>
        <input type="hidden" name="Id_compratore" value="<%=loggedUser.getId()%>">
        <input type="hidden" name="Id_venditore" value="<%=venditore.getId()%>">
        <input type="hidden" name="controllerAction" value="Profile.pubblicaRecensione"/>
    </form> -->

    <nav>
        <ul>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="javascript:logoutForm.submit()">Log-out</a></li>
            <li><a href="Dispatcher?controllerAction=Profile.view&username=<%=loggedUser.getUsername()%>">Profilo</a></li>
        </ul>
    </nav>
</header>

<main>
    <h1>Dettagli profilo</h1>
    <h3>Username: <%=venditore.getUsername()%></h3>
    <h3>Email: <%=venditore.getEmail()%></h3>
    <h3>Indirizzo di spedizione: <%=venditore.getIndirizzo_spedizione()%></h3>
    <h3>Numero di fossili messi in vendita/asta: <%=articolivendita.size() + articoliasta.size()%></h3>

    <h1>Fossili in vendita</h1>
    <section>
        <%if(!articolivendita.isEmpty()){
            for(i=0;i<articolivendita.size();i++){
                String name = articolivendita.get(i).getNome();
                String category = articolivendita.get(i).getCategoria();
                Float price = articolivendita.get(i).getPrezzo();
                String image = articolivendita.get(i).getImmagine();
                if(articolivendita.get(i).getStatus() == 0  && !articolivendita.get(i).isDeleted()) {%>
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
        }
        }else {%>
        <p>Non sono state trovati articoli a prezzo fisso</p>
        <%}%>

    </section>
    <h1>Fossili in asta</h1>
    <section>
        <% if(!articoliasta.isEmpty()){
            for(i=0;i<articoliasta.size();i++){
                String name = articoliasta.get(i).getNome();
                String category = articoliasta.get(i).getCategoria();
                Timestamp data=articoliasta.get(i).getData_scadenza();
                String image = articoliasta.get(i).getImmagine();
                if(articoliasta.get(i).getStatus() == 0 && !articoliasta.get(i).isDeleted()) {%>
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
    <h1>Lascia una recensione al venditore</h1>
    <section>
    <%if(has_bought) {
        if(recensione != null) {  %>
            <h3>Hai già lasciato una recensione</h3>
    <%  }else {%>
            <form name="review" action="Dispatcher" method="post">
                <!-- Sezione per la valutazione -->
                <label for="rating">Valutazione (1 a 5):</label>
                <div class="rating" id="rating">
                    <input type="radio" id="star1" name="rating" value="1" required>
                    <label for="star1">1</label>

                    <input type="radio" id="star2" name="rating" value="2">
                    <label for="star2">2</label>

                    <input type="radio" id="star3" name="rating" value="3">
                    <label for="star3">3</label>

                    <input type="radio" id="star4" name="rating" value="4">
                    <label for="star4">4</label>

                    <input type="radio" id="star5" name="rating" value="5">
                    <label for="star5">5</label>
                </div>
                <!-- Sezione per il commento -->
                <label for="comment">Commento:</label>
                <textarea id="comment" name="comment" rows="4" placeholder="Scrivi qui il tuo commento..." maxlength="250" required></textarea>

                <input type="hidden" name="Id_compratore" value="<%=loggedUser.getId()%>">
                <input type="hidden" name="Id_venditore" value="<%=venditore.getId()%>">
                <input type="hidden" name="controllerAction" value="Profile.pubblicaRecensione"/>

                <!-- Pulsante per inviare la recensione -->
                <button type="submit" class="submit-btn">Pubblica</button>
            </form>
    <%  }
    } else {%>
        <p>Devi prima aver ricevuto un ordine da questo venditore prima di poter lasciare una recensione</p>
    <%}%>
    </section>
    <h1>Recensioni</h1>
    <section id="recensioni">
        <%for(i=0;i<recensioni.size();i++) {%>
            <div class="box">
                <%if(recensioni.get(i).getCompratoreId() == loggedUser.getId()) {%>
                <p id="tua">La tua recensione</p>
                <%}%>
                <h3>Valutazione: <%=recensioni.get(i).getValutazione()%></h3>
                <p><%=recensioni.get(i).getCommento()%></p>
                <p><%=recensioni.get(i).getDataPubblicazione()%></p>
            </div>
        <%}%>
    </section>
</main>

</body>
</html>

