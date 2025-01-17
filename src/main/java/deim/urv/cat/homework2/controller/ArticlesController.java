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
        // Llamada al servicio
        List<Article> articles = articleService.getArticles();
        System.out.println("Articles del from service: " + articles);

        // Validación de artículos
        if (articles == null || articles.isEmpty()) {
            System.out.println("No articles .");
            models.put("message", "No hi ha articles disponibles.");
        } else {
            System.out.println("Articles retrieved: " + articles);
            models.put("articles", articles);
        }

        // Verifica el estado de sesión
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            models.put("isLoggedIn", false);
        }

    } catch (Exception e) {
        // Manejo de errores
        e.printStackTrace(); // Cambiar por un sistema de logs en producción
        models.put("errorMessage", "Error obtenint els articles.");
    }

    // Retorno de la vista
    return "/WEB-INF/views/layout/Articles.jsp";
}



}
