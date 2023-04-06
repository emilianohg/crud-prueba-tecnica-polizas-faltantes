import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FeedbackErrorComponent } from './components/feedback-error/feedback-error.component';
import { FeedbackDirective } from './components/feedback-error/feedback.directive';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { ListPolizasComponent } from './pages/list-polizas/list-polizas.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { CreatePolizaComponent } from './pages/create-poliza/create-poliza.component';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './pages/login/login.component';
import { InputSearchEmpleadoComponent } from './components/input-search-empleado/input-search-empleado.component';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    ListPolizasComponent,
    MainLayoutComponent,
    FeedbackErrorComponent,
    FeedbackDirective,
    NavbarComponent,
    CreatePolizaComponent,
    LoginComponent,
    InputSearchEmpleadoComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbTypeaheadModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
