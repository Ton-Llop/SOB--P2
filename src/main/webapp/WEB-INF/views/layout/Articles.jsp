<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Llistat d'Articles</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
</head>
<body>
    <header>
        <h1>Llistat d'Articles</h1>
    </header>
    <main>
        <!-- Mostrar missatge si no hi ha articles -->
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>

        <!-- Mostrar llistat d'articles -->
        <c:if test="${not empty articles}">
            <div class="article-container">
                <c:forEach items="${articles}" var="article">
                    <div class="article">
                        <h2>${article.title}</h2>
                        <p><strong>Autor:</strong> ${article.author}</p>
                        <p><strong>Descripció:</strong> ${article.content}</p>
                        <p><strong>Data de publicació:</strong> ${article.publicationDate}</p>
                        <p><strong>Visualitzacions:</strong> ${article.views}</p>
                        <p><strong>Tòpics:</strong> 
                            <c:forEach items="${article.topics}" var="topic">${topic} </c:forEach>
                        </p>

                        <!-- Mostrar imatge si existeix -->
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

        <!-- Mostrar missatge d'error si existeix -->
        <c:if test="${not empty errorMessage}">
            <p style="color:red;">${errorMessage}</p>
        </c:if>
    </main>
</body>
</html>
