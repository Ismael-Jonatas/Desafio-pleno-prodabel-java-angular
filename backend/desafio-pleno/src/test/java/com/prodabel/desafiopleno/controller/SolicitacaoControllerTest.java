package com.prodabel.desafiopleno.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.prodabel.desafiopleno.dto.AtribuirFuncionarioRequest;
import com.prodabel.desafiopleno.dto.SolicitacaoNovaRequest;
import com.prodabel.desafiopleno.dto.SolicitacaoResponse;
import com.prodabel.desafiopleno.model.Solicitacao;
import com.prodabel.desafiopleno.service.SolicitacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SolicitacaoControllerTest {


    private final SolicitacaoService solicitacaoService = Mockito.mock(SolicitacaoService.class);
    private final SolicitacaoController controller = new SolicitacaoController(solicitacaoService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void deveListarSolicitacoesERetornar200() throws Exception {
        SolicitacaoResponse response = new SolicitacaoResponse(1L, "Título", "Descrição", "Centro", "João", "joao@email.com", "Carlos", "2024/06/01");
        Mockito.when(solicitacaoService.listar()).thenReturn(List.of(response));

        mockMvc.perform(get("/solicitacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Título"));

        Mockito.verify(solicitacaoService).listar();
    }

    @Test
    void deveBuscarSolicitacaoPorIdERetornar200() throws Exception {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        solicitacao.setTitulo("Título");
        Mockito.when(solicitacaoService.buscarPorId(1L)).thenReturn(Optional.of(solicitacao));

        mockMvc.perform(get("/solicitacoes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        Mockito.verify(solicitacaoService).buscarPorId(1L);
    }

    @Test
    void deveCriarSolicitacaoERetornar200() throws Exception {
        SolicitacaoNovaRequest request = new SolicitacaoNovaRequest("Título", "Descrição", "Centro", "joao@email.com");
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        solicitacao.setTitulo("Título");
        Mockito.when(solicitacaoService.criar(any(SolicitacaoNovaRequest.class))).thenReturn(solicitacao);

        mockMvc.perform(post("/solicitacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        Mockito.verify(solicitacaoService).criar(any(SolicitacaoNovaRequest.class));
    }

    @Test
    void deveAtualizarSolicitacaoERetornar200() throws Exception {
        Solicitacao solicitacaoAtualizada = new Solicitacao();
        solicitacaoAtualizada.setId(1L);
        solicitacaoAtualizada.setTitulo("Novo Título");
        Mockito.when(solicitacaoService.atualizar(eq(1L), any(Solicitacao.class))).thenReturn(Optional.of(solicitacaoAtualizada));

        mockMvc.perform(put("/solicitacoes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(solicitacaoAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Novo Título"));

        Mockito.verify(solicitacaoService).atualizar(eq(1L), any(Solicitacao.class));
    }

    @Test
    void deveDeletarSolicitacaoERetornar204() throws Exception {
        Mockito.when(solicitacaoService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/solicitacoes/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(solicitacaoService).deletar(1L);
    }

    @Test
    void deveAtribuirFuncionarioERetornar200() throws Exception {
        AtribuirFuncionarioRequest request = new AtribuirFuncionarioRequest(2L);
        SolicitacaoResponse response = new SolicitacaoResponse(1L, "Título", "Descrição", "Centro", "João", "joao@email.com", "Carlos", "2024/06/01");
        Mockito.when(solicitacaoService.atribuirFuncionario(eq(1L), eq(2L))).thenReturn(response);

        mockMvc.perform(post("/solicitacoes/1/atribuir")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.funcionarioResponsavel").value("Carlos"));

        Mockito.verify(solicitacaoService).atribuirFuncionario(eq(1L), eq(2L));
    }
}