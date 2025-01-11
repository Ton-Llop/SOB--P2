<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Articles</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        .article-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            margin-top: 20px;
        }

        .article {
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            display: flex;
            gap: 20px;
        }

        .article img {
            max-width: 150px;
            height: auto;
            border-radius: 10px;
        }

        .article .info {
            flex-grow: 1;
        }

        .article h2 {
            margin: 0 0 10px 0;
            font-size: 18px;
        }

        .private-icon {
            color: red;
            font-weight: bold;
        }

        .public-icon {
            color: green;
            font-weight: bold;
        }

        #loading {
            text-align: center;
            margin: 20px 0;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <!-- Contenedor de artículos -->
    <main class="container">
        <h1>Llistat d'Articles</h1>
        <div id="article-container" class="article-container"></div>

        <!-- Indicador de carga -->
        <div id="loading" style="display: none;">Carregant més articles...</div>
    </main>

    <!-- Script para Scroll Infinito -->
    <script>
        const articles = ${articles}; // Lista completa de artículos cargada desde el backend
        const pageSize = 10; // Tamaño de página
        let currentPage = 0;

        // Función para renderizar artículos
        function renderArticles() {
            const container = document.getElementById("article-container");
            const startIndex = currentPage * pageSize;
            const endIndex = startIndex + pageSize;

            articles.slice(startIndex, endIndex).forEach(article => {
                const articleDiv = document.createElement("div");
                articleDiv.className = "article";
                articleDiv.innerHTML = `
                    <img src="${article.imageUrl}" alt="${article.title}" />
                    <div class="info">
                        <h2>${article.title}</h2>
                        <p><strong>Autor:</strong> ${article.author}</p>
                        <p><strong>Resumen:</strong> ${article.summary}</p>
                        <p><strong>Data de publicació:</strong> ${article.publicationDate}</p>
                        <p><strong>Visualitzacions:</strong> ${article.views}</p>
                        <span class="${article.isPrivate ? 'private-icon' : 'public-icon'}">
                            ${article.isPrivate ? '🔒 Privat' : '🌐 Públic'}
                        </span>
                    </div>
                `;
                container.appendChild(articleDiv);
            });

            currentPage++;
            document.getElementById("loading").style.display = "none";
        }

        // Función para manejar el scroll infinito
        function handleScroll() {
            const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
            if (scrollTop + clientHeight >= scrollHeight - 10) {
                if (currentPage * pageSize < articles.length) {
                    document.getElementById("loading").style.display = "block";
                    setTimeout(renderArticles, 500); // Simula un retraso de carga
                } else {
                    console.log("No hay más artículos para mostrar.");
                    window.removeEventListener("scroll", handleScroll);
                }
            }
        }

        // Inicialización
        window.addEventListener("scroll", handleScroll);
        renderArticles(); // Renderiza la primera página al cargar
    </script>
</body>
</html>
