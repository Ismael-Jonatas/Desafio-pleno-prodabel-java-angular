import { Routes } from '@angular/router';
import { UsuarioCadastroComponent } from './pages/usuario-cadastro/usuario-cadastro.component';
import { UsuarioListaComponent } from './pages/usuario-lista/usuario-lista.component';
import { SolicitacaoCadastroComponent } from './pages/solicitacao-cadastro/solicitacao-cadastro.component';
import { SolicitacaoListaComponent } from './pages/solicitacao-lista/solicitacao-lista.component';
import { SolicitacaoAtribuicaoComponent } from './pages/solicitacao-atribuicao/solicitacao-atribuicao.component';
import { AtendimentosChartComponent } from './pages/atendimentos-chart/atendimentos-chart.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'usuarios/novo', pathMatch: 'full' },
	{ path: 'usuarios/novo', component: UsuarioCadastroComponent },
  { path: 'usuarios', component: UsuarioListaComponent },
  { path: 'solicitacoes/novo', component: SolicitacaoCadastroComponent },
  { path: 'solicitacoes', component: SolicitacaoListaComponent },
  { path: 'solicitacoes/atribuir', component: SolicitacaoAtribuicaoComponent },
  { path: 'atendimentos', component: AtendimentosChartComponent },
	{ path: '**', redirectTo: 'usuarios/novo' }
];
