package deim.urv.cat.homework2.controller;

import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.Topic;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author USUARIO
 */
@Controller
@Path("/Filtrar")
public class FilterController {
    @Inject
    private ArticleServiceImpl articleService;
    
    @Inject
    private Article article;
    
    @Inject
    private Models models;
    
    @Context
    private HttpServletRequest request;
    
    @GET
    public String showForm() {
    // Obtener autores y tópicos desde el servicio
    List<String> authors = articleService.getAuthors();
    List<String> topics = articleService.getTopics();
    
    System.out.println("Authors: " + authors);
    System.out.println("Topics: " + topics);

    // Pasar datos al JSP
    request.setAttribute("authors", authors);
    request.setAttribute("topics", topics);

    return "/WEB-INF/views/layout/Filter.jsp";
}


    
    @POST
public String saveArticle(@FormParam("autor") String author,
                          @FormParam("topics") String topic) {

    System.out.println("Filtrando por autor: " + author + ", tópicos: " + topic);

    // Procesar los tópicos
    String[] llistaTopics = null;
    if (topic != null && !topic.isEmpty()) {
        llistaTopics = Arrays.stream(topic.split(","))
                             .map(String::trim) // Elimina espacios externos
                             .toArray(String[]::new);
    }

    // Si el autor está vacío, pasa null al servicio
    author = (author != null && !author.trim().isEmpty()) ? author : null;

    // Llamar al servicio para obtener los artículos filtrados
    List<Article> filtre = articleService.getByTopicAndUser(author, llistaTopics);

    // Depurar los datos obtenidos
    if (filtre != null) {
        filtre.forEach(article -> System.out.println("Artículo: " + article));
    } else {
        System.out.println("No se encontraron artículos.");
    }

    // Validar la respuesta
    if (filtre != null && !filtre.isEmpty()) {
        models.put("articles", filtre);

        HttpSession session = request.getSession(false); // No crear una nueva sesión si no existe
        if (session != null && session.getAttribute("username") != null) {
            models.put("isLoggedIn", true);
            models.put("username", session.getAttribute("username"));
        } else {
            models.put("isLoggedIn", false);
        }

        return "/WEB-INF/views/layout/Filtrats.jsp";
    } else {
        models.put("message", "No s'han trobat articles amb els criteris seleccionats.");
        return "/WEB-INF/views/layout/Filtrats.jsp";
    }
}


}
