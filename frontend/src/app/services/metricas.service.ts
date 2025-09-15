import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class MetricasService {
  private baseUrl = `${environment.apiUrl}/api/metricas`;

  constructor(private http: HttpClient) {}

  getAtendimentosPorBairro() {

    return this.http.get<Record<string, number>>(
      `${this.baseUrl}/atendimentos-por-bairro`
    );
  }
}
