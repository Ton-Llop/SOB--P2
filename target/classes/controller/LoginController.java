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
                // Crea una sessió per a l'usuari autenticat
                HttpSession session = request.getSession(true);
                session.setAttribute("username", loginForm.getEmail());

                log.log(Level.INFO, "Login correcte per a l'usuari: {0}", loginForm.getEmail());

                // Missatge de benvinguda
                models.put("message", "Benvingut de nou, " + loginForm.getEmail() + "!");
                return "redirect:/articles"; // Redirigeix al llistat d'articles
            } else {
                // Credencials incorrectes
                log.log(Level.WARNING, "Login fallit per a l'usuari: {0}", loginForm.getEmail());
                models.put("error", "Usuari o contrasenya incorrectes. Torna-ho a intentar.");
                return "login-form.jsp"; // Mostra el formulari amb un error
            }
        } catch (Exception e) {
            // Error inesperat durant el procés d'inici de sessió
            log.log(Level.SEVERE, "Error durant el procés de login", e);
            models.put("error", "S'ha produït un error inesperat. Torna-ho a intentar més tard.");
            return "login-form.jsp"; // Mostra un missatge genèric d'error
        }
    }
}
