<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 20/09/24
  Time: 16:24
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

    //Venditore venditore = (Venditore) request.getAttribute("venditore");

    int i;
%>
<html>
<head>
    <title>Nuova Vendita</title>
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
        }
        main h1 {
            text-align: center;
        }
        main form {
            background-color: #c9b89d;
            padding: 40px;
            border-radius: 0;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 1200px;
            width: 100%;
            margin: auto;
        }
        main form label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
        }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        textarea {
            resize: vertical;
        }
        button {
            background-color: #5B533D; /* Button background color */
            color: white;              /* Text color */
            border: none;              /* No border */
            padding: 12px 24px;        /* Button padding */
            font-size: 16px;           /* Font size */
            font-weight: bold;         /* Bold text */
            border-radius: 0;        /* Rounded corners */
            cursor: pointer;           /* Pointer cursor on hover */
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #403a2b;
        }
    </style>
    <script>
        /*function sell(){
            f = document.sellForm;
            f2 = document.sell;
            f.nome.value = f2.nome.value;
            f.categoria.value = f2.categoria.value;
            f.immagine.value = f2.immagine.value;
            f.descrizione.value = f2.descrizione.value;
            f.prezzo.value = f2.prezzo.value;
            f.submit();
        }

         */
        function check(){
            f = document.sellForm;
            console.log("controllerAction: " + f.controllerAction.value);
        }
    </script>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>

    <form name="logoutForm" action="Dispatcher" method="post">
        <input type="hidden" name="controllerAction" value="Home.logout"/>
    </form>
    <!--<form name="sellForm" action="Dispatcher" method="post">
        <input type="hidden" name="Id_vend" value="<%=loggedUser.getId()%>">
        <input type="hidden" name="nome"/>
        <input type="hidden" name="categoria"/>
        <input type="hidden" name="immagine"/>
        <input type="hidden" name="descrizione"/>
        <input type="hidden" name="prezzo"/>
        <input type="hidden" name="controllerAction" value="Sell.sell"/>
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

    <h1>Inserisci il tuo prodotto in vendita</h1>

    <form name="sellForm" onsubmit="return check()" action="Dispatcher" method="post" enctype="multipart/form-data">
        <!-- ID e controllerAction -->
        <input type="hidden" name="Id_vend" value="<%=loggedUser.getId()%>">

        <!-- Nome del prodotto -->
        <label for="nome">Nome del prodotto:</label>
        <input type="text" id="nome" name="nome" placeholder="Inserisci il nome del prodotto" required>

        <!-- Categoria del prodotto -->
        <label for="categoria">Categoria del prodotto:</label>
        <select id="categoria" name="categoria" required>
            <!-- <option value="nessuna">Nessuna</option> -->
            <option value="Ammoniti">Ammoniti</option>
            <option value="Trilobiti">Trilobiti</option>
            <option value="Piante">Piante</option>
            <option value="Pesci">Pesci</option>
            <option value="Rettili">Rettili</option>
        </select>

        <!-- URL dell'immagine -->
        <label for="immagine">Immagine:</label>
        <input type="file" id="immagine" name="immagine" placeholder="Inserisci l'URL dell'immagine" required>

        <!-- Descrizione del prodotto -->
        <label for="descrizione">Descrizione del prodotto:</label>
        <textarea id="descrizione" name="descrizione" rows="4" placeholder="Inserisci una descrizione dettagliata del prodotto" maxlength="250"></textarea>
        <!--<p id="charCount">Caratteri rimanenti: 300</p> -->

        <!-- Prezzo del prodotto -->
        <label for="prezzo">Prezzo (€):</label>
        <input type="number" id="prezzo" name="prezzo" placeholder="Inserisci il prezzo del prodotto" step="0.01" required>

        <input type="hidden" name="controllerAction" value="Sell.sell"/>

        <!-- Pulsante di invio -->
        <button type="submit">Vendi</button>
    </form>

</main>

</body>
</html>
