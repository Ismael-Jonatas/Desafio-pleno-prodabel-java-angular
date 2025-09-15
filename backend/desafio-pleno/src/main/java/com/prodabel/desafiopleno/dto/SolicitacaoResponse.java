package com.prodabel.desafiopleno.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoResponse {
    private Long id;
    private String titulo;
    private String descricao;
    private String bairro;
    private String nomeSolicitante;
    private String emailUsuario;
    private String funcionarioResponsavel;
    private String dataSolicitacao;
}
