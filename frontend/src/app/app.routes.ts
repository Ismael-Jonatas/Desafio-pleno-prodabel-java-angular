import { Routes } from '@angular/router';
import { UsuarioCadastroComponent } from './pages/usuario-cadastro/usuario-cadastro.component';
import { UsuarioListaComponent } from './pages/usuario-lista/usuario-lista.component';
import { SolicitacaoCadastroComponent } from './pages/solicitacao-cadastro/solicitacao-cadastro.component';
import { SolicitacaoListaComponent } from './pages/solicitacao-lista/solicitacao-lista.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'usuarios/novo', pathMatch: 'full' },
	{ path: 'usuarios/novo', component: UsuarioCadastroComponent },
  { path: 'usuarios', component: UsuarioListaComponent },
  { path: 'solicitacoes/novo', component: SolicitacaoCadastroComponent },
  { path: 'solicitacoes', component: SolicitacaoListaComponent },
	{ path: '**', redirectTo: 'usuarios/novo' }
];
