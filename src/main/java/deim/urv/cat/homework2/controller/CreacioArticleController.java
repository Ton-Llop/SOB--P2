package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleServiceImpl;
import deim.urv.cat.homework2.service.UserServiceImpl;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import java.util.Arrays;
import java.util.List;

@Controller
@Path("/article/create")
public class CreacioArticleController {

    @Inject
    private ArticleServiceImpl articleService;

    @Inject
    private UserServiceImpl userService;

    @Inject
    private Models models;

    @Context
    private HttpServletRequest request;

    @GET
    public String showForm() {
        // Comprovar si l'usuari ha iniciat sessió
        HttpSession session = request.getSession(false); // No crear una nova sessió si no existeix
        if (session != null && session.getAttribute("username") != null) {
            // Usuari loggejat
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            // Usuari no loggejat
            models.put("isLoggedIn", false);
        }
        return "/WEB-INF/views/layout/CreacioArticle.jsp";
    }

    @POST
public String saveArticle(@FormParam("title") String title,
                          @FormParam("content") String content,
                          @FormParam("topics") String topics,
                          @FormParam("isPrivate") String isPrivate,
                          @FormParam("image") String image,
                          @Context HttpServletRequest request) {

    try {
        // Recoge las credenciales del usuario autenticado
        String username = (String) request.getSession().getAttribute("username");
        String password = (String) request.getSession().getAttribute("password");

        // Convierte el campo 'isPrivate' a booleano
        boolean privateStatus = Boolean.parseBoolean(isPrivate);

        // Crea una lista de tópicos
        List<String> topicList = Arrays.asList(topics.split(","));

        // Crea un objeto 'Article'
        Article newArticle = new Article();
        newArticle.setTitle(title);
        newArticle.setContent(content);
        newArticle.setTopics(topicList);
        newArticle.setIsPrivate(privateStatus);
        newArticle.setImage(image);

        // Llama al servicio para guardar el artículo
        int articleId = articleService.crearArticle(newArticle, username, password);

        if (articleId > 0) {
            // Si se crea correctamente, redirige a 'article-details.jsp'
            return "redirect:/Web/article?id=" + articleId;
        } else {
            models.put("errorMessage", "No se pudo crear el artículo. Inténtalo nuevamente.");
            return "/WEB-INF/views/layout/CreacioArticle.jsp";
        }
    } catch (Exception e) {
        e.printStackTrace();
        models.put("errorMessage", "Ocurrió un error inesperado.");
        return "/WEB-INF/views/layout/CreacioArticle.jsp";
    }
}

}