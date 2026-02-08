package org.smortsogest.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank(message = "Name is required for registration")
    private String name;

    @NotBlank(message = "Email is required for registration")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required for registration")
    private String password;

    @NotBlank(message = "Confirm password is required for registration")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirm_password) {
        this.confirmPassword = confirm_password;
    }
}
