package com.prodabel.desafiopleno.repository;

import com.prodabel.desafiopleno.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
