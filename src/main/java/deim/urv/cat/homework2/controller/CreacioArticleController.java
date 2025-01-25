package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.Usuari;
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
import java.time.LocalDateTime;
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
        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : "defaultUser";
        String password = (session != null) ? (String) session.getAttribute("password") : "defaultPassword";

        // Crear el artículo
        Article newArticle = new Article();
        newArticle.setTitle(title);
        newArticle.setContent(content);
        newArticle.setImage(image);
        newArticle.setIsPrivate(Boolean.valueOf(isPrivate));
        newArticle.setTopics(List.of(topics.split(",")));
        newArticle.setPublicationDate(LocalDateTime.now());

        Usuari author = new Usuari();
        author.setUsername(username);
        newArticle.setAuthor(author);

        // Llamar al servicio para guardar el artículo
        int articleId = articleService.crearArticle(newArticle, username, password);

        if (articleId > 0) {
            newArticle.setId((int) (long) articleId); // Actualizar el ID del artículo
            models.put("article", newArticle);
            return "/WEB-INF/views/layout/ArticleCreated.jsp";
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
