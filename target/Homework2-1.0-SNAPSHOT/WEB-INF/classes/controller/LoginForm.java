package deim.urv.cat.homework2.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.mvc.binding.MvcBinding;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.FormParam;

@Named("loginForm")
@RequestScoped
public class LoginForm {

    @NotBlank(message = "El camp ID no pot estar buit.")
    @FormParam("id")
    @MvcBinding
    private String id;

    @NotBlank(message = "El camp Contrasenya no pot estar buit.")
    @FormParam("password")
    @MvcBinding
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}