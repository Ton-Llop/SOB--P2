<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home - Homework2</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
            color: #333;
        }

        .header {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .welcome-container {
            font-size: 20px;
        }

        .header-buttons {
            display: flex;
            gap: 10px;
        }

        .header-buttons button {
            padding: 8px 12px;
            border: none;
            background-color: white;
            color: #007bff;
            border-radius: 4px;
            cursor: pointer;
        }

        .header-buttons button:hover {
            background-color: #0056b3;
            color: white;
        }

        .header-search {
            margin-left: auto;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .search-bar {
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 200px;
        }

        .search-button {
            padding: 6px 10px;
            border: none;
            background-color: #fff;
            color: #007bff;
            border-radius: 4px;
            cursor: pointer;
        }

        .search-button:hover {
            background-color: #0056b3;
            color: white;
        }

        .container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .article-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .article {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background-color: #f9f9f9;
        }

        .article h2 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        .article p {
            margin: 5px 0;
            font-size: 14px;
        }

        .article img {
            max-width: 100%;
            border-radius: 8px;
        }

        .footer {
            text-align: center;
            padding: 10px;
            background-color: #007bff;
            color: white;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <!-- Cabecera -->
    <header class="header">
        <div class="header-content">
            <!-- Text de benvinguda -->
            <c:choose>
                <c:when test="${isLoggedIn}">
                    <div class="welcome-container">
                        Benvingut, <strong>${username}</strong>!
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="welcome-container">
                        Benvingut, amic!
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Botons -->
            <div class="header-buttons">
                <form action="<c:url value='/Web/Filtrar'/>" method="GET">
                    <button type="submit">Filtrar</button>
                </form>
                <c:if test="${isLoggedIn}">
                    <form action="<c:url value='/Web/article/create'/>" method="GET">
                        <button type="submit">Crear Article</button>
                    </form>
                    <form action="<c:url value='/Web/Logout'/>" method="GET">
                        <button type="submit">Tancar Sessió</button>
                    </form>
                </c:if>
                <c:if test="${!isLoggedIn}">
                    <form action="<c:url value='/Web/Login'/>" method="GET">
                        <button type="submit">Iniciar Sessió</button>
                    </form>
                    <form action="<c:url value='/Web/SignUp'/>" method="GET">
                        <button type="submit">Registrar-se</button>
                    </form>
                </c:if>
            </div>
        </div>
    </header>

    <!-- Contenedor Principal -->
    <main class="container">
        <h1>Articles</h1>
        <div class="article-container">
            <c:forEach items="${articles}" var="article">
                <div class="article">
                    <h2>${article.title}</h2>
                    <p><strong>Autor:</strong> ${article.author.username}</p>
                    <p><strong>Resum:</strong> ${article.summary}</p>
                    <p><strong>Data:</strong> ${article.publicationDate}</p>
                </div>
            </c:forEach>
        </div>
    </main>

    <!-- Peu de pàgina -->
    <footer class="footer">
        Homework2 - Tots els drets reservats al grup de SOB56 &copy; 2025
    </footer>
</body>
</html>
