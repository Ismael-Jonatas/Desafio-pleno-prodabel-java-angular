package com.prodabel.desafiopleno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodabel.desafiopleno.model.Funcionario;
import com.prodabel.desafiopleno.service.FuncionarioService;
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

class FuncionarioControllerTest {

    private final FuncionarioService funcionarioService = Mockito.mock(FuncionarioService.class);
    private final FuncionarioController controller = new FuncionarioController(funcionarioService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveListarFuncionariosERetornar200() throws Exception {
        Funcionario funcionario = new Funcionario(1L, "Carlos", "carlos@email.com");
        Mockito.when(funcionarioService.listar()).thenReturn(List.of(funcionario));

        mockMvc.perform(get("/funcionarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Carlos"));

        Mockito.verify(funcionarioService).listar();
    }

    @Test
    void deveBuscarFuncionarioPorIdERetornar200() throws Exception {
        Funcionario funcionario = new Funcionario(1L, "Carlos", "carlos@email.com");
        Mockito.when(funcionarioService.buscarPorId(1L)).thenReturn(Optional.of(funcionario));

        mockMvc.perform(get("/funcionarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"));

        Mockito.verify(funcionarioService).buscarPorId(1L);
    }

    @Test
    void deveCriarFuncionarioERetornar200() throws Exception {
        Funcionario funcionario = new Funcionario(null, "Ana", "ana@email.com");
        Funcionario funcionarioSalvo = new Funcionario(2L, "Ana", "ana@email.com");
        Mockito.when(funcionarioService.criar(any(Funcionario.class))).thenReturn(funcionarioSalvo);

        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ana"));

        Mockito.verify(funcionarioService).criar(any(Funcionario.class));
    }

    @Test
    void deveAtualizarFuncionarioERetornar200() throws Exception {
        Funcionario funcionarioAtualizado = new Funcionario(1L, "Carlos Silva", "carlos@email.com");
        Mockito.when(funcionarioService.atualizar(eq(1L), any(Funcionario.class))).thenReturn(Optional.of(funcionarioAtualizado));

        mockMvc.perform(put("/funcionarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionarioAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Silva"));

        Mockito.verify(funcionarioService).atualizar(eq(1L), any(Funcionario.class));
    }

    @Test
    void deveDeletarFuncionarioERetornar204() throws Exception {
        Mockito.when(funcionarioService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/funcionarios/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(funcionarioService).deletar(1L);
    }
}