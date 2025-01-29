<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Llistat d'Articles</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        /* Estilo general */
        .article-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr); /* 3 columnas en pantallas grandes */
            gap: 20px;
            margin-top: 20px;
        }

        .article {
            border: 1px solid #ccc;
            padding: 15px;
            border-radius: 8px;
            background-color: #f9f9f9;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .article img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .error-message {
            color: red;
            font-weight: bold;
            margin: 10px 0;
            text-align: center;
        }

        .article a {
            text-decoration: none;
            color: #007bff;
        }

        .article a:hover {
            text-decoration: underline;
        }

        .back-home {
            margin-top: 20px;
            text-align: center;
        }

        .back-home button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .back-home button:hover {
            background-color: #0056b3;
        }

        /* Media Queries para Responsividad */
        @media (max-width: 1024px) {
            .article-container {
                grid-template-columns: repeat(2, 1fr); /* 2 columnas en pantallas medianas */
            }
        }

        @media (max-width: 768px) {
            .article-container {
                grid-template-columns: repeat(1, 1fr); /* 1 columna en pantallas pequeñas */
            }
        }

        @media (max-width: 480px) {
            .article {
                padding: 10px; /* Reducir el padding en pantallas muy pequeñas */
            }
        }
    </style>
</head>
<body>
    <header>
        <h1>Llistat d'Articles</h1>
    </header>
    <main>
        <!-- Botón para volver al Home -->
        <div class="back-home">
            <form action="<c:url value='/Web/Home'/>" method="GET">
                <button type="submit">Home</button>
            </form>
        </div>

        <!-- Mostrar mensaje si no hay artículos -->
        <c:if test="${not empty message}">
            <p>${message}</p>
        </c:if>

        <!-- Mostrar mensaje de error si existe -->
        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>

        <!-- Mostrar listado de artículos -->
        <c:if test="${not empty articles}">
            <div class="article-container">
                <c:forEach items="${articles}" var="article">
                    <div class="article">
                        <!-- Título con enlace -->
                        <h2>
                            <a href="<c:url value='/Web/Article-Detall?id=${article.id}'/>">
                                ${article.title}
                            </a>
                        </h2>

                        <!-- Autor -->
                        <p><strong>Autor:</strong> ${article.author.username}</p>

                        <!-- Descripción -->
                        <p><strong>Descripció:</strong> ${article.content}</p>

                        <!-- Fecha de publicación -->
                        <p><strong>Data de publicació:</strong> 
                            <c:out value="${article.publicationDate}" />
                        </p>

                        <!-- Visualizaciones -->
                        <p><strong>Visualitzacions:</strong> ${article.views}</p>

                        <!-- Tópicos -->
                        <p><strong>Tòpics:</strong> 
                            <c:forEach items="${article.topics}" var="topic">
                                ${topic}
                            </c:forEach>
                        </p>

                        <!-- Mostrar imagen si existe -->
                        <c:if test="${not empty article.image}">
                            <img src="${article.image}" alt="Imatge de l'article" />
                        </c:if>

                        <!-- Privado -->
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

        <!-- Mensaje cuando no hay artículos -->
        <c:if test="${empty articles}">
            <p class="error-message">No hi ha articles disponibles per mostrar.</p>
        </c:if>
    </main>
</body>
</html>
