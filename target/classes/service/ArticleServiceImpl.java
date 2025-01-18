package deim.urv.cat.homework2.service;

import java.util.ArrayList;
import java.util.List;
import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.Usuari;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import jakarta.ws.rs.client.WebTarget;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;


public class ArticleServiceImpl implements ArticleService{
    
    
    private final WebTarget webTarget;
    private final jakarta.ws.rs.client.Client client;
    private static final String BASE_URL = "http://localhost:8080/Homework1/rest/api/v1/article";
    
    public ArticleServiceImpl() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
   

    @Override
public List<Article> getByTopicAndUser(String authorId, String... topicsId) {
    try {
        // Construir els paràmetres de la consulta
        StringBuilder queryParams = new StringBuilder();
        if (authorId != null && !authorId.isEmpty()) {
            queryParams.append("?author=").append(URLEncoder.encode(authorId, StandardCharsets.UTF_8));
        }
        if (topicsId != null && topicsId.length > 0) {
            if (queryParams.length() > 0) {
                queryParams.append("&");
            } else {
                queryParams.append("?");
            }
            queryParams.append("topic=").append(URLEncoder.encode(String.join(",", topicsId), StandardCharsets.UTF_8));
        }

        // Fer la crida REST
        Response response = client.target(BASE_URL + queryParams.toString())
                .request(MediaType.APPLICATION_JSON)
                .get();

        // Comprovar la resposta
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<Article>>() {});
        } else {
            System.err.println("Error al obtenir articles. Codi: " + response.getStatus());
            System.err.println("Detall de l'error: " + response.readEntity(String.class));
            return new ArrayList<>();
        }
    } catch (Exception e) {
        System.err.println("Excepció al obtenir articles: " + e.getMessage());
        e.printStackTrace();
        return new ArrayList<>();
    }
}


    @Override
    public List<Article> getArticles() {
    try {
        Response response = client.target(BASE_URL)
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        System.out.println("HTTP Status: " + response.getStatus());

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            List<Map<String, Object>> rawData = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
            return rawData.stream().map(Article::fromMap).collect(Collectors.toList());
        } else {
            System.err.println("Error al obtener los artículos: " + response.getStatus());
            return Collections.emptyList();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}


    

@Override
public Article obtenirArticle(int id) {
    try {
        Response response = client.target(BASE_URL + "/" + id)
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Map<String, Object> rawData = response.readEntity(new GenericType<Map<String, Object>>() {});
            return mapToArticle(rawData);
        } else {
            System.err.println("Error al obtener artículo por ID: " + response.getStatusInfo());
            return null;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
private Article mapToArticle(Map<String, Object> map) {
    Article article = new Article();
    article.setId((int) ((Number) map.get("id")).longValue());
    article.setImage((String) map.get("imatge"));
    article.setTitle((String) map.get("titol"));
    article.setContent((String) map.get("descripcio"));
    article.setViews(((Number) map.get("nViews")).intValue());
    article.setPublicationDate(LocalDateTime.parse((String) map.get("dataPubli"))); // Ajusta si usas otro formato
    article.setTopics((List<String>) map.get("topics"));

    // Si necesitas el autor
    Usuari author = new Usuari();
    author.setUsername((String) map.get("nomAut"));
    article.setAuthor(author);

    return article;
}



    @Override
    public int crearArticle(Article nou, String username, String encodedPassword) {
    try {
        String credentials = username + ":" + encodedPassword;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        Response response = client.target("http://localhost:8080/Homework1/rest/api/v1/article")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                .post(Entity.entity(nou, MediaType.APPLICATION_JSON));

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            return response.readEntity(Integer.class);
        } else {
            System.err.println("Error al crear artículo: " + response.getStatus());
            System.err.println(response.readEntity(String.class));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;
}
}
