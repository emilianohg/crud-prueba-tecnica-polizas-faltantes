import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import Toast from 'src/app/utils/toast';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required],
    });
  }

  login() {
    const data = this.form.value;
    this.authService.login(data).subscribe({
      next: (response) => {
        Toast.fire({
          icon: 'success',
          title: 'Autenticado correctamente',
        });
        this.router.navigateByUrl('/');
      },
      error: (error) => {
        console.error(error);
        Toast.fire({
          icon: 'error',
          title: error.error.data.message,
        });
      }
    });
  }
}
