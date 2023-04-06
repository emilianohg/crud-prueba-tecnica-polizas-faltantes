import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ListPolizasComponent } from './pages/list-polizas/list-polizas.component';
import { CreatePolizaComponent } from './pages/create-poliza/create-poliza.component';

const routes: Routes = [
  {
    path: 'auth',
    component: LoginComponent,
  },
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'polizas',
        pathMatch: 'full',
      },
      {
        path: 'polizas',
        component: ListPolizasComponent,
      },
      {
        path: 'polizas/create',
        component: CreatePolizaComponent,
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
