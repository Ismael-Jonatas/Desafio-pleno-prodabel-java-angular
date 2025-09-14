import { Routes } from '@angular/router';
import { UsuarioCadastroComponent } from './pages/usuario-cadastro/usuario-cadastro.component';
import { UsuarioListaComponent } from './pages/usuario-lista/usuario-lista.component';

export const routes: Routes = [
	{ path: '', redirectTo: 'usuarios/novo', pathMatch: 'full' },
	{ path: 'usuarios/novo', component: UsuarioCadastroComponent },
  { path: 'usuarios', component: UsuarioListaComponent },
	{ path: '**', redirectTo: 'usuarios/novo' }
];
