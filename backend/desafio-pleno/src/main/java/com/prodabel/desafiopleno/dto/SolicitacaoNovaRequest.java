package com.prodabel.desafiopleno.dto;

import jakarta.validation.constraints.*;

public record SolicitacaoNovaRequest (
        @NotBlank(message = "O título não pode ser vazio")
        String titulo,

        @NotBlank(message = "A descrição não pode ser vazia")
        String descricao,

        @NotBlank(message = "O bairro não pode ser vazio")
        String bairro,

        @NotBlank (message = "O email do usuário não pode ser vazio")
        @Email (message = "Email do usuário inválido")
        String emailUsuario
) { }
