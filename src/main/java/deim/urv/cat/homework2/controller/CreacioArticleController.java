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
@Path("article/create")
public class CreacioArticleController {

    @Inject
    private ArticleServiceImpl articleService;

    @Inject
    private Models models;

    @Context
    private HttpServletRequest request;

    @GET
    public String showForm() {
        // Comprovar si l'usuari ha iniciat sessió
        HttpSession session = request.getSession(false); // No crear una nova sessió si no existeix
        if (session != null && session.getAttribute("username") != null) {
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            models.put("isLoggedIn", false);
        }
        return "/WEB-INF/views/layout/CreacioArticle.jsp";
    }

    @POST
    public String saveArticle(
        @FormParam("title") String title,
        @FormParam("content") String content,
        @FormParam("topics") String topics,
        @FormParam("isPrivate") String isPrivate,
        @FormParam("image") String image
    ) {
        try {
            // Obtener datos de sesión
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");

            // Crear el artículo
            Article newArticle = new Article();
            newArticle.setTitle(title);
            newArticle.setContent(content);
            newArticle.setImage(image);
            newArticle.setIsPrivate(Boolean.parseBoolean(isPrivate));
            newArticle.setTopics(List.of(topics.split(",")));

            int articleId = articleService.crearArticle(newArticle, username, password);
            if (articleId > 0) {
                models.put("successMessage", "Article creat correctament amb ID: " + articleId);
                return "redirect:/Web/Articles"; // Redirigir a la página de artículos
            } else {
                models.put("errorMessage", "No s'ha pogut crear l'article.");
                return "/WEB-INF/views/layout/CreacioArticle.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            models.put("errorMessage", "S'ha produït un error al crear l'article.");
            return "/WEB-INF/views/layout/CreacioArticle.jsp";
        }
    }
}

