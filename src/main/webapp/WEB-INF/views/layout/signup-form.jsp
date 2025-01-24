<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
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

        // Validación de todos los campos
        function validateForm() {
            const inputs = document.querySelectorAll('.form-input');
            let isValid = true;

            inputs.forEach(input => {
                if (!input.value) {
                    input.classList.add('invalid-input');
                    isValid = false;
                } else {
                    input.classList.remove('invalid-input');
                }
            });

            return isValid;
        }

        document.addEventListener('DOMContentLoaded', function () {
            const passwordField = document.getElementById('password');
            passwordField.addEventListener('input', validatePassword);

            document.querySelector('form').addEventListener('submit', function (event) {
                if (!validateForm()) {
                    event.preventDefault();
                    alert('Por favor, completa todos los campos obligatorios.');
                }
            });
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

       <!-- Botons a la dreta -->
       <div class="header-buttons">
           <form action="<c:url value='/Web/Filtrar'/>" method="GET">
               <button type="submit">Filtrar</button>
           </form>
           <c:choose>
               <c:when test="${isLoggedIn}">
                   <form action="<c:url value='/Web/Logout'/>" method="GET">
                       <button type="submit">Tanca Sessió</button>
                   </form>
               </c:when>
               <c:otherwise>
                   <form action="<c:url value='/Web/Login'/>" method="GET">
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

<!-- Contenedor principal -->
<main class="container" style="margin-top: 80px;">
    <!-- Mostrar mensajes de error o información -->
    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="info-message">
            ${message}
        </div>
    </c:if>

    <!-- Formulario -->
    <form action="<c:url value='SignUp'/>" method="POST" class="form-container">
        <input type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}" />

        <!-- Nom -->
        <div class="form-group">
            <label for="nom">Nom</label>
            <input type="text" id="nom" name="nom" class="form-input" placeholder="Nom" value="${user.nom}" required>
        </div>

        <!-- Usuari -->
        <div class="form-group">
            <label for="username">Usuari</label>
            <input type="text" id="username" name="username" class="form-input" placeholder="Usuari" value="${user.username}" required>
        </div>

        <!-- Telèfon -->
        <div class="form-group">
            <label for="telf">Telèfon</label>
            <input type="text" id="telf" name="telf" class="form-input" placeholder="Telèfon" value="${user.telf}" required>
        </div>

        <!-- DNI -->
        <div class="form-group">
            <label for="dni">DNI</label>
            <input type="text" id="dni" name="dni" class="form-input" placeholder="DNI" value="${user.dni}" required>
        </div>

        <!-- Email -->
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" class="form-input" placeholder="Email" value="${user.email}" required>
        </div>

        <!-- Contrasenya -->
        <div class="form-group">
            <label for="password">Contrasenya</label>
            <input type="password" id="password" name="password" class="form-input" placeholder="Contrasenya" required>
            <div id="password-error" class="error-text"></div>
        </div>

        <!-- Botones -->
        <div class="button-container">
            <button type="button" onclick="goBack(event)" class="back-button">Cancelar</button>
            <button type="submit" class="register-button">Registrar-se</button>
        </div>
    </form>
</main>

<style>
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
        font-weight: bold;
        margin-bottom: 5px;
    }

    .form-input {
        width: 100%;
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .form-input.invalid-input {
        border-color: red;
    }

    .error-message {
        color: red;
        font-size: 14px;
        margin: 10px 0;
    }

    .button-container {
        display: flex;
        justify-content: space-between;
        gap: 20px;
    }

    .back-button, .register-button {
        padding: 10px 15px;
        font-size: 14px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .back-button {
        background-color: #f1f1f1;
        color: #333;
    }

    .register-button {
        background-color: #007bff;
        color: white;
    }
</style>
</body>
</html>
