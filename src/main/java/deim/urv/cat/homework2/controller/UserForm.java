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
        
    // JSR 303 validation
    @NotBlank
    @FormParam("firstName")
    @MvcBinding
    @Size(min=2, max=30, message = "First name must be between 2 and 30 characters")
    private String firstName;
    
    // JSR 303 validation
    @NotBlank
    @FormParam("lastName")
    @MvcBinding
    @Size(min=2, max=30, message = "Last name must be between 2 and 30 characters")
    private String lastName;

    @NotBlank
    @FormParam("email")
    @Email(message = "Email should be valid")
    @MvcBinding
    private String email;
    
    @NotBlank
    @FormParam("dni")
    @MvcBinding
    @Pattern(regexp = "\\d{8}[A-Za-z]", message = "DNI must have 8 digits followed by a letter")
    private String dni;
    
    @FormParam("telef")
    @MvcBinding
    private int telef;
    
    public String getFirstName() {
        return fixNull(this.firstName);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return fixNull(this.lastName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return fixNull(this.email);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDni() {
        return fixNull(this.dni);
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getTelef() {
        return this.telef;
    }

    public void setTelef(int telef) {
        this.telef = telef;
    }
    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}
