package com.prodabel.desafiopleno.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        String nome,

        @NotBlank(message = "O email não pode ser vazio")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O bairro não pode ser vazio")
        String bairro
) { }