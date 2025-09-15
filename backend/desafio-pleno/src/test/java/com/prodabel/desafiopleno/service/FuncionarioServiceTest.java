package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.model.Funcionario;
import com.prodabel.desafiopleno.repository.FuncionarioRepository;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    private FuncionarioRepository funcionarioRepository;
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(FuncionarioRepository.class);
        funcionarioService = new FuncionarioService(funcionarioRepository);
    }

    @Test
    void deveListarFuncionarios() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("João");
        funcionario.setEmail("joao@email.com");
        when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));

        List<Funcionario> result = funcionarioService.listar();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
        verify(funcionarioRepository).findAll();
    }

    @Test
    void deveBuscarFuncionarioPorId() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(2L);
        when(funcionarioRepository.findById(2L)).thenReturn(Optional.of(funcionario));

        Optional<Funcionario> result = funcionarioService.buscarPorId(2L);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        verify(funcionarioRepository).findById(2L);
    }

    @Test
    void deveCriarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Maria");
        funcionario.setEmail("maria@email.com");
        when(funcionarioRepository.save(any(Funcionario.class))).thenAnswer(invocation -> {
            Funcionario f = invocation.getArgument(0);
            f.setId(10L);
            return f;
        });

        Funcionario result = funcionarioService.criar(funcionario);

        assertEquals("Maria", result.getNome());
        assertEquals(10L, result.getId());
        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    void deveAtualizarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Antigo");
        funcionario.setEmail("antigo@email.com");
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(funcionarioRepository.save(any(Funcionario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario atualizado = new Funcionario();
        atualizado.setNome("Novo");
        atualizado.setEmail("novo@email.com");
        Optional<Funcionario> result = funcionarioService.atualizar(1L, atualizado);

        assertTrue(result.isPresent());
        assertEquals("Novo", result.get().getNome());
        verify(funcionarioRepository).findById(1L);
        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    void deveRetornarEmptyAoAtualizarFuncionarioInexistente() {
        when(funcionarioRepository.findById(99L)).thenReturn(Optional.empty());
        Funcionario atualizado = new Funcionario();
        Optional<Funcionario> result = funcionarioService.atualizar(99L, atualizado);

        assertTrue(result.isEmpty());
        verify(funcionarioRepository).findById(99L);
    }

    @Test
    void deveDeletarFuncionario() {
        when(funcionarioRepository.existsById(5L)).thenReturn(true);
        doNothing().when(funcionarioRepository).deleteById(5L);

        boolean result = funcionarioService.deletar(5L);

        assertTrue(result);
        verify(funcionarioRepository).existsById(5L);
        verify(funcionarioRepository).deleteById(5L);
    }

    @Test
    void deveRetornarFalseAoDeletarFuncionarioInexistente() {
        when(funcionarioRepository.existsById(6L)).thenReturn(false);

        boolean result = funcionarioService.deletar(6L);

        assertFalse(result);
        verify(funcionarioRepository).existsById(6L);
    }
}