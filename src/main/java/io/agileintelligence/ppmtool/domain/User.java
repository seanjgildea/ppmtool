package io.agileintelligence.ppmtool.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank( message = "Username is required")
    @Column( unique = true )
    private String username;

    @NotBlank(message = "Please enter your full name")
    private String fullname;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;

    private Date created_At;
    private Date updated_At;
}
