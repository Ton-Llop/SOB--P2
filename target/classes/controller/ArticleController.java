package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;

@Controller
@Path("/article")
public class ArticleController {

    @Inject
    private ArticleService articleService;

    @GET
    @Path("/{id}")
    public String getArticleDetail(@PathParam("id") int id, @Context HttpServletRequest request, Models models) {
        // Obtenir la sessió
        HttpSession session = request.getSession(false);
        boolean isAuthenticated = (session != null && session.getAttribute("username") != null);

        // Obtenir l'article
        Article article = articleService.getArticleById(id);
        if (article == null) {
            models.put("error", "L'article no existeix.");
            return "error.jsp";
        }

        // Si l'article és privat i l'usuari no està autenticat
        if (article.getIsPrivate() && !isAuthenticated) {
            models.put("error", "Aquest article és privat. Necessites iniciar sessió per accedir-hi.");
            return "login-form.jsp"; // Redirigir al login
        }

        // Afegir l'article al model per mostrar-lo a la vista
        models.put("article", article);
        return "article-detail.jsp";
    }
}

