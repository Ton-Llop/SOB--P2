package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Usuari;
import deim.urv.cat.homework2.service.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;

/**
 * UserInfoEditController permite editar los datos del perfil del usuario.
 */
@Path("edit")
@Controller
public class UserInfoEditController {

    @Context
    private HttpServletRequest request;

    @Inject
    private UserServiceImpl userService;

    @GET
    public String showEditForm() {
        HttpSession session = request.getSession(false); // Recuperar sesión, no crear una nueva
        if (session != null) {
            String username = (String) session.getAttribute("username");

            if (username != null) {
                Usuari user = userService.findUserByUsername(username);

                if (user != null) {
                    request.setAttribute("user", user); // Pasar el usuario actual a la vista
                    return "/WEB-INF/views/edit-info-user.jsp"; // Redirigir al formulario de edición
                } else {
                    request.setAttribute("error", "Usuario no encontrado.");
                    return "/WEB-INF/Error404.jsp"; // Vista de error
                }
            }
        }
        return "redirect:/login"; // Redirigir al login si no hay sesión
    }

    @POST
    public String saveChanges(
            @FormParam("nom") String nom,
            @FormParam("email") String email,
            @FormParam("dni") String dni,
            @FormParam("telef") int telef) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");

            if (username != null) {
                Usuari updatedUser = new Usuari();
                updatedUser.setUsername(username);

                // Actualizar campos si no están vacíos
                if (nom != null && !nom.isEmpty()) updatedUser.setNom(nom);
                if (email != null && !email.isEmpty()) updatedUser.setEmail(email);
                if (dni != null && !dni.isEmpty()) updatedUser.setDni(dni);
                if (telef != 0) updatedUser.setTelef(telef);

                long userId = userService.findUserByUsername(username).getId();
                boolean success = userService.updateUser(userId, updatedUser);

                if (success) {
                    return "redirect:/userInfo"; // Redirigir al perfil actualizado
                } else {
                    request.setAttribute("error", "Error al actualizar los datos. Inténtalo de nuevo.");
                    return "/WEB-INF/views/edit-info-user.jsp";
                }
            }
        }
        return "redirect:/login"; // Redirigir al login si no hay sesión
    }
}
