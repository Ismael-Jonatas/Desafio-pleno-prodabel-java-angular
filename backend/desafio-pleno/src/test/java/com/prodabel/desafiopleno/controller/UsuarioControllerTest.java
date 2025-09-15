package com.prodabel.desafiopleno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodabel.desafiopleno.dto.UsuarioRequest;
import com.prodabel.desafiopleno.model.Usuario;
import com.prodabel.desafiopleno.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private final UsuarioService usuarioService = Mockito.mock(UsuarioService.class);
    private final UsuarioController controller = new UsuarioController(usuarioService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveBuscarUsuarioPorIdERetornar200() throws Exception {
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "Centro");
        Mockito.when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));

        Mockito.verify(usuarioService).buscarPorId(1L);
    }

    @Test
    void deveCriarUsuarioERetornar200() throws Exception {
        UsuarioRequest request = new UsuarioRequest("Maria", "maria@email.com", "Bairro");
        Usuario usuario = new Usuario(2L, "Maria", "maria@email.com", "Bairro");
        Mockito.when(usuarioService.criar(any(UsuarioRequest.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));

        Mockito.verify(usuarioService).criar(any(UsuarioRequest.class));
    }

    @Test
    void deveAtualizarUsuarioERetornar200() throws Exception {
        Usuario usuarioAtualizado = new Usuario(1L, "João Silva", "joao@email.com", "Centro");
        Mockito.when(usuarioService.atualizar(eq(1L), any(Usuario.class))).thenReturn(Optional.of(usuarioAtualizado));

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));

        Mockito.verify(usuarioService).atualizar(eq(1L), any(Usuario.class));
    }

    @Test
    void deveDeletarUsuarioERetornar204() throws Exception {
        Mockito.when(usuarioService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(usuarioService).deletar(1L);
    }

    @Test
    void deveListarUsuariosERetornar200() throws Exception {
        Usuario usuario = new Usuario(1L, "João", "joao@email.com", "Centro");
        Mockito.when(usuarioService.listar()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));

        Mockito.verify(usuarioService).listar();
    }
}