package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.model.Solicitacao;
import com.prodabel.desafiopleno.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public List<Solicitacao> listar() {
        return solicitacaoRepository.findAll();
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return solicitacaoRepository.findById(id);
    }

    public Solicitacao criar(Solicitacao solicitacao) {
        return solicitacaoRepository.save(solicitacao);
    }

    public Optional<Solicitacao> atualizar(Long id, Solicitacao solicitacaoAtualizada) {
        return solicitacaoRepository.findById(id).map(solicitacao -> {
            solicitacao.setTitulo(solicitacaoAtualizada.getTitulo());
            solicitacao.setDescricao(solicitacaoAtualizada.getDescricao());
            solicitacao.setBairro(solicitacaoAtualizada.getBairro());
            solicitacao.setUsuario(solicitacaoAtualizada.getUsuario());
            solicitacao.setFuncionario(solicitacaoAtualizada.getFuncionario());
            return solicitacaoRepository.save(solicitacao);
        });
    }

    public boolean deletar(Long id) {
        if (solicitacaoRepository.existsById(id)) {
            solicitacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
