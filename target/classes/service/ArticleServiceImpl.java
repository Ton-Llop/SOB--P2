package deim.urv.cat.homework2.service;

import java.util.ArrayList;
import java.util.List;
import deim.urv.cat.homework2.model.Article;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class ArticleServiceImpl implements ArticleService {

    private static final String BASE_URL = "http://localhost:8080/api/article"; // Cambiar si es necesario
    private final Client client;

    public ArticleServiceImpl() {
        this.client = ClientBuilder.newClient();
    }

    @Override
    public List<Article> getByTopicAndUser(String authorId, String... topicsId) {
        try {
            String queryParams = "?author=" + authorId;
            if (topicsId != null && topicsId.length > 0) {
                queryParams += "&topic=" + String.join(",", topicsId);
            }

            Response response = client.target(BASE_URL + queryParams)
                .request(MediaType.APPLICATION_JSON)
                .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(new GenericType<List<Article>>() {});
            } else {
                System.err.println("Error al obtener artículos: " + response.getStatusInfo());
                return new ArrayList<>();
            }
        } catch (Exception e) {
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
    public int crearArticle(Article nou) {
        try {
            Response response = client.target(BASE_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nou, MediaType.APPLICATION_JSON));

            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                return response.readEntity(Integer.class);
            } else {
                System.err.println("Error al crear artículo: " + response.getStatusInfo());
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
