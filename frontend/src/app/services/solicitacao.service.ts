import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface SolicitacaoCadastro {
  titulo: string;
  descricao: string;
  bairro: string;
  emailUsuario: string;
}

export interface Solicitacao {
  id: number;
  titulo: string;
  descricao: string;
  bairro: string;
  nomeSolicitante: string;
  emailUsuario: string;
  funcionarioResponsavel?: string;
  dataSolicitacao: string;
}

@Injectable({ providedIn: 'root' })
export class SolicitacaoService {
  constructor(private http: HttpClient) {}

  cadastrar(solicitacao: SolicitacaoCadastro): Observable<any> {
    return this.http.post(`${environment.apiUrl}/solicitacoes`, solicitacao);
  }

  listar(): Observable<Solicitacao[]> {
    return this.http.get<Solicitacao[]>(`${environment.apiUrl}/solicitacoes`);
  }

  atribuir(solicitacaoId: number, funcionarioId: number) {
    return this.http.post(
      `${environment.apiUrl}/solicitacoes/${solicitacaoId}/atribuir`,
      { funcionarioId }
    );
  }
}
