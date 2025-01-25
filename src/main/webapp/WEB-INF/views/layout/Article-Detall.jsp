<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Detall de l'Article</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            font-size: 24px;
            color: #007bff;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            line-height: 1.6;
        }

        .image-container img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
            margin-top: 20px;
        }

        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <c:if test="${not empty article}">
            <!-- Títol de l'article -->
            <h1>${article.title}</h1>

            <!-- Autor -->
            <p><strong>Autor:</strong> ${article.author.username}</p>

            <!-- Data de publicació -->
            <p><strong>Data de publicació:</strong> 
                <c:out value="${article.publicationDate}" />
            </p>

            <!-- Visualitzacions -->
            <p><strong>Visualitzacions:</strong> ${article.views}</p>

            <!-- Descripció -->
            <p><strong>Contingut:</strong> ${article.content}</p>

            <!-- Tòpics -->
            <p><strong>Tòpics:</strong> 
                <c:forEach items="${article.topics}" var="topic">
                    ${topic}
                </c:forEach>
            </p>

            <!-- Privat -->
            <p><strong>Privat:</strong> 
                <c:choose>
                    <c:when test="${article.isPrivate}">Sí</c:when>
                    <c:otherwise>No</c:otherwise>
                </c:choose>
            </p>

            <!-- Imatge -->
            <c:if test="${not empty article.image}">
                <div class="image-container">
                    <img src="${article.image}" alt="Imatge de l'article">
                </div>
            </c:if>
        </c:if>

        <!-- Missatge si no hi ha article -->
        <c:if test="${empty article}">
            <p style="color: red;">No s'ha pogut trobar l'article.</p>
        </c:if>

        <!-- Botó per tornar enrere -->
        <a href="<c:url value='/Web/Articles'/>" class="back-button">Tornar al llistat</a>
        <a href="<c:url value='/Web/Filtrar'/>" class="back-button">Tornar als Filtres</a>
        
    </div>
</body>
</html>
