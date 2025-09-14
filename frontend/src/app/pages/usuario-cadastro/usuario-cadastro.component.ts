import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-usuario-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './usuario-cadastro.component.html',
  styleUrl: './usuario-cadastro.component.css'
})
export class UsuarioCadastroComponent {
  carregando = false;
  sucessoMsg = '';
  erroMsg = '';

  form = this.fb.group({
    nome: ['', [Validators.required, Validators.minLength(3), Validators.pattern('^[A-Za-zÀ-ÿ ]+$')]],
    email: ['', [Validators.required, Validators.email]],
    bairro: ['', [Validators.required]]
  });

  constructor(private fb: FormBuilder, private usuarioService: UsuarioService) {}

  onSubmit() {
    this.sucessoMsg = '';
    this.erroMsg = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.carregando = true;

    const usuario = {
      nome: this.form.value.nome ?? '',
      email: this.form.value.email ?? '',
      bairro: this.form.value.bairro ?? ''
    };

    this.usuarioService.register(usuario).subscribe({
      next: () => {
        this.sucessoMsg = 'Usuário cadastrado com sucesso!';
        this.form.reset();
        this.carregando = false;
      },
      error: (err) => {
        this.erroMsg = err?.error || 'Erro ao cadastrar usuário.';
        this.carregando = false;
      }
    });
  }
}
