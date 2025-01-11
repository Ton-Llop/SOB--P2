package deim.urv.cat.homework2.service;

import deim.urv.cat.homework2.controller.UserForm;
import deim.urv.cat.homework2.model.User;
import deim.urv.cat.homework2.model.Usuari;
import java.util.List;

public interface UserService {

    /**
     * Obtiene la lista completa de usuarios.
     * 
     * @return Lista de usuarios.
     */
    public List<Usuari> getAllUsers();

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario.
     * @return Usuario encontrado, o null si no existe.
     */
    public Usuari getUserById(long id);

    /**
     * Actualiza los datos de un usuario.
     * 
     * @param id ID del usuario a actualizar.
     * @param user Usuario con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean updateUser(long id, Usuari user);

    /**
     * Verifica las credenciales del usuario utilizando el encabezado Authorization.
     * 
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean loginCorrecte(String username, String password);
    
    public boolean addUser(UserForm user);

    public Usuari findUserByEmail(String email);
}
