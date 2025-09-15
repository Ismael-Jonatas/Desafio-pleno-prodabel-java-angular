package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.dto.UsuarioRequest;
import com.prodabel.desafiopleno.model.Usuario;
import com.prodabel.desafiopleno.repository.UsuarioRepository;
import com.prodabel.desafiopleno.exception.EmailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    private Usuario toUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setBairro(usuarioRequest.bairro());
        return usuario;
    }

    @Transactional
    public Usuario criar(UsuarioRequest usuarioRequest) {

        Usuario usuario = toUsuario(usuarioRequest);

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new EmailException("E-mail já cadastrado.");
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Optional<Usuario> atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setBairro(usuarioAtualizado.getBairro());
            return usuarioRepository.save(usuario);
        });
    }

    @Transactional
    public boolean deletar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}