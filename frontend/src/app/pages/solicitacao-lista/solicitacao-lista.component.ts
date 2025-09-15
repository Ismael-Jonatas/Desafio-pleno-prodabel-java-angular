import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SolicitacaoService, Solicitacao } from '../../services/solicitacao.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-solicitacao-lista',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './solicitacao-lista.component.html',
  styleUrl: './solicitacao-lista.component.css'
})
export class SolicitacaoListaComponent {
  solicitacoes$!: Observable<Solicitacao[]>;

  constructor(private solicitacaoService: SolicitacaoService) {
    this.solicitacoes$ = this.solicitacaoService.listar();
  }
}
