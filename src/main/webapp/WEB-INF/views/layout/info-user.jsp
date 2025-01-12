<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Información del Usuario</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />">
    <script src="<c:url value='/resources/js/jquery-1.11.1.min.js' />"></script>
    <script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        .user-info {
            list-style: none;
            padding: 0;
        }

        .user-info li {
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
        }

        .user-info li:last-child {
            border-bottom: none;
        }

        .btn-container {
            text-align: center;
            margin-top: 20px;
        }

        .btn-container a {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn-container a:hover {
            background-color: #0056b3;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Perfil del Usuario</h1>

        <c:if test="${not empty error}">
            <div class="error">
                <p>${error}</p>
            </div>
        </c:if>

        <ul class="user-info">
            <li><strong>Nombre de Usuario:</strong> ${username}</li>
            <li><strong>Nombre:</strong> ${name}</li>
            <li><strong>Email:</strong> ${email}</li>
            <li><strong>DNI:</strong> ${dni}</li>
            <li><strong>Teléfono:</strong> ${telf}</li>
        </ul>

        <div class="btn-container">
            <a href="<c:url value='/Web/Home' />">Volver al Inicio</a>
        </div>
    </div>
</body>
</html>
