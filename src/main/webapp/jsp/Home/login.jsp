<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 13/07/24
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String applicationMessage = (String) request.getAttribute("applicationMessage");
%>
<html>
<head>
    <title>Log-in</title>
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
            background-color: #B19770;
            width: 100%;
            display: flex;
            position:relative;
            top: 90px;
            bottom: 90px;
        }
        main img {
            height: 800px;
            width: 700px;
        }
        main form {

        }
        main form h1 {
            font-size: 40px;
            position:relative;
            left: 120px;
            top: 65px;
            margin-bottom: 10px;
        }
        main form h2 {
            font-size: 20px;
            position:relative;
            left: 120px;
            top:45px;
            margin-bottom: 150px;
        }
        main form h2 a{
            color: black;
        }
        main form h2 a:hover {
            color: black;
            cursor: pointer;
        }
        .form-group{
            position:relative;
            left: 120px;
            margin-bottom: 40px;
        }
        .form-group label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 25px;
        }
        input[type="text"],
        input[type="password"]{
            border: none;
            border-bottom: 1px solid black;
            padding-left: 10px;
            padding-bottom: 5px;
            background-color: #B19770;
            width: 250px;
            font-size: 19px;
        }
        input[type="text"]:focus,
        input[type="password"]:focus{
            border: none;
            border-bottom: 1px solid black;
            padding-left: 10px;
            padding-bottom: 5px;
            background-color: #B19770;
            width: 250px;
            font-size: 19px;
        }
        main form button {
            position: relative;
            margin-top: 30px;
            left: 120px;
            width: 120px;
            height: 60px;
            border: none;
            border-radius: 0;
            background-color: #F8D4A0;
            cursor: pointer;
            font-size: 20px;
        }
        main form button:hover {
            background-color: #DFBB88;
        }
        footer {
            background-color: #5B533D;
            display: flex;
            width: 100%;
            height: 110px;
            margin-top: auto;
        }
        .alert {
            padding: 15px;
            margin: 10px 0;
            border-radius: 5px;
            display: flex;
            align-items: center; /* Allinea verticalmente l'icona e il testo */
            font-family: Arial, sans-serif;
            font-size: 16px;
        }
        .alert-warning {
            background-color: #fff3cd; /* Giallo chiaro */
            color: #856404; /* Giallo scuro */
            border: 1px solid #ffeeba; /* Giallo più scuro */
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

<%if(applicationMessage != null) {%>
<div class="alert alert-warning" role="alert">
    <strong><%=applicationMessage%></strong>
</div>
<%}%>

<main>
    <img src="images/signin.jpg" alt="Image 1">
    <form action="Dispatcher" method="post">
        <h1>Entra nel tuo account</h1>
        <h2>Inserisci i tuoi dati</h2>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <input type="hidden" name="controllerAction" value="Home.logon"/>
        <button type="submit">Log-in</button>
    </form>
</main>

<footer>
    <!--<p>my footer</p>-->
</footer>

</body>
</html>
