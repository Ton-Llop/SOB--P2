<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Detall de l'Article</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/estilPrincipal.css'/>">
</head>
<body>
    <main class="container">
        <h1>${article.title}</h1>
        <img src="${article.imageUrl}" alt="${article.title}" style="max-width: 100%; height: auto; margin-top: 20px;" />
        <p><strong>Autor:</strong> ${article.author}</p>
        <p><strong>Data de publicació:</strong> ${article.publicationDate}</p>
        <p><strong>Visualitzacions:</strong> ${article.views}</p>
        <p><strong>Resum:</strong></p>
        <p>${article.summary}</p>
        <p><strong>Contingut:</strong></p>
        <p>${article.content}</p>

        <!-- Botón para volver al listado de artículos -->
        <a href="<c:url value='/articles'/>" class="back-button">Torna al llistat d'articles</a>
    </main>
</body>
</html>
