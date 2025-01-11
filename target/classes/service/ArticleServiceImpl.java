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


public class ArticleServiceImpl implements ArticleService {

    private static final String BASE_URL = "http://localhost:8080/api/article"; // Cambiar si es necesario
    private final Client client;

    public ArticleServiceImpl() {
        this.client = ClientBuilder.newClient();
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
    public List<Article> getAllArticle() {
        try {
            Response response = client.target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<Article>>() {});
            } else {
                System.err.println("Error al obtener todos los artículos: " + response.getStatusInfo());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Article getArticleById(int id) {
        try {
            Response response = client.target(BASE_URL + "/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(Article.class);
            } else {
                System.err.println("Error al obtener artículo por ID: " + response.getStatusInfo());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int crearArticle(Article nou, String username, String encodedPassword) {
    try {
        Response response;

        // Verifica si s'han proporcionat credencials
        if (username != null && encodedPassword != null) {
            // Combina el nom d'usuari i la contrasenya en format Basic Authentication
            String credentials = username + ":" + encodedPassword;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            // Envia la sol·licitud amb l'encapçalament Authorization
            response = client.target(BASE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                    .post(Entity.entity(nou, MediaType.APPLICATION_JSON));
        } else {
            // Envia la sol·licitud sense credencials
            response = client.target(BASE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(nou, MediaType.APPLICATION_JSON));
        }

        // Processa la resposta
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            // L'article s'ha creat correctament, retorna l'ID
            int idArticle = response.readEntity(Integer.class);
            System.out.println("Article creat amb ID: " + idArticle);
            return idArticle;
        } else {
            // Mostra errors si la creació ha fallat
            System.err.println("Error al crear article: " + response.getStatus());
            System.err.println("Missatge del servidor: " + response.readEntity(String.class));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return 0; // Retorna 0 si alguna cosa ha fallat
}

}
