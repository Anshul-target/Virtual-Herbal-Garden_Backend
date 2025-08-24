package com.project.VirtualHerbalPlant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUser {
    @NotBlank(message = "Enter the email")
    @Schema(example = "anshul@2002.com")
    private String email;
    @NotBlank(message = "Enter the password")
    @Schema(example = "Anshul@123")
    private String password;
}
