package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.service.UserService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("Login")
public class LoginController {

    @Inject
    private UserService userService;

    @Inject
    private Logger log;

    @Inject
    private Models models;

    @POST
    public String login(@BeanParam LoginForm loginForm, @jakarta.ws.rs.core.Context HttpServletRequest request) {
        try {
            boolean loginSuccessful = userService.loginCorrecte(loginForm.getEmail(), loginForm.getPassword());

            if (loginSuccessful) {
                // Crear una sesión para el usuario
                HttpSession session = request.getSession(true);
                session.setAttribute("username", loginForm.getEmail());

                log.log(Level.INFO, "Login successful for user: {0}", loginForm.getEmail());
                models.put("message", "Welcome back, " + loginForm.getEmail() + "!");
                return "welcome.jsp"; // Página de bienvenida
            } else {
                log.log(Level.WARNING, "Login failed for user: {0}", loginForm.getEmail());
                models.put("error", "Invalid email or password. Please try again.");
                return "login-form.jsp"; // Redirigir al formulario de login con error
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during login process", e);
            models.put("error", "An unexpected error occurred. Please try again later.");
            return "login-form.jsp";
        }
    }
}
