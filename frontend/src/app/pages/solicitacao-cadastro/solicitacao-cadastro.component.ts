import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';

import { SolicitacaoService } from '../../services/solicitacao.service';

@Component({
  selector: 'app-solicitacao-cadastro',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './solicitacao-cadastro.component.html',
  styleUrl: './solicitacao-cadastro.component.css'
})
export class SolicitacaoCadastroComponent {
  sucessoMsg = '';
  erroMsg = '';
  carregando = false;

  form = this.fb.group({
    titulo: ['', [Validators.required, Validators.minLength(3)]],
    descricao: ['', [Validators.required, Validators.minLength(10)]],
    bairro: ['', [Validators.required]],
    emailUsuario: ['', [Validators.required, Validators.email]]
  });

  constructor(private fb: FormBuilder, private solicitacaoService: SolicitacaoService) {}

  onSubmit() {
    this.sucessoMsg = '';
    this.erroMsg = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.carregando = true;
    const solicitacao = {
      titulo: this.form.value.titulo ?? '',
      descricao: this.form.value.descricao ?? '',
      bairro: this.form.value.bairro ?? '',
      emailUsuario: this.form.value.emailUsuario ?? ''
    };
    this.solicitacaoService.cadastrar(solicitacao).subscribe({
      next: () => {
        this.sucessoMsg = 'Solicitação cadastrada com sucesso!';
        this.form.reset();
        this.carregando = false;
      },
      error: (err) => {
        this.erroMsg = err?.error?.message || 'Erro ao cadastrar solicitação.';
        this.carregando = false;
      }
    });
  }
}
