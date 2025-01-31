package deim.urv.cat.homework2.service;

import java.util.ArrayList;
import java.util.List;
import deim.urv.cat.homework2.model.Article;
import deim.urv.cat.homework2.model.Usuari;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import java.util.Objects;
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
        // Construir los parámetros de consulta
        StringBuilder queryParams = new StringBuilder();
        
        // Solo incluir el parámetro `author` si no está vacío
        if (authorId != null && !authorId.trim().isEmpty()) {
            queryParams.append("?author=").append(URLEncoder.encode(authorId, StandardCharsets.UTF_8));
        }
        
        // Agregar los tópicos a la consulta
        if (topicsId != null && topicsId.length > 0) {
            for (String topic : topicsId) {
                if (queryParams.length() > 0) {
                    queryParams.append("&");
                } else {
                    queryParams.append("?");
                }
                queryParams.append("topic=").append(URLEncoder.encode(topic, StandardCharsets.UTF_8));
            }
        }

        // Log para depuración de la URL generada
        System.out.println("URL generada: " + BASE_URL + queryParams.toString());

        // Realizar la llamada REST
        Response response = client.target(BASE_URL + queryParams.toString())
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        // Comprobar la respuesta
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            List<Map<String, Object>> rawData = response.readEntity(new GenericType<List<Map<String, Object>>>() {});
            return rawData.stream().map(Article::fromMap).collect(Collectors.toList());
        } else {
            System.err.println("Error al obtener artículos. Código: " + response.getStatus());
            System.err.println("Detalle del error: " + response.readEntity(String.class));
            return Collections.emptyList();
        }
    } catch (Exception e) {
        System.err.println("Excepción al obtener artículos: " + e.getMessage());
        e.printStackTrace();
        return Collections.emptyList();
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
    article.setIsPrivate((boolean) map.get("isPrivate"));

    

    // Si necesitas el autor
    Usuari author = new Usuari();
    author.setUsername((String) map.get("nomAut"));
    article.setAuthor(author);

    return article;
}





   @Override
    public int crearArticle(Article article, String username, String encodedPassword) {
    try {
        // Construir las credenciales en formato Basic Auth
        String credentials = username + ":" + encodedPassword;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Enviar el artículo al backend con el encabezado Authorization
        Response response = client.target(BASE_URL)
                                  .request(MediaType.APPLICATION_JSON)
                                  .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                                  .post(Entity.entity(article, MediaType.APPLICATION_JSON));

        // Si la respuesta es exitosa
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            // Leer el ID generado desde la respuesta
            return response.readEntity(Integer.class);
        } else {
            System.err.println("Error al crear el artículo: " + response.readEntity(String.class));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0; 
}



    
    public List<String> getAuthors() {
    try {
        // Realizar una solicitud al backend para obtener todos los artículos
        Response response = client.target(BASE_URL)
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Leer la respuesta como una lista de mapas
            List<Map<String, Object>> articles = response.readEntity(new GenericType<List<Map<String, Object>>>() {});

            // Extraer y devolver los autores únicos
            return articles.stream()
                           .map(article -> (String) article.get("nomAut")) // Extraer el valor de "nomAut"
                           .filter(Objects::nonNull) // Filtrar valores nulos (si los hubiera)
                           .distinct() // Eliminar duplicados
                           .collect(Collectors.toList());
        } else {
            System.err.println("Error al obtener autores: " + response.getStatus());
            return Collections.emptyList();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}



    
public List<String> getTopics() {
    try {
        // Realizar una solicitud al backend para obtener todos los artículos
        Response response = client.target(BASE_URL)
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Procesar los artículos y extraer los tópicos únicos
            List<Article> articles = response.readEntity(new GenericType<List<Article>>() {});
            return articles.stream()
                           .flatMap(article -> article.getTopics().stream())
                           .distinct()
                           .collect(Collectors.toList());
        } else {
            System.err.println("Error al obtener tópicos: " + response.getStatus());
            return Collections.emptyList();
        }
    } catch (Exception e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}

}



