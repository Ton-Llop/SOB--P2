<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Filtrar Articles</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }

        header {
            text-align: center;
            margin-bottom: 20px;
            background-color: #007bff;
            color: white;
            padding: 10px 0;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 20px;
        }

        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        .button-container {
            margin-top: 20px;
            display: flex;
            justify-content: space-between; /* Asegura separación entre botones */
            gap: 10px;
        }

        button {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        button[type="submit"] {
            background-color: #007bff;
            color: white;
        }

        button[type="reset"] {
            background-color: #f8f9fa;
            color: #333;
        }

        button:hover {
            opacity: 0.9;
        }

        .home-button {
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 4px;
            font-size: 14px;
            display: inline-block;
        }

        .home-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <header>
        <h1>Filtrar Articles</h1>
    </header>
    <main class="container">
        <form action="<c:url value='/Web/Filtrar'/>" method="POST">
            <!-- Filtro por autor -->
            <label for="autor">Selecciona un autor:</label>
            <select name="autor" id="autor">
                <option value="">Tots els autors</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author}">${author}</option>
                </c:forEach>
            </select>

            <!-- Filtro por tópico -->
            <label for="topics">Selecciona un tòpic:</label>
            <select name="topics" id="topics">
                <option value="">Tots els tòpics</option>
                <c:forEach var="topic" items="${topics}">
                    <option value="${topic}">${topic}</option>
                </c:forEach>
            </select>

            <!-- Botones -->
            <div class="button-container">
                <!-- Botón para volver a Home.jsp -->
                <a href="<c:url value='/Web/Home'/>" class="home-button">Torna al Home</a>

                <!-- Botones existentes -->
                <button type="reset">Reiniciar</button>
                <button type="submit">Aplicar Filtres</button>
            </div>
        </form>
    </main>
</body>
</html>
