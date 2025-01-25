<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Articles Filtrats</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>" />
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

        .article {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
            background-color: #f9f9f9;
        }

        .article h2 {
            font-size: 20px;
            color: #007bff;
        }

        .article p {
            margin: 5px 0;
        }

        .detail-button {
            background-color: #007bff;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 4px;
            font-size: 14px;
            display: inline-block;
            margin-top: 10px;
        }

        .detail-button:hover {
            background-color: #0056b3;
        }

        .no-articles {
            text-align: center;
            margin-top: 50px;
        }

        .back-button {
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 4px;
            font-size: 14px;
            display: inline-block;
            margin-top: 20px;
        }

        .back-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <header>
        <h1>Articles Filtrats</h1>
    </header>
    <main class="container">
        <!-- Comprovar si hi ha articles -->
        <c:choose>
            <c:when test="${not empty articles}">
                <!-- Mostra els articles filtrats -->
                <c:forEach var="article" items="${articles}">
                    <div class="article">
                        <h2>${article.title}</h2>
                        <p><strong>Autor:</strong> ${article.author.username}</p>
                        <p><strong>Tòpics:</strong> 
                            <c:forEach var="topic" items="${article.topics}">
                                <span>${topic}</span>
                            </c:forEach>
                        </p>
                        <p><strong>Contingut:</strong> ${article.content}</p>
                        <p><strong>Publicat el:</strong> ${article.publicationDate}</p>

                        <!-- Botón para ver detalles del artículo -->
                        <a href="<c:url value='/Web/Article-Detall?id=${article.id}'/>" class="detail-button">Veure Detalls</a>
                    </div>
                </c:forEach>
                <!-- Botón para volver al formulario de filtros -->
                <div class="no-articles">
                    <a href="<c:url value='/Web/Filtrar'/>" class="back-button">Torna al formulari de filtres</a>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Missatge d'error si no hi ha articles -->
                <div class="no-articles">
                    <p class="error">No s'han trobat articles amb els criteris seleccionats.</p>
                    <a href="<c:url value='/Web/Filtrar'/>" class="back-button">Torna al formulari de filtres</a>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
</body>
</html>
