package com.prodabel.desafiopleno.repository;

import com.prodabel.desafiopleno.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}

