import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ListPolizasComponent } from './pages/list-polizas/list-polizas.component';
import { CreatePolizaComponent } from './pages/create-poliza/create-poliza.component';
import { EditPolizaComponent } from './pages/edit-poliza/edit-poliza.component';
import { DeletePolizaComponent } from './pages/delete-poliza/delete-poliza.component';
import { ShowPolizaComponent } from './pages/show-poliza/show-poliza.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
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
      },
      {
        path: 'polizas/:id',
        component: ShowPolizaComponent,
      },
      {
        path: 'polizas/:id/edit',
        component: EditPolizaComponent,
      },
      {
        path: 'polizas/:id/delete',
        component: DeletePolizaComponent,
      },
      {
        path: '**',
        redirectTo: 'polizas',
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
