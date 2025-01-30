package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.service.UserService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("/Login")
public class LoginController {

    @Inject
    private UserService userService;

    @Inject
    private Logger log;

    @Inject
    private Models models;

    @GET
    public String showLoginForm() {
        return "/WEB-INF/views/layout/login-form.jsp";
    }

    @POST
public String login(@BeanParam LoginForm loginForm, @Context HttpServletRequest request) {
    try {
        boolean loginSuccessful = userService.loginCorrecte(loginForm.getId(), loginForm.getPassword());

        if (loginSuccessful) {
            HttpSession session = request.getSession(true);
            session.setAttribute("username", loginForm.getId());

            log.log(Level.INFO, "Login correcte per a l'usuari: {0}", loginForm.getId());

            // Obtener la URL guardada antes del login
            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null) {
                session.removeAttribute("redirectAfterLogin"); // Limpiar la variable de sesión
                return "redirect:" + redirectUrl.replace(request.getContextPath(), ""); // Evita duplicaciones
            }

            // Si no hay URL almacenada, redirigir al home
            return "redirect:/Home";
        } else {
            log.log(Level.WARNING, "Login fallit per a l'usuari: {0}", loginForm.getId());
            models.put("error", "Usuari o contrasenya incorrectes. Torna-ho a intentar.");
            return "/WEB-INF/views/layout/login-form.jsp";
        }
    } catch (Exception e) {
        log.log(Level.SEVERE, "Error durant el procés de login", e);
        models.put("error", "S'ha produït un error inesperat. Torna-ho a intentar més tard.");
        return "/WEB-INF/views/layout/login-form.jsp";
    }
}

}
