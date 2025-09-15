package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.dto.SolicitacaoNovaRequest;
import com.prodabel.desafiopleno.exception.EmailException;
import com.prodabel.desafiopleno.exception.FuncionarioException;
import com.prodabel.desafiopleno.exception.SolicitacaoException;
import com.prodabel.desafiopleno.model.Funcionario;
import com.prodabel.desafiopleno.model.Solicitacao;
import com.prodabel.desafiopleno.model.Usuario;
import com.prodabel.desafiopleno.repository.SolicitacaoRepository;
import com.prodabel.desafiopleno.repository.UsuarioRepository;
import com.prodabel.desafiopleno.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prodabel.desafiopleno.dto.SolicitacaoResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository, FuncionarioRepository funcionarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    private Solicitacao toSolicitacao(SolicitacaoNovaRequest solicitacaoNovaRequest) {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTitulo(solicitacaoNovaRequest.titulo());
        solicitacao.setDescricao(solicitacaoNovaRequest.descricao());
        solicitacao.setBairro(solicitacaoNovaRequest.bairro());
        return solicitacao;
    }

    private SolicitacaoResponse toSolicitacaoResponse( Solicitacao solicitacao) {
        SolicitacaoResponse response = new SolicitacaoResponse();
        response.setId(solicitacao.getId());
        response.setTitulo(solicitacao.getTitulo());
        response.setDescricao(solicitacao.getDescricao());
        response.setBairro(solicitacao.getBairro());
        response.setNomeSolicitante(solicitacao.getUsuario() != null ? solicitacao.getUsuario().getNome() : null);
        response.setEmailUsuario(solicitacao.getUsuario() != null ? solicitacao.getUsuario().getEmail() : null);
        response.setFuncionarioResponsavel(solicitacao.getFuncionario() != null ? solicitacao.getFuncionario().getNome() : null);
        response.setDataSolicitacao(formatarData(solicitacao.getCriadoEm()));

        return response;
    }

    private String formatarData(LocalDateTime data) {
        if (data == null) return null;
        return data.format(java.time.format.DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public List<SolicitacaoResponse> listar() {
        return solicitacaoRepository.findAll()
                .stream()
                .map(this::toSolicitacaoResponse)
                .collect(Collectors.toList());
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return solicitacaoRepository.findById(id);
    }

    @Transactional
    public Solicitacao criar(SolicitacaoNovaRequest solicitacaoNovaRequest) {
        Solicitacao solicitacao = toSolicitacao(solicitacaoNovaRequest);

        Usuario usuario = usuarioRepository.findByEmail(solicitacaoNovaRequest.emailUsuario())
                .orElseThrow(() -> new EmailException("E-mail não cadastrado."));

        solicitacao.setUsuario(usuario);

        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
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

    @Transactional
    public boolean deletar(Long id) {
        if (solicitacaoRepository.existsById(id)) {
            solicitacaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public SolicitacaoResponse atribuirFuncionario(Long solicitacaoId, Long funcionarioId) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new SolicitacaoException("Solicitação não encontrada"));
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new FuncionarioException("Funcionário não encontrado"));
        solicitacao.setFuncionario(funcionario);
        solicitacaoRepository.save(solicitacao);
        return toSolicitacaoResponse(solicitacao);
    }
}
