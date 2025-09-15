import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface UsuarioCadastro {
  nome: string;
  email: string;
  bairro: string;
}

export interface Usuario {
  id: number;
  nome: string;
  email: string;
  bairro: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {

  constructor(private http: HttpClient) {}

  listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${environment.apiUrl}/usuarios`);
  }

  register(usuario: UsuarioCadastro): Observable<any> {
    return this.http.post(`${environment.apiUrl}/usuarios`, usuario);
  }
}
