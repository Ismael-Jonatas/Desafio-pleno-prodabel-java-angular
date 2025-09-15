import { Component, OnInit } from '@angular/core';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { MetricasService } from '../../services/metricas.service';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';

// ...existing imports...

@Component({
  selector: 'app-atendimentos-chart',
  standalone: true,
  imports: [CommonModule, BaseChartDirective],
  templateUrl: './atendimentos-chart.component.html',
  styleUrls: ['./atendimentos-chart.component.css']
})
export class AtendimentosChartComponent implements OnInit {
  loading = true;
  error?: string;

  public barChartLabels: string[] = [];
  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: this.barChartLabels,
    datasets: [
      {
        data: [],
        label: 'Atendimentos por Bairro',
        backgroundColor: [],
        borderColor: '#3730A3',
        borderWidth: 1
      }
    ]
  };

  public barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: true },
      tooltip: { enabled: true },
      title: { display: true, text: 'Atendimentos por Bairro' }
    },
    scales: {
      x: { ticks: { autoSkip: false, maxRotation: 45, minRotation: 0 } },
      y: { beginAtZero: true, title: { display: true, text: 'Qtd' } }
    }
  };

  constructor(private metricasService: MetricasService) {}

  ngOnInit(): void {
    this.metricasService.getAtendimentosPorBairro().subscribe({
      next: (map) => {
        const labels = Object.keys(map);
        const values = Object.values(map);

        // Cálculo das cores
        const min = Math.min(...values);
        const max = Math.max(...values);
        const media = values.reduce((a, b) => a + b, 0) / values.length;

        const backgroundColor = values.map(v => {
          if (v === min) return '#ef4444'; // vermelho
          if (v === max) return '#22c55e'; // verde
          return '#facc15'; // amarelo
        });

        this.barChartLabels.splice(0, this.barChartLabels.length, ...labels);
        this.barChartData = {
          labels,
          datasets: [
            {
              data: values,
              label: 'Atendimentos por Bairro',
              backgroundColor,
              borderColor: '#3730A3',
              borderWidth: 1
            }
          ]
        };
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Falha ao carregar dados.';
        console.error(err);
        this.loading = false;
      }
    });
  }
}
