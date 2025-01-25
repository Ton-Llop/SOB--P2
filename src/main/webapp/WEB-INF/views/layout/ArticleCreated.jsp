<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Article Creat</title>
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
            <!-- Mensaje de éxito -->
            <h1>Article Creat Amb Èxit</h1>

            <!-- ID -->
            <p><strong>ID:</strong> ${article.id}</p>

            <!-- Títol -->
            <p><strong>Títol:</strong> ${article.title}</p>

            <!-- Autor -->
            <p><strong>Autor:</strong> ${article.author.username}</p>

            <!-- Data de publicació -->
            <p><strong>Data de publicació:</strong> 
                <c:out value="${article.publicationDate}" />
            </p>

            <!-- Visualitzacions -->
            <p><strong>Visualitzacions:</strong> ${article.views}</p>

            <!-- Contingut -->
            <p><strong>Contingut:</strong> ${article.content}</p>

            <!-- Tòpics -->
            <p><strong>Tòpics:</strong> 
                <c:forEach var="topic" items="${article.topics}">
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

        <!-- Botó per tornar al Home -->
        <a href="<c:url value='/Web/Home'/>" class="back-button">Torna al Home</a>
    </div>
</body>
</html>
