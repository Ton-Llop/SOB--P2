package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.service.ArticleService;
import deim.urv.cat.homework2.model.Article;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import java.util.List;

@Controller
@Path("/Home")
public class HomeController {

    @Inject
    private ArticleService articleService;

    @Inject
    private Models models;

    @Context
    private HttpServletRequest request;

    @GET
    public String showHomePage() {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            models.put("isLoggedIn", false);
        }

        // Obtener los artículos desde el servicio
        List<Article> articles = articleService.getAllArticle();
        models.put("articles", articles);

        return "/WEB-INF/views/layout/Home.jsp";
    }
}
