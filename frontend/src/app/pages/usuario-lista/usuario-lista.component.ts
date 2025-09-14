import { Component } from '@angular/core';
import { UsuarioService, Usuario } from '../../services/usuario.service';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-usuario-lista',
  templateUrl: './usuario-lista.component.html',
  styleUrls: ['./usuario-lista.component.css'],
  standalone: true,
  imports: [CommonModule]

})
export class UsuarioListaComponent {
  usuarios$!: Observable<Usuario[]>;

  constructor(private usuarioService: UsuarioService) {
    this.usuarios$ = this.usuarioService.listar();
  }
}
