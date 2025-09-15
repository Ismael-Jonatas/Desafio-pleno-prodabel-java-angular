package com.prodabel.desafiopleno.service;

import com.prodabel.desafiopleno.repository.SolicitacaoRepository;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MetricasServiceTest {

    private SolicitacaoRepository solicitacaoRepository;
    private MetricasService metricasService;

    @BeforeEach
    void setUp() {
        solicitacaoRepository = mock(SolicitacaoRepository.class);
        metricasService = new MetricasService(solicitacaoRepository);
    }

    @Test
    void deveRetornarAtendimentosPorBairro() {
        List<Object[]> resultados = new ArrayList<>();
        resultados.add(new Object[]{"Centro", 5L});
        resultados.add(new Object[]{"Bairro", 2L});
        when(solicitacaoRepository.countByBairro()).thenReturn(resultados);

        Map<String, Long> mapa = metricasService.getAtendimentosPorBairro();

        assertEquals(2, mapa.size());
        assertEquals(5L, mapa.get("Centro"));
        assertEquals(2L, mapa.get("Bairro"));
        verify(solicitacaoRepository).countByBairro();
    }

    @Test
    void deveRetornarMapaVazioQuandoNaoHaAtendimentos() {
        when(solicitacaoRepository.countByBairro()).thenReturn(Collections.emptyList());

        Map<String, Long> mapa = metricasService.getAtendimentosPorBairro();

        assertTrue(mapa.isEmpty());
        verify(solicitacaoRepository).countByBairro();
    }
}