package org.smortsogest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NonNull;

public class RegisterRequest {
    @NotBlank(message = "Name is required for registration")
    public String name;

    @NotBlank(message = "Email is required for registration")
    @Email(message = "Invalid email")
    public String email;

    @NotBlank(message = "Password is required for registration")
    public String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
