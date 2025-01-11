<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Articles Filtrats</title>
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .article {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 15px;
            background-color: #f9f9f9;
        }

        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <main class="container">
        <h1>Articles Filtrats</h1>

        <!-- Comprovar si hi ha articles -->
        <c:choose>
            <c:when test="${not empty articles}">
                <!-- Mostra els articles filtrats -->
                <c:forEach var="article" items="${articles}">
                    <div class="article">
                        <h2>${article.title}</h2>
                        <p><strong>Autor:</strong> ${article.author.username}</p>
                        <p><strong>Tòpics:</strong> ${article.topics}</p>
                        <p><strong>Contingut:</strong> ${article.content}</p>
                        <p><strong>Publicat el:</strong> ${article.publicationDate}</p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- Mostra un missatge d'error -->
                <p class="error">No s'han trobat articles amb els criteris seleccionats.</p>
                <a href="<c:url value='/filtrar'/>">Torna al formulari de filtres</a>
            </c:otherwise>
        </c:choose>
    </main>
</body>
</html>
