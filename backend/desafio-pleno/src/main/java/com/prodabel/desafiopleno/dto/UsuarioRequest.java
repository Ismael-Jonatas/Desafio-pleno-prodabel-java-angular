package com.prodabel.desafiopleno.dto;

import com.prodabel.desafiopleno.model.Usuario;
import jakarta.validation.constraints.*;

public record UsuarioRequest(
        @NotBlank(message = "O nome não pode ser vazio")
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
        String nome,

        @NotBlank(message = "O email não pode ser vazio")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O bairro não pode ser vazio")
        String bairro
) {
    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setBairro(this.bairro);
        return usuario;
    }
}