import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Funcionario {
  id: number;
  nome: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  constructor(private http: HttpClient) {}

  listar(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(`${environment.apiUrl}/funcionarios`);
  }
}
