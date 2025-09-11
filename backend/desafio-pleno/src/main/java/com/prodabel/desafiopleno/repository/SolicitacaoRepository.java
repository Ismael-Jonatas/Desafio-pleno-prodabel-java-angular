package com.prodabel.desafiopleno.repository;

import com.prodabel.desafiopleno.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    @Query("SELECT s.bairro as bairro, COUNT(s) as total FROM Solicitacao s GROUP BY s.bairro")
    List<Object[]> countByBairro();
}
