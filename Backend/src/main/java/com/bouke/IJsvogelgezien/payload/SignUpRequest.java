package com.bouke.IJsvogelgezien.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {

    // Getters and setters
    @Size(min = 3, max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;

    @Size(min = 6, max = 40)
    private String password;

}