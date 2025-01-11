package deim.urv.cat.homework2.controller;

import jakarta.ws.rs.FormParam;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginForm {

    @FormParam("email")
    @Email(message = "Please enter a valid email address.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @FormParam("password")
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    // Getters y Setters
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
}
