<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/estilPrincipal.css'/>" />
    <script>
        // Función para manejar el clic en el botón de volver
        function goBack(event) {
            event.preventDefault(); // Evitar que el formulario se envíe
            window.location.href = "<c:url value='/Web/Home'/>"; // Redirigir a la página principal
        }

        // Validación de la contraseña
        function validatePassword() {
            const passwordField = document.getElementById('password');
            const errorMessage = document.getElementById('password-error');
            const password = passwordField.value;

            // Mostrar mensaje de error si la contraseña tiene menos de 8 caracteres
            if (password.length < 8) {
                errorMessage.textContent = 'La contrasenya ha de tenir almenys 8 caràcters.';
                passwordField.classList.add('invalid-input');
            } else {
                errorMessage.textContent = '';
                passwordField.classList.remove('invalid-input');
            }
        }

        // Asignar el evento de validación al campo de contraseña
        document.addEventListener('DOMContentLoaded', function () {
            const passwordField = document.getElementById('password');
            passwordField.addEventListener('input', validatePassword);
        });
    </script>
</head>
<body>
<header class="header">
   <div class="header-content">
       <!-- Text de benvinguda a l'esquerra -->
       <c:choose>
           <c:when test="${isLoggedIn}">
               <div class="welcome-container">
                   <form action="<c:url value='/Web/userInfo'/>" method="GET" style="margin: 0;">
                       <button type="submit" class="welcome-button">
                           Benvingut, <strong>${username}</strong>
                       </button>
                   </form>
               </div>
           </c:when>
       </c:choose>

       <!-- Barra de búsqueda -->
       <div class="header-search">
           <form action="<c:url value='/search'/>" method="GET" class="search-form">
               <input type="text" name="query" placeholder="Buscar artículos..." class="search-bar">
               <button type="submit" class="search-button">🔍</button>
           </form>
       </div>

       <!-- Botons a la dreta -->
       <div class="header-buttons">
           <!-- Botó Filtrar -->
           <form action="<c:url value='/Web/filtrar'/>" method="GET">
               <button type="submit">Filtrar</button>
           </form>
           <form action="<c:url value='/Web/createArticle'/>" method="GET">
               <button type="submit">Crear Articles</button>
           </form>
           <c:choose>
               <c:when test="${isLoggedIn}">
                   <!-- Botó de tancar sessió -->
                   <form action="<c:url value='/Web/Logout'/>" method="GET">
                       <button type="submit">Tanca Sessió</button>
                   </form>
               </c:when>
               <c:otherwise>
                   <!-- Botons de login i registre -->
                   <form action="<c:url value='/Web/login'/>" method="GET">
                       <button type="submit">Iniciar Sessió</button>
                   </form>
                   <form action="<c:url value='/Web/SignUp'/>" method="GET">
                       <button type="submit">Registrar-se</button>
                   </form>
               </c:otherwise>
           </c:choose>
       </div>
   </div>
</header>

<!-- Contenedor principal centrado -->
<main class="container" style="margin-top: 80px;">

    <!-- Mostra el missatge d'error si existeix -->
    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>

    <!-- Formulario centrado -->
    <form action="<c:url value='SignUp'/>" method="POST" class="form-container">
        <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>

        <!-- Nom -->
        <div class="form-group">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" id="nom" name="nom" class="form-input" placeholder="Nom" value="${user.nom}" required>
        </div>

        <!-- Usuari -->
        <div class="form-group">
            <label for="username" class="form-label">Usuari</label>
            <input type="text" id="username" name="username" class="form-input" placeholder="Usuari" value="${user.username}" required>
        </div>

        <!-- Telèfon -->
        <div class="form-group">
            <label for="telf" class="form-label">Telèfon</label>
            <input type="text" id="telf" name="telf" class="form-input" placeholder="Telèfon" value="${user.telf}" required>
        </div>

        <!-- DNI -->
        <div class="form-group">
            <label for="dni" class="form-label">DNI</label>
            <input type="text" id="dni" name="dni" class="form-input" placeholder="DNI" value="${user.dni}" required>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email" class="form-label">Email</label>
            <input type="email" id="email" name="email" class="form-input" placeholder="Email" value="${user.email}" required>
        </div>

        <!-- Contrasenya -->
        <div class="form-group">
            <label for="password" class="form-label">Contrasenya</label>
            <input type="password" id="password" name="password" class="form-input" placeholder="Contrasenya" required>
            <div id="password-error" class="error-text"></div>
        </div>

        <!-- Contenedor para los botones -->
        <div class="button-container">
            <div class="back-button-container">
                <button type="button" onclick="goBack(event)" class="back-button">
                    Cancelar
                </button>
            </div>

            <div class="create-button-container">
                <button type="submit" class="back-button">
                    Registrar-se
                </button>
            </div>
        </div>
    </form>
</main>

<style>
    .welcome-container {
        display: flex;
        align-items: center;
        color: white;
        font-size: 20px;
    }

    .form-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        max-width: 500px;
        margin: 0 auto;
    }

    .form-group {
        margin: 15px 0;
        width: 100%;
    }

    .form-group label {
        display: block;
        font-weight: bold;
        margin-bottom: 5px;
    }

    .form-input {
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
    }

    .form-input.invalid-input {
        border-color: red;
    }

    .error-text {
        color: red;
        font-size: 14px;
        margin-top: 5px;
    }

    .button-container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        margin-top: 20px;
        gap: 20px;
    }

    .back-button-container,
    .create-button-container {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 48%;
        margin: 0;
        padding: 0;
    }

    .back-button,
    .create-button-container button {
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0;
        padding: 0 20px;
        width: 100%;
        max-width: 200px;
        height: 50px;
        background-color: #f1f1f1;
        color: #333;
        border: none;
        border-radius: 5px;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .back-button:hover,
    .create-button-container button:hover {
        background-color: #ddd;
    }
</style>
</body>
</html>