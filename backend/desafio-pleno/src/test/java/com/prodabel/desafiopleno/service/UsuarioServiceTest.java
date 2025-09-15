package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.dto.UsuarioRequest;
import com.prodabel.desafiopleno.exception.EmailException;
import com.prodabel.desafiopleno.model.Usuario;
import com.prodabel.desafiopleno.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void deveListarUsuarios() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setBairro("Centro");
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> result = usuarioService.listar();

        assertEquals(1, result.size());
        assertEquals("João", result.get(0).getNome());
        verify(usuarioRepository).findAll();
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorId(2L);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        verify(usuarioRepository).findById(2L);
    }

    @Test
    void deveCriarUsuario() {
        UsuarioRequest request = new UsuarioRequest("Maria", "maria@email.com", "Bairro");
        when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario u = invocation.getArgument(0);
            u.setId(10L);
            return u;
        });

        Usuario result = usuarioService.criar(request);

        assertEquals("Maria", result.getNome());
        assertEquals(10L, result.getId());
        assertEquals("Bairro", result.getBairro());
        verify(usuarioRepository).findByEmail("maria@email.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveLancarEmailExceptionAoCriarUsuarioComEmailJaCadastrado() {
        UsuarioRequest request = new UsuarioRequest("Carlos", "carlos@email.com", "Centro");
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setEmail("carlos@email.com");
        when(usuarioRepository.findByEmail("carlos@email.com")).thenReturn(Optional.of(usuarioExistente));

        assertThrows(EmailException.class, () -> usuarioService.criar(request));
        verify(usuarioRepository).findByEmail("carlos@email.com");
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Antigo");
        usuario.setEmail("antigo@email.com");
        usuario.setBairro("AntigoBairro");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario atualizado = new Usuario();
        atualizado.setNome("Novo");
        atualizado.setEmail("novo@email.com");
        atualizado.setBairro("NovoBairro");
        Optional<Usuario> result = usuarioService.atualizar(1L, atualizado);

        assertTrue(result.isPresent());
        assertEquals("Novo", result.get().getNome());
        assertEquals("NovoBairro", result.get().getBairro());
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void deveRetornarEmptyAoAtualizarUsuarioInexistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());
        Usuario atualizado = new Usuario();
        Optional<Usuario> result = usuarioService.atualizar(99L, atualizado);

        assertTrue(result.isEmpty());
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void deveDeletarUsuario() {
        when(usuarioRepository.existsById(5L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(5L);

        boolean result = usuarioService.deletar(5L);

        assertTrue(result);
        verify(usuarioRepository).existsById(5L);
        verify(usuarioRepository).deleteById(5L);
    }

    @Test
    void deveRetornarFalseAoDeletarUsuarioInexistente() {
        when(usuarioRepository.existsById(6L)).thenReturn(false);

        boolean result = usuarioService.deletar(6L);

        assertFalse(result);
        verify(usuarioRepository).existsById(6L);
    }
}