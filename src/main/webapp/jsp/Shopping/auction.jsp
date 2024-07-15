<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 15/07/24
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        }
        main {
            padding: 0;
            height: 600px;
            background-color: #B19770;
            width: 100%;
            display: flex;
            flex-direction: row;
            position:relative;
            top: 90px;
            bottom: 90px;
        }
        main section {
            display: flex;
            flex-direction: column;
            margin: 20px;
        }
        #info {
            margin-left: 120px;
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
        }
        #nome {
            position: relative;
            left:120px;
            margin-top: 90px;
        }
        #prezzo,#venditore,#data {
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
</head>
<body>

<header>
    <h1>GeoBusiness</h1>
    <nav>
        <ul>
            <li><a href="Dispatcher?controllerAction=Home.view">Home</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewsign">Iscriviti</a></li>
            <li><a href="Dispatcher?controllerAction=Home.viewlogin">Log-in</a></li>
        </ul>
    </nav>
</header>

<main>
    <section>
        <img src="https://via.placeholder.com/150" alt="Image 1">
    </section>
    <section>
        <h2 id="descrizione">Descrizione</h2>
    </section>
    <section id="info">
        <h1 id="nome">Nome</h1>
        <h2 id="venditore">Venditore: </h2>
        <h2 id="data">Scade il: </h2>
        <h2 id="prezzo">Ultima offerta:  </h2>
        <button type="button">Fai Offerta</button>
    </section>
</main>

<footer>
    <p>my footer</p>
</footer>
</body>
</html>
