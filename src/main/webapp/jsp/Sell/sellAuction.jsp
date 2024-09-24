<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 20/09/24
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.geobusiness.geobusiness.model.mo.*" %>
<%
    boolean loggedOn = (Boolean) request.getAttribute("loggedOn");
    Utente loggedUser = (Utente) request.getAttribute("loggedUser");
    String applicationMessage = (String) request.getAttribute("applicationMessage");
    String menuActiveLink = "Profile";

    int i;
%>
<html>
<head>
    <title>Nuova Asta</title>
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
        }
    </style>
    <script>
        function sell(){
            f = document.auctionForm;
            f2 = document.auction;
            f.nome.value = f2.nome.value;
            f.categoria.value = f2.categoria.value;
            f.immagine.value = f2.immagine.value;
            f.descrizione.value = f2.descrizione.value;
            f.data_scad.value = f2.data_scad.value;
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
    <form name="auctionForm" action="Dispatcher" method="post">
        <input type="hidden" name="Id_vend" value="<%=loggedUser.getId()%>">
        <input type="hidden" name="nome"/>
        <input type="hidden" name="categoria"/>
        <input type="hidden" name="immagine"/>
        <input type="hidden" name="descrizione"/>
        <input type="hidden" name="data_scad"/>
        <input type="hidden" name="controllerAction" value="Sell.sell"/>
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

    <h1>Inserisci il tuo prodotto in vendita</h1>

    <form name="auction" onsubmit="sell(); return false;">
        <!-- Nome del prodotto -->
        <label for="nome">Nome del prodotto:</label>
        <input type="text" id="nome" name="nome" placeholder="Inserisci il nome del prodotto" required>

        <!-- Categoria del prodotto -->
        <label for="categoria">Categoria del prodotto:</label>
        <select id="categoria" name="categoria" required>
            <option value="nessuna">Nessuna</option>
            <option value="Ammoniti">Ammoniti</option>
            <option value="Trilobiti">Trilobiti</option>
            <option value="Ambre">Ambre</option>
            <option value="Pesci">Pesci</option>
            <option value="Rettili">Rettili</option>
        </select>

        <!-- URL dell'immagine -->
        <label for="immagine">URL dell'immagine:</label>
        <input type="text" id="immagine" name="immagine" placeholder="Inserisci l'URL dell'immagine" required>

        <!-- Descrizione del prodotto -->
        <label for="descrizione">Descrizione del prodotto:</label>
        <textarea id="descrizione" name="descrizione" rows="4" placeholder="Inserisci una descrizione dettagliata del prodotto" maxlength="250"></textarea>
        <!--<p id="charCount">Caratteri rimanenti: 300</p> -->

        <!-- devo far mettere data e ora -->
        <!-- Prezzo del prodotto -->
        <label for="data_scad">Scegli data e ora:</label>
        <input type="datetime-local" id="data_scad" name="data_scad" required>

        <!-- Pulsante di invio -->
        <button type="submit">Vendi</button>
    </form>

</main>

</body>
</html>
