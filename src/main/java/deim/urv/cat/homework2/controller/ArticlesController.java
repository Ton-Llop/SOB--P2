package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleServiceImpl;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Controller
@Path("/Articles")
public class ArticlesController {

    @Inject
    private ArticleServiceImpl articleService;

    @Inject
    private Models models;

    @Inject
    private HttpServletRequest request;

    @GET
    public String showArticles() {
        try {
            // Obtener artículos desde el servicio
            List<Article> articles = articleService.getAllArticle();

            // Si no hay artículos, mostrar un mensaje
            if (articles == null || articles.isEmpty()) {
                models.put("message", "No hi ha articuls disponibles.");
            } else {
                models.put("articles", articles);
            }

            // Comprobar si el usuario está logueado
            HttpSession session = request.getSession(false); 
            if (session != null && session.getAttribute("username") != null) {
                models.put("isLoggedIn", true);
                models.put("username", session.getAttribute("username"));
            } else {
                models.put("isLoggedIn", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            models.put("errorMessage", "Error.");
        }

        // Redirigir al JSP de artículos
        return "/WEB-INF/views/layout/Articles.jsp";
    }
}
