<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Filtrar Articles</title>
    <style>
        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }

        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
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
    </style>
</head>
<body>
    <main class="container">
        <h1>Filtrar Articles</h1>
        <form action="<c:url value='/Web/Filtrar'/>" method="POST">
            <!-- Llista desplegable per filtrar per autor -->
            <label for="autor">Selecciona un autor:</label>
            <select name="autor" id="autor">
                <option value="">Tots els autors</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author}">${author}</option>
                </c:forEach>
            </select>

            <!-- Llista desplegable per filtrar per tòpic -->
            <label for="topics">Selecciona un tòpic:</label>
            <select name="topics" id="topics">
                <option value="">Tots els tòpics</option>
                <c:forEach var="topic" items="${topics}">
                    <option value="${topic}">${topic}</option>
                </c:forEach>
            </select>

            <!-- Botons -->
            <button type="submit">Aplicar Filtres</button>
            <button type="reset">Reiniciar</button>
        </form>
    </main>
</body>
</html>
