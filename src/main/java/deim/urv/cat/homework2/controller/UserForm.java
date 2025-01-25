package deim.urv.cat.homework2.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.mvc.binding.MvcBinding;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;

import java.io.Serializable;

@Named("userForm")
@RequestScoped
public class UserForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @FormParam("nom")
    @MvcBinding
    @Size(min = 2, max = 30, message = "Nom must be between 2 and 30 characters")
    private String nom;

    @NotBlank
    @FormParam("username")
    @MvcBinding
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank
    @FormParam("email")
    @Email(message = "Email should be valid")
    @MvcBinding
    private String email;

    @NotBlank
    @FormParam("password")
    @MvcBinding
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank
    @FormParam("dni")
    @MvcBinding
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "DNI must have 8 digits followed by a letter")
    private String dni;

    @FormParam("telf")
    @MvcBinding
    @Pattern(regexp = "\\d{9}", message = "Telèfon must be 9 digits")
    private String telef;

    // Getters y Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelef() {
        return telef;
    }

    public void setTelef(String telef) {
        this.telef = telef;
    }
}
