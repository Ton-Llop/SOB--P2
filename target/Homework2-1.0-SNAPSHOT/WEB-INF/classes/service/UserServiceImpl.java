package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.controller.UserForm;
import deim.urv.cat.homework2.model.Usuari;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/Homework1/rest/api/v1/user";

    public UserServiceImpl() {
        this.client = ClientBuilder.newClient();
        this.webTarget = client.target(BASE_URI);
    }

    @Override
    public List<Usuari> getAllUsers() {
        try {
            Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

            if (response.getStatus() == 200) {
                return response.readEntity(new jakarta.ws.rs.core.GenericType<List<Usuari>>() {});
            } else {
                System.err.println("Error al obtener usuarios: " + response.getStatus());
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Usuari getUserById(long id) {
        try {
            Response response = webTarget.path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(Usuari.class);
            } else {
                System.err.println("Error al obtener usuario por ID: " + response.getStatus());
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    public Usuari findUserByUsername(String username) {
        try {
            Response response = webTarget.path("/username")
                    .queryParam("username", username)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                return response.readEntity(Usuari.class);
            } else {
                System.err.println("Error al buscar usuario por nombre de usuario: " + response.getStatus());
            }
        } catch (Exception e) {
               System.err.println("Error al buscar usuario: " + e.getMessage());
        }
        return null; 
    }

    @Override
    public boolean updateUser(long id, Usuari user) {
        try {
            Response response = webTarget.path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(user, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 200) {
                return true;
            } else {
                System.err.println("Error al actualizar usuario: " + response.getStatus());
            }
        } catch (Exception e) {
        }
        return false;
    }


    public boolean loginCorrecte(String username, String password) {
        try {
            // Construir la URL del endpoint
            String url = "http://localhost:8080/Homework1/rest/api/v1/user/LoginVerification";

            // Crear el header Authorization con credenciales codificadas
            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            // Crear el target y la solicitud
            WebTarget target = client.target(url);
            Invocation.Builder invocationBuilder = target.request();
            invocationBuilder.header("Authorization", "Basic " + encodedCredentials);

            // Ejecutar la solicitud GET
            Response response = invocationBuilder.get();

            // Comprobar el estado de la respuesta
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Credenciales correctas.");
                return true;
            } else {
                System.err.println("Error de credenciales: " + response.getStatus());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean addUser(UserForm user) {
        try {
            Response response = webTarget.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(user, MediaType.APPLICATION_JSON));

            return response.getStatus() == 201;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public Usuari findUserByEmail(String email) {
        try {
            Response response = webTarget.path("/email")
                    .queryParam("email", email)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                return response.readEntity(Usuari.class);
            } else {
                System.err.println("Error al buscar usuario por email: " + response.getStatus());
            }
        } catch (Exception e) {
        }
        return null;
    }

}
