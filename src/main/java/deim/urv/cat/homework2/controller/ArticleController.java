package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;

@Path("/Article-Detall")
@Controller
public class ArticleController {

    @Inject
    private ArticleService articleService;

    @Inject
    private HttpServletRequest request;

    @GET
    public String showArticleDetail(@QueryParam("id") int id, @Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("username") != null;

        // Obtener el artículo
        Article article = articleService.obtenirArticle(id);
        if (article == null) {
            request.setAttribute("errorMessage", "No s'ha trobat l'article.");
            return "/WEB-INF/views/layout/Articles.jsp";
        }

        // Verificar si el artículo es privado
        if (article.getIsPrivate() && !isLoggedIn) {
            // ruta relativa
            String requestedUrl = request.getContextPath() + "/Article-Detall?id=" + id;
            request.getSession(true).setAttribute("redirectAfterLogin", requestedUrl);
            
            // Redirigir al login
            return "redirect:/Login";
        }

        request.setAttribute("article", article);
        return "/WEB-INF/views/layout/Article-Detall.jsp";
    }
}
