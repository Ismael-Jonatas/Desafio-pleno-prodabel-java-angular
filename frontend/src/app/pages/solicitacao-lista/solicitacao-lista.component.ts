import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { SolicitacaoService, Solicitacao } from '../../services/solicitacao.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-solicitacao-lista',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule
  ],
  templateUrl: './solicitacao-lista.component.html',
  styleUrls: ['./solicitacao-lista.component.css']
})
export class SolicitacaoListaComponent {
  solicitacoes$!: Observable<Solicitacao[]>;

  displayedColumns: string[] = [
    'id',
    'titulo',
    'descricao',
    'bairro',
    'nomeSolicitante',
    'emailUsuario',
    'funcionarioResponsavel',
    'dataSolicitacao'
  ];

  constructor(private solicitacaoService: SolicitacaoService) {
    this.solicitacoes$ = this.solicitacaoService.listar();
  }
}
