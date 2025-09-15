package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.dto.SolicitacaoNovaRequest;
import com.prodabel.desafiopleno.dto.SolicitacaoResponse;
import com.prodabel.desafiopleno.exception.EmailException;
import com.prodabel.desafiopleno.exception.FuncionarioException;
import com.prodabel.desafiopleno.exception.SolicitacaoException;
import com.prodabel.desafiopleno.model.Funcionario;
import com.prodabel.desafiopleno.model.Solicitacao;
import com.prodabel.desafiopleno.model.Usuario;
import com.prodabel.desafiopleno.repository.FuncionarioRepository;
import com.prodabel.desafiopleno.repository.SolicitacaoRepository;
import com.prodabel.desafiopleno.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitacaoServiceTest {

    private SolicitacaoRepository solicitacaoRepository;
    private UsuarioRepository usuarioRepository;
    private FuncionarioRepository funcionarioRepository;
    private SolicitacaoService solicitacaoService;

    @BeforeEach
    void setUp() {
        solicitacaoRepository = mock(SolicitacaoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        funcionarioRepository = mock(FuncionarioRepository.class);
        solicitacaoService = new SolicitacaoService(solicitacaoRepository, usuarioRepository, funcionarioRepository);
    }

    @Test
    void deveListarSolicitacoes() {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        solicitacao.setTitulo("Teste");
        solicitacao.setDescricao("Desc");
        solicitacao.setBairro("Centro");
        Usuario usuario = new Usuario();
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        solicitacao.setUsuario(usuario);
        when(solicitacaoRepository.findAll()).thenReturn(List.of(solicitacao));

        List<SolicitacaoResponse> result = solicitacaoService.listar();

        assertEquals(1, result.size());
        assertEquals("Teste", result.get(0).getTitulo());
        verify(solicitacaoRepository).findAll();
    }

    @Test
    void deveBuscarSolicitacaoPorId() {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(2L);
        when(solicitacaoRepository.findById(2L)).thenReturn(Optional.of(solicitacao));

        Optional<Solicitacao> result = solicitacaoService.buscarPorId(2L);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        verify(solicitacaoRepository).findById(2L);
    }

    @Test
    void deveCriarSolicitacao() {
        SolicitacaoNovaRequest request = new SolicitacaoNovaRequest("Titulo", "Descricao", "Bairro", "email@email.com");
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("email@email.com");
        when(usuarioRepository.findByEmail("email@email.com")).thenReturn(Optional.of(usuario));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenAnswer(invocation -> {
            Solicitacao s = invocation.getArgument(0);
            s.setId(10L);
            return s;
        });

        Solicitacao result = solicitacaoService.criar(request);

        assertEquals("Titulo", result.getTitulo());
        assertEquals(10L, result.getId());
        assertEquals(usuario, result.getUsuario());
        verify(usuarioRepository).findByEmail("email@email.com");
        verify(solicitacaoRepository).save(any(Solicitacao.class));
    }

    @Test
    void deveLancarEmailExceptionAoCriarSolicitacaoComEmailInexistente() {
        SolicitacaoNovaRequest request = new SolicitacaoNovaRequest("Titulo", "Descricao", "Bairro", "naoexiste@email.com");
        when(usuarioRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(EmailException.class, () -> solicitacaoService.criar(request));
        verify(usuarioRepository).findByEmail("naoexiste@email.com");
    }

    @Test
    void deveAtualizarSolicitacao() {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        solicitacao.setTitulo("Antigo");
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacao));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Solicitacao atualizada = new Solicitacao();
        atualizada.setTitulo("Novo");
        Optional<Solicitacao> result = solicitacaoService.atualizar(1L, atualizada);

        assertTrue(result.isPresent());
        assertEquals("Novo", result.get().getTitulo());
        verify(solicitacaoRepository).findById(1L);
        verify(solicitacaoRepository).save(any(Solicitacao.class));
    }

    @Test
    void deveRetornarEmptyAoAtualizarSolicitacaoInexistente() {
        when(solicitacaoRepository.findById(99L)).thenReturn(Optional.empty());
        Solicitacao atualizada = new Solicitacao();
        Optional<Solicitacao> result = solicitacaoService.atualizar(99L, atualizada);

        assertTrue(result.isEmpty());
        verify(solicitacaoRepository).findById(99L);
    }

    @Test
    void deveDeletarSolicitacao() {
        when(solicitacaoRepository.existsById(5L)).thenReturn(true);
        doNothing().when(solicitacaoRepository).deleteById(5L);

        boolean result = solicitacaoService.deletar(5L);

        assertTrue(result);
        verify(solicitacaoRepository).existsById(5L);
        verify(solicitacaoRepository).deleteById(5L);
    }

    @Test
    void deveRetornarFalseAoDeletarSolicitacaoInexistente() {
        when(solicitacaoRepository.existsById(6L)).thenReturn(false);

        boolean result = solicitacaoService.deletar(6L);

        assertFalse(result);
        verify(solicitacaoRepository).existsById(6L);
    }

    @Test
    void deveAtribuirFuncionario() {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        Funcionario funcionario = new Funcionario();
        funcionario.setId(2L);
        funcionario.setNome("Carlos");
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacao));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(funcionario));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenReturn(solicitacao);

        SolicitacaoResponse response = solicitacaoService.atribuirFuncionario(1L, 2L);

        assertEquals("Carlos", response.getFuncionarioResponsavel());
        verify(solicitacaoRepository).findById(1L);
        verify(funcionarioRepository).findById(2L);
        verify(solicitacaoRepository).save(solicitacao);
    }

    @Test
    void deveLancarSolicitacaoExceptionAoAtribuirFuncionarioParaSolicitacaoInexistente() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SolicitacaoException.class, () -> solicitacaoService.atribuirFuncionario(1L, 2L));
        verify(solicitacaoRepository).findById(1L);
    }

    @Test
    void deveLancarFuncionarioExceptionAoAtribuirFuncionarioInexistente() {
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setId(1L);
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacao));
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(FuncionarioException.class, () -> solicitacaoService.atribuirFuncionario(1L, 2L));
        verify(solicitacaoRepository).findById(1L);
        verify(funcionarioRepository).findById(2L);
    }
}