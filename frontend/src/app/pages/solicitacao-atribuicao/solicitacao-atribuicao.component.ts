import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { SolicitacaoService, Solicitacao } from '../../services/solicitacao.service';
import { FuncionarioService, Funcionario } from '../../services/funcionario.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-solicitacao-atribuicao',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './solicitacao-atribuicao.component.html',
  styleUrl: './solicitacao-atribuicao.component.css'
})
export class SolicitacaoAtribuicaoComponent {
  sucessoMsg = '';
  erroMsg = '';
  carregando = false;

  solicitacoes$!: Observable<Solicitacao[]>;
  funcionarios$!: Observable<Funcionario[]>;

  form = this.fb.group({
    solicitacaoId: [null as number | null, [Validators.required]],
    funcionarioId: [null as number | null, [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    private solicitacaoService: SolicitacaoService,
    private funcionarioService: FuncionarioService
  ) {}

  ngOnInit() {
    this.solicitacoes$ = this.solicitacaoService.listar();
    this.funcionarios$ = this.funcionarioService.listar();
  }

  onSubmit() {
    this.sucessoMsg = '';
    this.erroMsg = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.carregando = true;

    const solicitacaoId = this.form.value.solicitacaoId!;
    const funcionarioId = this.form.value.funcionarioId!;

    this.solicitacaoService.atribuir(solicitacaoId, funcionarioId).subscribe({
      next: () => {
        this.sucessoMsg = 'Funcionário atribuído com sucesso!';
        this.carregando = false;
      },
      error: (err) => {
        this.erroMsg = err?.error?.message || 'Erro ao atribuir funcionário.';
        this.carregando = false;
      }
    });
  }
}
