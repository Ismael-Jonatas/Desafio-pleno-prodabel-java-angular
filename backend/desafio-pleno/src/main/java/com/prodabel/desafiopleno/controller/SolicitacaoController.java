package com.prodabel.desafiopleno.controller;

import com.prodabel.desafiopleno.dto.AtribuirFuncionarioRequest;
import com.prodabel.desafiopleno.dto.SolicitacaoNovaRequest;
import com.prodabel.desafiopleno.model.Solicitacao;
import com.prodabel.desafiopleno.service.SolicitacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prodabel.desafiopleno.dto.SolicitacaoResponse;

import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    @GetMapping
    public List<SolicitacaoResponse> listar() {
        return solicitacaoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        return solicitacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Solicitacao criar(@Valid @RequestBody SolicitacaoNovaRequest solicitacaoNovaRequest) {
        return solicitacaoService.criar(solicitacaoNovaRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitacao> atualizar(@PathVariable Long id, @Valid @RequestBody Solicitacao solicitacaoAtualizada) {
        return solicitacaoService.atualizar(id, solicitacaoAtualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (solicitacaoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/atribuir")
    public ResponseEntity<SolicitacaoResponse> atribuirFuncionario(
            @PathVariable Long id,
            @RequestBody AtribuirFuncionarioRequest request) {
        SolicitacaoResponse response = solicitacaoService.atribuirFuncionario(id, request.getFuncionarioId());
        return ResponseEntity.ok(response);
    }
}

