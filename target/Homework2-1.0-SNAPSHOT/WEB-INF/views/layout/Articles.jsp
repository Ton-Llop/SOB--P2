<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Llistat d'Articles</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        .article-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
        }
        .article {
            border: 1px solid #ccc;
            padding: 15px;
            border-radius: 8px;
            background-color: #f9f9f9;
            max-width: 300px;
            flex: 1 1 calc(33.333% - 20px);
            box-sizing: border-box;
        }
        .article img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
        }
        .error-message {
            color: red;
            font-weight: bold;
            margin: 10px 0;
        }
    </style>
</head>
<body>
    <header>
        <h1>Llistat d'Articles</h1>
    </header>
    <main>
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty articles}">
            <div class="article-container">
                <c:forEach items="${articles}" var="article">
                    <div class="article">
                        <h2>${article.title}</h2>
                        <p><strong>Autor:</strong> ${article.author.username}</p>
                        <p><strong>Descripció:</strong> ${article.content}</p>
                        <p><strong>Data de publicació:</strong> ${article.publicationDate}</p>
                        <p><strong>Visualitzacions:</strong> ${article.views}</p>
                        <p><strong>Tòpics:</strong> 
                            <c:forEach items="${article.topics}" var="topic">
                                ${topic}
                            </c:forEach>
                        </p>
                        <c:if test="${not empty article.image}">
                            <img src="${article.image}" alt="Imatge de l'article" />
                        </c:if>
                        <p><strong>Privat:</strong> 
                            <c:choose>
                                <c:when test="${article.isPrivate}">Sí</c:when>
                                <c:otherwise>No</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty articles}">
            <p class="error-message">No hi ha articles disponibles per mostrar.</p>
        </c:if>
    </main>
</body>
</html>
