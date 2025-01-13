<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registro Exitoso - Homework2</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }
        .message-container {
            text-align: center;
            margin: 20px auto;
            padding: 20px;
            background-color: #e0ffe0;
            border: 1px solid #a3d4a3;
            border-radius: 8px;
            color: #007bff;
            font-size: 20px;
        }
    </style>
</head>
<body>
    <!-- Cabecera -->
    <header class="header">
        <div class="header-content">
            <!-- Mensaje de bienvenida -->
            <div class="welcome-container">
                Benvingut, <strong>${username}</strong>! El teu registre ha estat satisfactori.
            </div>

            <!-- Botones -->
            <div class="header-buttons">
                <form action="<c:url value='/Web/Home'/>" method="GET">
                    <button type="submit">Anar al Home</button>
                </form>
                <form action="<c:url value='/Web/Logout'/>" method="GET">
                    <button type="submit">Tancar Sessió</button>
                </form>
            </div>
        </div>
    </header>

    <!-- Contenido principal -->
    <main class="container">
        <div class="message-container">
            <p>Gràcies per registrar-te, <strong>${username}</strong>! Ja pots començar a navegar i crear articles.</p>
        </div>
        <h1>Articles</h1>
        <a href="<c:url value='/Web/Articles'/>" class="button-large">Veure Articles</a>
    </main>

    <!-- Pie de página -->
    <footer class="footer">
        Homework2 - Tots els drets reservats al grup de SOB56 &copy; 2025
    </footer>
</body>
</html>
