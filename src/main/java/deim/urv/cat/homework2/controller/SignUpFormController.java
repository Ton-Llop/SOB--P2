package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.AlertMessage;
import deim.urv.cat.homework2.model.SignUpAttempts;
import deim.urv.cat.homework2.service.UserService;
import deim.urv.cat.homework2.model.Usuari;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.UriRef;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.mvc.security.CsrfProtected;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("SignUp")
public class SignUpFormController {    
    @Inject BindingResult bindingResult;
    @Inject Logger log;
    @Inject UserService service;
    @Inject Models models;
    @Inject SignUpAttempts attempts;
    
    @GET
    public String showForm() {
        return "/WEB-INF/views/layout/signup-form.jsp"; // Muestra el formulario
    }    
    
    @POST
    @UriRef("sign-up")
    @CsrfProtected
    public String signUp(@Valid @BeanParam UserForm userForm) {
        models.put("user", userForm);

        // Validación de datos
        if (bindingResult.isFailed()) {
            AlertMessage alert = AlertMessage.danger("Validation failed!");
            bindingResult.getAllErrors().forEach((ParamError t) -> 
                alert.addError(t.getParamName(), "", t.getMessage())
            );
            log.log(Level.WARNING, "Data binding for signupFormController failed.");
            models.put("errors", alert);
            return "/WEB-INF/views/layout/signup-form.jsp";
        }
        
        // Validación de intentos fallidos
        if (attempts.hasExceededMaxAttempts()) {
            models.put("message", "Has excedido el número máximo de intentos. Por favor, inténtalo más tarde.");
            return "/WEB-INF/views/layout/signup-form.jsp";
        }

        // Validar si el usuario ya existe
        Usuari user = service.findUserByEmail(userForm.getUsername()); 
        if (user != null) {
            log.log(Level.WARNING, "A user with this username {0} already exists.", userForm.getUsername());
            models.put("message", "El nombre de usuario ya está registrado. Intenta con otro.");
            attempts.increment();
            return "/WEB-INF/views/layout/signup-form.jsp";
        }

        // Registro exitoso
        log.log(Level.INFO, "Registro exitoso para el usuario: {0}", userForm.getUsername());
        service.addUser(userForm);
        attempts.reset(); // Reinicia intentos

        // Redirigir a la página de éxito o perfil del usuario
        return "redirect: /WEB-INF/views/layout/info-user.jsp"; // Redirige al controlador UserInfoController
    } 
}
