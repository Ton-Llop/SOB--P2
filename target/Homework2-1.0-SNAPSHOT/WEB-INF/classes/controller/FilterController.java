package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleServiceImpl;
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
@Path("filtrar")
public class FilterController {

    @Inject
    private ArticleServiceImpl articleService;

    @Inject
    private Models models;

    @Context
    private HttpServletRequest request;


    @GET
    public String showForm() {
        // Comprovar si l'usuari està autenticat
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            models.put("isLoggedIn", false);
        }
        return "/WEB-INF/views/filterForm.jsp";
    }

    @POST
public String filterArticles(@FormParam("autor") String author,
                             @FormParam("topics") String topic) {

    // Validar els paràmetres de filtratge
    String[] topicList = null;
    if (topic != null && !topic.isEmpty()) {
        topicList = Arrays.stream(topic.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    // Llamar al servei per obtenir els articles filtrats
    List<Article> filteredArticles = articleService.getByTopicAndUser(author, topicList);

    // Afegir els resultats (o llista buida) al model
    models.put("articles", filteredArticles);

    // Afegir estat d'autenticació
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("username") != null) {
        models.put("isLoggedIn", true);
        models.put("username", session.getAttribute("username"));
    } else {
        models.put("isLoggedIn", false);
    }

    return "/WEB-INF/views/filtrats.jsp";

}
}
