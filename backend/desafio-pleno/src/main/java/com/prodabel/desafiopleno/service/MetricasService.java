package com.prodabel.desafiopleno.service;


import com.prodabel.desafiopleno.repository.SolicitacaoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetricasService {
    private final SolicitacaoRepository solicitacaoRepository;

    public MetricasService(SolicitacaoRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    public Map<String, Long> getAtendimentosPorBairro() {
        List<Object[]> resultados = solicitacaoRepository.countByBairro();
        Map<String, Long> mapa = new HashMap<>();
        for (Object[] obj : resultados) {
            String bairro = (String) obj[0];
            Long total = (Long) obj[1];
            mapa.put(bairro, total);
        }
        return mapa;
    }
}
