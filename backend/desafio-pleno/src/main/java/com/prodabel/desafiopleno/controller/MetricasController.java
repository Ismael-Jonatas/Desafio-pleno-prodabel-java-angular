package com.prodabel.desafiopleno.controller;

import com.prodabel.desafiopleno.service.MetricasService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/metricas")
@RequiredArgsConstructor
public class MetricasController {
    private final MetricasService metricasService;

    @GetMapping("/atendimentos-por-bairro")
    public Map<String, Long> atendimentosPorBairro() {
        return metricasService.getAtendimentosPorBairro();
    }
}
