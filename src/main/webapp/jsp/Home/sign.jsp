<%--
  Created by IntelliJ IDEA.
  User: giuggiu
  Date: 12/07/24
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Iscriviti</title>
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
        #address {
            border: none;
            border-bottom: 1px solid black;
            padding-left: 10px;
            padding-bottom: 5px;
            background-color: #B19770;
            width: 400px;
            font-size: 19px;
        }
        #address:focus {
            border: none;
            border-bottom: 1px solid black;
            padding-left: 10px;
            padding-bottom: 5px;
            background-color: #B19770;
            width: 400px;
            font-size: 19px;
        }
        .form-group-select {
            position: relative;
            left:120px;
            margin-bottom: 28px;
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
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>
    <nav>
        <ul>
            <li><a href="view.jsp">Home</a></li>
            <li><a href="sign.jsp">Iscriviti</a></li>
            <li><a href="login.jsp">Log-in</a></li>
        </ul>
    </nav>
</header>

<main>
    <img src="https://via.placeholder.com/150" alt="Image 1">
    <form action="#" method="post">
        <h1>Crea il tuo Account</h1>
        <h2>Hai già un account? Fai il <a href="#">Login</a></h2>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="address">Indirizzo di spedizione/consegna</label>
            <input type="text" id="address" name="address" placeholder="Scrivi indirizzo come: Via Bologna 2, Padova">
        </div>
        <div class="form-group-select">
            <label>Vuoi vendere o comprare?</label>
            <div class="inline">
                <input type="radio" id="vendere" name="ruolo" value="vendere">
                <label for="vendere">Vendere</label>
                <input type="radio" id="comprare" name="ruolo" value="comprare">
                <label for="comprare">Comprare</label>
            </div>
        </div>
        <button type="submit">Iscriviti</button>
    </form>
</main>

<footer>

</footer>

</body>
</html>
