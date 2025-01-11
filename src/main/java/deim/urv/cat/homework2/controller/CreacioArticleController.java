package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.service.ArticleService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.security.CsrfProtected;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Path("/article/create")
public class CreacioArticleController {

    @Inject
    private ArticleService articleService;

    @Inject
    private Logger log;

    @Inject
    private Models models;

    @POST
    @CsrfProtected
    public String createArticle(@Valid @BeanParam Article article, @Context HttpServletRequest request) {
        try {
            // Verifica si l'usuari està autenticat
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("username") == null || session.getAttribute("encodedPassword") == null) {
                log.log(Level.WARNING, "Usuari no autenticat intentant crear un article.");
                models.put("error", "Has d'estar autenticat per crear un article.");
                return "login-form.jsp"; // Redirigeix al formulari de login
            }

            // Obté les credencials de la sessió
            String username = (String) session.getAttribute("username");
            String encodedPassword = (String) session.getAttribute("encodedPassword");

            // Crida al servei per crear l'article
            int articleId = articleService.crearArticle(article, username, encodedPassword);

            if (articleId > 0) {
                log.log(Level.INFO, "Article creat amb èxit amb ID: {0}", articleId);
                return "redirect:/articles"; // Redirigeix al llistat d'articles
            } else {
                log.log(Level.WARNING, "Error al crear l'article.");
                models.put("error", "No s'ha pogut crear l'article. Si us plau, torna-ho a intentar.");
                return "create-article.jsp"; // Retorna al formulari amb un missatge d'error
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al processar la creació de l'article.", e);
            models.put("error", "S'ha produït un error inesperat. Si us plau, torna-ho a intentar més tard.");
            return "create-article.jsp"; // Mostra un error genèric al formulari
        }
    }
}
