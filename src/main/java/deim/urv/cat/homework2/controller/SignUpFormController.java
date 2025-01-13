package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Usuari;
import deim.urv.cat.homework2.service.UserService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("SignUp")
public class SignUpFormController {
    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject UserService service;
    @Inject Models models;
    @Inject HttpServletRequest request;

    @GET
    public String showForm() {
        return "/WEB-INF/views/layout/signup-form.jsp"; // Muestra el formulario
    }

    @POST
    @UriRef("sign-up")
    public String signUp(@Valid @BeanParam UserForm userForm) {
        models.put("user", userForm);

        // Validar datos
        if (bindingResult.isFailed()) {
            models.put("message", "Errores en el formulario. Por favor corrígelos.");
            return "/WEB-INF/views/layout/signup-form.jsp";
        }

        // Verifica si el usuario ya existe
        Usuari existingUser = service.findUserByEmail(userForm.getEmail());
        if (existingUser != null) {
            models.put("message", "El correo ya está registrado.");
            return "/WEB-INF/views/layout/signup-form.jsp";
        }

        // Registro exitoso
        try {
            service.addUser(userForm);
            HttpSession session = request.getSession(true);
            session.setAttribute("username", userForm.getUsername());
            models.put("username", userForm.getUsername());
            log.log(Level.INFO, "Usuario registrado con éxito: {0}", userForm.getUsername());
            return "/WEB-INF/views/layout/signup-success.jsp"; // Redirige a la vista de éxito
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error durante el registro", e);
            models.put("message", "Error interno. Intente de nuevo.");
            return "/WEB-INF/views/layout/signup-form.jsp";
        }
    }
}
