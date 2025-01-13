<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Crear Nou Article</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-group textarea {
            resize: none;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .button-container button {
            padding: 10px 20px;
            border: none;
            background-color: #007bff;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }

        .button-container button:hover {
            background-color: #0056b3;
        }

        .button-container .back-button {
            background-color: #6c757d;
        }

        .button-container .back-button:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <main class="container">
        <h1>Crear Nou Article</h1>

        <!-- Mensajes de error o éxito -->
        <c:if test="${not empty errorMessage}">
            <div style="color: red; margin-bottom: 10px;">
                ${errorMessage}
            </div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div style="color: green; margin-bottom: 10px;">
                ${successMessage}
            </div>
        </c:if>

        <!-- Formulario centrado -->
        <form action="<c:url value='/Web/article/create'/>" method="POST" class="form-container">
            <div class="form-group">
                <label for="title">Títol</label>
                <input type="text" id="title" name="title" placeholder="Escriu el títol" required>
            </div>
            <div class="form-group">
                <label for="content">Contingut</label>
                <textarea id="content" name="content" rows="5" placeholder="Escriu el contingut" required></textarea>
            </div>
            <div class="form-group">
                <label for="topics">Tòpics</label>
                <input type="text" id="topics" name="topics" placeholder="Introdueix tòpics separats per comes" required>
            </div>
            <div class="form-group">
                <label for="isPrivate">Privat</label>
                <select id="isPrivate" name="isPrivate" required>
                    <option value="true">Sí</option>
                    <option value="false">No</option>
                </select>
            </div>
            <div class="form-group">
                <label for="image">Imatge (URL)</label>
                <input type="text" id="image" name="image" placeholder="Introdueix el link de la imatge" required>
            </div>

            <!-- Contenedor para los botones -->
            <div class="button-container">
                <button type="button" class="back-button" onclick="window.location.href='<c:url value="/Web/Home"/>'">Cancelar</button>
                <button type="submit">Crear Article</button>
            </div>
        </form>
    </main>
</body>
</html>
