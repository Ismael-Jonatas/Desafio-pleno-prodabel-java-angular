package com.prodabel.desafiopleno.controller;

import com.prodabel.desafiopleno.service.MetricasService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

class MetricasControllerTest {

    private final MetricasService metricasService = Mockito.mock(MetricasService.class);
    private final MetricasController controller = new MetricasController(metricasService);
    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    @Test
    void deveRetornarAtendimentosPorBairroComStatus200() throws Exception {
        Map<String, Long> resultado = Map.of("Centro", 5L, "Bairro", 3L);
        when(metricasService.getAtendimentosPorBairro()).thenReturn(resultado);

        mockMvc.perform(get("/api/metricas/atendimentos-por-bairro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Centro").value(5))
                .andExpect(jsonPath("$.Bairro").value(3));

        verify(metricasService).getAtendimentosPorBairro();
    }
}