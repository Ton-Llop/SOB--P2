package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Usuari;
import deim.urv.cat.homework2.service.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

@Path("userInfo")
@Controller
public class UserInfoController {

    @Context
    private HttpServletRequest request;

    @Inject
    private UserServiceImpl userService;

    @GET
    public String showProfile() {
        HttpSession session = request.getSession(false); // Recuperar sesión, no crear una nueva
        if (session != null) {
            String username = (String) session.getAttribute("username");

            if (username != null) {
                Usuari user = userService.findUserByUsername(username);

                if (user != null) {
                    // Establecer los detalles del usuario como atributos de la solicitud para la vista
                    request.setAttribute("username", username);
                    request.setAttribute("name", user.getNom());
                    request.setAttribute("email", user.getEmail());
                    request.setAttribute("dni", user.getDni());
                    request.setAttribute("telf", user.getTelef());

                    return "/WEB-INF/views/info-user.jsp"; // Redirigir a la vista de perfil
                } else {
                    // Usuario no encontrado en la base de datos
                    request.setAttribute("error", "Usuario no encontrado.");
                    return "/WEB-INF/Error404.jsp"; // Redirigir a una vista de error
                }
            }
        }
        // Redirigir al login si la sesión o el nombre de usuario no existen
        return "redirect:/login";
    }
}
