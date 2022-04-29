import { ExtratoComponent } from './pages/contas/extrato/extrato.component';
import { TransferenciaComponent } from './pages/contas/transferencia/transferencia.component';
import { SaqueComponent } from './pages/contas/saque/saque.component';
import { ContasCadastrarEditarComponent } from './pages/contas/contas-cadastrar-editar/contas-cadastrar-editar.component';
import { ClientesCadastrarEditarComponent } from './pages/clientes/clientes-cadastrar-editar/clientes-cadastrar-editar.component';
import { ContasComponent } from './pages/contas/contas.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { DepositoComponent } from './pages/contas/deposito/deposito.component';
import { IndrabankComponent } from './pages/indrabank/indrabank.component';
import { HeaderComponent } from './components/header/header.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: IndrabankComponent,
  },
  {
    path: 'bank',
    component: HeaderComponent,
  },
  {
    path: 'clientes',
    component: ClientesComponent,
  },
  {
    path: 'contas',
    component: ContasComponent,
  },
  {
    path: 'clientes/cadastrar',
    component: ClientesCadastrarEditarComponent,
  },
  {
    path: 'clientes/editar/:id',
    component: ClientesCadastrarEditarComponent,
  },
  {
    path: 'contas/cadastrarConta',
    component: ContasCadastrarEditarComponent,
  },
  {
    path: 'contas/deposito/:id',
    component: DepositoComponent,
  },
  {
    path: 'contas/saque/:id',
    component: SaqueComponent,
  },
  {
    path: 'contas/transferencia/:id',
    component: TransferenciaComponent,
  },
  {
    path: 'contas/extrato/:id',
    component: ExtratoComponent,
  },
  {
    path: 'contas/editarConta/:id',
    component: ContasCadastrarEditarComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
