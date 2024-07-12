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
            margin-bottom: 150px;
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
        }
        .form-group-select {
            position: relative;
            left:120px;
            margin-bottom: 28px;
        }
    </style>
</head>
<body>

<header>
    <h1>GeoBusiness</h1>
    <nav>
        <ul>
            <li><a href="#">Home</a></li>
            <li><a href="#">Iscriviti</a></li>
            <li><a href="#">Log-in</a></li>
        </ul>
    </nav>
</header>

<main>
    <img src="https://via.placeholder.com/150" alt="Image 1">
    <form action="#" method="post">
        <h1>Crea il tuo Account</h1>
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group-select">
            <label>Vuoi vendere o comprare?</label>
            <div class="inline">
                <input type="radio" id="vendere" name="gender" value="vendere">
                <label for="vendere">Vendere</label>
                <input type="radio" id="comprare" name="gender" value="comprare">
                <label for="comprare">Comprare</label>
            </div>
        </div>
        <button type="submit">Iscriviti</button>
    </form>
</main>

</body>
</html>
