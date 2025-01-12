package deim.urv.cat.homework2.controller;

import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

/**
 * Controlador para gestionar la página de éxito después del registro.
 */
@Controller
@Path("SignUpSuccess")
public class SignUpSuccessController {

    @Inject
    private Models models;

    @Context
    private HttpServletRequest request;

    @GET
    public String showSignUpSuccess() {
        // Comprobar si el usuario está registrado y sus datos están en sesión
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Pasar los datos del usuario al modelo para mostrarlos en el JSP
            models.put("user", session.getAttribute("user"));
            return "/WEB-INF/views/layout/signup-success.jsp";
        } else {
            // Si no hay datos del usuario, redirigir al formulario de registro
            return "redirect:/Web/SignUp";
        }
    }
}
